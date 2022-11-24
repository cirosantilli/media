/* slides.jar — a/b/f/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.b.f;

import recovered.a.a.e.c.TypeD;
import recovered.a.a.e.c.TypeE;
import recovered.a.a.p.TypeH;
import recovered.a.a.p.a.TypeA;
import recovered.a.b.f.TypeF;

public class TypeC
extends TypeA {
    private static String z = "POST";

    public TypeC() {
        super(z);
    }

    protected TypeH a__La_a_p_h() {
        return new TypeF();
    }

    protected void a(recovered.a.a.d.a.TypeD[] typeDArray) {
        for (int i = 0; i < typeDArray.length; ++i) {
            try {
                ((TypeD)typeDArray[i].a__La_a_e_c_h()).b__V(2);
                ((TypeD)typeDArray[i].a__La_a_e_c_h()).a__V(0);
                ((TypeE)typeDArray[i].a__La_a_e_c_h()).a__V();
                ((TypeE)typeDArray[i].a__La_a_e_c_h()).b__V(2);
                ((TypeE)typeDArray[i].a__La_a_e_c_h()).a__V(0);
                ((TypeE)typeDArray[i].a__La_a_e_c_h()).a__V();
                continue;
            }
            catch (ClassCastException classCastException) {
            }
        }
    }
}
