#!/usr/bin/env python3
"""Static SWF/AVM1 inspection for the archived websites; no Flash execution.

Python standard library only; this file is self-contained. Input defaults to
../../cia-2010-websites-dump/ relative to the working directory. Reports are
written to swf/ inside the current working directory. Use --self-test to
run the embedded regression checks.
"""

import argparse
import datetime as dt
import sys
import tempfile
import unittest
import collections
import csv
import hashlib
import json
import math
import re
import struct
import zlib
from pathlib import Path
from html.parser import HTMLParser
from urllib.parse import parse_qsl, urlsplit, unquote


LIMIT = 64 * 1024 * 1024
CORPUS_SHAPE = "81213a59d2eb327de6a95f986314e491ebb847042de50b4818c2ac6204ffc76f"
OPCODES = {
    0: "End", 4: "NextFrame", 5: "PreviousFrame", 6: "Play", 7: "Stop",
    8: "ToggleQuality", 9: "StopSounds", 10: "Add", 11: "Subtract",
    12: "Multiply", 13: "Divide", 14: "Equals", 15: "Less", 16: "And",
    17: "Or", 18: "Not", 19: "StringEquals", 20: "StringLength",
    21: "StringExtract", 23: "Pop", 24: "ToInteger", 28: "GetVariable",
    29: "SetVariable", 32: "SetTarget2", 33: "StringAdd", 34: "GetProperty",
    35: "SetProperty", 36: "CloneSprite", 37: "RemoveSprite", 38: "Trace",
    39: "StartDrag", 40: "EndDrag", 41: "StringLess", 42: "Throw",
    43: "CastOp", 44: "ImplementsOp", 45: "FSCommand2", 48: "RandomNumber",
    49: "MBStringLength", 50: "CharToAscii", 51: "AsciiToChar", 52: "GetTime",
    53: "MBStringExtract", 54: "MBCharToAscii", 55: "MBAsciiToChar",
    58: "Delete", 59: "Delete2", 60: "DefineLocal", 61: "CallFunction",
    62: "Return", 63: "Modulo", 64: "NewObject", 65: "DefineLocal2",
    66: "InitArray", 67: "InitObject", 68: "TypeOf", 69: "TargetPath",
    70: "Enumerate", 71: "Add2", 72: "Less2", 73: "Equals2", 74: "ToNumber",
    75: "ToString", 76: "PushDuplicate", 77: "StackSwap", 78: "GetMember",
    79: "SetMember", 80: "Increment", 81: "Decrement", 82: "CallMethod",
    83: "NewMethod", 84: "InstanceOf", 85: "Enumerate2", 96: "BitAnd",
    97: "BitOr", 98: "BitXor", 99: "BitLShift", 100: "BitRShift",
    101: "BitURShift", 102: "StrictEquals", 103: "Greater", 104: "StringGreater",
    105: "Extends", 129: "GotoFrame", 131: "GetURL", 135: "StoreRegister",
    136: "ConstantPool", 138: "WaitForFrame", 139: "SetTarget",
    140: "GotoLabel", 141: "WaitForFrame2", 142: "DefineFunction2", 143: "Try",
    148: "With", 150: "Push", 153: "Jump", 154: "GetURL2",
    155: "DefineFunction", 157: "If", 158: "Call", 159: "GotoFrame2",
}
TAGS = {0: "End", 1: "ShowFrame", 2: "DefineShape", 6: "DefineBits",
        8: "JPEGTables", 9: "SetBackgroundColor", 12: "DoAction",
        26: "PlaceObject2",
        39: "DefineSprite", 56: "ExportAssets", 59: "DoInitAction",
        69: "FileAttributes", 77: "Metadata", 82: "DoABC", 87: "DefineBinaryData"}


def sha(data):
    return hashlib.sha256(data).hexdigest()


class Reader:
    def __init__(self, data, base=0):
        self.data, self.pos, self.base = data, 0, base

    def take(self, n):
        if n < 0 or self.pos + n > len(self.data):
            raise ValueError(f"truncated data at {self.base + self.pos}")
        b = self.data[self.pos:self.pos+n]
        self.pos += n
        return b

    def u1(self):
        return self.take(1)[0]

    def u2(self):
        return int.from_bytes(self.take(2), "little")

    def u4(self):
        return int.from_bytes(self.take(4), "little")

    def string(self):
        end = self.data.find(b"\0", self.pos)
        if end < 0:
            raise ValueError(f"unterminated string at {self.base + self.pos}")
        return self.take(end-self.pos+1)[:-1].decode("utf-8", "surrogateescape")


def unpack_swf(raw):
    if len(raw) < 9 or raw[:3] not in (b"FWS", b"CWS", b"ZWS"):
        raise ValueError("not a SWF")
    declared = int.from_bytes(raw[4:8], "little")
    if not 9 <= declared <= LIMIT:
        raise ValueError("invalid or excessive declared length")
    if raw[:3] == b"ZWS":
        raise ValueError("LZMA SWF not supported; no ZWS files in the corpus")
    if raw[:3] == b"CWS":
        inflater = zlib.decompressobj()
        body = inflater.decompress(raw[8:], LIMIT + 1)
        if len(body) > LIMIT or not inflater.eof:
            raise ValueError("truncated or excessive compressed stream")
        trailing = inflater.unused_data
        data = b"FWS" + raw[3:8] + body
    else:
        data, trailing = raw[:declared], raw[declared:]
    if len(data) != declared:
        raise ValueError(f"length mismatch: {len(data)} != {declared}")
    bits = "".join(f"{b:08b}" for b in data[8:25])
    width = int(bits[:5], 2)
    coords = []
    for i in range(4):
        v = int(bits[5+i*width:5+(i+1)*width], 2) if width else 0
        coords.append(v - (1 << width) if width and v & (1 << (width-1)) else v)
    pos = 8 + (5 + width*4 + 7)//8
    r = Reader(data[pos:], pos)
    rate, frames = r.u2()/256, r.u2()
    meta = dict(compression=raw[:3].decode(), version=raw[3], declared_size=declared,
                frame_rect_twips=coords, width=(coords[1]-coords[0])/20,
                height=(coords[3]-coords[2])/20, frame_rate=rate, frame_count=frames,
                trailing_bytes=len(trailing), trailing_sha256=sha(trailing) if trailing else None)
    return data, pos + r.pos, meta


def parse_tags(data, base, scope="root", depth=0):
    if depth > 32:
        raise ValueError("excessive sprite nesting")
    r = Reader(data, base)
    result = []
    while r.pos < len(data):
        offset = base + r.pos
        header = r.u2()
        code, length = header >> 6, header & 63
        if length == 63:
            length = r.u4()
        start = base + r.pos
        payload = r.take(length)
        tag = dict(code=code, name=TAGS.get(code, f"Tag{code}"), offset=offset,
                   payload_offset=start, size=length, scope=scope, sha256=sha(payload), data=payload)
        result.append(tag)
        if code == 39:
            sr = Reader(payload, start)
            sprite_id, frames = sr.u2(), sr.u2()
            tag.update(sprite_id=sprite_id, frame_count=frames)
            result.extend(parse_tags(payload[4:], start+4, f"{scope}/sprite:{sprite_id}", depth+1))
        if code == 0:
            if any(r.take(len(data)-r.pos)):
                raise ValueError("nonzero bytes after End tag")
            break
    return result


def parse_actions(data, base=0, pool=None, depth=0):
    """Parse AVM1, including bodies stored AFTER function action headers.

    Constant references are resolved against the lexically encountered pool.
    This matches these files; arbitrary control-flow-dependent pools are not
    evaluated. All offsets refer to the decompressed SWF.
    """
    if depth > 64:
        raise ValueError("excessive action nesting")
    pool = [] if pool is None else list(pool)
    r, actions = Reader(data, base), []
    while r.pos < len(data):
        offset = base + r.pos
        opcode = r.u1()
        length = r.u2() if opcode >= 128 else 0
        pr = Reader(r.take(length), base + r.pos - length)
        action = dict(offset=offset, opcode=opcode, op=OPCODES.get(opcode, f"Op{opcode:02x}"))
        if opcode == 136:
            pool = []
            for _ in range(pr.u2()):
                string_offset = pr.base + pr.pos
                pool.append(dict(offset=string_offset, value=pr.string()))
            action["pool"] = list(pool)
        elif opcode == 150:
            values = []
            while pr.pos < len(pr.data):
                start, kind = pr.base + pr.pos, pr.u1()
                value = dict(type=kind, offset=start)
                if kind == 0:
                    value["value"] = pr.string()
                elif kind == 1:
                    value["value"] = struct.unpack("<f", pr.take(4))[0]
                elif kind == 2:
                    value["value"] = None
                elif kind == 3:
                    value["value"] = "undefined"
                elif kind in (4, 5):
                    value["value"] = pr.u1()
                elif kind == 6:
                    b = pr.take(8)
                    value["value"] = struct.unpack("<d", b[4:] + b[:4])[0]
                elif kind == 7:
                    value["value"] = pr.u4()
                elif kind in (8, 9):
                    index = pr.u1() if kind == 8 else pr.u2()
                    if index >= len(pool):
                        raise ValueError(f"invalid constant reference {index} at {start}")
                    value.update(index=index, value=pool[index]["value"], pool_offset=pool[index]["offset"])
                else:
                    raise ValueError(f"unknown Push type {kind} at {start}")
                if isinstance(value.get("value"), float) and not math.isfinite(value["value"]):
                    value["value"] = str(value["value"])
                values.append(value)
            action["values"] = values
        elif opcode in (142, 155):
            action["function_name"] = pr.string()
            count = pr.u2()
            if opcode == 142:
                action.update(register_count=pr.u1(), flags=pr.u2())
            params = []
            for _ in range(count):
                params.append(dict(register=pr.u1() if opcode == 142 else 0, name=pr.string()))
            action["parameters"] = params
            size = pr.u2()
            start = base + r.pos
            action["body"] = parse_actions(r.take(size), start, pool, depth+1)
        elif opcode == 148:
            size = pr.u2()
            start = base + r.pos
            action["body"] = parse_actions(r.take(size), start, pool, depth+1)
        elif opcode == 143:
            flags = pr.u1()
            sizes = [pr.u2(), pr.u2(), pr.u2()]
            action.update(flags=flags, catch=pr.u1() if flags & 4 else pr.string())
            for name, size in zip(("try_body", "catch_body", "finally_body"), sizes):
                start = base + r.pos
                action[name] = parse_actions(r.take(size), start, pool, depth+1)
        elif opcode in (153, 157):
            action["target"] = base + r.pos + int.from_bytes(pr.take(2), "little", signed=True)
        elif opcode == 135:
            action["register"] = pr.u1()
        elif opcode == 131:
            action.update(url=pr.string(), target=pr.string())
        elif opcode in (139, 140):
            action["value"] = pr.string()
        elif pr.data:
            action["raw_operands"] = pr.take(len(pr.data)).hex()
        if pr.pos != len(pr.data):
            raise ValueError(f"unparsed operands at {offset}")
        actions.append(action)
        if opcode == 0:
            if any(r.take(len(data)-r.pos)):
                raise ValueError(f"nonzero bytes after End action at {offset}")
            break
    return actions


def walk(actions):
    for action in actions:
        yield action
        for name in ("body", "try_body", "catch_body", "finally_body"):
            yield from walk(action.get(name, []))


def disassemble(actions, out, depth=0):
    for a in actions:
        detail = {k: v for k, v in a.items() if k not in ("offset", "opcode", "op", "body", "pool", "try_body", "catch_body", "finally_body")}
        if "pool" in a:
            detail["pool"] = [s["value"] for s in a["pool"]]
        if "values" in a:
            detail["values"] = [(v["type"], v["value"]) for v in a["values"]]
        out.write(f"{'  '*depth}{a['offset']:06x} {a['op']} {json.dumps(detail, ensure_ascii=True)}\n")
        for name in ("body", "try_body", "catch_body", "finally_body"):
            if name in a:
                disassemble(a[name], out, depth+1)


def action_shape(actions):
    """Fingerprint instruction structure, NOT semantics: erase string values.

    Keep numbers, registers, flags, nesting and branch targets
    (as instruction indices). A match alone does not establish equivalent code.
    """
    offsets = {a["offset"]: i for i, a in enumerate(walk(actions))}

    def normalize(seq):
        result = []
        for a in seq:
            item = {"opcode": a["opcode"]}
            for key in ("register", "register_count", "flags", "raw_operands"):
                if key in a:
                    item[key] = a[key]
            if "pool" in a:
                item["pool_count"] = len(a["pool"])
            if "values" in a:
                item["values"] = [(v["type"], "<string>" if v["type"] in (0, 8, 9) else v["value"])
                                  for v in a["values"]]
            if a["opcode"] in (153, 157):
                item["target"] = offsets.get(a["target"], "outside-block")
            if "parameters" in a:
                item["parameters"] = [p["register"] for p in a["parameters"]]
            if "catch" in a:
                item["catch"] = a["catch"] if isinstance(a["catch"], int) else "<string>"
            for key in ("body", "try_body", "catch_body", "finally_body"):
                if key in a:
                    item[key] = normalize(a[key])
            result.append(item)
        return result

    return sha(json.dumps(normalize(actions), sort_keys=True).encode())


def decode_request_template(value):
    """Decode the three template sections identified by manual AVM1 analysis.

    Positive numbers request freshly generated random text of that length.
    These are not recovered header values or passwords.
    """
    parts = value.split("::")
    if len(parts) != 3:
        raise ValueError("expected three ::-separated template sections")

    def item(v):
        try:
            n = float(v)
        except ValueError:
            n = 0
        return {"random_length": n} if n > 0 and math.isfinite(n) else {"literal": v}

    def pairs(part):
        tokens = part.split(":") if part else []
        if len(tokens) % 2:
            raise ValueError("unpaired request template field")
        return [dict(name=tokens[i], **item(tokens[i+1])) for i in range(0, len(tokens), 2)]

    return dict(url_suffix=[item(v) for v in parts[0].split(":") if v],
                amf_headers=pairs(parts[1]), request_fields=pairs(parts[2]))


class FlashHTML(HTMLParser):
    def __init__(self):
        super().__init__()
        self.movies, self.flashvars = set(), []

    def handle_starttag(self, tag, attrs):
        a = dict(attrs)
        name = (a.get("name") or "").lower()
        if tag == "param" and name == "movie" and a.get("value"):
            self.movies.add(a["value"])
        if tag in ("embed", "object"):
            for k in ("src", "data"):
                if a.get(k):
                    self.movies.add(a[k])
        if tag == "param" and name == "flashvars" and a.get("value"):
            self.flashvars.append(a["value"])
        if a.get("flashvars"):
            self.flashvars.append(a["flashvars"])


def site_directory(path):
    # This dump stores domain / Wayback timestamp / original resource path.
    return next((p.parent for p in path.parents if re.fullmatch(r"\d{14}", p.name)), None)


def html_context(path):
    site = site_directory(path)
    result = []
    if site is None:
        return result
    for page in sorted(site.rglob("*")):
        if not page.is_file() or page.suffix.lower() not in (".html", ".htm", ".php"):
            continue
        if page.stat().st_size > 2 * 1024 * 1024:
            continue
        parser = FlashHTML()
        parser.feed(page.read_text(errors="replace"))
        if not any(Path(unquote(urlsplit(m).path)).name == path.name for m in parser.movies):
            continue
        urls = sorted({v for f in parser.flashvars for k, v in parse_qsl(f) if k == "gatewayUrl"})
        if urls:
            result.append(dict(html=str(page), gateway_urls=urls))
    return result


def configuration(path, strings, blocks, shape):
    root = [s for s in strings if s["block"] == "root"]
    templates = []
    for s in root:
        if s["value"].count("::") == 2:
            entry = dict(value=s["value"], offset=s["offset"])
            try:
                entry["decoded"] = decode_request_template(s["value"])
            except ValueError as e:
                entry["decode_error"] = str(e)
            templates.append(entry)
    context = html_context(path)
    site = site_directory(path)
    endpoints = [dict(value=s["value"], offset=s["offset"])
                 for s in root if s["value"].startswith("servlet/")]
    resources = []
    targets = {s["value"] for s in endpoints} | {u for h in context for u in h["gateway_urls"]}
    if site:
        for p in sorted(site.rglob("*")):
            if p.is_file():
                rel = p.relative_to(site).parts
                if len(rel) >= 2 and re.fullmatch(r"\d{14}", rel[0]):
                    resource = "/".join(rel[1:])
                    if resource in targets or resource.removesuffix("/index.html") in targets:
                        entry = dict(file=str(p), size=p.stat().st_size, sha256=sha(p.read_bytes()))
                        raw = p.read_bytes()
                        if len(raw) <= 4096 and raw.isascii() and b"=" in raw:
                            entry["text"] = raw.decode("ascii")
                            entry["form_fields"] = parse_qsl(entry["text"], keep_blank_values=True)
                        resources.append(entry)
    protocol = None
    if shape == CORPUS_SHAPE:
        # These slots were traced through the constructor, Service call and
        # LoadVars callback. Only apply the interpretation to the known shape.
        root_pool = blocks[1]["actions"][0]["pool"]
        service_pool = blocks[5]["actions"][0]["pool"]
        service, method = root_pool[32], service_pool[30]
        protocol = dict(service=service, method=method,
                        assembled_amf_target=service["value"] + "." + method["value"],
                        layout_mode_field=root_pool[36], cycle_delay_ms_field=root_pool[39],
                        basis="manually traced slots in recognized corpus instruction shape")
    return dict(swf=str(path), site=site.name if site else None, embedded_endpoints=endpoints,
                stylesheets=[dict(value=s["value"], offset=s["offset"])
                             for s in root if s["value"].endswith(".css")],
                request_templates=templates, html_context=context, archived_endpoints=resources,
                recognized_family_protocol=protocol)


def inspect(path):
    raw = path.read_bytes()
    data, pos, meta = unpack_swf(raw)
    tags = parse_tags(data[pos:], pos)
    exports = {}
    for t in tags:
        if t["code"] == 56:
            r = Reader(t["data"], t["payload_offset"])
            for _ in range(r.u2()):
                char_id = r.u2()
                exports[char_id] = r.string()
    blocks, strings = [], []
    for t in tags:
        if t["code"] not in (12, 59):
            continue
        skip = 2 if t["code"] == 59 else 0
        sprite = int.from_bytes(t["data"][:2], "little") if skip else None
        name = exports.get(sprite, t["scope"])
        actions = parse_actions(t["data"][skip:], t["payload_offset"]+skip)
        blocks.append(dict(name=name, offset=t["offset"], actions=actions,
                           shape_sha256=action_shape(actions)))
        for a in walk(actions):
            for i, s in enumerate(a.get("pool", [])):
                strings.append(dict(kind="constant_pool", block=name, pool_index=i, **s))
            for v in a.get("values", []):
                if v["type"] == 0:
                    strings.append(dict(kind="push_literal", block=name, offset=v["offset"], value=v["value"]))
    inventory = dict(swf=str(path), sha256=sha(raw), size=len(raw), unpacked_sha256=sha(data), **meta,
                     tag_counts=dict(collections.Counter(t["name"] for t in tags)),
                     exports=exports, blocks=len(blocks), strings=len(strings),
                     actions=sum(1 for b in blocks for _ in walk(b["actions"])),
                     action_counts=dict(collections.Counter(a["op"] for b in blocks for a in walk(b["actions"]))),
                     shape_sha256=sha("\n".join(b["shape_sha256"] for b in blocks).encode()),
                     block_shapes=[dict(name=b["name"], sha256=b["shape_sha256"]) for b in blocks],
                     tags=[{k: v for k, v in t.items() if k != "data"} for t in tags],
                     errors=[], warnings=[])
    # Do not silently present partial coverage as a complete script extraction.
    for t in tags:
        if t["code"] in (7, 34, 70, 72, 82) or (t["code"] == 26 and t["data"] and t["data"][0] & 128):
            inventory["warnings"].append(f"potential unparsed action container: tag {t['code']} at {t['offset']}")
    if any(a["op"].startswith("Op") for b in blocks for a in walk(b["actions"])):
        inventory["warnings"].append("unknown action opcodes preserved without interpretation")
    return inventory, data, blocks, strings


# Report exporters are embedded so this script can be moved on its own.
DATE_FIELDS = ("file", "artifact_type", "kind", "entry", "member", "raw_value",
          "local_datetime", "timezone", "utc_datetime")
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


def date_rows(inventory, root=None):
    rows = []
    for artifact in inventory:
        file = artifact.get("jar", artifact.get("swf", ""))
        if root is not None:
            file = str(Path(file).relative_to(root))
        artifact_type = "swf" if "swf" in artifact else Path(file).suffix.lstrip(".").lower()
        if artifact.get("valid_zip") is False:
            artifact_type = "non-archive"

        def add(kind, raw, local="", zone="", utc="", entry="", member=""):
            rows.append(dict(file=file, artifact_type=artifact_type, kind=kind, entry=entry,
                             member=member, raw_value=raw, local_datetime=local,
                             timezone=zone, utc_datetime=utc))

        for part in Path(file).parts:
            if re.fullmatch(r"\d{14}", part):
                try:
                    stamp = dt.datetime.strptime(part, "%Y%m%d%H%M%S").isoformat()
                except ValueError:
                    continue
                add("wayback_capture", part, stamp, "UTC", stamp+"Z")
                break
        for stamp in sorted(set(artifact.get("timestamps", []))):
            add("zip_entry_timestamp", stamp, stamp, "unspecified")
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
                            entry=resource["entry"], member=member.get("name", ""))
                match = re.search(r"\.old\.(\d{13})$", member.get("name", ""))
                if match:
                    stamp = (dt.datetime(1970, 1, 1, tzinfo=dt.timezone.utc) +
                             dt.timedelta(milliseconds=int(match[1])))
                    add("backup_filename_epoch_ms", match[1],
                        stamp.replace(tzinfo=None).isoformat(timespec="milliseconds"), "UTC",
                        stamp.isoformat(timespec="milliseconds").replace("+00:00", "Z"),
                        resource["entry"], member["name"])
        for block in artifact.get("certificates", []):
            for cert in block.get("certificates", []):
                for kind, raw in re.findall(r"^(notBefore|notAfter)=(.*)$", cert.get("details", ""), re.M):
                    match = CERT_DATE.fullmatch(raw)
                    stamp = calendar_date(match) if match else ""
                    add("certificate_"+kind, raw, stamp, "UTC" if stamp else "",
                        stamp+"Z" if stamp else "", block["entry"], cert.get("public_key_sha256", ""))
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


def write_dates(inventory, path, root=None):
    rows = date_rows(inventory, root)
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
                                   for c in (SWFTests, DateTests, PathTests,))
        return 0 if unittest.TextTestRunner(verbosity=2).run(suite).wasSuccessful() else 1
    output = Path.cwd() / "swf"
    output.mkdir(exist_ok=True)
    paths = [p for p in directory.rglob("*") if p.is_file() and p.suffix.lower() in ('.swf',)]
    inventory, configs = [], []
    with (output / "strings.jsonl").open("w") as strings_out:
        for path in sorted(paths):
            try:
                item, data, blocks, strings = inspect(path)
                configs.append(configuration(path, strings, blocks, item["shape_sha256"]))
                prefix = sha(str(path).encode())[:12] + "-" + path.stem
                (output / (prefix + ".unpacked")).write_bytes(data)
                (output / (prefix + ".actions.json")).write_text(json.dumps(blocks, indent=2) + "\n")
                with (output / (prefix + ".disasm.txt")).open("w") as out:
                    for b in blocks:
                        out.write(f"\nBLOCK {b['name']} @ {b['offset']:06x}\n")
                        disassemble(b["actions"], out)
                item["output_prefix"] = prefix
                for s in strings:
                    strings_out.write(json.dumps(dict(swf=str(path), **s)) + "\n")
                inventory.append(item)
            except (OSError, ValueError, zlib.error, struct.error) as e:
                inventory.append(dict(swf=str(path), errors=[str(e)]))
    (output / "inventory.json").write_text(json.dumps(inventory, indent=2) + "\n")
    write_dates(inventory, output / "dates.csv", directory)
    write_paths(output / "strings.jsonl", output / "internal-paths.csv", configs)
    (output / "configs.json").write_text(json.dumps(configs, indent=2) + "\n")
    with (output / "configs.csv").open("w", newline="") as out:
        writer = csv.writer(out)
        writer.writerow(["site", "swf", "gateway_urls_from_html", "embedded_endpoints", "stylesheets", "request_template", "assembled_amf_target"])
        for c in configs:
            writer.writerow([c["site"], c["swf"],
                             ";".join(sorted({u for h in c["html_context"] for u in h["gateway_urls"]})),
                             ";".join(s["value"] for s in c["embedded_endpoints"]),
                             ";".join(s["value"] for s in c["stylesheets"]),
                             ";".join(s["value"] for s in c["request_templates"]),
                             (c["recognized_family_protocol"] or {}).get("assembled_amf_target", "")])
    summary = dict(files=len(inventory), valid=sum("version" in i for i in inventory),
                   strings=sum(i.get("strings", 0) for i in inventory),
                   actions=sum(i.get("actions", 0) for i in inventory),
                   errors=sum(len(i["errors"]) for i in inventory),
                   warnings=sum(len(i.get("warnings", [])) for i in inventory),
                   structure_groups=dict(collections.Counter(i["shape_sha256"] for i in inventory if "shape_sha256" in i)))
    (output / "summary.json").write_text(json.dumps(summary, indent=2) + "\n")
    print(json.dumps(summary, indent=2))
    return 1 if summary["errors"] else 0


# Embedded regression checks; run only with --self-test.
swf = sys.modules[__name__]

def action(code, payload=b""):
    return bytes([code]) + (struct.pack("<H", len(payload)) if code >= 128 else b"") + payload


def pool(*values):
    return action(136, struct.pack("<H", len(values)) + b"".join(v.encode() + b"\0" for v in values))


def tag(code, data=b""):
    return struct.pack("<H", (code << 6) | 63) + struct.pack("<I", len(data)) + data


def movie(tags=b"\0\0", compressed=True):
    # RECT: one signed bit per coordinate, all zero; 12fps; one frame.
    body = b"\x08\x00" + struct.pack("<HH", 12 * 256, 1) + tags
    return (b"CWS" if compressed else b"FWS") + b"\x07" + struct.pack("<I", len(body)+8) + (
        zlib.compress(body) if compressed else body)


class SWFTests(unittest.TestCase):
    def test_compression_and_headers(self):
        c, pos, meta = swf.unpack_swf(movie())
        f, _, _ = swf.unpack_swf(movie(compressed=False))
        self.assertEqual(c, f)
        self.assertEqual(pos, 14)
        self.assertEqual(meta["frame_rate"], 12)
        self.assertEqual(meta["frame_rect_twips"], [0, 0, 0, 0])
        self.assertEqual(swf.unpack_swf(movie()+b"extra")[2]["trailing_bytes"], 5)

    def test_malformed_headers(self):
        bad = [b"HTML", movie()[:-2], movie()[:4]+struct.pack("<I", 100)+movie()[8:],
               movie()[:4]+struct.pack("<I", swf.LIMIT+1)+movie()[8:],
               b"ZWS"+movie()[3:]]
        for data in bad:
            with self.subTest(data=data[:8]), self.assertRaises(ValueError):
                swf.unpack_swf(data)

    def test_nested_tags_and_bounds(self):
        data = tag(39, struct.pack("<HH", 7, 1)+tag(12, b"\0")+tag(0))+tag(0)
        tags = swf.parse_tags(data, 100)
        self.assertEqual([t["code"] for t in tags], [39, 12, 0, 0])
        self.assertEqual(tags[1]["scope"], "root/sprite:7")
        self.assertEqual(tags[1]["payload_offset"], 116)
        with self.assertRaises(ValueError):
            swf.parse_tags(tag(12, b"abc")[:-1], 0)

    def test_function_body_and_pool_inheritance(self):
        body = action(150, b"\x09\x01\x00") + b"\x3e"
        header = b"fn\0" + struct.pack("<HBH", 1, 3, 41) + b"\x02arg\0" + struct.pack("<H", len(body))
        parsed = swf.parse_actions(pool("a", "文") + action(142, header) + body + b"\0", 500)
        fn = parsed[1]
        self.assertEqual(fn["parameters"], [{"register": 2, "name": "arg"}])
        self.assertEqual(fn["body"][0]["values"][0]["value"], "文")
        self.assertEqual(fn["body"][0]["values"][0]["pool_offset"], 507)
        self.assertEqual(parsed[-1]["op"], "End")

    def test_push_types_and_swf_double_word_order(self):
        d = struct.pack("<d", -123.5)
        payload = b"\x00x\0\x01"+struct.pack("<f", 1.5)+b"\x02\x03\x04\x07\x05\x01\x06"+d[4:]+d[:4]
        payload += b"\x07"+struct.pack("<I", 0xffffffff)
        values = swf.parse_actions(action(150, payload))[0]["values"]
        self.assertEqual([v["value"] for v in values], ["x", 1.5, None, "undefined", 7, 1, -123.5, 0xffffffff])

    def test_invalid_actions(self):
        for data in [action(150, b"\x08\x00"), action(150, b"\xff"),
                     pool("abc")[:-1], action(150, b"\0no terminator"), b"\0\x07"]:
            with self.subTest(data=data), self.assertRaises(ValueError):
                swf.parse_actions(data)

    def test_branch_targets(self):
        parsed = swf.parse_actions(action(153, struct.pack("<h", -5)), 123)
        self.assertEqual(parsed[0]["target"], 123)

    def test_fingerprints_ignore_string_contents(self):
        a = swf.parse_actions(pool("first")+action(150, b"\x08\x00")+b"\x07\0")
        b = swf.parse_actions(pool("different length")+action(150, b"\x08\x00")+b"\x07\0", 200)
        c = swf.parse_actions(pool("first")+action(150, b"\x08\x00")+b"\x06\0")
        self.assertEqual(swf.action_shape(a), swf.action_shape(b))
        self.assertNotEqual(swf.action_shape(a), swf.action_shape(c))

    def test_request_template(self):
        value = swf.decode_request_template("::epipe:22:refresh:cons::chkr:49:client_id:24")
        self.assertEqual(value["url_suffix"], [])
        self.assertEqual(value["amf_headers"], [{"name": "epipe", "random_length": 22},
                                               {"name": "refresh", "literal": "cons"}])
        self.assertEqual(value["request_fields"][1], {"name": "client_id", "random_length": 24})
        with self.assertRaises(ValueError):
            swf.decode_request_template("::unpaired::")

    def test_html(self):
        parser = swf.FlashHTML()
        parser.feed('<PARAM NAME="movie" VALUE="a.swf"/><embed src="a.swf" '
                    'flashvars="gatewayUrl=servlet%2Fnews&amp;another=1"/>')
        self.assertEqual(parser.movies, {"a.swf"})
        self.assertEqual(parser.flashvars, ["gatewayUrl=servlet%2Fnews&another=1"])

    def test_archived_starwars_and_worlddispatch(self):
        cases = [("starwarsweb.net/20101230033642/starwarsweb.swf", "s.a", "a", "e"),
                 ("worlddispatch.net/20101231111653/worlddispatch.swf", "s.f", "c", "f")]
        for name, target, mode, delay in cases:
            with self.subTest(name=name):
                path = ROOT / name
                inv, data, blocks, strings = swf.inspect(path)
                self.assertEqual((inv["actions"], inv["strings"], inv["blocks"]), (12871, 1481, 49))
                self.assertEqual(inv["shape_sha256"], swf.CORPUS_SHAPE)
                self.assertEqual(inv["warnings"], [])
                conf = swf.configuration(path, strings, blocks, inv["shape_sha256"])
                proto = conf["recognized_family_protocol"]
                self.assertEqual(proto["assembled_amf_target"], target)
                self.assertEqual(proto["layout_mode_field"]["value"], mode)
                self.assertEqual(proto["cycle_delay_ms_field"]["value"], delay)
                self.assertTrue(conf["html_context"])
                self.assertEqual(data[:3], b"FWS")


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


if __name__ == "__main__":
    raise SystemExit(main())
