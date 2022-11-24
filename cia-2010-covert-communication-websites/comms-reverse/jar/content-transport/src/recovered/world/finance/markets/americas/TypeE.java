/* slides.jar — world/finance/markets/americas/e.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.americas;

import recovered.a.a.b.TypeA;
import recovered.a.a.e.TypeD;
import recovered.a.a.e.a.TypeH;
import recovered.a.a.e.b.TypeC;
import recovered.a.a.k.TypeB;
import recovered.org.b.b.TypeG;

public class TypeE
implements TypeH {
    private recovered.a.a.e.b.a.TypeG a = null;
    private int b = 0;
    private byte[] c = new byte[0];
    private static String[] z = new String[]{"The given array is not large enough to fill this object: ", "Wrong type generated for TextData.", "Unable to create TextData.", "The given array is not large enough to hold the contents of this object due to: ", "ConfigurableTextContent is not cloneable.", "System is set up incorrectly."};

    public TypeE() {
        this(null);
    }

    public TypeE(recovered.a.a.e.b.a.TypeG typeG) {
        this.a(typeG);
    }

    public void a(recovered.a.a.e.b.a.TypeG typeG) {
        this.a = typeG;
        if (this.a == null) {
            try {
                this.a = new TypeB().b__La_a_e_b_a_c().a__La_a_e_b_a_g();
            }
            catch (recovered.a.a.k.TypeA typeA) {
                new TypeB().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName()).d(z[5], typeA);
            }
        }
    }

    public recovered.a.a.e.b.a.TypeG a__La_a_e_b_a_g() {
        return this.a;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            TypeE typeE = (TypeE)object;
            return this.a__La_a_e_b_a_g().equals(typeE.a__La_a_e_b_a_g());
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public recovered.a.a.e.c.TypeH a__La_a_e_c_h() throws TypeD {
        try {
            return new recovered.a.a.e.c.TypeD((recovered.a.a.e.b.b.TypeE)this.a__La_a_e_b_a_g().a__La_a_e_b_b_d());
        }
        catch (TypeC typeC) {
            throw new TypeD(z[2], typeC);
        }
        catch (ClassCastException classCastException) {
            throw new TypeD(z[1], classCastException);
        }
    }

    public void a__V(int n) {
        this.b = n;
    }

    public int a__I() {
        int n = this.b;
        if (this.a__La_a_e_b_a_g() != null) {
            n += this.a__La_a_e_b_a_g().a__I();
        }
        return n;
    }

    public int a(byte[] byArray, int n) throws TypeA {
        int n2 = 0;
        if (this.a__La_a_e_b_a_g() != null) {
            n2 += this.a__La_a_e_b_a_g().a(byArray, n + n2);
        }
        try {
            this.c = recovered.a.a.q.TypeB.a__ArrayB(byArray, n + n2, this.b);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new TypeA(z[0] + typeA.toString());
        }
        return n2 += this.b;
    }

    public int a__I(byte[] byArray) throws TypeA {
        return this.a(byArray, 0);
    }

    public int b(byte[] byArray, int n) throws TypeA {
        int n2 = 0;
        if (this.a__La_a_e_b_a_g() != null) {
            n2 += this.a__La_a_e_b_a_g().b(byArray, n + n2);
        }
        try {
            System.arraycopy(this.c, 0, byArray, n + n2, this.c.length);
        }
        catch (Exception exception) {
            throw new TypeA(z[3] + exception.toString());
        }
        return n2 += this.c.length;
    }

    public int b__I(byte[] byArray) throws TypeA {
        return this.b(byArray, 0);
    }

    public byte[] a__ArrayB() throws TypeA {
        int n = this.a__I();
        if (n == 0) {
            return new byte[0];
        }
        byte[] byArray = new byte[n];
        int n2 = this.b(byArray, 0);
        if (n2 < byArray.length) {
            byte[] byArray2 = new byte[n2];
            System.arraycopy(byArray, 0, byArray2, 0, n2);
            byArray = byArray2;
        }
        return byArray;
    }

    public Object clone() {
        try {
            TypeE typeE = (TypeE)super.clone();
            if (this.a__La_a_e_b_a_g() != null) {
                typeE.a((recovered.a.a.e.b.a.TypeG)this.a__La_a_e_b_a_g().clone());
            }
            return typeE;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            TypeG typeG = TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
            typeG.e(z[4]);
            return null;
        }
    }
}
