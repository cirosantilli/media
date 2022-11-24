/* slides.jar — org/b/f/a/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.f.a;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.Vector;
import recovered.org.b.f.TypeB;
import recovered.org.b.f.TypeC;
import recovered.org.b.f.TypeD;
import recovered.org.b.f.TypeE;
import recovered.org.b.g.b.WorkerThread;

public class TypeA
implements TypeE,
Runnable {
    private String e;
    private String f;
    private byte[] g;
    private Hashtable h;
    private int i;
    private String j;
    private int[] k = new int[1];
    private int l;
    private Hashtable[] m = new Hashtable[1];
    private Vector[] n = new Vector[1];
    private TypeD o;
    private boolean[] p = new boolean[1];
    private int q;
    private int[] r = new int[1];
    private Exception[] s = new Exception[1];
    private String[] t = new String[1];
    private int u = 512;
    private ThreadGroup v;
    private Object w = new Integer(0);
    private static String[] z = new String[]{"Content-Length", "application/x-www-form-urlencoded", "POST", "http", "Set-Cookie", "; ", "Content-Type", "GET", "Cookie", "1.0", "close", "Request timed-out.", "Proxy-Connection", "Connection", "Error while making request: ", "\r\n"};

    public TypeA(String string, String string2, int n, String string3) {
        this.e = string;
        this.i = n;
        this.j = string3;
        this.b__V(string2);
        this.m[0] = new Hashtable();
        this.n[0] = new Vector();
        this.o = new TypeD();
        this.k[0] = -1;
        this.t[0] = "";
        this.q = 75;
        this.r[0] = 0;
        this.l = 2500;
        this.s[0] = null;
    }

    public TypeA(String string, String string2, int n, String string3, ThreadGroup threadGroup) {
        this(string, string2, n, string3);
        this.v = threadGroup;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void d(String string) {
        Object object = this.w;
        synchronized (object) {
            this.f = string != null ? string : new String("");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a__V(byte[] byArray) {
        Object object = this.w;
        synchronized (object) {
            this.g = byArray != null ? byArray : new byte[0];
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b__V(String string) {
        Object object = this.w;
        synchronized (object) {
            this.h = this.e(string);
        }
    }

    private Hashtable e(String string) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        if (string == null) {
            return hashtable;
        }
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (n < string.length()) {
            String string2 = "";
            String string3 = "";
            n2 = n;
            n3 = string.indexOf(":", n2);
            if (n3 == -1) {
                n3 = string.indexOf(z[15], n2);
                if (n3 == -1) {
                    string2 = string.substring(n2);
                    n5 = string.length() + 1;
                } else {
                    string2 = string.substring(n2, n3);
                    n5 = n3;
                }
            } else {
                string2 = string.substring(n2, n3);
                n4 = string.length() <= n3 + 1 ? string.length() : (string.charAt(n3 + 1) == ' ' ? n3 + 2 : n3 + 1);
                if (n4 >= string.length()) {
                    n5 = string.length() + 1;
                } else {
                    n5 = string.indexOf(z[15], n4);
                    if (n5 == -1) {
                        n5 = string.length() + 1;
                        string3 = string.substring(n4);
                    } else {
                        string3 = string.substring(n4, n5);
                    }
                }
            }
            hashtable.put(string2, string3);
            n = n5 + 2;
        }
        return hashtable;
    }

    public String f(String string) {
        return (String)this.m[0].get(string);
    }

    public boolean g(String string) {
        return this.m[0].containsKey(string);
    }

    public String c__Ljava_lang_String() {
        return this.t[0];
    }

    public void a(TypeC typeC) {
        this.o.a(typeC);
    }

    public byte[] i__ArrayB() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < this.n[0].size(); ++i) {
            try {
                byteArrayOutputStream.write((byte[])this.n[0].elementAt(i));
                continue;
            }
            catch (IOException iOException) {
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b__V(int n) {
        Object object = this.w;
        synchronized (object) {
            this.l = n;
        }
    }

    public void k__V() {
    }

    public void a__V(long l) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] a__ArrayB(String string, byte[] byArray) throws TypeB {
        TypeB typeB = null;
        Object object = this.w;
        synchronized (object) {
            try {
                this.d(string);
                this.a__V(byArray);
                this.s[0] = null;
                this.p[0] = false;
                WorkerThread workerThread = null;
                workerThread = this.v != null ? new WorkerThread(this.v, this) : new WorkerThread(this);
                workerThread.start();
                if (this.l > -1) {
                    this.r[0] = 0;
                    while (this.r[0] <= this.l && !this.p[0]) {
                        this.b(this.q);
                        this.r[0] = this.r[0] + this.q;
                    }
                    if (!this.p[0]) {
                        this.k__V();
                        workerThread.stop();
                        workerThread = null;
                        throw new TypeB(z[11]);
                    }
                } else {
                    while (!this.p[0]) {
                        this.b(this.q);
                    }
                }
                if (this.s[0] != null) {
                    this.k__V();
                    throw new TypeB(z[14] + this.s[0].getMessage(), this.s[0]);
                }
                String string2 = this.c__Ljava_lang_String();
                if (string2 != null && this.c__Ljava_lang_String().equals(z[9]) || this.g(z[13]) && this.f(z[13]).equalsIgnoreCase(z[10]) || this.g(z[12]) && this.f(z[12]).equalsIgnoreCase(z[10])) {
                    this.k__V();
                }
                return this.i__ArrayB();
            }
            catch (TypeB typeB2) {
                typeB = typeB2;
            }
        }
        if (typeB != null) {
            throw typeB;
        }
        return null;
    }

    protected URLConnection a__Ljava_net_URLConnection(URL uRL) throws IOException {
        return uRL.openConnection();
    }

    private void g__V() throws TypeB {
        try {
            Object object;
            Object object2;
            Object object3;
            Object object4;
            String string = this.f;
            if (string.length() == 0 || this.f.charAt(0) != '/') {
                string = "/" + string;
            }
            Vector vector = this.o.a__Ljava_util_Vector(this.e, string);
            if (this.j.equals(z[7]) && this.g != null && this.g.length > 0) {
                string = string + "?" + new String(this.g);
            }
            URL uRL = new URL(z[3], this.e, this.i, string);
            URLConnection uRLConnection = this.a__Ljava_net_URLConnection(uRL);
            uRLConnection.setDoInput(true);
            if (this.j.equals(z[2])) {
                this.r[0] = 0;
                uRLConnection.setDoOutput(true);
                uRLConnection.setRequestProperty(z[6], z[1]);
                uRLConnection.setRequestProperty(z[0], new String("" + this.g.length));
            }
            this.r[0] = 0;
            if (this.h != null) {
                object4 = this.h.keys();
                while (((java.util.Enumeration)object4).hasMoreElements()) {
                    this.r[0] = 0;
                    object3 = (String)((java.util.Enumeration)object4).nextElement();
                    String string2 = (String)this.h.get(object3);
                    uRLConnection.setRequestProperty((String)object3, string2);
                }
            }
            if (vector != null && vector.size() > 0) {
                object4 = new StringBuffer();
                object3 = (TypeC)vector.elementAt(0);
                this.r[0] = 0;
                ((StringBuffer)object4).append(((TypeC)object3).a__Ljava_lang_String());
                ((StringBuffer)object4).append("=");
                ((StringBuffer)object4).append(((TypeC)object3).b__Ljava_lang_String());
                for (int i = 1; i < vector.size(); ++i) {
                    object3 = (TypeC)vector.elementAt(i);
                    this.r[0] = 0;
                    ((StringBuffer)object4).append(z[5]);
                    ((StringBuffer)object4).append(((TypeC)object3).a__Ljava_lang_String());
                    ((StringBuffer)object4).append("=");
                    ((StringBuffer)object4).append(((TypeC)object3).b__Ljava_lang_String());
                }
                uRLConnection.setRequestProperty(z[8], ((StringBuffer)object4).toString());
            }
            this.r[0] = 0;
            if (this.j.equals(z[2])) {
                this.r[0] = 0;
                object4 = uRLConnection.getOutputStream();
                ((OutputStream)object4).write(this.g);
                ((OutputStream)object4).flush();
                ((OutputStream)object4).close();
            }
            this.r[0] = 0;
            uRLConnection.connect();
            this.r[0] = 0;
            object4 = uRLConnection.getInputStream();
            this.r[0] = 0;
            this.t[0] = "";
            try {
                this.k[0] = ((HttpURLConnection)uRLConnection).getResponseCode();
            }
            catch (IOException iOException) {
                this.k[0] = -1;
            }
            catch (ClassCastException classCastException) {
                this.k[0] = -1;
            }
            this.m[0] = new Hashtable();
            int n = 0;
            boolean bl = true;
            while (bl) {
                this.r[0] = 0;
                object2 = uRLConnection.getHeaderFieldKey(n);
                if (object2 != null) {
                    object = uRLConnection.getHeaderField(n);
                    ++n;
                    if (object == null) {
                        object = "";
                    }
                    if (((String)object2).equalsIgnoreCase(z[4])) {
                        TypeC typeC = new TypeC(this.e, (String)object);
                        this.a(typeC);
                    }
                    this.m[0].put(object2, object);
                    continue;
                }
                if (n == 0) {
                    ++n;
                    continue;
                }
                bl = false;
            }
            this.n[0] = new Vector();
            object2 = new ByteArrayOutputStream();
            object = new byte[this.u];
            int n2 = 0;
            while (((InputStream)object4).available() >= 0 && n2 >= 0) {
                n2 = ((InputStream)object4).read((byte[])object, 0, ((byte[])object).length);
                this.r[0] = 0;
                if (n2 < 0 || n2 == 0) continue;
                ((ByteArrayOutputStream)object2).write((byte[])object, 0, n2);
            }
            this.n[0].addElement(((ByteArrayOutputStream)object2).toByteArray());
            this.r[0] = 0;
            ((InputStream)object4).close();
        }
        catch (Exception exception) {
            throw new TypeB(exception.getMessage(), exception);
        }
        this.p[0] = true;
    }

    public void run() {
        try {
            this.g__V();
        }
        catch (TypeB typeB) {
            this.s[0] = typeB;
            this.p[0] = true;
        }
    }

    public void i__V() {
        this.k__V();
    }

    public void finalize() throws Throwable {
        this.i__V();
        super.finalize();
    }

    private void b(long l) {
        try {
            Thread.currentThread();
            Thread.sleep(l);
        }
        catch (InterruptedException interruptedException) {
        }
    }
}
