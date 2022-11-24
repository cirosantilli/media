package users.help;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/** Decodes the archived At configuration container. */
final class JarResources {
    private static final int WORD = 0xffff;
    private static final BigInteger MODULUS = BigInteger.valueOf(65537);
    private static final BigInteger KEY_MASK = BigInteger.ONE.shiftLeft(128).subtract(BigInteger.ONE);
    private static final byte[] KEY = "0C5C45F2EC3C2A63".getBytes(StandardCharsets.US_ASCII);

    private JarResources() {}

    // At.a() opens the classpath resource used by the original applet.
    static Map<String, byte[]> load() throws IOException {
        try (InputStream input = JarResources.class.getResourceAsStream("/resource/resources.bin")) {
            if (input == null) throw new IOException("This applet needs the local server configuration.");
            return decode(read(input, 8 * 1024 * 1024));
        }
    }

    // At delegates to its resource codecs for IDEA/CBC, gzip, and field decoding.
    static Map<String, byte[]> decode(byte[] ciphertext) throws IOException {
        if (ciphertext.length < 16 || ciphertext.length % 8 != 0)
            throw new IOException("Invalid resource container length");
        byte[] blocks = ciphertext.clone();
        swapPairs(blocks);
        decryptIdea(blocks);
        swapPairs(blocks);
        // The first block is the CBC chain prefix. Only later plaintext blocks are used.
        for (int i = 8; i < blocks.length; i++) blocks[i] ^= ciphertext[i - 8];
        int padding = blocks[blocks.length - 1] & 0xff;
        if (padding < 1 || padding > 8) throw new IOException("Invalid resource padding");
        for (int i = blocks.length - padding; i < blocks.length - 1; i++)
            if (blocks[i] != 0) throw new IOException("Invalid resource padding");

        byte[] records;
        try (InputStream gzip = new GZIPInputStream(new ByteArrayInputStream(blocks, 8, blocks.length - 8 - padding))) {
            records = read(gzip, 8 * 1024 * 1024);
        }
        ByteBuffer data = ByteBuffer.wrap(records).order(ByteOrder.LITTLE_ENDIAN);
        Map<String, byte[]> result = new LinkedHashMap<>();
        while (data.hasRemaining()) {
            String name = new String(record(data, 1, 1000), StandardCharsets.UTF_8);
            result.put(name, record(data, 0, 500000));
        }
        return result;
    }

    private static byte[] record(ByteBuffer data, int min, int max) throws IOException {
        if (data.remaining() < 4) throw new IOException("Truncated resource record");
        int length = data.getInt();
        if (length < min || length > max || length > data.remaining())
            throw new IOException("Invalid resource record length");
        byte[] result = new byte[length];
        data.get(result);
        return result;
    }

    static byte[] read(InputStream input, int limit) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        for (int count; (count = input.read(buffer)) != -1;) {
            if (count > limit - result.size()) throw new IOException("Input exceeds " + limit + " bytes");
            result.write(buffer, 0, count);
        }
        return result.toByteArray();
    }

    private static void swapPairs(byte[] data) {
        for (int i = 0; i < data.length; i += 2) {
            byte first = data[i];
            data[i] = data[i + 1];
            data[i + 1] = first;
        }
    }

    private static int residue(int word) {
        return word == 0 ? 65536 : word;
    }

    private static int multiply(int first, int second) {
        return (int) ((long) residue(first) * residue(second) % 65537) & WORD;
    }

    private static void decryptIdea(byte[] data) {
        int[] key = new int[52];
        BigInteger schedule = new BigInteger(1, KEY);
        for (int i = 0; i < key.length; i++) {
            key[i] = schedule.shiftRight(112 - 16 * (i % 8)).intValue() & WORD;
            if (i % 8 == 7) schedule = schedule.shiftLeft(25).and(KEY_MASK).or(schedule.shiftRight(103));
        }
        int[] inverse = new int[key.length];
        for (int i = 0; i < key.length; i++) {
            if (i % 6 == 0 || i % 6 == 3)
                inverse[i] = BigInteger.valueOf(residue(key[i])).modInverse(MODULUS).intValue() & WORD;
        }

        ByteBuffer blocks = ByteBuffer.wrap(data);
        while (blocks.hasRemaining()) {
            int start = blocks.position();
            int y1 = blocks.getShort() & WORD;
            int y2 = blocks.getShort() & WORD;
            int y3 = blocks.getShort() & WORD;
            int y4 = blocks.getShort() & WORD;
            int a = multiply(y1, inverse[48]);
            int b = (y3 - key[50]) & WORD;
            int c = (y2 - key[49]) & WORD;
            int d = multiply(y4, inverse[51]);
            for (int round = 42; round >= 0; round -= 6) {
                int mix = multiply(a ^ b, key[round + 4]);
                int mask = multiply(((c ^ d) + mix) & WORD, key[round + 5]);
                int sum = (mix + mask) & WORD;
                int originalB = ((c ^ sum) - key[round + 1]) & WORD;
                c = ((b ^ mask) - key[round + 2]) & WORD;
                a = multiply(a ^ mask, inverse[round]);
                b = originalB;
                d = multiply(d ^ sum, inverse[round + 3]);
            }
            blocks.position(start);
            blocks.putShort((short) a).putShort((short) b).putShort((short) c).putShort((short) d);
        }
    }
}
