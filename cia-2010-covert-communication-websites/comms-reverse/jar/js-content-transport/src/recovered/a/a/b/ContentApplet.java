/* aeronet.jar — a/a/b/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.b;

import java.applet.Applet;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import recovered.a.a.b.Configuration;
import recovered.a.a.b.ContentTimer;
import recovered.a.a.b.a.ContentChannel;
import recovered.a.a.b.a.ContentTransport;
import recovered.a.a.b.b.StartupWorker;
import recovered.b.a.TypeB;

public class ContentApplet
extends Applet {
    private String a;
    private String b;
    private String c;
    private JSObject d;
    private static Hashtable e;
    private ContentTransport f;
    private StartupWorker g;
    private Vector h = new Vector();
    private static String[] z;

    // a.a.b.c.init: read the site settings and start the shared startup worker.
    public void init() {
        try {
            this.a__V();
            if (this.g == null || !this.g.a__Z()) {
                this.g = new StartupWorker(Configuration.a__La_a_b_d().a__Ljava_lang_String(z[0]));
                this.g.start();
            }
        }
        catch (TypeB typeB) {
            typeB.printStackTrace();
        }
    }

    // a.a.b.c.start: wait up to ten half-second sleeps, then fill the channels and start their display timers.
    public void start() {
        for (int i = 0; i < 10 && !this.g.a__Z(); ++i) {
            try {
                Thread.sleep(500L);
                continue;
            }
            catch (Exception exception) {
            }
        }
        if (!this.g.a__Z()) {
            this.g.a__V();
            this.g = null;
        }
        ContentTransport.a(this.g);
        this.f.a(true);
        this.c__V();
        this.b__V();
    }

    public void stop() {
        this.f.a(false);
    }

    public void destroy() {
        if (this.g != null && this.g.a__Z()) {
            this.g.a__V();
            this.g = null;
        }
    }

    // a.a.b.c.a(String,String): call JS.Update.Method with the DOM ID and the replacement HTML.
    public synchronized void a(String string, String string2) {
        if (this.d != null) {
            Object[] objectArray = new Object[]{string, string2};
            try {
                this.d.call(this.a, objectArray);
            }
            catch (Exception exception) {
            }
        }
    }

    // a.a.b.c.a(String): call JS.PreLoad.Method with an image URL.
    public synchronized void a__V(String string) {
        if (this.d != null) {
            Object[] objectArray = new Object[]{string};
            try {
                this.d.call(this.b, objectArray);
            }
            catch (Exception exception) {
            }
        }
    }

    // a.a.b.c.a(): resolve the servlet against getCodeBase() and read each category timer from the HTML applet parameters.
    protected void a__V() throws TypeB {
        try {
            this.d = JSObject.getWindow(this);
        }
        catch (JSException jSException) {
            throw new TypeB(jSException);
        }
        try {
            Configuration.a__La_a_b_d().b__V();
        }
        catch (IOException iOException) {
            throw new TypeB(iOException);
        }
        this.a = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[2]);
        this.b = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[5]);
        String string = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[4]);
        try {
            URL uRL = this.getCodeBase();
            this.c = new URL(uRL, string).toString();
            this.f = new ContentTransport(new URL(uRL, string));
        }
        catch (Exception exception) {
            throw new TypeB(exception);
        }
        int n = Integer.valueOf(Configuration.a__La_a_b_d().a__Ljava_lang_String(z[1]));
        n = Math.max(n, 1);
        ContentChannel.a__V(n);
        StringTokenizer stringTokenizer = new StringTokenizer(Configuration.a__La_a_b_d().a__Ljava_lang_String(z[3]), ";", false);
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken();
            ContentChannel contentChannel = new ContentChannel(string2);
            e.put(string2, contentChannel);
            long l = 0L;
            try {
                l = Long.valueOf(this.getParameter(string2));
            }
            catch (NumberFormatException numberFormatException) {
                l = 5000L;
            }
            this.f.a__V(string2);
            contentChannel.a(this.f);
            ContentTimer contentTimer = new ContentTimer(this, l, string2);
            this.h.addElement(contentTimer);
        }
    }

    private void b__V() {
        for (int i = 0; i < this.h.size(); ++i) {
            ((Thread)this.h.elementAt(i)).start();
        }
    }

    private void c__V() {
        Enumeration enumeration = e.elements();
        while (enumeration.hasMoreElements()) {
            ((ContentChannel)enumeration.nextElement()).d__V();
            try {
                Thread.currentThread();
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    static Hashtable d__Ljava_util_Hashtable() {
        return e;
    }

    static {
        z = new String[]{"Applet.ThreadName", "Applet.MinCache", "JS.Update.Method", "Applet.Categories", "Servlet.Url", "JS.PreLoad.Method"};
        e = new Hashtable();
    }
}
