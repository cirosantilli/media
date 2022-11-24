/* world.jar — b/a/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.b.a;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.io.InputStream;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import recovered.b.a.a.TrafficWorker;
import recovered.b.a.b.TypeA;
import recovered.b.a.b.TypeB;
import recovered.b.a.b.TypeC;
import recovered.b.a.b.TypeD;

public class MeterApplet
extends Applet
implements Runnable {
    private Object a;
    private TrafficWorker b;
    private boolean c;
    private Thread d;
    private int e;
    private int f;
    private String g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private String m;
    private Color n;
    private Color o;
    private Color p;
    private String q;
    private int r;
    private int s;
    private double t;
    private int u;
    private int v;
    private String w;
    private int x;
    private static final Class y;
    private Properties z;
    private final String A;
    private final String B;
    private final String C;
    private final String D;
    private final String E;
    private final String F;
    private final String G;
    private final String H;
    private final String I;
    private final String J;
    private final String K;
    private final String L;
    private final String M;
    private final String N;
    private final String O;
    private final String P;
    private final String Q;
    private final String R;
    private final String S;
    private final String T;
    public static boolean U;
    private static String[] V;

    public MeterApplet() {
        boolean bl = U;
        this.a = null;
        this.b = null;
        this.c = false;
        this.d = null;
        this.e = 1000;
        this.f = 1;
        this.g = "";
        this.h = 1024;
        this.i = 512;
        this.j = 1;
        this.k = 1000;
        this.l = 500;
        this.m = V[18];
        this.n = new Color(255, 255, 255);
        this.o = new Color(192, 192, 192);
        this.p = new Color(198, 0, 8);
        this.q = V[16];
        this.r = 1;
        this.s = 6;
        this.t = 9000.0;
        this.u = 1;
        this.v = 1;
        this.w = null;
        this.x = 0;
        this.z = null;
        this.A = V[8];
        this.B = V[7];
        this.C = V[10];
        this.D = V[3];
        this.E = V[19];
        this.F = V[17];
        this.G = V[4];
        this.H = V[2];
        this.I = V[14];
        this.J = V[23];
        this.K = V[22];
        this.L = V[15];
        this.M = V[5];
        this.N = V[20];
        this.O = V[21];
        this.P = V[9];
        this.Q = V[11];
        this.R = V[6];
        this.S = V[12];
        this.T = V[13];
        if (bl) {
            int n = recovered.c.a.a.TypeB.b;
            recovered.c.a.a.TypeB.b = ++n;
        }
    }

    public String getAppletInfo() {
        return V[24];
    }

    public void init() {
        this.z = this.a__Ljava_util_Properties();
        this.b__V();
        this.g();
    }

    public void start() {
        this.c = false;
        this.d = new Thread(this);
        this.d.start();
        this.b.start();
    }

    public void stop() {
        boolean bl = U;
        this.c = true;
        while (this.d.isAlive()) {
        }
        this.b.b__V();
        this.b = null;
        if (recovered.c.a.a.TypeB.b != 0) {
            U = !bl;
        }
    }

    public void run() {
        try {
            while (!this.c) {
                Thread.sleep(1000L);
                this.repaint();
                if (!U) continue;
                break;
            }
        }
        catch (Exception exception) {
        }
    }

    public void repaint() {
        boolean bl = U;
        switch (this.f) {
            case 0: {
                ((TypeB)this.a).b(this.b.a__D());
                if (!bl) break;
            }
            case 1: {
                ((TypeC)this.a).b(this.b.a__D());
                if (!bl) break;
            }
            case 2: {
                ((TypeA)this.a).b(this.b.a__D());
                if (!bl) break;
            }
            case 3: {
                ((TypeD)this.a).b(this.b.a__D());
            }
        }
        super.repaint();
        ((Canvas)this.a).repaint();
    }

    private Properties a__Ljava_util_Properties() {
        Properties properties = null;
        try {
            InputStream inputStream = y.getResourceAsStream(V[0]);
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            properties = new Properties();
            properties.load(zipInputStream);
        }
        catch (Exception exception) {
            properties = new Properties();
        }
        return properties;
    }

    private void b__V() {
        boolean bl = U;
        this.f = this.a(V[2], this.f);
        switch (this.f) {
            case 0: {
                this.c();
                if (!bl) break;
            }
            case 1: {
                this.d__V();
                if (!bl) break;
            }
            case 2: {
                this.f();
                if (!bl) break;
            }
            case 3: {
                this.e();
                if (!bl) break;
            }
            default: {
                throw new RuntimeException(V[26]);
            }
        }
        this.setLayout(new BorderLayout());
        this.add((Component)((Canvas)this.a), V[25]);
    }

    private void c() {
        this.a = new TypeB();
        TypeB typeB = (TypeB)this.a;
        typeB.setBackground(this.a(V[14], this.n));
        typeB.setForeground(this.a(V[23], this.o));
        typeB.a(this.a(V[22], this.p));
        String string = this.a(V[5], this.q);
        if (string != null) {
            typeB.setFont(new Font(string, 0, 12));
        }
        typeB.a(this.a(V[9], this.t));
    }

    private void d__V() {
        this.a = new TypeC();
        TypeC typeC = (TypeC)this.a;
        typeC.setBackground(this.a(V[14], this.n));
        typeC.setForeground(this.a(V[23], this.o));
        typeC.a(this.a(V[22], this.p));
        typeC.c(this.a(V[20], this.r));
        typeC.d__V(this.a(V[6], this.s));
        typeC.a(this.a(V[9], this.t));
        typeC.b(this.a(V[11], this.u));
        typeC.a(this.a(V[21], this.v));
        typeC.e(this.a(V[12], this.x));
        typeC.f(this.a(V[13], this.x));
        Image image = null;
        image = this.a(V[15], image);
        if (image != null) {
            typeC.a(image);
        }
    }

    private void e() {
        this.a = new TypeD();
        TypeD typeD = (TypeD)this.a;
        typeD.setBackground(this.a(V[14], this.n));
        typeD.setForeground(this.a(V[23], this.o));
        typeD.a(this.a(V[22], this.p));
        typeD.c(this.a(V[20], this.r));
        typeD.d__V(this.a(V[6], this.s));
        typeD.a(this.a(V[9], this.t));
        typeD.b(this.a(V[11], this.u));
        typeD.a(this.a(V[21], this.v));
        typeD.e(this.a(V[12], this.x));
        typeD.f(this.a(V[13], this.x));
        Image image = null;
        image = this.a(V[15], image);
        if (image != null) {
            typeD.a(image);
        }
    }

    private void f() {
        this.a = new TypeA();
        TypeA typeA = (TypeA)this.a;
        typeA.setBackground(this.a(V[14], this.n));
        typeA.setForeground(this.a(V[23], this.o));
        typeA.a(this.a(V[22], this.p));
        typeA.b(this.a(V[20], this.r));
        typeA.c(this.a(V[6], this.s));
        typeA.a(this.a(V[9], this.t));
        typeA.a(this.a(V[11], this.u));
        Image image = null;
        image = this.a(V[15], image);
        if (image != null) {
            typeA.a(image);
        }
    }

    private void g() {
        this.b = new TrafficWorker(this.a(V[8], this.g));
        this.b.a(this.a(V[10], this.i), this.b.u);
        this.b.a(this.a(V[7], this.h), this.b.t);
        this.b.b(this.a(V[19], this.m));
        this.b.a(this.a(V[17], this.j));
        this.b.b(this.a(V[3], this.k));
        this.b.c(this.a(V[4], this.l));
    }

    private double a(String string, double d) {
        String string2 = this.z.getProperty(string);
        if (string2 != null && string2.length() > 0) {
            d = Double.valueOf(string2);
        }
        return d;
    }

    private int a(String string, int n) {
        String string2 = this.z.getProperty(string);
        if (string2 != null && string2.length() > 0) {
            n = Integer.parseInt(string2);
        }
        return n;
    }

    private String a(String string, String string2) {
        String string3 = this.z.getProperty(string);
        if (string3 != null && string3.length() > 0) {
            string2 = new String(string3);
        }
        return string2;
    }

    private Color a(String string, Color color) {
        String string2 = this.z.getProperty(string);
        if (string2 != null && string2.length() > 0) {
            int n = Integer.decode(V[1] + string2.substring(0, 2));
            int n2 = Integer.decode(V[1] + string2.substring(2, 4));
            int n3 = Integer.decode(V[1] + string2.substring(4, 6));
            color = new Color(n, n2, n3);
        }
        return color;
    }

    private Image a(String string, Image image) {
        block5: {
            block4: {
                String string2 = this.z.getProperty(string);
                if (string2 == null || string2.length() <= 0) break block4;
                image = this.getImage(this.getCodeBase(), string2);
                if (!U) break block5;
            }
            if (this.w != null && this.w.length() > 0) {
                image = this.getImage(this.getCodeBase(), this.w);
            }
        }
        return image;
    }

    static {
        V = new String[]{"bw.properties", "0x", "DisplayMode", "Cycle", "Delay", "Font", "Thickness", "SendSize", "URL", "ScaleMax", "ReadSize", "ScaleMode", "BorderX", "BorderY", "BackColor", "BackImage", "Monospaced", "Repetition", "UD", "Pattern", "DisplayForeColor", "Orientation", "DrawColor", "ForeColor", "Name: Traffic (network bandwidth meter)\r\n", "Center", "U/I Mode not set"};
        y = MeterApplet.class;
    }
}
