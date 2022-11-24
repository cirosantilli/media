/* slides.jar — a/b/f/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.b.f;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import recovered.org.b.e.TypeC;

public class TypeB {
    private static final Class b;
    private static recovered.org.b.e.TypeB d;
    private static Hashtable e;
    static /* synthetic */ Class f;
    private static String[] z;

    public static Properties a__Ljava_util_Properties() throws IOException {
        return TypeB.b__Lorg_b_e_b().d__Ljava_util_Properties();
    }

    public static recovered.org.b.e.TypeB b__Lorg_b_e_b() throws IOException {
        if (d == null) {
            TypeB.c__V();
        }
        return d;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void a__V(String string, String string2) {
        Hashtable hashtable = e;
        synchronized (hashtable) {
            if (d != null) {
                try {
                    d.a__V(string, string2.getBytes(z[0]));
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    d.a__V(string, string2.getBytes());
                }
            }
            e.put(string, string2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected static void c__V() throws IOException {
        InputStream inputStream = b.getResourceAsStream(z[1]);
        d = new TypeC(z[2].getBytes()).a__Lorg_b_e_b(inputStream);
        Hashtable hashtable = e;
        synchronized (hashtable) {
            Enumeration enumeration = e.keys();
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                String string2 = (String)e.get(string);
                try {
                    d.a__V(string, string2.getBytes(z[0]));
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    d.a__V(string, string2.getBytes());
                }
            }
        }
    }

    static /* synthetic */ Class a__Ljava_lang_Class(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    static {
        z = new String[]{"UTF8", "Client.properties", "60A514D96C19F93CB71DF5A25E3B3305"};
        b = f == null ? (f = TypeB.a__Ljava_lang_Class("a.b.f.b")) : f;
        e = new Hashtable();
        TypeB.a__V("Connection.Request.Cookie.Size", "16");
        TypeB.a__V("Host.Connection.Delay", "1200");
    }

    private static char[] z(String string) {
        char[] cArray = string.toCharArray();
        if (cArray.length < 2) {
            cArray = cArray;
            cArray[0] = (char)(cArray[0] ^ 0x4F);
        }
        return cArray;
    }

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    private static String z(char[] cArray) {
        char[] cArray2;
        block9: {
            int n;
            int n2;
            block8: {
                cArray2 = cArray;
                n2 = cArray.length;
                n = 0;
                if (!true) break block8;
                n2 = n2;
                if (n2 <= n) break block9;
            }
            do {
                int n3;
                cArray2 = cArray2;
                int n4 = n;
                char c = cArray2[n4];
                switch (n % 5) {
                    case 0: {
                        n3 = 109;
                        break;
                    }
                    case 1: {
                        n3 = 26;
                        break;
                    }
                    case 2: {
                        n3 = 17;
                        break;
                    }
                    case 3: {
                        n3 = 59;
                        break;
                    }
                    default: {
                        n3 = 79;
                    }
                }
                cArray2[n4] = (char)(c ^ n3);
                ++n;
                n2 = n2;
            } while (n2 > n);
        }
        return new String(cArray2).intern();
    }
}
