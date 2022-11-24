/* slides.jar — world/finance/markets/americas/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.americas;

import recovered.a.a.e.TypeD;
import recovered.a.a.e.b.a.TypeE;
import recovered.a.a.e.b.b.TypeC;
import recovered.a.a.e.c.TypeA;
import recovered.a.a.e.c.TypeH;
import recovered.org.b.b.TypeG;

public class TypeB
implements recovered.a.a.e.a.TypeB {
    private TypeE a = null;
    private int b = 0;
    private byte[] c = new byte[0];
    private static String[] z = new String[]{"The given array is not large enough to hold the contents of this object due to: ", "Unable to create ImageData.", "Wrong type generated for ImageData.", "ConfigurableImageContent is not cloneable.", "The given array is not large enough to fill this object: "};

    public TypeB() {
        this(null);
    }

    public TypeB(TypeE typeE) {
        this.a(typeE);
    }

    public void a(TypeE typeE) {
        this.a = typeE;
    }

    public TypeE a__La_a_e_b_a_e() {
        return this.a;
    }

    public TypeH a__La_a_e_c_h() throws TypeD {
        TypeA typeA = null;
        if (this.a__La_a_e_b_a_e() == null) {
            typeA = new TypeA();
        } else {
            try {
                typeA = new TypeA((TypeC)this.a__La_a_e_b_a_e().a__La_a_e_b_b_d());
            }
            catch (recovered.a.a.e.b.TypeC typeC) {
                throw new TypeD(z[1], typeC);
            }
            catch (ClassCastException classCastException) {
                throw new TypeD(z[2], classCastException);
            }
        }
        return typeA;
    }

    public void a__V(int n) {
        this.b = n;
    }

    public int a__I() {
        int n = this.b;
        if (this.a__La_a_e_b_a_e() != null) {
            n += this.a__La_a_e_b_a_e().a__I();
        }
        return n;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            TypeB typeB = (TypeB)object;
            return this.a__La_a_e_b_a_e() == null && typeB.a__La_a_e_b_a_e() == null || this.a__La_a_e_b_a_e().equals(typeB.a__La_a_e_b_a_e());
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public int a(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        int n2 = 0;
        if (this.a__La_a_e_b_a_e() != null) {
            n2 += this.a__La_a_e_b_a_e().a(byArray, n + n2);
        }
        try {
            this.c = recovered.a.a.q.TypeB.a__ArrayB(byArray, n + n2, this.b);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new recovered.a.a.b.TypeA(z[4] + typeA.toString());
        }
        return n2 += this.b;
    }

    public int a__I(byte[] byArray) throws recovered.a.a.b.TypeA {
        return this.a(byArray, 0);
    }

    public int b(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        int n2 = 0;
        if (this.a__La_a_e_b_a_e() != null) {
            n2 += this.a__La_a_e_b_a_e().b(byArray, n + n2);
        }
        try {
            System.arraycopy(this.c, 0, byArray, n + n2, this.c.length);
        }
        catch (Exception exception) {
            throw new recovered.a.a.b.TypeA(z[0] + exception.toString());
        }
        return n2 += this.c.length;
    }

    public int b__I(byte[] byArray) throws recovered.a.a.b.TypeA {
        return this.b(byArray, 0);
    }

    public byte[] a__ArrayB() throws recovered.a.a.b.TypeA {
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
            TypeB typeB = (TypeB)super.clone();
            if (this.a__La_a_e_b_a_e() != null) {
                typeB.a((TypeE)this.a__La_a_e_b_a_e().clone());
            }
            return typeB;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            TypeG typeG = recovered.a.a.k.TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
            typeG.e(z[3]);
            return null;
        }
    }
}
