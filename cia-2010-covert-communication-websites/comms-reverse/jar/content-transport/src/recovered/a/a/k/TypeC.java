/* slides.jar — a/a/k/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.k;

import recovered.a.a.k.TypeA;
import recovered.a.a.k.TypeB;

public class TypeC
extends TypeB {
    private static TypeC e;
    private static recovered.a.a.i.a.TypeA f;
    private static recovered.a.a.d.c.TypeA g;
    private static recovered.a.a.e.a.TypeA h;
    private static String z;

    public void a(recovered.a.a.i.a.TypeA typeA) {
        f = typeA;
    }

    public void a(recovered.a.a.d.c.TypeA typeA) {
        g = typeA;
    }

    public void a(recovered.a.a.e.a.TypeA typeA) {
        h = typeA;
    }

    public static TypeC a__La_a_k_c() {
        return e;
    }

    public recovered.a.a.i.a.TypeA b__La_a_i_a_a() throws TypeA {
        if (f == null) {
            throw new TypeA(z);
        }
        return f;
    }

    public recovered.a.a.d.c.TypeA c__La_a_d_c_a() throws TypeA {
        if (g == null) {
            throw new TypeA(z);
        }
        return g;
    }

    public recovered.a.a.e.a.TypeA d__La_a_e_a_a() throws TypeA {
        if (h == null) {
            throw new TypeA(z);
        }
        return h;
    }

    static {
        z = "The requested factory has not been defined.";
        e = new TypeC();
    }
}
