/* slides.jar — org/b/f/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.f;

import java.util.Date;
import java.util.StringTokenizer;
import recovered.org.b.f.TypeB;

public class TypeC {
    private String a = "";
    private String b = "";
    private Date c = null;
    private String d = "";
    private String e;
    private boolean f;
    private static String[] z = new String[]{"expires", "Malformed Cookie.", "secure", "path"};

    public TypeC() {
        this.e = "";
        this.f = false;
    }

    public TypeC(String string, String string2) throws TypeB {
        this.e = string;
        this.f = false;
        boolean bl = false;
        StringTokenizer stringTokenizer = new StringTokenizer(string2, ";", false);
        while (stringTokenizer.hasMoreTokens()) {
            String string3 = stringTokenizer.nextToken();
            if (string3 == null) {
                throw new TypeB(z[1]);
            }
            int n = string3.indexOf(61);
            String string4 = "";
            String string5 = "";
            if (n >= 0) {
                string4 = string3.substring(0, n);
                string5 = string3.substring(n + 1);
            } else {
                string4 = string3;
            }
            if (string4 == null || string4.length() <= 0 || string5 == null) {
                // empty if block
            }
            if (!bl) {
                bl = true;
                this.a__V(string4);
                this.b__V(string5);
                continue;
            }
            if (string4.length() > 0) {
                string4 = string4.substring(1);
            }
            if (string4.equalsIgnoreCase(z[3])) {
                this.c(string5);
                continue;
            }
            if (string4.equalsIgnoreCase(z[0])) {
                this.a(null);
                continue;
            }
            if (!string4.equalsIgnoreCase(z[2])) continue;
            this.a(false);
        }
    }

    public String a__Ljava_lang_String() {
        return this.a;
    }

    public void a__V(String string) {
        this.a = string;
    }

    public String b__Ljava_lang_String() {
        return this.b;
    }

    public void b__V(String string) {
        this.b = string;
    }

    public void a(Date date) {
        this.c = date;
    }

    public String d__Ljava_lang_String() {
        return this.d;
    }

    public void c(String string) {
        this.d = string;
    }

    public String e__Ljava_lang_String() {
        return this.e;
    }

    public void d(String string) {
        this.e = string;
    }

    public void a(boolean bl) {
        this.f = bl;
    }
}
