#!/usr/bin/env python3
"""Statically inspect archived applet JARs. Never loads or executes their classes.

Uses only Python's standard library; OpenSSL is optional for certificates.
Input defaults to ../../cia-2010-websites-dump/ relative to the working
directory. Reports are written to jar/ inside the current working directory.
This file is self-contained; --self-test runs its embedded regression checks.
"""

import argparse
import datetime as dt
import sys
import tempfile
import unittest
import csv
from urllib.parse import urlsplit
import base64
import collections
import gzip
import hashlib
import io
import json
import re
import shutil
import struct
import subprocess
import zipfile
import zlib
from pathlib import Path


def sha256(data):
    return hashlib.sha256(data).hexdigest()


class Reader:
    def __init__(self, data):
        self.data, self.pos = data, 0

    def take(self, size):
        if size < 0 or self.pos + size > len(self.data):
            raise ValueError("truncated data")
        result = self.data[self.pos:self.pos + size]
        self.pos += size
        return result

    def u1(self):
        return self.take(1)[0]

    def u2(self):
        return int.from_bytes(self.take(2), "big")

    def u4(self):
        return int.from_bytes(self.take(4), "big")


def mutf8(data):
    # JVM modified UTF-8 encodes NUL as C0 80 and supplementary characters
    # as UTF-16 surrogate pairs. Preserve isolated surrogates for XOR decoding.
    return data.replace(b"\xc0\x80", b"\x00").decode("utf-8", "surrogatepass")


class ClassFile:
    def __init__(self, data):
        r = Reader(data)
        if r.take(4) != b"\xca\xfe\xba\xbe":
            raise ValueError("not a class file")
        self.minor, self.major = r.u2(), r.u2()
        self.cp = [None] * r.u2()
        i = 1
        while i < len(self.cp):
            tag = r.u1()
            if tag == 1:
                value = mutf8(r.take(r.u2()))
            elif tag in (3, 4):
                value = struct.unpack(">i" if tag == 3 else ">f", r.take(4))[0]
            elif tag in (5, 6):
                value = struct.unpack(">q" if tag == 5 else ">d", r.take(8))[0]
            elif tag in (7, 8, 16, 19, 20):
                value = r.u2()
            elif tag in (9, 10, 11, 12, 17, 18):
                value = (r.u2(), r.u2())
            elif tag == 15:
                value = (r.u1(), r.u2())
            else:
                raise ValueError(f"unknown constant tag {tag}")
            self.cp[i] = (tag, value)
            i += 2 if tag in (5, 6) else 1
        self.access, this, parent = r.u2(), r.u2(), r.u2()
        self.name = self.utf(self.cp[this][1])
        self.parent = self.utf(self.cp[parent][1]) if parent else None
        r.take(2 * r.u2())
        self.fields = self.members(r)
        self.methods = self.members(r)
        self.attributes = self.attrs(r)
        self.source = None
        if "SourceFile" in self.attributes:
            self.source = self.utf(int.from_bytes(self.attributes["SourceFile"], "big"))

    def utf(self, index):
        tag, value = self.cp[index]
        if tag != 1:
            raise ValueError("expected UTF8 constant")
        return value

    def attrs(self, r):
        result = {}
        for _ in range(r.u2()):
            name = self.utf(r.u2())
            result[name] = r.take(r.u4())
        return result

    def members(self, r):
        result = []
        for _ in range(r.u2()):
            access, name, desc = r.u2(), self.utf(r.u2()), self.utf(r.u2())
            attrs = self.attrs(r)
            code = None
            if "Code" in attrs:
                cr = Reader(attrs["Code"])
                cr.take(4)
                code = cr.take(cr.u4())
            result.append(dict(access=access, name=name, descriptor=desc, code=code))
        return result

    def string(self, index):
        item = self.cp[index]
        return self.utf(item[1]) if item and item[0] == 8 else None


def instructions(code):
    """Yield (offset, opcode, operands), respecting variable-length instructions."""
    sizes = {16: 1, 17: 2, 18: 1, 19: 2, 20: 2, 132: 2,
             169: 1, 185: 4, 186: 4, 187: 2, 188: 1, 189: 2,
             192: 2, 193: 2, 197: 3, 198: 2, 199: 2, 200: 4, 201: 4}
    sizes.update({op: 1 for op in list(range(21, 26)) + list(range(54, 59))})
    sizes.update({op: 2 for op in list(range(153, 169)) + list(range(178, 185))})
    r = Reader(code)
    while r.pos < len(code):
        offset, op = r.pos, r.u1()
        if op in (170, 171):
            r.take((-r.pos) % 4)
            default = offset + struct.unpack(">i", r.take(4))[0]
            cases = {}
            if op == 170:
                low, high = struct.unpack(">ii", r.take(8))
                if high < low or high - low > 65535:
                    raise ValueError("invalid tableswitch")
                for k in range(low, high + 1):
                    cases[k] = offset + struct.unpack(">i", r.take(4))[0]
            else:
                count = r.u4()
                if count > 65535:
                    raise ValueError("invalid lookupswitch")
                for _ in range(count):
                    k, jump = struct.unpack(">ii", r.take(8))
                    cases[k] = offset + jump
            arg = (default, cases)
        elif op == 196:
            sub = r.u1()
            arg = bytes([sub]) + r.take(4 if sub == 132 else 2)
        else:
            arg = r.take(sizes.get(op, 0))
        yield offset, op, arg


def push_int(ins, cf):
    _, op, arg = ins
    if 2 <= op <= 8:
        return op - 3
    if op in (16, 17):
        return int.from_bytes(arg, "big", signed=True)
    if op in (18, 19):
        tag, value = cf.cp[int.from_bytes(arg, "big")]
        if tag == 3:
            return value
    return None


def xor_keys(cf, method):
    """Recognize the five-way character XOR loop in static initializers.

    Require irem, the 0..3 switch, five constant branches converging on
    ixor/i2c/castore, and String.toCharArray. Return provenance and plaintext.
    Does not execute arbitrary bytecode or guess keys from English text.
    """
    keys = []
    if method["code"]:
        seq = list(instructions(method["code"]))
        at = {ins[0]: i for i, ins in enumerate(seq)}
        for i, (off, op, arg) in enumerate(seq):
            if op != 170 or i < 2 or seq[i-1][1] != 112 or push_int(seq[i-2], cf) != 5:
                continue
            default, cases = arg
            if list(cases) != [0, 1, 2, 3]:
                continue
            key, ends = [], []
            for target in list(cases.values()) + [default]:
                j = at[target]
                key.append(push_int(seq[j], cf))
                nxt = seq[j + 1]
                end = (nxt[0] + int.from_bytes(nxt[2], "big", signed=True)
                       if nxt[1] == 167 else nxt[0])
                ends.append(end)
            if None in key or len(set(ends)) != 1:
                continue
            endidx = at.get(ends[0], -1)
            if [x[1] for x in seq[endidx:endidx+3]] != [130, 146, 85]:
                continue
            keys.append((off, key))
    return keys


def xor_strings(cf):
    if not any(x == (1, "toCharArray") for x in cf.cp):
        return []
    # Some builds move the loop into z(char[]) and add a one-character
    # correction in z(String). Follow only recognized call sites.
    helpers = {}
    direct_helpers = {}
    short_xor = 0
    for m in cf.methods:
        keys = xor_keys(cf, m)
        if m["descriptor"] == "([C)Ljava/lang/String;" and len(keys) == 1:
            helpers[m["name"]] = keys[0][1]
        if m["descriptor"] == "(Ljava/lang/String;)Ljava/lang/String;" and len(keys) == 1:
            direct_helpers[m["name"]] = keys[0][1]
        if m["descriptor"] == "(Ljava/lang/String;)[C" and m["code"]:
            seq = list(instructions(m["code"]))
            for i, ins in enumerate(seq):
                if ins[1] == 130 and i and [x[1] for x in seq[i:i+3]] == [130, 146, 85]:
                    short_xor = push_int(seq[i-1], cf) or 0
    results = []
    for method in cf.methods:
        if not method["code"]:
            continue
        seq = list(instructions(method["code"]))
        at = {ins[0]: i for i, ins in enumerate(seq)}
        keys = xor_keys(cf, method)
        for i, (offset, op, arg) in enumerate(seq):
            if op not in (18, 19) or i + 1 == len(seq):
                continue
            index = int.from_bytes(arg, "big")
            value = cf.string(index)
            if value is None:
                continue
            key, correction = None, 0
            nxt = seq[i+1]
            if nxt[1] == 168:  # jsr decoder subroutine
                target = nxt[0] + int.from_bytes(nxt[2], "big", signed=True)
                j = at[target]
                if len(keys) == 1 and target < keys[0][0]:
                    key = keys[0][1]
                elif j + 2 < len(seq) and seq[j+1][1] == seq[j+2][1] == 184:
                    ref = cf.cp[int.from_bytes(seq[j+2][2], "big")][1]
                    name_type = cf.cp[ref[1]][1]
                    key = helpers.get(cf.utf(name_type[0]))
                    correction = short_xor
            elif nxt[1] == 184 and (helpers or direct_helpers):
                ref = cf.cp[int.from_bytes(nxt[2], "big")][1]
                name_type = cf.cp[ref[1]][1]
                if cf.utf(cf.cp[ref[0]][1]) != cf.name:
                    continue
                if cf.utf(name_type[1]) == "(Ljava/lang/String;)[C":
                    key = helpers.get(cf.utf(name_type[0]))
                    correction = short_xor
                elif cf.utf(name_type[1]) == "(Ljava/lang/String;)Ljava/lang/String;":
                    key = direct_helpers.get(cf.utf(name_type[0]))
            if key is None:
                continue
            if len(value) == 1:
                value = chr(ord(value) ^ correction)
            decoded = "".join(chr(ord(c) ^ key[i % 5]) for i, c in enumerate(value))
            results.append(dict(cp_index=index, method=method["name"], offset=offset,
                                xor_key=key, value=decoded))
    return results


def random_xor(data):
    """8-byte little-endian seed + XOR with java.util.Random.nextBytes()."""
    if len(data) < 8:
        return b""
    state = (int.from_bytes(data[:8], "little") ^ 0x5DEECE66D) & ((1 << 48) - 1)
    result = bytearray()
    for pos in range(8, len(data), 4):
        state = (state * 0x5DEECE66D + 11) & ((1 << 48) - 1)
        rand = (state >> 16).to_bytes(4, "little")
        result.extend(x ^ y for x, y in zip(data[pos:pos+4], rand))
    return bytes(result)


def text_properties(data):
    return (data.startswith(b"#") and b"=" in data and
            all(x in (9, 10, 13) or 32 <= x < 127 for x in data))


def idea_decrypt(data, key):
    """IDEA ECB primitive, implemented here to avoid an obsolete crypto dependency.

    Reverse the eight encryption rounds algebraically. The applets' engine
    consumes the first 16 key bytes (ASCII hex, not hex-decoded bytes).
    """
    if len(key) < 16 or len(data) % 8:
        raise ValueError("invalid IDEA key or block length")
    word = int.from_bytes(key[:16], "big")
    subkeys = []
    while len(subkeys) < 52:
        subkeys.extend((word >> shift) & 65535 for shift in range(112, -1, -16))
        word = ((word << 25) | (word >> 103)) & ((1 << 128) - 1)
    def mul(a, b):
        return (((a or 65536) * (b or 65536)) % 65537) & 65535
    def inv(a):
        return pow(a or 65536, -1, 65537) & 65535
    inverse = {i: inv(subkeys[i]) for i in list(range(0, 48, 6)) + list(range(3, 48, 6)) + [48, 51]}
    out = bytearray()
    for offset in range(0, len(data), 8):
        y1, y2, y3, y4 = struct.unpack(">4H", data[offset:offset+8])
        x1, x2, x3, x4 = (mul(y1, inverse[48]), (y3-subkeys[50]) & 65535,
                          (y2-subkeys[49]) & 65535, mul(y4, inverse[51]))
        for i in range(42, -1, -6):
            e = mul(x1 ^ x2, subkeys[i+4])
            f = mul(((x3 ^ x4) + e) & 65535, subkeys[i+5])
            g = (e + f) & 65535
            x1, x2, x3, x4 = (mul(x1 ^ f, inverse[i]), ((x3 ^ g) - subkeys[i+1]) & 65535,
                              ((x2 ^ f) - subkeys[i+2]) & 65535, mul(x4 ^ g, inverse[i+3]))
        out.extend(struct.pack(">4H", x1, x2, x3, x4))
    return bytes(out)


def binary_resources(data, key):
    """Applet container: byte-pair-swapped IDEA CBC, padding, gzip, LE records."""
    def swap_pairs(b):
        out = bytearray(b)
        out[::2], out[1::2] = b[1::2], b[::2]
        return bytes(out)
    if len(data) < 16 or len(data) % 8:
        raise ValueError("invalid container length")
    clear = swap_pairs(idea_decrypt(swap_pairs(data), key.encode("ascii")))
    # The first plaintext block is an expendable random prefix. CBC chaining
    # recovers subsequent blocks without knowing the original IV.
    clear = bytes(x ^ y for x, y in zip(clear[8:], data[:-8]))
    pad = clear[-1]
    if not 1 <= pad <= 8 or clear[-pad:-1] != bytes(pad-1):
        raise ValueError("invalid container padding")
    with gzip.GzipFile(fileobj=io.BytesIO(clear[:-pad])) as gz:
        payload = gz.read(8 * 1024 * 1024 + 1)
    if len(payload) > 8 * 1024 * 1024:
        raise ValueError("container exceeds decoded size limit")
    r, result = Reader(payload), []
    while r.pos < len(payload):
        name_size = int.from_bytes(r.take(4), "little")
        if not 0 < name_size <= 1000:
            raise ValueError("invalid resource name length")
        name = r.take(name_size).decode("utf-8")
        size = int.from_bytes(r.take(4), "little")
        if size > 500000:
            raise ValueError("invalid resource size")
        result.append((name, r.take(size)))
    return result


def certificate_info(data):
    if not shutil.which("openssl"):
        return [{"error": "openssl not installed"}]
    def run(args, value):
        p = subprocess.run(["openssl"] + args, input=value, capture_output=True, timeout=15)
        if p.returncode:
            raise ValueError(p.stderr.decode(errors="replace"))
        return p.stdout
    pem = run(["pkcs7", "-inform", "DER", "-print_certs"], data)
    result = []
    for block in re.findall(rb"-----BEGIN CERTIFICATE-----.*?-----END CERTIFICATE-----", pem, re.S):
        detail = run(["x509", "-noout", "-subject", "-issuer", "-serial", "-dates",
                      "-fingerprint", "-sha256"], block).decode()
        pub = run(["x509", "-pubkey", "-noout"], block)
        der = run(["pkey", "-pubin", "-outform", "DER"], pub)
        result.append(dict(details=detail, public_key_sha256=sha256(der)))
    return result


def inspect(path, emit):
    raw = path.read_bytes()
    summary = dict(jar=str(path), sha256=sha256(raw), size=len(raw), errors=[])
    if not zipfile.is_zipfile(path):
        summary.update(valid_zip=False, preview=raw[:200].decode("utf-8", "replace"))
        return summary
    summary.update(valid_zip=True, classes=0, decoded_strings=0, resources=[], certificates=[],
                   sources=[], manifests=[], timestamps=[], versions={})
    resource_keys = collections.defaultdict(list)
    pending_resources = []
    with zipfile.ZipFile(path) as z:
        infos = z.infolist()
        if len(infos) > 20000 or sum(i.file_size for i in infos) > 128 * 1024 * 1024:
            summary["errors"].append("archive exceeds inspection limits")
            return summary
        summary["zip_comment"] = z.comment.decode("utf-8", "replace")
        summary["timestamps"] = sorted({"%04d-%02d-%02dT%02d:%02d:%02d" % i.date_time for i in infos})
        for info in infos:
            if info.is_dir():
                continue
            n = info.filename
            try:
                data = z.read(info)
                emit(dict(kind="entry", entry=n, sha256=sha256(data), size=len(data),
                          compressed_size=info.compress_size, compression=info.compress_type))
                if data.startswith(b"\xca\xfe\xba\xbe"):
                    cf = ClassFile(data)
                    summary["classes"] += 1
                    version = f"{cf.major}.{cf.minor}"
                    summary["versions"][version] = summary["versions"].get(version, 0) + 1
                    if cf.source:
                        summary["sources"].append(dict(entry=n, source=cf.source))
                    decoded = xor_strings(cf)
                    strings = [s["value"] for s in decoded]
                    strings += [cf.string(i) for i, x in enumerate(cf.cp) if x and x[0] == 8]
                    for resource in strings:
                        if resource.endswith((".properties", "resources.bin")):
                            for key in strings:
                                if re.fullmatch(r"[0-9A-Fa-f]{32,64}", key):
                                    resource_keys[resource.rsplit("/", 1)[-1]].append((n, key))
                    decoded_indexes = {s["cp_index"] for s in decoded}
                    for s in decoded:
                        emit(dict(kind="xor_string", entry=n, **s))
                    summary["decoded_strings"] += len(decoded)
                    for i, item in enumerate(cf.cp):
                        if item and item[0] == 1:
                            emit(dict(kind="utf8", entry=n, cp_index=i, value=item[1]))
                        elif item and item[0] == 8 and i not in decoded_indexes:
                            emit(dict(kind="literal", entry=n, cp_index=i, value=cf.string(i)))
                else:
                    if n.upper().endswith("MANIFEST.MF"):
                        text = data.decode("utf-8", "replace")
                        summary["manifests"].append(dict(entry=n, text=text))
                        emit(dict(kind="manifest", entry=n, value=text))
                    if n.upper().endswith((".DSA", ".RSA", ".EC")):
                        summary["certificates"].append(dict(entry=n, certificates=certificate_info(data)))
                    if n.endswith((".properties", ".configs", ".bin")):
                        decoded = random_xor(data)
                        item = dict(entry=n, size=len(data), sha256=sha256(data))
                        if text_properties(decoded):
                            item.update(encoding="java-random-xor", text=decoded.decode("ascii"))
                            emit(dict(kind="config", entry=n, encoding=item["encoding"], value=item["text"]))
                        elif text_properties(data):
                            item.update(encoding="plaintext", text=data.decode("ascii"))
                            emit(dict(kind="config", entry=n, encoding=item["encoding"], value=item["text"]))
                        else:
                            item["encoding"] = "unresolved"
                            pending_resources.append((item, data))
                        summary["resources"].append(item)
                    for match in re.finditer(rb"[\x20-\x7e]{4,}", data):
                        emit(dict(kind="resource_ascii", entry=n, offset=match.start(),
                                  value=match.group().decode("ascii")))
            except (ValueError, IndexError, KeyError, struct.error, UnicodeError,
                    zipfile.BadZipFile, RuntimeError, subprocess.TimeoutExpired) as e:
                summary["errors"].append(f"{n}: {e}")
        for item, data in pending_resources:
            if zipfile.is_zipfile(io.BytesIO(data)):
                with zipfile.ZipFile(io.BytesIO(data)) as inner:
                    if sum(x.file_size for x in inner.infolist()) > 8 * 1024 * 1024:
                        summary["errors"].append(f"{item['entry']}: nested ZIP exceeds limits")
                        continue
                    item["encoding"] = "nested-zip"
                    item["members"] = []
                    for ni in inner.infolist():
                        if ni.is_dir():
                            continue
                        value = inner.read(ni).decode("utf-8", "replace")
                        item["members"].append(dict(name=ni.filename, text=value))
                        emit(dict(kind="config", entry=item["entry"], member=ni.filename,
                                  encoding="nested-zip", value=value))
                continue
            candidates = resource_keys[item["entry"].rsplit("/", 1)[-1]]
            for source, key in candidates:
                try:
                    members = binary_resources(data, key)
                except (ValueError, OSError, EOFError, UnicodeError, zlib.error):
                    continue
                item.update(encoding="idea-cbc-pairswap-gzip", key_source=source,
                            key_ascii_prefix=key[:16], members=[])
                for name, value in members:
                    record = dict(name=name, size=len(value), sha256=sha256(value))
                    try:
                        decoded = value.decode("utf-8")
                        if any(not c.isprintable() and c not in "\r\n\t" for c in decoded):
                            raise ValueError("binary data")
                        record["text"] = decoded
                    except (UnicodeError, ValueError):
                        record["base64"] = base64.b64encode(value).decode("ascii")
                    item["members"].append(record)
                    emit(dict(kind="config", entry=item["entry"], encoding=item["encoding"],
                              member=name, **({"value": record["text"]} if "text" in record
                                              else {"base64": record["base64"]})))
                break
    return summary


# Report exporters are embedded so this script can be moved on its own.
DATE_FIELDS = ("file", "artifact_type", "kind", "entry", "member", "raw_value",
          "local_datetime", "timezone", "utc_datetime", "interpretation")
MONTHS = {name: i for i, name in enumerate(
    "Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec".split(), 1)}
CONFIG_DATE = re.compile(
    r"\b(?:Mon|Tue|Wed|Thu|Fri|Sat|Sun) "
    r"(?P<month>[A-Z][a-z]{2}) +(?P<day>\d{1,2}) "
    r"(?P<time>\d{2}:\d{2}:\d{2}) (?P<zone>[A-Z]+) (?P<year>\d{4})\b")
CERT_DATE = re.compile(
    r"(?P<month>[A-Z][a-z]{2}) +(?P<day>\d{1,2}) "
    r"(?P<time>\d{2}:\d{2}:\d{2}) (?P<year>\d{4}) (?P<zone>GMT)")


def calendar_date(match):
    try:
        return dt.datetime(int(match["year"]), MONTHS[match["month"]], int(match["day"]),
                           *(int(n) for n in match["time"].split(":"))).isoformat()
    except (ValueError, KeyError):
        return ""


def date_rows(inventory):
    rows = []
    for artifact in inventory:
        file = artifact.get("jar", artifact.get("swf", ""))
        artifact_type = "swf" if "swf" in artifact else Path(file).suffix.lstrip(".").lower()
        if artifact.get("valid_zip") is False:
            artifact_type = "non-archive"

        def add(kind, raw, local="", zone="", utc="", entry="", member="", interpretation=""):
            rows.append(dict(file=file, artifact_type=artifact_type, kind=kind, entry=entry,
                             member=member, raw_value=raw, local_datetime=local,
                             timezone=zone, utc_datetime=utc, interpretation=interpretation))

        for part in Path(file).parts:
            if re.fullmatch(r"\d{14}", part):
                try:
                    stamp = dt.datetime.strptime(part, "%Y%m%d%H%M%S").isoformat()
                except ValueError:
                    continue
                add("wayback_capture", part, stamp, "UTC", stamp+"Z",
                    interpretation="Capture timestamp from dump path; not a build date")
                break
        for stamp in sorted(set(artifact.get("timestamps", []))):
            add("zip_entry_timestamp", stamp, stamp, "unspecified",
                interpretation="Distinct ZIP modification time; inventory does not retain member mapping")
        for resource in artifact.get("resources", []):
            for member in [resource] + resource.get("members", []):
                # Duplicate generated comments within one member describe the same date.
                seen = set()
                for line in member.get("text", "").splitlines():
                    if not line.lstrip().startswith(("#", "!")):
                        continue
                    for match in CONFIG_DATE.finditer(line):
                        raw = match.group()
                        if raw in seen:
                            continue
                        seen.add(raw)
                        add("config_date_comment", raw, calendar_date(match), match["zone"],
                            entry=resource["entry"], member=member.get("name", ""),
                            interpretation="Stored configuration comment; timezone abbreviation preserved, not resolved")
                match = re.search(r"\.old\.(\d{13})$", member.get("name", ""))
                if match:
                    stamp = (dt.datetime(1970, 1, 1, tzinfo=dt.timezone.utc) +
                             dt.timedelta(milliseconds=int(match[1])))
                    add("backup_filename_epoch_ms", match[1],
                        stamp.replace(tzinfo=None).isoformat(timespec="milliseconds"), "UTC",
                        stamp.isoformat(timespec="milliseconds").replace("+00:00", "Z"),
                        resource["entry"], member["name"],
                        "Filename suffix interpreted as Unix epoch milliseconds; not authenticated")
        for block in artifact.get("certificates", []):
            for cert in block.get("certificates", []):
                for kind, raw in re.findall(r"^(notBefore|notAfter)=(.*)$", cert.get("details", ""), re.M):
                    match = CERT_DATE.fullmatch(raw)
                    stamp = calendar_date(match) if match else ""
                    add("certificate_"+kind, raw, stamp, "UTC" if stamp else "",
                        stamp+"Z" if stamp else "", block["entry"], cert.get("public_key_sha256", ""),
                        "Certificate validity boundary; not a signing date or trust verification")
    # Keep all captures and artifacts from one site together. The site directory
    # is the parent of the capture directory, even for nested artifact paths.
    by_site = collections.defaultdict(list)
    for row in rows:
        path = Path(row["file"])
        site = next((str(p.parent) for p in path.parents
                     if re.fullmatch(r"\d{14}", p.name)), str(path.parent))
        by_site[site].append(row)

    def earliest(site):
        return (min((r["local_datetime"] for r in by_site[site]
                     if r["local_datetime"]), default="~"), site)

    return [row for site in sorted(by_site, key=earliest)
            for row in sorted(by_site[site], key=lambda r: (
                r["local_datetime"] or "~", r["file"], r["kind"],
                r["entry"], r["member"], r["raw_value"]))]


def write_dates(inventory, path):
    rows = date_rows(inventory)
    with Path(path).open("w", newline="", encoding="utf-8") as out:
        writer = csv.DictWriter(out, fieldnames=DATE_FIELDS)
        writer.writeheader()
        writer.writerows(rows)
    return len(rows)


PATH_FIELDS = ("file", "kind", "path", "hostname", "host_relation", "entry", "member",
          "source_kind", "location", "raw_value")
TEXT_KINDS = {"literal", "xor_string", "config", "constant_pool", "push_literal", "resource_ascii"}
PATTERNS = [
    ("url", re.compile(r"(?<![\w])(?:https?|ftp|file)://[^\s<>\"'\\]+", re.I)),
    ("schemeless_url", re.compile(r"(?<![\w.])www\.[a-zA-Z0-9.-]+/[^\s<>\"'\\]+")),
    ("filesystem_candidate", re.compile(r"(?<![\w])(?:[A-Za-z]:[\\/]|\\\\[\w.-]+\\)[^\s<>\"']+")),
    ("filesystem_candidate", re.compile(r"(?<![\w/])/(?:home|Users|tmp|var|opt|usr|etc|srv)/[^\s<>\"']+")),
    ("endpoint_path", re.compile(r"(?<![\w.:])/?(?:servlet|cgi-bin)/[\w./%?&=+#~:-]+")),
    ("resource_candidate", re.compile(
        r"(?<![\w./])/?(?:[\w.-]+/)*[\w.-]+\.(?:css|png|jpe?g|gif|ico|html?|jsp|php|cgi|pl|"
        r"properties|configs|bin|rc|xml|txt|log|zip|jar)\b(?:\?[^\s<>\"']*)?", re.I)),
]


def references(text, key=""):
    # Java properties quote URL punctuation; preserve the original separately.
    value = text.replace(r"\:", ":").replace(r"\/", "/")
    if not all(c.isprintable() or c in "\r\n\t" for c in value):
        return
    occupied = []
    for kind, pattern in PATTERNS:
        for match in pattern.finditer(value):
            if any(match.start() < end and match.end() > start for start, end in occupied):
                continue
            candidate = match.group().rstrip(").,;")
            if kind == "resource_candidate" and candidate in ("Request.Zip", "Response.Zip"):
                continue  # Known compression-option keys, not filenames.
            if len(candidate) > 4096:
                continue
            occupied.append(match.span())
            yield kind, candidate
    # Preserve other explicitly named configuration paths, even without a suffix.
    if not occupied and re.search(r"(?i)(?:path|url|location)$", key) and re.fullmatch(r"/?[\w./%-]+", value.strip()):
        if "/" in value:
            yield "configured_path", value.strip()


def path_rows(records, swf_configs=()):
    rows, seen = [], set()

    def add(record, kind, value, raw, member=""):
        file = record.get("jar", record.get("swf", ""))
        host, relation = "", "relative_or_unspecified"
        if kind in ("url", "schemeless_url", "html_gateway"):
            try:
                host = urlsplit(("//" if kind == "schemeless_url" else "") + value).hostname or ""
            except ValueError:
                return
            if host:
                site = next((p.parent.name for p in Path(file).parents if re.fullmatch(r"\d{14}", p.name)), "")
                normalize = lambda s: re.sub(r"^www\.", "", s.lower())
                relation = ("same_site" if normalize(host) == normalize(site) else
                            "external_reference" if site else "unknown_site")
        if kind == "filesystem_candidate" or value.startswith("file://"):
            relation = "local_filesystem_candidate"
        entry = record.get("entry", record.get("block", ""))
        # Collapse UTF-8/literal duplicates and repeated uses in the same member.
        identity = (file, entry, member, kind, value)
        if identity in seen:
            return
        seen.add(identity)
        location = ";".join(f"{k}={record[k]}" for k in ("offset", "cp_index") if k in record)
        rows.append(dict(file=file, kind=kind, path=value, hostname=host, host_relation=relation,
                         entry=entry, member=member, source_kind=record.get("kind", ""),
                         location=location, raw_value=raw))

    for record in records:
        text = record.get("value")
        if record.get("kind") not in TEXT_KINDS or not isinstance(text, str):
            continue
        multiline_config = record["kind"] == "config" and ("\n" in text or "\r" in text)
        for line in text.splitlines():
            if line.lstrip().startswith(("#", "!")) and multiline_config:
                continue
            key, value = record.get("member", ""), line
            if multiline_config:
                match = re.match(r"\s*([^:=\s]+)\s*[=:]\s*(.*)", line)
                if match:
                    key, value = match.groups()
            for kind, candidate in references(value, key):
                # For properties files keep both the enclosing member and key.
                member = record.get("member", "")
                if key and key != member:
                    member = (member + "::" if member else "") + key
                add(record, kind, candidate, line, member)
    for config in swf_configs:
        for context in config.get("html_context", []):
            for value in context["gateway_urls"]:
                add(dict(swf=config["swf"], entry=context["html"], kind="html_flashvars"),
                    "html_gateway", value, value, "gatewayUrl")
    return sorted(rows, key=lambda r: (r["file"], r["kind"], r["path"], r["entry"], r["member"]))


def write_paths(strings_path, output_path, swf_configs=()):
    with Path(strings_path).open(encoding="utf-8") as source:
        rows = path_rows((json.loads(line) for line in source), swf_configs)
    with Path(output_path).open("w", newline="", encoding="utf-8") as out:
        writer = csv.DictWriter(out, fieldnames=PATH_FIELDS)
        writer.writeheader()
        writer.writerows(rows)
    return len(rows)


CERTIFICATE_FIELDS = ("file", "archive_sha256", "signature_entry", "certificate_index",
          "subject", "issuer", "serial", "not_before", "not_after",
          "not_before_utc", "not_after_utc", "certificate_sha256",
          "public_key_sha256", "verification", "error")


def utc_date(value):
    match = CERT_DATE.fullmatch(value)
    stamp = calendar_date(match) if match else ""
    return stamp + "Z" if stamp else ""


def certificate_rows(inventory):
    rows = []
    for artifact in inventory:
        for block in artifact.get("certificates", []):
            for index, cert in enumerate(block.get("certificates", []), 1):
                details = {}
                for line in cert.get("details", "").splitlines():
                    key, separator, value = line.partition("=")
                    if separator:
                        details[key.casefold()] = value
                before, after = details.get("notbefore", ""), details.get("notafter", "")
                rows.append(dict(
                    file=artifact["jar"], archive_sha256=artifact.get("sha256", ""),
                    signature_entry=block["entry"], certificate_index=str(index),
                    subject=details.get("subject", ""), issuer=details.get("issuer", ""),
                    serial=details.get("serial", ""), not_before=before, not_after=after,
                    not_before_utc=utc_date(before), not_after_utc=utc_date(after),
                    certificate_sha256=details.get("sha256 fingerprint", "").replace(":", "").lower(),
                    public_key_sha256=cert.get("public_key_sha256", ""),
                    verification="not_performed", error=cert.get("error", "")))
    return sorted(rows, key=lambda r: (r["file"], r["signature_entry"], int(r["certificate_index"])))


def write_certificates(inventory, path):
    rows = certificate_rows(inventory)
    with Path(path).open("w", newline="", encoding="utf-8") as out:
        writer = csv.DictWriter(out, fieldnames=CERTIFICATE_FIELDS)
        writer.writeheader()
        writer.writerows(rows)
    return len(rows)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("directory", nargs="?", type=Path,
                        default=Path("../../cia-2010-websites-dump/"),
                        help="input directory (default: ../../cia-2010-websites-dump/, relative to cwd)")
    parser.add_argument("--self-test", action="store_true", help="run embedded tests against the input directory")
    args = parser.parse_args()
    directory = args.directory.resolve()
    if not directory.is_dir():
        parser.error(f"input directory does not exist or is not a directory: {args.directory}")
    if args.self_test:
        global ROOT
        ROOT = directory
        suite = unittest.TestSuite(unittest.defaultTestLoader.loadTestsFromTestCase(c)
                                   for c in (DecoderTests, DateTests, PathTests, CertificateTests,))
        return 0 if unittest.TextTestRunner(verbosity=2).run(suite).wasSuccessful() else 1
    output = Path.cwd() / "jar"
    output.mkdir(exist_ok=True)
    paths = [p for p in directory.rglob("*") if p.is_file() and p.suffix.lower() in ('.jar', '.zip')]
    summaries = []
    with (output / "strings.jsonl").open("w") as out, (output / "configs.jsonl").open("w") as configs:
        for p in sorted(paths):
            def emit(record):
                line = json.dumps(dict(jar=str(p), **record), ensure_ascii=True) + "\n"
                out.write(line)
                if record["kind"] == "config":
                    configs.write(line)
            try:
                summaries.append(inspect(p, emit))
            except (OSError, ValueError, zipfile.BadZipFile) as e:
                summaries.append(dict(jar=str(p), errors=[str(e)]))
    (output / "inventory.json").write_text(json.dumps(summaries, indent=2) + "\n")
    write_dates(summaries, output / "dates.csv")
    write_certificates(summaries, output / "certificates.csv")
    write_paths(output / "strings.jsonl", output / "internal-paths.csv")
    resources = [r for j in summaries for r in j.get("resources", [])]
    result = dict(files=len(summaries), valid_zip=sum(x.get("valid_zip", False) for x in summaries),
                  classes=sum(x.get("classes", 0) for x in summaries),
                  decoded_strings=sum(x.get("decoded_strings", 0) for x in summaries),
                  resources=len(resources), unresolved_resources=sum(r["encoding"] == "unresolved" for r in resources),
                  families=dict(collections.Counter(r["entry"].rsplit("/", 1)[-1] for r in resources)),
                  errors=sum(len(x["errors"]) for x in summaries))
    (output / "summary.json").write_text(json.dumps(result, indent=2) + "\n")
    print(json.dumps(result, indent=2))
    return 1 if result["errors"] else 0


# Embedded regression checks; run only with --self-test.
class DecoderTests(unittest.TestCase):
    def test_idea_known_answer(self):
        self.assertEqual(
            idea_decrypt(bytes.fromhex("11fbed2b01986de5"),
                         bytes.fromhex("00010002000300040005000600070008")),
            bytes.fromhex("0000000100020003"))

    def test_java_random_known_answer(self):
        # Independently generated with new java.util.Random(12345).nextBytes().
        self.assertEqual(random_xor((12345).to_bytes(8, "little") + bytes(17)).hex(),
                         "d6209f5c31b3618322a9d8ee7807c8ea8e")

    def test_modified_utf8(self):
        self.assertEqual(mutf8(b"A\xc0\x80B"), "A\x00B")
        self.assertEqual(mutf8(bytes.fromhex("eda0bdedb880")), "\ud83d\ude00")

    def test_obfuscated_string_variants(self):
        cases = [
            ("aeronet-news.com/20110202122601/aeronet.jar", "a/a/b/d.class", "applet.configs"),
            ("activegaminginfo.com/20110202130304/gaming.jar", "a/b/f/b.class", "Client.properties"),
            ("information-junky.com/20110201160020/infoweather.jar", "Server.class", "Connection"),
        ]
        for jar, entry, expected in cases:
            with self.subTest(jar=jar), zipfile.ZipFile(ROOT / jar) as z:
                decoded = xor_strings(ClassFile(z.read(entry)))
                self.assertIn(expected, [s["value"] for s in decoded])

    def test_random_config(self):
        with zipfile.ZipFile(ROOT / "aeronet-news.com/20110202122601/aeronet.jar") as z:
            clear = random_xor(z.read("a/a/b/applet.configs"))
        self.assertIn(b"#Thu Aug 19 08:19:00 AMT 2010\r\n", clear)
        self.assertIn(b"Servlet.Url=/servlet/News\r\n", clear)

    def test_idea_container_and_unicode(self):
        with zipfile.ZipFile(ROOT / "iraniangoalkicks.com/20110202091917/clamping.jar") as z:
            data = z.read("resource/resources.bin")
        fields = dict(binary_resources(data, "0C5C45F2EC3C2A63E87CBE6537C38159CABD803DE5129FB6AF5664AEE13EF69C"))
        self.assertEqual(len(fields), 49)
        self.assertEqual(fields["ServerURL"], b"http://www.iraniangoalkicks.com/cgi-bin/keeper.cgi")
        self.assertEqual(fields["SendLabel"].decode(), "ارسال")
        self.assertTrue(fields["res.Stop.gif"].startswith(b"GIF89a"))
        self.assertNotIn(fields["ServerURL"], data)
        with self.assertRaises((ValueError, OSError)):
            binary_resources(data, "0000000000000000")

    def test_other_container_families(self):
        cases = [
            ("activegaminginfo.com/20110202130304/gaming.jar", "a/b/f/Client.properties",
             "60A514D96C19F93C", "/servlet/gamesServlet"),
            ("bcenews.com/20110202210331/plan.jar", "a/a/e/Applet.properties",
             "7A5DAEC4029C91ED", "/servlet/inform"),
        ]
        for jar, entry, key, expected in cases:
            with self.subTest(jar=jar), zipfile.ZipFile(ROOT / jar) as z:
                fields = dict(binary_resources(z.read(entry), key))
                self.assertEqual(fields["Connection.Servlet.Path"].decode(), expected)

    def test_nested_zip(self):
        records = []
        result = inspect(ROOT / "differentviewtoday.com/20110202185659/bwm.jar", records.append)
        self.assertEqual(result["errors"], [])
        configs = [r for r in records if r["kind"] == "config"]
        self.assertEqual(len(configs), 1)
        self.assertEqual(configs[0]["encoding"], "nested-zip")
        self.assertIn("http://www.differentviewtoday.com/cgi-bin/architect.cgi", configs[0]["value"])

    def test_html_not_misclassified_as_jar(self):
        result = inspect(ROOT / "globalcitizennews.net/20130929144711/news.jar", lambda _: None)
        self.assertFalse(result["valid_zip"])


class DateTests(unittest.TestCase):
    def test_all_jar_sources(self):
        sample = dict(jar="example.net/20110202122601/app.jar", valid_zip=True,
                      timestamps=["2010-08-19T08:19:00"] * 2,
                      resources=[dict(entry="resources.bin", members=[dict(
                          name="Config.properties.old.1229457768184",
                          text="#Generated: Thu Aug 19 08:19:00 AMT 2010\n"
                               "#Thu Aug 19 08:19:00 AMT 2010\n"
                               "value=Thu Aug 19 08:19:00 AMT 2010\n")])],
                      certificates=[dict(entry="META-INF/KEY.DSA", certificates=[dict(
                          public_key_sha256="key-hash",
                          details="notBefore=Aug 14 20:14:24 2007 GMT\n"
                                  "notAfter=Nov 12 20:14:24 2007 GMT\n")])])
        rows = date_rows([sample])
        self.assertEqual(len(rows), 6)
        kinds = {r["kind"]: r for r in rows}
        comment = kinds["config_date_comment"]
        self.assertEqual(comment["timezone"], "AMT")
        self.assertEqual(comment["local_datetime"], "2010-08-19T08:19:00")
        self.assertEqual(comment["utc_datetime"], "")
        self.assertEqual(comment["entry"], "resources.bin")
        self.assertEqual(kinds["wayback_capture"]["utc_datetime"], "2011-02-02T12:26:01Z")
        self.assertEqual(kinds["backup_filename_epoch_ms"]["utc_datetime"], "2008-12-16T20:02:48.184Z")
        self.assertEqual(kinds["certificate_notBefore"]["utc_datetime"], "2007-08-14T20:14:24Z")
        self.assertEqual(kinds["zip_entry_timestamp"]["utc_datetime"], "")

    def test_swf_and_html_capture_dates(self):
        rows = date_rows([dict(swf="site/20101222095511/movie.swf"),
                          dict(jar="site/20130929144711/news.jar", valid_zip=False),
                          dict(swf="site/99999999999999/invalid.swf")])
        self.assertEqual(len(rows), 2)
        self.assertEqual([r["artifact_type"] for r in rows], ["swf", "non-archive"])
        self.assertTrue(all(r["kind"] == "wayback_capture" for r in rows))

    def test_csv_round_trip_and_empty_inventory(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / "dates.csv"
            inventory = [dict(swf='a,"b/20101222095511/movie.swf')]
            self.assertEqual(write_dates(inventory, path), 1)
            with path.open(newline="") as f:
                self.assertEqual(list(csv.DictReader(f)), date_rows(inventory))
            self.assertEqual(write_dates([], path), 0)
            self.assertTrue(path.read_text().startswith("file,artifact_type,kind,"))


class PathTests(unittest.TestCase):
    def test_escaped_url_is_not_a_drive_or_relative_path(self):
        self.assertEqual(list(references(r"http\://www.example.net/cgi-bin/send.cgi")),
                         [("url", "http://www.example.net/cgi-bin/send.cgi")])
        self.assertEqual(list(references("/servlet/home/example")),
                         [("endpoint_path", "/servlet/home/example")])

    def test_filesystems_resources_and_noise(self):
        self.assertEqual(list(references(r"C:\build\client\Main.java"))[0][0], "filesystem_candidate")
        self.assertEqual(list(references("/home/alice/project"))[0][0], "filesystem_candidate")
        self.assertEqual(list(references("/slide.jpg")), [("resource_candidate", "/slide.jpg")])
        self.assertEqual(list(references("Request.Zip")), [])
        self.assertEqual(list(references("bad\x01C:\\accidental")), [])
        self.assertEqual(list(references("java/lang/String")), [])

    def test_properties_provenance_and_host_relationship(self):
        rows = path_rows([dict(jar="example.net/20100101000000/app.jar", kind="config",
                               entry="resources.bin", member="Config.properties.old.1",
                               value="#See http://example.org/comment\n"
                                     "ServerURL=http\\://www.example.net/cgi-bin/send.cgi\n"
                                     "OtherURL=http://different.net/path\n"
                                     "Servlet.Path=/unusual/endpoint\n")])
        by_path = {r["path"]: r for r in rows}
        self.assertEqual(len(rows), 3)
        own = by_path["http://www.example.net/cgi-bin/send.cgi"]
        self.assertEqual(own["host_relation"], "same_site")
        self.assertEqual(own["member"], "Config.properties.old.1::ServerURL")
        self.assertIn(r"http\:", own["raw_value"])
        self.assertEqual(by_path["http://different.net/path"]["host_relation"], "external_reference")
        self.assertEqual(by_path["/unusual/endpoint"]["kind"], "configured_path")

    def test_duplicates_and_html_gateways(self):
        record = dict(swf="example.net/20100101000000/a.swf", block="root", kind="constant_pool",
                      offset=50, value="servlet/layouts")
        configs = [dict(swf=record["swf"], html_context=[dict(html="example.net/20091231000000/iframe.html",
                                                          gateway_urls=["servlet/gateway"])])]
        rows = path_rows([record, dict(record, offset=90)], configs)
        self.assertEqual(len(rows), 2)
        self.assertEqual(rows[0]["location"], "offset=50")
        self.assertEqual(rows[1]["kind"], "html_gateway")
        self.assertTrue(rows[1]["entry"].endswith("iframe.html"))

    def test_csv_round_trip(self):
        with tempfile.TemporaryDirectory() as directory:
            source, output = Path(directory)/"strings.jsonl", Path(directory)/"internal-paths.csv"
            record = dict(jar="site/20100101000000/a.jar", kind="literal", entry="Some.class",
                          value='load "file.css", then continue')
            source.write_text(json.dumps(record)+"\n")
            self.assertEqual(write_paths(source, output), 1)
            with output.open(newline="") as f:
                self.assertEqual(list(csv.DictReader(f)), path_rows([record]))


class CertificateTests(unittest.TestCase):
    def fixture(self):
        details = ('subject=C=US, O="Example, Inc.", CN=Example\n'
                   'issuer=C=US, O=Issuer\nserial=00AB\n'
                   'notBefore=Aug 14 20:14:24 2007 GMT\n'
                   'notAfter=Nov 12 20:14:24 2007 GMT\n'
                   'sha256 Fingerprint=' + ':'.join(['AB']*32) + '\n')
        return [dict(jar="site/20080101000000/app.jar", sha256="archive-hash", certificates=[
            dict(entry="META-INF/KEY.DSA", certificates=[dict(details=details, public_key_sha256="key-hash"),
                                                       dict(details=details, public_key_sha256="other-key")])])]

    def test_chain_and_fields(self):
        rows = certificate_rows(self.fixture())
        self.assertEqual([r['certificate_index'] for r in rows], ['1', '2'])
        self.assertEqual(rows[0]['signature_entry'], 'META-INF/KEY.DSA')
        self.assertEqual(rows[0]['subject'], 'C=US, O="Example, Inc.", CN=Example')
        self.assertEqual(rows[0]['serial'], '00AB')
        self.assertEqual(rows[0]['not_before_utc'], '2007-08-14T20:14:24Z')
        self.assertEqual(rows[0]['certificate_sha256'], 'ab'*32)
        self.assertEqual(rows[1]['public_key_sha256'], 'other-key')
        self.assertEqual(rows[0]['verification'], 'not_performed')

    def test_error_and_unsigned_archive(self):
        inventory = [dict(jar="unsigned.jar"), dict(jar="signed.jar", certificates=[
            dict(entry="META-INF/KEY.RSA", certificates=[dict(error="openssl not installed")])])]
        rows = certificate_rows(inventory)
        self.assertEqual(len(rows), 1)
        self.assertEqual(rows[0]['error'], 'openssl not installed')
        self.assertEqual(rows[0]['certificate_sha256'], '')

    def test_csv_round_trip(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory)/"certificates.csv"
            self.assertEqual(write_certificates(self.fixture(), path), 2)
            with path.open(newline="") as f:
                self.assertEqual(list(csv.DictReader(f)), certificate_rows(self.fixture()))
            self.assertEqual(write_certificates([], path), 0)
            self.assertTrue(path.read_text().startswith("file,archive_sha256,"))


if __name__ == "__main__":
    raise SystemExit(main())
