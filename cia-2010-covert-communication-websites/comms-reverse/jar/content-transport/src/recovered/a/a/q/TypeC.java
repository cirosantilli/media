/* slides.jar — a/a/q/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.q;

import recovered.a.a.n.TypeD;
import recovered.a.a.n.TypeH;
import recovered.a.a.n.TypeI;
import recovered.a.a.q.TypeB;

public abstract class TypeC
extends TypeB {
    private TypeI a = null;

    protected TypeC(TypeH typeH) {
        this.a(typeH);
    }

    public void a(TypeH typeH) {
        try {
            this.a = (TypeI)typeH;
        }
        catch (ClassCastException classCastException) {
            this.a = new TypeD(typeH);
        }
    }

    protected TypeI a__La_a_n_i() {
        return this.a;
    }
}
