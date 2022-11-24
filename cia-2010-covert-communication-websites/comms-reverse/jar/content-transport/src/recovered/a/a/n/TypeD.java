/* slides.jar — a/a/n/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.n;

import java.io.IOException;
import recovered.a.a.n.TypeH;
import recovered.a.a.n.TypeI;
import recovered.a.a.n.TypeL;

public class TypeD
implements TypeI {
    private TypeH a = null;
    private TypeH b = null;
    private int c = -1;
    private static String[] z = new String[]{"The clean data provider cannot provide a needed number of bytes: ", "The clean data provider is set to null; no data can be provided."};

    public TypeD(TypeH typeH) {
        this(typeH, new TypeL());
    }

    public TypeD(TypeH typeH, TypeH typeH2) {
        this.a(typeH);
        this.b(typeH2);
    }

    private void a(TypeH typeH) {
        this.a = typeH;
    }

    protected TypeH a__La_a_n_h() {
        return this.a;
    }

    private void b(TypeH typeH) {
        this.b = typeH;
    }

    private TypeH b__La_a_n_h() {
        return this.b;
    }

    public int available() {
        int n = -1;
        int n2 = -1;
        if (this.a__La_a_n_h() != null) {
            n = this.a__La_a_n_h().available();
        }
        if (this.b__La_a_n_h() != null) {
            n2 = this.b__La_a_n_h().available();
        }
        if (n > 0 || n2 > 0) {
            return 1;
        }
        if (n == 0 || n2 == 0) {
            return 0;
        }
        return -1;
    }

    public byte[] a__ArrayB(int n) throws IOException {
        if (n < 0) {
            n = 0;
        }
        byte[] byArray = null;
        byte[] byArray2 = null;
        byArray = this.a__La_a_n_h() != null ? this.a__La_a_n_h().a__ArrayB(n) : new byte[]{};
        int n2 = n - byArray.length;
        if (n2 > 0) {
            if (this.b__La_a_n_h() == null) {
                throw new IOException(z[1]);
            }
            try {
                byArray2 = this.b__La_a_n_h().a__ArrayB(n2);
            }
            catch (IOException iOException) {
                throw new IOException(z[0] + iOException.getMessage());
            }
        } else {
            byArray2 = new byte[]{};
        }
        byte[] byArray3 = new byte[n];
        System.arraycopy(byArray, 0, byArray3, 0, byArray.length);
        System.arraycopy(byArray2, 0, byArray3, byArray.length, byArray2.length);
        this.c = byArray.length;
        return byArray3;
    }
}
