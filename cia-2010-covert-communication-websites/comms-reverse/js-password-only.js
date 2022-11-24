/* Password-only CGI protocol. Method names in comments refer to
   Iran Football Source's futbol.js. Classy Motors' driver.js supplies the
   hexadecimal variant. The replay server injects the per-site settings. */
"use strict";

(() => {
    const text = new TextDecoder();
    const zeroIV = new Uint8Array(16);
    const random = length => crypto.getRandomValues(new Uint8Array(length));
    const base64 = bytes => btoa(Array.from(bytes, byte => String.fromCharCode(byte)).join(""));
    const unbase64 = value => Uint8Array.from(atob(value), ch => ch.charCodeAt(0));
    const unhex = value => Uint8Array.from(value.match(/../g) || [], pair => parseInt(pair, 16));
    const hex = bytes => Array.from(bytes, byte => byte.toString(16).padStart(2, "0")).join("").toUpperCase();

    function concat(...parts) {
        const result = new Uint8Array(parts.reduce((size, part) => size + part.length, 0));
        let offset = 0;
        for (const part of parts) {
            result.set(part, offset);
            offset += part.length;
        }
        return result;
    }

    // dc(): write each UTF-16 code unit as one or two bytes, including surrogates.
    function passwordBytes(value) {
        const result = [];
        for (let i = 0; i < value.length; ++i) {
            const unit = value.charCodeAt(i);
            if (unit > 255) result.push(unit >> 8);
            result.push(unit & 255);
        }
        return Uint8Array.from(result);
    }

    // dp() / dn(): the original message codec encodes UTF-16 code units.
    // Surrogate pairs therefore occupy six bytes on the wire.
    function encodeMessage(value) {
        const bytes = [];
        for (let i = 0; i < value.length; ++i) {
            const unit = value.charCodeAt(i);
            if (unit < 128) bytes.push(unit);
            else if (unit < 2048) bytes.push(192 | unit >> 6, 128 | unit & 63);
            else bytes.push(224 | unit >> 12, 128 | (unit >> 6) & 63, 128 | unit & 63);
        }
        return Uint8Array.from(bytes);
    }

    function decodeMessage(bytes) {
        let value = "";
        for (let i = 0; i < bytes.length;) {
            const leading = bytes[i++];
            if (leading < 128) {
                value += String.fromCharCode(leading);
                continue;
            }
            const count = leading >= 240 ? 3 : leading >= 224 ? 2 : 1;
            if (leading < 192 || leading >= 248 || i + count > bytes.length) {
                throw new Error("Malformed message encoding");
            }
            let unit = leading & (127 >> (count + 1));
            for (let j = 0; j < count; ++j) {
                const next = bytes[i++];
                if ((next & 192) !== 128) throw new Error("Malformed message encoding");
                unit = (unit << 6) | (next & 63);
            }
            value += String.fromCodePoint(unit);
        }
        return value;
    }

    // oc.gd(): repeatedly apply SHA-256. ic() uses one round. kt() and ed() use 100.
    async function hash(bytes, rounds = 100) {
        for (let i = 0; i < rounds; ++i) {
            bytes = new Uint8Array(await crypto.subtle.digest("SHA-256", bytes));
        }
        return bytes;
    }

    // gl(): zero-IV AES-CBC, with PKCS#7 enabled for messages.
    async function aes(bytes, keyBytes, decrypt = false, iv = zeroIV) {
        const key = await crypto.subtle.importKey("raw", keyBytes, "AES-CBC", false, ["encrypt", "decrypt"]);
        const method = decrypt ? "decrypt" : "encrypt";
        return new Uint8Array(await crypto.subtle[method]({name: "AES-CBC", iv}, key, bytes));
    }

    // ic(): the stored shared secret uses CBC without padding. Generate a final
    // padding block whose chaining IV is the last block of the stored ciphertext.
    async function sharedSecret(sealed, password) {
        const key = await hash(password, 1);
        const pad = await aes(new Uint8Array(0), key, false, sealed.slice(-16));
        return aes(concat(sealed, pad), key, true);
    }

    // pe.fe() / fz(): RSAES-PKCS1-v1_5 followed by the wrapped password.
    // The 128-byte RSA ciphertext comes from the replay server's injected key.
    function wrapKey(key, modulus, exponent) {
        const padding = random(128 - key.length - 3);
        for (let i = 0; i < padding.length; ++i) {
            while (!padding[i]) padding[i] = random(1)[0];
        }
        const block = concat([0, 2], padding, [0], key);
        const n = BigInt("0x" + modulus);
        let base = block.reduce((number, byte) => (number << 8n) + BigInt(byte), 0n);
        let power = BigInt("0x" + exponent);
        let result = 1n;
        while (power) {
            if (power & 1n) result = result * base % n;
            base = base * base % n;
            power >>= 1n;
        }
        return unhex(result.toString(16).padStart(256, "0"));
    }

    class Mailbox {
        constructor(config, transport = Mailbox.post) {
            this.config = config;
            this.transport = transport;
            // qy.de()/dt() for Base64, pr.de()/dt() for hexadecimal.
            this.encode = config.encoding === "h" ? hex : base64;
            this.decode = config.encoding === "h" ? unhex : unbase64;
        }

        // ju.gr() exchanges the CGI request body.
        static async post(endpoint, body) {
            const response = await fetch(endpoint, {method: "POST", body});
            if (!response.ok) throw new Error("Request failed (" + response.status + ")");
            return response.text();
        }

        // func1() → nf() → ic()/rp(): recover the shared secret and start login.
        // jw() masks the password, jv.nx() starts the handshake, ia() parses it,
        // and jv.nw()/ec() exchange the initial session challenge.
        async login(value) {
            const password = passwordBytes(value);
            const {sealedSecret, modulus, exponent, endpoint, encoding} = this.config;
            this.secret = await sharedSecret(unhex(sealedSecret), password);
            this.key = await hash(concat(password, this.secret));
            const mask = await hash(this.secret.slice(0, password.length));
            const masked = password.map((byte, i) => byte ^ mask[i % mask.length]);
            const wrappingKey = random(32);
            const envelope = concat([128], wrapKey(wrappingKey, modulus, exponent),
                                    await aes(masked, wrappingKey));
            const reply = this.decode(await this.transport(endpoint, encoding + this.encode(envelope)));
            if (reply.length !== 48) throw new Error("Malformed login reply");
            this.token = text.decode(reply.slice(0, 16));
            this.challenge = reply.slice(16);
            await this.command([0]);
        }

        // jv.he() / ed(): answer the last challenge. hj() parses the response.
        async command(command) {
            const proof = await hash(concat(this.challenge, this.secret));
            const reply = this.decode(await this.transport(
                this.config.endpoint, this.token + this.encode(concat(proof, command))));
            if (reply.length < 33 || reply[32] !== command[0] || (reply.length > 33 && reply[33] !== 0)) {
                throw new Error("Invalid command reply");
            }
            this.challenge = reply.slice(0, 32);
            return reply.slice(34);
        }

        // oq.kb() / cr() / fq(): first/next inbox commands (32 / 18).
        // kw.om() unpacks the encrypted timestamp, extension and body.
        // oq.mc() orders the completed download by time.
        async inbox() {
            const messages = [];
            for (let reply = await this.command([32, random(1)[0]]); reply.length;) {
                const [id, metadataLength] = reply;
                const bodyOffset = 2 + metadataLength;
                const metadata = await aes(reply.slice(2, bodyOffset), this.key, true);
                const timestamp = new DataView(metadata.buffer).getUint32(0, true);
                const marker = text.decode(metadata.slice(4));
                const body = decodeMessage(await aes(reply.slice(bodyOffset), this.key, true));
                messages.push({id, timestamp, marker, body});
                reply = await this.command([18, id]);
            }
            return messages.sort((first, next) => first.timestamp - next.timestamp);
        }

        // func8() / eq() / kw.qd() / eh(): send a text record with the configured
        // marker, normalizing CRLF to LF before encrypting the body.
        async send(body) {
            const timestamp = new Uint8Array(4);
            new DataView(timestamp.buffer).setUint32(0, Math.floor(Date.now() / 1000), true);
            const metadata = await aes(concat(timestamp, passwordBytes(this.config.marker)), this.key);
            const content = await aes(encodeMessage(body.replace(/\r\n/g, "\n")), this.key);
            await this.command(concat([37, random(1)[0], 0, metadata.length], metadata, content));
        }

        // func4() / fa(): delete by the same message ID as the inbox record.
        async remove(id) {
            await this.command([21, id]);
        }
    }

    function element(tag, label) {
        const node = document.createElement(tag);
        if (label) node.textContent = label;
        return node;
    }

    // func0() locates displayDiv. mj() references nl for the archived submit
    // image. The form and mailbox controls are new and use text-only toolbars.
    function mount() {
        const config = window.passwordMailboxConfig;
        const mountPoint = config && document.getElementById(config.container);
        if (!mountPoint) return;
        const panel = element("section");
        panel.style.cssText = "box-sizing:border-box;margin:1rem 0;padding:1rem;background:#f8fafc;color:#17212b;border:1px solid #94a3b8;text-align:left";
        const title = element("h2", "Reconstruction mailbox");
        const controls = element("div");
        const status = element("p");
        status.setAttribute("role", "status");
        panel.append(title, controls, status);
        mountPoint.append(panel);

        function button(label, action, parent = controls) {
            const node = element("button", label);
            node.type = "button";
            node.onclick = () => run(action);
            parent.append(node);
            return node;
        }

        async function run(action) {
            const buttons = Array.from(controls.querySelectorAll("button"));
            buttons.forEach(node => { node.disabled = true; });
            status.textContent = "";
            try {
                await action();
            } catch (error) {
                status.textContent = error.message;
            } finally {
                buttons.forEach(node => { node.disabled = false; });
            }
        }

        function loginForm() {
            controls.replaceChildren();
            const form = element("form");
            const label = element("label", "Demo password ");
            const password = element("input");
            password.type = "password";
            password.required = true;
            label.append(password);
            const submit = element("button", " Open mailbox");
            if (config.loginImage) {
                const icon = element("img");
                icon.src = config.loginImage;
                icon.alt = "";
                icon.onerror = () => icon.remove();
                submit.prepend(icon);
            }
            form.append(label, submit);
            controls.append(form);
            form.onsubmit = event => {
                event.preventDefault();
                run(async () => {
                    const mailbox = new Mailbox(config);
                    await mailbox.login(password.value);
                    showMailbox(mailbox, await mailbox.inbox());
                });
            };
        }

        function showMailbox(mailbox, messages, draft = "") {
            controls.replaceChildren();
            const list = element("ul");
            const body = element("pre");
            body.style.whiteSpace = "pre-wrap";
            const actions = element("div");
            controls.append(list, body, actions);
            // func6() / pq(): read the body from the downloaded inbox.
            for (const message of messages) {
                const item = element("li");
                button("Message " + message.id, () => {
                    body.textContent = message.body;
                    actions.replaceChildren();
                    button("Quote", () => {
                        // func3() / lq(): append the selected message to the draft.
                        editor.value += " \n--------------------\n" + message.body.replace(/^\uFEFF/, "") + "\n";
                        editor.focus();
                    }, actions);
                    button("Delete", async () => {
                        await mailbox.remove(message.id);
                        showMailbox(mailbox, messages.filter(entry => entry.id !== message.id), editor.value);
                    }, actions);
                }, item);
                list.append(item);
            }
            if (!messages.length) list.append(element("li", "Inbox is empty"));
            const editor = element("textarea");
            editor.setAttribute("aria-label", "Message");
            editor.value = draft;
            controls.append(editor);
            button("Send", async () => {
                if (!editor.value.trim()) return;
                await mailbox.send(editor.value);
                editor.value = "";
                status.textContent = "Message sent.";
            });
            button("Sign out", loginForm);
        }

        loginForm();
    }

    globalThis.PasswordMailbox = Mailbox;
    if (typeof window !== "undefined") window.func0 = mount;
})();
