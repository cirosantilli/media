/* aeronet.jar — a/a/b/a/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.b.a;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.EmptyStackException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import recovered.a.a.b.Configuration;
import recovered.a.a.b.a.ContentChannel;
import recovered.a.a.b.a.TypeC;
import recovered.a.a.b.a.TypeE;
import recovered.a.a.b.a.TypeF;
import recovered.a.a.b.a.TypeG;
import recovered.a.a.b.a.a.TypeA;
import recovered.a.a.b.a.a.TypeB;
import recovered.a.a.b.b.StartupWorker;

public class ContentTransport
implements TypeC {
    private Hashtable a = new Hashtable();
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private Hashtable m = new Hashtable();
    private Vector n = new Vector();
    private Vector o = new Vector();
    private Vector p = new Vector();
    private Vector q = new Vector();
    private URL r;
    private TypeF s = new TypeF(this);
    boolean t = false;
    boolean u = false;
    private int v;
    private Random w = new Random();
    private boolean x = false;
    private static StartupWorker y;
    private static String[] z;

    public ContentTransport(URL uRL) {
        this.r = uRL;
        this.a__V();
    }

    public static void a(StartupWorker startupWorker) {
        y = startupWorker;
    }

    public void a(boolean bl) {
        this.x = bl;
    }

    private void a__V() {
        this.t = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[29]) != null;
        this.u = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[14]) != null;
        this.b = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[28]);
        boolean bl = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[23]) != null;
        this.m.put(z[28], new TypeE(this, this.b, bl));
        this.c = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[22]);
        bl = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[27]) != null;
        this.m.put(z[22], new TypeE(this, this.c, bl));
        this.d = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[11]);
        bl = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[10]) != null;
        this.m.put(z[11], new TypeE(this, this.d, bl));
        this.e = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[12]);
        bl = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[25]) != null;
        this.m.put(z[12], new TypeE(this, this.e, bl));
        int n = 0;
        String string = z[17] + n;
        String string2 = Configuration.a__La_a_b_d().a__Ljava_lang_String(string);
        while (string2 != null) {
            int n2 = Integer.valueOf(Configuration.a__La_a_b_d().a__Ljava_lang_String(string + z[15]));
            bl = Configuration.a__La_a_b_d().a__Ljava_lang_String(string + z[26]) != null;
            TypeE typeE = new TypeE(this, string2, n2);
            if (bl) {
                this.o.addElement(typeE);
            } else {
                this.n.addElement(typeE);
            }
            string = z[17] + ++n;
            string2 = Configuration.a__La_a_b_d().a__Ljava_lang_String(string);
        }
        this.l = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[18]);
        this.f = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[19]);
        this.g = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[24]);
        this.i = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[13]);
        this.h = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[9]);
        this.k = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[16]);
        this.j = Configuration.a__La_a_b_d().a__Ljava_lang_String(z[21]);
        this.v = Integer.valueOf(Configuration.a__La_a_b_d().a__Ljava_lang_String(z[8]));
        n = 0;
        string = z[20] + n;
        string2 = Configuration.a__La_a_b_d().a__Ljava_lang_String(string);
        while (string2 != null) {
            this.p.addElement(string2);
            string = z[20] + ++n;
            string2 = Configuration.a__La_a_b_d().a__Ljava_lang_String(string);
        }
    }

    public void a(TypeG typeG) {
        this.q.addElement(typeG);
        if (this.x) {
            this.b__V();
        }
    }

    private synchronized void b__V() {
        if (!this.s.isAlive()) {
            this.s = null;
            this.s = new TypeF(this);
            this.s.start();
        }
    }

    public void a__V(String string) {
        this.a.put(string, new Stack());
    }

    private Vector b__Ljava_util_Vector(String string) throws recovered.c.a.a.TypeA {
        Vector<TypeA> vector = new Vector<TypeA>();
        recovered.c.a.a.TypeB typeB = new recovered.c.a.a.TypeB(string);
        for (int i = 0; i < typeB.a__I(); ++i) {
            TypeA typeA = null;
            recovered.c.a.a.TypeC typeC = typeB.b__Lc_a_a_c(i);
            recovered.c.a.a.TypeB typeB2 = null;
            if (!typeC.e(this.f)) {
                typeB2 = typeC.b__Lc_a_a_b(this.f);
                typeA = new recovered.a.a.b.a.a.TypeC();
            }
            if (!typeC.e(this.g)) {
                typeB2 = typeC.b__Lc_a_a_b(this.g);
                typeA = new TypeB();
            }
            if (typeB2 == null) continue;
            for (int j = 0; j < typeB2.a__I(); ++j) {
                recovered.c.a.a.TypeC typeC2 = typeB2.b__Lc_a_a_c(j);
                if (typeC2.d__Z(this.l)) {
                    typeA.a__V(typeC2.c__Ljava_lang_String(this.l));
                    continue;
                }
                if (typeC2.d__Z(this.k)) {
                    typeA.c__V(typeC2.c__Ljava_lang_String(this.k));
                    continue;
                }
                if (typeC2.d__Z(this.j)) {
                    typeA.b__V(typeC2.c__Ljava_lang_String(this.j));
                    continue;
                }
                if (typeC2.d__Z(this.i)) {
                    ((recovered.a.a.b.a.a.TypeC)typeA).d__V(typeC2.c__Ljava_lang_String(this.i));
                    continue;
                }
                if (!typeC2.d__Z(this.h)) continue;
                ((TypeB)typeA).d__V(typeC2.c__Ljava_lang_String(this.h));
            }
            this.a(typeB2);
            vector.addElement(typeA);
        }
        return vector;
    }

    private void a(recovered.c.a.a.TypeB typeB) throws recovered.c.a.a.TypeA {
        for (int i = 0; i < typeB.a__I(); ++i) {
            recovered.c.a.a.TypeC typeC = typeB.b__Lc_a_a_c(i);
            Enumeration enumeration = typeC.a__Ljava_util_Enumeration();
            while (enumeration.hasMoreElements()) {
                String string = enumeration.nextElement().toString();
                if (!this.p.contains(string)) continue;
                try {
                    byte[] byArray = typeC.c__Ljava_lang_String(string).getBytes();
                    if (y == null || !y.a__Z()) continue;
                    y.a__I(this.b__ArrayB(byArray));
                }
                catch (Exception exception) {}
            }
        }
    }

    private synchronized void a(TypeG typeG, String string, recovered.c.a.a.TypeB typeB) throws IOException, recovered.c.a.a.TypeA {
        URL uRL = null;
        uRL = string != null ? new URL(this.r.toString() + string) : new URL(this.r.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        if (typeB.a__I() == 0) {
            httpURLConnection.setRequestMethod(z[3]);
        } else {
            httpURLConnection.setRequestMethod(z[7]);
        }
        OutputStream outputStream = null;
        if (this.u) {
            httpURLConnection.setRequestProperty(z[2], z[6]);
        }
        if (this.t) {
            httpURLConnection.setRequestProperty(z[1], z[6]);
            outputStream = new GZIPOutputStream(httpURLConnection.getOutputStream());
        } else {
            httpURLConnection.setRequestProperty(z[1], z[4]);
            outputStream = httpURLConnection.getOutputStream();
        }
        if (typeB.a__I() > 0) {
            outputStream.write(typeB.toString().getBytes());
        }
        outputStream.flush();
        outputStream.close();
        InputStream inputStream = null;
        String string2 = httpURLConnection.getHeaderField(z[0]);
        inputStream = string2 != null && string2.indexOf(z[6]) != -1 ? new GZIPInputStream(httpURLConnection.getInputStream()) : httpURLConnection.getInputStream();
        byte[] byArray = recovered.b.a.TypeC.a(inputStream, 10000L);
        if (byArray != null && byArray.length > 0) {
            Vector vector = this.b__Ljava_util_Vector(new String(byArray, z[5]));
            ContentChannel contentChannel = (ContentChannel)typeG.getSource();
            for (int i = 0; i < vector.size(); ++i) {
                ((Stack)this.a.get(typeG.a__Ljava_lang_String())).push(((TypeA)vector.elementAt(i)).a__Ljava_lang_String());
                contentChannel.a__V((TypeA)vector.elementAt(i));
            }
        }
    }

    private String a(String string, recovered.c.a.a.TypeB typeB) throws recovered.c.a.a.TypeA {
        String string2;
        TypeE typeE;
        int n;
        recovered.c.a.a.TypeC typeC;
        StringBuffer stringBuffer = new StringBuffer("?");
        String string3 = "";
        try {
            string3 = ((Stack)this.a.get(string)).pop().toString();
        }
        catch (EmptyStackException emptyStackException) {
            string3 = "";
        }
        TypeE typeE2 = (TypeE)this.m.get(z[28]);
        TypeE typeE3 = (TypeE)this.m.get(z[22]);
        TypeE typeE4 = (TypeE)this.m.get(z[12]);
        TypeE typeE5 = (TypeE)this.m.get(z[11]);
        if (typeE2.b__Z()) {
            this.a(stringBuffer, typeE2.a__Ljava_lang_String(), string);
        } else {
            typeC = new recovered.c.a.a.TypeC();
            typeC.a(typeE2.a__Ljava_lang_String(), string);
            typeB.a__Lc_a_a_b(typeC);
        }
        if (typeE3.b__Z()) {
            this.a(stringBuffer, typeE3.a__Ljava_lang_String(), string3);
        } else {
            typeC = new recovered.c.a.a.TypeC();
            typeC.a(typeE3.a__Ljava_lang_String(), string3);
            typeB.a__Lc_a_a_b(typeC);
        }
        if (typeE4.b__Z()) {
            this.a(stringBuffer, typeE4.a__Ljava_lang_String(), String.valueOf(1));
        } else {
            typeC = new recovered.c.a.a.TypeC();
            typeC.a(typeE4.a__Ljava_lang_String(), String.valueOf(1));
            typeB.a__Lc_a_a_b(typeC);
        }
        if (typeE5.b__Z()) {
            this.a(stringBuffer, typeE5.a__Ljava_lang_String(), String.valueOf(this.v));
        } else {
            typeC = new recovered.c.a.a.TypeC();
            typeC.a(typeE5.a__Ljava_lang_String(), String.valueOf(this.v));
            typeB.a__Lc_a_a_b(typeC);
        }
        for (n = 0; n < this.o.size(); ++n) {
            typeE = (TypeE)this.o.elementAt(n);
            string2 = this.a__Ljava_lang_String(typeE.c__I());
            this.a(stringBuffer, typeE.a__Ljava_lang_String(), string2);
        }
        for (n = 0; n < this.n.size(); ++n) {
            typeE = (TypeE)this.n.elementAt(n);
            string2 = this.a__Ljava_lang_String(typeE.c__I());
            recovered.c.a.a.TypeC typeC2 = new recovered.c.a.a.TypeC();
            typeC2.a(typeE.a__Ljava_lang_String(), string2);
            typeB.a__Lc_a_a_b(typeC2);
        }
        String string4 = stringBuffer.length() > 1 ? stringBuffer.toString() : null;
        stringBuffer = null;
        return string4;
    }

    private String a__Ljava_lang_String(int n) {
        byte[] byArray = null;
        if (y != null && y.a__Z()) {
            try {
                byArray = y.a__ArrayB(n);
            }
            catch (Exception exception) {
                byArray = this.b__ArrayB(n);
            }
        } else {
            byArray = this.b__ArrayB(n);
        }
        return this.a__Ljava_lang_String(byArray);
    }

    private void a(StringBuffer stringBuffer, String string, String string2) {
        if (stringBuffer.length() > 1) {
            stringBuffer.append('&');
        }
        stringBuffer.append(string);
        stringBuffer.append("=");
        stringBuffer.append(string2);
    }

    private byte[] b__ArrayB(int n) {
        byte[] byArray = new byte[n];
        this.w.nextBytes(byArray);
        return byArray;
    }

    private String a__Ljava_lang_String(byte[] byArray) {
        return new String(recovered.b.b.TypeC.a__ArrayB(byArray));
    }

    private byte[] b__ArrayB(byte[] byArray) throws recovered.b.b.TypeA {
        return recovered.b.b.TypeC.b__ArrayB(byArray);
    }

    static Vector a(ContentTransport contentTransport) {
        return contentTransport.q;
    }

    static String a(ContentTransport contentTransport, String string, recovered.c.a.a.TypeB typeB) throws recovered.c.a.a.TypeA {
        return contentTransport.a(string, typeB);
    }

    static void a(ContentTransport contentTransport, TypeG typeG, String string, recovered.c.a.a.TypeB typeB) throws IOException, recovered.c.a.a.TypeA {
        contentTransport.a(typeG, string, typeB);
    }

    static {
        z = new String[]{"Content-Encoding", "Content-Type", "Accept-Encoding", "GET", "application/json", "UTF8", "gzip", "POST", "Applet.BundleSize", "ResponseKey.ImgUrl", "RequestKey.Count.IsQuery", "RequestKey.Count", "RequestKey.Offset", "ResponseKey.TextData", "Response.Zip", ".Size", "ResponseKey.Link", "RequestKey.UserData.", "ResponseKey.Index", "ResponseKey.TextItem", "ResponseKey.UserData.", "ResponseKey.Description", "RequestKey.IndexId", "RequestKey.Category.IsQuery", "ResponseKey.ImageItem", "RequestKey.Offset.IsQuery", ".IsQuery", "RequestKey.IndexId.IsQuery", "RequestKey.Category", "Request.Zip"};
    }
}
