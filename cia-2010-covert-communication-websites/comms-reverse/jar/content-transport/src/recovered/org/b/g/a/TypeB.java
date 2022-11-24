/* slides.jar — org/b/g/a/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.g.a;

import recovered.org.b.g.a.TypeA;

public class TypeB {
    private static TypeB a;
    private static TypeA b;
    private static String[] c;
    private TypeA d;

    public static void a(TypeA typeA) {
        if (typeA == null) {
            if (b == null) {
                TypeB.b__V();
            }
            a = new TypeB(b);
        } else {
            a = new TypeB(typeA);
        }
    }

    public static TypeA a__Lorg_b_g_a_a() {
        if (a == null) {
            TypeB.a(null);
        }
        return a.c__Lorg_b_g_a_a();
    }

    private static void b__V() {
        b = TypeA.a__Lorg_b_g_a_a(c);
    }

    private TypeB(TypeA typeA) {
        this.d = typeA != null ? typeA : b;
    }

    public TypeA c__Lorg_b_g_a_a() {
        return this.d;
    }

    static {
        c = new String[]{"java.security.SecureRandom", "java.util.Random"};
    }
}
