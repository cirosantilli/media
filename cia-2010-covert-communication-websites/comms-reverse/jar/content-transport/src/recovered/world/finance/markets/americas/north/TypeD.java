/* slides.jar — world/finance/markets/americas/north/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.americas.north;

import java.awt.Font;
import recovered.a.a.b.TypeA;
import recovered.a.a.e.b.TypeC;
import recovered.a.a.e.b.b.TypeB;
import recovered.org.b.b.TypeG;

public class TypeD
implements recovered.a.a.e.b.a.TypeD {
    private String a = null;
    private int b = -1;
    private int c = -1;
    private int d = 0;
    private byte[] e = new byte[0];
    private static String[] z = new String[]{" is not in the range 0 - ", "Array index out of bounds: ", "Null data array.", "The given array is not large enough to hold the contents of this object due to: ", "Cannot clone ConfigurableFontDefinition: ", "The given array is not large enough to fill this object: "};

    public TypeD() {
    }

    public TypeD(String string, int n, int n2) {
        this.a__V(string);
        this.a__V(n);
        this.b__V(n2);
    }

    public void a__V(String string) {
        this.a = string;
    }

    public void a__V(int n) {
        this.b = n;
    }

    public void b__V(int n) {
        this.c = n;
    }

    public Font a__Ljava_awt_Font() {
        return new Font(this.b__Ljava_lang_String(), this.c__I(), this.d__I());
    }

    public String b__Ljava_lang_String() {
        return this.a;
    }

    public int c__I() {
        return this.b;
    }

    public int d__I() {
        return this.c;
    }

    public Object clone() {
        try {
            recovered.a.a.e.b.a.TypeD typeD = (recovered.a.a.e.b.a.TypeD)super.clone();
            if (this.b__Ljava_lang_String() != null) {
                typeD.a__V(new String(this.b__Ljava_lang_String()));
            }
            return typeD;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            TypeG typeG = recovered.a.a.k.TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
            typeG.e(z[4], cloneNotSupportedException);
            return null;
        }
    }

    public void c(int n) {
        this.d = n;
    }

    public int a__I() {
        return this.d;
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

    public int b__I(byte[] byArray) throws TypeA {
        return this.b(byArray, 0);
    }

    public int b(byte[] byArray, int n) throws TypeA {
        if (byArray == null) {
            throw new TypeA(z[2]);
        }
        if (n >= byArray.length || n < 0) {
            throw new TypeA(z[1] + n + z[0] + byArray.length);
        }
        int n2 = 0;
        try {
            System.arraycopy(this.e, 0, byArray, n + n2, this.e.length);
        }
        catch (Exception exception) {
            throw new TypeA(z[3] + exception.toString());
        }
        return n2 += this.e.length;
    }

    public int a__I(byte[] byArray) throws TypeA {
        return this.a(byArray, 0);
    }

    public int a(byte[] byArray, int n) throws TypeA {
        if (byArray == null) {
            throw new TypeA(z[2]);
        }
        if (n >= byArray.length || n < 0) {
            throw new TypeA(z[1] + n + z[0] + byArray.length);
        }
        int n2 = 0;
        try {
            this.e = recovered.a.a.q.TypeB.a__ArrayB(byArray, n + n2, this.d);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new TypeA(z[5] + typeA.toString());
        }
        return n2 += this.d;
    }

    public recovered.a.a.e.b.b.TypeD a__La_a_e_b_b_d() throws TypeC {
        return new TypeB(this.a__Ljava_awt_Font());
    }
}
