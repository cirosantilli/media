/* world.jar — c/b/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.c.b;

import recovered.c.b.TypeB;

public class TypeC
extends TypeB {
    private static char[] b;
    private static byte[] c;

    protected static char[] a__ArrayC() {
        char[] cArray = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/', '\u0000', '='};
        return cArray;
    }

    private static void b__V() {
        b = TypeC.a__ArrayC();
    }

    private static void c() {
        int n;
        for (n = 0; n < c.length; ++n) {
            TypeC.c[n] = -1;
        }
        for (n = 0; n < b.length; ++n) {
            char c = b[n];
            TypeC.c[c] = (byte)n;
        }
    }

    static {
        c = new byte[128];
        TypeC.b__V();
        TypeC.c();
    }
}
