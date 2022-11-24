/* slides.jar — a/b/f/i.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.b.f;

import recovered.a.a.h.TypeA;

public class TypeI
extends TypeA {
    private static final Class g;
    static /* synthetic */ Class h;
    private static String z;

    public static void a__V() {
        TypeI typeI = new TypeI();
        typeI.d__V();
    }

    protected String a__Ljava_lang_String() {
        return z;
    }

    protected Class b__Ljava_lang_Class() {
        return g;
    }

    static /* synthetic */ Class b__Ljava_lang_Class(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    static {
        z = "/manifest.log";
        g = h == null ? (h = TypeI.b__Ljava_lang_Class("a.b.f.i")) : h;
    }

    private static char[] z(String string) {
        char[] cArray = string.toCharArray();
        if (cArray.length < 2) {
            cArray = cArray;
            cArray[0] = (char)(cArray[0] ^ 0x7A);
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
                        n3 = 15;
                        break;
                    }
                    case 1: {
                        n3 = 28;
                        break;
                    }
                    case 2: {
                        n3 = 121;
                        break;
                    }
                    case 3: {
                        n3 = 25;
                        break;
                    }
                    default: {
                        n3 = 122;
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
