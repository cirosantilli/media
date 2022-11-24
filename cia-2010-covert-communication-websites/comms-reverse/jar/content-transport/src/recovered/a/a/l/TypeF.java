/* slides.jar — a/a/l/f.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.l;

import java.io.IOException;
import recovered.a.a.b.TypeA;
import recovered.a.a.l.TypeC;
import recovered.a.a.l.TypeD;
import recovered.a.a.l.TypeN;
import recovered.a.a.n.TypeH;

public class TypeF
extends recovered.a.a.q.TypeC {
    private static String[] z = new String[]{"Failed to stuff data into request parameters.", "Data Provider for data to fill into RequestParmeters is null; data insertion cannot occur.", "Failed to stuff data into the request parameters.", "Failed to stuff data into request parameters; request parameters are null."};

    public TypeF(TypeH typeH) {
        super(typeH);
    }

    public TypeN a(TypeN typeN) throws TypeC {
        if (typeN == null) {
            throw new TypeC(z[3]);
        }
        try {
            int n = typeN.a__I();
            byte[] byArray = this.b__ArrayB(n);
            typeN.a__I(byArray);
            return typeN;
        }
        catch (TypeA typeA) {
            throw new TypeC(z[2], typeA);
        }
    }

    protected byte[] b__ArrayB(int n) throws TypeD {
        if (super.a__La_a_n_i() == null) {
            throw new TypeD(z[1]);
        }
        try {
            return super.a__La_a_n_i().a__ArrayB(n);
        }
        catch (IOException iOException) {
            throw new TypeD(z[0], iOException);
        }
    }
}
