/* slides.jar — world/finance/markets/europe/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.europe;

import recovered.a.a.i.b.TypeA;

public class TypeB
implements recovered.a.a.i.a.TypeB {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    private static final int e = 4;
    private static final int f = 0;
    private int g;
    private int h;
    private int i = 0;
    private byte[] j = new byte[0];
    private static String[] z = new String[]{"The given array is not large enough to hold the contents of this object due to: ", "The given array is not large enough to fill this object: "};

    public TypeB() {
        this(4);
    }

    public TypeB(int n) {
        this(n, 0);
    }

    public TypeB(int n, int n2) {
        this.a__V(n);
        this.b__V(n2);
    }

    public int b__I() {
        return this.g;
    }

    public void a__V(int n) {
        this.g = n;
    }

    public int c__I() {
        return this.h;
    }

    public void b__V(int n) {
        this.h = n;
    }

    public boolean equals(Object object) {
        try {
            if (object == null) {
                return false;
            }
            TypeB typeB = (TypeB)object;
            return this.b__I() == typeB.b__I() && this.c__I() == typeB.c__I();
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public TypeA a__La_a_i_b_a() {
        return new recovered.a.a.i.b.TypeB(this.g, this.h);
    }

    public void c(int n) {
        this.i = n;
    }

    public int a__I() {
        return this.i;
    }

    public int a(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        int n2 = 0;
        try {
            this.j = recovered.a.a.q.TypeB.a__ArrayB(byArray, n + n2, this.i);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new recovered.a.a.b.TypeA(z[1] + typeA.toString());
        }
        return n2 += this.i;
    }

    public int a__I(byte[] byArray) throws recovered.a.a.b.TypeA {
        return this.a(byArray, 0);
    }

    public int b(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        int n2 = 0;
        try {
            System.arraycopy(this.j, 0, byArray, n + n2, this.j.length);
        }
        catch (Exception exception) {
            throw new recovered.a.a.b.TypeA(z[0] + exception.toString());
        }
        return n2 += this.j.length;
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
            return super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }
}
