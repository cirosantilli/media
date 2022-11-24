package replay.display;

import java.awt.Component;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static replay.display.ArchivedApi.*;

// The retained archived classes implement the serialized wire types, reliable
// transport, authentication, and encryption. This source composes those APIs.
final class DisplayMailbox {
    interface Transport { byte[] post(byte[] request) throws Exception; }

    static final class Display {
        final String text;
        final Component image;
        final long duration;
        final int speed;

        Display(String text, Component image, long duration, int speed) {
            this.text = text;
            this.image = image;
            this.duration = duration;
            this.speed = speed;
        }
    }

    static final class Message {
        final Object record;
        final long id;
        final String subject;

        Message(Object record) throws Exception {
            this.record = record;
            id = (Long) invoke(record, "d", long.class);
            subject = String.valueOf(invoke(record, "b", Object.class));
        }

        @Override public String toString() { return subject; }
    }

    private final Transport transport;
    private final Object client = create("c.a.d.d.g");
    private final Object outgoing = create("c.a.d.o");
    private final Object unread = create("c.a.d.o");
    private final Object read = create("c.a.d.o");
    private final Object actions = create("c.a.d.l");
    private final Object acknowledgements = create("c.a.d.l");
    private final Object requests;
    private final Object requestEncoder;
    private final Object responseDecoder;
    private Object messageCodec;
    private byte[] credentialBytes;
    private boolean rejected;
    private long lastMessageId;

    DisplayMailbox(Transport transport) throws Exception {
        this.transport = transport;
        connectStreams();
        Object reliable = create("a.a.g.a.d.b", client);
        final int[] supplied = {0};

        // c.a.g.b.a(c.b.i.r[]) serializes each display request and fills its
        // secure field from c.b.m.f, including the original length accounting.
        Object source = listener("c.b.m.f", (proxy, method, args) -> {
            if (method.getName().equals("b")) return supplied[0];
            int size = (Integer) args[0];
            byte[] data = bytes(reliable, "a", size);
            supplied[0] = data.length;
            return Arrays.copyOf(data, size);
        });
        Object sink = listener("c.b.m.c", (proxy, method, args) -> {
            set(reliable, "a", (byte[]) args[0]);
            return null;
        });
        requestEncoder = create("c.a.g.b", source, Integer.parseInt(config().getProperty("Connection.Request.SecureSize")));
        responseDecoder = create("c.a.a.b", sink);
        requests = Array.newInstance(Class.forName("c.b.i.r"), 1);
        Array.set(requests, 0, create("c.b.i.m", "display", "", 0));
    }

    // c.a.e.d.a() reads the encrypted c/a/e/Applet.properties resource.
    static Properties config() throws Exception {
        return (Properties) invoke(Class.forName("c.a.e.d"), "a", Properties.class);
    }

    // a.a.i.a.a is the original sequence state machine, including its
    // hashing, clear key and final trigger. Event 1 opens the login form.
    static KeyListener activationListener(Runnable open) throws Exception {
        Properties config = config();
        Object keys = create("a.a.i.a.a");
        set(keys, "a", config.getProperty("KeyListener.KeySequence"));
        set(keys, "a", config.getProperty("KeyListener.StickyKey").charAt(0));
        set(keys, "b", config.getProperty("KeyListener.ClearKey").charAt(0));
        set(keys, "a", listener("a.a.i.a.b", (proxy, method, args) -> {
            if ((Integer) invoke(args[0], "a", int.class) == 1) open.run();
            return null;
        }));
        return (KeyListener) keys;
    }

    // c.a.d.d.c.c connects the upload, unread, read and action streams
    // to the client dispatcher c.a.d.d.g.a(c.a.d.d.c.c).
    private void connectStreams() throws Exception {
        set(Class.forName("c.a.d.a.h"), "b");
        Object streams = create("c.a.d.d.c.c");
        set(streams, "a", outgoing);
        set(streams, "b", unread);
        set(streams, "c", read);
        set(streams, "a", actions);
        set(streams, "b", acknowledgements);
        set(streams, "a", new byte[0]);
        set(client, "a", streams);

        // c.a.d.d.g.a(c.a.d.e.c) publishes the encrypted message key.
        // c.a.d.f.e.a(byte[]) unwraps it with a.a.e.h.b.b(byte[], byte[])
        // using the account bytes immediately followed by the password bytes.
        set(client, "a", listener("c.a.d.e.b", (proxy, method, args) -> {
            byte[] wrapped = bytes(args[0], "a");
            byte[] key = bytes(Class.forName("a.a.e.h.b"), "b", wrapped, credentialBytes);
            Arrays.fill(credentialBytes, (byte) 0);
            credentialBytes = null;
            messageCodec = create("c.a.d.e.a", key);
            return null;
        }));
        set(client, "a", listener("c.a.d.c.f", (proxy, method, args) -> {
            rejected = !(Boolean) invoke(args[0], "b", boolean.class);
            if (rejected && credentialBytes != null) {
                Arrays.fill(credentialBytes, (byte) 0);
                credentialBytes = null;
            }
            return null;
        }));
    }

    // c.a.d.d.e.d supplies UTF-8 credentials and the configured shared secret.
    // c.a.d.d.g.b() advances authentication on the following display requests.
    void login(String account, String password) throws Exception {
        rejected = false;
        Object credentials = create("c.a.d.d.e.d");
        byte[] userBytes = account.getBytes(StandardCharsets.UTF_8);
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        credentialBytes = Arrays.copyOf(userBytes, userBytes.length + passwordBytes.length);
        System.arraycopy(passwordBytes, 0, credentialBytes, userBytes.length, passwordBytes.length);
        set(credentials, "a", userBytes);
        set(credentials, "b", passwordBytes);
        String hex = config().getProperty("Authentication.SharedSecret");
        byte[] secret = new byte[hex.length() / 2];
        for (int i = 0; i < secret.length; ++i) {
            secret[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        set(credentials, "c", secret);
        set(client, "a", credentials);
        set(client, "b");
    }

    boolean isAuthenticated() { return messageCodec != null; }
    boolean isRejected() { return rejected; }

    // c.a.a.b.a(byte[]) dispatches the extracted secure bytes to the reliable
    // transport and returns the display elements from the same servlet response.
    Display poll() throws Exception {
        byte[] response = transport.post(bytes(requestEncoder, "a", requests));
        Object displays = invoke(responseDecoder, "a", Class.forName("[Lc.b.e.b.d;"), response);
        Object display = Array.get(displays, 0);
        Object view = invoke(display, "a", Class.forName("c.b.o.a.h"));
        Object effect = invoke(display, "b", Class.forName("c.b.n.a.a"));
        // c.b.o.a.d.b() / c.b.n.a.b.a(): ticker string and pixels per 1600 ms.
        if (view.getClass().getName().equals("c.b.o.a.d")) {
            return new Display((String) invoke(view, "b", String.class), null, 0,
                               (Integer) invoke(effect, "a", int.class));
        }
        // c.b.o.a.a.paint() draws the decoded image. c.b.n.a.c.a() gives milliseconds.
        return new Display(null, (Component) view,
                           (Long) invoke(effect, "a", long.class), 0);
    }

    // c.a.d.o.b(int) indexes the two downloaded folders, both keyed by record ID.
    List<Message> messages() throws Exception {
        Map<Long, Message> result = new LinkedHashMap<>();
        for (Object folder : new Object[]{unread, read}) {
            int count = (Integer) invoke(folder, "b", int.class);
            for (int i = 0; i < count; ++i) {
                Message message = new Message(invoke(folder, "b", Class.forName("c.a.d.g"), i));
                result.put(message.id, message);
            }
        }
        return new ArrayList<>(result.values());
    }

    // c.a.d.e.a decodes the message body. Original selection emits action 300
    // while opening marks it read with action 200. Action 100 deletes it.
    String open(Message message) throws Exception {
        action(300, message);
        action(200, message);
        return new String(bytes(messageCodec, "b", bytes(message.record, "a")), StandardCharsets.UTF_8);
    }

    void delete(Message message) throws Exception {
        action(100, message);
        invoke(unread, "d", Class.forName("c.a.d.g"), message.id);
        invoke(read, "d", Class.forName("c.a.d.g"), message.id);
    }

    private void action(int kind, Message message) throws Exception {
        set(actions, "a", create("c.a.d.k", kind, message.record));
    }

    // c.a.d.g stores the encrypted body, subject and ID. c.a.d.o holds it
    // until the server acknowledges upload through the normal dispatcher.
    void send(String subject, String body) throws Exception {
        Object record = create("c.a.d.g");
        set(record, "a", bytes(messageCodec, "a", body.getBytes(StandardCharsets.UTF_8)));
        set(record, "a", subject);
        long id = Math.max(System.currentTimeMillis(), lastMessageId + 1);
        lastMessageId = id;
        set(record, "a", id);
        set(outgoing, "a", id, record);
    }

    boolean hasPendingMessages() throws Exception {
        return (Integer) invoke(outgoing, "b", int.class) != 0;
    }
}
