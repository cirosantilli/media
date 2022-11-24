/* slides.jar — a/b/b/a/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.b.b.a;

import recovered.a.b.b.a.TypeA;

public class TypeB
extends recovered.a.a.n.a.a.TypeA {
    private static TypeB n;
    private String p = TypeA.a;

    protected TypeB() {
        this.b__V();
    }

    protected TypeB(ThreadGroup threadGroup) {
        super(threadGroup);
        this.b__V();
    }

    public static TypeB a__La_b_b_a_b() {
        if (n == null || !n.isAlive()) {
            ThreadGroup threadGroup = recovered.a.a.k.TypeB.a__La_a_k_b().c__La_a_p_o().a__Ljava_lang_ThreadGroup();
            n = threadGroup == null ? new TypeB() : new TypeB(threadGroup);
        }
        return n;
    }

    protected String a__Ljava_lang_String() {
        return this.p;
    }
}
