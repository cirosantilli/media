#!/usr/bin/env python3
"""Run an archived website and its recovered JAR, SWF or JavaScript client locally.

python3 reconstruction.py             # original applet tag for CheerpJ Applet Runner
python3 reconstruction.py --cheerpj   # load CheerpJ from the page
python3 reconstruction.py --native    # also launch installed Java 8 appletviewer
python3 reconstruction.py --native --self-test # native linkage + protocol, no GUI
python3 reconstruction.py --check     # check archive/config/class preservation
python3 reconstruction.py --list      # list supported hostnames
python3 reconstruction.py iraniangoalkicks.com
python3 reconstruction.py starwarsweb.net  # Flash content loader via Ruffle
python3 reconstruction.py classymotors.net # JavaScript messaging; no emulator
python3 reconstruction.py worldofonlinenews.com # original digital bandwidth meter
python3 reconstruction.py globaltourist.net --meter-preview /tmp/globaltourist.png
python3 reconstruction.py mynepalnews.com --native # keyboard-activated slideshow family
python3 reconstruction.py inews-today.com --native # original scrolling display and messaging
python3 reconstruction.py lajornadanow.com --jar-scroll-speed 2.5 # original constructor speed

Use --source-jar with European News Flash, My Nepal News, iNews Today or Half Court
builds under comms-reverse/jar/password-only and comms-reverse/jar/slideshow.
Use --source-js comms-reverse/password-only.js for a readable JS mailbox.
Use --source-js comms-reverse/polling.js for the Open Book and Al Ashak News.
These clients use the same local servers and separate mailbox interfaces.

The archive defaults to ../../cia-2010-websites-dump/, resolved relative to this
script, independently of the working directory. Open http://127.0.0.1:8000/.
For applets, enable CheerpJ Applet Runner for the local address in a Chromium
browser, or use --cheerpj to have the page load CheerpJ itself.
For JAR/JS sites, log in with the demo password printed by the server.
The optional hostname selects a site; the default is latinamericanewsbeat.com.
The default demo password is demodemodemo; original credentials are unknown.

Slideshow JAR reconstruction: five sites (JAR-SLIDESHOW in --list)
  Requires a Java 8 JDK, including when using the browser runtime. Focus the
  applet, type q azsx p (without spaces).
  This sequence is for mynepalnews.com; other sites print their own keys.
  half-court.net uses the older hold variant: press/release x, hold a while
  typing court, then release a. Its complete archived JAR is unchanged.
  Log in as smiley or karla with the same printed password. All original classes and embedded
  icons/background remain original. Recovered sequences from jar-activation-sequences.json
  use the entire original JAR unchanged. Otherwise only the activation hash changes
  to accept a demo fallback; --list and startup print the actual sequence.
  Each account has distinct opening text and one scripted reply, queued after
  the first user upload. Reload and log in again to retrieve it. Only one active
  applet is supported; mailboxes and read/delete state survive page reloads in
  helper-process memory and reset when the server stops. My Nepal News cycles
  its archived mountain image with two labeled reconstruction photos.
  --jar-slideshow-interval sets how long each server-supplied slide is shown.
  The default is four seconds, from the archived client's effect constructor.
  Other slideshows hold their archived background fixed. Recovered RSS responses are reused
  exactly (My Nepal News has its original 2011-02-11 Top Stories response).
  Headless checks cover activation, login, encrypted transfer and native linkage.
  Read/delete actions and mailbox state across reloads are tested with the
  original protocol classes. Desktop/browser UI rendering is not verified.

Scrolling JAR reconstruction: three sites (JAR-SCROLLING in --list)
  inews-today.com, lajornadanow.com and turkishnewslinks.com use the same
  messaging implementation as the slideshow clients, with original scrolling
  text rendering and movement. --list shows each site's opening sequence.
  All original outer keys are retained. Recovered JSON sequences take priority
  over demo fallbacks. Spaces in --list separate steps, not keystrokes.
  The local server cycles through three site-specific demo headlines, each
  labeled as a reconstruction. The original applet renders the ticker.
  Both demo accounts and replies on the next login work as on slideshow sites.
  Scrolling defaults to 40 pixels/s for the local demo. Content-queue refills
  carry the login protocol too; the original constructor speed of 2.5 pixels/s
  can make authentication take minutes. Restore it with --jar-scroll-speed 2.5.
  Only the server-controlled effect field changes; client classes stay original
  and the historical server speed remains unknown.

Weather reconstruction: information-junky.com and nepalnewsbrief.com
  The original 21-class JARs run unchanged against a Python weather server.
  Missing artwork is made up and visibly labeled; readings and initial cities
  are synthetic. No login or messaging UI has been recovered in these JARs.
  --weather-controls enables the original CUST parameter and enlarges the applet
  so its Cities button is accessible. This optional view is not the archived layout.
  The normal view retains archived dimensions, defaults and original field timing.
  --self-test runs both original protocol classes and the original text painter;
  it does not verify a browser or desktop window.

JavaScript reconstruction: messaging sites (see --list, JS-PASSWORD-ONLY section)
  Select a hostname, for example classymotors.net, iranfootballsource.com,
  wahidfutbol.com or russiansportsworld.com. Follow the login anchor printed at
  startup, or scroll to the login form at the bottom of the homepage.
  Use the printed demo password (default demodemodemo), then click the login
  icon or press Enter. Original English, Farsi, Arabic and Russian labels are
  retained. Compose, Reply, Delete and Send use the recovered client code.
  The first send queues a scripted reply. Reload and log in again to retrieve it.
  No browser plugin, emulator or external JavaScript runtime is required.

  The clients perform RSA key wrapping, SHA-256 challenge responses and AES
  message encryption against our implementation of their recovered protocol.
  Four strings are changed in each served copy: server URL, password-encrypted
  demo secret, local RSA public exponent/modulus. Variables are ta/rk/sz/sy in
  most clients and _url/_ssEnc/KeyE/KeyN in todaysnewsandweather-ru.com.
  Three inherited defensive UI edits prevent form navigation on Enter,
  initialize an inbox string, and use this.value for message selection. These
  were applied proactively, not all demonstrated necessary in modern browsers.
  No other client source is changed; /__reconstruction__/original.js serves the
  exact archived script. --check verifies that reversing the listed edits
  restores the original bytes. Archived files remain untouched.

  Original same-site icons take priority: Iran Football Source's golden football
  survives in a July 2013 capture. The earlier eleven-site audit found 76 of
  77 references missing; Hodermod adds seven more missing icons. Their replacements are original, unmodified lock/compose/reply/delete/
  close/inbox/send images decrypted in memory from latinamericanewsbeat.com's
  today.jar. They match across 45 JARs (Inbox: 44); Wahid Futbol uses the same
  filenames. This is a historically grounded substitution, NOT proof that the
  missing JS icons were identical. If that donor JAR is absent, simple SVG
  placeholders are used only on the earlier sites. Per-icon paths/hashes are in
  --check; the full archive
  comparison is recorded in javascript-icons.json beside this script.

  Page HTML, CSS and surviving artwork are original, with feed URLs redirected
  locally. Some external feeds have small period replacements, others are empty;
  provenance records sources. Missing secondary pages/images remain unavailable.
  Classy Motors has two decorative SVG substitutes and November 2010 car stories.
  Some pages and assets come from different archive dates, noted per site.
  Middle East News Today's missing newsDiv is added at the bottom and its broken
  Java fallback hook is wired to func0. That login placement is reconstructed.
  Network Connection's unused, unarchived Adobe embedding helper is omitted.
  Russian Sports World retains its automatic browser-environment report, which
  is acknowledged without generating a message. No report is sent externally.
  The recovered interfaces have no file-upload control. Exit retains the
  original window-close attempt, which modern browsers may refuse for a tab.
  Messages are synthetic or supplied by you, stored in memory until restart.
  --self-test needs Node.js and runs the actual selected client through login,
  inbox, Unicode send, reply, delete and bad-password handling. Only DOM and
  HTTP transport are mocked; this is not a browser rendering test.

SWF reconstruction: starwarsweb.net
  The recovered Flash 7 / ActionScript 2 client loads server-defined display
  objects over AMF0 from servlet/starwarsimg (operation s.a). It also requests
  servlet/layouts and propset.css. Those responses were not recovered: the one
  captured servlet response is empty. The replacement server cycles surviving
  game-cover images in the original 158x217 panel; its content/order are invented.
  No login or messaging interface was recovered in this SWF. It can load additional
  movies, but their contents are missing. The JAR password/conversation do not apply.
  The page uses Ruffle 0.7.1 from unpkg.com. Ruffle's AMF decoder loses the registered
  k/l/o class prototypes needed by the original instanceof checks. The served SWF
  gains one ActionScript block that restores those prototypes before onResult.
  Every original SWF tag is retained byte-for-byte; the archived SWF is untouched.
  Served HTML expands the relative gatewayUrl FlashVar to an absolute localhost
  URL because Ruffle's NetConnection.connect requires an HTTP(S) URL.
  /__reconstruction__/original.swf serves the exact original for comparison.
  Missing hover images reuse normal buttons. Missing category pages remain absent.
  --self-test checks SWF preservation and AMF replies, not browser rendering.

Bandwidth meters: worldofonlinenews.com and globaltourist.net
  All 21 original classes remain byte-identical; only URL in the nested
  bw.properties ZIP is replaced. The original applet draws and measures traffic.
  Local deterministic test bytes and --meter-delay (default 0.12 seconds) make
  it runnable without the lost server. This is NOT a measurement of Internet speed.
  --meter-preview PNG invokes the unchanged digital Canvas.paint with original
  dimensions, colors, scale and font setup, drawing a real local measurement.
  This is a component rendering, not a historical or browser screenshot; fonts
  may differ. --self-test exercises original network and ObjectInput/ObjectOutput
  HostHook classes with synthetic payloads. The missing hook provider, activation
  and any full messaging UI are not reconstructed.

  Recovered feeds take priority and are served byte-for-byte, including their
  original titles and links. Later captures are identified in provenance. Missing
  feeds use documented period stories; missing JS icons may use art from the
  closely related messaging JAR family. The new sites do not invent missing art.

Fidelity
  Each supported messaging JAR's 96 original Java classes remain byte-for-byte identical.
  The applet's interface, cryptography and messaging protocol are recovered code.

  Website: original HTML, CSS and surviving images, with small layout/menu fixes
  applied while serving. Assets are drawn from several archived captures.
  Missing images: some reuse homepage artwork; missing labels, logos, arrows
  and the RSS converter badge have simple replacements. Some decorative
  backgrounds and secondary pages were not archived and remain unavailable.
  News feeds: plausible period stories where supplied, reconstructed rather than
  recovered. Their source links are recorded in the provenance output.
  JAR configuration: server address and cryptographic settings are changed to
  connect locally; invalidated signatures are removed from the served copy.
  Server: our implementation of the observed protocol, not recovered server
  software. The messages are fictional; automatic replies and storage behavior
  are approximations. Demo state lives in memory and resets on server restart.
  Browser runtime: enable the CheerpJ Applet Runner extension for the local
  address, or pass --cheerpj to load the runtime from the vendor CDN.
  Native Java 8: the messaging JARs reference the browser's LiveConnect API,
  absent from OpenJDK appletviewer. --native compiles a temporary, separate
  netscape.javascript compatibility library using that JDK's javac. getWindow()
  throws JSException, which the original client already catches when browser
  access is unavailable. No browser objects or metadata are invented, and no
  original class is modified. The library is passed only to the native viewer
  through its Java 8 boot class path and removed when the server exits.
  --native --self-test checks all original class signatures and this exception
  behavior before the usual protocol test; it does not test a graphical window.
  File selection: the original modal AWT dialog has been reported stuck under
  CheerpJ; its underlying defect remains unidentified. The attempted JavaScript
  picker workaround was removed because it did not restore working uploads.
  CheerpJ's title-bar "Add files" button imports into the browser's virtual
  /files/uploads/ directory only. The applet must still select and send that file
  for the Python server to receive it. A user-reported Applet Runner upload
  reached the local server.

In-memory JAR hotpatching
  When the Python server starts, it decrypts resource/resources.bin, changes
  four configuration entries, then re-encrypts it:
    ServerURL        -> the localhost messaging endpoint
    KeyE, KeyN       -> the demo server's newly generated RSA public key
    EncSharedSecret  -> the demo shared secret, encrypted for the configured
                        password (default: demodemodemo; override: --password)
  It rebuilds the served JAR and removes the now-invalid signature metadata.
  No Java class bytes, client logic or UI code are rewritten. The --cheerpj
  option initializes CheerpJ in the served HTML.

The original JAR and all archived source files remain untouched on disk. You are
operating the actual recovered client within a reconstructed surrounding service.
The messaging JAR sites share one recovered client/protocol family; meters are separate. Other JAR families,
including some of the article's featured gallery examples, are not supported yet.
--check and --self-test apply to the selected hostname. The latter runs its actual
Java protocol classes for JAR sites and JavaScript client for JS sites offline;
neither check validates browser rendering.
Run notes: /__reconstruction__/about
Source hashes and change inventory: /__reconstruction__/provenance.json
"""

import argparse
import ast
import base64
import datetime
import gzip
import hashlib
import html
import io
import json
import mimetypes
import os
import re
import runpy
import secrets
import select
import shutil
import struct
import subprocess
import tempfile
import threading
import time
import urllib.parse
import zipfile
import zlib
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from html.parser import HTMLParser
from dataclasses import dataclass
from pathlib import Path
from xml.etree import ElementTree

try:
    from cryptography.hazmat.primitives.asymmetric import rsa, padding
    from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
except ImportError:
    raise SystemExit('This reconstruction needs Python cryptography: python3 -m pip install cryptography')

DEFAULT_HOST = 'latinamericanewsbeat.com'
APPLET_KINDS = ('jar', 'jar-slideshow', 'jar-scrolling', 'jar-weather', 'meter')
RESOURCE = 'resource/resources.bin'
RESOURCE_KEY = '0C5C45F2EC3C2A63'
RUNTIME = 'https://cjrtnc.leaningtech.com/4.3/loader.js'
DISPLAY_DISCLAIMER = 'Reconstruction only, not based on archived content.'
SCROLLING_HEADLINES = {
    'inews-today.com': (
        'Regional leaders meet for trade talks in Geneva.',
        'New night trains link cities across Europe.',
        'Weekend markets draw crowds in Seoul.',
    ),
    'lajornadanow.com': (
        'La feria del libro reúne a lectores en México.',
        'Bogotá amplía sus rutas de autobús.',
        'Montevideo lleva el cine al aire libre a la rambla.',
    ),
    'turkishnewslinks.com': (
        'Istanbul adds weekend ferry trips across the Bosphorus.',
        'Ankara opens an exhibition of Anatolian textiles.',
        'Olive growers bring the harvest to markets in Izmir.',
    ),
}
# The supplementary photos were edited and labeled for the 146×219 applet.
# annapurna-south.jpg is adapted from Binaya Adhikari's CC BY-SA 4.0 photo.
# https://commons.wikimedia.org/wiki/File:Annapurna_south_and_nepali_village.jpg
# https://creativecommons.org/licenses/by-sa/4.0/
# swayambhunath-flags.jpg is adapted from Arieh's public-domain photo.
# https://commons.wikimedia.org/wiki/File:Swayambunathflags.jpg
NEPAL_SLIDES = ('annapurna-south.jpg', 'swayambhunath-flags.jpg')
RUFFLE_RUNTIME = 'https://unpkg.com/@ruffle-rs/ruffle@0.7.1/ruffle.js'
DEFAULT_DUMP = Path(__file__).resolve().parent / '../../cia-2010-websites-dump'
RECONSTRUCTION_TMP = Path(__file__).with_name('reconstruction') / 'tmp'
SOURCE_JAR_SITE = 'europeannewsflash.com'
DISPLAY_SOURCE_SITES = ('mynepalnews.com', 'inews-today.com', 'half-court.net')
SOURCE_JAR_ROOT = Path(__file__).resolve().with_name('comms-reverse') / 'jar'
PASSWORD_ONLY_SOURCE_PROJECT = SOURCE_JAR_ROOT / 'password-only'
DISPLAY_SOURCE_PROJECT = SOURCE_JAR_ROOT / 'slideshow'
MAX_UPLOAD = 5 * 1024 * 1024  # Cm.c(): min(hex(MaxFileSize), 5120) * 1024


@dataclass(frozen=True)
class Site:
    hostname: str
    homepage: str
    client: str
    description: str
    language: str = 'en'
    presentation: str = 'generic'
    notes: tuple = ()
    kind: str = 'jar'
    client_sha256: str = ''
    allow_placeholder_art: bool = True
    activation_code: str = ''  # Fallback only; recovered JSON sequences take priority.
    hold_activation: bool = False

    @property
    def capture(self):
        return self.homepage.split('/')[0]

    @property
    def client_url(self):
        return '/' + self.client.split('/', 1)[1]


def activation_settings(site):
    """Share the exact-capture activation choice between --list and runtime."""
    records = json.loads((Path(__file__).with_name('jar-activation-sequences.json')).read_text())
    matches = [row for row in records if row['jar'] == site.hostname + '/' + site.client]
    if len(matches) > 1:
        raise ValueError('Duplicate activation records for ' + site.hostname)
    if matches and matches[0]['sequence']:
        row = matches[0]
        expected_format, expected_mode = (('literal', 'hold_trigger_while_typing') if site.hold_activation
                                          else ('sha256', 'press_trigger_after_typing'))
        if row['sequence_format'] != expected_format or row['activation_mode'] != expected_mode:
            raise ValueError('Unsupported activation variant for ' + site.hostname)
        # The older listener checks literal characters while the trigger is held.
        # The newer listener hashes the ASCII hex of its UTF-16BE buffer at trigger time.
        expected = (row['sequence'] if site.hold_activation else
                    hashlib.sha256(row['sequence'].encode('utf-16-be').hex().encode('ascii')).hexdigest().upper())
        if not site.hold_activation and expected != row['hash'].upper():
            raise ValueError('Recovered sequence does not match its hash: ' + site.hostname)
        if row['clear_key'] in row['sequence'] or row['trigger_key'] in row['sequence']:
            raise ValueError('Recovered sequence conflicts with the outer keys: ' + site.hostname)
        return row['clear_key'], row['sequence'], row['trigger_key'], expected
    if site.hold_activation:
        raise ValueError('The hold variant requires an original literal sequence')
    code = site.activation_code
    return code[0], code[1:-1], code[-1], None


# Explicit snapshots retain each site's recovered client, labels and layout.
# The messaging clients share a protocol; the SWF is a separate content loader.
SITES = {site.hostname: site for site in (
    Site(DEFAULT_HOST, '20100513190714/index.html', '20100513191045/today.jar',
         'Latin America news (default)', presentation='latin'),
    Site('iraniangoalkicks.com', '20080211111124/index.html', '20110202091917/clamping.jar',
         'Iranian football; highlighted by Reuters', 'fa', notes=(
             'The surviving homepage is from 2008 and the JAR from 2011.',
             'Missing 155x14 navigation separators are reconstructed; some secondary-page images are absent.')),
    Site('farsi-newsandweather.com', '20110202122304/index.html', '20110202122354/steps.jar',
         'Farsi news and weather', 'fa'),
    Site('firstnewssource.com', '20110203072201/index.html', '20110203072529/btools.jar',
         'Farsi news with sliced header artwork', 'fa', notes=(
             'Missing decorative backgrounds: images/news_33.gif, News_34.gif and menu8.gif.',)),
    Site('europeannewsflash.com', '20110201191814/index.html', '20110201192135/users.jar',
         'European news', notes=('The embedded news page background main_text.jpg is unarchived.',)),
    Site('global-view-news.com', '20110201100028/index.html', '20110201100059/ticker.jar',
         'World news'),
    Site('news-latina.com', '20110208065123/index.html', '20110208065133/news.jar',
         'Latin American news', 'es'),
    Site('esmundonoticias.com', '20110201124007/index.html', '20110201124235/anuncios.jar',
         'Spanish news, business and sports', 'es', notes=('The decorative images/icons/rsshat.gif is unarchived.',)),
    Site('shadesofnews.com', '20110201184753/index.html', '20110201184827/sports.jar',
         'Arabic news', 'ar', notes=('The menu background images/menu8.gif is unarchived.',)),
    Site('health-men-today.com', '20110207232317/index.html', '20110207232346/cardio.jar',
         'Arabic health site', 'ar', notes=('Some Arabic page text is already garbled in the archive.',)),
    Site('todayoutdoors.com', '20110228050804/index.html', '20110228050838/fires.jar',
         'Hiking and outdoor activities', notes=('Decorative navigation backgrounds tableft10.gif and tabright10.gif are unarchived.',)),
    Site('soccerguidesite.com', '20110207103008/index.html', '20110207103045/tools.jar',
         'Korean football', 'ko', allow_placeholder_art=False, notes=(
             'Some secondary pages and artwork are not archived.',)),
    Site('globalsentinelsite.com', '20110201094751/index.html', '20110201094846/steps.jar',
         'Global Sentinel', allow_placeholder_art=False),
    Site('mynepalnews.com', '20110203000411/index.html', '20110204143500/nepali.jar',
         'My Nepal News', kind='jar-slideshow',
         allow_placeholder_art=False, activation_code='qdemop',
         client_sha256='d4de26de8d03ed9bad213d75057a3c8df5c802719ac3374058f525f569822568'),
    Site('half-court.net', '20101229100006/index.html', '20101229100220/basket.jar',
         'Basketball; press-and-hold activation', kind='jar-slideshow', allow_placeholder_art=False,
         activation_code='xcourta', hold_activation=True,
         client_sha256='d9c3ef450f19b880396e1a3d3e70263e4a395bfe4986c872bd8f09d16fea7959'),
    Site('echessnews.com', '20110207193351/index.html', '20110207193421/ad.jar',
         'Chess', kind='jar-slideshow', allow_placeholder_art=False, activation_code='qdemoz',
         client_sha256='94f666a6e084717e5b66b4c57d17c21f0bb4f9165c10e0a194819bd9111747e6'),
    Site('first-tee-golf.com', '20110203073359/index.html', '20110203073503/golf.jar',
         'Golf', kind='jar-slideshow', allow_placeholder_art=False, activation_code='xtestm',
         client_sha256='3ca080e045723259a2db9d125c4ae5b4af5ba845a4c5cd7afb8d4f375b8faf7c'),
    Site('soldiersofsouthasia.com', '20110207203640/index.html', '20110207203807/war.jar',
         'South Asian military news', kind='jar-slideshow', allow_placeholder_art=False, activation_code='pdemoz',
         client_sha256='8d4b80b90d805559a9e249cb524bd187ea3ce7bdc92aacc5d2d1faaf78470dcd'),
    Site('inews-today.com', '20110125190345/index.html', '20110201140115/headlines.jar',
         'World news', kind='jar-scrolling', allow_placeholder_art=False, activation_code='wdemoq',
         client_sha256='69f76966399590f06163dfb2c9334c3c345c815d526b32845f226926793bfaa1'),
    Site('lajornadanow.com', '20100513135348/index.html', '20100513135549/noticias.jar',
         'Latin American news', kind='jar-scrolling', allow_placeholder_art=False, activation_code='zdemoq',
         client_sha256='8950905f6cd503a1c7eb4a959377a1fec46dcaa7dd0a108450e9f76661b466f4'),
    Site('turkishnewslinks.com', '20110129021514/index.html', '20110129021654/feeds.jar',
         'Turkish news', kind='jar-scrolling', allow_placeholder_art=False, activation_code='cdemoz',
         client_sha256='3791d98d23031076ca3637630d4e9506c6f2e5ede9d8c252b329955db29f9c26'),
    Site('information-junky.com', '20110201155443/index.html', '20110201160020/infoweather.jar',
         'Weather; made-up artwork and readings', kind='jar-weather',
         client_sha256='25fd7af53dde40abdc9495cccd4a6a8a7fbb81d8ed833259be9e3638734ff0cb'),
    Site('nepalnewsbrief.com', '20101114222707/index.html', '20110207221911/regionweather.jar',
         'Weather; made-up artwork and readings', kind='jar-weather',
         client_sha256='c28d8712e01b9c13525131b15de2766249a2cd87dcb7d540239d935d2cdaf46c'),
    Site('worldofonlinenews.com', '20110210051430/index.html', '20110210051552/world.jar',
         'World of Online News; original working bandwidth meter', kind='meter', allow_placeholder_art=False,
         client_sha256='0a88aeab4328b4f1ec449ba53ae45625a61a4624d26034479f6fa056af73bad2'),
    Site('globaltourist.net', '20100920041558/index.html', '20101229090929/speed.jar',
         'Global Tourist; original working bandwidth meter', kind='meter', allow_placeholder_art=False,
         client_sha256='98884d8f244cc88e6a2e04802dcf596d51876ab0dd02870f80dbb7e312354ee2', notes=(
             'Homepage and JAR are from different 2010 captures.',)),
    Site('hodermod.com', '20110207132544/index.html', '20110207133001/futbol.js',
         'Hodermod; Persian football messaging', 'fa', kind='js', allow_placeholder_art=False,
         client_sha256='a045c164f42dfd325695eb372ab2d367e0035f8af5c12d9d29670a71f8cd7a6d'),
    Site('starwarsweb.net', '20101230033220/index.html', '20101230033642/starwarsweb.swf',
         'Star Wars: Beyond the Unknown; original content loader, replacement server content',
         presentation='starwars', kind='swf', notes=(
             'No login or messaging interface was recovered in this SWF; additional server-loaded movies are unavailable.',
             'The archived servlet/starwarsimg response is empty; servlet/layouts and propset.css were not captured.',
             'The Flash panel cycles surviving game artwork using newly constructed AMF responses.',
             'Missing hover images reuse their normal button images; unarchived category pages remain unavailable.',)),
    Site('classymotors.net', '20101123200141/index.html', '20110106001007/driver.js',
         'Classy Motors; recovered JavaScript login, inbox and messaging client',
         presentation='classymotors', kind='js',
         client_sha256='ab37c4bd70f0c4660084ceecf15ab6246750e31bf31455ca2950d3a57607eacd', notes=(
             'The homepage is from November 2010 and the JavaScript/assets from January 2011.',
             'Seven messaging icons are borrowed from the recovered JAR family; two missing decorative CSS images have SVG substitutes.',
             'Secondary pages retain their HTML but 15 referenced images are missing from the archive.',
             'Missing RSS feeds are replaced with a small reconstructed automotive news sample.',)),
    Site('iranfootballsource.com', '20110202091611/index.html', '20110202091901/futbol.js',
         'Iran Football Source', 'fa', kind='js', client_sha256='7518d4885e67f3b904848ecfacfb631bd4c8595cf40e2bdb50f462edba415878',
         notes=('The original golden-football login icon survives in a July 2013 capture; the six toolbar icons are missing.',)),
    Site('iraniangoals.com', '20090411072857/index.html', '20110202091909/journal.js',
         'Iranian Goals', 'fa', kind='js', client_sha256='7f8339ff82fb66f3e63d8aeebe8a030b415630a75a4216078e51816a44e2ceb0',
         notes=('The homepage is from April 2009 and the JavaScript from February 2011.',)),
    Site('kukrinews.com', '20100513094901/index.html', '20100513094909/news.js',
         'Kukri News; Nepal', 'en', kind='js', client_sha256='ffa05d7b339ef44d84bdd71ed9ad75f58ce9e979385f8fc708bce91b6ab25501',
         notes=()),
    Site('middle-east-newstoday.com', '20100517070603/index.html', '20100517070625/kurds.js',
         'Middle East News Today', 'ar', kind='js', client_sha256='4a3f2b66b797b79b44e778f74251ced94c5bb6b39d6f996ef4c11db994a185e2',
         notes=('The captured HTML lacks its newsDiv container and has a broken Java-to-JavaScript startup hook; a container is placed at the bottom and func0 is invoked on load. The position is reconstructed.',)),
    Site('networkconnectionsite.com', '20110208004005/index.html', '20110208004203/netnews.js',
         'Network Connection', 'en', kind='js', client_sha256='46d23ed9b29eeab33412739ba64fcd45c3775bd300a295c58ad68ecdb335b649',
         notes=('An unarchived, unused Adobe embedding helper is omitted while serving.',)),
    Site('thefootball-life.com', '20110202151142/index.html', '20110202151214/groups.js',
         'The Football Life', 'en', kind='js', client_sha256='6eff391083c371de0f0e05e0b0c56b6ede025fb1fa109714690b8a50a8db3816',
         notes=()),
    Site('todaysnewsandweather-ru.com', '20110207094714/index.html', '20110207094735/blacksea.js',
         'Today’s News and Weather', 'ru', kind='js', client_sha256='815f6f2414e9c2bea659c8030567ab871588d82ef76dd98a0d0c76fdc0d95c54',
         notes=('This client retains descriptive function names instead of the other sites’ short names.',)),
    Site('totallynewsnow.com', '20110207201846/index.html', '20110207201929/tnewnow.js',
         'Totally News Now', 'en', kind='js', client_sha256='9d5ba7f646c6d2cdb145aa8a59afee4cf83576bd495e752d300ac235baf261fc',
         notes=()),
    Site('wahidfutbol.com', '20110202203450/index.html', '20110202203517/wahid.js',
         'Wahid Futbol', 'ar', kind='js', client_sha256='2bfcb17111e3e1086203bb18ab27232d6b31b7eadc272cce54e807046023764c',
         notes=()),
    Site('www.russiansportsworld.com', '20110207170820/index.html', '20110207171201/snowboard.js',
         'Russian Sports World', 'ru', kind='js', client_sha256='7cf4ff00f8e250330e3cdf10816fe471b3776c0bdb52913b812fde888fdddf7c',
         notes=('The original client sends a browser-environment report after login; the local server acknowledges it without adding an inbox reply.',)),
    Site('the-open-book-online.com', '20110202225251/index.html', '20110202225254/breaking.js',
         'The Open Book; XML polling', kind='js-polling',
         client_sha256='5b813becf8e95a359b543dc8d24b6bcdda49a0ed89e15e14359f107ac92058ee'),
    Site('al-ashak-news-me.com', '20110203003800/index.html', '20110203003835/ashak.js',
         'Al Ashak News; delimited-text polling', 'ar', kind='js-polling',
         client_sha256='844c1249ad2704219043bbb7150e1c458918bfb1d25efa634d13618edd50609f'),
)}

DEMO_INITIAL = ('We need the information on the target urgently. There are indications '
                'they may act within the next 48 hours. Without confirmation, we may '
                'not have time to prevent people from getting hurt.\n\n'
                'Can you get it today? Please confirm as soon as you can.')
DEMO_REPLY = ('OK. Our man will be there to pick it up.')
DEMO_SECOND_INITIAL = ('We have two conflicting reports about tomorrow\'s delegation meeting. '
                       'Can you confirm whether it is still going ahead, and who is expected to attend? '
                       'The director would prefer facts to another beautifully formatted guess.')
DEMO_SECOND_REPLY = ('Received. That settles the discrepancy between our sources. '
                     'I will update the morning briefing. '
                     'You may have spared us another two-hour meeting.')

# Editorial reconstructions, not recovered feed entries. Dates are story/event
# dates, not invented exact publication times. Links document the underlying news.
# All sections use the homepage's May 2010 period, including November page shells.
NEWS = {
    'forlan': ('2010-05-12', 'Forlán double gives Atlético glory',
              'Uruguay striker Diego Forlán scored twice as Atlético Madrid beat Fulham 2–1 after extra time in Hamburg.',
              'https://www.uefa.com/uefaeuropaleague/news/0250-0c50f4f42d9b-b99f75c54493-1000--forlan-double-gives-atletico-glory/'),
    'brazil': ('2010-05-11', 'Ronaldinho left out of Brazil squad',
              'Coach Dunga has named his World Cup squad without Ronaldinho. Brazil will face North Korea, Ivory Coast and Portugal in the group stage.',
              'https://www.fourfourtwo.com/news/ronaldinho-left-out-brazil-squad'),
    'argentina': ('2010-05-12', 'Zanetti out of Argentina squad',
                 'Argentina’s preliminary World Cup selection leaves out the two experienced Inter players as preparations for South Africa continue.',
                 'https://www.fourfourtwo.com/news/zanetti-out-argentina-squad'),
    'haiti': ('2010-05-11', 'Brazil Becomes First Country to Contribute to Haiti Reconstruction Fund',
             'Brazil has become the first contributor to a reconstruction fund helping Haiti recover from January’s earthquake.',
             'https://documents1.worldbank.org/curated/en/473641647004086178/pdf/Announcement-of-Brazil-Becomes-First-Country-to-Contribute-to-Haiti-Reconstruction-Fund-on-May-11-2010.pdf'),
    'costa-rica': ('2010-05-08', 'Chinchilla asume la presidencia de Costa Rica',
                  'Laura Chinchilla becomes the country’s first woman president, succeeding Óscar Arias and promising greater attention to Central America.',
                  'https://elpais.com/internacional/2010/05/08/actualidad/1273269602_850215.html'),
    'investment': ('2010-05-06', 'Foreign Direct Investment in Latin America and the Caribbean Expected to Rise 40%-50% in 2010',
                   'ECLAC expects foreign direct investment in the region to rise by 40–50 percent in 2010 following last year’s sharp fall.',
                   'https://www.cepal.org/en/pressreleases/foreign-direct-investment-latin-america-and-caribbean-expected-rise-40-50-2010'),
    'cannes': ('2010-05-12', 'The Festival opens with Ridley Scott',
              'The film festival opens with Ridley Scott’s adventure starring Russell Crowe and Cate Blanchett. Screenings continue until May 23.',
              'https://www.festival-cannes.com/en/2010/the-festival-opens-with-ridley-scott/'),
    'coalition': ('2010-05-12', 'Prime Minister and Deputy Prime Minister hold first press conference',
                 'The Conservative and Liberal Democrat leaders appear together at Downing Street after agreeing to share power.',
                 'https://www.gov.uk/government/news/pm-and-deputy-pm-hold-first-press-conference'),
    'greece': ('2010-05-09', 'IMF Executive Board Approves €30 Billion Stand-By Arrangement for Greece',
              'The three-year arrangement forms part of a €110 billion package with the European Union as Greece confronts its debt crisis.',
              'https://www.imf.org/external/np/sec/pr/2010/pr10187.htm'),
}
FEEDS = {
    'latam': ('Latin America News', ('forlan', 'argentina', 'haiti', 'brazil', 'costa-rica', 'investment')),
    'business': ('Business News', ('haiti', 'greece', 'investment')),
    'sports': ('Sports News', ('forlan', 'argentina', 'brazil')),
    'entertainment': ('Entertainment News', ('cannes',)),
    'world': ('World News', ('coalition', 'haiti', 'greece', 'costa-rica')),
}

# Small dated fallback feeds for the added sites. These are editorial samples,
# never passed off as archived converter responses. No modern network fetches.
PERIOD_NEWS = {
    'egypt-2008': ('2008-02-10', 'Egypt 1-0 Cameroon',
                  'Egypt beat Cameroon 1–0 in the final, with Mohamed Aboutrika scoring the winner.',
                  'https://www.rte.ie/sport/soccer/2008/0210/227915-egypt_cameroon/'),
    'egypt-2011': ('2011-01-28', 'EGYPT: UN High Commissioner for Human Rights urges Government restraint and respect for human rights',
                  'The UN human rights chief called on Egypt to protect demonstrators and investigate reports of excessive force.',
                  'https://archive.crin.org/en/resources/infodetail03a9.html?id=23988'),
    'japan-2011': ('2011-01-29', 'Lee wins Asian Cup for Japan',
                  'Japan defeated Australia 1–0 after extra time in the final in Qatar.',
                  'https://www.aljazeera.com/sports/2011/1/29/lee-wins-asian-cup-for-japan'),
    'brazil-2011': ('2011-01-01', 'UPDATE 2-Rousseff becomes first woman to lead Brazil',
                   'Brazil’s first woman president succeeds Luiz Inácio Lula da Silva and promises to build on his economic record.',
                   'https://www.investing.com/news/forex-news/update-1-brazil-bids-farewell-to-lula-as-rousseff-steps-in-184316'),
    'iphone-2011': ('2011-01-11', 'Verizon Wireless & Apple Team Up to Deliver iPhone 4 on Verizon',
                   'Apple and Verizon say the phone will go on sale on February 10, with advance orders for eligible customers from February 3.',
                   'https://www.apple.com/newsroom/2011/01/11Verizon-Wireless-Apple-Team-Up-to-Deliver-iPhone-4-on-Verizon/'),
}
NEWS_TRANSLATIONS = {
    'fa': {
        'egypt-2008': ('مصر قهرمان جام ملت‌های آفریقا شد', 'مصر در دیدار نهایی با گل محمد ابوتریکه، کامرون را یک بر صفر شکست داد.'),
        'egypt-2011': ('سازمان ملل خواستار خویشتنداری در مصر شد', 'مسئول حقوق بشر سازمان ملل خواستار حمایت از تظاهرکنندگان و بررسی گزارش‌های استفاده بیش از حد از زور شد.'),
        'japan-2011': ('لی جام ملت‌های آسیا را برای ژاپن فتح کرد', 'ژاپن در دیدار نهایی در قطر، استرالیا را در وقت اضافه یک بر صفر شکست داد.'),
        'brazil-2011': ('دیلما روسف ریاست جمهوری برزیل را آغاز کرد', 'نخستین رئیس‌جمهور زن برزیل جانشین لولا داسیلوا شد.'),
        'iphone-2011': ('آیفون ۴ از فوریه در شبکه ورایزن عرضه می‌شود', 'اپل و ورایزن اعلام کردند عرضه این تلفن از ۱۰ فوریه آغاز خواهد شد.'),
    },
    'ar': {
        'egypt-2011': ('الأمم المتحدة تدعو إلى ضبط النفس في مصر', 'دعت مسؤولة حقوق الإنسان إلى حماية المتظاهرين والتحقيق في تقارير الاستخدام المفرط للقوة.'),
        'japan-2011': ('لي يمنح اليابان كأس آسيا', 'فازت اليابان على أستراليا بهدف دون رد بعد وقت إضافي في النهائي الذي أقيم في قطر.'),
        'brazil-2011': ('ديلما روسيف تتولى رئاسة البرازيل', 'أصبحت روسيف أول امرأة تتولى رئاسة البرازيل خلفاً للولا دا سيلفا.'),
        'iphone-2011': ('آيفون 4 يصل إلى شبكة فيرايزون في فبراير', 'أعلنت آبل وفيرايزون بدء بيع الهاتف في العاشر من فبراير.'),
    },
    'es': {
        'egypt-2011': ('La ONU pide moderación ante las protestas en Egipto', 'La responsable de derechos humanos pidió proteger a los manifestantes e investigar el uso excesivo de la fuerza.'),
        'japan-2011': ('Lee gana la Copa de Asia para Japón', 'Japón derrotó a Australia por 1–0 en la prórroga de la final disputada en Catar.'),
        'brazil-2011': ('Dilma Rousseff asume la presidencia de Brasil', 'La primera presidenta del país sucede a Luiz Inácio Lula da Silva y promete continuar sus avances económicos.'),
        'iphone-2011': ('Verizon anuncia la llegada del iPhone 4 en febrero', 'Apple y Verizon anunciaron que el teléfono saldrá a la venta el 10 de febrero.'),
    },
}

LAYOUT = '''<style>
/* Preserve the archived 800px image-slice layout without inline baseline gaps. */
#wrap {display:flow-root;width:800px;margin:0 auto;text-align:left;}
#topbanner,#navigation,#middlebanner,#bottombanner {
  display:flex;flex-wrap:wrap;align-content:flex-start;gap:0;font-size:0;line-height:0;
}
#topbanner>*,#navigation>*,#middlebanner>*,#bottombanner>* {flex:none;}
#topbanner img,#navigation img,#middlebanner img,#bottombanner img {display:block;}
#sidelogin cheerpj-applet {display:block;width:250px;height:75px;}
#java-status {max-width:250px;font:10px Verdana,Arial,sans-serif;color:white;}
.rss-item {margin:0 0 13px;line-height:1.4;}
.rss-date {display:block;font-size:9px;color:#ddd;margin:3px 0;}
</style>'''

# No persistent reconstruction footer. Errors belong next to the applet, and
# initialization starts after the applet element exists (also on slow CDN loads).
JAVA_START = r'''<script>
(function () {
  const applet = document.querySelector('cheerpj-applet');
  if (!applet) return;
  const host = applet.parentElement;
  const status = document.createElement('div');
  status.id = 'java-status'; status.hidden = true; host.appendChild(status);
  function failed(message) { status.hidden = false; status.textContent = message; }
  window.addEventListener('securitypolicyviolation', function (e) {
    if ((e.blockedURI || '').includes('leaningtech.com'))
      failed('Java runtime blocked: ' + e.violatedDirective + '. See browser console.');
  });
  const loader = document.createElement('script');
  loader.src = 'https://cjrtnc.leaningtech.com/4.3/loader.js';
  loader.onerror = function () {
    failed('Java could not load. Check access to cjrtnc.leaningtech.com, or run python3 reconstruction.py --native.');
  };
  loader.onload = async function () {
    try { await cheerpjInit({version:8, status:'splash'}); }
    catch (e) { console.error(e); failed('Java runtime: ' + e); }
  };
  document.head.appendChild(loader);
})();
</script>'''


def feed_items(category):
    return sorted((NEWS[key] for key in FEEDS[category][1]), key=lambda item: item[0], reverse=True)


def feed_html(category):
    return '<!-- Plausible period feed; not recovered archive content. -->' + ''.join(
        '<div class="rss-item"><a target="_blank" rel="noopener" href="' + html.escape(url, quote=True)
        + '">' + html.escape(title) + '</a><span class="rss-date">' + date
        + '</span>' + html.escape(description) + '</div>'
        for date, title, description, url in feed_items(category))


JAVA_CHECK = r'''
import __APPLET_PACKAGE__.*;
import java.util.*;
import java.net.*;
import java.io.*;
public class OriginalClientCheck {
  static void metadata(O client, At config) throws Exception {
    String marker = config.a("MetaDataExt");
    if(marker == null || marker.length() == 0) return;
    // The applet's login report leaves Aj's type at -1 (255 on the wire).
    // Use synthetic contents rather than collecting this machine's details.
    Aj report = new Aj();
    report.a("[navigator]\nappName=Regression test\n[java]\njavaVendor=Test\n".getBytes("UTF-8"));
    report.a(marker); report.a(System.currentTimeMillis()); client.a(report);
  }
  public static void main(String[] args) throws Exception {
    final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    URL.setURLStreamHandlerFactory(protocol -> !protocol.equals("http") ? null : new URLStreamHandler() {
      protected URLConnection openConnection(URL url) throws IOException {
        if(!url.getHost().equals("127.0.0.1") || !url.getPath().equals(args[2]))
          throw new IOException("Unexpected URL " + url);
        return new URLConnection(url) {
          ByteArrayOutputStream body = new ByteArrayOutputStream();
          public void connect() {}
          public OutputStream getOutputStream() { return body; }
          public InputStream getInputStream() throws IOException {
            synchronized(input) {
              System.out.println("REQUEST " + Base64.getEncoder().encodeToString(body.toByteArray()));
              System.out.flush();
              String line = input.readLine();
              if(line == null || line.startsWith("ERROR")) throw new IOException(line);
              return new ByteArrayInputStream(Base64.getDecoder().decode(line));
            }
          }
        };
      }
    });
    At config = At.b(); config.a();
    Cj encoding = "0".equals(config.a("EncodeType")) ? Cj.a :
                  "2".equals(config.a("EncodeType")) ? Cj.e : Cj.c;
    byte[] password = args[0].getBytes("UTF-8");
    Bf decoder = new Bf(Cc.a(password, 1)); decoder.a(false);
    byte[] secret = decoder.b(Cn.a(config.a("EncSharedSecret")));
    O client = new O(config.a("ServerURL"), new A(), password, secret, encoding,
                     Cn.a(config.a("KeyE")), Cn.a(config.a("KeyN")));
    client.a(); client.b();
    if(!client.c().hasMoreElements()) throw new AssertionError("Empty inbox");
    metadata(client, config);
    client = new O(config.a("ServerURL"), new A(), password, secret, encoding,
                   Cn.a(config.a("KeyE")), Cn.a(config.a("KeyN")));
    client.a(); client.b();
    if(Collections.list(client.c()).size() != 1)
      throw new AssertionError("Login metadata generated an inbox reply");
    metadata(client, config);
    String messageMarker = config.a("MessageMarker");
    if(messageMarker == null) messageMarker = "txt";
    String text = "Original Java client round trip: caf\u00e9 \u4e16\u754c";
    Aj outgoing = new Aj();
    outgoing.a(text.getBytes("UTF-8")); outgoing.a(messageMarker);
    outgoing.a(System.currentTimeMillis()); outgoing.a(1); client.a(outgoing);
    if(Collections.list(client.c()).size() != 1)
      throw new AssertionError("Reply arrived before the next login");
    client = new O(config.a("ServerURL"), new A(), password, secret, encoding,
                   Cn.a(config.a("KeyE")), Cn.a(config.a("KeyN")));
    client.a(); client.b();
    if(Collections.list(client.c()).size() != 2)
      throw new AssertionError("Expected initial message and exactly one text reply");
    Object reply = null;
    Enumeration ids = client.c();
    while(ids.hasMoreElements()) {
      Object id = ids.nextElement();
      if(new String(client.b(id).b(), "UTF-8").equals(args[1])) reply = id;
    }
    if(reply == null) throw new AssertionError("No decoded reply");
    client.a(reply);
    ids = client.c();
    while(ids.hasMoreElements()) if(reply.equals(ids.nextElement())) throw new AssertionError("Delete failed");
    Aj upload = new Aj(); upload.a(new byte[]{0,1,2,3,(byte)255});
    // Bb uses type 1 for both text and uploads; Cm supplies the file extension.
    upload.a("bin"); upload.a(System.currentTimeMillis()); upload.a(1); client.a(upload);
    client = new O(config.a("ServerURL"), new A(), password, secret, encoding,
                   Cn.a(config.a("KeyE")), Cn.a(config.a("KeyN")));
    client.a(); client.b();
    if(Collections.list(client.c()).size() != 1)
      throw new AssertionError("An upload after the first reply changed the inbox");
    System.out.println("PASS: original JAR login, metadata, UTF-8 send, one demo reply across logins, deletion, and binary upload");
  }
}
'''

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



def idea_encrypt(block, key):
    """IDEA block primitive, inverse of idea_decrypt, for the resource wrapper."""
    word = int.from_bytes(key[:16], 'big')
    keys = []
    while len(keys) < 52:
        keys.extend((word >> shift) & 65535 for shift in range(112, -1, -16))
        word = ((word << 25) | (word >> 103)) & ((1 << 128) - 1)
    def mul(a, b):
        return (((a or 65536) * (b or 65536)) % 65537) & 65535
    a, b, c, d = struct.unpack('>4H', block)
    for i in range(0, 48, 6):
        a, b, c, d = mul(a, keys[i]), (b + keys[i+1]) & 65535, (c + keys[i+2]) & 65535, mul(d, keys[i+3])
        e = mul(a ^ c, keys[i+4])
        f = mul(((b ^ d) + e) & 65535, keys[i+5])
        g = (e + f) & 65535
        a, b, c, d = a ^ f, c ^ f, b ^ g, d ^ g
    return struct.pack('>4H', mul(a, keys[48]), (c + keys[49]) & 65535,
                       (b + keys[50]) & 65535, mul(d, keys[51]))


def encode_resources(resources, key=RESOURCE_KEY):
    payload = bytearray()
    for name, data in resources.items():
        name = name.encode('utf-8')
        payload += struct.pack('<I', len(name)) + name + struct.pack('<I', len(data)) + data
    clear = gzip.compress(bytes(payload), mtime=0)
    count = 8 - len(clear) % 8
    clear += bytes(count-1) + bytes([count])
    def swap(data):
        return b''.join(data[i:i+2][::-1] for i in range(0, len(data), 2))
    previous = secrets.token_bytes(8)
    result = bytearray(previous)
    for pos in range(0, len(clear), 8):
        block = bytes(a ^ b for a, b in zip(clear[pos:pos+8], previous))
        previous = swap(idea_encrypt(swap(block), key.encode()))
        result += previous
    return bytes(result)


def digest(data, rounds=100):
    for _ in range(rounds):
        data = hashlib.sha256(data).digest()
    return data


def aes(data, key, decrypt=False, padded=True):
    if not decrypt and padded:
        n = 16 - len(data) % 16
        data += bytes([n]) * n
    if not data or len(data) % 16:
        raise ValueError('Invalid AES block length')
    cipher = Cipher(algorithms.AES(key), modes.CBC(bytes(16)))
    ctx = cipher.decryptor() if decrypt else cipher.encryptor()
    result = ctx.update(data) + ctx.finalize()
    if decrypt and padded:
        n = result[-1]
        if not 1 <= n <= 16 or result[-n:] != bytes([n]) * n:
            raise ValueError('Invalid AES padding')
        result = result[:-n]
    return result


def pack_message(body, marker, timestamp, key):
    metadata = aes(struct.pack('<I', int(timestamp)) + marker.encode(), key)
    if len(metadata) > 127:
        raise ValueError('Metadata too long')
    return bytes([len(metadata)]) + metadata + aes(body, key)


def unpack_message(blob, key):
    if not blob or blob[0] > 127:
        raise ValueError('Invalid message header')
    size = blob[0]
    metadata = aes(blob[1:1+size], key, decrypt=True)
    if len(metadata) < 4:
        raise ValueError('Truncated message metadata')
    body = aes(blob[1+size:], key, decrypt=True)
    return body, metadata[4:].decode('utf-8', errors='replace'), struct.unpack('<I', metadata[:4])[0]


class AppletMetadata(HTMLParser):
    """Read the original applet declaration without reserializing the HTML."""
    def __init__(self, text):
        super().__init__()
        self.applet = None
        self.feed(text)
        if not self.applet or not self.applet.get('code'):
            raise ValueError('Selected homepage does not contain an applet')

    def handle_starttag(self, tag, attrs):
        if tag == 'applet' and self.applet is None:
            self.applet = dict(attrs)


def decode_html(data):
    # Several captures declare a legacy charset but are actually archived UTF-8.
    try:
        return data.decode('utf-8-sig')
    except UnicodeDecodeError:
        match = re.search(rb'charset\s*=\s*["\']?([\w-]+)', data[:4096], re.I)
        encoding = match[1].decode('ascii') if match else 'windows-1252'
        try:
            return data.decode(encoding, errors='replace')
        except LookupError:
            return data.decode('windows-1252', errors='replace')


def archive_request_key(url):
    """Match dump filenames, whose decoded query URLs lose repeated slashes."""
    return re.sub(r'/+', '/', urllib.parse.unquote(html.unescape(url)))


def external_resource_tools():
    script = DEFAULT_DUMP / 'download_external_resources.py'
    return runpy.run_path(str(script)) if script.is_file() else {}


class ArchiveReconstruction:
    inject_cheerpj = False

    def __init__(self, dump, port, password, site=None):
        self.site = site or SITES[DEFAULT_HOST]
        self.root = (dump / self.site.hostname).resolve()
        self.origin = 'http://127.0.0.1:' + str(port)
        self.password = password.encode('utf-8')
        self.files = {}
        if not self.root.is_dir():
            raise ValueError('Archive not found: ' + str(self.root))
        target = datetime.datetime.strptime(self.site.capture, '%Y%m%d%H%M%S')
        captures = [p for p in self.root.iterdir() if p.is_dir() and re.fullmatch(r'\d{14}', p.name)]
        captures.sort(key=lambda p: abs((datetime.datetime.strptime(p.name, '%Y%m%d%H%M%S') - target).total_seconds()))
        for capture in captures:
            for p in sorted(capture.rglob('*')):
                if p.is_file() and p.resolve().is_relative_to(self.root):
                    self.files.setdefault('/' + p.relative_to(capture).as_posix(), p)
        # Never substitute a later parked/hacked homepage for the chosen snapshot.
        self.homepage_url = '/' + self.site.homepage.split('/', 1)[1]
        self.files[self.homepage_url] = self.root / self.site.homepage
        self.archived_feeds = {}
        for url, file in self.files.items():
            if '?' not in url and not re.search(r'(?:rss|feed).*\.(?:html?|php|js|xml)$', url, re.I):
                continue
            data = file.read_bytes()
            # Wrapper pages containing only a remote script reference are not feeds.
            if not re.search(rb'class=["\']rss-(?:box|item)|<rss\b|<feed\b', data, re.I):
                continue
            key = archive_request_key(url)
            if '?' in key and key.endswith('/index.html'):
                key = key[:-len('/index.html')]
            self.archived_feeds[key] = file
            # These sites replaced their HTML feed wrappers with rendered PHP.
            # Reuse those later captures verbatim, with dates in provenance.
            if self.site.hostname in ('iranfootballsource.com', 'shadesofnews.com') and re.fullmatch(r'/rss-index\d+\.php', url):
                self.archived_feeds[url[:-4] + '.html'] = file
        self.dump = dump.resolve()
        self.external_tools = external_resource_tools()
        self.external_archive = None
        if self.external_tools:
            external_root = self.external_tools['default_external_root'](self.dump)
            load = self.external_tools['ExternalArchive']
            self.external_archive = load(external_root)
            if not self.external_archive.files:
                self.external_archive = load(self.dump)
        # Curated feed responses have pinned hashes and can come from a later capture.
        # Serve the saved script bytes with the original headline links intact.
        self.external_feed_sources = {}
        records = self.external_archive.files.values() if self.external_archive else ()
        for metadata in records:
            if metadata.get('site') != self.site.hostname or 'items' not in metadata:
                continue
            file = metadata['path']
            if hashlib.sha256(file.read_bytes()).hexdigest() != metadata['sha256']:
                raise ValueError('External feed does not match its recorded archive hash')
            original = urllib.parse.urlsplit(metadata['original_url'])
            key = archive_request_key('/' + original.netloc + original.path + '?' + original.query)
            self.archived_feeds.setdefault(key, file)
            if self.archived_feeds[key] == file:
                self.external_feed_sources[key] = metadata

    def rewrite_external(self, text, base, stamp, css=False, absolute=False, script=False):
        if not self.external_archive or not self.external_archive.records:
            return text

        def change(value, base, kind):
            url = self.external_tools['normalize'](value, base)
            if url and self.external_archive.nearest(url, stamp):
                prefix = self.origin if absolute else ''
                fragment = urllib.parse.urlsplit(value).fragment
                suffix = '#' + urllib.parse.quote(fragment, safe='%') if fragment else ''
                return prefix + '/__reconstruction__/archive/' + stamp + '/' + urllib.parse.quote(url, safe='') + suffix
            return None

        if css:
            return self.external_tools['css_urls'](text, base, change)
        if script:
            return self.external_tools['rewrite_written_markup'](text, base, change)
        return self.external_tools['Markup'](text, base, change).render()

    def rewrite_site_resource(self, text, file, css=False):
        stamp, relative = file.relative_to(self.root).as_posix().split('/', 1)
        base = 'http://' + self.site.hostname + '/' + relative
        return self.rewrite_external(text, base, stamp, css)

    def external_response(self, stamp, url):
        if not self.external_archive or not re.fullmatch(r'\d{14}', stamp):
            return None
        try:
            record = self.external_archive.nearest(url, stamp)
        except ValueError:
            return None
        if not record:
            return None
        data = record['path'].read_bytes()
        mime = record['content_type']
        if record.get('sha256') and hashlib.sha256(data).hexdigest() != record['sha256']:
            return None
        kind = mime.split(';', 1)[0].lower()
        javascript = kind in ('application/javascript', 'text/javascript')
        if javascript or kind in ('text/html', 'application/xhtml+xml', 'text/css', 'image/svg+xml'):
            text = self.external_tools['decode_text'](data, mime)
            rewritten = self.rewrite_external(text, record['original_url'], record['capture'],
                                              css=kind == 'text/css', script=javascript, absolute=True)
            if rewritten != text:
                data = rewritten.encode()
                mime = kind + '; charset=utf-8'
        return data, mime

    def feed_archive_report(self):
        result = []
        for url, file in sorted(self.archived_feeds.items()):
            metadata = self.external_feed_sources.get(url)
            source = str(file.relative_to(self.external_archive.root if metadata else self.root))
            capture = metadata['capture'] if metadata else file.relative_to(self.root).parts[0]
            row = dict(url=url, source=source, sha256=hashlib.sha256(file.read_bytes()).hexdigest(),
                       later_than_homepage=capture > self.site.capture,
                       served='Original response bytes, without rewriting headlines or links')
            if metadata:
                row.update(original_url=metadata['original_url'], wayback_url=metadata['wayback_url'],
                           capture=capture, storage=self.external_archive.root.name)
            result.append(row)
        return result

    def page(self, path):
        return self.presentation.page(path)


class MessagingReconstruction(ArchiveReconstruction):
    """Shared recovered RSA/challenge/AES protocol used by Java and JavaScript."""
    client_label = 'Applet'

    def __init__(self, dump, port, password, site=None):
        super().__init__(dump, port, password, site)
        self.secret = secrets.token_bytes(32)
        self.key = digest(self.password + self.secret)
        self.private_key = rsa.generate_private_key(public_exponent=65537, key_size=1024)
        self.lock = threading.Lock()
        self.sessions = {}
        self.messages = {}
        self.received = []
        self.next_id = 1
        self.reply_queued = False

    def session_token(self):
        return secrets.token_bytes(16)

    def outgoing_category(self, argument, marker):
        if argument == 255 and marker == self.config.get('MetaDataExt'):
            return 'metadata'
        if argument == 1:
            return 'text' if marker == self.config.get('MessageMarker', 'txt') else 'file'
        raise ValueError('Unsupported outgoing message type: ' + str(argument))

    def add_message(self, body, marker):
        if self.next_id > 127:
            raise ValueError('Demo mailbox full; restart the server to reset it')
        ident = self.next_id
        self.next_id += 1
        self.messages[ident] = (body, marker, int(time.time()))
        return ident

    def encode(self, data, encoding):
        if encoding in (ord('b'), ord('B')):
            return base64.b64encode(data)
        if encoding in (ord('h'), ord('H')):
            return data.hex().upper().encode()
        return data

    def decode(self, data, encoding):
        if encoding in (ord('b'), ord('B')):
            return base64.b64decode(data, validate=True)
        if encoding in (ord('h'), ord('H')):
            return bytes.fromhex(data.decode('ascii'))
        return data

    def protocol(self, raw):
        """journal.Av/O/Bv: preserve the original handshake and message encoding."""
        with self.lock:
            now = time.monotonic()
            self.sessions = {k: v for k, v in self.sessions.items() if now-v['touched'] < 3600}
            token = raw[:16]
            if token not in self.sessions:
                if not raw or raw[0] not in b'1bBhH':
                    raise ValueError('Unknown session')
                encoding = raw[0]
                packet = self.decode(raw[1:], encoding)
                if not packet or packet[0] != 128 or len(packet) < 145:
                    raise ValueError('Invalid RSA envelope')
                wrapping_key = self.private_key.decrypt(packet[1:129], padding.PKCS1v15())
                challenge_login = aes(packet[129:], wrapping_key, decrypt=True)
                mask = digest(self.secret[:min(len(self.password), len(self.secret))])
                expected = bytes(v ^ mask[i % len(mask)] for i, v in enumerate(self.password))
                if not secrets.compare_digest(challenge_login, expected):
                    raise ValueError('Bad login')
                token, challenge = self.session_token(), secrets.token_bytes(32)
                if len(self.sessions) >= 64:
                    raise ValueError('Too many demo sessions')
                self.sessions[token] = dict(challenge=challenge, encoding=encoding, authenticated=False, touched=now)
                print(self.client_label + ': login handshake accepted', flush=True)
                return self.encode(token + challenge, encoding)
            session = self.sessions[token]
            packet = self.decode(raw[16:], session['encoding'])
            expected = digest(session['challenge'] + self.secret)
            if len(packet) < 33 or not secrets.compare_digest(packet[:32], expected):
                raise ValueError('Invalid challenge response')
            session['challenge'] = secrets.token_bytes(32)
            session['touched'] = now
            if not session['authenticated']:
                if len(packet) != 33:
                    raise ValueError('Invalid session initialization')
                session['authenticated'] = True
                return self.encode(session['challenge'] + b'\x00', session['encoding'])
            if len(packet) < 34:
                raise ValueError('Missing command')
            op, argument = packet[32:34]
            item = None
            if op in (32, 18):  # first message, next message
                ids = sorted(i for i in self.messages if op == 32 or i > argument)
                if ids:
                    item = ids[0]
            elif op == 21:  # delete
                self.messages.pop(argument, None)
                print(self.client_label + ': deleted message ' + str(argument), flush=True)
            elif op == 37:  # send text, upload a file, or submit login metadata
                body, marker, timestamp = unpack_message(packet[35:], self.key)
                if len(body) > MAX_UPLOAD:
                    raise ValueError('File too large')
                # The applet's automatic environment report leaves Aj.type at -1.
                # Bb sends both text and files with type 1, distinguished only by
                # MessageMarker versus Cm's filename extension. An uploaded file
                # with that same extension is indistinguishable from text here.
                category = self.outgoing_category(argument, marker)
                self.received.append(dict(body=body, marker=marker, timestamp=timestamp,
                                          kind=argument, category=category))
                if len(self.received) > 100:
                    self.received.pop(0)
                if category == 'metadata':
                    print(f'{self.client_label}: acknowledged login metadata ({len(body)} bytes)', flush=True)
                else:
                    if category == 'text':
                        text = re.sub(r'[\x00-\x08\x0b-\x1f\x7f-\x9f]', '\ufffd', body.decode('utf-8', errors='replace'))
                        print(f'{self.client_label}: received text ({len(body)} bytes)\n{text}', flush=True)
                    else:
                        checksum = hashlib.sha256(body).hexdigest()
                        print(f'{self.client_label}: received file ({len(body)} bytes), SHA-256 {checksum}', flush=True)
                    if not self.reply_queued:
                        reply = DEMO_REPLY.encode() if category == 'text' else b'LOCAL DEMO REPLY\n\nFile received.'
                        reply_id = self.add_message(reply, 'dat')
                        self.reply_queued = True
                        print(f'{self.client_label}: queued demo reply {reply_id}', flush=True)
                # Acknowledge the send without piggybacking the queued message.
                # The next login retrieves it through the ordinary inbox commands.
            else:
                raise ValueError('Unsupported command: ' + str(op))
            response = session['challenge'] + bytes([op, 0])
            if item is not None:
                body, marker, timestamp = self.messages[item]
                response += bytes([item]) + pack_message(body, marker, timestamp, self.key)
            return self.encode(response, session['encoding'])


class Reconstruction(MessagingReconstruction):
    client_mime = 'application/java-archive'
    response_mime = 'application/octet-stream'
    runtime = RUNTIME

    def __init__(self, dump, port, password, site=None):
        super().__init__(dump, port, password, site)
        self.applet = AppletMetadata(decode_html(self.files[self.homepage_url].read_bytes())).applet
        self.applet_class = self.applet['code'].removesuffix('.class').replace('/', '.')
        self.applet_package = self.applet_class.rsplit('.', 1)[0]
        self.original = (self.root / self.site.client).read_bytes()
        with zipfile.ZipFile(io.BytesIO(self.original)) as archive:
            if self.applet_class.replace('.', '/') + '.class' not in archive.namelist():
                raise ValueError('The selected JAR does not contain the homepage applet class')
            self.resources = dict(binary_resources(archive.read(RESOURCE), RESOURCE_KEY))
        self.config = {k: v.decode('utf-8') for k, v in self.resources.items() if not k.startswith('res.')}
        self.endpoint = urllib.parse.urlsplit(self.config['ServerURL']).path
        if not self.endpoint.startswith('/'):
            raise ValueError('Invalid original endpoint')
        self.presentation = (LatinPresentation if self.site.presentation == 'latin' else Presentation)(self)
        self.jar = self.patch_jar()
        self.source_jar = None
        self.client_bytes = self.jar
        self.add_message(DEMO_INITIAL.encode(), 'dat')

    def patch_jar(self):
        numbers = self.private_key.public_key().public_numbers()
        def positive_hex(n):
            data = n.to_bytes((n.bit_length()+7)//8, 'big')
            if data[0] & 128:
                data = b'\x00' + data
            return data.hex().upper().encode()
        self.changes = {
            'ServerURL': (self.origin + self.endpoint).encode(),
            'KeyE': positive_hex(numbers.e),
            'KeyN': positive_hex(numbers.n),
            'EncSharedSecret': aes(self.secret, digest(self.password, 1), padded=False).hex().upper().encode(),
        }
        resources = dict(self.resources, **self.changes)
        encoded = encode_resources(resources)
        if dict(binary_resources(encoded, RESOURCE_KEY)) != resources:
            raise ValueError('Resource encode/decode validation failed')
        out = io.BytesIO()
        self.removed_signatures = []
        with zipfile.ZipFile(io.BytesIO(self.original)) as source, zipfile.ZipFile(out, 'w') as dest:
            for entry in source.infolist():
                if entry.filename.upper().startswith('META-INF/'):
                    self.removed_signatures.append(entry.filename)
                    continue
                data = encoded if entry.filename == RESOURCE else source.read(entry)
                dest.writestr(entry, data)
            dest.writestr('META-INF/MANIFEST.MF', 'Manifest-Version: 1.0\r\n\r\n')
        return out.getvalue()

    def use_source_jar(self, file):
        keys = ('EncodeType', 'MaxFileSize', 'MessageMarker')
        config = {key: self.resources[key] for key in keys if key in self.resources}
        config.update(self.changes)
        out = io.BytesIO()
        with zipfile.ZipFile(file) as source, zipfile.ZipFile(out, 'w') as dest:
            classes = ('Members', 'LegacyClient', 'JarResources')
            if any(f'users/help/{name}.class' not in source.namelist() for name in classes):
                raise ValueError('Expected a JAR built from ' + str(PASSWORD_ONLY_SOURCE_PROJECT))
            for entry in source.infolist():
                if entry.filename != RESOURCE and not entry.filename.upper().startswith('META-INF/'):
                    dest.writestr(entry, source.read(entry))
            dest.writestr(RESOURCE, encode_resources(config))
            dest.writestr('META-INF/MANIFEST.MF', 'Manifest-Version: 1.0\r\n\r\n')
        self.client_bytes = out.getvalue()
        self.source_jar = file.resolve()

    def report(self):
        with zipfile.ZipFile(io.BytesIO(self.original)) as old, zipfile.ZipFile(io.BytesIO(self.client_bytes)) as new:
            classes = [n for n in old.namelist() if n.endswith('.class')]
            served_files = set(new.namelist())
            identical = all(n in served_files and old.read(n) == new.read(n) for n in classes)
        client_notes = (
            ['The source-built applet has a separately designed mailbox interface.'] if self.source_jar else
            ['Automatic login metadata is acknowledged without an inbox reply.',
             'Original AWT file picker retained. An Applet Runner upload reached the server in a user-reported run.'])
        return dict(kind=self.site.kind, domain=self.site.hostname, source=str(self.root), homepage=self.site.homepage,
                    jar=self.site.client, original_sha256=hashlib.sha256(self.original).hexdigest(),
                    source_jar=str(self.source_jar) if self.source_jar else None,
                    served_jar_sha256=hashlib.sha256(self.client_bytes).hexdigest(),
                    applet_class=self.applet_class, applet_dimensions=[self.applet['width'], self.applet['height']],
                    original_endpoint=self.config['ServerURL'], local_endpoint=self.origin + self.endpoint,
                    original_transport_encoding=self.config.get('EncodeType'),
                    original_class_count=len(classes), all_class_bytes_identical=identical,
                    configuration_changes=list(self.changes),
                    removed_signature_metadata=[] if self.source_jar else self.removed_signatures,
                    static_paths=len(self.files), runtime=self.runtime if self.inject_cheerpj else None,
                    native_compatibility=('Java 8 AppletViewer uses the source-built applet directly.' if self.source_jar else
                                          'Separate temporary LiveConnect types; getWindow throws JSException for the original absent-browser fallback. No original classes changed.'),
                    feed_reconstruction=self.presentation.feed_report(),
                    notes=['Server and messages are local demo substitutes.'] + client_notes
                          + ['Assets come from several captures. No archived files are overwritten.']
                          + list(self.site.notes) + self.presentation.notes)

    def page(self, path):
        return self.presentation.page(path)



# Local protocol support: the separate Java helper reuses original client codecs.
# Its classes are never added to the browser/native client JAR.
# Verified renamed wire types, in order: request, image, content, effect, response.
# These bindings specialize only the new server helper, never the original JAR.
WIRE_TYPES = {
    'mynepalnews.com': ('irf.gnh.qec.wig.a', 'irf.gnh.qec.zu.vba.a', 'irf.gnh.qec.zu.c',
                        'irf.gnh.qec.ztx.c', 'irf.gnh.qec.er.a'),
    'half-court.net': ('timeout.whistle.ball.technical.a', 'timeout.whistle.ball.half.coach.a',
                       'timeout.whistle.ball.half.c', 'timeout.whistle.ball.elbow.c', 'timeout.whistle.ball.foul.a'),
    'echessnews.com': ('chess.game.faqs.new.a', 'chess.game.faqs.recent.top.a', 'chess.game.faqs.recent.c',
                      'chess.game.faqs.latest.c', 'chess.game.faqs.frequent.a'),
    'first-tee-golf.com': ('qi.yawy.defh.ad.a', 'qi.yawy.defh.yb.bkjp.a', 'qi.yawy.defh.yb.c',
                          'qi.yawy.defh.ih.c', 'qi.yawy.defh.oe.a'),
    'soldiersofsouthasia.com': ('tgwp.rti.xboi.jb.a', 'tgwp.rti.xboi.sbrz.dvm.a', 'tgwp.rti.xboi.sbrz.c',
                               'tgwp.rti.xboi.eg.c', 'tgwp.rti.xboi.yra.a'),
    # Scrolling: request, text descriptor, text content, scrolling effect, response.
    'inews-today.com': ('ftkj.xzb.bvw.fcsi.a', 'ftkj.xzb.bvw.yf.qbm.e', 'ftkj.xzb.bvw.yf.f',
                        'ftkj.xzb.bvw.pqnn.b', 'ftkj.xzb.bvw.swg.a'),
    'lajornadanow.com': ('sxdf.vufq.xurb.ibd.a', 'sxdf.vufq.xurb.rkir.ex.e', 'sxdf.vufq.xurb.rkir.f',
                         'sxdf.vufq.xurb.ne.b', 'sxdf.vufq.xurb.sw.a'),
    'turkishnewslinks.com': ('news.feeds.sports.cricket.a', 'news.feeds.sports.soccer.scores.e',
                             'news.feeds.sports.soccer.f', 'news.feeds.sports.vball.b', 'news.feeds.sports.bball.a'),
}


# The source build and the local server translate their reflection names
# through the same map. The archived applet classes retain their own names.
HALF_COURT_TYPES = json.loads((DISPLAY_SOURCE_PROJECT / 'half-court-types.json').read_text())


def slideshow_source(code, app):
    if not app.site.hold_activation:
        return code

    def renamed(match):
        name = match[0]
        mapped = HALF_COURT_TYPES.get(name.replace('/', '.'), name)
        return mapped.replace('.', '/') if '/' in name else mapped

    return re.sub(r'[abc](?:/[a-z]+)+|[abc](?:\.[a-z]+)+', renamed, code)


def receiver_class(type_map=None, kind='messages'):
    """Generate a small SERVER-ONLY adapter for the original message receiver.

    Equivalent source (javac rejects the original obfuscated return-type-only
    overloads inherited from these classes):

      class LocalSlideshowReceiver extends c.a.d.d.a.b {
        public LocalSlideshowReceiver(c.a.d.o list) { super(list); }
        protected a.a.g.a.a.d a() {
          return new a.a.g.a.a.d(new c.a.d.d.a.k(new c.a.d.g()));
        }
      }

    This uses the generic wire receiver without the client-only progress-window
    subclass. It neither edits nor replaces any original client class.
    The older applet lacks an action-list receiver. Its two server adapters
    wrap the archived action codec/list adapter and disable the checksum, as
    its original action sender does (also matching the newer action receiver).
    """
    u2 = lambda n: struct.pack('>H', n)
    u4 = lambda n: struct.pack('>I', n)
    pool = []

    def add(data):
        pool.append(data)
        return len(pool)

    def utf(value):
        if type_map:
            value = re.sub(r'[abc](?:/[a-z]+)+',
                           lambda match: type_map.get(match[0].replace('/', '.'), match[0]).replace('.', '/'), value)
        data = value.encode()
        return add(b'\x01' + u2(len(data)) + data)

    def cls(value):
        return add(b'\x07' + u2(utf(value)))

    def ref(owner, name, descriptor):
        return add(b'\x0a' + u2(cls(owner))
                   + u2(add(b'\x0c' + u2(utf(name)) + u2(utf(descriptor)))))

    name, parent, code = cls('LocalSlideshowReceiver'), cls('c/a/d/d/a/b'), utf('Code')
    init_name, init_desc = utf('<init>'), utf('(Lc/a/d/o;)V')
    init = b'\x2a\x2b\xb7' + u2(ref('c/a/d/d/a/b', '<init>', '(Lc/a/d/o;)V')) + b'\xb1'
    method_name, method_desc = utf('a'), utf('()La/a/g/a/a/d;')
    receiver, message, data = cls('a/a/g/a/a/d'), cls('c/a/d/d/a/k'), cls('c/a/d/g')
    body = (b'\xbb' + u2(receiver) + b'\x59\xbb' + u2(message) + b'\x59\xbb' + u2(data)
            + b'\x59\xb7' + u2(ref('c/a/d/g', '<init>', '()V'))
            + b'\xb7' + u2(ref('c/a/d/d/a/k', '<init>', '(Lc/a/d/g;)V'))
            + b'\xb7' + u2(ref('a/a/g/a/a/d', '<init>', '(La/a/g/a/a/g;)V')) + b'\xb0')

    if kind == 'action-object':
        name, parent = cls('LocalActionObject'), cls('a/a/g/a/a/d')
        init_desc = utf('(La/a/g/a/a/g;)V')
        init = b'\x2a\x2b\xb7' + u2(ref('a/a/g/a/a/d', '<init>', '(La/a/g/a/a/g;)V')) + b'\xb1'
        method_name, method_desc, body = utf('e'), utf('()Z'), b'\x03\xac'
    elif kind == 'action-list':
        name, parent = cls('LocalActionReceiver'), cls('a/a/g/a/a/b')
        init_desc = utf('(Lc/a/d/l;)V')
        init = (b'\x2a\xbb' + u2(cls('c/a/d/d/b/e')) + b'\x59\x2b\xb7'
                + u2(ref('c/a/d/d/b/e', '<init>', '(Lc/a/d/l;)V'))
                + b'\xb7' + u2(ref('a/a/g/a/a/b', '<init>', '(La/a/g/a/a/i;)V')) + b'\xb1')
        body = (b'\xbb' + u2(cls('LocalActionObject')) + b'\x59\xbb' + u2(cls('c/a/d/d/b/f'))
                + b'\x59\xbb' + u2(cls('c/a/d/k')) + b'\x59\x02\xbb' + u2(data)
                + b'\x59\xb7' + u2(ref('c/a/d/g', '<init>', '()V'))
                + b'\xb7' + u2(ref('c/a/d/k', '<init>', '(ILc/a/d/g;)V'))
                + b'\xb7' + u2(ref('c/a/d/d/b/f', '<init>', '(Lc/a/d/k;)V'))
                + b'\xb7' + u2(ref('LocalActionObject', '<init>', '(La/a/g/a/a/g;)V')) + b'\xb0')

    def method(access, name, descriptor, body, stack, locals_):
        data = u2(stack) + u2(locals_) + u4(len(body)) + body + u2(0) + u2(0)
        return u2(access) + u2(name) + u2(descriptor) + u2(1) + u2(code) + u4(len(data)) + data

    return (b'\xca\xfe\xba\xbe' + u2(0) + u2(49) + u2(len(pool)+1) + b''.join(pool)
            + u2(0x21) + u2(name) + u2(parent) + u2(0) + u2(0) + u2(2)
            + method(1, init_name, init_desc, init, 4, 2)
            + method(4, method_name, method_desc, body, 9, 1) + u2(0))


class SlideshowBackend:
    def __init__(self, app):
        self.app = app
        self.temp = None
        self.process = None
        self.lock = threading.Lock()

    def prepare(self):
        if self.temp:
            return
        viewer = shutil.which('appletviewer')
        if not viewer:
            raise ValueError('The slideshow server needs a Java 8 JDK with appletviewer, java and javac.')
        self.java_bin = Path(viewer).resolve().parent
        self.temp = tempfile.TemporaryDirectory(prefix='slideshow-reconstruction-')
        self.root = Path(self.temp.name)
        try:
            (self.root / 'client.jar').write_bytes(self.app.jar)
            for photo in self.app.extra_slides:
                shutil.copyfile(photo, self.root / photo.name)
            (self.root / 'LocalSlideshowReceiver.class').write_bytes(receiver_class(HALF_COURT_TYPES if self.app.site.hold_activation else None))
            if self.app.site.hold_activation:
                for name, kind in [('LocalActionObject', 'action-object'), ('LocalActionReceiver', 'action-list')]:
                    (self.root / (name + '.class')).write_bytes(receiver_class(HALF_COURT_TYPES, kind))
            source = Path(__file__).with_name('reconstruction') / 'SlideshowServer.java'
            code = slideshow_source(source.read_text(), self.app)
            for original, local in zip(WIRE_TYPES['mynepalnews.com'], WIRE_TYPES[self.app.site.hostname]):
                code = code.replace('"' + original + '"', '"' + local + '"')
            code = code.replace('"/nepal2.jpg"', json.dumps(self.app.background))
            (self.root / source.name).write_text(code)
            subprocess.run([str(self.java_bin / 'javac'), '-d', str(self.root), str(self.root / source.name)],
                           check=True, timeout=30)
        except BaseException:
            self.close()
            raise

    def command(self, mode):
        self.prepare()
        return [str(self.java_bin / 'java'), '-Xmx256m', '-Djava.awt.headless=true',
                '-Djdk.serialFilter=maxdepth=20;maxrefs=10000;maxarray=8388608;maxbytes=8388608',
                '-Dslideshow.activation=' + self.app.activation_sequence,
                '-Dslideshow.hold=' + str(self.app.site.hold_activation).lower(),
                '-Dslideshow.scrolling=' + str(self.app.scrolling).lower(),
                '-Dslideshow.scrollSpeed=' + str(self.app.scroll_speed),
                '-Dslideshow.intervalMs=' + str(self.app.slideshow_interval_ms),
                '-Dslideshow.texts=' + '\n'.join(self.app.scroll_texts),
                '-Dslideshow.extraSlides=' + ','.join('/' + photo.name for photo in self.app.extra_slides),
                '-cp', str(self.root) + ':' + str(self.root / 'client.jar'), 'SlideshowServer', mode]

    def read_reply(self):
        if not select.select([self.process.stdout], [], [], 20)[0]:
            self.close()
            raise ValueError('Slideshow server response timed out')
        reply = self.process.stdout.readline().rstrip('\n')
        if not reply:
            self.close()
            raise ValueError('Slideshow server exited')
        if reply.startswith('ERR '):
            raise ValueError(base64.b64decode(reply[4:]).decode(errors='replace'))
        return reply

    def startup_input(self):
        return ''.join(base64.b64encode(value).decode() + '\n' for value in
                       (self.app.password, self.app.demo_initial.encode(), self.app.demo_reply.encode(),
                        self.app.demo_second_initial.encode(), self.app.demo_second_reply.encode()))

    def start(self):
        if self.process:
            return
        self.process = subprocess.Popen(self.command('serve'), stdin=subprocess.PIPE,
                                        stdout=subprocess.PIPE, text=True, bufsize=1)
        self.process.stdin.write(self.startup_input())
        self.process.stdin.flush()
        try:
            if self.read_reply() != 'READY':
                raise ValueError('Unexpected slideshow server greeting')
        except BaseException:
            self.close()
            raise

    def exchange(self, raw):
        with self.lock:
            self.start()
            self.process.stdin.write(base64.b64encode(raw).decode() + '\n')
            self.process.stdin.flush()
            reply = self.read_reply()
            if not reply.startswith('OK '):
                raise ValueError('Unexpected slideshow server response')
            return base64.b64decode(reply[3:], validate=True)

    def reset(self):
        """Reset the active connection for a new page; retain per-user mailboxes."""
        with self.lock:
            if self.process:
                self.process.stdin.write('RESET\n')
                self.process.stdin.flush()
                if self.read_reply() != 'READY':
                    raise ValueError('Unexpected slideshow server reset response')

    def self_test(self):
        try:
            command = self.command('test')
            request = self.root / 'test-request.bin'
            command.insert(1, '-Dslideshow.test.request=' + str(request))
            result = subprocess.run(command, input=self.startup_input(), check=True, capture_output=True,
                                    text=True, timeout=60)
            print(result.stdout, end='')
            reply = self.exchange(request.read_bytes())
            self.reset()
            validation = subprocess.run(self.command('validate-response'),
                                        input=base64.b64encode(reply).decode() + '\n',
                                        check=True, capture_output=True, text=True, timeout=20)
            print(validation.stdout, end='')
            return result.stdout + validation.stdout
        except subprocess.CalledProcessError as error:
            raise ValueError(error.stdout + error.stderr) from error
        finally:
            self.close()

    def close(self):
        if self.process:
            self.process.terminate()
            try:
                self.process.wait(timeout=5)
            except subprocess.TimeoutExpired:
                self.process.kill()
                self.process.wait()
            self.process.stdin.close()
            self.process.stdout.close()
            self.process = None
        if self.temp:
            self.temp.cleanup()
            self.temp = None


class SlideshowReconstruction(ArchiveReconstruction):
    demo_usernames = ('smiley', 'karla')
    """Original slideshow/scrolling applet, with a separate local protocol counterpart."""
    client_mime = 'application/java-archive'
    response_mime = 'application/octet-stream'
    runtime = RUNTIME
    resource_path = 'c/a/e/Applet.properties'
    resource_key = '7A5DAEC4029C91ED3B3C1EB328552E60'

    def __init__(self, dump, port, password, site):
        super().__init__(dump, port, password, site)
        self.original = (self.root / site.client).read_bytes()
        if hashlib.sha256(self.original).hexdigest() != site.client_sha256:
            raise ValueError('The slideshow reconstruction requires the verified archived JAR')
        self.applet = AppletMetadata(decode_html(self.files[self.homepage_url].read_bytes())).applet
        self.applet_class = self.applet['code'].removesuffix('.class').replace('/', '.')
        if site.hold_activation:
            self.resource_path = 'b/b/d/Applet.properties'
        with zipfile.ZipFile(io.BytesIO(self.original)) as archive:
            self.resources = dict(binary_resources(archive.read(self.resource_path), self.resource_key))
            for name in WIRE_TYPES[site.hostname]:
                if name.replace('.', '/') + '.class' not in archive.namelist():
                    raise ValueError('Missing verified slideshow wire class: ' + name)
        self.config = {k: v.decode('utf-8') for k, v in self.resources.items() if not k.startswith('img.')}
        self.endpoint = self.config['Connection.Servlet.Path']
        self.clear_key, self.activation_sequence, self.trigger_key, original_hash = activation_settings(site)
        if (self.clear_key != self.config['KeyListener.ClearKey'] or
                self.trigger_key != self.config['KeyListener.StickyKey']):
            raise ValueError('Listed activation keys do not match the archived configuration')
        configured = self.config['KeyListener.KeySequence']
        if original_hash and original_hash != (configured if site.hold_activation else configured.upper()):
            raise ValueError('Recovered activation hash does not match the archived JAR')
        self.activation_source = 'recovered_original' if original_hash else 'demo_fallback'
        self.activation = (f'Click the applet, type {self.clear_key} {self.activation_sequence} {self.trigger_key}.')
        if site.hold_activation:
            self.activation = (f'Click the applet, press and release {self.clear_key}, hold {self.trigger_key}, '
                               f'type {self.activation_sequence}, then release {self.trigger_key}.')
        self.scrolling = site.kind == 'jar-scrolling'
        self.slideshow_interval_ms = 4000  # Archived slideshow effect constructor default.
        # The client sends protocol bytes with content refills. Its 2.5 px/s
        # constructor default can delay login until several ticker items pass.
        self.scroll_speed = 64  # Local demo: 40 pixels/s; original effect units are pixels per 1600 ms.
        if self.config['Content.Type'] != ('Scrolling' if self.scrolling else 'SlideShow'):
            raise ValueError('Configured display does not match the selected subtype')
        self.background = self.config.get('Applet.BackgroundImageLocation', '')
        slide_directory = Path(__file__).with_name('reconstruction') / 'sites' / site.hostname
        self.extra_slides = tuple(slide_directory / name for name in NEPAL_SLIDES) if site.hostname == 'mynepalnews.com' else ()
        self.scroll_texts = tuple(f'{headline} — {DISPLAY_DISCLAIMER}'
                                  for headline in SCROLLING_HEADLINES.get(site.hostname, ()))
        if self.scrolling:
            self.cover_note = 'Three rotating demo headlines with reconstruction labels, rendered by the original applet.'
        elif self.extra_slides:
            self.cover_note = 'The slideshow cycles through the original mountain image and two labeled reconstruction photos.'
        else:
            self.cover_note = 'The original background is held fixed. Historical slides are unavailable.'
        with zipfile.ZipFile(io.BytesIO(self.original)) as archive:
            if self.background:
                archive.getinfo(self.background.lstrip('/'))
        self.changes = {}
        if original_hash:
            self.client_bytes = self.jar = self.original
        else:
            sequence = self.activation_sequence.encode('utf-16-be').hex().encode('ascii')
            self.changes['KeyListener.KeySequence'] = hashlib.sha256(sequence).hexdigest().upper().encode()
            patched = dict(self.resources, **self.changes)
            encoded = encode_resources(patched, self.resource_key)
            if dict(binary_resources(encoded, self.resource_key)) != patched:
                raise ValueError('Slideshow configuration round trip failed')
            output = io.BytesIO()
            with zipfile.ZipFile(io.BytesIO(self.original)) as source, zipfile.ZipFile(output, 'w') as dest:
                for entry in source.infolist():
                    dest.writestr(entry, encoded if entry.filename == self.resource_path else source.read(entry))
            self.client_bytes = self.jar = output.getvalue()
        self.source_jar = None
        self.presentation = SlideshowPresentation(self)
        self.demo_initial, self.demo_reply = DEMO_INITIAL, DEMO_REPLY
        self.demo_second_initial, self.demo_second_reply = DEMO_SECOND_INITIAL, DEMO_SECOND_REPLY
        self.backend = SlideshowBackend(self)

    def use_source_jar(self, file):
        output = io.BytesIO()
        entry_point = self.applet_class.replace('.', '/') + '.class'
        with (zipfile.ZipFile(file) as source, zipfile.ZipFile(io.BytesIO(self.jar)) as archived,
              zipfile.ZipFile(output, 'w') as served):
            required = (entry_point, 'replay/display/SourceApplet.class', 'replay/display/DisplayMailbox.class')
            if any(name not in source.namelist() for name in required):
                raise ValueError(f'Expected a display source build for {self.site.hostname}')
            for info in source.infolist():
                data = archived.read(self.resource_path) if info.filename == self.resource_path else source.read(info)
                served.writestr(info, data)
            if self.resource_path not in source.namelist():
                served.writestr(self.resource_path, archived.read(self.resource_path))
        self.client_bytes = output.getvalue()
        self.source_jar = file.resolve()

    def protocol(self, raw):
        return self.backend.exchange(raw)

    def close(self):
        self.backend.close()

    def report(self):
        with zipfile.ZipFile(io.BytesIO(self.original)) as old, zipfile.ZipFile(io.BytesIO(self.client_bytes)) as served:
            classes = {n for n in old.namelist() if n.endswith('.class')}
            common = set(old.namelist()) & set(served.namelist())
            changed = sorted(n for n in common if old.read(n) != served.read(n))
            removed = sorted(set(old.namelist()) - common)
            added = sorted(set(served.namelist()) - common)
            retained_classes = classes.intersection(common).difference(changed)
        source_notes = [
            'New applet, display scheduler and mailbox window. Archived runtime classes retain the wire format, '
            'authentication, keyboard activation and cryptography.',
            'Display polling advances during mailbox transfers. This can skip queued cover items during activity.',
            'The rebuilt applet supports text messaging. Local accounts and conversation are demo replacements.',
        ]
        return dict(kind=self.site.kind, domain=self.site.hostname, homepage=self.site.homepage,
                    source_jar=str(self.source_jar) if self.source_jar else None,
                    retained_archived_class_count=len(retained_classes),
                    served_sha256=hashlib.sha256(self.client_bytes).hexdigest(),
                    jar=self.site.client, original_sha256=hashlib.sha256(self.original).hexdigest(),
                    applet_class=self.applet_class, applet_dimensions=[self.applet['width'], self.applet['height']],
                    original_class_count=len(classes), all_class_bytes_identical=retained_classes == classes,
                    changed_entries=changed, removed_entries=removed, added_entries=added,
                    configuration_changes=list(self.changes),
                    original_endpoint=self.endpoint, local_endpoint=self.origin + self.endpoint,
                    original_background=self.background.lstrip('/') or None, original_icons_preserved=not self.source_jar,
                    cover_display=self.cover_note.replace('original applet', 'rebuilt applet') if self.source_jar else self.cover_note,
                    reconstruction_slides=[photo.relative_to(Path(__file__).parent).as_posix() for photo in self.extra_slides],
                    slideshow_interval_seconds=None if self.scrolling else self.slideshow_interval_ms / 1000,
                    scrolling_text=self.scroll_texts[0] if self.scroll_texts else None,
                    scrolling_texts=list(self.scroll_texts),
                    scrolling_speed_pixels_per_second=self.scroll_speed / 1.6 if self.scrolling else None,
                    scrolling_speed_source=('original constructor default' if self.scroll_speed == 4 else
                                            'local demo setting; historical server speed unknown') if self.scrolling else None,
                    activation=self.activation, activation_source=self.activation_source,
                    entire_jar_identical=self.client_bytes == self.original,
                    demo_conversation='Distinct opening per account; one scripted reply after the first upload, delivered on the next login.',
                    demo_usernames=list(self.demo_usernames), runtime=self.runtime if self.inject_cheerpj else None,
                    feed_reconstruction=self.presentation.feed_report(),
                    native_linkage=dict(checked_classes=len(classes)-2,
                                        optional_excluded=[slideshow_source(n, self) for n in ('a.a.h.d', 'a.a.h.e')],
                                        reason='Unused Log4j adapters require an absent optional dependency.') if not self.source_jar else None,
                    notes=source_notes if self.source_jar else [
                        f'All {len(classes)} original classes, icons and background bytes are preserved.',
                        ('Original activation sequence recovered; the entire archived JAR is served unchanged.'
                         if not self.changes else 'Original activation sequence unrecovered; only its hash is replaced for the demo fallback.'),
                        'The original applet derives the servlet host/port from its local codebase.',
                        self.cover_note,
                        'Original login, inbox/outbox, editor, transfer/status windows and crypto are used.',
                        'Local server and simulated conversation are substitutes, not recovered server content.',
                        'One active applet per server. Reload resets the connection; per-user mailboxes survive in memory until the helper/server stops.',
                        'Java 8 JDK required for the local server, including with the browser runtime.',
                        'Native mode adds a temporary URLPermission for images inside this exact local JAR.',
                        'Protocol and keyboard tests are headless; desktop/browser rendering is not verified here.',
                        'Read/delete actions, next-login replies and mailbox isolation are tested with original client classes. Attachments are not tested.'])


def source_display_self_test(app):
    source = DISPLAY_SOURCE_PROJECT / 'test' / 'SourceDisplayCheck.java'
    app.backend.prepare()
    root = app.backend.root
    jar = root / 'rebuilt.jar'
    jar.write_bytes(app.client_bytes)
    java = app.backend.java_bin
    classpath = os.pathsep.join(map(str, (root, jar)))
    try:
        subprocess.run([str(java / 'javac'), '-encoding', 'UTF-8', '-cp', str(jar), '-d', str(root), str(source)],
                       check=True, timeout=30)
        command = [str(java / 'java'), '-Djava.awt.headless=true', '-cp', classpath,
                   'replay.display.SourceDisplayCheck', app.password.decode(), app.activation_sequence,
                   app.demo_initial, app.demo_reply, 'scroll' if app.scrolling else 'slides',
                   str(3 if app.scrolling else 1 + len(app.extra_slides)),
                   app.applet['width'], app.applet['height']]
        with subprocess.Popen(command, stdin=subprocess.PIPE, stdout=subprocess.PIPE,
                              text=True, bufsize=1) as process:
            timer = threading.Timer(60, process.kill)
            timer.start()
            try:
                for line in process.stdout:
                    if line.startswith('REQUEST '):
                        response = base64.b64encode(app.protocol(base64.b64decode(line[8:]))).decode()
                    elif line == 'RESET\n':
                        app.backend.reset()
                        response = 'READY'
                    else:
                        print(line, end='')
                        continue
                    process.stdin.write(response + '\n')
                    process.stdin.flush()
                if process.wait() != 0:
                    raise ValueError('Rebuilt display client failed its offline integration test')
            finally:
                timer.cancel()
                if process.poll() is None:
                    process.kill()
    finally:
        app.close()


def slideshow_self_test(app):
    if app.source_jar:
        source_display_self_test(app)
        return
    report = app.report()
    expected_changes = [app.resource_path] if app.changes else []
    if (not report['all_class_bytes_identical'] or report['changed_entries'] != expected_changes
            or (not app.changes and not report['entire_jar_identical'])):
        raise AssertionError('Unexpected slideshow client changes')
    app.backend.self_test()
    print('PASS: ' + ('all client classes and original artwork unchanged; only the fallback activation hash replaced.'
                     if app.changes else 'entire archived JAR unchanged; recovered original activation accepted.'))


class WeatherReconstruction(ArchiveReconstruction):
    """Original weather applet; Python counterpart to Server and a$6, no JAR edits."""
    client_mime = 'application/java-archive'
    response_mime = 'application/xml; charset=utf-8'
    runtime = RUNTIME
    # These station identifiers exist in both clients' ConstantsLoc tables.
    # Selection and readings below are invented, not historical observations.
    initial_cities = (('VNKT', 'Kathmandu Airport, Nepal'),
                      ('EGLL', 'London, United Kingdom'), ('KLGA', 'New York, NY'))
    readings = dict(reportdate='DEMO', wind=': NE 5 km/h', visibility=': 10 km',
                    sky=': partly cloudy', condition=': dry', temperature=': 18 C',
                    dewpoint=': 8 C', humidity=': 52%', pressure=': 1015 hPa',
                    imagename='weather-demo.gif')

    def __init__(self, dump, port, password, site):
        super().__init__(dump, port, password, site)
        self.applet = AppletMetadata(decode_html(self.files[self.homepage_url].read_bytes())).applet
        self.applet_class = self.applet['code'].removesuffix('.class').replace('/', '.')
        self.original = (self.root / site.client).read_bytes()
        if hashlib.sha256(self.original).hexdigest() != site.client_sha256:
            raise ValueError('Unrecognized weather JAR; refusing version-specific reconstruction')
        self.client_bytes = self.original
        self.endpoint = '/cgi-bin/' + ('infoweather.cgi' if self.applet_class == 'InfoWeatherApplet' else 'regionweather.cgi')
        self.cities = list(self.initial_cities)
        self.lock = threading.Lock()
        self.requests = []
        self.weather_controls = False
        self.presentation = WeatherPresentation(self)

    def city_list(self):
        return '|'.join(code + ':' + name for code, name in self.cities)

    def protocol(self, data):
        # Original formatQuery emits this XML-looking envelope; its SQL body
        # is Base64 random ASCII padding, never SQL to execute. No XML parser,
        # external entities, database or remote weather service is involved.
        match = re.fullmatch(rb'\s*<\?xml version="1\.0" \?>\s*'
                             rb"<Cities n='([0-9]+)' id='([0-9]{4})'>\s*"
                             rb'<SQL q="([^"<>]{1,4096})">([A-Za-z0-9+/=;\s]*)</SQL>\s*</Cities>\s*', data)
        if not match or int(match[1]) > 5:
            raise ValueError('Invalid weather envelope')
        # a$6.a(false) uses even parity for the middle two ID digits.
        if (int(chr(match[2][1])) + int(chr(match[2][2]))) % 2:
            raise ValueError('Unsupported weather envelope ID')
        command = match[3].decode('ascii').split(',')
        if len(command) != 4:
            raise ValueError('Invalid weather command')
        action, first, second, third = command
        with self.lock:
            if action == 'gc' and command[1:] == ['na', 'na', 'na']:
                reply = self.city_list()
            elif action == 'gw' and third == 'na':
                reply = (self.readings.get(second, 'N/A') if first in dict(self.cities)
                         else 'ERROR Unknown demo station')
            elif action == 'sc':
                codes, names, countries = first.split(':'), second.split(':'), third.split(':')
                if not 1 <= len(codes) <= 5 or not len(codes) == len(names) == len(countries):
                    raise ValueError('Invalid city selection')
                if any(not re.fullmatch(r'[A-Z0-9]{4}', code) for code in codes):
                    raise ValueError('Invalid station identifier')
                if any(not value or len(value) > 100 or any(c in value for c in '|<>\r\n')
                       for value in names + countries):
                    raise ValueError('Invalid city label')
                # Original Customize passes rightCode, right, rightCountry.
                self.cities = [(code, name + ', ' + country)
                               for code, name, country in zip(codes, names, countries)]
                reply = self.city_list()
            else:
                raise ValueError('Unsupported weather command')
            self.requests.append(dict(command=command, padding_bytes=len(match[4]), reply=reply))
            del self.requests[:-100]
        encoded = base64.b64encode(reply.encode('utf-8'))
        # formatOutput extracts double-quoted attributes of Reply elements,
        # concatenates them, decodes standard Base64 and returns a String.
        return b'<?xml version="1.0" ?>\n<Cities><Reply value="' + encoded + b'" /></Cities>\n'

    def standalone_page(self, controls=False, native=False):
        width, height = ('260', '130') if controls else (self.applet['width'], self.applet['height'])
        inject = self.inject_cheerpj and not native
        tag = 'cheerpj-applet' if inject else 'applet'
        params = '<param name="CUST" value="true">' if controls else ''
        return ('<!doctype html><html><head><meta charset="utf-8"><title>Weather reconstruction</title></head><body>'
                '<p>Made-up artwork and weather readings. Original Java applet.</p>'
                + ('<p>Enlarged demo view; original CUST option enabled. Click Cities.</p>' if controls else '')
                + '<' + tag + ' codebase="/" archive="' + self.site.client_url + '" code="' + self.applet_class
                + '" width="' + width + '" height="' + height + '" style="display:inline-block;width:' + width
                + 'px;height:' + height + 'px">' + params + '</' + tag + '>'
                + (JAVA_START if inject else '') + '</body></html>').encode()

    def report(self):
        return dict(kind=self.site.kind, domain=self.site.hostname, homepage=self.site.homepage,
                    jar=self.site.client, original_sha256=hashlib.sha256(self.original).hexdigest(),
                    entire_jar_identical=self.client_bytes == self.original, all_class_bytes_identical=True,
                    original_class_count=21, applet_class=self.applet_class,
                    applet_dimensions=[self.applet['width'], self.applet['height']], endpoint=self.endpoint,
                    original_commands=['gc (getCities)', 'gw (getWeather)', 'sc (setCities)'],
                    messaging_recovered=False, weather_controls=self.weather_controls,
                    controls_override='Optional CUST=true and 260x130 standalone view; defaults are unchanged on the archived page.',
                    artwork=self.presentation.artwork_report(), readings=self.readings, cities=list(self.cities),
                    requests=list(self.requests), feed_reconstruction=self.presentation.feed_report(),
                    notes=['All weather readings and initial city selection are made up.',
                           'Artwork is newly drawn and labeled, not recovered historical artwork.',
                           'City selection persists in process memory; shared by applets on this server.',
                           'Original field/city rotation and timing are unchanged.',
                           'No recovered login, inbox, dynamic class loader or object-stream hook in these 21 classes.',
                           'Original protocol and painter tested offline; desktop/browser appearance requires verification.'])


class MeterReconstruction(ArchiveReconstruction):
    """Original meter UI and transport; replace only the nested config's URL."""
    # The archived HostHook strings identify an interface. The recovered request
    # and response paths exercise throughput measurement.
    client_mime = 'application/java-archive'
    response_mime = 'application/octet-stream'
    runtime = RUNTIME

    def __init__(self, dump, port, password, site):
        super().__init__(dump, port, password, site)
        self.applet = AppletMetadata(decode_html(self.files[self.homepage_url].read_bytes())).applet
        self.applet_class = self.applet['code'].removesuffix('.class').replace('/', '.')
        self.original = (self.root / site.client).read_bytes()
        if hashlib.sha256(self.original).hexdigest() != site.client_sha256:
            raise ValueError('Unrecognized bandwidth-meter JAR; refusing version-specific reconstruction')
        self.requests = []
        self.meter_delay = 0.12
        with zipfile.ZipFile(io.BytesIO(self.original)) as archive:
            entries = [n for n in archive.namelist() if n.endswith('/bw.properties')]
            if len(entries) != 1:
                raise ValueError('Expected one ZIP-packed bandwidth-meter configuration')
            self.resource_path = entries[0]
            with zipfile.ZipFile(io.BytesIO(archive.read(self.resource_path))) as inner:
                if len(inner.namelist()) != 1:
                    raise ValueError('Unexpected meter configuration members')
                self.member = inner.namelist()[0]
                self.properties = inner.read(self.member)
                self.config = dict(re.findall(r'^\s*([^#\s=]+)\s*=\s*(.*?)\s*$',
                                             self.properties.decode('ascii'), re.M))
                self.endpoint = urllib.parse.urlsplit(self.config['URL']).path
                if not self.endpoint.startswith('/cgi-bin/'):
                    raise ValueError('Unexpected meter endpoint')
                patched = re.sub(rb'(?m)^(URL\s*=\s*)[^\r\n]+',
                                 lambda m: m[1] + (self.origin + self.endpoint).encode(), self.properties)
                packed = io.BytesIO()
                with zipfile.ZipFile(packed, 'w') as dest:
                    for info in inner.infolist():
                        dest.writestr(info, patched if info.filename == self.member else inner.read(info))
            result = io.BytesIO()
            with zipfile.ZipFile(result, 'w') as dest:
                for info in archive.infolist():
                    dest.writestr(info, packed.getvalue() if info.filename == self.resource_path else archive.read(info))
        self.client_bytes = result.getvalue()
        self.presentation = Presentation(self)

    def protocol(self, data):
        # This envelope wraps arbitrary binary bytes; do NOT parse it as XML.
        request = re.match(rb'<THRUPUT><REQUEST value="([0-9]+)">([0-9]+)</REQUEST>'
                           rb'<TIME>[^<]{1,80}</TIME><BYTES size="([0-9]+)">', data)
        tail = b'</BYTES></THRUPUT>'
        if not request or not data.endswith(tail):
            raise ValueError('Invalid bandwidth-meter envelope')
        receive, sequence, send = map(int, request.groups())
        payload = data[request.end():-len(tail)]
        if send != len(payload) or not 0 <= receive <= 256 * 1024 or send > 256 * 1024:
            raise ValueError('Invalid bandwidth-meter byte counts')
        # A reproducible local response, never a recovered historical server reply.
        response = bytes(range(256)) * (receive // 256) + bytes(range(receive % 256))
        record = dict(sequence=sequence, sent=send, received=len(response),
                      upload_sha256=hashlib.sha256(payload).hexdigest(),
                      sample_hex=payload[:32].hex(), time=time.time())
        self.requests.append(record)
        del self.requests[:-100]
        time.sleep(self.meter_delay)
        print('METER: request ' + str(sequence) + ', uploaded ' + str(send)
              + ', downloaded ' + str(len(response)) + ' bytes', flush=True)
        return response

    def report(self):
        with zipfile.ZipFile(io.BytesIO(self.original)) as old, zipfile.ZipFile(io.BytesIO(self.client_bytes)) as new:
            classes = [n for n in old.namelist() if n.endswith('.class')]
            identical = all(old.read(n) == new.read(n) for n in classes)
            with zipfile.ZipFile(io.BytesIO(new.read(self.resource_path))) as packed:
                reverted = packed.read(self.member).replace((self.origin + self.endpoint).encode(), self.config['URL'].encode())
        return dict(kind='meter', domain=self.site.hostname, homepage=self.site.homepage,
                    jar=self.site.client, original_sha256=hashlib.sha256(self.original).hexdigest(),
                    original_class_count=len(classes), all_class_bytes_identical=identical,
                    configuration_changes=['URL'], configuration_reversible=reverted == self.properties,
                    original_endpoint=self.config['URL'], local_endpoint=self.origin + self.endpoint,
                    applet_class=self.applet_class, applet_dimensions=[self.applet['width'], self.applet['height']],
                    original_meter_settings=self.config, response_delay_seconds=self.meter_delay,
                    requests=list(self.requests), runtime=self.runtime if self.inject_cheerpj else None,
                    static_paths=len(self.files),
                    feed_reconstruction=self.presentation.feed_report(),
                    notes=['Original Java meter display and traffic generation; only the configured URL is patched.',
                           'The displayed rate measures local traffic including an intentional server delay; not Internet speed.',
                           'Server returns deterministic test bytes; its historical response was not recovered.',
                           'The meter has a bidirectional ObjectInput/ObjectOutput HostHook for payload transport.',
                           'No login or inbox was recovered in these 21-class meter JARs. No missing payload is invented.',
                           'All archived files remain unchanged.'] + list(self.site.notes))


class JavaScriptReconstruction(MessagingReconstruction):
    client_label = 'JavaScript'
    client_mime = 'application/javascript; charset=utf-8'
    response_mime = 'text/plain; charset=utf-8'
    runtime = None
    # JS login artwork varies by site. Ciro spotted a visual match for the lock
    # from latinamericanewsbeat.com's today.jar in the Wahid Futbol screenshot:
    # http://cqcounter.com/whois/www/wahidfutbol.com.html
    shared_icon_members = dict(login='res.Lock.png', compose='res.Compose.gif', reply='res.Reply.gif',
                               delete='res.Delete.gif', exit='res.Close.gif', inbox='res.Inbox.gif', send='res.Send.gif')
    shared_icon_hashes = dict(
        login='6ca97582c11a808d9b5323cf83a2d82e2836a1848c7b7a9b20d9cb06af8f1427',
        compose='a4492cb496917e7629117817845144bf9ed7726c9bb43f45a62e2ac5d8287d43',
        reply='4fd085432613c9887519bffcfbf0a08a4c43178c620002634aa83083f115e700',
        delete='c3f281ebad1f866d5004f8ef777d51bf2c722430389ddee83ade31ac1feb311c',
        exit='1948ff83ae5fbe68591605799a363b1fad2524149d2e2a8fb40c54ce71670cec',
        inbox='75301cbc0fa5e0b25e7777206b9a9a044408ebd849e796fdde8c55d89f67ab3e',
        send='386672b9e863aa163c2ef6c478ef74cb4932cb284be76956701b985bb1cef02a')
    # Inherited defensive UI edits; protocol and cryptographic functions stay intact.
    ui_fixes = (
        ("onSubmit='javascript:func1()'", "onSubmit='func1();return false'"),
        ('var em,pz=by();', 'var em="",pz=by();'),
        ("onchange='javascript:func6(\"+gp+\".value)'", "onchange='func6(this.value)'"),
    )

    def __init__(self, dump, port, password, site):
        super().__init__(dump, port, password, site)
        self.demo_password = password
        self.source_js = None
        # Reproduce driver.js dc(): encode UTF-16 code units in one or two bytes.
        units = password.encode('utf-16-be')
        self.password = b''.join(units[i:i+2] if units[i] else units[i+1:i+2]
                                 for i in range(0, len(units), 2))
        self.key = digest(self.password + self.secret)
        self.original = (self.root / site.client).read_bytes()
        self.source_sha256 = site.client_sha256
        if hashlib.sha256(self.original).hexdigest() != self.source_sha256:
            raise ValueError('Unrecognized JavaScript client; refusing version-specific edits')
        source = self.original.decode('utf-8')
        self.descriptive_names = source.startswith('var _headerInbox=')
        names = dict(ta='_url', rk='_ssEnc', sz='KeyE', sy='KeyN', qk='_msgExt', rd='_mdExt', rg='_codec')
        self.setting_names = {k: names[k] if self.descriptive_names else k for k in names}
        def literal(name):
            match = re.search(r'\b' + re.escape(name) + r'''\s*=\s*("(?:[^"\\]|\\.)*"|'(?:[^'\\]|\\.)*')''', source)
            if not match:
                raise ValueError('Missing JavaScript literal: ' + name)
            return ast.literal_eval(match[1])
        self.original_settings = {k: literal(v) for k, v in self.setting_names.items()}
        roles = ('login', 'compose', 'reply', 'delete', 'exit', 'inbox', 'send')
        icon_vars = ('nl', 'lu', 'nk', 'mk', 'kj', 'ni', 'og')
        self.icons = {role: '/' + literal('_image' + role.title() if self.descriptive_names else var).lstrip('/')
                      for role, var in zip(roles, icon_vars)}
        # Use real, unmodified artwork from the related Java client as an explicitly
        # documented substitute. A surviving same-site image always takes priority.
        donor = SITES[DEFAULT_HOST]
        self.icon_donor = dump / donor.hostname / donor.client
        self.shared_icons = {}
        if self.icon_donor.is_file():
            with zipfile.ZipFile(self.icon_donor) as archive:
                resources = dict(binary_resources(archive.read(RESOURCE), RESOURCE_KEY))
            for role, member in self.shared_icon_members.items():
                data = resources.get(member)
                if data is not None and hashlib.sha256(data).hexdigest() == self.shared_icon_hashes[role]:
                    self.shared_icons[role] = data
        homepage = decode_html(self.files[self.homepage_url].read_bytes())
        self.display_div = re.search(r'displayDiv\s*=\s*["\']([^"\']+)', homepage)[1]
        metadata_var = '_doDataMining' if self.descriptive_names else 'lw'
        self.sends_metadata = bool(re.search(r'\b' + metadata_var + r'=true\b', source))
        self.endpoint = urllib.parse.urlsplit(self.original_settings['ta']).path
        self.config = dict(MessageMarker=self.original_settings['qk'], MetaDataExt=self.original_settings['rd'])
        numbers = self.private_key.public_key().public_numbers()
        self.changes = dict(ta=self.origin + self.endpoint,
                            rk=aes(self.secret, digest(self.password, 1), padded=False).hex().upper(),
                            sz=format(numbers.e, 'X'), sy=format(numbers.n, 'X'))
        self.edits = []
        for key, value in self.changes.items():
            name = self.setting_names[key]
            old = re.search(r'\b' + name + r'="[^"]*"', source)[0]
            new = name + '=' + json.dumps(value)
            self.edits.append((old, new))
            source = source.replace(old, new, 1)
        if self.descriptive_names:
            self.ui_fixes = (self.ui_fixes[0],
                ('var options,mList=getMessageArray();', 'var options="",mList=getMessageArray();'),
                ("onchange='javascript:func6(\"+_msgListTag+\".value)'", "onchange='func6(this.value)'"))
        for old, new in self.ui_fixes:
            if source.count(old) != 1:
                raise ValueError('Unexpected JavaScript UI source')
            source = source.replace(old, new, 1)
            self.edits.append((old, new))
        self.client_bytes = source.encode('utf-8')
        self.presentation = (ClassyMotorsPresentation if site.presentation == 'classymotors' else JavaScriptPresentation)(self)
        self.add_message(DEMO_INITIAL.encode(), self.config['MessageMarker'])

    def use_source_js(self, path):
        encoding = self.original_settings['rg'].lower()
        if encoding not in ('b', 'h'):
            raise ValueError('Unsupported password-only JS encoding: ' + encoding)
        login_image = self.icons['login']
        config = dict(endpoint=self.endpoint, sealedSecret=self.changes['rk'],
                      modulus=self.changes['sy'], exponent=self.changes['sz'],
                      marker=self.config['MessageMarker'], encoding=encoding, container=self.display_div,
                      loginImage=login_image if login_image in self.files else None)
        source = path.read_bytes()
        source.decode('utf-8')
        self.client_bytes = ('globalThis.passwordMailboxConfig = ' + json.dumps(config) + ';\n').encode() + source
        self.source_js = path.resolve()

    def session_token(self):
        # JS prepends a 16-character string to XHR text. ASCII avoids UTF-8 expansion.
        return secrets.token_hex(8).encode('ascii')

    def outgoing_category(self, argument, marker):
        # Unlike the JAR, driver.js uses a random argument byte, not a type code.
        if marker == self.config['MessageMarker']:
            return 'text'
        if marker == self.config['MetaDataExt']:
            return 'metadata'
        raise ValueError('Unsupported JavaScript message marker')

    def report(self):
        restored = self.client_bytes.decode('utf-8')
        for old, new in reversed(self.edits):
            restored = restored.replace(new, old, 1)
        icons = []
        for role, url in self.icons.items():
            file = self.files.get(url)
            borrowed = self.shared_icons.get(role) if file is None else None
            icons.append(dict(role=role, url=url, recovered=bool(file),
                              source=str(file) if file else str(self.icon_donor) if borrowed else None,
                              member=self.shared_icon_members[role] if borrowed else None,
                              sha256=hashlib.sha256(file.read_bytes() if file else borrowed).hexdigest() if file or borrowed else None,
                              replacement=None if file else 'Recovered JAR artwork; exact JS artwork unverified' if borrowed
                                  else 'Unavailable; no invented replacement' if not self.site.allow_placeholder_art
                                  else 'Simple SVG placeholder; original design unknown'))
        return dict(kind='js', domain=self.site.hostname, source=str(self.root), homepage=self.site.homepage,
                    client=self.site.client, original_sha256=self.source_sha256,
                    served_sha256=hashlib.sha256(self.client_bytes).hexdigest(),
                    original_client_url='/__reconstruction__/original.js',
                    source_js=str(self.source_js) if self.source_js else None,
                    only_documented_changes=(not self.source_js and restored.encode('utf-8') == self.original),
                    original_endpoint=self.original_settings['ta'], local_endpoint=self.origin + self.endpoint,
                    configuration_changes={self.setting_names[k]: v for k, v in
                        dict(ta='Local endpoint', rk='Demo password-encrypted shared secret',
                             sz='Local RSA exponent', sy='Local RSA modulus').items()},
                    ui_corrections=['Prevent login form navigation after submission',
                                    'Initialize the inbox option string', 'Use this.value in the message selector'] if not self.source_js else [],
                    runtime='Native browser JavaScript; no emulator or external runtime',
                    login_url=self.origin + '/#' + self.display_div, icons=icons,
                    automatic_metadata_report=self.sends_metadata and not self.source_js,
                    static_paths=len(self.files), feed_reconstruction=self.presentation.feed_report(),
                    notes=list(self.site.notes) + ([
                        'Readable client reimplements the wire protocol with a separate mailbox interface.',
                        'The login control uses the same-site image when it survives.',
                        'Sign out and log in again to retrieve the scripted reply.',
                        'The readable client omits the optional archived browser-environment report.',
                    ] if self.source_js else [
                        'Original cryptography, message serialization, labels and UI functions are used, with the listed edits only.',
                        'Three inherited defensive UI edits are applied; not all were demonstrated necessary in current browsers.',
                        'The clients share seven icon roles, but there is insufficient recovered artwork to establish identical designs.',
                        'Missing JS icons use unmodified art from the closely related messaging JAR family when available; exact original JS artwork is unverified.',
                        'Login is at the bottom of the homepage; the password and conversation are demo replacements.',
                        'Inbox is downloaded at login. Reload and log in again to retrieve queued replies.',
                        'Exit retains the original window-close attempt; browsers may refuse to close a manually opened tab.',
                        'The recovered interface composes text; no file-upload control is present.',
                        'Messages live only in server memory. Archived files remain untouched.']))


POLLING_SITES = {
    'the-open-book-online.com': ('xml', 'http://www.the-open-book-online.com/servlet/Reviews',
                                 {'logobottom': ('Book reviews', 'Reading recommendations')}),
    'al-ashak-news-me.com': ('txt', '/servlet/News',
                             {'rotateweather': ('حالة الطقس', 'أحوال الطقس المحلية'),
                              'feed1': ('أخبار كرة القدم', 'نتائج المباريات'),
                              'feed2': ('أخبار رياضية', 'أخبار الأندية')}),
}


class PollingReconstruction(ArchiveReconstruction):
    """Serve the two recovered POST pollers against labeled sample responses."""

    client_mime = 'application/javascript; charset=utf-8'

    def __init__(self, dump, port, password, site):
        super().__init__(dump, port, password, site)
        self.format, self.original_endpoint, self.topics = POLLING_SITES[site.hostname]
        self.endpoint = urllib.parse.urlsplit(self.original_endpoint).path
        self.response_mime = ('application/xml' if self.format == 'xml' else 'text/plain') + '; charset=utf-8'
        self.original = (self.root / site.client).read_bytes()
        if hashlib.sha256(self.original).hexdigest() != site.client_sha256:
            raise ValueError('Unexpected archived polling script')
        self.client_bytes = self.original
        self.source_js = None
        self.presentation = Presentation(self)

    def use_source_js(self, path):
        source = path.read_bytes()
        source.decode('utf-8')
        self.client_bytes = source
        self.source_js = path.resolve()

    def protocol(self, raw):
        if len(raw) > 8192:
            raise ValueError('Polling request too large')
        if self.format == 'xml':
            request = ElementTree.fromstring(raw)
            if request.tag != 'service':
                raise ValueError('Expected a service element')
            target = request.findtext('content')
            pointer = request.findtext('pointerId')
            count = request.findtext('count')
        else:
            fields = urllib.parse.parse_qs(raw.decode('utf-8'), keep_blank_values=True)
            target = fields.get('feeds', [''])[0]
            pointer = fields.get('info', [''])[0]
            count = fields.get('return', [''])[0]
        if target not in self.topics:
            raise ValueError('Unknown polling target')
        start = int(pointer or 0)
        count = int(count or 0)
        if start < 0 or not 1 <= count <= 12:
            raise ValueError('Invalid polling pointer or count')
        topics = self.topics[target]
        label = 'Reconstruction sample' if self.site.language == 'en' else 'عينة توضيحية'
        items = [(str(index + 1), f'{topics[index % len(topics)]} · {label}')
                 for index in range(start, start + count)]
        if self.format == 'xml':
            response = ElementTree.Element('service')
            for next_pointer, text in items:
                item = ElementTree.SubElement(response, 'item')
                ElementTree.SubElement(item, 'detail').text = next_pointer
                ElementTree.SubElement(item, 'txt').text = text
            return ElementTree.tostring(response, encoding='utf-8')
        return ';'.join(urllib.parse.urlencode({'descript': next_pointer, 'txttag': text})
                        for next_pointer, text in items).encode()

    def report(self):
        return dict(kind=self.site.kind, domain=self.site.hostname, client=self.site.client,
                    original_endpoint=self.original_endpoint, local_endpoint=self.origin + self.endpoint,
                    method='POST', response_format=self.format, target_ids=list(self.topics),
                    source_js=str(self.source_js) if self.source_js else None,
                    original_client_url='/__reconstruction__/original.js',
                    response_content='Labeled replay samples in the recovered wire format.',
                    authentication_found=False, encryption_found=False)


@dataclass
class AMFObject:
    alias: str
    fields: dict


class AMFReader(Reader):
    """Bounded AMF0 reader for the archived Flash Remoting request format."""
    def __init__(self, data):
        super().__init__(data)
        self.references = []
        self.values = 0

    def u16(self):
        return int.from_bytes(self.take(2), 'big')

    def u32(self):
        return int.from_bytes(self.take(4), 'big')

    def string(self, length=None):
        length = self.u16() if length is None else length
        if length > 65536:
            raise ValueError('AMF string too large')
        return self.take(length).decode('utf-8')

    def value(self, depth=0):
        self.values += 1
        if depth > 24 or self.values > 4096:
            raise ValueError('AMF nesting or item limit exceeded')
        kind = self.u1()
        if kind == 0:
            return struct.unpack('>d', self.take(8))[0]
        if kind == 1:
            return bool(self.u1())
        if kind in (2, 12, 15):
            return self.string(None if kind == 2 else self.u32())
        if kind in (5, 6):
            return None
        if kind == 7:
            index = self.u16()
            if index >= len(self.references):
                raise ValueError('Invalid AMF reference')
            return self.references[index]
        if kind == 10:
            count = self.u32()
            if count > 4096:
                raise ValueError('AMF array too large')
            result = []
            self.references.append(result)
            result.extend(self.value(depth + 1) for _ in range(count))
            return result
        if kind in (3, 8, 16):
            if kind == 8:
                self.u32()  # ECMA array count is advisory; properties end with 000009.
            alias = self.string() if kind == 16 else None
            fields = {}
            result = AMFObject(alias, fields) if alias is not None else fields
            self.references.append(result)
            while True:
                key = self.string()
                if not key and self.data[self.pos:self.pos+1] == b'\x09':
                    self.take(1)
                    return result
                fields[key] = self.value(depth + 1)
        raise ValueError('Unsupported AMF0 marker: ' + str(kind))


def amf_string(value):
    raw = value.encode('utf-8')
    if len(raw) > 65535:
        raise ValueError('AMF string too large')
    return struct.pack('>H', len(raw)) + raw


def amf_value(value):
    if value is None:
        return b'\x05'
    if isinstance(value, bool):
        return b'\x01' + bytes([value])
    if isinstance(value, (int, float)):
        return b'\x00' + struct.pack('>d', value)
    if isinstance(value, str):
        return b'\x02' + amf_string(value)
    if isinstance(value, (list, tuple)):
        return b'\x0a' + struct.pack('>I', len(value)) + b''.join(amf_value(v) for v in value)
    if isinstance(value, AMFObject):
        prefix, fields = b'\x10' + amf_string(value.alias), value.fields
    elif isinstance(value, dict):
        prefix, fields = b'\x03', value
    else:
        raise ValueError('Unsupported AMF value')
    return prefix + b''.join(amf_string(k) + amf_value(v) for k, v in fields.items()) + b'\x00\x00\x09'


def amf_packet(messages, headers=()):
    packet = struct.pack('>HH', 0, len(headers))
    for name, required, value in headers:
        body = amf_value(value)
        packet += amf_string(name) + bytes([required]) + struct.pack('>I', len(body)) + body
    packet += struct.pack('>H', len(messages))
    for target, response, value in messages:
        body = amf_value(value)
        packet += amf_string(target) + amf_string(response) + struct.pack('>I', len(body)) + body
    return packet


def read_amf_packet(data):
    if len(data) > 128 * 1024:
        raise ValueError('AMF request too large')
    reader = AMFReader(data)
    if reader.u16() != 0:
        raise ValueError('Expected AMF0 packet')
    def value():
        length = reader.u32()
        start = reader.pos
        reader.references = []  # Object references are local to each header/message.
        result = reader.value()
        if length != 0xffffffff and reader.pos - start != length:
            raise ValueError('AMF value length mismatch')
        return result
    count = reader.u16()
    if count > 32:
        raise ValueError('Too many AMF headers')
    headers = []
    for _ in range(count):
        name, required = reader.string(), bool(reader.u1())
        headers.append((name, required, value()))
    count = reader.u16()
    if not 1 <= count <= 32:
        raise ValueError('Invalid AMF message count')
    messages = []
    for _ in range(count):
        target, response = reader.string(), reader.string()
        messages.append((target, response, value()))
    if reader.pos != len(data):
        raise ValueError('Trailing AMF bytes')
    return headers, messages


class AVM1Code:
    """Small assembler used only for the documented Ruffle AMF type adapter."""
    def __init__(self):
        self.data, self.labels, self.branches = bytearray(), {}, []

    def op(self, code, payload=b''):
        self.data.append(code)
        if code >= 128:
            self.data.extend(struct.pack('<H', len(payload)))
            self.data.extend(payload)
        return self

    def push(self, *values):
        payload = bytearray()
        for value in values:
            if value is None:
                payload.extend(b'\x02')
            elif isinstance(value, str):
                payload.extend(b'\x00' + value.encode() + b'\x00')
            else:
                payload.extend(b'\x07' + struct.pack('<i', value))
        return self.op(150, payload)

    def var(self, name):
        return self.push(name).op(28)

    def member(self, name):
        return self.push(name).op(78)

    def label(self, name):
        self.labels[name] = len(self.data)
        return self

    def branch(self, code, label):
        self.op(code, b'\x00\x00')
        self.branches.append((len(self.data)-2, label))
        return self

    def function(self, name, params, body):
        # AVM1 function bodies follow the ActionLength-delimited header.
        header = name.encode() + b'\x00' + struct.pack('<H', len(params))
        header += b''.join(p.encode() + b'\x00' for p in params)
        self.op(155, header + struct.pack('<H', len(body)))
        self.data.extend(body)
        return self

    def finish(self):
        for offset, label in self.branches:
            self.data[offset:offset+2] = struct.pack('<h', self.labels[label] - offset - 2)
        return bytes(self.data)


def swf_type_adapter():
    """Reapply AMF class prototypes before yhH.onResult's instanceof checks.

    Ruffle 0.7.1 deserializes AMF0 typed objects as plain objects. Each local
    response carries __demoType=k/l/o as well as its original AMF class alias.
    Original actions remain intact; this extra root DoAction wraps one callback.
    https://github.com/ruffle-rs/ruffle/blob/v0.7.1/core/src/avm1/amf.rs
    """
    code = AVM1Code()
    code.var('v').push(None).op(73).branch(157, 'return')
    for alias, classname in (('k', 'AOT'), ('l', 'KVq'), ('o', 'uPi')):
        code.var('v').member('__demoType').push(alias).op(73).op(18).branch(157, 'next-' + alias)
        code.var('v').push('__proto__').var(classname).member('prototype').op(79)
        code.label('next-' + alias)
    code.var('v').member('__demoType').push('k').op(73).op(18).branch(157, 'return')
    code.push('i', 0).op(60).label('loop')
    code.var('i').var('v').member('d').member('length').op(72).op(18).branch(157, 'return')
    code.var('v').member('d').var('i').op(78).push(1).var('_root').push('__demoHydrate').op(82).op(23)
    code.push('i').var('i').op(80).op(29).branch(153, 'loop')
    code.label('return').var('v').op(62).op(0)
    bootstrap = AVM1Code().function('__demoHydrate', ['v'], code.finish())
    bootstrap.var('yhH').member('prototype').push('__demoOriginalOnResult')
    bootstrap.var('yhH').member('prototype').member('onResult').op(79)
    wrapper = AVM1Code().var('v').push(1).var('_root').push('__demoHydrate').op(82)
    wrapper.push(1).var('this').push('__demoOriginalOnResult').op(82).op(23).op(0)
    bootstrap.var('yhH').member('prototype').push('onResult').function('', ['v'], wrapper.finish()).op(79).op(0)
    return bootstrap.finish()


def swf_parts(raw):
    if raw[:3] not in (b'FWS', b'CWS') or len(raw) < 12:
        raise ValueError('Unsupported SWF header')
    data = raw if raw[:3] == b'FWS' else raw[:8] + zlib.decompress(raw[8:])
    if len(data) != int.from_bytes(data[4:8], 'little'):
        raise ValueError('Invalid SWF length')
    pos = 8 + (5 + 4 * (data[8] >> 3) + 7) // 8 + 4
    header, tags = data[:pos], []
    while pos < len(data):
        start = pos
        if pos + 2 > len(data):
            raise ValueError('Truncated SWF tag')
        tag = int.from_bytes(data[pos:pos+2], 'little')
        pos += 2
        length = tag & 63
        if length == 63:
            if pos + 4 > len(data):
                raise ValueError('Truncated SWF tag length')
            length = int.from_bytes(data[pos:pos+4], 'little')
            pos += 4
        pos += length
        if pos > len(data):
            raise ValueError('Truncated SWF tag data')
        tags.append((tag >> 6, data[start:pos]))
    return header, tags


def adapt_swf(raw):
    header, tags = swf_parts(raw)
    body = swf_type_adapter()
    extra = struct.pack('<HI', (12 << 6) | 63, len(body)) + body
    frame = next(i for i, (kind, _) in enumerate(tags) if kind == 1)
    data = header + b''.join(tag for _, tag in tags[:frame]) + extra + b''.join(tag for _, tag in tags[frame:])
    return b'CWS' + data[3:4] + struct.pack('<I', len(data)) + zlib.compress(data[8:])


class FlashReconstruction(ArchiveReconstruction):
    client_mime = 'application/x-shockwave-flash'
    response_mime = 'application/x-amf'
    runtime = RUFFLE_RUNTIME
    endpoint = '/servlet/starwarsimg'
    source_sha256 = '4df2ee04ebad564a81bc7fb45c95882e1c73042cc8a464e8ffcd537a94ac0acc'
    # Actual surviving artwork. Their use/order in this panel is reconstructed.
    games = (('The Force Unleashed II', '/images/cover_force_unleashed2.jpg'),
             ('Battlefront II', '/images/cover_battlefront2.jpg'),
             ('LEGO Star Wars II', '/images/cover_lego_2.jpg'),
             ('Republic Heroes', '/images/cover_republic_heroes.jpg'))

    def __init__(self, dump, port, password, site):
        super().__init__(dump, port, password, site)
        self.original = (self.root / site.client).read_bytes()
        if hashlib.sha256(self.original).hexdigest() != self.source_sha256:
            raise ValueError('Unrecognized Star Wars SWF; refusing to apply a version-specific adapter')
        self.client_bytes = adapt_swf(self.original)
        self.presentation = StarWarsPresentation(self)
        self.lock, self.requests = threading.Lock(), 0

    def panel(self, index):
        title, image = self.games[index % len(self.games)]
        def obj(alias, **fields):
            base = dict(f=0, n=0, r=158, j=217, e=None, c=None, b=None, __demoType=alias)
            base.update(fields)
            return AMFObject(alias, base)
        return obj('k', d=[
            obj('l', f=4, n=3, r=150, j=20, t='<p class="heading">FEATURED GAMES</p>'),
            obj('o', f=28, n=29, r=102, j=145, p=image),
            obj('l', f=4, n=182, r=150, j=33, t='<p>' + html.escape(title) + '</p>'),
        ])

    def protocol(self, data):
        headers, messages = read_amf_packet(data)
        for target, response, args in messages:
            if target != 's.a' or not re.fullmatch(r'/\d+', response):
                raise ValueError('Unexpected Flash Remoting operation')
            if not isinstance(args, list) or len(args) != 1 or not isinstance(args[0], dict):
                raise ValueError('Expected one AMF request object')
        with self.lock:
            responses = []
            for target, response, args in messages:
                responses.append((response + '/onResult', 'null', self.panel(self.requests)))
                self.requests += 1
            print('SWF: served ' + str(len(responses)) + ' reconstructed content item(s) via s.a', flush=True)
            return amf_packet(responses)

    def report(self):
        old_header, old_tags = swf_parts(self.original)
        new_header, new_tags = swf_parts(self.client_bytes)
        frame = next(i for i, (kind, _) in enumerate(old_tags) if kind == 1)
        return dict(kind='swf', domain=self.site.hostname, source=str(self.root), homepage=self.site.homepage,
                    swf=self.site.client, original_sha256=self.source_sha256,
                    served_sha256=hashlib.sha256(self.client_bytes).hexdigest(),
                    original_swf_url='/__reconstruction__/original.swf',
                    all_original_tags_identical=(old_tags == new_tags[:frame] + new_tags[frame+1:]),
                    compatibility_adapter='One added DoAction restores k/l/o AMF object prototypes before yhH.onResult.',
                    embedded_stage=[80, 80], original_html_dimensions=[158, 217],
                    runtime=self.runtime, gateway=self.endpoint,
                    served_gateway_url=self.origin + self.endpoint, amf_target='s.a',
                    reconstructed_endpoints=['/servlet/layouts', self.endpoint, '/propset.css'],
                    reconstructed_layout='a=0&e=6000 (cycling mode, 6 seconds)',
                    server_content_recovered=False, messaging_recovered=False,
                    static_paths=len(self.files), notes=list(self.site.notes) + [
                        'All original SWF tags and archived files remain intact; only the served copy gains an adapter.',
                        'No authentication or invented messaging UI is added to the SWF.',
                        'AMF and HTTP checks are offline; Ruffle rendering needs browser verification.'])



FEED_HOSTS = ('convert.rss-to-javascript.com', 'www.rss-to-javascript.com',
              'feed2js.org', 'www.feed2js.org', 'feedlocka.com', 'www.feedlocka.com')


class Presentation:
    """Shared served-only HTML changes; site artwork/layout stay in the dump."""
    notes = ['Original layouts and applet dimensions are retained.',
             'Archived feed responses take priority; missing feeds use period samples, with source links and dates in provenance.',
             'Some unrecovered secondary assets may remain absent.']

    def __init__(self, app):
        self.app = app
        self.files = app.files

    def feed_report(self):
        stories = {s[3]: s for c in ('world', 'latam', 'business', 'sports', 'technology') for s in self.feed_items(c)}
        return dict(archived=self.app.feed_archive_report(), language=self.app.site.language,
                    notes='Original feed responses take priority. Only absent feeds use these period samples; selection and translations are reconstructed.',
                    stories=[dict(date=d, title=t, description=b, source=u) for d, t, b, u in stories.values()])

    def story(self, key):
        date, title, description, url = PERIOD_NEWS[key]
        title, description = NEWS_TRANSLATIONS.get(self.app.site.language, {}).get(key, (title, description))
        return date, title, description, url

    def feed_items(self, category):
        cutoff = datetime.datetime.strptime(self.app.site.capture, '%Y%m%d%H%M%S').date().isoformat()
        if self.app.site.capture < '20090101000000':
            keys = ('egypt-2008',) if category == 'sports' else ()
        elif self.app.site.capture < '20110101000000':
            return [s for s in feed_items(category) if s[0] <= cutoff] if category in FEEDS else []
        else:
            keys = {'latam': ('brazil-2011',), 'business': ('brazil-2011', 'iphone-2011'),
                    'sports': ('japan-2011',), 'technology': ('iphone-2011',),
                    'world': ('egypt-2011', 'japan-2011', 'brazil-2011')}.get(category, ())
        return sorted((self.story(key) for key in keys if self.story(key)[0] <= cutoff), reverse=True)

    def feed_category(self, source):
        source = source.lower()
        if any(word in source for word in ('football', 'sport', 'deporte', '/sow/')):
            return 'sports'
        if any(word in source for word in ('health', 'teberooz', 'darabet')):
            return 'health'
        if any(word in source for word in ('cloudysky', 'weather')):
            return 'weather'
        if any(word in source for word in ('entertainment', 'entretenimiento', 'arts')):
            return 'entertainment'
        if any(word in source for word in ('hitech', 'tecnologia', 'technology')):
            return 'technology'
        if any(word in source for word in ('negocios', 'econews', 'business')):
            return 'business'
        if any(word in source for word in ('latin', 'latam', 'ipsnoticias')):
            return 'latam'
        return 'world'

    def feed_html(self, category, count=6):
        direction = 'rtl' if self.app.site.language in ('fa', 'ar') else 'ltr'
        return '<!-- Reconstructed period feed; not archived content. --><div class="rss-box" dir="' + direction + '"><ul class="rss-items">' + ''.join(
            '<li class="rss-item" style="margin-bottom:12px"><a target="_blank" rel="noopener" href="'
            + html.escape(url, quote=True) + '">' + html.escape(title) + '</a><br><small>' + date
            + '</small><br>' + html.escape(description) + '</li>'
            for date, title, description, url in self.feed_items(category)[:count]) + '</ul></div>'

    def feed_xml(self, category):
        return ('<?xml version="1.0" encoding="utf-8"?>'
                '<rss version="2.0" xmlns:dc="http://purl.org/dc/elements/1.1/"><channel><title>'
                + html.escape(self.app.site.hostname + ' — ' + category) + '</title><link>'
                + self.app.origin + '/</link><description>Reconstructed period feed, not recovered entries.</description>'
                + ''.join('<item><title>' + html.escape(title) + '</title><link>' + html.escape(url)
                          + '</link><description>' + html.escape(description) + '</description><dc:date>'
                          + date + '</dc:date></item>' for date, title, description, url in self.feed_items(category))
                + '</channel></rss>').encode()

    def page(self, path):
        text = self.app.rewrite_site_resource(decode_html(path.read_bytes()), path)
        text = re.sub(r'(<meta\b[^>]*charset\s*=\s*["\']?)[\w-]+', r'\1utf-8', text, flags=re.I)
        text = re.sub(r'<base\b[^>]*>', '', text, flags=re.I)
        text = re.sub(r'https?://(?:www\.)?' + re.escape(self.app.site.hostname.removeprefix('www.')) + r'/+', '/', text, flags=re.I)
        for host in FEED_HOSTS:
            text = re.sub(r'https?://' + re.escape(host) + r'(?=[/\s"\'>])', '/' + host, text, flags=re.I)
        if self.app.inject_cheerpj and re.search(r'<applet\b', text, re.I):
            width, height = int(self.app.applet['width']), int(self.app.applet['height'])
            text = re.sub(r'<applet\b', f'<cheerpj-applet style="display:inline-block;width:{width}px;height:{height}px"', text, flags=re.I)
            text = re.sub(r'</applet\s*>', '</cheerpj-applet>', text, flags=re.I)
            if re.search(r'</body\s*>', text, re.I):
                text = re.sub(r'</body\s*>', JAVA_START + '</body>', text, count=1, flags=re.I)
            else:
                text += JAVA_START
        return text.encode()

    def missing_image(self, path):
        if self.app.site.hostname == 'iraniangoalkicks.com' and path in (
                '/images/index_14.jpg', '/images/index_17.jpg', '/images/index_17-17.jpg', '/images/index_20.jpg'):
            return (b'<svg xmlns="http://www.w3.org/2000/svg" width="155" height="14">'
                    b'<rect width="155" height="14" fill="white"/></svg>'), 'image/svg+xml'
        return None


class JavaScriptPresentation(Presentation):
    notes = ['Original JavaScript interface, language and container are retained unless the site notes identify a repair.']

    def feed_items(self, category):
        cutoff = datetime.datetime.strptime(self.app.site.capture, '%Y%m%d%H%M%S').date().isoformat()
        return [story for story in super().feed_items(category) if story[0] <= cutoff]

    def feed_report(self):
        stories = {s[3]: s for c in ('world', 'business', 'sports', 'technology') for s in self.feed_items(c)}
        return dict(archived=self.app.feed_archive_report(), notes='Original feed responses take priority; missing feeds use period samples no later than the homepage capture.',
                    stories=[dict(date=d, title=t, description=b, source=u) for d, t, b, u in stories.values()])

    def page(self, path):
        text = super().page(path).decode()
        if path == self.app.files[self.app.homepage_url]:
            if self.app.site.hostname == 'middle-east-newstoday.com':
                text = text.replace('window.onLoad = swapAppletJS;', 'window.onload = func0;')
                text = text.replace('</body>', '<div id="newsDiv" style="clear:both;margin:0 auto;text-align:center"></div></body>')
            if self.app.site.hostname == 'networkconnectionsite.com':
                text = re.sub(r'<script\b[^>]*src="Scripts/AC_RunActiveContent.js"[^>]*>\s*</script>', '', text)
        return text.encode()

    def missing_image(self, path):
        role = next((role for role, url in self.app.icons.items() if url == path), None)
        if role is None:
            return super().missing_image(path)
        if role in self.app.shared_icons:
            data = self.app.shared_icons[role]
            return data, 'image/png' if data.startswith(b'\x89PNG') else 'image/gif'
        if not self.app.site.allow_placeholder_art:
            return None
        glyph = dict(login='OK', compose='+', reply='↩', delete='−', exit='×', inbox='≡', send='→')[role]
        width = 30 if role == 'login' else 16
        return (f'<svg xmlns="http://www.w3.org/2000/svg" width="{width}" height="18">'
                f'<rect x="0.5" y="0.5" width="{width-1}" height="17" rx="2" fill="#eee" stroke="#666"/>'
                f'<text x="{width/2}" y="13" text-anchor="middle" font-family="sans-serif" font-size="13" fill="#333">'
                + glyph + '</text></svg>').encode(), 'image/svg+xml'


class SlideshowPresentation(Presentation):
    notes = ['Original artwork retained; missing feeds remain empty unless separately recovered.',
             'Content iframe named body is renamed together with its navigation targets to avoid shadowing document.body.',
             'Unused, unarchived AC_RunActiveContent.js references are omitted. Original doctypes are retained.']

    def page(self, path):
        text = super().page(path).decode()
        # HTMLDocument named access lets <iframe name="body"> shadow the real
        # document.body. CheerpJ calls document.body.appendChild during startup.
        # Rename the browsing context and its targets, retaining layout and links.
        if re.search(r'<iframe\b[^>]*\bname\s*=\s*([\"\'])body\1', text, re.I):
            text = re.sub(r'(<iframe\b[^>]*\bname\s*=\s*)([\"\'])body\2',
                          r'\1\2reconstruction-content-frame\2', text, flags=re.I)
            text = re.sub(r'(\btarget\s*=\s*)([\"\'])body\2',
                          r'\1\2reconstruction-content-frame\2', text, flags=re.I)
        if 'AC_FL_RunContent' not in text and '/Scripts/AC_RunActiveContent.js' not in self.app.files:
            text = re.sub(r'<script\b[^>]*\bsrc=[\"\']/?Scripts/AC_RunActiveContent\.js[\"\'][^>]*>\s*</script>',
                          '', text, flags=re.I)
        return text.encode()

    def feed_items(self, category):
        return []

    def feed_report(self):
        return dict(archived=self.app.feed_archive_report(), stories=[],
                    notes='Archived feed responses take priority and are served byte-for-byte. Missing feeds remain empty.',
                    page_compatibility_changes=self.notes)


class WeatherPresentation(SlideshowPresentation):
    """Keep archived feeds and label the explicitly authorized replacement art."""
    # GIF and JPEG exports let the original Java 8 applets load the labeled art.
    # Their archived renderer still controls position, font, and rotation timing.
    notes = ['Original applet dimensions and parameters retained on archived pages.',
             'Missing weather artwork and readings are made up and visibly labeled.']

    def artwork_report(self):
        base = Path(__file__).with_name('reconstruction') / 'weather-art'
        background = 'information-junky.gif' if self.app.applet_class == 'InfoWeatherApplet' else 'nepalnewsbrief.jpg'
        names = {'/images/bgimage.gif': background, '/images/bg.jpg': background,
                 '/images/btn.jpg': 'cities.jpg', '/images/button1.jpg': 'cities.jpg',
                 '/images/warning.gif': 'warning.gif', '/images/weather-demo.gif': 'weather-demo.gif'}
        return [dict(url=url, file='reconstruction/weather-art/' + name,
                     sha256=hashlib.sha256((base / name).read_bytes()).hexdigest(),
                     invented=True) for url, name in names.items() if url not in self.files]

    def missing_image(self, path):
        asset = next((row for row in self.artwork_report() if row['url'] == path), None)
        if asset:
            file = Path(__file__).parent / asset['file']
            return file.read_bytes(), mimetypes.guess_type(file.name)[0]
        return super().missing_image(path)

    def page(self, path):
        text = super().page(path).decode()
        text = re.sub(r'(</(?:cheerpj-)?applet\s*>)', r'\1<div style="font:11px sans-serif">'
                      'Made-up weather artwork and readings. '
                      '<a href="/__weather_controls__.html">Enlarged view with Cities controls</a></div>',
                      text, flags=re.I)
        return text.encode()


class ClassyMotorsPresentation(JavaScriptPresentation):
    notes = ['Original page structure, CSS, login location and messaging UI retained.']
    stories = (
        ('2010-11-18', 'Captivating from the outside, magnificent from the inside. The new BMW 6 Series Convertible.',
         'BMW has announced its next-generation four-seat convertible, with six-cylinder and V8 engines.',
         'https://www.press.bmwgroup.com/global/article/detail/T0089173EN/captivating-from-the-outside-magnificent-from-the-inside-the-new-bmw-6-series-convertible?language=en'),
        ('2010-11-16', 'GM Raises Stock Offering Price Range and Increases Size',
         'General Motors has raised the proposed common-share price range to $32–$33 and increased its preferred-stock offering.',
         'https://investors.gm.com/events/event-details/gm-raises-stock-offering-price-range-and-increases-size/'),
    )

    def feed_category(self, source):
        return 'luxury' if 'topluxurycars' in source.lower() else 'automotive'

    def feed_items(self, category):
        return self.stories[:1] if category == 'luxury' else self.stories

    def feed_report(self):
        return dict(archived=self.app.feed_archive_report(), notes='Original feed responses take priority; missing feeds use two November 2010 automotive stories.',
                    stories=[dict(date=d, title=t, description=b, source=u) for d, t, b, u in self.stories])

    def missing_image(self, path):
        if path in self.app.icons.values():
            return super().missing_image(path)
        labels = {'li.gif': '•', 'quote.gif': '“'}
        name = path.removeprefix('/img2/')
        if not path.startswith('/img2/') or name not in labels:
            return None
        width = 16
        return (f'<svg xmlns="http://www.w3.org/2000/svg" width="{width}" height="18">'
                f'<rect x="0.5" y="0.5" width="{width-1}" height="17" rx="2" fill="#eee" stroke="#963"/>'
                f'<text x="{width/2}" y="13" text-anchor="middle" font-family="sans-serif" font-size="13" fill="#654">'
                + html.escape(labels[name]) + '</text></svg>').encode(), 'image/svg+xml'


class StarWarsPresentation(Presentation):
    notes = ['Surviving homepage and links_home.html are served with their original artwork.']
    flash_css = (b'p {font-family:Verdana; font-size:12px; color:#ffffff; text-align:center;}'
                 b'.heading {font-size:10px; color:#febb01; font-weight:bold;}')

    def feed_items(self, category):
        return []  # This captured site has no RSS feed to replace.

    def feed_report(self):
        return dict(stories={}, notes='No feed reconstruction for this site.')

    def page(self, path):
        text = super().page(path).decode()
        if path.name == 'index.html':
            css = ('<style>#topbanner{display:block;width:960px;height:262px;line-height:0}'
                   '#topbanner img,#wrap>img,#wrap>a>img{vertical-align:top}'
                   '#wrap{line-height:0}#maincontent{line-height:normal}</style>')
            text = text.replace('</head>', css + '</head>')
        if path.name == 'links_home.html':
            # Retain this page's original link-target behavior, without an external base URL.
            text = text.replace('</head>', '<base target="_blank"></head>')
        if path.name == 'starwarsflash.html':
            # This SWF passes gatewayUrl straight to NetConnection.connect().
            # Ruffle only selects HTTP remoting for an absolute HTTP(S) URL;
            # the original relative URL otherwise never sends an AMF request.
            # Update both object and embed FlashVars in the served HTML only.
            text = text.replace('gatewayUrl=servlet/starwarsimg',
                                'gatewayUrl=' + html.escape(self.app.origin + self.app.endpoint, quote=True))
            # Ruffle's polyfill consumes the original object/embed parameters,
            # including gatewayUrl, transparent background, and 158x217 dimensions.
            boot = '''<style>html,body{margin:0;padding:0;background:transparent}
object,embed,ruffle-object,ruffle-embed{display:block}
#flash-status{font:11px sans-serif;color:white}</style>
<script>
window.RufflePlayer = {config: {autoplay:'on', unmuteOverlay:'hidden',
  splashScreen:false, contextMenu:'off', upgradeToHttps:false,
  showSwfDownload:false}};
function flashFailed(message) {
  const show = function () {
    const status = document.getElementById('flash-status');
    status.hidden = false; status.textContent = message;
  };
  if (document.readyState === 'loading') document.addEventListener('DOMContentLoaded', show);
  else show();
}
window.addEventListener('securitypolicyviolation', function (e) {
  if ((e.blockedURI || '').includes('unpkg.com'))
    flashFailed('Flash runtime blocked: ' + e.violatedDirective);
});
</script>
<script src="''' + RUFFLE_RUNTIME + '''" onerror="flashFailed('Ruffle could not load. Check access to unpkg.com.')"></script>'''
            text = text.replace('</head>', boot + '</head>')
            text = text.replace('</body>', '<p id="flash-status" hidden></p></body>')
        return text.encode()

    def missing_image(self, path):
        if re.fullmatch(r'/images/[a-z]+_over\.jpg', path):
            normal = self.files.get(path.replace('_over.jpg', '_btn.jpg'))
            if normal:
                return normal.read_bytes(), 'image/jpeg'
        return None


class LatinPresentation(Presentation):
    """Repairs specific to Latin America News Beat, never imposed on other sites."""
    notes = ['Missing secondary portraits reuse homepage slices; missing labels, logos, arrows and RSS badge are approximations.',
             'RSS headlines and descriptions are editorial reconstructions of real period events, not recovered entries.',
             'Fallback headline titles and source links were checked against period publications; descriptions are summaries.']

    def feed_report(self):
        return dict(snapshot='2010-05-13', archived=self.app.feed_archive_report(),
                        dates='Story/event dates, not exact feed publication times.',
                        stories={key: dict(date=v[0], title=v[1], description=v[2], source=v[3])
                                 for key, v in NEWS.items()},
                        sections={key: list(v[1]) for key, v in FEEDS.items()})

    def feed_items(self, category):
        return feed_items(category) if category in FEEDS else []

    def feed_html(self, category, count=6):
        if category not in FEEDS:
            return ''
        return feed_html(category)

    def page(self, path):
        text = super().page(path).decode()
        text = re.sub(r'</head\s*>', LAYOUT + '</head>', text, count=1, flags=re.I)
        return text.encode()

    def missing_image(self, path):
        """Known missing slices only; never silently replace unrelated resources."""
        match = re.fullmatch(r'/images/((?:person[234]?|welcome2?)_)(business|sports|entertainment|world)\.jpg', path)
        if match:
            return self.files['/images/' + match[1] + 'index.jpg'].read_bytes(), 'image/jpeg'
        label, width, height, background = '', 250, 45, '#5f4e65'
        if path in ('/images/reg.gif', '/images/reg2.gif', '/images/reg3.gif'):
            height = {'/images/reg.gif': 20, '/images/reg2.gif': 31, '/images/reg3.gif': 45}[path]
            label = 'NEW MEMBERS' if height == 31 else ''
        elif path in ('/images/lanews_int.gif', '/images/lanews2_int.gif'):
            height = 20 if path.endswith('/lanews_int.gif') else 31
            label = 'INTERNATIONAL' if height == 20 else 'NEWS'
        elif path in ('/images/collapsed.gif', '/images/linkarrow.gif'):
            return (b'<svg xmlns="http://www.w3.org/2000/svg" width="10" height="10">'
                    b'<path fill="white" d="M3 1 L8 5 L3 9 Z"/></svg>'), 'image/svg+xml'
        else:
            labels = {'logo_4tg.gif': '4thegame', 'logo_baseball.jpg': 'Baseball',
                      'logo_conmebol.jpg': 'CONMEBOL', 'logo_global.jpg': 'Global Rhythm',
                      'logo_latin_heat.jpg': 'Latin Heat', 'logo_business_chronicle.jpg': 'Latin Business Chronicle',
                      'logo_ein.jpg': 'EIN News', 'logo_world_news.gif': 'World News',
                      'logo_worldpress.gif': 'World Press'}
            label = labels.get(path.removeprefix('/images/'))
            if label is None:
                return None
            background = '#11464f'
        return (f'<svg xmlns="http://www.w3.org/2000/svg" width="{width}" height="{height}">'
                f'<rect width="100%" height="100%" fill="{background}"/>'
                f'<text x="50%" y="50%" dy=".35em" text-anchor="middle" fill="white" '
                f'font-family="Arial,sans-serif" font-size="16">{html.escape(label)}</text></svg>').encode(), 'image/svg+xml'

    def menu_script(self):
        text = self.files['/menu.js'].read_text()
        text = text.replace('arrows, bypixels;', 'arrows, bypixels = 3;')
        text = text.replace('for(var i in hidden) {', 'for(var i in hidden) {\n'
                            '            if (!/^\\d+$/.test(hidden[i]) || !titles[hidden[i]]) continue;')
        return text.encode()


class Handler(BaseHTTPRequestHandler):
    server_version = 'LocalArchive/1.0'

    @property
    def app(self):
        return self.server.app

    def reply(self, status, data, mime='text/plain; charset=utf-8', headers=()):
        headers = list(headers)
        # CheerpJ seeks within JARs using HTTP byte ranges. HEAD describes the
        # whole representation; malformed/multiple ranges are ignored (HTTP 200).
        if status == 200 and self.command in ('GET', 'HEAD'):
            headers.append(('Accept-Ranges', 'bytes'))
            request_range = self.headers.get('Range', '')
            match = re.fullmatch(r'bytes=(\d*)-(\d*)', request_range)
            if self.command == 'GET' and not self.headers.get('If-Range') and match and any(match.groups()):
                total = len(data)
                first, last = match.groups()
                start = int(first) if first else max(0, total - int(last))
                end = min(int(last), total - 1) if first and last else total - 1
                if start >= total or end < start:
                    status, data = 416, b''
                    headers.append(('Content-Range', f'bytes */{total}'))
                else:
                    status, data = 206, data[start:end+1]
                    headers.append(('Content-Range', f'bytes {start}-{end}/{total}'))
        self.send_response(status)
        self.send_header('Content-Type', mime)
        self.send_header('Content-Length', str(len(data)))
        self.send_header('Cache-Control', 'no-store')
        self.send_header('X-Content-Type-Options', 'nosniff')
        # No archived script, Java network request, or image should contact a retired site.
        runtime_host = {'swf': 'https://unpkg.com', 'jar': 'https://cjrtnc.leaningtech.com',
                        'jar-slideshow': 'https://cjrtnc.leaningtech.com',
                        'jar-scrolling': 'https://cjrtnc.leaningtech.com',
                        'jar-weather': 'https://cjrtnc.leaningtech.com',
                        'meter': 'https://cjrtnc.leaningtech.com'}.get(self.app.site.kind, '')
        # Ruffle probes the original object before replacing it with its player.
        object_source = "'self'" if self.app.site.kind == 'swf' else "'none'"
        # Ruffle initializes audio with an inline WAV, even for a silent movie.
        media_source = "'self' data: blob:" if self.app.site.kind == 'swf' else "'self'"
        self.send_header('Content-Security-Policy', f"default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval' blob: {runtime_host}; connect-src 'self' {runtime_host}; img-src 'self' data: blob: {runtime_host}; media-src {media_source}; style-src 'self' 'unsafe-inline' {runtime_host}; font-src 'self' data: {runtime_host}; worker-src 'self' blob: {runtime_host}; frame-src 'self' blob: {runtime_host}; object-src {object_source}; base-uri 'self'; form-action 'self'")
        for name, value in headers:
            self.send_header(name, value)
        self.end_headers()
        if self.command != 'HEAD':
            self.wfile.write(data)

    def valid_host(self):
        return self.headers.get('Host') in {urllib.parse.urlsplit(self.app.origin).netloc,
                                          'localhost:' + str(self.server.server_port)}

    def do_HEAD(self):
        self.do_GET()

    def do_GET(self):
        if not self.valid_host():
            return self.reply(403, b'Local host required')
        if self.headers.get('Host', '').startswith('localhost:'):
            return self.reply(302, b'', headers=[('Location', self.app.origin + self.path)])
        raw_path = urllib.parse.urlsplit(self.path).path
        prefix = '/__reconstruction__/archive/'
        if raw_path.startswith(prefix):
            stamp, _, original = raw_path[len(prefix):].partition('/')
            response = self.app.external_response(stamp, urllib.parse.unquote(original))
            if response:
                return self.reply(200, *response)
            return self.reply(404, b'This resource was not preserved in the archive.')
        path = urllib.parse.unquote(raw_path)
        if any(p in ('.', '..') for p in path.split('/')) or '\\' in path or '\x00' in path:
            return self.reply(400, b'Invalid path')
        if path in ('/', '/index.htm'):
            path = self.app.homepage_url
        if path == self.app.site.client_url:
            return self.reply(200, self.app.client_bytes, self.app.client_mime)
        # Query strings are part of a feed's identity. Match archived filenames
        # before any synthetic route, and send the recovered response unchanged.
        feed = self.app.archived_feeds.get(archive_request_key(self.path))
        if feed is not None:
            data = feed.read_bytes()
            if b'document.write(' in data[:4096]:
                mime = 'application/javascript; charset=utf-8'
            elif re.search(rb'<(?:rss|feed)\b', data[:4096]):
                mime = 'application/xml; charset=utf-8'
            else:
                mime = 'text/html; charset=utf-8'
            return self.reply(200, data, mime)
        if self.app.site.kind == 'jar-weather':
            if path == '/__reconstruction__/native.html':
                # Weather images resolve against documentBase, not codeBase.
                return self.reply(302, b'', headers=[('Location', '/__weather_native__.html')])
            if path in ('/__weather_native__.html', '/__weather_controls__.html'):
                return self.reply(200, self.app.standalone_page(
                    controls=path == '/__weather_controls__.html' or self.app.weather_controls,
                    native=path != '/__weather_controls__.html'), 'text/html; charset=utf-8')
            if path == '/__reconstruction__/about':
                return self.reply(200, ('<!doctype html><meta charset="utf-8"><h1>Weather reconstruction</h1>'
                    '<p>Original applet; made-up artwork, city selection and weather readings. No recovered messaging UI.</p>'
                    '<pre>' + html.escape(json.dumps(self.app.report(), indent=2)) + '</pre>').encode(), 'text/html; charset=utf-8')
        if self.app.site.kind == 'meter' and path == '/__reconstruction__/about':
            page = ('<!doctype html><meta charset="utf-8"><h1>Original bandwidth meter</h1>'
                    '<p>The original applet exchanges test bytes with this local server. Its displayed rate includes '
                    'a configurable server delay and is not your Internet speed. No login/inbox was recovered '
                    'in this small meter JAR.</p><pre>' + html.escape(json.dumps(self.app.report(), indent=2))
                    + '</pre><a href="/">Back to site</a>')
            return self.reply(200, page.encode(), 'text/html; charset=utf-8')
        if self.app.site.kind in ('jar-slideshow', 'jar-scrolling'):
            if self.command == 'GET' and path in ('/', self.app.homepage_url, '/__reconstruction__/native.html'):
                self.app.backend.reset()
            if path == '/__reconstruction__/about':
                report = self.app.report()
                page = ('<!doctype html><meta charset="utf-8"><h1>' + html.escape(self.app.site.hostname) + ' reconstruction</h1>'
                        '<p>' + html.escape(self.app.activation) + ' Log in as '
                        + ' or '.join(self.app.demo_usernames) + ' with the '
                        'password printed by the server. '
                        + ('The rebuilt applet opens a local mailbox window.' if self.app.source_jar else
                           'The original Java windows handle messaging.') + '</p>'
                        '<p>Send a message, then reload and log in again to retrieve your account’s scripted reply.</p>'
                        '<p>' + html.escape(report['cover_display']) + ' '
                        'Use one active applet per server. Mailboxes survive page reloads in memory until the server stops.</p><pre>'
                        + html.escape(json.dumps(report, indent=2)) + '</pre><a href="/">Back</a>')
                return self.reply(200, page.encode(), 'text/html; charset=utf-8')
        if self.app.site.kind in ('jar', 'jar-slideshow', 'jar-scrolling', 'jar-weather', 'meter') and path == '/__reconstruction__/original.jar':
            return self.reply(200, self.app.original, self.app.client_mime)
        if self.app.site.kind in ('js', 'js-polling'):
            if path == '/__reconstruction__/original.js':
                return self.reply(200, self.app.original, self.app.client_mime)
            if path == '/__reconstruction__/native.html':
                return self.reply(404, b'This site uses native browser JavaScript. Open the homepage.')
            if path == '/__reconstruction__/about':
                title = html.escape(self.app.site.hostname)
                client = 'readable' if self.app.source_js else 'archived'
                if self.app.site.kind == 'js-polling':
                    detail = ('The ' + client + ' polling client renders labeled sample responses '
                              'in the recovered servlet format.')
                else:
                    detail = ('The ' + client + ' JavaScript login is at the bottom of the homepage. '
                              'Use the demo password printed by the server. Compose and Send use the original client protocol. '
                              'Log in again to retrieve the scripted reply. Messages are kept only in server memory.')
                page = ('<!doctype html><meta charset="utf-8"><title>' + title + ' reconstruction</title>'
                        '<h1>' + title + '</h1><p>' + detail + '</p>'
                        '<pre>' + html.escape(json.dumps(self.app.report(), indent=2)) + '</pre><a href="/">Back to site</a>')
                return self.reply(200, page.encode(), 'text/html; charset=utf-8')
        if self.app.site.kind == 'swf':
            if path == '/servlet/layouts':
                return self.reply(200, b'a=0&e=6000', 'application/x-www-form-urlencoded')
            if path == '/propset.css':
                return self.reply(200, self.app.presentation.flash_css, 'text/css')
            if path == '/__reconstruction__/original.swf':
                return self.reply(200, self.app.original, self.app.client_mime)
            if path == '/__reconstruction__/native.html':
                return self.reply(404, b'This site uses Flash. Open /starwarsflash.html to run it with Ruffle.')
            if path == '/__reconstruction__/about':
                page = ('<!doctype html><meta charset="utf-8"><title>Star Wars reconstruction</title>'
                        '<h1>starwarsweb.net</h1><p>The archived SWF runs in Ruffle. The server supplies '
                        'reconstructed display settings and AMF content using surviving game artwork. '
                        'The original panel contents and any additional server-loaded movies are missing; '
                        'no login or messaging interface was recovered.</p><p>A small added ActionScript '
                        'adapter restores AMF object types for Ruffle; all original SWF tags are preserved. '
                        'Browser rendering remains unverified by the agent.</p><pre>'
                        + html.escape(json.dumps(self.app.report(), indent=2)) + '</pre><a href="/">Back to site</a>')
                return self.reply(200, page.encode(), 'text/html; charset=utf-8')
        if path == '/menu.js' and isinstance(self.app.presentation, LatinPresentation):
            return self.reply(200, self.app.presentation.menu_script(), 'application/javascript; charset=utf-8')
        if path == '/www.rss-to-javascript.com/images/rss-to-jss-small.gif' and self.app.site.allow_placeholder_art:
            # Approximation of the unpreserved converter badge, generated in memory.
            badge = (b'<svg xmlns="http://www.w3.org/2000/svg" width="100" height="15">'
                     b'<rect width="100" height="15" fill="#eee"/><rect width="26" height="15" fill="#f80"/>'
                     b'<g font-family="Verdana,sans-serif" font-size="9" font-weight="bold">'
                     b'<text x="3" y="11" fill="white">RSS</text><text x="29" y="11" fill="#333">to JavaScript</text></g></svg>')
            return self.reply(200, badge, 'image/svg+xml')
        feed_match = re.fullmatch(r'/feeds/(latam|business|sports|entertainment|world|technology|health|weather)\.(xml|js)', path)
        if feed_match:
            category, kind = feed_match.groups()
            if kind == 'xml':
                return self.reply(200, self.app.presentation.feed_xml(category), 'application/rss+xml; charset=utf-8')
            return self.reply(200, ('document.write(' + json.dumps(self.app.presentation.feed_html(category)) + ');').encode(),
                              'application/javascript; charset=utf-8')
        converter_host, _, converter_path = path.lstrip('/').partition('/')
        if converter_host in FEED_HOSTS and converter_path.lstrip('/') in ('', 'feed2js.php'):
            query = urllib.parse.parse_qs(urllib.parse.urlsplit(self.path).query)
            source = query.get('src', [''])[0]
            category = self.app.presentation.feed_category(source)
            try:
                count = max(1, min(50, int(query.get('num', ['6'])[0]) or 50))
            except ValueError:
                count = 6
            fragment = self.app.presentation.feed_html(category, count)
            if converter_host != 'www.rss-to-javascript.com' and query.get('html') != ['y'] and query.get('as_html') != ['1']:
                return self.reply(200, ('document.write(' + json.dumps(fragment) + ');').encode(),
                                  'application/javascript; charset=utf-8')
            return self.reply(200, ('<!doctype html><meta charset="utf-8"><title>' + category.title()
                                   + ' News</title>' + fragment).encode(), 'text/html; charset=utf-8')
        if path == '/__reconstruction__/native.html':
            page = ('<html><body><applet archive="' + html.escape(self.app.site.client_url) + '" code="'
                    + html.escape(self.app.applet_class) + '" width="' + self.app.applet['width']
                    + '" height="' + self.app.applet['height'] + '"></applet></body></html>')
            return self.reply(200, page.encode(), 'text/html; charset=utf-8')
        if path == '/__reconstruction__/about':
            if getattr(self.app, 'source_jar', None):
                client_description = 'The source-built applet uses a separate mailbox interface and the recovered protocol.'
                jar_description = 'The server supplies its local demo configuration to the source-built JAR.'
            else:
                client_description = 'The page runs the original Java classes.'
                jar_description = ('The original server private key and password are unavailable. Four connection settings are replaced; '
                                   'all 96 Java classes are byte-identical. Obsolete signatures are removed from the in-memory demo JAR.')
            body = ('<!doctype html><meta charset="utf-8"><title>Local reconstruction</title><h1>'
                    + html.escape(self.app.site.hostname) + '</h1>'
                    '<p>' + client_description + ' Login with the demo password printed by the server. '
                    'Send a message, then sign out and log in again to retrieve the local demo reply.</p>'
                    '<p>Enable CheerpJ Applet Runner for the local address in a Chromium browser. '
                    'Use <code>--cheerpj</code> to load the runtime from the page, or <code>--native</code> '
                    'to open Java 8 AppletViewer.</p>'
                    '<p>All messages are synthetic or supplied by you, stored only in server memory, and cleared on restart. '
                    'Feed replacements and missing artwork are documented below. '
                    'Original news hyperlinks retain their destinations. Use the applet on the homepage to log in.</p>'
                    '<p>The browser Java runtime is supplied by <a href="https://cheerpj.com/">CheerpJ / Leaning Technologies</a>.</p>'
                    '<p>' + jar_description + '</p>'
                    '<pre>' + html.escape(json.dumps(self.app.report(), indent=2)) + '</pre><a href="/">Back to site</a>')
            return self.reply(200, body.encode(), 'text/html; charset=utf-8')
        if path == '/__reconstruction__/provenance.json':
            return self.reply(200, json.dumps(self.app.report(), indent=2).encode(), 'application/json')
        file = self.app.files.get(path)
        if file is None and path.endswith('.php'):
            # Some archived pages link to their original PHP names while the
            # dump stores the rendered response under an HTML extension.
            file = self.app.files.get(path[:-4] + '.html')
        elif file is None and path.endswith('.html'):
            file = self.app.files.get(path[:-5] + '.php')
        if file is None:
            replacement = self.app.presentation.missing_image(path)
            if replacement:
                return self.reply(200, *replacement)
        if file is None or not file.resolve().is_relative_to(self.app.root):
            return self.reply(404, b'This resource was not preserved in the archive.')
        if file.suffix.lower() in ('.html', '.htm', '.php'):
            return self.reply(200, self.app.page(file), 'text/html; charset=utf-8')
        if file.suffix.lower() == '.css':
            data = file.read_bytes()
            text = decode_html(data)
            rewritten = self.app.rewrite_site_resource(text, file, css=True)
            if rewritten != text:
                return self.reply(200, rewritten.encode(), 'text/css; charset=utf-8')
            return self.reply(200, data, 'text/css')
        mime = mimetypes.guess_type(file.name)[0] or 'application/octet-stream'
        return self.reply(200, file.read_bytes(), mime)

    def do_POST(self):
        if not self.valid_host() or urllib.parse.urlsplit(self.path).path != self.app.endpoint:
            return self.reply(403, b'Local demo endpoint required')
        origin = self.headers.get('Origin')
        if origin is not None and origin != self.app.origin:
            return self.reply(403, b'Origin mismatch')
        try:
            length = int(self.headers.get('Content-Length', '-1'))
            if not 0 < length <= 8 * 1024 * 1024:
                return self.reply(413, b'Invalid request size')
            self.connection.settimeout(15)
            data = self.rfile.read(length)
            if len(data) != length:
                raise ValueError('Truncated request')
            result = self.app.protocol(data)
        except (ValueError, TypeError, IndexError, OSError, ElementTree.ParseError) as error:
            print(self.app.site.kind.upper() + ': rejected request (' + str(error) + ')', flush=True)
            return self.reply(403, b'Bad login or invalid request')
        return self.reply(200, result, self.app.response_mime)


def meter_preview(app, output):
    """Render the unchanged original Canvas to PNG, using a live local measurement."""
    if app.config.get('DisplayMode') != '0':
        raise ValueError('Headless preview currently supports original digital mode 0 only')
    server = ThreadingHTTPServer(('127.0.0.1', 0), Handler)
    local = MeterReconstruction(app.root.parent, server.server_port, app.password.decode(), app.site)
    local.meter_delay = app.meter_delay
    server.app = local
    thread = threading.Thread(target=server.serve_forever, daemon=True)
    thread.start()
    output = output.resolve()
    output.parent.mkdir(parents=True, exist_ok=True)
    # Initialization follows b.a.c.c(); original Canvas.paint chooses the text size.
    source = r'''
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
public class MeterRender {
 public static void main(String[] args) throws Exception {
  b.a.a.c worker=new b.a.a.c(args[0]);
  worker.a(Integer.parseInt(args[1]),true);worker.a(Integer.parseInt(args[2]),false);
  worker.b(100);worker.c(0);worker.setDaemon(true);worker.start();
  long limit=System.currentTimeMillis()+10000;
  while(worker.a()<=0 && System.currentTimeMillis()<limit) Thread.sleep(50);
  if(worker.a()<=0) throw new AssertionError("No throughput measured");
  int w=Integer.parseInt(args[3]),h=Integer.parseInt(args[4]);
  b.a.b.b meter=new b.a.b.b();
  meter.setSize(w,h);meter.setFont(new Font(args[5],Font.PLAIN,12));
  meter.setBackground(Color.decode("#"+args[6]));meter.a(Color.decode("#"+args[7]));
  meter.setForeground(Color.decode("#"+args[10]));
  meter.a(Double.parseDouble(args[8]));
  double rate=worker.a();meter.b(rate);
  BufferedImage image=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
  Graphics2D g=image.createGraphics();g.setBackground(meter.getBackground());g.setColor(meter.getBackground());g.fillRect(0,0,w,h);
  g.setFont(meter.getFont());meter.paint(g);g.dispose();
  ImageIO.write(image,"png",new File(args[9]));
  worker.b();System.out.println("Rendered original digital-meter class at "+w+"x"+h+", measured "+rate+" Kbps");
 }
}
'''
    try:
        with tempfile.TemporaryDirectory(prefix='meter-render-') as tmp:
            root = Path(tmp)
            (root / 'meter.jar').write_bytes(local.client_bytes)
            (root / 'MeterRender.java').write_text(source)
            subprocess.run(['javac', '-source', '8', '-target', '8', '-cp', str(root / 'meter.jar'),
                            str(root / 'MeterRender.java')], check=True, timeout=30)
            result = subprocess.run(['java', '-Djava.awt.headless=true', '-cp', str(root / 'meter.jar') + ':' + tmp,
                            'MeterRender', local.origin + local.endpoint, local.config['SendSize'], local.config['ReadSize'],
                            local.applet['width'], local.applet['height'], local.config['Font'],
                            local.config['BackColor'], local.config['DrawColor'],
                            local.config['ScaleMax'], str(output), local.config['ForeColor']],
                            check=True, capture_output=True, text=True, timeout=20)
            print(result.stdout, end='')
        report = local.report()
        report['rendering'] = 'Original b.a.b.b Canvas.paint into Java BufferedImage; not a full browser screenshot. Platform font metrics may vary.'
        report['render_result'] = result.stdout.strip()
        output.with_suffix(output.suffix + '.json').write_text(json.dumps(report, indent=2) + '\n')
        print('Saved ' + str(output))
    finally:
        server.shutdown()
        server.server_close()
        thread.join(timeout=5)


def weather_self_test(app):
    """Drive the unchanged Java Server against Python over pipes, no network/GUI."""
    viewer = shutil.which('appletviewer')
    if not viewer:
        raise SystemExit('Weather integration checks require a Java 8 JDK with appletviewer.')
    java_bin = Path(viewer).resolve().parent
    source = RECONSTRUCTION_TMP / 'WeatherClientCheck.java'
    with tempfile.TemporaryDirectory(prefix='weather-check-') as tmp:
        root = Path(tmp)
        client = root / 'client.jar'
        client.write_bytes(app.client_bytes)
        assert client.read_bytes() == app.original
        subprocess.run([str(java_bin / 'javac'), '-cp', str(client), '-d', tmp, str(source)], check=True)
        defaults = root / 'defaults.properties'
        process = subprocess.Popen([str(java_bin / 'java'), '-Djava.awt.headless=false', '-cp', tmp + ':' + str(client),
                                    'WeatherClientCheck', 'protocol', app.applet_class, str(defaults)],
                                   stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
        pending = bytearray()
        try:
            deadline = time.monotonic() + 45
            while True:
                if time.monotonic() > deadline:
                    raise TimeoutError('Weather Java integration timed out')
                if not select.select([process.stdout], [], [], 1)[0]:
                    continue
                chunk = process.stdout.read1(65536)
                if not chunk:
                    break
                pending.extend(chunk)
                while b'\n' in pending:
                    line, _, rest = pending.partition(b'\n')
                    pending[:] = rest
                    if line.startswith(b'REQUEST '):
                        request = base64.b64decode(line[len(b'REQUEST '):])
                        response = app.protocol(request)
                        process.stdin.write(base64.b64encode(response) + b'\n')
                        process.stdin.flush()
                    else:
                        print(line.decode(errors='replace'), flush=True)
            if process.wait(timeout=5):
                raise AssertionError('Original weather client integration failed')
        finally:
            if process.poll() is None:
                process.kill()
            process.wait()
            process.stdin.close()
            process.stdout.close()
        # Test invalid envelopes without mutating the persisted selection.
        before = list(app.cities)
        for bad in (b'', b'<Cities/>', b'<!DOCTYPE x SYSTEM "file:///etc/passwd">',
                    b'<?xml version="1.0" ?><Cities n=\'99\' id=\'0000\'><SQL q="gc,na,na,na"></SQL></Cities>'):
            try:
                app.protocol(bad)
            except ValueError:
                pass
            else:
                raise AssertionError('Accepted malformed weather request')
        assert app.cities == before
        background_url = '/images/bgimage.gif' if app.applet_class == 'InfoWeatherApplet' else '/images/bg.jpg'
        row = next(a for a in app.presentation.artwork_report() if a['url'] == background_url)
        background = Path(__file__).parent / row['file']
        # Keep a reviewable preview outside the archive; it is not a screenshot.
        preview = RECONSTRUCTION_TMP / 'results' / 'weather' / (app.site.hostname + '-original-painter.png')
        preview.parent.mkdir(parents=True, exist_ok=True)
        subprocess.run([str(java_bin / 'java'), '-Djava.awt.headless=true', '-cp', tmp + ':' + str(client),
                        'WeatherClientCheck', 'paint', app.applet_class, str(defaults), str(background), str(preview)],
                       check=True, timeout=30)
        for row in app.presentation.artwork_report():
            data, mime = app.presentation.missing_image(row['url'])
            assert hashlib.sha256(data).hexdigest() == row['sha256'] and mime.startswith('image/')
        page = app.page(app.files[app.homepage_url])
        applet_tag = b'<cheerpj-applet' if app.inject_cheerpj else b'<applet'
        assert b'Made-up weather artwork and readings' in page and applet_tag in page.lower()
        assert (b'cheerpjInit(' in page) == app.inject_cheerpj
        assert b'<param name="CUST" value="true">' in app.standalone_page(controls=True)
        assert (b'cheerpjInit(' in app.standalone_page(controls=True)) == app.inject_cheerpj
        assert b'<applet ' in app.standalone_page(native=True)
        assert b'<param' not in app.standalone_page(native=True)
        # Exercise the real HTTP handler offline, including MIME, CSP, original
        # JAR byte ranges and native document-base paths. No listening socket.
        class Connection:
            def __init__(self, request):
                self.request = io.BytesIO(request)
                self.response = io.BytesIO()
            def makefile(self, *args, **kwargs):
                return self.request
            def sendall(self, data):
                self.response.write(data)
            def settimeout(self, timeout):
                pass
        class QuietHandler(Handler):
            def log_message(self, *args):
                pass
        class LocalServer:
            server_port = urllib.parse.urlsplit(app.origin).port
        server = LocalServer()
        server.app = app
        def http(path, body=None, extra=b''):
            method = b'GET' if body is None else b'POST'
            body = b'' if body is None else body
            raw = (method + b' ' + path.encode() + b' HTTP/1.0\r\nHost: '
                   + urllib.parse.urlsplit(app.origin).netloc.encode()
                   + b'\r\nContent-Length: ' + str(len(body)).encode() + b'\r\n' + extra + b'\r\n' + body)
            connection = Connection(raw)
            QuietHandler(connection, ('127.0.0.1', 1), server)
            return connection.response.getvalue().split(b'\r\n\r\n', 1)
        for path, marker, mime in (
                ('/', b'Made-up weather artwork and readings', b'text/html'),
                ('/__weather_native__.html', b'<applet codebase="/"', b'text/html'),
                ('/__weather_controls__.html', b'<param name="CUST"', b'text/html'),
                (app.site.client_url, app.original, b'application/java-archive'),
                (background_url, background.read_bytes(), b'image/'),
                ('/__reconstruction__/about', b'No recovered messaging UI', b'text/html'),
                ('/__reconstruction__/provenance.json', b'"entire_jar_identical": true', b'application/json')):
            headers, body = http(path)
            assert b' 200 ' in headers and mime in headers and marker in body, path
            assert b"script-src 'self' 'unsafe-inline' 'unsafe-eval' blob: https://cjrtnc.leaningtech.com" in headers
        headers, body = http(app.site.client_url, extra=b'Range: bytes=0-9\r\n')
        assert b' 206 ' in headers and body == app.original[:10]
        headers, body = http('/__reconstruction__/native.html')
        assert b' 302 ' in headers and b'Location: /__weather_native__.html' in headers
        request = b'<?xml version="1.0" ?><Cities n=\'0\' id=\'0000\'><SQL q="gc,na,na,na"></SQL></Cities>'
        headers, body = http(app.endpoint, request)
        assert b' 200 ' in headers and body == app.protocol(request)
        print('PASS: real HTTP handler, MIME/CSP, unchanged JAR ranges, artwork and native document base (offline).')
        print('PASS: unchanged complete JAR; Python server; labeled assets; archived-size and optional controls views.')


def meter_self_test(app):
    """Run original meter traffic and its payload hook against a loopback-only server."""
    source = r'''
import java.io.*;
import java.lang.reflect.*;
import java.util.concurrent.atomic.AtomicInteger;
public class MeterCheck {
 public static void main(String[] args) throws Exception {
  final int send=Integer.parseInt(args[1]), receive=Integer.parseInt(args[2]);
  final b.a.a.c meter=new b.a.a.c(args[0]);
  meter.a(send,true); meter.a(receive,false); meter.b(50); meter.c(0);
  meter.setDaemon(true); meter.start();
  long deadline=System.currentTimeMillis()+15000;
  while(meter.a()<=0 && System.currentTimeMillis()<deadline) Thread.sleep(50);
  if(meter.a()<=0) throw new AssertionError("Original meter did not measure throughput");
  System.out.println("PASS: original meter measured "+meter.a()+" Kbps");
  final AtomicInteger replies=new AtomicInteger();
  Object hook=Proxy.newProxyInstance(MeterCheck.class.getClassLoader(),
    new Class<?>[]{ObjectInput.class,ObjectOutput.class}, (proxy, method, values)-> {
     if(method.getName().equals("readBoolean")) return true;
     if(method.getName().equals("read") && values[0] instanceof byte[]) {
      byte[] dst=(byte[])values[0];
      byte[] marker="LOCAL-HOOK-PAYLOAD".getBytes("US-ASCII");
      java.util.Arrays.fill(dst,(byte)0);
      System.arraycopy(marker,0,dst,0,marker.length);
      dst[dst.length-1]=1;
      return dst.length-1;
     }
     if(method.getName().equals("write") && values[0] instanceof byte[]) {
      byte[] packet=(byte[])values[0];
      if(packet[0]==1) {
       if(packet.length!=receive+5) throw new AssertionError("Response length");
       for(int i=0;i<receive;i++) if(packet[i+1]!=(byte)i) throw new AssertionError("Response bytes");
       replies.incrementAndGet();
      }
      return null;
     }
     if(method.getReturnType()==boolean.class) return false;
     if(method.getReturnType()==int.class) return 0;
     return null;
    });
  Field f=b.a.a.c.class.getDeclaredField("r"); f.setAccessible(true);
  Object listener=f.get(meter);
  listener.getClass().getMethod("writeObject",Object.class).invoke(listener,hook);
  while(replies.get()==0 && System.currentTimeMillis()<deadline) Thread.sleep(50);
  meter.b();
  if(replies.get()==0) throw new AssertionError("No payload response through HostHook");
  System.out.println("PASS: original HostHook transported test payload and returned exact response bytes");
 }
}
'''
    server = ThreadingHTTPServer(('127.0.0.1', 0), Handler)
    test_app = MeterReconstruction(app.root.parent, server.server_port, app.password.decode(), app.site)
    test_app.meter_delay = 0.05
    server.app = test_app
    thread = threading.Thread(target=server.serve_forever, daemon=True)
    thread.start()
    try:
        with tempfile.TemporaryDirectory(prefix='meter-check-') as tmp:
            root = Path(tmp)
            (root / 'meter.jar').write_bytes(test_app.client_bytes)
            (root / 'MeterCheck.java').write_text(source)
            subprocess.run(['javac', '-source', '8', '-target', '8', '-cp', str(root / 'meter.jar'),
                            str(root / 'MeterCheck.java')], check=True, timeout=30)
            subprocess.run(['java', '-Djava.awt.headless=true', '-cp', str(root / 'meter.jar') + ':' + tmp,
                            'MeterCheck', test_app.origin + test_app.endpoint,
                            test_app.config['SendSize'], test_app.config['ReadSize']], check=True, timeout=25)
        assert test_app.report()['all_class_bytes_identical']
        assert test_app.report()['configuration_reversible']
        assert any(bytes.fromhex(r['sample_hex']).startswith(b'LOCAL-HOOK-PAYLOAD') for r in test_app.requests)
        # Reject requests that would allocate an unbounded response.
        bad = b'<THRUPUT><REQUEST value="99999999">1</REQUEST><TIME>x</TIME><BYTES size="0"></BYTES></THRUPUT>'
        try:
            test_app.protocol(bad)
        except ValueError:
            pass
        else:
            raise AssertionError('Unbounded response accepted')
        print('PASS: ' + app.site.hostname + ': original meter classes, loopback HTTP, payload hook and preservation')
    finally:
        server.shutdown()
        server.server_close()
        thread.join(timeout=5)


def self_test(app):
    """Execute the selected JAR's protocol classes with HTTP transport over pipes.

    This works offline and does not need a desktop, a browser runtime, or permission
    to connect to loopback. It is not a claim of a CheerpJ or live-HTTP smoke test.
    """
    if not shutil.which('javac') or not shutil.which('java'):
        raise SystemExit('The offline integration check needs a JDK (java and javac).')
    with tempfile.TemporaryDirectory(prefix='newsbeat-check-') as temp:
        root = Path(temp)
        jar = root / 'client.jar'
        jar.write_bytes(app.client_bytes)
        if app.source_jar:
            source = PASSWORD_ONLY_SOURCE_PROJECT / 'test' / 'SourceClientCheck.java'
            main_class = 'users.help.SourceClientCheck'
        else:
            main_class = 'OriginalClientCheck'
            source = root / (main_class + '.java')
            source.write_text(JAVA_CHECK.replace('__APPLET_PACKAGE__', app.applet_package))
        subprocess.run(['javac', '-encoding', 'UTF-8', '-source', '8', '-target', '8',
                        '-cp', str(jar), '-d', temp, str(source)], check=True)
        process = subprocess.Popen(['java', '-Djava.awt.headless=true', '-cp', str(jar) + ':' + temp,
                                    main_class, app.password.decode(), DEMO_REPLY, app.endpoint],
                                   stdin=subprocess.PIPE, stdout=subprocess.PIPE, text=True, bufsize=1)
        passed = False
        timer = threading.Timer(60, process.kill)
        timer.start()
        try:
            for line in process.stdout:
                if line.startswith('REQUEST '):
                    try:
                        answer = base64.b64encode(app.protocol(base64.b64decode(line[8:]))).decode()
                    except (ValueError, TypeError, IndexError) as error:
                        answer = 'ERROR ' + str(error)
                    process.stdin.write(answer + '\n')
                    process.stdin.flush()
                else:
                    print(line, end='')
                    passed |= line.startswith('PASS:')
            if process.wait() != 0 or not passed:
                raise SystemExit('Java client integration check failed')
        finally:
            timer.cancel()
            if process.poll() is None:
                process.kill()
                process.wait()
    if not app.source_jar and not app.report()['all_class_bytes_identical']:
        raise AssertionError('Java classes changed')
    if not any(m['body'] == bytes([0, 1, 2, 3, 255]) for m in app.received):
        raise AssertionError('Uploaded bytes were not preserved')
    test_client = 'Source' if app.source_jar else 'Original'
    if not any(m['body'] == f'{test_client} Java client round trip: café 世界'.encode() for m in app.received):
        raise AssertionError('UTF-8 message was not preserved')
    expected_categories = ['metadata', 'metadata'] if not app.source_jar and app.config.get('MetaDataExt') else []
    expected_categories += ['text', 'file']
    if [m['category'] for m in app.received] != expected_categories:
        raise AssertionError('Outgoing text, file, or login metadata misclassified')
    if not app.source_jar:
        print('PASS: ' + app.site.hostname + ': all original classes unchanged; binary upload preserved; no network used')


def flash_self_test(app):
    # Fixed AMF0 fixture, independent of our encoder. A Flash NetConnection.call
    # carries a strict argument array and can use an unknown (ffffffff) length.
    request = bytes.fromhex(
        '0000000000010003732e6100022f31ffffffff0a00000001'
        '030007726571756573740200034142430002696402000358595a000009')
    headers, messages = read_amf_packet(request)
    assert headers == [] and messages == [('s.a', '/1', [dict(request='ABC', id='XYZ')])]
    _, responses = read_amf_packet(app.protocol(request))
    target, response, panel = responses[0]
    assert (target, response, panel.alias) == ('/1/onResult', 'null', 'k')
    assert [child.alias for child in panel.fields['d']] == ['l', 'o', 'l']
    assert panel.fields['d'][1].fields['p'] in app.files
    batch = amf_packet([('s.a', '/2', [dict(request='A'*59, id='B'*37)]),
                        ('s.a', '/3', [dict(request='C'*59, id='D'*37)])],
                       [('header', False, 'E'*46)])
    _, responses = read_amf_packet(app.protocol(batch))
    assert [r[0] for r in responses] == ['/2/onResult', '/3/onResult']
    for bad in (request[:-1], request + b'x', b'\x00\x03' + request[2:],
                request.replace(b's.a', b'x.y'), amf_packet([('s.a', '/1', None)])):
        try:
            app.protocol(bad)
        except (ValueError, IndexError):
            pass
        else:
            raise AssertionError('Invalid AMF request accepted')
    report = app.report()
    assert report['all_original_tags_identical']
    old_header, old_tags = swf_parts(app.original)
    new_header, new_tags = swf_parts(app.client_bytes)
    assert old_header[3:4] == new_header[3:4] and old_header[8:] == new_header[8:]
    assert len(new_tags) == len(old_tags) + 1
    page = app.page(app.files['/starwarsflash.html'])
    assert RUFFLE_RUNTIME.encode() in page
    assert page.count(('gatewayUrl=' + app.origin + app.endpoint).encode()) == 2
    assert b'gatewayUrl=servlet/starwarsimg' not in page
    assert b'gatewayUrl=servlet/starwarsimg' in app.files['/starwarsflash.html'].read_bytes()
    assert b'cheerpjInit' not in page
    assert not report['messaging_recovered']
    print('PASS: starwarsweb.net: original SWF tags preserved; AMF0 fixture, typed content, batching and invalid requests checked. Ruffle graphics not tested.')


JAVASCRIPT_CHECK = r"""const vm = require('node:vm');
const fs = require('node:fs');
const readline = require('node:readline');
const assert = require('node:assert/strict');
const source = fs.readFileSync(process.argv[2], 'utf8');
const password = process.argv[3], initial = process.argv[4], reply = process.argv[5];
const containerId = process.argv[6];
const names = source.startsWith('var _headerInbox=')
    ? {url:'_url', ready:'_dmComplete', messages:'getMessageArray', text:'getMessageText', success:'_confirmMessageUploaded', failure:'_errorMessageLogin', draft:'_composeData'}
    : {url:'ta', ready:'gu', messages:'by', text:'kq', success:'gc', failure:'ig', draft:'bb'};
const messages = c => c.ctx[names.messages]();
const messageText = m => m[names.text]();
let pending = null;
const input = readline.createInterface({input: process.stdin});
input.on('line', line => {
    assert(pending, 'unsolicited transport reply');
    const xhr = pending; pending = null;
    xhr.readyState = 4;
    xhr.status = line.startsWith('ERROR ') ? 403 : 200;
    xhr.responseText = xhr.status === 200 ? Buffer.from(line, 'base64').toString('utf8') : '';
    xhr.onreadystatechange();
});
function client() {
    const elements = new Map(), alerts = [], ctx = {};
    let markup = '';
    const root = {
        get innerHTML() { return markup; },
        set innerHTML(value) {
            markup = value; elements.clear();
            for (const match of value.matchAll(/<(input|textarea|select|form)\b([^>]*)>/g)) {
                const attrs = Object.fromEntries([...match[2].matchAll(/([\w-]+)='([^']*)'/g)].map(a => [a[1], a[2]]));
                const el = {tag: match[1], attrs, value: attrs.value || '', selectionStart: 0,
                    focus() {}, setSelectionRange(a) {this.selectionStart = a;},
                    getAttribute(k) {return this.attrs[k];}};
                if (el.tag === 'textarea') el.value = value.slice(match.index + match[0].length).split('</textarea>')[0];
                if (el.tag === 'select') {
                    const options = [...value.matchAll(/<option value='([^']*)'([^>]*)>/g)];
                    el.value = (options.find(o => o[2].includes('selected')) || options[0] || [,''])[1];
                }
                elements.set(attrs.id || attrs.name, el);
            }
        },
        getElementsByTagName(tag) {return [...elements.values()].filter(e => e.tag === tag);}
    };
    Object.assign(ctx, {
        navigator: {appCodeName:'Mozilla',appName:'Netscape',appVersion:'demo',userAgent:'offline client test',language:'en',platform:'test',cookieEnabled:true,plugins:[],mimeTypes:[],javaEnabled:()=>false},
        history: {length:1},
        document: {referrer:'',getElementById: id => id === containerId ? root : elements.get(id)},
        alert: message => alerts.push(message), confirm: () => true,
        close: () => {ctx.closed = true;},
        XMLHttpRequest: function () {
            this.open = (method, url) => {assert.equal(method, 'POST'); assert.equal(url, ctx[names.url]);};
            this.send = data => {
                assert.equal(pending, null, 'concurrent legacy XHR'); pending = this;
                process.stdout.write('REQUEST ' + Buffer.from(data, 'utf8').toString('base64') + '\n');
            };
        },
        setTimeout: (fn, delay) => setTimeout(() => typeof fn === 'string' ? vm.runInContext(fn, ctx) : fn(), delay),
        clearTimeout,
    });
    ctx.window = ctx; ctx.self = ctx;
    vm.createContext(ctx);
    vm.runInContext(source, ctx);
    ctx.displayDiv = containerId;
    return {ctx, root, elements, alerts};
}
async function until(predicate) {
    for (let i = 0; i < 2000; i++) {if (predicate()) return; await new Promise(r => setTimeout(r, 5));}
    throw new Error('Client callback timed out');
}
async function login(c, pw) {
    c.ctx.func0();
    assert.match(c.root.innerHTML, /onSubmit='func1\(\);return false'/);
    c.elements.get('pw').value = pw; c.ctx.func1();
    await until(() => c.ctx[names.ready] || c.alerts.length);
}
(async () => {
    const c = client(); await login(c, password);
    assert.deepEqual(c.alerts, []); assert.equal(messages(c).length, 1);
    assert.equal(messageText(messages(c)[0]), initial); assert.equal(c.elements.get('message').value, initial);
    assert(!c.root.innerHTML.includes('undefined')); assert.match(c.root.innerHTML, /onchange='func6\(this.value\)'/);
    c.ctx.func3(); assert(c.elements.get('compose').value.includes(initial)); // Reply quotes selected message.
    c.ctx.func7(); c.ctx.func2();
    c.elements.get('compose').value = 'I can get the info today. Can we arrange a dead drop at our usual location tomorrow 9 AM?\nOriginal JS: café 世界';
    c.ctx.func8(); await until(() => c.alerts.length);
    assert.deepEqual(c.alerts, [c.ctx[names.success]]); assert.equal(c.ctx[names.draft], '');
    assert.equal(messages(c).length, 1, 'reply must wait for fresh inbox retrieval');
    const d = client(); await login(d, password);
    assert.deepEqual(d.alerts, []); assert.equal(messages(d).length, 2);
    d.elements.get('msgList').value = '1'; d.ctx.func6('1');
    assert.equal(d.elements.get('message').value, reply);
    d.ctx.func4(); await until(() => !pending && messages(d).length === 1);
    const e = client(); await login(e, password);
    assert.equal(messages(e).length, 1); assert.equal(messageText(messages(e)[0]), initial);
    e.ctx.func5(); assert.equal(e.root.innerHTML, ''); assert(e.ctx.closed);
    const bad = client(); await login(bad, password + '!');
    assert.deepEqual(bad.alerts, [bad.ctx[names.failure]]);
    console.log('PASS: recovered JavaScript RSA/challenge/AES client: login, inbox, Unicode send, reply, reload, delete, exit and bad password. DOM/XHR mocked; no browser rendering claimed.');
    input.close();
})().catch(error => {console.error(error); process.exit(1);});
"""


SOURCE_JS_CHECK = r'''
const assert = require('node:assert/strict');
const readline = require('node:readline');
globalThis.crypto = require('node:crypto').webcrypto;
const [source, password, opening, reply] = process.argv.slice(2);
require(source);
const lines = readline.createInterface({input: process.stdin});
async function transport(endpoint, body) {
    const read = new Promise(resolve => lines.once('line', resolve));
    console.log('REQUEST ' + Buffer.from(body).toString('base64'));
    const answer = await read;
    if (answer.startsWith('ERROR ')) throw new Error(answer.slice(6));
    return Buffer.from(answer, 'base64').toString();
}
async function check() {
    const first = new PasswordMailbox(passwordMailboxConfig, transport);
    await assert.rejects(first.login('invalid'));
    await first.login(password);
    assert.deepEqual((await first.inbox()).map(item => item.body), [opening]);
    await first.send('Source JS test — café 世界');
    assert.deepEqual((await first.inbox()).map(item => item.body), [opening, reply]);
    const second = new PasswordMailbox(passwordMailboxConfig, transport);
    await second.login(password);
    const messages = await second.inbox();
    await second.remove(messages[0].id);
    assert.deepEqual((await second.inbox()).map(item => item.body), [reply]);
    console.log('PASS: rebuilt JS client login, inbox, Unicode send, reconnect, and delete');
}
check().catch(error => {console.error(error); process.exitCode = 1;}).finally(() => lines.close());
'''


def javascript_self_test(app):
    """Run the recovered client under Node with only DOM and HTTP transport mocked."""
    if not shutil.which('node'):
        raise SystemExit('The JavaScript integration check needs Node.js (node). The website needs only a browser.')
    with tempfile.TemporaryDirectory(prefix='javascript-check-') as temp:
        root = Path(temp)
        source, runner = root / 'driver.js', root / 'check.js'
        source.write_bytes(app.client_bytes)
        runner.write_text(SOURCE_JS_CHECK if app.source_js else JAVASCRIPT_CHECK)
        process = subprocess.Popen(['node', str(runner), str(source), app.demo_password, DEMO_INITIAL, DEMO_REPLY, app.display_div],
                                   stdin=subprocess.PIPE, stdout=subprocess.PIPE, text=True, bufsize=1)
        passed = False
        timer = threading.Timer(60, process.kill)
        timer.start()
        try:
            for line in process.stdout:
                if line.startswith('REQUEST '):
                    try:
                        answer = base64.b64encode(app.protocol(base64.b64decode(line[8:]))).decode()
                    except (ValueError, TypeError, IndexError) as error:
                        answer = 'ERROR ' + str(error)
                    process.stdin.write(answer + '\n')
                    process.stdin.flush()
                else:
                    print(line, end='')
                    passed |= line.startswith('PASS:')
            if process.wait() != 0 or not passed:
                raise SystemExit('Recovered JavaScript client integration check failed')
        finally:
            timer.cancel()
            if process.poll() is None:
                process.kill()
                process.wait()
    assert app.report()['only_documented_changes'] == (app.source_js is None)
    text_messages = [m for m in app.received if m['category'] == 'text']
    assert len(text_messages) == 1
    assert text_messages[0]['body'].decode().endswith('Source JS test — café 世界' if app.source_js else 'Original JS: café 世界')
    metadata = [m for m in app.received if m['category'] == 'metadata']
    assert len(metadata) == (3 if app.sends_metadata and not app.source_js else 0)
    assert len(app.messages) == 1



POLLING_JS_CHECK = r"""
const assert = require('node:assert/strict');
const fs = require('node:fs');
const [client, fixture] = process.argv.slice(2);
const {format, endpoint, target, responses, firstText} = JSON.parse(fs.readFileSync(fixture));
require(client);
const {Poller, codecs} = ArchivePolling;
const codec = codecs[format];

// Minimal DOM objects for testing the XML request fields and parsed response
// independently of a browser. Python parses both fixture documents first.
const element = tagName => ({
    tagName, children: [], textContent: '',
    append(node) { this.children.push(node); },
});
const doc = {
    implementation: {
        createDocument(ns, root) {
            return {documentElement: element(root), createElement: element};
        },
    },
    getElementById() { return null; },
};
globalThis.XMLSerializer = class {
    serializeToString(xml) {
        assert.equal(xml.documentElement.tagName, 'service');
        return JSON.stringify(Object.fromEntries(xml.documentElement.children.map(x => [x.tagName, x.textContent])));
    }
};
globalThis.DOMParser = class {
    parseFromString(wire, type) {
        assert.equal(type, 'application/xml');
        const response = responses.find(value => value.wire === wire);
        assert.ok(response);
        return {documentElement: {children: response.items}, getElementsByTagName: () => []};
    }
};

async function check() {
    let requests = 0;
    const poller = new Poller(target, endpoint, codec, doc, async (url, request) => {
        assert.equal(url, endpoint);
        assert.equal(request.method, 'POST');
        assert.equal(request.headers['Content-Type'], 'application/x-www-form-urlencoded');
        const count = String(codec.initialCount);
        if (format === 'xml') {
            const fields = JSON.parse(request.body);
            assert.match(fields.dateline, /^[0-9A-F]{52}$/);
            assert.equal(Buffer.from(fields.initems, 'base64').length, 27);
            assert.deepEqual([fields.content, fields.pointerId, fields.count, fields.hdlnslocs],
                             [target, requests ? count : '', count, String(requests)]);
        } else {
            const fields = Object.fromEntries(request.body.split('&').map(field => {
                const split = field.indexOf('=');
                return [field.slice(0, split), field.slice(split + 1)];
            }));
            assert.match(fields.imagemd5, /^[0-9A-F]{68}$/);
            assert.equal(Buffer.from(fields.fetchprog, 'base64').length, 28);
            assert.deepEqual([fields.feeds, fields.info, fields.return, fields.imagenumber],
                             [target, requests ? count : '', count, String(requests)]);
        }
        const response = responses[requests++];
        return {ok: true, text: async () => response.wire};
    });

    await poller.refill(codec.initialCount);
    assert.equal(poller.queue.length, codec.initialCount);
    assert.equal(poller.queue[0].text, firstText);
    assert.equal(poller.pointer, String(codec.initialCount));
    poller.tick();
    await poller.inFlight;
    assert.equal(requests, 2);
    assert.equal(poller.pointer, String(codec.initialCount * 2));
    assert.equal(poller.queue.length, codec.initialCount * 2 - 1);
    if (format === 'txt') {
        const records = codecs.txt.response('descript=7&txttag=A%26B%3B+C%3D1');
        assert.equal(records[0].text, 'A&B; C=1');
    }
    console.log('PASS: ' + format + ' request fields, response decoding, continuation pointer, and queue refill');
}
check().catch(error => {console.error(error); process.exitCode = 1;});
"""


def polling_self_test(app):
    if not shutil.which('node'):
        raise SystemExit('The polling integration check needs Node.js (node).')
    target = next(iter(app.topics))
    count = 2 if app.format == 'xml' else 3
    responses = []
    for start in ('', str(count)):
        if app.format == 'xml':
            request = f'<service><content>{target}</content><pointerId>{start}</pointerId><count>{count}</count></service>'
        else:
            request = urllib.parse.urlencode({'feeds': target, 'info': start, 'return': count})
        wire = app.protocol(request.encode()).decode()
        response = {'wire': wire}
        if app.format == 'xml':
            response['items'] = [dict(children=[dict(tagName=field.tag, textContent=field.text) for field in record])
                                 for record in ElementTree.fromstring(wire)]
            if not start:
                first_text = ElementTree.fromstring(wire)[0].findtext('txt')
        elif not start:
            first_text = urllib.parse.parse_qs(wire.split(';')[0])['txttag'][0]
        responses.append(response)
    data = dict(format=app.format, endpoint=app.endpoint, target=target, responses=responses, firstText=first_text)
    with tempfile.TemporaryDirectory(prefix='polling-check-') as temp:
        root = Path(temp)
        client, fixture, runner = root / 'client.js', root / 'fixture.json', root / 'check.js'
        source = Path(__file__).with_name('comms-reverse') / 'polling.js'
        client.write_bytes(app.client_bytes if app.source_js else source.read_bytes())
        fixture.write_text(json.dumps(data))
        runner.write_text(POLLING_JS_CHECK)
        subprocess.run(['node', str(runner), str(client), str(fixture)], check=True, timeout=15)


def build_native_browser_bridge(root, javac):
    """Supply absent LiveConnect types; the original client catches JSException.

    Appletviewer has no browser window. Never fake browser objects or metadata.
    This separate, temporary library is used only by the native Java 8 launcher.
    """
    sources = {
        'JSException': '''package netscape.javascript;
public class JSException extends RuntimeException {
    public JSException() { super(); }
    public JSException(String message) { super(message); }
}
''',
        'JSObject': '''package netscape.javascript;
public abstract class JSObject {
    protected JSObject() {}
    public static JSObject getWindow(java.applet.Applet applet) {
        throw new JSException("No browser JavaScript window in native appletviewer");
    }
    public abstract Object call(String name, Object[] args) throws JSException;
    public abstract Object eval(String script) throws JSException;
    public abstract Object getMember(String name) throws JSException;
    public abstract void setMember(String name, Object value) throws JSException;
    public abstract void removeMember(String name) throws JSException;
    public abstract Object getSlot(int index) throws JSException;
    public abstract void setSlot(int index, Object value) throws JSException;
}
'''}
    package = root / 'netscape/javascript'
    package.mkdir(parents=True)
    files = []
    for name, source in sources.items():
        file = package / (name + '.java')
        file.write_text(source)
        files.append(str(file))
    subprocess.run([str(javac), '-source', '8', '-target', '8', '-encoding', 'UTF-8',
                    '-d', str(root), *files], check=True, timeout=30)
    library = root / 'native-browser-unavailable.jar'
    with zipfile.ZipFile(library, 'w') as archive:
        for file in sorted(package.glob('*.class')):
            archive.write(file, file.relative_to(root).as_posix())
    return library


def build_native_image_policy(root, app, origin=None):
    """Allow Java 8 Toolkit to read images inside this exact locally served JAR.

    Appletviewer supplies SocketPermission for the origin, but SunToolkit's
    jar:http image check requires URLPermission and does not fall back to it.
    Keep the policy temporary, additive, and scoped to the applet's code source.
    """
    jar_url = (origin or app.origin) + app.site.client_url
    policy = root / 'native-image.policy'
    quoted = json.dumps(jar_url)
    policy.write_text('grant codeBase ' + quoted + ' {\n'
                      '    permission java.net.URLPermission ' + quoted + ', "*:*";\n'
                      '};\n')
    return policy


def native_image_self_test(app, java_bin):
    """Reproduce the reported failure and decode the original image with the fix."""
    class JarHandler(BaseHTTPRequestHandler):
        def do_GET(self):
            if self.path != app.site.client_url:
                self.send_error(404)
                return
            self.send_response(200)
            self.send_header('Content-Length', str(len(app.client_bytes)))
            self.end_headers()
            self.wfile.write(app.client_bytes)

        def log_message(self, *args):
            pass

    with ThreadingHTTPServer(('127.0.0.1', 0), JarHandler) as server:
        thread = threading.Thread(target=server.serve_forever, daemon=True)
        thread.start()
        try:
            origin = 'http://127.0.0.1:' + str(server.server_port)
            with tempfile.TemporaryDirectory(prefix='native-image-check-') as tmp:
                root = Path(tmp)
                source = RECONSTRUCTION_TMP / 'NativeSlideshowImageCheck.java'
                local_source = root / source.name
                local_source.write_text(slideshow_source(source.read_text(), app))
                subprocess.run([str(java_bin / 'javac'), '-d', tmp, str(local_source)], check=True, timeout=30)
                # Only the local test driver is trusted. The HTTP-loaded applet
                # retains its real AppletClassLoader protection domain.
                driver_grant = ('grant codeBase ' + json.dumps(root.as_uri() + '/')
                                + ' { permission java.security.AllPermission; };\n')
                fixed = build_native_image_policy(root, app, origin).read_text()
                for expected, grant in (('denied', ''), ('allowed', fixed)):
                    policy = root / (expected + '.policy')
                    policy.write_text(driver_grant + grant)
                    subprocess.run([str(java_bin / 'java'), '-Djava.awt.headless=true',
                                    '-Djava.security.policy==' + policy.as_uri(), '-cp', tmp,
                                    'NativeSlideshowImageCheck', origin + '/__reconstruction__/',
                                    app.site.client_url, expected, app.config['Applet.BackgroundImageLocation']], check=True, timeout=30)
        finally:
            server.shutdown()
            thread.join()


def native_link_self_test(app):
    """Check original applet linkage and the absent-browser API, without a GUI."""
    viewer = shutil.which('appletviewer')
    if not viewer:
        raise SystemExit('Native linkage checks require a Java 8 JDK with appletviewer.')
    java_bin = Path(viewer).resolve().parent
    source = r'''
import java.lang.reflect.InvocationTargetException;
import java.util.jar.JarFile;
public class NativeLinkCheck {
    public static void main(String[] args) throws Exception {
        Class.forName(args[0]).getDeclaredConstructors();
        int checked = 0;
        try (JarFile jar = new JarFile(args[1])) {
            java.util.Enumeration<java.util.jar.JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (!name.endsWith(".class")) continue;
                // Optional Log4j adapters are bundled but inactive in this applet.
                if ((args[2].equals("jar-slideshow") || args[2].equals("jar-scrolling")) &&
                    (name.equals("a/a/h/d.class") || name.equals("a/a/h/e.class"))) continue;
                Class<?> type = Class.forName(name.substring(0, name.length()-6).replace('/', '.'), false,
                                              NativeLinkCheck.class.getClassLoader());
                type.getDeclaredConstructors(); type.getDeclaredMethods(); type.getDeclaredFields();
                checked++;
            }
        }
        try {
            Class.forName("netscape.javascript.JSObject").getMethod("getWindow", java.applet.Applet.class)
                .invoke(null, new Object[]{null});
            throw new AssertionError("A browser window was fabricated");
        } catch (InvocationTargetException ex) {
            if (!ex.getCause().getClass().getName().equals("netscape.javascript.JSException")) throw ex;
        }
        if ((args[2].equals("jar-slideshow") || args[2].equals("jar-scrolling")))
            System.out.println("Two unused optional Log4j adapters excluded from linkage check.");
        System.out.println("PASS: " + checked + " original classes link; absent browser raises JSException. No GUI exercised.");
    }
}
'''
    if app.site.kind in ('jar-slideshow', 'jar-scrolling'):
        source = slideshow_source(source, app)
    with tempfile.TemporaryDirectory(prefix='native-link-check-') as tmp:
        root = Path(tmp)
        library = build_native_browser_bridge(root, java_bin / 'javac')
        jar = root / 'client.jar'
        jar.write_bytes(app.client_bytes)
        code = root / 'NativeLinkCheck.java'
        code.write_text(source)
        subprocess.run([str(java_bin / 'javac'), str(code)], check=True, timeout=30)
        subprocess.run([str(java_bin / 'java'), '-Djava.awt.headless=true', '-Xbootclasspath/a:' + str(library),
                        '-cp', str(jar) + ':' + tmp, 'NativeLinkCheck', app.applet_class, str(jar), app.site.kind],
                       check=True, timeout=30)
    if app.site.kind in ('jar-slideshow', 'jar-scrolling') and app.background:
        native_image_self_test(app, java_bin)


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument('hostname', nargs='?', default=DEFAULT_HOST, help='site to reconstruct (default: %(default)s)')
    parser.add_argument('--list', action='store_true', help='list supported hostnames by client type')
    parser.add_argument('--dump', type=Path, default=DEFAULT_DUMP, help='archive root; default: ../../cia-2010-websites-dump/ relative to this script')
    parser.add_argument('--port', type=int, default=8000)
    parser.add_argument('--password', default='demodemodemo', help='JAR/JS demo password (default: %(default)s); never an original credential')
    parser.add_argument('--meter-preview', type=Path, metavar='PNG', help='render original digital meter to PNG using live loopback traffic, then exit')
    parser.add_argument('--meter-delay', type=float, default=0.12, help='local bandwidth-meter response delay in seconds (default: %(default)s)')
    parser.add_argument('--jar-scroll-speed', dest='scroll_speed', type=float, metavar='PIXELS_PER_SECOND',
                        help='local scrolling demo speed; default: 40 pixels/s; use 2.5 for the original constructor speed. Faster scrolling also speeds up authentication polling; historical server speed is unknown')
    parser.add_argument('--jar-slideshow-interval', type=float, metavar='SECONDS',
                        help='time each slideshow image is shown (default: 4 seconds, from the archived client)')
    parser.add_argument('--weather-controls', action='store_true', help='weather only: enlarged standalone view with original CUST parameter enabled')
    parser.add_argument('--cheerpj', action='store_true', help='inject load CheerpJ runtime in JAR pages. Allows browsers to open JAR without the CheerpJ browser extension. Breaks file upload.')
    parser.add_argument('--native', action='store_true', help='launch Java 8 appletviewer in addition to serving the website')
    parser.add_argument('--source-jar', type=Path, metavar='FILE',
                        help='serve a rebuilt JAR with the local configuration injected')
    parser.add_argument('--source-js', type=Path, metavar='FILE',
                        help='serve a readable JavaScript client for the selected JS site')
    parser.add_argument('--check', action='store_true', help='report client preservation and reconstruction provenance, then exit')
    parser.add_argument('--self-test', action='store_true', help='offline Java/JavaScript client integration or SWF/AMF protocol checks')
    args = parser.parse_args()
    if args.list:
        headings = {'jar': 'JAR-PASSWORD-ONLY', 'meter': 'JAR-METER', 'js': 'JS-PASSWORD-ONLY', 'js-polling': 'JS-POLLING'}
        sites = sorted(SITES.values(), key=lambda site: site.hostname)
        for kind in ('jar', 'jar-slideshow', 'jar-scrolling', 'jar-weather', 'meter', 'swf', 'js', 'js-polling'):
            heading = headings.get(kind, kind.upper())
            if kind in ('jar-slideshow', 'jar-scrolling'):
                heading += ': Click the applet; spaces below separate steps. Do not type spaces or Enter.'
            print(('\n' if kind != 'jar' else '') + heading)
            for site in sites:
                if site.kind == kind:
                    description = site.description
                    if site.activation_code:
                        clear, sequence, trigger, _ = activation_settings(site)
                        description += (f'; open: {clear} [hold {trigger}] {sequence} [release {trigger}]'
                                        if site.hold_activation else f'; open: {clear} {sequence} {trigger}')
                    print(f'  {site.hostname:30} {site.language:2}  {description}')
        return
    hostname = args.hostname.lower().removeprefix('www.').rstrip('.')
    hostname = next((name for name in SITES if name.removeprefix('www.') == hostname), hostname)
    if hostname not in SITES:
        parser.error('Unsupported hostname: ' + args.hostname + '. Use --list to see supported sites.')
    if not 1 <= args.port <= 65535 or not 1 <= len(args.password.encode()) <= 64:
        parser.error('Use a port in 1..65535 and a password of 1..64 UTF-8 bytes')
    site = SITES[hostname]
    if args.source_jar and hostname not in (SOURCE_JAR_SITE, *DISPLAY_SOURCE_SITES):
        parser.error('--source-jar is available for ' + ', '.join((SOURCE_JAR_SITE, *DISPLAY_SOURCE_SITES)) + '.')
    if args.source_js and site.kind not in ('js', 'js-polling'):
        parser.error('--source-js applies to JavaScript sites only.')
    if args.native and site.kind not in APPLET_KINDS:
        parser.error('--native uses Java appletviewer and applies to JAR sites only. Use the browser for this site.')
    if args.cheerpj and site.kind not in APPLET_KINDS:
        parser.error('--cheerpj applies to JAR sites only.')
    if args.jar_slideshow_interval is not None:
        if site.kind != 'jar-slideshow':
            parser.error('--jar-slideshow-interval applies to slideshow JARs only.')
        interval_ms = args.jar_slideshow_interval * 1000
        if not 1 <= interval_ms < 2 ** 63:
            parser.error('--jar-slideshow-interval must be at least 0.001 seconds and fit in a signed 64-bit millisecond count.')
    factory = {'jar': Reconstruction, 'jar-slideshow': SlideshowReconstruction, 'jar-scrolling': SlideshowReconstruction, 'meter': MeterReconstruction, 'jar-weather': WeatherReconstruction,
               'swf': FlashReconstruction, 'js': JavaScriptReconstruction, 'js-polling': PollingReconstruction}[site.kind]
    app = factory(args.dump.resolve(), args.port, args.password, site)
    if args.source_jar:
        try:
            app.use_source_jar(args.source_jar)
        except (OSError, ValueError, zipfile.BadZipFile) as error:
            parser.error(str(error))
    if args.source_js:
        try:
            app.use_source_js(args.source_js)
        except (OSError, ValueError) as error:
            parser.error(str(error))
    app.inject_cheerpj = args.cheerpj
    if args.weather_controls:
        if site.kind != 'jar-weather':
            parser.error('--weather-controls requires a weather JAR')
        app.weather_controls = True
    if args.scroll_speed is not None:
        if site.kind != 'jar-scrolling' or not 0.625 <= args.scroll_speed <= 1000:
            parser.error('--jar-scroll-speed requires a scrolling JAR and a speed from 0.625 to 1000 pixels/s')
        app.scroll_speed = round(args.scroll_speed * 1.6)
    if args.jar_slideshow_interval is not None:
        app.slideshow_interval_ms = round(interval_ms)
    if not 0 <= args.meter_delay <= 5:
        parser.error('--meter-delay must be between 0 and 5 seconds')
    if site.kind == 'meter':
        app.meter_delay = args.meter_delay
    if args.meter_preview:
        if site.kind != 'meter':
            parser.error('--meter-preview applies to bandwidth-meter sites only')
        meter_preview(app, args.meter_preview)
        return
    if args.self_test:
        if args.native and site.kind in ('jar', 'jar-slideshow', 'jar-scrolling') and not args.source_jar:
            native_link_self_test(app)
        {'jar': self_test, 'jar-slideshow': slideshow_self_test, 'jar-scrolling': slideshow_self_test, 'meter': meter_self_test, 'jar-weather': weather_self_test,
         'swf': flash_self_test, 'js': javascript_self_test, 'js-polling': polling_self_test}[site.kind](app)
        return
    if args.check:
        print(json.dumps(app.report(), indent=2))
        return
    server = ThreadingHTTPServer(('127.0.0.1', args.port), Handler)
    server.app = app
    viewer = None
    native_support = None
    print('Site: ' + app.origin + ('/__weather_controls__.html' if args.weather_controls else '/'), flush=True)
    if args.source_jar:
        print('Client JAR: ' + str(app.source_jar), flush=True)
    if args.source_js:
        print('Client JavaScript: ' + str(app.source_js), flush=True)
    if site.kind in APPLET_KINDS and not args.cheerpj and not args.native:
        print('Enable CheerpJ Applet Runner for the local address, or restart with --cheerpj to load it from the page.', flush=True)
    if site.kind in ('jar', 'js'):
        print('Demo password: ' + args.password, flush=True)
    elif site.kind == 'jar-weather':
        print('Weather: made-up artwork and readings; original applet.', flush=True)
        print('Cities controls: ' + app.origin + '/__weather_controls__.html', flush=True)
    elif site.kind == 'meter':
        print('Meter: original display; local test traffic, not Internet speed. Delay: ' + str(app.meter_delay) + ' s.', flush=True)
    elif site.kind == 'swf':
        print('SWF: original content loader with reconstructed server responses; no recovered login/messaging UI.', flush=True)
    if site.kind in ('jar-slideshow', 'jar-scrolling'):
        print('Open with: ' + app.activation, flush=True)
        print('Login: ' + ' or '.join(app.demo_usernames), flush=True)
        print('Password: ' + args.password, flush=True)
    if site.kind in ('jar', 'js'):
        action = 'Reload' if site.kind == 'js' else 'Exit'
        print('The first send queues one demo reply. ' + action + ' and log in again to retrieve it.', flush=True)
    if site.kind == 'js':
        print('JavaScript: scroll to Login at the bottom, or open ' + app.origin + '/#' + app.display_div, flush=True)
    try:
        if site.kind in ('jar-slideshow', 'jar-scrolling'):
            app.backend.start()
        if args.native:
            executable = shutil.which('appletviewer')
            if not executable:
                raise SystemExit('Java 8 appletviewer was not found. Use the browser version or install a Java 8 JDK.')
            command = [executable]
            if site.kind in ('jar', 'jar-slideshow', 'jar-scrolling') and not args.source_jar:
                javac = Path(executable).resolve().with_name('javac')
                if not javac.is_file():
                    raise SystemExit('Native messaging applets need javac beside the Java 8 appletviewer to build the browser-bridge compatibility library.')
                native_support = tempfile.TemporaryDirectory(prefix='native-applet-')
                library = build_native_browser_bridge(Path(native_support.name), javac)
                command.append('-J-Xbootclasspath/a:' + str(library))
                print('Native Java: browser bridge unavailable; original client fallback enabled.', flush=True)
                if site.kind in ('jar-slideshow', 'jar-scrolling') and app.background:
                    policy = build_native_image_policy(Path(native_support.name), app)
                    command.append('-J-Djava.security.policy=' + policy.as_uri())
                    print('Native Java: image-loading permission granted for the local applet JAR.', flush=True)
            native_page = '/__weather_native__.html' if site.kind == 'jar-weather' else '/__reconstruction__/native.html'
            viewer = subprocess.Popen(command + [app.origin + native_page])
        server.serve_forever()
    except KeyboardInterrupt:
        pass
    finally:
        server.server_close()
        if viewer and viewer.poll() is None:
            viewer.terminate()
            try:
                viewer.wait(timeout=5)
            except subprocess.TimeoutExpired:
                viewer.kill()
                viewer.wait()
        if native_support:
            native_support.cleanup()
        if site.kind in ('jar-slideshow', 'jar-scrolling'):
            app.close()


if __name__ == '__main__':
    main()
