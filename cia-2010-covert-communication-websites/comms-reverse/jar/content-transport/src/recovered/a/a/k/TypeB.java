/* slides.jar — a/a/k/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.k;

import recovered.a.a.e.b.a.TypeC;
import recovered.a.a.h.TypeE;
import recovered.a.a.h.TypeF;
import recovered.a.a.k.TypeA;
import recovered.a.a.p.TypeN;
import recovered.a.a.p.TypeO;
import recovered.org.b.b.TypeH;

public class TypeB {
    private static TypeB a;
    private static TypeC b;
    private static TypeF c;
    private static TypeO d;
    private static String z;

    public static TypeB a__La_a_k_b() {
        if (a == null) {
            TypeB.a(new TypeB());
        }
        return a;
    }

    public static void a(TypeB typeB) {
        a = typeB;
    }

    public void a(TypeC typeC) {
        b = typeC;
    }

    public void a(TypeF typeF) {
        c = typeF;
    }

    public void a(TypeO typeO) {
        d = typeO;
    }

    public TypeC b__La_a_e_b_a_c() throws TypeA {
        if (b == null) {
            throw new TypeA(z);
        }
        return b;
    }

    public TypeO c__La_a_p_o() {
        if (d == null) {
            return new TypeN(null);
        }
        return d;
    }

    public TypeF d__La_a_h_f() {
        if (c == null) {
            return new TypeE(new TypeH());
        }
        return c;
    }

    static {
        z = "The requested factory has not been defined.";
    }
}
