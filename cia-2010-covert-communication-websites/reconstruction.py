#!/usr/bin/env python3
"""Run an archived website and its original Java applet against a local demo server.

python3 reconstruction.py             # modern browser + hosted CheerpJ runtime
python3 reconstruction.py --native    # also launch installed Java 8 appletviewer
python3 reconstruction.py --check     # check archive/config/class preservation
python3 reconstruction.py --list      # list supported hostnames
python3 reconstruction.py iraniangoalkicks.com

The archive defaults to ../../cia-2010-websites-dump/, resolved relative to this
script, independently of the working directory. Open http://127.0.0.1:8000/ and
log in using the original applet with the password printed by the server.
The optional hostname selects a site; the default is latinamericanewsbeat.com.
The password and scripted demo conversation are the same for every site.

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
The selected sites share one recovered client/protocol family. Other JAR families,
including some of the article's featured gallery examples, are not supported yet.
--check and --self-test apply to the selected hostname. The latter runs its actual
Java protocol classes offline; it does not validate CheerpJ's graphical rendering.
Run notes: /__reconstruction__/about
Source hashes and change inventory: /__reconstruction__/provenance.json
"""

import argparse
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
DEFAULT_DUMP = Path(__file__).resolve().parent / '../../cia-2010-websites-dump'
MAX_UPLOAD = 5 * 1024 * 1024  # Cm.c(): min(hex(MaxFileSize), 5120) * 1024


@dataclass(frozen=True)
class Site:
    hostname: str
    homepage: str
    jar: str
    description: str
    language: str = 'en'
    presentation: str = 'generic'
    notes: tuple = ()

    @property
    def capture(self):
        return self.homepage.split('/')[0]

    @property
    def jar_url(self):
        return '/' + self.jar.split('/', 1)[1]


# Explicit, reviewable snapshots; adding a profile does not replace its Java UI.
# These use the same recovered resource/protocol family, with distinct original
# entry classes, labels, dimensions, endpoints and transport encodings.
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


class Reconstruction:
    def __init__(self, dump, port, password, site=None):
        self.site = site or SITES[DEFAULT_HOST]
        self.root = (dump / self.site.hostname).resolve()
        self.origin = 'http://127.0.0.1:' + str(port)
        self.password = password.encode('utf-8')
        self.secret = secrets.token_bytes(32)
        self.key = digest(self.password + self.secret)
        self.private_key = rsa.generate_private_key(public_exponent=65537, key_size=1024)
        self.lock = threading.Lock()
        self.sessions = {}
        self.messages = {}
        self.received = []
        self.next_id = 1
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
        self.applet = AppletMetadata(decode_html(self.files[self.homepage_url].read_bytes())).applet
        self.applet_class = self.applet['code'].removesuffix('.class').replace('/', '.')
        self.applet_package = self.applet_class.rsplit('.', 1)[0]
        self.original = (self.root / self.site.jar).read_bytes()
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
                token, challenge = secrets.token_bytes(16), secrets.token_bytes(32)
                if len(self.sessions) >= 64:
                    raise ValueError('Too many demo sessions')
                self.sessions[token] = dict(challenge=challenge, encoding=encoding, authenticated=False, touched=now)
                print('Applet: login handshake accepted', flush=True)
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
                print('Applet: deleted message ' + str(argument), flush=True)
            elif op == 37:  # send text, upload a file, or submit login metadata
                body, marker, timestamp = unpack_message(packet[35:], self.key)
                if len(body) > MAX_UPLOAD:
                    raise ValueError('File too large')
                # The applet's automatic environment report leaves Aj.type at -1.
                # Bb sends both text and files with type 1, distinguished only by
                # MessageMarker versus Cm's filename extension. An uploaded file
                # with that same extension is indistinguishable from text here.
                if argument == 255 and marker == self.config.get('MetaDataExt'):
                    category = 'metadata'
                    reply = None
                elif argument == 1:
                    if marker == self.config.get('MessageMarker', 'txt'):
                        category = 'text'
                        reply = DEMO_REPLY.encode()
                    else:
                        category = 'file'
                        reply = ('LOCAL DEMO REPLY\n\nReceived file marker: ' + marker + '\nBytes: ' + str(len(body))).encode()
                else:
                    raise ValueError('Unsupported outgoing message type: ' + str(argument))
                self.received.append(dict(body=body, marker=marker, timestamp=timestamp,
                                          kind=argument, category=category))
                if len(self.received) > 100:
                    self.received.pop(0)
                if reply is None:
                    print('Applet: acknowledged login metadata (' + str(len(body)) + ' bytes); no inbox reply', flush=True)
                else:
                    reply_id = self.add_message(reply, 'dat')
                    print('Applet: received ' + category + ' (' + str(len(body)) + ' bytes); queued demo reply ' + str(reply_id), flush=True)
                # Acknowledge the send without piggybacking the queued message.
                # The next login retrieves it through the ordinary inbox commands.
            else:
                raise ValueError('Unsupported command: ' + str(op))
            response = session['challenge'] + bytes([op, 0])
            if item is not None:
                body, marker, timestamp = self.messages[item]
                response += bytes([item]) + pack_message(body, marker, timestamp, self.key)
            return self.encode(response, session['encoding'])

    def report(self):
        with zipfile.ZipFile(io.BytesIO(self.original)) as old, zipfile.ZipFile(io.BytesIO(self.jar)) as new:
            classes = [n for n in old.namelist() if n.endswith('.class')]
            identical = all(old.read(n) == new.read(n) for n in classes)
        return dict(domain=self.site.hostname, source=str(self.root), homepage=self.site.homepage,
                    jar=self.site.jar, original_sha256=hashlib.sha256(self.original).hexdigest(),
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
        self.send_header('Content-Security-Policy', "default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval' blob: https://cjrtnc.leaningtech.com; connect-src 'self' https://cjrtnc.leaningtech.com; img-src 'self' data: blob: https://cjrtnc.leaningtech.com; style-src 'self' 'unsafe-inline' https://cjrtnc.leaningtech.com; font-src 'self' data: https://cjrtnc.leaningtech.com; worker-src 'self' blob: https://cjrtnc.leaningtech.com; frame-src 'self' blob: https://cjrtnc.leaningtech.com; object-src 'none'; base-uri 'self'; form-action 'self'")
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
        if path == self.app.site.jar_url:
            return self.reply(200, self.app.jar, 'application/java-archive')
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
            page = ('<html><body><applet archive="' + html.escape(self.app.site.jar_url) + '" code="'
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
        if file is None and path.startswith('/images/'):
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
            print('Applet: rejected request (' + str(error) + ')', flush=True)
            return self.reply(403, b'Bad login or invalid request')
        return self.reply(200, result, 'application/octet-stream')


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


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument('hostname', nargs='?', default=DEFAULT_HOST, help='site to reconstruct (default: %(default)s)')
    parser.add_argument('--list', action='store_true', help='list supported hostnames and exit')
    parser.add_argument('--dump', type=Path, default=DEFAULT_DUMP, help='archive root; default: ../../cia-2010-websites-dump/ relative to this script')
    parser.add_argument('--port', type=int, default=8000)
    parser.add_argument('--password', default='demodemodemo', help='local demo password (default: %(default)s); never an original credential')
    parser.add_argument('--native', action='store_true', help='launch Java 8 appletviewer in addition to serving the website')
    parser.add_argument('--check', action='store_true', help='verify JAR preservation and resource round trip, then exit')
    parser.add_argument('--self-test', action='store_true', help='run original Java protocol classes against the Python server logic, offline')
    args = parser.parse_args()
    if args.list:
        for site in SITES.values():
            print(f'{site.hostname:30} {site.language:2}  {site.description}')
        return
    hostname = args.hostname.lower().removeprefix('www.').rstrip('.')
    if hostname not in SITES:
        parser.error('Unsupported hostname: ' + args.hostname + '. Use --list to see supported sites.')
    if not 1 <= args.port <= 65535 or not 1 <= len(args.password.encode()) <= 64:
        parser.error('Use a port in 1..65535 and a password of 1..64 UTF-8 bytes')
    app = Reconstruction(args.dump.resolve(), args.port, args.password, SITES[hostname])
    if args.self_test:
        self_test(app)
        return
    if args.check:
        print(json.dumps(app.report(), indent=2))
        return
    server = ThreadingHTTPServer(('127.0.0.1', args.port), Handler)
    server.app = app
    viewer = None
    print('Site: ' + app.origin + '/', flush=True)
    print('Reconstructing: ' + app.site.hostname, flush=True)
    print('Demo password: ' + args.password, flush=True)
    print('Archive read in place: ' + str(app.root), flush=True)
    print('Messages live only in memory. Exit and log in again to retrieve demo replies.', flush=True)
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
