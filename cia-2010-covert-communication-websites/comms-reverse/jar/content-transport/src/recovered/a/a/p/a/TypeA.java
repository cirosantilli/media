/* slides.jar — a/a/p/a/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.p.a;

import recovered.a.a.k.TypeB;
import recovered.a.a.l.TypeL;
import recovered.a.a.l.TypeN;
import recovered.a.a.o.TypeC;
import recovered.a.a.p.TypeD;
import recovered.a.a.p.TypeG;
import recovered.a.a.p.TypeH;

public abstract class TypeA
extends TypeG {
    private static recovered.org.b.b.TypeG l;
    private TypeD m;
    private Object n = new Integer(0);
    private String o;
    private static String[] z;

    public TypeA(String string) {
        this.o = string;
        if (l == null) {
            l = TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
        }
        try {
            this.a(this.a__La_a_p_h());
        }
        catch (TypeD typeD) {
            l.c(z[1], typeD);
            this.m = typeD;
        }
        TypeL typeL = super.a__La_a_l_l();
        if (typeL != null && typeL instanceof TypeC) {
            this.a((TypeC)((Object)typeL));
        }
    }

    public String c__Ljava_lang_String() {
        return this.o;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected recovered.a.a.d.a.TypeD[] b__ArrayLa_a_d_a_d(TypeN[] typeNArray) throws TypeD {
        if (this.m != null) {
            throw new TypeD(z[0], this.m);
        }
        recovered.a.a.d.a.TypeD[] typeDArray = null;
        TypeD typeD = null;
        Object object = this.n;
        synchronized (object) {
            try {
                typeDArray = super.b__ArrayLa_a_d_a_d(typeNArray);
            }
            catch (TypeD typeD2) {
                typeD = typeD2;
            }
        }
        if (typeD != null) {
            throw typeD;
        }
        if (typeDArray == null) {
            return null;
        }
        this.a(typeDArray);
        return typeDArray;
    }

    protected abstract TypeH a__La_a_p_h();

    protected void a(recovered.a.a.d.a.TypeD[] typeDArray) {
    }

    static {
        z = new String[]{"Unable to initialize the coordinator.", "Error in initialization:"};
    }
}
