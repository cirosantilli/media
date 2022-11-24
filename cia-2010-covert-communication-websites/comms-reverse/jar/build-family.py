#!/usr/bin/env python3
"""Build a representative archived applet from its recovered Java sources."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import subprocess
import sys
import tempfile
import zipfile

ROOT = Path(__file__).resolve().parent
CAPTURES = json.loads((ROOT / 'captures.json').read_text())


def build(args):
    family = ROOT / args.family
    record = CAPTURES[args.family]
    capture = args.dump / record['capture']
    if hashlib.sha256(capture.read_bytes()).hexdigest() != record['sha256']:
        raise ValueError(f'Unexpected capture hash for {capture}')
    out = args.output or family / 'build' / capture.name
    out.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(dir=out.parent) as temporary:
        tmp = Path(temporary)
        compiled = tmp / 'compiled'
        compiled.mkdir()
        api = tmp / 'api'
        api.mkdir()
        if args.family in ('js-content-transport', 'content-transport'):
            sys.path.insert(0, str(ROOT.parents[1]))
            from reconstruction import build_native_browser_bridge
            build_native_browser_bridge(api / 'browser', args.javac)
        apis = sorted((family / 'compile-api').rglob('*.java'))
        if apis:
            subprocess.run([args.javac, '-d', api, *apis], check=True)
        subprocess.run([args.javac, '-encoding', 'UTF-8', '-source', '8', '-target', '8',
                        '-classpath', os.pathsep.join(map(str, [api, api / 'browser'])),
                        '-d', compiled, *sorted((family / 'src').rglob('*.java'))], check=True)
        restored = tmp / 'restored'
        subprocess.run([args.java, '-cp', args.asm, ROOT / 'RestoreNames.java',
                        family / 'names.tsv', compiled, capture, restored], check=True)
        with zipfile.ZipFile(capture) as original, zipfile.ZipFile(
                tmp / 'build.jar', 'w', compression=zipfile.ZIP_DEFLATED) as result:
            for item in original.infolist():
                if item.filename.endswith('.class'):
                    result.writestr(item, (restored / item.filename).read_bytes())
                else:
                    result.writestr(item, original.read(item))
        (tmp / 'build.jar').replace(out)
    print(out)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('family', choices=sorted(CAPTURES))
    parser.add_argument('--dump', type=Path, default=ROOT.parents[3] / 'cia-2010-websites-dump')
    parser.add_argument('--output', type=Path)
    parser.add_argument('--javac', default=os.environ.get('JAVAC8', 'javac'), help='Java 8 javac')
    parser.add_argument('--java', default=os.environ.get('JAVA17', '/usr/lib/jvm/default-java/bin/java'),
                        help='Java 17 or newer for the class-name remapper')
    parser.add_argument('--asm', default='/usr/share/java/asm-all.jar', help='ASM 9 all-in-one JAR')
    build(parser.parse_args())
