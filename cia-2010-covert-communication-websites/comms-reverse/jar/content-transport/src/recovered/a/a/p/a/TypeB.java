/* slides.jar — a/a/p/a/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.p.a;

import java.util.Properties;
import recovered.a.a.a.TypeI;
import recovered.a.a.l.TypeK;
import recovered.a.a.n.TypeF;
import recovered.a.a.n.a.TypeD;
import recovered.a.a.p.TypeE;
import recovered.a.a.p.TypeH;
import recovered.org.b.b.TypeG;
import recovered.org.b.f.TypeC;

public abstract class TypeB
implements TypeH {
    private recovered.a.a.h.TypeF a = recovered.a.a.k.TypeB.a__La_a_k_b().d__La_a_h_f();
    private static TypeG b;
    private Properties c;
    private String d;
    private String e;
    private int f;
    private String g;
    private int h;
    private int i;
    private int j;
    private TypeE k;
    private static String[] z;

    public TypeB() {
        b = this.a.a__Lorg_b_b_g(this.getClass().getName());
    }

    protected void a__V(Properties properties) {
        this.c = properties;
        int n = -1;
        int n2 = 0;
        if (this.c != null) {
            this.d = this.c.getProperty(this.c__Ljava_lang_String());
            this.e = this.c.getProperty(this.d__Ljava_lang_String());
            this.g = this.c.getProperty(this.g__Ljava_lang_String());
            String string = this.c.getProperty(this.e__Ljava_lang_String());
            try {
                this.f = Integer.parseInt(string);
            }
            catch (NumberFormatException numberFormatException) {
                this.f = this.i__I();
            }
            String string2 = this.c.getProperty(this.h__Ljava_lang_String());
            try {
                this.h = Integer.parseInt(string2);
            }
            catch (NumberFormatException numberFormatException) {
                this.h = this.j();
            }
            String string3 = this.c.getProperty(this.k__Ljava_lang_String());
            try {
                this.i = Integer.parseInt(string3);
            }
            catch (NumberFormatException numberFormatException) {
                this.i = this.l();
            }
            String string4 = this.c.getProperty(this.m());
            try {
                n2 = Integer.parseInt(string4);
            }
            catch (NumberFormatException numberFormatException) {
                n2 = this.n();
            }
            String string5 = this.c.getProperty(this.o());
            try {
                this.j = Integer.parseInt(string5);
            }
            catch (NumberFormatException numberFormatException) {
                this.j = this.p();
            }
        }
        if (this.d != null && this.e != null) {
            long l = System.currentTimeMillis();
            boolean bl = false;
            while (!bl && System.currentTimeMillis() - l < (long)n2) {
                bl = this.b__La_a_n_a_d().a__Z();
                if (bl) continue;
                Thread.currentThread();
                Thread.yield();
            }
        }
    }

    protected void a(TypeE typeE) {
        this.k = typeE;
    }

    protected TypeE a__La_a_p_e() {
        return this.k;
    }

    public String a__Ljava_lang_String() throws TypeE {
        if (this.d == null) {
            if (this.k != null) {
                throw new TypeE(z[0], this.k);
            }
            throw new TypeE(z[2] + this.c__Ljava_lang_String() + z[1]);
        }
        return this.d;
    }

    public String b__Ljava_lang_String() throws TypeE {
        if (this.e == null) {
            if (this.k != null) {
                throw new TypeE(z[4], this.k);
            }
            throw new TypeE(z[2] + this.d__Ljava_lang_String() + z[1]);
        }
        return this.e;
    }

    public int c__I() throws TypeE {
        if (this.f == -1) {
            if (this.k != null) {
                throw new TypeE(z[3], this.k);
            }
            throw new TypeE(z[2] + this.e__Ljava_lang_String() + z[1]);
        }
        return this.f;
    }

    public String f__Ljava_lang_String() throws TypeE {
        return this.g;
    }

    public TypeC[] g__ArrayLorg_b_f_c() throws TypeE {
        return new TypeC[0];
    }

    public TypeF c__La_a_n_f() {
        return this.b__La_a_n_a_d();
    }

    protected abstract TypeD b__La_a_n_a_d();

    public abstract String c__Ljava_lang_String();

    protected abstract String d__Ljava_lang_String();

    public abstract String e__Ljava_lang_String();

    protected abstract String g__Ljava_lang_String();

    protected abstract String h__Ljava_lang_String();

    protected int i__I() {
        return 80;
    }

    protected int j() {
        return 6000;
    }

    protected abstract String k__Ljava_lang_String();

    protected int l() {
        return 15000;
    }

    protected abstract String m();

    protected int n() {
        return 4000;
    }

    protected abstract String o();

    protected int p() {
        return 99;
    }

    public int d__I() {
        return this.h;
    }

    public int e__I() {
        return this.i;
    }

    public int f__I() {
        return this.j;
    }

    public abstract /* synthetic */ TypeI b__La_a_a_i() throws TypeE;

    public abstract /* synthetic */ TypeK a__La_a_l_k() throws TypeE;

    static {
        z = new String[]{"No Properties file to read request host.", " is not defined in the properties file.", "The key ", "No Properties file to read request port.", "No Properties file to read request path."};
    }
}
