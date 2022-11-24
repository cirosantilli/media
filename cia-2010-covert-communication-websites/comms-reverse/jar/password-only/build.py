#!/usr/bin/env python3
"""Compile the Java 8 applet from the sources in this directory."""

import argparse
from pathlib import Path
import subprocess
import tempfile
import zipfile


def main():
    root = Path(__file__).resolve().parent
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('output', type=Path, nargs='?', default=root / 'build/europeannewsflash.com/users.jar')
    args = parser.parse_args()
    output = args.output.resolve()
    output.parent.mkdir(parents=True, exist_ok=True)
    sources = sorted((root / 'src').rglob('*.java'))
    with tempfile.TemporaryDirectory(prefix='.europeannewsflash-build-', dir=output.parent) as temporary:
        classes = Path(temporary) / 'classes'
        classes.mkdir()
        subprocess.run(['javac', '-encoding', 'UTF-8', '-source', '8', '-target', '8',
                        '-d', classes, *sources], check=True)
        jar_file = Path(temporary) / 'users.jar'
        with zipfile.ZipFile(jar_file, 'w', compression=zipfile.ZIP_DEFLATED) as archive:
            archive.writestr('META-INF/MANIFEST.MF', 'Manifest-Version: 1.0\r\n\r\n')
            for compiled in sorted(classes.rglob('*.class')):
                archive.write(compiled, compiled.relative_to(classes).as_posix())
        jar_file.replace(output)
    print(output)


if __name__ == '__main__':
    main()
