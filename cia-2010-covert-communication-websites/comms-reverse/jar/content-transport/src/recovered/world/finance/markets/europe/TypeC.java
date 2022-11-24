/* slides.jar — world/finance/markets/europe/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.europe;

import recovered.a.a.i.a.TypeD;
import recovered.a.a.i.b.TypeA;
import recovered.a.a.q.TypeB;

public class TypeC
implements TypeD {
    private static final long a = 4000L;
    private long b;
    private int c = 0;
    private byte[] d = new byte[0];
    private static String[] z = new String[]{"The given array is not large enough to hold the contents of this object due to: ", "The given array is not large enough to fill this object: "};

    public TypeC() {
        this(4000L);
    }

    public TypeC(long l) {
        this.a__V(l);
    }

    public long a__J() {
        return this.b;
    }

    public void a__V(long l) {
        this.b = l >= 0L ? l : 4000L;
    }

    public boolean equals(Object object) {
        try {
            if (object == null) {
                return false;
            }
            TypeC typeC = (TypeC)object;
            return this.a__J() == typeC.a__J();
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public TypeA a__La_a_i_b_a() {
        return new recovered.a.a.i.b.TypeC(this.b);
    }

    public void a__V(int n) {
        this.c = n;
    }

    public int a__I() {
        return this.c;
    }

    public int a(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        int n2 = 0;
        try {
            this.d = TypeB.a__ArrayB(byArray, n + n2, this.c);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new recovered.a.a.b.TypeA(z[1] + typeA.toString());
        }
        return n2 += this.c;
    }

    public int a__I(byte[] byArray) throws recovered.a.a.b.TypeA {
        return this.a(byArray, 0);
    }

    public int b(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        int n2 = 0;
        try {
            System.arraycopy(this.d, 0, byArray, n + n2, this.d.length);
        }
        catch (Exception exception) {
            throw new recovered.a.a.b.TypeA(z[0] + exception.toString());
        }
        return n2 += this.d.length;
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
