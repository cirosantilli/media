package replay.display;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class SourceApplet extends Applet {
    private final Cover cover = new Cover();
    private volatile ScheduledThreadPoolExecutor worker;
    private DisplayMailbox mailbox;
    private URL endpoint;
    private volatile MailboxWindow window;

    // Original entry: c.a.h.a.init(), Content.Type / Connection.Servlet.Path.
    // Use the archived servlet path only on the reconstruction's codebase.
    @Override public void init() {
        setLayout(new BorderLayout());
        add(cover, BorderLayout.CENTER);
        cover.setFocusable(true);
        cover.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent event) {
                cover.requestFocusInWindow();
            }
        });
        try {
            endpoint = new URL(getCodeBase(), DisplayMailbox.config().getProperty("Connection.Servlet.Path"));
            cover.addKeyListener(DisplayMailbox.activationListener(this::openWindow));
        } catch (Exception error) {
            showError(error);
        }
    }

    // c.a.h.a.run() delegates refills to c.b.k.*. This replay keeps a small
    // display queue and advances login independently of visible item duration.
    @Override public void start() {
        if (endpoint == null || worker != null) return;
        worker = new ScheduledThreadPoolExecutor(1);
        worker.execute(() -> {
            try {
                if (mailbox == null) mailbox = new DisplayMailbox(this::post);
            } catch (Exception error) {
                showError(error);
            }
        });
        worker.scheduleWithFixedDelay(this::tick, 0, 250, TimeUnit.MILLISECONDS);
        cover.animation.start();
    }

    @Override public void stop() {
        cover.animation.stop();
        if (worker != null) {
            worker.shutdown();
            worker = null;
        }
        EventQueue.invokeLater(() -> {
            if (window != null) window.dispose();
        });
    }

    // c.b.i / c.b.h exchange binary objects through the local servlet.
    private byte[] post(byte[] request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.setInstanceFollowRedirects(false);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setFixedLengthStreamingMode(request.length);
        try {
            try (OutputStream output = connection.getOutputStream()) {
                output.write(request);
            }
            if (connection.getResponseCode() != 200) {
                throw new IOException("Servlet returned " + connection.getResponseCode());
            }
            try (InputStream input = connection.getInputStream()) {
                ByteArrayOutputStream reply = new ByteArrayOutputStream();
                byte[] buffer = new byte[8192];
                for (int n; (n = input.read(buffer)) != -1;) {
                    reply.write(buffer, 0, n);
                    if (reply.size() > 8 * 1024 * 1024) throw new IOException("Servlet response too large");
                }
                return reply.toByteArray();
            }
        } finally {
            connection.disconnect();
        }
    }

    private void tick() {
        if (mailbox == null) return;
        try {
            MailboxWindow visible = window;
            boolean communicating = visible != null && visible.active;
            if (cover.needsDisplay() || communicating) {
                DisplayMailbox.Display display = mailbox.poll();
                cover.offer(display);
            }
            if (communicating) {
                if (mailbox.isRejected()) {
                    EventQueue.invokeLater(() -> visible.failure("Login rejected. Reload the page to try again."));
                } else if (mailbox.isAuthenticated()) {
                    List<DisplayMailbox.Message> messages = mailbox.messages();
                    EventQueue.invokeLater(() -> visible.update(messages));
                }
            }
        } catch (Exception error) {
            showError(error);
        }
    }

    private void openWindow() {
        EventQueue.invokeLater(() -> {
            if (window == null) window = new MailboxWindow();
            window.setVisible(true);
            window.toFront();
        });
    }

    private void showError(Exception error) {
        EventQueue.invokeLater(() -> {
            if (window != null) window.failure(error.toString());
            else showStatus(error.toString());
        });
    }

    private interface Operation { void run() throws Exception; }

    private void submit(Operation operation) {
        ScheduledThreadPoolExecutor activeWorker = worker;
        if (activeWorker == null) return;
        activeWorker.execute(() -> {
            try {
                operation.run();
            } catch (Exception error) {
                showError(error);
            }
        });
    }

    private static final class Cover extends Canvas {
        private static final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        private final ArrayDeque<DisplayMailbox.Display> queue = new ArrayDeque<>();
        private final Timer animation = new Timer(25, event -> repaint());
        private DisplayMailbox.Display current;
        private long startMillis;

        synchronized boolean needsDisplay() { return queue.size() < 2; }

        synchronized void offer(DisplayMailbox.Display display) {
            if (needsDisplay()) queue.addLast(display);
        }

        private synchronized void advance(long now, boolean finished) {
            if ((current == null || finished) && !queue.isEmpty()) {
                current = queue.removeFirst();
                startMillis = now;
            }
        }

        // c.b.k.a moves the text left at speed/1600 px/ms. The slideshow
        // effect uses its own duration; both values come from the server.
        @Override public void paint(Graphics graphics) {
            graphics.setColor(new Color(18, 32, 43));
            graphics.fillRect(0, 0, getWidth(), getHeight());
            graphics.setFont(FONT);
            long now = System.currentTimeMillis();
            long elapsed = now - startMillis;
            boolean finished = false;
            if (current != null) {
                finished = current.image != null ? elapsed >= current.duration
                        : elapsed * current.speed / 1600L >= getWidth() + graphics.getFontMetrics().stringWidth(current.text);
            }
            advance(now, finished);
            if (current == null) return;
            if (current.image != null) {
                // The slides use the applet's natural 146 × 219 pixel viewport.
                current.image.paint(graphics);
            } else {
                int x = getWidth() - (int) ((now - startMillis) * current.speed / 1600L);
                graphics.setColor(Color.WHITE);
                graphics.drawString(current.text, x, Math.max(12, getHeight() / 2 + 4));
            }
        }

        @Override public void update(Graphics graphics) { paint(graphics); }
    }

    // New window. The underlying actions map to the original message stream
    // methods and codes listed in DisplayMailbox.
    private final class MailboxWindow extends JFrame {
        private final JLabel status = new JLabel("Enter the local demo credentials.");
        private final JPanel contents = new JPanel(new BorderLayout(8, 8));
        private final DefaultListModel<DisplayMailbox.Message> items = new DefaultListModel<>();
        private final JList<DisplayMailbox.Message> inbox = new JList<>(items);
        private final JTextArea body = new JTextArea(7, 36);
        private final JTextArea draft = new JTextArea(5, 36);
        private final JTextField subject = new JTextField();
        private boolean ready;
        volatile boolean active;

        MailboxWindow() {
            super("Reconstruction mailbox");
            setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            JPanel page = new JPanel(new BorderLayout(8, 8));
            page.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            page.add(status, BorderLayout.SOUTH);
            page.add(contents);
            setContentPane(page);
            JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
            JTextField account = new JTextField("smiley", 16);
            JPasswordField password = new JPasswordField(16);
            form.add(new JLabel("Account"));
            form.add(account);
            form.add(new JLabel("Password"));
            form.add(password);
            JButton login = new JButton("Open mailbox");
            form.add(login);
            getRootPane().setDefaultButton(login);
            login.addActionListener(event -> {
                login.setEnabled(false);
                status.setText("Connecting…");
                String name = account.getText();
                String value = new String(password.getPassword());
                password.setText("");
                submit(() -> {
                    mailbox.login(name, value);
                    active = true;
                });
            });
            contents.add(form);
            pack();
        }

        void update(List<DisplayMailbox.Message> messages) {
            if (!ready) showMailbox();
            if (messages.size() != items.size() || !sameMessages(messages)) {
                items.clear();
                for (DisplayMailbox.Message message : messages) items.addElement(message);
            }
        }

        private boolean sameMessages(List<DisplayMailbox.Message> messages) {
            for (int i = 0; i < items.size(); ++i) {
                if (items.get(i).id != messages.get(i).id) return false;
            }
            return true;
        }

        private void showMailbox() {
            ready = true;
            getRootPane().setDefaultButton(null);
            status.setText("Connected.");
            contents.removeAll();
            JPanel messages = new JPanel(new BorderLayout(4, 4));
            messages.add(new JScrollPane(inbox), BorderLayout.NORTH);
            body.setEditable(false);
            messages.add(new JScrollPane(body));
            JPanel inboxActions = new JPanel();
            JButton open = new JButton("Read");
            open.addActionListener(event -> {
                DisplayMailbox.Message message = inbox.getSelectedValue();
                if (message != null) submit(() -> {
                    String text = mailbox.open(message);
                    EventQueue.invokeLater(() -> body.setText(text));
                });
            });
            JButton delete = new JButton("Delete");
            delete.addActionListener(event -> {
                DisplayMailbox.Message message = inbox.getSelectedValue();
                if (message != null) submit(() -> mailbox.delete(message));
            });
            inboxActions.add(open);
            inboxActions.add(delete);
            messages.add(inboxActions, BorderLayout.SOUTH);
            contents.add(messages, BorderLayout.NORTH);
            JPanel compose = new JPanel(new BorderLayout(4, 4));
            subject.setToolTipText("Subject");
            JPanel subjectLine = new JPanel(new BorderLayout(4, 4));
            subjectLine.add(new JLabel("Subject"), BorderLayout.WEST);
            subjectLine.add(subject);
            compose.add(subjectLine, BorderLayout.NORTH);
            compose.add(new JScrollPane(draft));
            JButton send = new JButton("Send");
            send.addActionListener(event -> {
                String text = draft.getText();
                if (text.trim().isEmpty()) return;
                String title = subject.getText();
                submit(() -> {
                    mailbox.send(title, text);
                    EventQueue.invokeLater(() -> {
                        draft.setText("");
                        status.setText("Queued for sending.");
                    });
                });
            });
            compose.add(send, BorderLayout.SOUTH);
            contents.add(compose, BorderLayout.SOUTH);
            pack();
        }

        void failure(String message) {
            active = false;
            status.setText(message);
            pack();
        }
    }
}
