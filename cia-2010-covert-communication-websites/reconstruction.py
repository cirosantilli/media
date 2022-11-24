#!/usr/bin/env python3
"""Run an archived website and its recovered JAR, SWF or JavaScript client locally.

python3 reconstruction.py             # modern browser + hosted CheerpJ runtime
python3 reconstruction.py --native    # also launch installed Java 8 appletviewer
python3 reconstruction.py --check     # check archive/config/class preservation
python3 reconstruction.py --list      # list supported hostnames
python3 reconstruction.py iraniangoalkicks.com
python3 reconstruction.py starwarsweb.net  # Flash content loader via Ruffle
python3 reconstruction.py classymotors.net # JavaScript messaging; no emulator

The archive defaults to ../../cia-2010-websites-dump/, resolved relative to this
script, independently of the working directory. Open http://127.0.0.1:8000/.
For JAR/JS sites, log in with the demo password printed by the server.
The optional hostname selects a site; the default is latinamericanewsbeat.com.
The password and scripted demo conversation are the same for every JAR/JS site.

JavaScript reconstruction: eleven messaging sites (see --list, JS section)
  Select a hostname, for example classymotors.net, iranfootballsource.com,
  wahidfutbol.com or russiansportsworld.com. Follow the login anchor printed at
  startup, or scroll to the login form at the bottom of the homepage.
  Use the printed demo password (default demodemodemo), then click the login
  icon or press Enter. Original English, Farsi, Arabic and Russian labels are
  retained. Compose, Reply, Delete and Send use the recovered client code.
  After sending, reload and log in again to retrieve the scripted reply.
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
  survives in a July 2013 capture. The other 76 of 77 JS icon references are
  missing. Their replacements are original, unmodified lock/compose/reply/delete/
  close/inbox/send images decrypted in memory from latinamericanewsbeat.com's
  today.jar. They match across 45 JARs (Inbox: 44); Wahid Futbol uses the same
  filenames. This is a historically grounded substitution, NOT proof that the
  missing JS icons were identical. If that donor JAR is absent, simple SVG
  placeholders are used. Per-icon paths/hashes are in --check; the full archive
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

Fidelity
  Each supported JAR's 96 original Java classes remain byte-for-byte identical.
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
  Runtime: CheerpJ replaces the historical Java browser plugin, so window
  presentation and font rendering may differ. It downloads from the vendor CDN.
  File selection: the original modal AWT dialog has been reported stuck under
  CheerpJ; its underlying defect remains unidentified. The attempted JavaScript
  picker workaround was removed because it did not restore working uploads.
  CheerpJ's title-bar "Add files" button imports into the browser's virtual
  /files/uploads/ directory only. The applet must still select and send that file
  for the Python server to receive it. Browser file uploads remain unverified.

In-memory JAR hotpatching
  When the Python server starts, it decrypts resource/resources.bin, changes
  four configuration entries, then re-encrypts it:
    ServerURL        -> the localhost messaging endpoint
    KeyE, KeyN       -> the demo server's newly generated RSA public key
    EncSharedSecret  -> the demo shared secret, encrypted for the configured
                        password (default: demodemodemo; override: --password)
  It rebuilds the served JAR and removes the now-invalid signature metadata.
  No Java class bytes, client logic or UI code are rewritten. Separate HTML
  changes initialize CheerpJ; they do not change the applet's connection settings.

The original JAR and all archived source files remain untouched on disk. You are
operating the actual recovered client within a reconstructed surrounding service.
The selected JAR sites share one recovered client/protocol family. Other JAR families,
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
import re
import secrets
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

try:
    from cryptography.hazmat.primitives.asymmetric import rsa, padding
    from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
except ImportError:
    raise SystemExit('This reconstruction needs Python cryptography: python3 -m pip install cryptography')

DEFAULT_HOST = 'latinamericanewsbeat.com'
RESOURCE = 'resource/resources.bin'
RESOURCE_KEY = '0C5C45F2EC3C2A63'
RUNTIME = 'https://cjrtnc.leaningtech.com/4.3/loader.js'
RUFFLE_RUNTIME = 'https://unpkg.com/@ruffle-rs/ruffle@0.7.1/ruffle.js'
DEFAULT_DUMP = Path(__file__).resolve().parent / '../../cia-2010-websites-dump'
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

    @property
    def capture(self):
        return self.homepage.split('/')[0]

    @property
    def client_url(self):
        return '/' + self.client.split('/', 1)[1]


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
)}

DEMO_INITIAL = ('We need the information on the target urgently. There are indications '
                'they may act within the next 48 hours. Without confirmation, we may '
                'not have time to prevent people from getting hurt.\n\n'
                'Can you get it today? Please confirm as soon as you can.')
DEMO_REPLY = ('OK. Our man will be there to pick it up.')

# Editorial reconstructions, not recovered feed entries. Dates are story/event
# dates, not invented exact publication times. Links document the underlying news.
# All sections use the homepage's May 2010 period, including November page shells.
NEWS = {
    'forlan': ('2010-05-12', 'Forlán double wins European final for Atlético',
              'Uruguay striker Diego Forlán scored twice as Atlético Madrid beat Fulham 2–1 after extra time in Hamburg.',
              'https://www.uefa.com/newsfiles/uefacup/2010/2000967_TS.pdf'),
    'brazil': ('2010-05-11', 'Brazil squad leaves out Ronaldinho',
              'Coach Dunga has named his World Cup squad without Ronaldinho. Brazil will face North Korea, Ivory Coast and Portugal in the group stage.',
              'https://www.fourfourtwo.com/news/ronaldinho-left-out-brazil-squad'),
    'argentina': ('2010-05-12', 'Maradona omits Zanetti and Cambiasso',
                 'Argentina’s preliminary World Cup selection leaves out the two experienced Inter players as preparations for South Africa continue.',
                 'https://www.fourfourtwo.com/news/zanetti-out-argentina-squad'),
    'haiti': ('2010-05-11', 'Brazil commits $55 million to Haiti recovery fund',
             'Brazil has become the first contributor to a reconstruction fund helping Haiti recover from January’s earthquake.',
             'https://documents1.worldbank.org/curated/en/473641647004086178/pdf/Announcement-of-Brazil-Becomes-First-Country-to-Contribute-to-Haiti-Reconstruction-Fund-on-May-11-2010.pdf'),
    'costa-rica': ('2010-05-08', 'Chinchilla takes office in Costa Rica',
                  'Laura Chinchilla becomes the country’s first woman president, succeeding Óscar Arias and promising greater attention to Central America.',
                  'https://elpais.com/internacional/2010/05/08/actualidad/1273269602_850215.html'),
    'investment': ('2010-05-06', 'UN forecasts Latin American investment rebound',
                   'ECLAC expects foreign direct investment in the region to rise by 40–50 percent in 2010 following last year’s sharp fall.',
                   'https://www.cepal.org/en/pressreleases/foreign-direct-investment-latin-america-and-caribbean-expected-rise-40-50-2010'),
    'cannes': ('2010-05-12', 'Robin Hood opens Cannes festival',
              'The film festival opens with Ridley Scott’s adventure starring Russell Crowe and Cate Blanchett. Screenings continue until May 23.',
              'https://fresques.ina.fr/festival-de-cannes-en/fiche-media/Cannes00521/opening-of-the-63rd-cannes-film-festival.html'),
    'shakira': ('2010-05-11', 'Shakira’s World Cup single goes on sale',
               'The Colombian singer teams up with South Africa’s Freshlyground on Waka Waka (This Time for Africa), the tournament’s official song.',
               'https://sony.mediaroom.com/2010-07-06-Shakira-to-Perform-Waka-Waka-at-World-Cup-Closing-Ceremony'),
    'coalition': ('2010-05-12', 'Cameron and Clegg present Britain’s new coalition',
                 'The Conservative and Liberal Democrat leaders appear together at Downing Street after agreeing to share power.',
                 'https://time.com/archive/6950051/britains-hot-new-bromance-cameron-and-clegg/'),
    'greece': ('2010-05-09', 'IMF approves €30 billion Greek rescue loan',
              'The three-year arrangement forms part of a €110 billion package with the European Union as Greece confronts its debt crisis.',
              'https://www.imf.org/external/np/sec/pr/2010/pr10187.htm'),
}
FEEDS = {
    'latam': ('Latin America News', ('forlan', 'argentina', 'haiti', 'brazil', 'costa-rica', 'investment')),
    'business': ('Business News', ('haiti', 'greece', 'investment')),
    'sports': ('Sports News', ('forlan', 'argentina', 'brazil')),
    'entertainment': ('Entertainment News', ('cannes', 'shakira')),
    'world': ('World News', ('coalition', 'haiti', 'greece', 'costa-rica')),
}

# Small dated fallback feeds for the added sites. These are editorial samples,
# never passed off as archived converter responses. No modern network fetches.
PERIOD_NEWS = {
    'egypt-2008': ('2008-02-10', 'Egypt retain the African Nations Cup',
                  'Egypt beat Cameroon 1–0 in the final, with Mohamed Aboutrika scoring the winner.',
                  'https://www.rte.ie/sport/soccer/2008/0210/227915-egypt_cameroon/'),
    'egypt-2011': ('2011-01-28', 'UN urges restraint as protests continue in Egypt',
                  'The UN human rights chief called on Egypt to protect demonstrators and investigate reports of excessive force.',
                  'https://www.un.org/sg/en/content/highlight/2011-01-28.html'),
    'japan-2011': ('2011-01-29', 'Japan win the Asian Cup',
                  'Japan defeated Australia 1–0 after extra time in the final in Qatar.',
                  'https://assets.afc.staging.digitalservices.sportradar.com/migration/t/s/tsg-asiancup2011.pdf'),
    'brazil-2011': ('2011-01-01', 'Dilma Rousseff takes office in Brazil',
                   'Brazil’s first woman president succeeds Luiz Inácio Lula da Silva and promises to build on his economic record.',
                   'https://www.investing.com/news/forex-news/update-1-brazil-bids-farewell-to-lula-as-rousseff-steps-in-184316'),
    'iphone-2011': ('2011-01-11', 'Verizon announces February launch for iPhone 4',
                   'Apple and Verizon say the phone will go on sale on February 10, with advance orders for eligible customers from February 3.',
                   'https://www.apple.com/newsroom/2011/01/11Verizon-Wireless-Apple-Team-Up-to-Deliver-iPhone-4-on-Verizon/'),
}
NEWS_TRANSLATIONS = {
    'fa': {
        'egypt-2008': ('مصر قهرمان جام ملت‌های آفریقا شد', 'مصر در دیدار نهایی با گل محمد ابوتریکه، کامرون را یک بر صفر شکست داد.'),
        'egypt-2011': ('سازمان ملل خواستار خویشتنداری در مصر شد', 'مسئول حقوق بشر سازمان ملل خواستار حمایت از تظاهرکنندگان و بررسی گزارش‌های استفاده بیش از حد از زور شد.'),
        'japan-2011': ('ژاپن قهرمان جام ملت‌های آسیا شد', 'ژاپن در دیدار نهایی در قطر، استرالیا را در وقت اضافه یک بر صفر شکست داد.'),
        'brazil-2011': ('دیلما روسف ریاست جمهوری برزیل را آغاز کرد', 'نخستین رئیس‌جمهور زن برزیل جانشین لولا داسیلوا شد.'),
        'iphone-2011': ('آیفون ۴ از فوریه در شبکه ورایزن عرضه می‌شود', 'اپل و ورایزن اعلام کردند عرضه این تلفن از ۱۰ فوریه آغاز خواهد شد.'),
    },
    'ar': {
        'egypt-2011': ('الأمم المتحدة تدعو إلى ضبط النفس في مصر', 'دعت مسؤولة حقوق الإنسان إلى حماية المتظاهرين والتحقيق في تقارير الاستخدام المفرط للقوة.'),
        'japan-2011': ('اليابان تفوز بكأس آسيا', 'فازت اليابان على أستراليا بهدف دون رد بعد وقت إضافي في النهائي الذي أقيم في قطر.'),
        'brazil-2011': ('ديلما روسيف تتولى رئاسة البرازيل', 'أصبحت روسيف أول امرأة تتولى رئاسة البرازيل خلفاً للولا دا سيلفا.'),
        'iphone-2011': ('آيفون 4 يصل إلى شبكة فيرايزون في فبراير', 'أعلنت آبل وفيرايزون بدء بيع الهاتف في العاشر من فبراير.'),
    },
    'es': {
        'egypt-2011': ('La ONU pide moderación ante las protestas en Egipto', 'La responsable de derechos humanos pidió proteger a los manifestantes e investigar el uso excesivo de la fuerza.'),
        'japan-2011': ('Japón gana la Copa de Asia', 'Japón derrotó a Australia por 1–0 en la prórroga de la final disputada en Catar.'),
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
    if(Collections.list(client.c()).size() != 2)
      throw new AssertionError("Expected initial message and one upload receipt after deleting text reply");
    boolean receipt = false;
    ids = client.c();
    while(ids.hasMoreElements()) {
      String body = new String(client.b(ids.nextElement()).b(), "UTF-8");
      if(body.equals(args[1])) throw new AssertionError("Upload classified as text");
      if(body.startsWith("LOCAL DEMO REPLY\n\nReceived file")) receipt = true;
    }
    if(!receipt) throw new AssertionError("Missing upload receipt");
    System.out.println("PASS: original JAR resource decoding, login, silent metadata, inbox, UTF-8 send, exactly one reply on next login, delete and upload");
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


def encode_resources(resources):
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
        previous = swap(idea_encrypt(swap(block), RESOURCE_KEY.encode()))
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


class ArchiveReconstruction:
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
                if category == 'metadata':
                    reply = None
                elif category == 'text':
                    reply = DEMO_REPLY.encode()
                else:
                    reply = ('LOCAL DEMO REPLY\n\nReceived file marker: ' + marker + '\nBytes: ' + str(len(body))).encode()
                self.received.append(dict(body=body, marker=marker, timestamp=timestamp,
                                          kind=argument, category=category))
                if len(self.received) > 100:
                    self.received.pop(0)
                if reply is None:
                    print(self.client_label + ': acknowledged login metadata (' + str(len(body)) + ' bytes); no inbox reply', flush=True)
                else:
                    reply_id = self.add_message(reply, 'dat')
                    print(self.client_label + ': received ' + category + ' (' + str(len(body)) + ' bytes); queued demo reply ' + str(reply_id), flush=True)
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

    def report(self):
        with zipfile.ZipFile(io.BytesIO(self.original)) as old, zipfile.ZipFile(io.BytesIO(self.jar)) as new:
            classes = [n for n in old.namelist() if n.endswith('.class')]
            identical = all(old.read(n) == new.read(n) for n in classes)
        return dict(kind=self.site.kind, domain=self.site.hostname, source=str(self.root), homepage=self.site.homepage,
                    jar=self.site.client, original_sha256=hashlib.sha256(self.original).hexdigest(),
                    applet_class=self.applet_class, applet_dimensions=[self.applet['width'], self.applet['height']],
                    original_endpoint=self.config['ServerURL'], local_endpoint=self.origin + self.endpoint,
                    original_transport_encoding=self.config.get('EncodeType'),
                    original_class_count=len(classes), all_class_bytes_identical=identical,
                    configuration_changes=list(self.changes), removed_signature_metadata=self.removed_signatures,
                    static_paths=len(self.files), runtime=RUNTIME,
                    feed_reconstruction=self.presentation.feed_report(),
                    notes=['Server and messages are local demo substitutes.',
                           'Automatic login metadata is acknowledged without an inbox reply.',
                           'Original AWT file picker retained; browser uploads have a reported unresolved modal-dialog problem.',
                           'Assets come from several captures. No archived files are overwritten.']
                          + list(self.site.notes) + self.presentation.notes)

    def page(self, path):
        return self.presentation.page(path)



class JavaScriptReconstruction(MessagingReconstruction):
    client_label = 'JavaScript'
    client_mime = 'application/javascript; charset=utf-8'
    response_mime = 'text/plain; charset=utf-8'
    runtime = None
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
                                  else 'Simple SVG placeholder; original design unknown'))
        return dict(kind='js', domain=self.site.hostname, source=str(self.root), homepage=self.site.homepage,
                    client=self.site.client, original_sha256=self.source_sha256,
                    served_sha256=hashlib.sha256(self.client_bytes).hexdigest(),
                    original_client_url='/__reconstruction__/original.js',
                    only_documented_changes=(restored.encode('utf-8') == self.original),
                    original_endpoint=self.original_settings['ta'], local_endpoint=self.origin + self.endpoint,
                    configuration_changes={self.setting_names[k]: v for k, v in
                        dict(ta='Local endpoint', rk='Demo password-encrypted shared secret',
                             sz='Local RSA exponent', sy='Local RSA modulus').items()},
                    ui_corrections=['Prevent login form navigation after submission',
                                    'Initialize the inbox option string', 'Use this.value in the message selector'],
                    runtime='Native browser JavaScript; no emulator or external runtime',
                    login_url=self.origin + '/#' + self.display_div, icons=icons,
                    automatic_metadata_report=self.sends_metadata,
                    static_paths=len(self.files), feed_reconstruction=self.presentation.feed_report(),
                    notes=list(self.site.notes) + [
                        'Original cryptography, message serialization, labels and UI functions are used, with the listed edits only.',
                        'Three inherited defensive UI edits are applied; not all were demonstrated necessary in current browsers.',
                        'The clients share seven icon roles, but there is insufficient recovered artwork to establish identical designs.',
                        'Missing JS icons use unmodified art from the related JAR family when available; this is a documented substitution, not proof of the original JS design.',
                        'Login is at the bottom of the homepage; the password and conversation are demo replacements.',
                        'Inbox is downloaded at login. Reload and log in again to retrieve queued replies.',
                        'Exit retains the original window-close attempt; browsers may refuse to close a manually opened tab.',
                        'The recovered interface composes text; no file-upload control is present.',
                        'Messages live only in server memory. Archived files remain untouched.'])


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
             'External feeds use small reconstructed period samples, translated for the site; unmatched categories are empty.',
             'Some unrecovered secondary assets may remain absent.']

    def __init__(self, app):
        self.app = app
        self.files = app.files

    def feed_report(self):
        keys = ('egypt-2008',) if self.app.site.capture < '20100101000000' else (
            'egypt-2011', 'japan-2011', 'brazil-2011', 'iphone-2011')
        return dict(recovered=False, language=self.app.site.language,
                    notes='Small period samples; selection, wording and translations are reconstructed. Unmatched categories are empty.',
                    stories={key: dict(date=self.story(key)[0], title=self.story(key)[1],
                                       description=self.story(key)[2], source=self.story(key)[3]) for key in keys})

    def story(self, key):
        date, title, description, url = PERIOD_NEWS[key]
        title, description = NEWS_TRANSLATIONS.get(self.app.site.language, {}).get(key, (title, description))
        return date, title, description, url

    def feed_items(self, category):
        if self.app.site.capture < '20100101000000':
            keys = ('egypt-2008',) if category == 'sports' else ()
        else:
            keys = {'latam': ('brazil-2011',), 'business': ('brazil-2011', 'iphone-2011'),
                    'sports': ('japan-2011',), 'technology': ('iphone-2011',),
                    'world': ('egypt-2011', 'japan-2011', 'brazil-2011')}.get(category, ())
        return sorted((self.story(key) for key in keys), reverse=True)

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
        text = decode_html(path.read_bytes())
        text = re.sub(r'(<meta\b[^>]*charset\s*=\s*["\']?)[\w-]+', r'\1utf-8', text, flags=re.I)
        text = re.sub(r'<base\b[^>]*>', '', text, flags=re.I)
        text = re.sub(r'https?://(?:www\.)?' + re.escape(self.app.site.hostname.removeprefix('www.')) + r'(?=/)', '', text, flags=re.I)
        for host in FEED_HOSTS:
            text = re.sub(r'https?://' + re.escape(host) + r'(?=[/\s"\'>])', '/' + host, text, flags=re.I)
        if re.search(r'<applet\b', text, re.I):
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
        return dict(recovered=False, notes='Small reconstructed feed samples, filtered to dates no later than the homepage capture. Some feeds remain empty.',
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
        glyph = dict(login='OK', compose='+', reply='↩', delete='−', exit='×', inbox='≡', send='→')[role]
        width = 30 if role == 'login' else 16
        return (f'<svg xmlns="http://www.w3.org/2000/svg" width="{width}" height="18">'
                f'<rect x="0.5" y="0.5" width="{width-1}" height="17" rx="2" fill="#eee" stroke="#666"/>'
                f'<text x="{width/2}" y="13" text-anchor="middle" font-family="sans-serif" font-size="13" fill="#333">'
                + glyph + '</text></svg>').encode(), 'image/svg+xml'


class ClassyMotorsPresentation(JavaScriptPresentation):
    notes = ['Original page structure, CSS, login location and recovered messaging UI retained.']
    stories = (
        ('2010-11-18', 'BMW reveals the new 6 Series Convertible',
         'BMW has announced its next-generation four-seat convertible, with six-cylinder and V8 engines.',
         'https://www.press.bmwgroup.com/global/article/detail/T0089173EN/captivating-from-the-outside-magnificent-from-the-inside-the-new-bmw-6-series-convertible?language=en'),
        ('2010-11-16', 'GM raises proposed share offering price range',
         'General Motors has raised the proposed common-share price range to $32–$33 and increased its preferred-stock offering.',
         'https://investors.gm.com/events/event-details/gm-raises-stock-offering-price-range-and-increases-size/'),
    )

    def feed_category(self, source):
        return 'luxury' if 'topluxurycars' in source.lower() else 'automotive'

    def feed_items(self, category):
        return self.stories[:1] if category == 'luxury' else self.stories

    def feed_report(self):
        return dict(recovered=False, notes='Two reconstructed November 2010 automotive stories; original feed selection/wording unavailable.',
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
             'Shakira release date is confirmed by a later Sony announcement; its July results are not used as May news.']

    def feed_report(self):
        return dict(snapshot='2010-05-13', recovered=False,
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
        runtime_host = {'swf': 'https://unpkg.com', 'jar': 'https://cjrtnc.leaningtech.com'}.get(self.app.site.kind, '')
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
        path = urllib.parse.unquote(urllib.parse.urlsplit(self.path).path)
        if any(p in ('.', '..') for p in path.split('/')) or '\\' in path or '\x00' in path:
            return self.reply(400, b'Invalid path')
        if path in ('/', '/index.htm'):
            path = self.app.homepage_url
        if path == self.app.site.client_url:
            return self.reply(200, self.app.client_bytes, self.app.client_mime)
        if self.app.site.kind == 'js':
            if path == '/__reconstruction__/original.js':
                return self.reply(200, self.app.original, self.app.client_mime)
            if path == '/__reconstruction__/native.html':
                return self.reply(404, b'This site uses native browser JavaScript. Open the homepage.')
            if path == '/__reconstruction__/about':
                title = html.escape(self.app.site.hostname)
                page = ('<!doctype html><meta charset="utf-8"><title>' + title + ' reconstruction</title>'
                        '<h1>' + title + '</h1><p>The recovered JavaScript login is at the bottom of the homepage. '
                        'Use the demo password printed by the server. Compose and Send use the original client protocol. '
                        'Reload and log in again to retrieve the scripted reply. Messages are kept only in server memory.</p>'
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
        if path == '/www.rss-to-javascript.com/images/rss-to-jss-small.gif':
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
            body = ('<!doctype html><meta charset="utf-8"><title>Local reconstruction</title><h1>'
                    + html.escape(self.app.site.hostname) + '</h1>'
                    '<p>The page runs the original Java classes. Login with the demo password printed by the server. '
                    'Choose Compose, type a message, and Send. Exit and log in again to retrieve the local demo reply.</p>'
                    '<p>For Java 8 on your desktop: <code>python3 reconstruction.py --native</code>. '
                    'The browser version downloads CheerpJ from its vendor CDN. No old browser plugin is needed.</p>'
                    '<p>All messages are synthetic or supplied by you, stored only in server memory, and cleared on restart. '
                    'Feed replacements and missing artwork are documented below. '
                    'Original news hyperlinks retain their destinations. Use the original applet on the homepage to log in.</p>'
                    '<p>The browser Java runtime is supplied by <a href="https://cheerpj.com/">CheerpJ / Leaning Technologies</a>.</p>'
                    '<p>The original server private key and password are unavailable. Four connection settings are replaced; '
                    'all 96 Java classes are byte-identical. Obsolete signatures are removed from the in-memory demo JAR.</p>'
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
        except (ValueError, TypeError, IndexError, OSError) as error:
            print(self.app.site.kind.upper() + ': rejected request (' + str(error) + ')', flush=True)
            return self.reply(403, b'Bad login or invalid request')
        return self.reply(200, result, self.app.response_mime)


def self_test(app):
    """Execute the original protocol classes; substitute only HTTP transport with pipes.

    This works offline and does not need a desktop, a browser runtime, or permission
    to connect to loopback. It is not a claim of a CheerpJ or live-HTTP smoke test.
    """
    if not shutil.which('javac') or not shutil.which('java'):
        raise SystemExit('The offline integration check needs a JDK (java and javac).')
    with tempfile.TemporaryDirectory(prefix='newsbeat-check-') as temp:
        root = Path(temp)
        (root / 'today.jar').write_bytes(app.jar)
        source = root / 'OriginalClientCheck.java'
        source.write_text(JAVA_CHECK.replace('__APPLET_PACKAGE__', app.applet_package))
        subprocess.run(['javac', '-source', '8', '-target', '8', '-cp', str(root / 'today.jar'), str(source)], check=True)
        process = subprocess.Popen(['java', '-Djava.awt.headless=true', '-cp', str(root / 'today.jar') + ':' + temp,
                                    'OriginalClientCheck', app.password.decode(), DEMO_REPLY, app.endpoint],
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
                raise SystemExit('Original Java client integration check failed')
        finally:
            timer.cancel()
            if process.poll() is None:
                process.kill()
                process.wait()
    if not app.report()['all_class_bytes_identical']:
        raise AssertionError('Java classes changed')
    if not any(m['body'] == bytes([0, 1, 2, 3, 255]) for m in app.received):
        raise AssertionError('Uploaded bytes were not preserved')
    if not any(m['body'] == 'Original Java client round trip: café 世界'.encode() for m in app.received):
        raise AssertionError('UTF-8 message was not preserved')
    expected_categories = ['metadata', 'metadata'] if app.config.get('MetaDataExt') else []
    expected_categories += ['text', 'file']
    if [m['category'] for m in app.received] != expected_categories:
        raise AssertionError('Outgoing text, file, or login metadata misclassified')
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


def javascript_self_test(app):
    """Run the recovered client under Node with only DOM and HTTP transport mocked."""
    if not shutil.which('node'):
        raise SystemExit('The JavaScript integration check needs Node.js (node). The website needs only a browser.')
    with tempfile.TemporaryDirectory(prefix='javascript-check-') as temp:
        root = Path(temp)
        source, runner = root / 'driver.js', root / 'check.js'
        source.write_bytes(app.client_bytes)
        runner.write_text(JAVASCRIPT_CHECK)
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
    assert app.report()['only_documented_changes']
    text_messages = [m for m in app.received if m['category'] == 'text']
    assert len(text_messages) == 1
    assert text_messages[0]['body'].decode().endswith('Original JS: café 世界')
    metadata = [m for m in app.received if m['category'] == 'metadata']
    assert len(metadata) == (3 if app.sends_metadata else 0)
    assert len(app.messages) == 1


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument('hostname', nargs='?', default=DEFAULT_HOST, help='site to reconstruct (default: %(default)s)')
    parser.add_argument('--list', action='store_true', help='list supported hostnames in JAR, SWF and JS sections')
    parser.add_argument('--dump', type=Path, default=DEFAULT_DUMP, help='archive root; default: ../../cia-2010-websites-dump/ relative to this script')
    parser.add_argument('--port', type=int, default=8000)
    parser.add_argument('--password', default='demodemodemo', help='JAR/JS demo password (default: %(default)s); never an original credential')
    parser.add_argument('--native', action='store_true', help='launch Java 8 appletviewer in addition to serving the website')
    parser.add_argument('--check', action='store_true', help='report client preservation and reconstruction provenance, then exit')
    parser.add_argument('--self-test', action='store_true', help='offline Java/JavaScript client integration or SWF/AMF protocol checks')
    args = parser.parse_args()
    if args.list:
        for kind in ('jar', 'swf', 'js'):
            print(('\n' if kind != 'jar' else '') + kind.upper())
            for site in SITES.values():
                if site.kind == kind:
                    print(f'  {site.hostname:30} {site.language:2}  {site.description}')
        return
    hostname = args.hostname.lower().removeprefix('www.').rstrip('.')
    hostname = next((name for name in SITES if name.removeprefix('www.') == hostname), hostname)
    if hostname not in SITES:
        parser.error('Unsupported hostname: ' + args.hostname + '. Use --list to see supported sites.')
    if not 1 <= args.port <= 65535 or not 1 <= len(args.password.encode()) <= 64:
        parser.error('Use a port in 1..65535 and a password of 1..64 UTF-8 bytes')
    site = SITES[hostname]
    if args.native and site.kind != 'jar':
        parser.error('--native uses Java appletviewer and applies to JAR sites only. Use the browser for this site.')
    factory = {'jar': Reconstruction, 'swf': FlashReconstruction, 'js': JavaScriptReconstruction}[site.kind]
    app = factory(args.dump.resolve(), args.port, args.password, site)
    if args.self_test:
        {'jar': self_test, 'swf': flash_self_test, 'js': javascript_self_test}[site.kind](app)
        return
    if args.check:
        print(json.dumps(app.report(), indent=2))
        return
    server = ThreadingHTTPServer(('127.0.0.1', args.port), Handler)
    server.app = app
    viewer = None
    print('Site: ' + app.origin + '/', flush=True)
    print('Reconstructing: ' + app.site.hostname, flush=True)
    if site.kind in ('jar', 'js'):
        print('Demo password: ' + args.password, flush=True)
    else:
        print('SWF: original content loader with reconstructed server responses; no recovered login/messaging UI.', flush=True)
    print('Archive read in place: ' + str(app.root), flush=True)
    if site.kind in ('jar', 'js'):
        action = 'Reload' if site.kind == 'js' else 'Exit'
        print('Messages live only in memory. ' + action + ' and log in again to retrieve demo replies.', flush=True)
    if site.kind == 'js':
        print('JavaScript: scroll to Login at the bottom, or open ' + app.origin + '/#' + app.display_div, flush=True)
    if args.native:
        executable = shutil.which('appletviewer')
        if not executable:
            server.server_close()
            raise SystemExit('Java 8 appletviewer was not found. Use the browser version or install a Java 8 JDK.')
        viewer = subprocess.Popen([executable, app.origin + '/__reconstruction__/native.html'])
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        pass
    finally:
        server.server_close()
        if viewer and viewer.poll() is None:
            viewer.terminate()


if __name__ == '__main__':
    main()
