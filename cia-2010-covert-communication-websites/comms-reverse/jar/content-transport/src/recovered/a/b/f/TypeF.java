/* slides.jar — a/b/f/f.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.b.f;

import java.io.IOException;
import java.util.Properties;
import recovered.a.a.a.TypeI;
import recovered.a.a.l.TypeK;
import recovered.a.a.n.a.TypeD;
import recovered.a.a.p.TypeE;
import recovered.a.b.b.a.TypeA;
import recovered.a.b.f.TypeB;
import recovered.org.b.b.TypeG;
import recovered.org.b.f.TypeC;

public class TypeF
extends recovered.a.a.p.a.TypeB {
    private static TypeG l;
    private recovered.a.b.b.a.TypeB m;
    private TypeK n;
    private TypeI o;
    private byte[] p = null;
    private TypeC q;
    private static String[] z;

    public TypeF() {
        if (l == null) {
            l = recovered.a.a.k.TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
        }
        this.a__V(this.a__Ljava_util_Properties());
    }

    private Properties a__Ljava_util_Properties() {
        Properties properties = null;
        try {
            properties = TypeB.a__Ljava_util_Properties();
        }
        catch (IOException iOException) {
            l.d(z[5], iOException);
            this.a(new TypeE(z[6], iOException));
        }
        return properties;
    }

    public void a__V(Properties properties) {
        String string = properties.getProperty(z[13]);
        if (string == null || string.length() == 0) {
            l.c(z[12]);
            string = z[10];
        }
        TypeA.a = string;
        int n = -1;
        try {
            n = Integer.parseInt(properties.getProperty(z[11], z[14]));
        }
        catch (NumberFormatException numberFormatException) {
        }
        catch (NullPointerException nullPointerException) {
        }
        if (n < 1) {
            n = 50;
        }
        if (this.n == null) {
            this.m = recovered.a.b.b.a.TypeB.a__La_b_b_a_b();
            this.n = new recovered.a.b.d.TypeB(this.m, n);
            this.o = new recovered.a.b.c.TypeB(this.m);
        }
        super.a__V(properties);
        if (this.p == null) {
            int n2 = -1;
            String string2 = properties.getProperty(z[9]);
            try {
                n2 = Integer.parseInt(string2);
            }
            catch (NumberFormatException numberFormatException) {
                n2 = 16;
            }
            String string3 = properties.getProperty(z[15]);
            if (string3 == null || string3.length() == 0) {
                string3 = z[16];
            }
            String string4 = "";
            try {
                this.p = this.c__La_a_n_f().a__ArrayB(n2);
                string4 = new String(recovered.org.b.d.e.TypeA.c(this.p));
            }
            catch (IOException iOException) {
            }
            try {
                this.q = new TypeC();
                this.q.d(this.a__Ljava_lang_String());
                this.q.c(this.b__Ljava_lang_String());
                this.q.a__V(string3);
                this.q.b__V(string4);
            }
            catch (TypeE typeE) {
                this.q = null;
            }
        }
    }

    public TypeC[] g__ArrayLorg_b_f_c() throws TypeE {
        if (this.q == null) {
            if (this.a__La_a_p_e() != null) {
                throw new TypeE(z[0], this.a__La_a_p_e());
            }
            throw new TypeE(z[1]);
        }
        return new TypeC[]{this.q};
    }

    public TypeK a__La_a_l_k() {
        return this.n;
    }

    public TypeI b__La_a_a_i() {
        return this.o;
    }

    protected TypeD b__La_a_n_a_d() {
        return this.m;
    }

    public String c__Ljava_lang_String() {
        return z[19];
    }

    protected String d__Ljava_lang_String() {
        return z[8];
    }

    public String e__Ljava_lang_String() {
        return z[18];
    }

    protected String g__Ljava_lang_String() {
        return z[2];
    }

    protected String h__Ljava_lang_String() {
        return z[17];
    }

    protected String k__Ljava_lang_String() {
        return z[7];
    }

    protected String m() {
        return z[4];
    }

    protected int n() {
        return 2000;
    }

    protected String o() {
        return z[3];
    }

    protected int p() {
        return 25;
    }

    static {
        z = new String[]{"No Properties file to read cookie data.", "The session cookie key Connection.Request.Cookie.Name is not defined in the properties file.", "Connection.Request.Header", "Connection.Request.MaxBundleSize", "Host.Connection.Delay", "Error loading properties.", "Unable to load configurations.", "Connection.Socket.TimeOut", "Connection.Servlet.Path", "Connection.Request.Cookie.Size", "thread", "Connection.Request.SecureSize", "The thread name was not defined, the default value will be used.", "Host.Connection.ThreadName", "-1", "Connection.Request.Cookie.Name", "SessionId", "Connection.Request.TimeOut", "Connection.Servlet.Port", "Connection.Servlet.Address"};
    }
}
