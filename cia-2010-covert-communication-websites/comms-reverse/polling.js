/* XML reference: the-open-book-online.com/20110202225254/breaking.js.
   Text reference: al-ashak-news-me.com/20110203003835/ashak.js.
   Both POST variants share the queue. Their request fields and response parsers differ. */
"use strict";

(() => {
    function randomBytes(size) {
        return Uint8Array.from({length: size}, () => Math.floor(Math.random() * 256));
    }

    // breaking.js d()/z() and ashak.js ds()/eu() encode random filler bytes.
    // The field named imagemd5 is hex-encoded randomness, without an MD5 digest.
    const hex = size => Array.from(randomBytes(size), b => b.toString(16).padStart(2, "0")).join("").toUpperCase();
    const base64 = size => btoa(String.fromCharCode(...randomBytes(size)));

    // ashak.js dw()/q()/de.bk(): decode percent escapes and form-style plus signs.
    function unescapeField(value) {
        try {
            return decodeURIComponent(value.replace(/\+/g, " "));
        } catch {
            return value;
        }
    }

    // breaking.js gp.bp(): direct children are records; detail carries the
    // continuation pointer. The final pointer goes back as pointerId.
    function parseXml(doc) {
        if (!doc.documentElement || doc.getElementsByTagName("parsererror").length) {
            throw new Error("Invalid XML polling response");
        }
        return Array.from(doc.documentElement.children, record => {
            const fields = Object.fromEntries(Array.from(record.children, field => [field.tagName, field.textContent]));
            return {pointer: fields.detail, text: fields.txt, image: fields.imagelink,
                    fallback: fields.dname, link: fields.content};
        }).filter(item => item.pointer);
    }

    // ashak.js de.fy(): split records and fields before decoding so escaped
    // delimiters remain part of the value. descript provides the next info value.
    function parseText(body) {
        return body.split(";").map(record => {
            const fields = Object.create(null);
            for (const pair of record.split("&")) {
                const delimiter = pair.indexOf("=");
                if (delimiter !== -1) {
                    fields[unescapeField(pair.slice(0, delimiter))] = unescapeField(pair.slice(delimiter + 1));
                }
            }
            return {pointer: fields.descript, text: fields.txttag, image: fields.imgfeeds,
                    fallback: fields.hdlnurls, link: fields.content};
        }).filter(item => item.pointer);
    }

    const codecs = {
        xml: {
            initialCount: 2,
            refillAt: 2,
            // breaking.js fh.dj(): XML body sent with the original XHR form content type.
            request(doc, target, pointer, seen, count) {
                const xml = doc.implementation.createDocument("", "service", null);
                const fields = {dateline: hex(26), pointerId: pointer, count, hdlnslocs: seen,
                                content: target, initems: base64(27)};
                for (const [key, value] of Object.entries(fields)) {
                    const node = xml.createElement(key);
                    node.textContent = String(value);
                    xml.documentElement.append(node);
                }
                return new XMLSerializer().serializeToString(xml);
            },
            response: body => parseXml(new DOMParser().parseFromString(body, "application/xml")),
        },
        txt: {
            initialCount: 3,
            refillAt: 6,
            // ashak.js n.gs(): the random values are carried verbatim, including Base64 padding.
            request(doc, target, pointer, seen, count) {
                return "feeds=" + target + "&fetchprog=" + base64(28) + "&imagemd5=" + hex(34)
                     + "&imagenumber=" + seen + "&info=" + pointer + "&return=" + count;
            },
            response: parseText,
        },
    };

    function httpUrl(value, base) {
        if (value) {
            try {
                const url = new URL(value, base);
                if (url.protocol === "http:" || url.protocol === "https:") return url;
            } catch {
                return null;
            }
        }
        return null;
    }

    // bt.gi()/cl.ec(): text wins over image, then fallback text. Render markup
    // as text from an inert template, and load replay images from the local origin.
    function render(container, item) {
        const doc = container.ownerDocument;
        const text = item.text || item.fallback;
        const image = item.text ? null : httpUrl(item.image, doc.baseURI);
        let content;
        if (image && image.origin === new URL(doc.baseURI).origin) {
            content = doc.createElement("img");
            content.src = image.href;
            content.alt = item.fallback || "";
        } else {
            const template = doc.createElement("template");
            template.innerHTML = text || "";
            content = doc.createTextNode(template.content.textContent);
        }
        const link = httpUrl(item.link, doc.baseURI);
        if (link) {
            const anchor = doc.createElement("a");
            anchor.href = link.href;
            anchor.target = "_blank";
            anchor.rel = "noopener noreferrer";
            anchor.append(content);
            container.replaceChildren(anchor);
        } else {
            container.replaceChildren(content);
        }
    }

    class Poller {
        constructor(target, endpoint, codec, doc, transport) {
            this.target = target;
            this.endpoint = endpoint;
            this.codec = codec;
            this.doc = doc;
            this.transport = transport;
            this.queue = [];
            this.pointer = "";
            this.seen = 0;
            this.inFlight = null;
            this.container = doc.getElementById(target);
        }

        // fh.ca()/gf() and n.ag()/cy(): request more records and buffer the
        // response, advancing the pointer to the last complete record.
        refill(count) {
            if (!this.inFlight) {
                this.inFlight = this.load(count).catch(error => {
                    if (this.container) this.container.dataset.pollingError = error.message;
                }).finally(() => { this.inFlight = null; });
            }
            return this.inFlight;
        }

        async load(count) {
            const body = this.codec.request(this.doc, this.target, this.pointer, this.seen, count);
            const reply = await this.transport(this.endpoint, {
                method: "POST", body, headers: {"Content-Type": "application/x-www-form-urlencoded"},
            });
            if (!reply.ok) throw new Error("Polling endpoint returned " + reply.status);
            const records = this.codec.response(await reply.text());
            this.seen = 1;
            if (records.length) this.pointer = records[records.length - 1].pointer;
            this.queue.push(...records);
            if (this.container) delete this.container.dataset.pollingError;
        }

        // fh.ct()/n.db(): consume one queued item per tick and top up at low water.
        tick() {
            if (!this.queue.length) {
                this.refill(this.codec.refillAt);
                return;
            }
            const item = this.queue.shift();
            if (this.container) render(this.container, item);
            if (this.queue.length <= this.codec.refillAt) this.refill(this.codec.initialCount);
        }
    }

    // cu()/cd.gd() and fq()/gi.cz(): prime feeds in page order, then rotate them.
    async function start(targets, interval, format, doc = document, transport = fetch) {
        const codec = codecs[format];
        const pollers = targets.map(({endpoint, target}) => new Poller(target, endpoint, codec, doc, transport));
        if (!pollers.length) return () => {};
        for (const poller of pollers) await poller.refill(codec.initialCount);
        const tick = () => pollers.forEach(poller => poller.tick());
        tick();
        const timer = setInterval(tick, interval);
        return () => clearInterval(timer);
    }

    globalThis.ArchivePolling = {start, Poller, codecs, parseXml, parseText};
    if (typeof window !== "undefined") {
        // Preserve the entry functions and per-page settings in the archived HTML.
        window.cu = (feeds, interval) => start(feeds.map(({q, dh}) => ({endpoint: q, target: dh})), interval, "xml");
        window.fq = (feeds, interval) => start(feeds.map(({z, h}) => ({endpoint: z, target: h})), interval, "txt");
    }
})();
