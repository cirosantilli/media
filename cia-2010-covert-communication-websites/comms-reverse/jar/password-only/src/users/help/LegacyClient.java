package users.help;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/** The password-only session and mailbox protocol from Av, O, Ah and Bv. */
final class LegacyClient {
    private static final int FIRST = 32, NEXT = 18, DELETE = 21, SEND = 37;
    private static final int TOKEN_SIZE = 16, CHALLENGE_SIZE = 32, HASH_ROUNDS = 100;
    private static final byte[] ZERO_IV = new byte[16];
    private static final byte[] HEX = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
    private static final SecureRandom RANDOM = new SecureRandom();

    interface Transport {
        byte[] post(byte[] request) throws IOException;
    }

    static final class Configuration {
        final URL endpoint;
        final BigInteger exponent, modulus;
        final byte[] encryptedSecret;
        final int encoding, maxFileBytes;
        final String textMarker;

        Configuration(Map<String, byte[]> resources) throws IOException {
            endpoint = new URL(required(resources, "ServerURL"));
            exponent = new BigInteger(1, unhex(required(resources, "KeyE")));
            modulus = new BigInteger(1, unhex(required(resources, "KeyN")));
            encryptedSecret = unhex(required(resources, "EncSharedSecret"));
            String configuredEncoding = value(resources, "EncodeType", "1");
            encoding = "0".equals(configuredEncoding) ? '1' : "2".equals(configuredEncoding) ? 'h' : 'b';
            maxFileBytes = fileLimit(value(resources, "MaxFileSize", "800"));
            textMarker = value(resources, "MessageMarker", "txt");
        }

        static Configuration load() throws IOException {
            return new Configuration(JarResources.load());
        }

        private static String value(Map<String, byte[]> resources, String name, String fallback) {
            byte[] data = resources.get(name);
            return data == null ? fallback : new String(data, StandardCharsets.UTF_8);
        }

        private static String required(Map<String, byte[]> resources, String name) throws IOException {
            String result = value(resources, name, "");
            if (result.isEmpty()) throw new IOException("Missing resource " + name);
            return result;
        }

        private static int fileLimit(String value) {
            // Cm measures its hexadecimal setting in KiB.
            long kibibytes = 2048;
            try {
                kibibytes = Long.parseLong(value.replaceFirst("(?i)^0x", ""), 16);
            } catch (NumberFormatException ignored) {
                // Use the archived default when the setting cannot be parsed.
            }
            return (int) Math.min(Math.max(kibibytes, 0), 5120) * 1024;
        }
    }

    static final class Message {
        final int id;
        final byte[] body;
        final String marker;
        final long timestamp;

        Message(int id, byte[] body, String marker, long timestamp) {
            this.id = id;
            this.body = body;
            this.marker = marker;
            this.timestamp = timestamp;
        }

        String text() {
            return new String(body, StandardCharsets.UTF_8);
        }
    }

    private final Configuration config;
    private final Transport transport;
    private final byte[] secret, messageKey, token;
    private final Map<Integer, Message> inbox = new TreeMap<>();
    private byte[] challenge;

    // Av.a() calls Av.b() for the password envelope and Av.c() to initialize the session.
    LegacyClient(Configuration config, byte[] password, Transport transport)
            throws IOException, GeneralSecurityException {
        this.config = config;
        this.transport = transport;
        if (password.length == 0 || password.length > 64)
            throw new IOException("Enter a password of 1 through 64 UTF-8 bytes.");

        secret = aes(config.encryptedSecret, digest(password, 1), Cipher.DECRYPT_MODE, false);
        if (secret.length != CHALLENGE_SIZE) throw new IOException("Invalid shared-secret length");
        messageKey = digest(join(password, secret), HASH_ROUNDS);

        byte[] mask = digest(Arrays.copyOf(secret, Math.min(password.length, secret.length)), HASH_ROUNDS);
        byte[] maskedPassword = password.clone();
        for (int i = 0; i < maskedPassword.length; i++) maskedPassword[i] ^= mask[i % mask.length];
        byte[] wrappingKey = new byte[32];
        RANDOM.nextBytes(wrappingKey);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(config.modulus, config.exponent));
        Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsa.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] envelope = rsa.doFinal(wrappingKey);
        byte[] login = join(new byte[]{(byte) envelope.length}, envelope,
                            aes(maskedPassword, wrappingKey, Cipher.ENCRYPT_MODE, true));
        byte[] response = decode(transport.post(join(new byte[]{(byte) config.encoding}, encode(login))));
        if (response.length != TOKEN_SIZE + CHALLENGE_SIZE) throw new IOException("Invalid login response");
        token = Arrays.copyOf(response, TOKEN_SIZE);
        challenge = Arrays.copyOfRange(response, TOKEN_SIZE, response.length);
        byte[] initialized = request(new byte[]{0});
        if (initialized.length != CHALLENGE_SIZE + 1 || initialized[CHALLENGE_SIZE] != 0)
            throw new IOException("Invalid session initialization response");
    }

    List<Message> inbox() {
        return new ArrayList<>(inbox.values());
    }

    // O.b() walks the first/next responses and indexes the delivered records.
    void fetchInbox() throws IOException, GeneralSecurityException {
        Map<Integer, Message> refreshed = new TreeMap<>();
        Message message = command(FIRST, 0, null);
        while (message != null) {
            refreshed.put(message.id, message);
            int previous = message.id;
            message = command(NEXT, previous, null);
            if (message != null && message.id <= previous)
                throw new IOException("Inbox IDs are out of order");
        }
        inbox.clear();
        inbox.putAll(refreshed);
    }

    // O.a(Object) sends the delete operation for the selected record ID.
    void delete(int id) throws IOException, GeneralSecurityException {
        if (inbox.containsKey(id)) {
            Message returned = command(DELETE, id, null);
            inbox.remove(id);
            cache(returned);
        }
    }

    // Bb text composition feeds O.a(Aj) after newline normalization.
    void sendText(String text) throws IOException, GeneralSecurityException {
        send(text.replace("\r\n", "\n").getBytes(StandardCharsets.UTF_8), config.textMarker);
    }

    // Cm hands uploaded bytes and the filename extension to the same O.a(Aj) path.
    void sendFile(String name, byte[] contents) throws IOException, GeneralSecurityException {
        if (contents.length > config.maxFileBytes) throw new IOException("File is too large");
        int end = name.length();
        while (end > 0 && name.charAt(end - 1) == '.') end--;
        int start = name.lastIndexOf('.', end - 1) + 1;
        send(contents, name.substring(start, end));
    }

    // Bv.a(Aj, Bf) encrypts the timestamp/marker and the body separately.
    private void send(byte[] body, String marker) throws IOException, GeneralSecurityException {
        byte[] name = marker.getBytes(StandardCharsets.UTF_8);
        byte[] metadata = ByteBuffer.allocate(4 + name.length).order(ByteOrder.LITTLE_ENDIAN)
            .putInt((int) (System.currentTimeMillis() / 1000)).put(name).array();
        byte[] encryptedMetadata = aes(metadata, messageKey, Cipher.ENCRYPT_MODE, true);
        if (encryptedMetadata.length > 127) throw new IOException("Message marker is too long");
        byte[] payload = join(new byte[]{(byte) encryptedMetadata.length}, encryptedMetadata,
                              aes(body, messageKey, Cipher.ENCRYPT_MODE, true));
        cache(command(SEND, 1, payload));
    }

    private void cache(Message message) {
        if (message != null) inbox.put(message.id, message);
    }

    // Av.a(Ah) exchanges the command. Bv.a(byte[], Bf) reads any returned record.
    private Message command(int operation, int argument, byte[] payload) throws IOException, GeneralSecurityException {
        byte[] command = payload == null ? new byte[]{(byte) operation, (byte) argument}
            : join(new byte[]{(byte) operation, (byte) argument, 0}, payload);
        byte[] response = request(command);
        if (response.length < 34 || response[32] != operation || response[33] != 0)
            throw new IOException("Invalid mailbox response");
        if (response.length == 34) return null;
        if (response.length < 36) throw new IOException("Truncated mailbox message");
        int id = response[34] & 0xff;
        int metadataSize = response[35] & 0xff;
        int bodyOffset = 36 + metadataSize;
        if (id == 0 || id > 127 || metadataSize == 0 || metadataSize > 127 || bodyOffset >= response.length)
            throw new IOException("Invalid mailbox message header");
        byte[] metadata = aes(Arrays.copyOfRange(response, 36, bodyOffset), messageKey, Cipher.DECRYPT_MODE, true);
        if (metadata.length < 4) throw new IOException("Truncated mailbox metadata");
        long time = Integer.toUnsignedLong(ByteBuffer.wrap(metadata).order(ByteOrder.LITTLE_ENDIAN).getInt());
        String marker = new String(metadata, 4, metadata.length - 4, StandardCharsets.UTF_8);
        byte[] body = aes(Arrays.copyOfRange(response, bodyOffset, response.length), messageKey, Cipher.DECRYPT_MODE, true);
        return new Message(id, body, marker, time);
    }

    // Av.b(Ah) prepends the token and challenge proof and saves the next challenge.
    private byte[] request(byte[] command) throws IOException, GeneralSecurityException {
        byte[] proof = digest(join(challenge, secret), HASH_ROUNDS);
        byte[] response = decode(transport.post(join(token, encode(join(proof, command)))));
        if (response.length < CHALLENGE_SIZE) throw new IOException("Missing server challenge");
        challenge = Arrays.copyOf(response, CHALLENGE_SIZE);
        return response;
    }

    static Transport http(URL endpoint) {
        return request -> {
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            try {
                connection.setInstanceFollowRedirects(false);
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/octet-stream");
                connection.setFixedLengthStreamingMode(request.length);
                try (OutputStream output = connection.getOutputStream()) {
                    output.write(request);
                }
                if (connection.getResponseCode() != 200) throw new IOException("Server rejected the request");
                try (InputStream input = connection.getInputStream()) {
                    return JarResources.read(input, 12 * 1024 * 1024);
                }
            } finally {
                connection.disconnect();
            }
        };
    }

    private byte[] encode(byte[] data) {
        if (config.encoding == 'b') return Base64.getEncoder().encode(data);
        if (config.encoding != 'h') return data;
        byte[] result = new byte[2 * data.length];
        for (int i = 0; i < data.length; i++) {
            result[2 * i] = HEX[(data[i] & 0xff) >> 4];
            result[2 * i + 1] = HEX[data[i] & 15];
        }
        return result;
    }

    private byte[] decode(byte[] data) throws IOException {
        if (config.encoding == 'h') return unhex(new String(data, StandardCharsets.US_ASCII));
        if (config.encoding != 'b') return data;
        try {
            return Base64.getDecoder().decode(data);
        } catch (IllegalArgumentException error) {
            throw new IOException("Invalid Base64 response", error);
        }
    }

    private static byte[] unhex(String data) throws IOException {
        if (data.length() % 2 != 0) throw new IOException("Invalid hexadecimal data length");
        byte[] result = new byte[data.length() / 2];
        for (int i = 0; i < result.length; i++) {
            int high = Character.digit(data.charAt(2 * i), 16);
            int low = Character.digit(data.charAt(2 * i + 1), 16);
            if (high < 0 || low < 0) throw new IOException("Invalid hexadecimal data");
            result[i] = (byte) ((high << 4) | low);
        }
        return result;
    }

    private static byte[] digest(byte[] data, int rounds) throws GeneralSecurityException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        for (int i = 0; i < rounds; i++) data = sha256.digest(data);
        return data;
    }

    private static byte[] aes(byte[] data, byte[] key, int mode, boolean padded) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(padded ? "AES/CBC/PKCS5Padding" : "AES/CBC/NoPadding");
        cipher.init(mode, new SecretKeySpec(key, "AES"), new IvParameterSpec(ZERO_IV));
        return cipher.doFinal(data);
    }

    private static byte[] join(byte[]... fields) {
        ByteArrayOutputStream packet = new ByteArrayOutputStream();
        for (byte[] field : fields) packet.write(field, 0, field.length);
        return packet.toByteArray();
    }
}
