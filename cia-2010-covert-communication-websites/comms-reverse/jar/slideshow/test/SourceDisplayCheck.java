package replay.display;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public final class SourceDisplayCheck {
    private static final BufferedReader INPUT = new BufferedReader(
            new InputStreamReader(System.in, StandardCharsets.US_ASCII));

    public static void main(String[] args) throws Exception {
        checkActivation(args[1]);
        DisplayMailbox first = new DisplayMailbox(SourceDisplayCheck::exchange);
        checkCover(first, args[4].equals("scroll"));
        first.login("smiley", args[0]);
        for (int i = 0; i < 100; ++i) first.poll();
        require(first.isAuthenticated(), "login");
        require(first.messages().size() == 1, "opening inbox count");
        // c.a.d.e.a applies the archived message normalization (uppercase).
        require(first.open(first.messages().get(0)).equals(args[2].toUpperCase(Locale.ROOT)), "opening message");

        first.send("Source build test", "Hello from the rebuilt applet.");
        for (int i = 0; i < 150; ++i) first.poll();
        require(!first.hasPendingMessages(), "upload acknowledgement");

        System.out.println("RESET");
        require("READY".equals(INPUT.readLine()), "server reset");
        DisplayMailbox second = new DisplayMailbox(SourceDisplayCheck::exchange);
        second.login("smiley", args[0]);
        for (int i = 0; i < 100; ++i) second.poll();
        require(second.isAuthenticated(), "second login");
        require(second.messages().size() == 2, "read and unread folders");
        require(second.open(second.messages().get(0)).equals(args[3].toUpperCase(Locale.ROOT)), "local reply");
        for (DisplayMailbox.Message message : second.messages()) second.delete(message);
        for (int i = 0; i < 70; ++i) second.poll();

        System.out.println("RESET");
        require("READY".equals(INPUT.readLine()), "second server reset");
        DisplayMailbox third = new DisplayMailbox(SourceDisplayCheck::exchange);
        third.login("smiley", args[0]);
        for (int i = 0; i < 70; ++i) third.poll();
        require(third.isAuthenticated() && third.messages().isEmpty(), "delete survives reconnect");
        System.out.println("PASS: rebuilt applet activation, display, login, reading, sending, reply and delete");
    }

    private static byte[] exchange(byte[] request) throws Exception {
        System.out.println("REQUEST " + Base64.getEncoder().encodeToString(request));
        String response = INPUT.readLine();
        if (response == null) throw new IllegalStateException("Server closed the test pipe");
        return Base64.getDecoder().decode(response);
    }

    private static void checkActivation(String sequence) throws Exception {
        Properties properties = DisplayMailbox.config();
        char clear = properties.getProperty("KeyListener.ClearKey").charAt(0);
        char trigger = properties.getProperty("KeyListener.StickyKey").charAt(0);
        int[] matches = {0};
        KeyListener keys = DisplayMailbox.activationListener(() -> ++matches[0]);
        type(keys, clear + "!" + sequence + trigger);
        require(matches[0] == 0, "wrong activation");
        type(keys, clear + sequence + trigger);
        require(matches[0] == 1, "correct activation");
    }

    private static void type(KeyListener keys, String sequence) {
        Canvas canvas = new Canvas();
        for (char key : sequence.toCharArray()) {
            keys.keyPressed(new KeyEvent(canvas, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_UNDEFINED, key));
            keys.keyReleased(new KeyEvent(canvas, KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_UNDEFINED, key));
        }
    }

    private static void checkCover(DisplayMailbox client, boolean scrolling) throws Exception {
        Set<Object> variants = new HashSet<>();
        Object opening = null;
        for (int i = 0; i < 4; ++i) {
            DisplayMailbox.Display display = client.poll();
            Object variant;
            if (scrolling) {
                require(display.speed > 0 && !display.text.isEmpty(), "ticker speed and text");
                variant = display.text;
            } else {
                require(display.duration > 0 && display.image != null, "slide duration and image");
                int width = 146, height = 219;
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                java.awt.Graphics graphics = image.getGraphics();
                for (int tries = 0; tries < 20; ++tries) {
                    display.image.paint(graphics);
                    if (image.getRGB(width / 2, height / 2) != 0xff000000) break;
                    Thread.sleep(25);
                }
                graphics.dispose();
                variant = java.util.Arrays.hashCode(image.getRGB(0, 0, width, height, null, 0, width));
            }
            if (i == 0) opening = variant;
            if (i == 3) require(opening.equals(variant), "display loop");
            variants.add(variant);
        }
        require(variants.size() == 3, "three displayed items");
    }

    private static void require(boolean condition, String reason) {
        if (!condition) throw new AssertionError(reason);
    }
}
