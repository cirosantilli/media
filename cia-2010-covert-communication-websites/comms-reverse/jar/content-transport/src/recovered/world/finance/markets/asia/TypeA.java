/* slides.jar — world/finance/markets/asia/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.asia;

import java.io.Serializable;
import recovered.a.a.e.TypeD;
import recovered.a.a.e.a.TypeE;
import recovered.a.a.i.a.TypeC;
import recovered.a.a.k.TypeB;
import recovered.org.b.b.TypeG;

public class TypeA
implements recovered.a.a.d.c.TypeB,
Serializable {
    private TypeE a;
    private TypeC b;
    private String c;
    private int d = 0;
    private byte[] e = new byte[0];
    private static String[] z = new String[]{"The given array is not large enough to fill this object: ", "The given array is not large enough to hold the contents of this object due to: ", "ConfigurableDisplaySet is not cloneable."};

    public TypeA() {
        this.a((TypeE)null);
        this.a((TypeC)null);
        this.a__V(null);
    }

    public TypeA(TypeE typeE, TypeC typeC, String string) {
        this.a(typeE);
        this.a(typeC);
        this.a__V(string);
    }

    public TypeE a__La_a_e_a_e() {
        return this.a;
    }

    public void a(TypeE typeE) {
        this.a = typeE;
    }

    public TypeC b__La_a_i_a_c() {
        return this.b;
    }

    public void a(TypeC typeC) {
        this.b = typeC;
    }

    public String c__Ljava_lang_String() {
        return this.c;
    }

    public void a__V(String string) {
        this.c = string;
    }

    public void a__V(int n) {
        this.d = n;
    }

    public int a__I() {
        int n = 0;
        if (this.b__La_a_i_a_c() != null) {
            n += this.b__La_a_i_a_c().a__I();
        }
        if (this.a__La_a_e_a_e() != null) {
            try {
                n += this.a__La_a_e_a_e().a__I();
            }
            catch (ClassCastException classCastException) {
            }
        }
        return n += this.d;
    }

    public recovered.a.a.d.a.TypeD d__La_a_d_a_d() throws TypeD {
        return new recovered.a.a.d.a.TypeB(this.a__La_a_e_a_e().a__La_a_e_c_h(), this.b__La_a_i_a_c().a__La_a_i_b_a(), this.c__Ljava_lang_String());
    }

    public int a(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        int n2 = 0;
        if (this.b__La_a_i_a_c() != null) {
            n2 += this.b__La_a_i_a_c().a(byArray, n + n2);
        }
        if (this.a__La_a_e_a_e() != null) {
            try {
                n2 += this.a__La_a_e_a_e().a(byArray, n + n2);
            }
            catch (ClassCastException classCastException) {
            }
        }
        try {
            this.e = recovered.a.a.q.TypeB.a__ArrayB(byArray, n + n2, this.d);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new recovered.a.a.b.TypeA(z[0] + typeA.toString());
        }
        return n2 += this.d;
    }

    public int a__I(byte[] byArray) throws recovered.a.a.b.TypeA {
        return this.a(byArray, 0);
    }

    public int b(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        int n2 = 0;
        if (this.b__La_a_i_a_c() != null) {
            n2 += this.b__La_a_i_a_c().b(byArray, n + n2);
        }
        if (this.a__La_a_e_a_e() != null) {
            try {
                n2 += this.a__La_a_e_a_e().b(byArray, n + n2);
            }
            catch (ClassCastException classCastException) {
            }
        }
        try {
            System.arraycopy(this.e, 0, byArray, n + n2, this.e.length);
        }
        catch (Exception exception) {
            throw new recovered.a.a.b.TypeA(z[1] + exception.toString());
        }
        return n2 += this.e.length;
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
            TypeA typeA = (TypeA)super.clone();
            if (this.c__Ljava_lang_String() != null) {
                typeA.a__V(new String(this.c__Ljava_lang_String()));
            }
            if (this.a__La_a_e_a_e() != null) {
                typeA.a((TypeE)this.a__La_a_e_a_e().clone());
            }
            if (this.b__La_a_i_a_c() != null) {
                typeA.a((TypeC)this.b__La_a_i_a_c().clone());
            }
            return typeA;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            TypeG typeG = TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
            typeG.e(z[2]);
            return null;
        }
    }
}
