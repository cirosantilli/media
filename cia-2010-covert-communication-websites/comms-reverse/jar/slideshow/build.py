#!/usr/bin/env python3
"""Build either display applet against its archived protocol bytecode."""

import argparse
import hashlib
import re
import shutil
import struct
import subprocess
import tempfile
import zipfile
from dataclasses import dataclass
from pathlib import Path

PROJECT = Path(__file__).resolve().parent
DUMP = PROJECT.parents[4] / 'cia-2010-websites-dump'


@dataclass(frozen=True)
class SiteBuild:
    capture: str
    applet_class: str
    wire_namespace: str
    sha256: str


SITES = {
    'mynepalnews.com': SiteBuild(
        '20110204143500/nepali.jar', 'irf.gnh.aw.vycw', 'irf/gnh/qec/',
        'd4de26de8d03ed9bad213d75057a3c8df5c802719ac3374058f525f569822568',
    ),
    'inews-today.com': SiteBuild(
        '20110201140115/headlines.jar', 'ftkj.xzb.uujr.rvcp', 'ftkj/xzb/bvw/',
        '69f76966399590f06163dfb2c9334c3c345c815d526b32845f226926793bfaa1',
    ),
}
RUNTIME_ROOTS = {
    'a/a/g/a/d/b', 'a/a/i/a/a', 'a/a/i/a/b', 'c/a/a/b', 'c/a/d/a/h',
    'c/a/d/c/f', 'c/a/d/d/c/c', 'c/a/d/d/e/d', 'c/a/d/d/g', 'c/a/d/e/a',
    'c/a/d/e/b', 'c/a/d/g', 'c/a/d/k', 'c/a/d/l', 'c/a/d/o', 'c/a/e/d',
    'c/a/g/b', 'c/b/i/m', 'c/b/i/r', 'c/b/m/c', 'c/b/m/f', 'c/b/n/a/a',
    'c/b/o/a/h', 'c/b/e/b/d',
}
CONFIG = 'c/a/e/Applet.properties'


def constant_strings(data):
    """Read UTF-8 class names and descriptors from a JVM constant pool."""
    count = struct.unpack_from('>H', data, 8)[0]
    widths = {3: 4, 4: 4, 5: 8, 6: 8, 7: 2, 8: 2, 9: 4, 10: 4, 11: 4,
              12: 4, 15: 3, 16: 2, 17: 4, 18: 4, 19: 2, 20: 2}
    offset, index = 10, 1
    while index < count:
        tag = data[offset]
        offset += 1
        if tag == 1:
            size = struct.unpack_from('>H', data, offset)[0]
            offset += 2
            yield data[offset:offset + size]
            offset += size
        else:
            offset += widths[tag]
        index += 2 if tag in (5, 6) else 1


def dependencies(classes, roots):
    """Keep the serialized class descriptors and their static dependencies."""
    remaining = set(roots)
    selected = set()
    while remaining:
        name = remaining.pop()
        selected.add(name)
        for value in constant_strings(classes[name]):
            for match in re.findall(rb'[\w$]+(?:[/.][\w$]+)+', value):
                target = match.decode('ascii').replace('.', '/')
                candidates = (target, target[1:]) if target.startswith('L') else (target,)
                remaining.update(ref for ref in candidates if ref in classes and ref not in selected)
    return selected


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('domain', choices=SITES)
    parser.add_argument('--dump', type=Path, default=DUMP)
    parser.add_argument('--output', type=Path)
    args = parser.parse_args()
    site = SITES[args.domain]
    javac = shutil.which('javac')
    if not javac:
        parser.error('javac is required')
    archive_path = args.dump / args.domain / site.capture
    if hashlib.sha256(archive_path.read_bytes()).hexdigest() != site.sha256:
        parser.error(f'Unexpected archive digest for {archive_path}')
    output = args.output or PROJECT / 'build' / args.domain / archive_path.name
    sources = sorted((PROJECT / 'src').rglob('*.java'))
    with tempfile.TemporaryDirectory(prefix='display-build-') as directory, zipfile.ZipFile(archive_path) as archive:
        work = Path(directory)
        # Preserve the applet class named by the archived HTML. Both entry
        # points delegate to the same implementation and load Applet.properties.
        package, _, name = site.applet_class.rpartition('.')
        entry = work / (name + '.java')
        entry.write_text(
            f'package {package};\n'
            f'public final class {name} extends replay.display.SourceApplet {{}}\n',
            encoding='utf-8',
        )
        compiled = work / 'classes'
        compiled.mkdir()
        subprocess.run([javac, '-encoding', 'UTF-8', '-source', '8', '-target', '8',
                        '-d', str(compiled), *map(str, sources), str(entry)], check=True)
        classes = {name.removesuffix('.class'): archive.read(name)
                   for name in archive.namelist() if name.endswith('.class')}
        # These per-domain classes can be resolved by name during deserialization.
        roots = RUNTIME_ROOTS | {name for name in classes if name.startswith(site.wire_namespace)}
        selected = dependencies(classes, roots)
        output.parent.mkdir(parents=True, exist_ok=True)
        with zipfile.ZipFile(output, 'w', compression=zipfile.ZIP_DEFLATED) as jar:
            jar.writestr('META-INF/MANIFEST.MF', 'Manifest-Version: 1.0\r\n\r\n')
            jar.writestr(CONFIG, archive.read(CONFIG))
            for name in sorted(selected):
                jar.writestr(name + '.class', classes[name])
            for path in sorted(compiled.rglob('*.class')):
                jar.write(path, path.relative_to(compiled).as_posix())
    print(f'{output} ({len(selected)} archived runtime classes)')


if __name__ == '__main__':
    main()
