/* slides.jar — world/finance/markets/middleeast/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.middleeast;

import recovered.a.a.l.TypeN;
import recovered.a.a.q.TypeB;

public class TypeA
implements TypeN {
    private String a;
    private String b;
    private int c;
    private int d = 0;
    private byte[] e = new byte[0];
    private static String[] z = new String[]{"The given array is not large enough to hold the contents of this object due to: ", "The given array is not large enough to fill this object: "};

    public TypeA(String string, String string2, int n) {
        this.a__V(string);
        this.b__V(string2);
        this.a__V(n);
    }

    public void a__V(String string) {
        this.a = string;
    }

    public String a__Ljava_lang_String() {
        return this.a;
    }

    public void b__V(String string) {
        this.b = string;
    }

    public String b__Ljava_lang_String() {
        return this.b;
    }

    public void a__V(int n) {
        this.c = n;
    }

    public int c__I() {
        return this.c;
    }

    public boolean equals(Object object) {
        try {
            TypeA typeA = (TypeA)object;
            return this.a__Ljava_lang_String().equals(typeA.a__Ljava_lang_String()) && this.b__Ljava_lang_String().equals(typeA.b__Ljava_lang_String()) && this.c__I() == typeA.c__I();
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public int a__I() {
        return this.d;
    }

    public void b__V(int n) {
        this.d = n;
    }

    public int a(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        int n2 = 0;
        try {
            this.e = TypeB.a__ArrayB(byArray, n + n2, this.d);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new recovered.a.a.b.TypeA(z[1] + typeA.toString());
        }
        return n2 += this.d;
    }

    public int a__I(byte[] byArray) throws recovered.a.a.b.TypeA {
        return this.a(byArray, 0);
    }

    public int b(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        int n2 = 0;
        try {
            System.arraycopy(this.e, 0, byArray, n + n2, this.e.length);
        }
        catch (Exception exception) {
            throw new recovered.a.a.b.TypeA(z[0] + exception.toString());
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
}
