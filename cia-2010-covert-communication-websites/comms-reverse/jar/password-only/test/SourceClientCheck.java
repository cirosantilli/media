package users.help;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/** Exercises the rebuilt client with the Python demo server over test pipes. */
public final class SourceClientCheck {
    private SourceClientCheck() {}

    private static LegacyClient.Transport pipeTransport() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        return request -> {
            System.out.println("REQUEST " + Base64.getEncoder().encodeToString(request));
            System.out.flush();
            String response = input.readLine();
            if (response == null || response.startsWith("ERROR ")) throw new IOException(response);
            return Base64.getDecoder().decode(response);
        };
    }

    public static void main(String[] args) throws Exception {
        LegacyClient.Configuration config = LegacyClient.Configuration.load();
        if (!config.endpoint.getPath().equals(args[2])) throw new AssertionError("Wrong configured endpoint");
        boolean http = args.length > 3 && "--http".equals(args[3]);
        LegacyClient.Transport transport = http ? LegacyClient.http(config.endpoint) : pipeTransport();
        byte[] password = args[0].getBytes(StandardCharsets.UTF_8);
        byte[] wrongPassword = password.clone();
        wrongPassword[0] ^= 1;
        try {
            new LegacyClient(config, wrongPassword, transport);
            throw new AssertionError("Wrong password was accepted");
        } catch (IOException error) {
            String expected = http ? "Server rejected the request" : "ERROR Bad login";
            if (!expected.equals(error.getMessage())) throw error;
        }

        LegacyClient client = new LegacyClient(config, password, transport);
        client.fetchInbox();
        if (client.inbox().size() != 1) throw new AssertionError("Expected initial inbox message");
        client.sendText("Source Java client round trip: café 世界");
        if (client.inbox().size() != 1) throw new AssertionError("Send changed the current inbox");

        LegacyClient resumed = new LegacyClient(config, password, transport);
        resumed.fetchInbox();
        if (resumed.inbox().size() != 2) throw new AssertionError("Expected a reply across logins");
        LegacyClient.Message reply = resumed.inbox().stream().filter(message -> message.text().equals(args[1]))
            .findFirst().orElseThrow(() -> new AssertionError("Reply body differs"));
        resumed.delete(reply.id);
        if (resumed.inbox().size() != 1) throw new AssertionError("Deleted message still present");
        resumed.sendFile("payload.bin", new byte[]{0, 1, 2, 3, (byte) 255});
        resumed.fetchInbox();
        if (resumed.inbox().size() != 1) throw new AssertionError("Upload generated another reply");
        System.out.println("PASS: rebuilt JAR rejected a bad password, logged in, sent UTF-8, retrieved and deleted the reply, and uploaded binary data.");
    }
}
