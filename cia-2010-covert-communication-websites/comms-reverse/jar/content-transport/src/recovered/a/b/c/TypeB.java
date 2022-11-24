/* slides.jar — a/b/c/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.b.c;

import recovered.a.a.a.TypeA;
import recovered.a.a.a.TypeF;
import recovered.a.a.a.TypeJ;
import recovered.a.a.e.TypeD;
import recovered.a.a.n.TypeE;

public class TypeB
extends TypeJ {
    private TypeF a = null;
    private static String z = "Cannot convert DisplayDataSet: ";

    public TypeB(TypeE typeE) {
        this.a(typeE);
    }

    public void a(TypeE typeE) {
        this.a = new TypeF(typeE);
    }

    public recovered.a.a.d.a.TypeD[] a__ArrayLa_a_d_a_d(byte[] byArray) throws TypeA {
        recovered.a.a.d.c.TypeB[] typeBArray = super.a__ArrayLa_a_d_c_b(byArray);
        if (this.a != null) {
            for (int i = 0; i < typeBArray.length; ++i) {
                this.a.a(typeBArray[i]);
            }
        }
        recovered.a.a.d.a.TypeD[] typeDArray = new recovered.a.a.d.a.TypeD[typeBArray.length];
        try {
            for (int i = 0; i < typeBArray.length; ++i) {
                typeDArray[i] = typeBArray[i].d__La_a_d_a_d();
            }
        }
        catch (TypeD typeD) {
            throw new TypeA(z + typeD.getMessage(), typeD);
        }
        return typeDArray;
    }
}
