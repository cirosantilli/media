/* slides.jar — world/finance/markets/americas/north/f.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.americas.north;

import recovered.a.a.b.TypeA;
import recovered.a.a.e.b.a.TypeH;
import recovered.a.a.e.b.b.TypeC;
import recovered.a.a.e.b.b.TypeD;
import recovered.a.a.k.TypeB;
import recovered.org.b.b.TypeG;

public class TypeF
implements TypeH {
    private String a = null;
    private int b = 0;
    private byte[] c = new byte[0];
    private static String[] z = new String[]{"Null data array.", "The given array is not large enough to hold the contents of this object due to: ", " is not in the range 0 - ", "Array index out of bounds: ", "ConfigurableURLImageData is not cloneable.", "The given array is not large enough to fill this object: "};

    public TypeF() {
        this(null);
    }

    public TypeF(String string) {
        this.a = string;
    }

    public String a__Ljava_lang_String() {
        return this.a;
    }

    public void a__V(String string) {
        this.a = string;
    }

    public TypeD a__La_a_e_b_b_d() throws recovered.a.a.e.b.TypeC {
        if (this.a__Ljava_lang_String() != null) {
            return new TypeC(this.a__Ljava_lang_String());
        }
        return new TypeC();
    }

    public void a__V(int n) {
        this.b = n;
    }

    public int a__I() {
        return this.b;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            TypeF typeF = (TypeF)object;
            return this.a__Ljava_lang_String().equals(typeF.a__Ljava_lang_String());
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public int a(byte[] byArray, int n) throws TypeA {
        if (byArray == null) {
            throw new TypeA(z[0]);
        }
        if (n >= byArray.length || n < 0) {
            throw new TypeA(z[3] + n + z[2] + byArray.length);
        }
        int n2 = 0;
        try {
            this.c = recovered.a.a.q.TypeB.a__ArrayB(byArray, n + n2, this.b);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new TypeA(z[5] + typeA.toString());
        }
        return n2 += this.b;
    }

    public int a__I(byte[] byArray) throws TypeA {
        return this.a(byArray, 0);
    }

    public int b(byte[] byArray, int n) throws TypeA {
        if (byArray == null) {
            throw new TypeA(z[0]);
        }
        if (n >= byArray.length || n < 0) {
            throw new TypeA(z[3] + n + z[2] + byArray.length);
        }
        int n2 = 0;
        try {
            System.arraycopy(this.c, 0, byArray, n + n2, this.c.length);
        }
        catch (Exception exception) {
            throw new TypeA(z[1] + exception.toString());
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
            TypeF typeF = (TypeF)super.clone();
            if (this.a__Ljava_lang_String() != null) {
                typeF.a__V(new String(this.a__Ljava_lang_String()));
            }
            return typeF;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            TypeG typeG = TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
            typeG.e(z[4]);
            return null;
        }
    }
}
