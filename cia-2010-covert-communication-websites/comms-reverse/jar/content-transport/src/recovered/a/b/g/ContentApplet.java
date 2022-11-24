/* slides.jar — a/b/g/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.b.g;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import recovered.a.a.e.b.b.TypeE;
import recovered.a.a.e.c.TypeA;
import recovered.a.a.e.c.TypeD;
import recovered.a.a.o.ContentEvent;
import recovered.a.a.p.TypeG;
import recovered.a.a.p.TypeN;
import recovered.a.b.f.TypeB;
import recovered.a.b.f.TypeC;
import recovered.a.b.f.TypeI;
import recovered.org.b.g.b.WorkerThread;

public class ContentApplet
extends Applet
implements Runnable,
recovered.a.a.o.TypeC {
    private TypeA a;
    private Image b;
    private boolean c;
    private Component d;
    private JSObject e;
    private recovered.org.b.b.TypeG f;
    private TypeG g;
    private WorkerThread h;
    private String i = z[53];
    private WorkerThread j;
    private String k = z[55];
    private ThreadGroup l;
    private String m = z[54];
    private recovered.a.a.r.TypeC n;
    private long o = 0L;
    private static final Color C;
    private static final Color H;
    private Color P;
    private String Q;
    private long R = 3200L;
    private String S;
    private int T;
    private Color U;
    private String V;
    private int W;
    private long X;
    private long Y;
    private int Z;
    private int ab;
    private boolean bb;
    private static String[] z;

    // a.b.g.a.init: the HTML codebase supplies the servlet host and port.
    public void init() {
        Object var1_1 = null;
        Object var2_2 = null;
        Object var3_3 = null;
        Object var4_4 = null;
        Object var5_5 = null;
        this.l = new ThreadGroup(this.m);
        TypeN typeN = new TypeN(this.l);
        recovered.a.a.k.TypeB.a__La_a_k_b().a(typeN);
        TypeI.a__V();
        this.f = recovered.a.a.k.TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
        this.c = false;
        URL uRL = this.getCodeBase();
        int n = uRL.getPort();
        if (n == -1) {
            n = 80;
        }
        TypeB.a__V(z[22], uRL.getHost());
        TypeB.a__V(z[20], new String("" + n));
        this.f.a__V(z[19] + uRL.getHost() + ":" + n);
        this.e__V();
        this.setBackground(this.P);
        this.setLayout(null);
        try {
            this.e = JSObject.getWindow(this);
        }
        catch (JSException jSException) {
            this.f.d(z[21], jSException);
        }
        catch (NullPointerException nullPointerException) {
            this.f.d(z[21], nullPointerException);
        }
    }

    public void start() {
        this.c = false;
        this.c__V();
        this.repaint();
        this.h = new WorkerThread(this.l, this, this.i);
        this.h.start();
        try {
            Thread.sleep(350L);
        }
        catch (InterruptedException interruptedException) {
        }
    }

    public synchronized void stop() {
        this.f.a__V(z[7]);
        Thread[] threadArray = new Thread[this.l.activeCount()];
        int n = this.l.enumerate(threadArray);
        for (int i = 0; i < n; ++i) {
            Thread thread = threadArray[i];
            try {
                if (thread instanceof WorkerThread) {
                    ((WorkerThread)thread).a__V();
                    thread = null;
                    continue;
                }
                thread = null;
                continue;
            }
            catch (NullPointerException nullPointerException) {
            }
        }
        Thread.currentThread();
        Thread.yield();
    }

    public void destroy() {
        long l;
        long l2 = l = System.currentTimeMillis();
        while (this.l.activeCount() > 1 && l2 - l < 1000L) {
            try {
                Thread.sleep(25L);
            }
            catch (InterruptedException interruptedException) {}
        }
        this.f.a__V(z[6]);
        try {
            this.l.destroy();
        }
        catch (Throwable throwable) {
        }
    }

    public int getHeight() {
        return this.getBounds().height;
    }

    public int getWidth() {
        return this.getBounds().width;
    }

    public void run() {
        if (Thread.currentThread() instanceof WorkerThread) {
            WorkerThread workerThread = (WorkerThread)Thread.currentThread();
            if (workerThread.getName().equals(this.i)) {
                this.a(workerThread);
            } else if (workerThread.getName().equals(this.k)) {
                this.b(workerThread);
            }
        }
    }

    private recovered.a.a.e.b.b.TypeC b__La_a_e_b_b_c() {
        recovered.a.a.e.b.b.TypeC typeC = null;
        Image image = this.createImage(super.getSize().width, super.getSize().height);
        if (image != null) {
            Graphics graphics = image.getGraphics();
            graphics.setColor(this.P);
            graphics.fillRect(0, 0, super.getSize().width, super.getSize().height);
            typeC = new recovered.a.a.e.b.b.TypeC(image);
        }
        return typeC;
    }

    // a.b.g.a.a(org.b.g.b.b): prepare the background and select the slideshow or scrolling display from the settings.
    public void a(WorkerThread workerThread) {
        Object object;
        recovered.a.a.e.b.b.TypeC typeC = null;
        boolean bl = true;
        if (this.Q != null && this.Q.length() > 0) {
            typeC = new recovered.a.a.e.b.b.TypeC(this.Q);
        }
        if (typeC == null || typeC.a__Ljava_awt_Image() == null) {
            this.f.a__V(z[10] + this.Q + z[14]);
            typeC = this.b__La_a_e_b_b_c();
            bl = false;
        }
        this.g = new TypeC();
        this.g.a(this);
        if (this.V.equals(z[15])) {
            object = new recovered.a.a.d.a.TypeA(recovered.a.b.e.TypeA.d, this.l, this.Z, this.ab, this.Y, this.X);
            ((recovered.a.a.d.a.TypeA)object).a(this.g);
            this.n = new recovered.a.a.r.TypeB(recovered.a.b.e.TypeA.d, (recovered.a.a.d.a.TypeA)object);
        } else {
            object = new recovered.a.a.d.a.TypeA(recovered.a.b.e.TypeA.b, this.l, this.Z, this.ab, this.Y, this.X);
            ((recovered.a.a.d.a.TypeA)object).a(this.g);
            this.n = new recovered.a.a.r.TypeA(recovered.a.b.e.TypeA.b, (recovered.a.a.d.a.TypeA)object);
            ((recovered.a.a.r.TypeA)this.n).a__V(this.W);
        }
        this.add((Component)((Object)this.n));
        ((Component)((Object)this.n)).setBounds(0, 0, this.getWidth(), this.getHeight());
        object = new TypeE(this.S, new Font("", 0, this.T), this.U);
        ((TypeE)object).a__V(2);
        TypeD typeD = new TypeD((TypeE)object);
        this.n.a(typeD);
        if (typeC != null && typeC.a__Ljava_awt_Image() != null) {
            long l = 2500L;
            long l2 = System.currentTimeMillis();
            while (System.currentTimeMillis() - l2 < l && !typeC.b__Z()) {
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException interruptedException) {}
            }
            if (!typeC.b__Z()) {
                this.f.a__V(z[16] + this.Q + z[14]);
                typeC = this.b__La_a_e_b_b_c();
                bl = false;
            } else if (typeC.c__Z()) {
                this.f.a__V(z[18] + this.Q + z[14]);
                typeC = this.b__La_a_e_b_b_c();
                bl = false;
            }
            if (bl && typeC.a__Ljava_awt_Image().getWidth(null) != this.getWidth() || typeC.a__Ljava_awt_Image().getHeight(null) != this.getHeight()) {
                if (this.bb) {
                    this.f.a__V(z[13] + typeC.a__Ljava_awt_Image().getHeight(null) + z[11] + typeC.a__Ljava_awt_Image().getWidth(null) + z[12] + this.getWidth() + z[11] + this.getHeight());
                    Image image = typeC.a__Ljava_awt_Image();
                    image = image.getScaledInstance(this.getWidth(), this.getHeight(), 4);
                    typeC = new recovered.a.a.e.b.b.TypeC(image);
                } else {
                    this.f.a__V(z[17]);
                    Image image = this.createImage(this.getWidth(), this.getHeight());
                    if (image != null) {
                        Graphics graphics = image.getGraphics();
                        graphics.setColor(this.P);
                        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
                        Image image2 = typeC.a__Ljava_awt_Image();
                        graphics.drawImage(image2, 0, 0, null);
                        typeC = new recovered.a.a.e.b.b.TypeC(image);
                    }
                }
            }
            this.a = new TypeA(typeC);
            this.a.setBounds(0, 0, super.getSize().width, super.getSize().height);
        }
        if (this.a != null) {
            this.add(this.a);
        }
        this.repaint();
        try {
            Thread.sleep(1500L);
        }
        catch (InterruptedException interruptedException) {
        }
        if (!workerThread.c__Z()) {
            this.d__V();
            this.repaint();
            this.c = true;
            this.j = new WorkerThread(this.l, this, this.k);
            this.j.setPriority(Math.min(6, 10));
            this.j.start();
        }
        this.requestFocus();
    }

    public void repaint() {
        super.repaint();
        this.o = System.currentTimeMillis();
    }

    // a.b.g.a.b(org.b.g.b.b): run the display loop and request periodic full repaints.
    public void b(WorkerThread workerThread) {
        this.repaint();
        boolean bl = true;
        this.n.d__V();
        while (!workerThread.c__Z()) {
            if (System.currentTimeMillis() > this.o + this.R) {
                this.repaint();
            }
            this.n.c__Z();
            if (workerThread.c__Z()) continue;
            try {
                Thread.sleep(25L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    public void update(Graphics graphics) {
        Rectangle rectangle = (Rectangle)graphics.getClip();
        Rectangle rectangle2 = new Rectangle(rectangle.getBounds().x - 8, rectangle.getBounds().y - 8, rectangle.getBounds().width + 16, rectangle.getBounds().height + 16);
        if (this.b == null) {
            try {
                this.b = this.createImage(this.getWidth(), this.getHeight());
            }
            catch (Exception exception) {
                this.f.d(z[2], exception);
            }
        }
        graphics.setClip(rectangle2);
        this.b.getGraphics().setClip(rectangle2);
        this.paint(this.b.getGraphics());
        graphics.drawImage(this.b, 0, 0, null);
    }

    // a.b.g.a.a(a.a.o.d): dispatch events 358 (status), 357 (open link) and 359 (clear status) to LiveConnect.
    public void a(recovered.a.a.o.TypeD typeD) {
        try {
            ContentEvent contentEvent = (ContentEvent)typeD;
            if (contentEvent.a__I() == 358) {
                this.a(contentEvent);
            } else if (contentEvent.a__I() == 357) {
                this.c(contentEvent);
            } else if (contentEvent.a__I() == 359) {
                this.b(contentEvent);
            }
        }
        catch (ClassCastException classCastException) {
        }
    }

    public void a(ContentEvent contentEvent) {
        if (this.e != null) {
            try {
                String string = (String)contentEvent.c__Ljava_lang_Object();
                if (string != null) {
                    this.e.eval(z[1] + string + "\"");
                }
            }
            catch (Exception exception) {
                this.f.d(z[0], exception);
            }
        }
    }

    public void b(ContentEvent contentEvent) {
        if (this.e != null) {
            try {
                this.e.eval(z[9]);
            }
            catch (Exception exception) {
                this.f.d(z[8], exception);
            }
        }
    }

    public void c(ContentEvent contentEvent) {
        if (this.e != null) {
            try {
                String string = (String)contentEvent.c__Ljava_lang_Object();
                this.e.eval(z[4] + string + z[3]);
            }
            catch (Exception exception) {
                this.f.d(z[5], exception);
            }
        }
    }

    private void c__V() {
        this.setCursor(new Cursor(3));
        TypeE typeE = new TypeE(this.S, new Font("", 0, this.T), this.U);
        typeE.a__V(2);
        this.d = new TypeD(typeE);
        this.d.setBounds((this.getWidth() - this.d.getBounds().width) / 2, (this.getHeight() - this.d.getBounds().height) / 2, this.d.getBounds().width, this.d.getBounds().height);
        this.add(this.d);
    }

    private void d__V() {
        this.remove(this.d);
        this.setCursor(new Cursor(0));
    }

    private void e__V() {
        String string = z[24];
        Properties properties = null;
        try {
            properties = TypeB.a__Ljava_util_Properties();
        }
        catch (IOException iOException) {
            properties = new Properties();
        }
        try {
            this.P = Color.decode(properties.getProperty(z[32], string));
        }
        catch (NumberFormatException numberFormatException) {
            this.P = C;
        }
        this.Q = properties.getProperty(z[43], "");
        try {
            this.R = Integer.parseInt(properties.getProperty(z[49], string));
        }
        catch (NumberFormatException numberFormatException) {
            this.R = 3200L;
        }
        if (this.R < 0L) {
            this.R = 0L;
        }
        this.S = properties.getProperty(z[36], "");
        try {
            this.T = Integer.parseInt(properties.getProperty(z[38], string));
        }
        catch (NumberFormatException numberFormatException) {
            this.T = 10;
        }
        if (this.T <= 0) {
            this.S = "";
            this.T = 10;
        } else if (this.T > 64) {
            this.T = 64;
        }
        try {
            this.U = Color.decode(properties.getProperty(z[33], string));
        }
        catch (NumberFormatException numberFormatException) {
            this.U = H;
        }
        this.V = properties.getProperty(z[45], z[23]);
        if (!this.V.equalsIgnoreCase(z[15])) {
            this.V = z[23];
        }
        try {
            this.W = Integer.parseInt(properties.getProperty(z[25], string));
        }
        catch (NumberFormatException numberFormatException) {
            this.W = 150;
        }
        if (this.W < 0) {
            this.W = 0;
        }
        try {
            this.X = Integer.parseInt(properties.getProperty(z[31], string));
        }
        catch (NumberFormatException numberFormatException) {
            this.X = 5L;
        }
        if (this.X < 0L) {
            this.X = 0L;
        }
        try {
            this.Y = Integer.parseInt(properties.getProperty(z[42], string));
        }
        catch (NumberFormatException numberFormatException) {
            this.Y = 750L;
        }
        if (this.Y < 0L) {
            this.Y = 0L;
        }
        try {
            this.Z = Integer.parseInt(properties.getProperty(z[37], string));
        }
        catch (NumberFormatException numberFormatException) {
            this.Z = 5;
        }
        if (this.Z < 0) {
            this.Z = 0;
        }
        try {
            this.ab = Integer.parseInt(properties.getProperty(z[28], string));
        }
        catch (NumberFormatException numberFormatException) {
            this.ab = 5;
        }
        if (this.ab <= 0) {
            this.ab = 1;
        }
        this.bb = Boolean.valueOf(properties.getProperty(z[50], new Boolean(true).toString()));
        if (this.f.a__I() <= 10000) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(z[35]);
            stringBuffer.append(z[30] + this.P + z[41]);
            stringBuffer.append(z[44] + this.Q + z[41]);
            stringBuffer.append(z[27] + this.bb + z[41]);
            stringBuffer.append(z[40] + this.R + z[41]);
            stringBuffer.append(z[46] + this.S + z[41]);
            stringBuffer.append(z[39] + this.T + z[41]);
            stringBuffer.append(z[34] + this.U + z[41]);
            stringBuffer.append(z[48] + this.V + z[41]);
            stringBuffer.append(z[26] + this.W + z[41]);
            stringBuffer.append(z[29] + this.X + z[41]);
            stringBuffer.append(z[52] + this.Y + z[41]);
            stringBuffer.append(z[51] + this.Z + z[41]);
            stringBuffer.append(z[47] + this.ab);
            this.f.a__V(stringBuffer.toString());
        }
    }

    static {
        z = new String[]{"Javascript display link error: ", "status=\"", "Error creating off-screen image: ", "\")", "open(\"", "Javascript html link error: ", "Applet is done.", "Applet is stopping.", "Javascript remove link error: ", "status=\" \"", "Unable to load the background image: ", ", ", " to ", "Rescaling background image from: ", ". Will use empty background.", "SlideShow", "Unable to load the background image within the alloted time (image may be too large): ", "Configuring mis-sized background image.", "Errored when loading the background image: ", "Running from code-base: ", "Connection.Servlet.Port", "Javascript init error: ", "Connection.Servlet.Address", "Scrolling", "NONE", "Content.ScrollingContentBufferSize", "Content.ScrollingContentBufferSize=", "Applet.ScaleBackgroundImage=", "Content.QueueRefillAmount", "Content.RequestFireRate=", "Applet.BackgroundColor=", "Content.RequestFireRate", "Applet.BackgroundColor", "Applet.SplashScreen.LoadingTextColor", "Applet.SplashScreen.LoadingTextColor=", "Applet Configurations: ", "Applet.SplashScreen.LoadingText", "Content.MinimumQueueSize", "Applet.SplashScreen.LoadingTextSize", "Applet.SplashScreen.LoadingTextSize=", "Applet.FullRepaintDelay=", "; ", "Content.RequestRefreshRate", "Applet.BackgroundImageLocation", "Applet.BackgroundImageLocation=", "Content.Type", "Applet.SplashScreen.LoadingText=", "Content.QueueRefillAmount=", "Content.Type=", "Applet.FullRepaintDelay", "Applet.ScaleBackgroundImage", "Content.MinimumQueueSize=", "Content.RequestRefreshRate=", "init", "media", "display"};
        C = new Color(255, 255, 255);
        H = new Color(0, 0, 0);
    }
}
