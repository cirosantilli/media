/* slides.jar — a/a/a/f.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.a;

import java.io.IOException;
import recovered.a.a.a.TypeA;
import recovered.a.a.a.TypeD;
import recovered.a.a.d.c.TypeB;
import recovered.a.a.n.TypeE;

public class TypeF
extends recovered.a.a.q.TypeD {
    private static String[] z = new String[]{"Error in consuming the parsed data.", "Problem in consuming secure data; data set is null.", "Failed to unstuff data from the response object.", "Failed to unstuff data from response data; response display set is null."};

    public TypeF(TypeE typeE) {
        super(typeE);
    }

    public void a(TypeB typeB) throws TypeA {
        if (typeB == null) {
            throw new TypeA(z[3]);
        }
        try {
            byte[] byArray = typeB.a__ArrayB();
            this.a__V(byArray);
        }
        catch (recovered.a.a.b.TypeA typeA) {
            throw new TypeA(z[2], typeA);
        }
    }

    protected void a__V(byte[] byArray) throws TypeD {
        if (byArray == null) {
            throw new TypeD(z[1]);
        }
        try {
            super.a__La_a_n_e().a__V(byArray);
        }
        catch (IOException iOException) {
            throw new TypeD(z[0], iOException);
        }
    }
}
