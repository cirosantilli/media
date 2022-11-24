#!/usr/bin/env python3
"""Assemble the World Dispatch AVM1 sources and package the archived movie assets."""
import argparse
import hashlib
import json
from pathlib import Path
import struct
import sys
import zlib

ROOT = Path(__file__).resolve().parent
sys.path.insert(0, str(ROOT.parents[1]))
import swf

CAPTURE = 'worlddispatch.net/20101231111653/worlddispatch.swf'
SHA256 = '22ababfeaf1a949e8dce365982aa66adae34eabc578a572c8afa23002b70318a'
OPCODES = {name: code for code, name in swf.OPCODES.items()}


def word(n):
    return struct.pack('<H', n)


def string(s):
    return s.encode('utf-8') + b'\0'


def push_value(kind, value):
    if kind == 0:
        raw = string(value)
    elif kind in (2, 3):
        raw = b''
    elif kind == 6:
        double = struct.pack('<d', float(value))
        raw = double[4:] + double[:4]
    else:
        raw = struct.pack({1: '<f', 4: 'B', 5: 'B', 7: '<I', 8: 'B', 9: '<H'}[kind], value)
    return bytes([kind]) + raw


def assemble(path):
    lines = []
    for line in path.read_text().splitlines():
        code = line.partition('\t// ')[0]
        if not code.strip() or code.lstrip().startswith('//'):
            continue
        depth = len(code) - len(code.lstrip(' '))
        label, name, *arg = code.split(maxsplit=2)
        lines.append((depth, label, name, json.loads(arg[0]) if arg else None))
    output = bytearray()
    labels, branches = {}, []
    cursor = 0

    def block(depth):
        nonlocal cursor
        while cursor < len(lines) and lines[cursor][0] >= depth:
            indent, label, name, arg = lines[cursor]
            if indent != depth or label in labels:
                raise ValueError(f'{path}: invalid indentation or repeated label at {label}')
            cursor += 1
            labels[label] = len(output)
            op = OPCODES[name]
            output.append(op)
            if op < 128:
                continue
            payload = bytearray()
            nested = name in ('With', 'DefineFunction', 'DefineFunction2')
            if name == 'Push':
                payload.extend(b''.join(push_value(*v) for v in arg))
            elif name == 'ConstantPool':
                payload.extend(word(len(arg)) + b''.join(map(string, arg)))
            elif name in ('If', 'Jump'):
                branches.append((len(output) + 2, arg))
                payload.extend(word(0))
            elif name == 'StoreRegister':
                payload.append(arg)
            elif name == 'GetURL2':
                payload.extend(bytes.fromhex(arg))
            elif nested:
                if name != 'With':
                    params = arg['parameters']
                    payload.extend(string(arg['function_name']) + word(len(params)))
                    if name == 'DefineFunction2':
                        payload.append(arg['register_count'])
                        payload.extend(word(arg['flags']))
                    for param in params:
                        if name == 'DefineFunction2':
                            payload.append(param['register'])
                        payload.extend(string(param['name']))
                payload.extend(word(0))
            else:
                raise ValueError(f'{path}: unsupported instruction {name}')
            output.extend(word(len(payload)) + payload)
            if nested:
                start = len(output)
                block(depth + 2)
                output[start-2:start] = word(len(output) - start)
    block(0)
    for pos, target in branches:
        output[pos:pos+2] = struct.pack('<h', labels[target] - pos - 2)
    return output


def rebuild(capture):
    if hashlib.sha256(capture.read_bytes()).hexdigest() != SHA256:
        raise ValueError(f'Expected the World Dispatch capture from {CAPTURE}')
    inventory, data, blocks, _ = swf.inspect(capture)
    _, start, _ = swf.unpack_swf(capture.read_bytes())
    by_offset = {b['offset']: b['name'] for b in blocks}
    sources = set((ROOT / 'src').glob('*.avm1'))
    tags = inventory['tags']

    def rebuild_scope(scope):
        result = bytearray()
        for tag in tags:
            if tag['scope'] != scope:
                continue
            offset = tag['payload_offset']
            payload = data[offset:offset + tag['size']]
            kind = tag['code']
            if kind in (12, 59):
                name = by_offset[tag['offset']].removeprefix('__Packages.').replace('/', '-').replace(':', '-')
                source = ROOT / 'src' / (name + '.avm1')
                sources.remove(source)
                payload = (payload[:2] if kind == 59 else b'') + assemble(source)
            elif kind == 39:
                payload = payload[:4] + rebuild_scope(f"{scope}/sprite:{tag['sprite_id']}")
            if offset - tag['offset'] == 6 or len(payload) >= 63:
                result.extend(word((kind << 6) | 63) + struct.pack('<I', len(payload)))
            else:
                result.extend(word((kind << 6) | len(payload)))
            result.extend(payload)
        return result

    body = data[8:start] + rebuild_scope('root')
    if sources:
        raise ValueError(f'Unused AVM1 files: {sources}')
    return b'CWS' + data[3:4] + struct.pack('<I', len(body) + 8) + zlib.compress(body)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--dump', type=Path, default=ROOT.parents[3] / 'cia-2010-websites-dump')
    parser.add_argument('--output', type=Path, default=ROOT / 'build/worlddispatch.swf')
    args = parser.parse_args()
    movie = rebuild(args.dump / CAPTURE)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_bytes(movie)
    print(args.output)
