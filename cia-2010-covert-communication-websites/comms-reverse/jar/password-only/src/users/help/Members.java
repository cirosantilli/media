package users.help;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/** Source-built applet entry point for the archived page. */
public final class Members extends Applet {
    private static final Color INK = new Color(0x183e40);
    private static final Color PAPER = new Color(0xeaf4f1);
    private static final DateTimeFormatter RECEIVED_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm 'UTC'")
        .withZone(ZoneOffset.UTC);

    private TextField password;
    private Label loginStatus, mailboxStatus;
    private Button open;
    private Frame frame;
    private TextArea messageBody, draft;
    private java.awt.List inbox;
    private List<LegacyClient.Message> visibleMessages = Collections.emptyList();
    private LegacyClient.Configuration configuration;
    private LegacyClient client;
    private boolean busy, destroyed;
    private int session;

    @Override public void init() {
        EventQueue.invokeLater(() -> {
            if (destroyed) return;
            setLayout(new GridLayout(3, 1, 0, 2));
            setBackground(PAPER);
            setForeground(INK);
            password = new TextField(12);
            password.setEchoChar('*');
            open = new Button("Open archive mailbox");
            loginStatus = new Label("Enter demo password", Label.CENTER);
            add(loginStatus);
            add(password);
            add(open);
            open.addActionListener(event -> signIn());
            password.addActionListener(event -> signIn());
            validate();
        });
    }

    private void signIn() {
        if (frame != null) {
            frame.toFront();
            return;
        }
        if (busy) return;
        byte[] secret = password.getText().getBytes(StandardCharsets.UTF_8);
        password.setText("");
        run("Opening mailbox…", () -> {
            try {
                LegacyClient.Configuration settings = LegacyClient.Configuration.load();
                LegacyClient connected = new LegacyClient(settings, secret, LegacyClient.http(settings.endpoint));
                connected.fetchInbox();
                return new Login(settings, connected);
            } finally {
                Arrays.fill(secret, (byte) 0);
            }
        }, this::showMailbox);
    }

    private static final class Login {
        final LegacyClient.Configuration settings;
        final LegacyClient client;

        Login(LegacyClient.Configuration settings, LegacyClient client) {
            this.settings = settings;
            this.client = client;
        }
    }

    private void showMailbox(Login login) {
        configuration = login.settings;
        client = login.client;
        frame = new Frame("European News Flash · Archive mailbox");
        frame.setLayout(new BorderLayout(8, 8));
        frame.setBackground(PAPER);
        frame.setForeground(INK);

        Panel header = new Panel(new BorderLayout());
        header.setBackground(INK);
        header.setForeground(Color.WHITE);
        header.add(new Label("  Local archive mailbox"), BorderLayout.CENTER);
        header.add(action("Sign out", this::signOut), BorderLayout.EAST);
        frame.add(header, BorderLayout.NORTH);

        Panel messages = new Panel(new BorderLayout(4, 4));
        messages.setPreferredSize(new Dimension(240, 350));
        inbox = new java.awt.List();
        inbox.addItemListener(event -> showSelection());
        messages.add(new Label("Inbox"), BorderLayout.NORTH);
        messages.add(inbox, BorderLayout.CENTER);
        Panel manage = new Panel(new FlowLayout(FlowLayout.LEFT));
        manage.add(action("Quote", this::quoteSelected));
        manage.add(action("Save…", this::saveSelected));
        manage.add(action("Delete", this::deleteSelected));
        messages.add(manage, BorderLayout.SOUTH);
        frame.add(messages, BorderLayout.WEST);

        Panel detail = new Panel(new GridLayout(2, 1, 4, 8));
        Panel reader = new Panel(new BorderLayout());
        reader.add(new Label("Selected message"), BorderLayout.NORTH);
        messageBody = new TextArea("", 6, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        messageBody.setEditable(false);
        reader.add(messageBody, BorderLayout.CENTER);
        detail.add(reader);

        Panel composer = new Panel(new BorderLayout());
        composer.add(new Label("Write a message"), BorderLayout.NORTH);
        draft = new TextArea("", 5, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        composer.add(draft, BorderLayout.CENTER);
        Panel actions = new Panel(new FlowLayout(FlowLayout.LEFT));
        actions.add(action("Send text", this::sendText));
        actions.add(action("Send file…", this::sendFile));
        composer.add(actions, BorderLayout.SOUTH);
        detail.add(composer);
        frame.add(detail, BorderLayout.CENTER);

        mailboxStatus = new Label();
        frame.add(mailboxStatus, BorderLayout.SOUTH);
        frame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent event) {
                signOut();
            }
        });
        renderInbox();
        status("Demo session active");
        loginStatus.setText("Mailbox is open");
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private static Button action(String title, Runnable task) {
        Button button = new Button(title);
        button.addActionListener(event -> task.run());
        return button;
    }

    private void renderInbox() {
        LegacyClient.Message previous = selected();
        visibleMessages = client.inbox();
        visibleMessages.sort((first, second) -> Long.compare(second.timestamp, first.timestamp));
        inbox.removeAll();
        int selectedIndex = 0;
        for (int i = 0; i < visibleMessages.size(); i++) {
            LegacyClient.Message message = visibleMessages.get(i);
            inbox.add("#" + message.id + "  " + RECEIVED_TIME.format(Instant.ofEpochSecond(message.timestamp)));
            if (previous != null && previous.id == message.id) selectedIndex = i;
        }
        if (!visibleMessages.isEmpty()) inbox.select(selectedIndex);
        showSelection();
    }

    private LegacyClient.Message selected() {
        int index = inbox.getSelectedIndex();
        return index >= 0 && index < visibleMessages.size() ? visibleMessages.get(index) : null;
    }

    private void showSelection() {
        LegacyClient.Message message = selected();
        messageBody.setText(message == null ? "" : message.text());
    }

    private void deleteSelected() {
        LegacyClient.Message message = selected();
        if (busy || message == null || !confirmDeletion(message.id)) return;
        LegacyClient active = client;
        run("Deleting message…", () -> {
            active.delete(message.id);
            return "Message deleted";
        }, this::mailboxUpdate);
    }

    private boolean confirmDeletion(int id) {
        Dialog confirm = new Dialog(frame, "Delete message", true);
        boolean[] accepted = {false};
        confirm.add(new Label("Delete message #" + id + "?"), BorderLayout.CENTER);
        Panel choices = new Panel(new FlowLayout(FlowLayout.RIGHT));
        choices.add(action("Cancel", confirm::dispose));
        choices.add(action("Delete", () -> {
            accepted[0] = true;
            confirm.dispose();
        }));
        confirm.add(choices, BorderLayout.SOUTH);
        confirm.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent event) {
                confirm.dispose();
            }
        });
        confirm.pack();
        confirm.setLocationRelativeTo(frame);
        confirm.setVisible(true);
        return accepted[0];
    }

    private void quoteSelected() {
        LegacyClient.Message message = selected();
        if (message == null) return;
        String text = message.text();
        if (text.startsWith("\ufeff") || text.startsWith("?")) text = text.substring(1);
        if (text.isEmpty()) return;
        draft.setText("\n-------------------------\n" + text + draft.getText());
        draft.setCaretPosition(0);
        draft.requestFocus();
    }

    private void sendText() {
        String text = draft.getText();
        if (text.isEmpty()) return;
        LegacyClient active = client;
        run("Sending message…", () -> {
            active.sendText(text);
            return "Message sent";
        }, result -> {
            if (draft.getText().equals(text)) draft.setText("");
            mailboxUpdate(result);
        });
    }

    private void sendFile() {
        if (busy) return;
        Path file = chooseFile("Choose a file to send", FileDialog.LOAD, null);
        if (file == null) return;
        LegacyClient active = client;
        int limit = configuration.maxFileBytes;
        run("Sending file…", () -> {
            try (InputStream input = Files.newInputStream(file)) {
                active.sendFile(file.getFileName().toString(), JarResources.read(input, limit));
            }
            return "File sent";
        }, this::mailboxUpdate);
    }

    private void saveSelected() {
        if (busy) return;
        LegacyClient.Message message = selected();
        if (message == null) return;
        String suffix = message.marker.matches("[a-zA-Z0-9][a-zA-Z0-9.]{0,31}") ? message.marker : "bin";
        Path file = chooseFile("Save message", FileDialog.SAVE, "message-" + message.id + "." + suffix);
        if (file != null) {
            run("Saving message…", () -> {
                Files.write(file, message.body);
                return "Message saved";
            }, this::status);
        }
    }

    private Path chooseFile(String title, int mode, String suggestedName) {
        FileDialog dialog = new FileDialog(frame, title, mode);
        try {
            if (suggestedName != null) dialog.setFile(suggestedName);
            dialog.setVisible(true);
            return dialog.getFile() == null ? null : Paths.get(dialog.getDirectory(), dialog.getFile());
        } finally {
            dialog.dispose();
        }
    }

    private void mailboxUpdate(String completed) {
        renderInbox();
        status(completed);
    }

    private void status(String text) {
        Label target = frame == null ? loginStatus : mailboxStatus;
        target.setText(text);
    }

    private <T> void run(String label, Callable<T> work, Consumer<T> success) {
        if (busy || destroyed) return;
        busy = true;
        int generation = session;
        status(label);
        Thread thread = new Thread(() -> {
            try {
                T result = work.call();
                EventQueue.invokeLater(() -> finish(generation, () -> success.accept(result)));
            } catch (Exception error) {
                String message = error.getMessage();
                EventQueue.invokeLater(() -> finish(generation, () -> status(message == null ? "Operation failed" : message)));
            }
        }, "archive-mailbox");
        thread.setDaemon(true);
        thread.start();
    }

    private void finish(int generation, Runnable result) {
        if (generation == session && !destroyed) {
            busy = false;
            result.run();
        }
    }

    private void signOut() {
        session++;
        busy = false;
        if (frame != null) frame.dispose();
        frame = null;
        client = null;
        configuration = null;
        visibleMessages = Collections.emptyList();
        if (loginStatus != null) loginStatus.setText("Enter demo password");
    }

    @Override public void destroy() {
        EventQueue.invokeLater(() -> {
            destroyed = true;
            signOut();
        });
    }
}
