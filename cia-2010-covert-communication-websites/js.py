#!/usr/bin/env python3
"""Classify archived JavaScript clients by static fingerprints.

Reads .js files from the supplied paths, or from the main archive dump by
default. Writes inventory.json, summary.json, and endpoints.csv under ./js.
"""

import argparse
from collections import Counter
import hashlib
import json
from pathlib import Path
import re
from urllib.parse import urljoin, urlsplit

from jar import archive_location, parse_endpoint, write_endpoints


DEFAULT_DUMP = Path(__file__).resolve().parents[2] / 'cia-2010-websites-dump'
HTML_RESPONSE = re.compile(
    rb'\A(?:\s|<!--[\s\S]*?-->)*(?:<!doctype\s+html|<(?:html|head|body|title|meta|script)\b)',
    re.IGNORECASE,
)
MESSAGING = {
    'login_entry_point': re.compile(r'\bfunction\s+func[01]\s*\('),
    'password_input': re.compile(r"""\btype\s*=\s*['"]password\b""", re.IGNORECASE),
    'login_submit': re.compile(r'\bonsubmit\s*=.*?\bfunc1\s*\(', re.IGNORECASE),
    'display_div_render': re.compile(r'\bgetElementById\s*\(\s*displayDiv\s*\)\s*\.\s*innerHTML\s*='),
    'http_client': re.compile(r'\bXMLHttpRequest\b'),
}
CONTENT_TRANSPORT = {
    'http_client': re.compile(r'\bXMLHttpRequest\b'),
    'poll_timer': re.compile(r'\bsetInterval\s*\('),
    'api_compatibility': re.compile(r'\bMSXML2\.(?:XMLHTTP|DOMDocument)\b'),
    'html_renderer': re.compile(r'\bgetElementById\s*\([^)]*\)\s*\.\s*innerHTML\s*='),
    'click_through': re.compile(r'\bwindow\.open\s*\('),
}
RESPONSE_PARSERS = {
    'xml': {
        'xml_response_check': re.compile(r'\bif\s*\(\s*!\s*\w+\.responseXML\s*\)'),
        'xml_item_parser': re.compile(r'\b(?:tagName|nodeName|documentElement)\b'),
        'xml_item_value': re.compile(r'\bnodeValue\b'),
    },
    'delimited-text': {
        'text_response_check': re.compile(r'\bif\s*\(\s*!\s*\w+\.responseText\s*\)'),
        'record_separator': re.compile(r"""\.split\(\s*['"];['"]\s*\)"""),
        'field_separator': re.compile(r"""\.split\(\s*['"]&['"]\s*\)"""),
        'value_separator': re.compile(r"""\.indexOf\(\s*['"]=['"]\s*\)"""),
    },
}
XML_FIELDS = re.compile(
    r"""\.(?:tagName|nodeName)\s*={2,3}\s*['"]([\w:-]+)['"]"""
)


PASSWORD_ENDPOINT = re.compile(r"""\b(ta|_url)\s*=\s*(['"])([^'"\r\n]+)\2""")
CGI_LITERAL = re.compile(r"""(['"])((?:https?://[^/'"\s]+)?/[^'"\s<>]+\.cgi(?:\?[^'"\s<>]*)?)\1""", re.I)
SCRIPT = re.compile(r"<script\b([^>]*)>(.*?)</script\s*>", re.I | re.S)
SCRIPT_SRC = re.compile(r"""\bsrc\s*=\s*(?:"([^"]*)"|'([^']*)'|([^\s>]+))""", re.I)
POLLING_SETUP = re.compile(
    r"""\{\s*(\w+)\s*:\s*['"]([^'"\s<>]*?/[^'"\s<>]+)['"]\s*,\s*(\w+)\s*:\s*['"]([\w-]+)['"]"""
)


def password_endpoints(text, source, site):
    found = []
    for setting, _, value in PASSWORD_ENDPOINT.findall(text):
        if endpoint := parse_endpoint(value, site):
            found.append(dict(endpoint, source=str(source), setting=setting))
    if not found:
        # Some variants rename the URL setting. Record their CGI literal as
        # evidence without treating other absolute literals as server URLs.
        for _, value in CGI_LITERAL.findall(text):
            if endpoint := parse_endpoint(value, site):
                found.append(dict(endpoint, source=str(source), setting='cgi URL literal'))
    return found


def archive_url_path(path, root, capture):
    return '/' + path.relative_to(capture or root).as_posix().partition('?')[0]


def polling_pages(root, site):
    """Index URLs from inline initialization objects on captured pages."""
    pages = []
    for page in sorted(root.rglob('*')):
        if page.name.partition('?')[0].lower().endswith(('.html', '.htm', '.shtml')) and page.is_file():
            try:
                tags = SCRIPT.findall(page.read_text(errors='replace'))
            except OSError:
                continue  # Keep the script's classification if its HTML context is unavailable.
            script_paths = set()
            candidates = []
            _, capture, _ = archive_location(page)
            page_url = 'http://' + (site or 'archive.invalid') + archive_url_path(page, root, capture)
            for attrs, inline in tags:
                if match := SCRIPT_SRC.search(attrs):
                    value = next(value for value in match.groups() if value is not None)
                    try:
                        url = urlsplit(urljoin(page_url, value))
                    except ValueError:
                        continue
                    if url.hostname and url.hostname.removeprefix('www.') == (site or 'archive.invalid').removeprefix('www.'):
                        script_paths.add(url.path)
                else:
                    candidates.extend(POLLING_SETUP.findall(inline))
            if script_paths and candidates:
                pages.append((page, script_paths, candidates))
    return pages


def polling_endpoints(path, text, cache):
    root, capture, site = archive_location(path)
    if root not in cache:
        cache[root] = polling_pages(root, site)
    script_path = archive_url_path(path, root, capture)
    found = []
    seen = set()
    for page, script_paths, candidates in cache[root]:
        if script_path not in script_paths:
            continue
        for url_key, value, div_key, _ in candidates:
            if value in seen or not all(re.search(r'\.' + re.escape(key) + r'\b', text)
                                        for key in (url_key, div_key)):
                continue
            if endpoint := parse_endpoint(value, site):
                found.append(dict(endpoint, source=str(page), setting=url_key))
                seen.add(value)
    return found


def inspect(path, page_cache=None):
    """Return the client family and the static evidence for a saved response."""
    raw = path.read_bytes()
    summary = {
        'file': str(path),
        'size': len(raw),
        'sha256': hashlib.sha256(raw).hexdigest(),
        'client_family': 'unclassified',
        'endpoints': [],
    }
    body = raw.removeprefix(b'\xef\xbb\xbf').strip()
    if not body or HTML_RESPONSE.match(body):
        summary['client_family'] = 'not-javascript'
        summary['reason'] = 'empty response' if not body else 'HTML response'
        return summary

    text = body.decode('utf-8', errors='replace')
    if all(pattern.search(text) for pattern in MESSAGING.values()):
        _, _, site = archive_location(path)
        summary.update(client_family='js-password-only', client_family_evidence=list(MESSAGING),
                       endpoints=password_endpoints(text, path, site))
        return summary

    if all(pattern.search(text) for pattern in CONTENT_TRANSPORT.values()):
        for response_format, fingerprints in RESPONSE_PARSERS.items():
            if not all(pattern.search(text) for pattern in fingerprints.values()):
                continue
            summary.update(
                client_family='js-content-transport',
                client_family_evidence=[*CONTENT_TRANSPORT, *fingerprints],
                response_format=response_format,
                endpoints=polling_endpoints(path, text, page_cache if page_cache is not None else {}),
                request_methods=sorted(set(re.findall(r"""['"](GET|POST)['"]""", text))),
            )
            if response_format == 'xml':
                summary['xml_fields'] = sorted(set(XML_FIELDS.findall(text)))
            break
    return summary


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('paths', nargs='*', type=Path, help='JS files or archive directories')
    parser.add_argument('--output', type=Path, default=Path('js'), help='report directory (default: %(default)s)')
    args = parser.parse_args()

    scripts = set()
    for path in args.paths or [DEFAULT_DUMP]:
        if path.is_dir():
            scripts.update(
                file.resolve() for file in path.rglob('*')
                if '.git' not in file.relative_to(path).parts and file.is_file()
                and file.name.partition('?')[0].lower().endswith('.js')
            )
        elif path.is_file():
            scripts.add(path.resolve())
        else:
            parser.error(f'input path does not exist: {path}')

    reports = []
    endpoint_pages = {}
    for path in sorted(scripts):
        try:
            reports.append(inspect(path, endpoint_pages))
        except OSError as error:
            reports.append({'file': str(path), 'error': str(error)})
    families = Counter(row['client_family'] for row in reports if 'client_family' in row)
    summary = {
        'files': len(reports),
        'client_families': dict(sorted(families.items())),
        'content_response_formats': dict(sorted(Counter(
            row['response_format'] for row in reports if 'response_format' in row
        ).items())),
        'errors': sum('error' in row for row in reports),
    }
    args.output.mkdir(parents=True, exist_ok=True)
    summary['endpoint_styles'] = write_endpoints(reports, args.output / 'endpoints.csv')
    for name, report in (('inventory', reports), ('summary', summary)):
        (args.output / f'{name}.json').write_text(json.dumps(report, indent=2) + '\n')
    print(json.dumps(summary, indent=2))
    return bool(summary['errors'])


if __name__ == '__main__':
    raise SystemExit(main())
