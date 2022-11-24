/* slides.jar — a/a/q/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.q;

import recovered.a.a.q.TypeA;

public abstract class TypeB {
    private static String[] z = new String[]{").", ") to satisfy the data request (", "Not enough bytes in the input array (", "No input array from which to pull data to stuff."};

    protected TypeB() {
    }

    public static byte[] a__ArrayB(byte[] byArray, int n, int n2) throws TypeA {
        if (byArray == null) {
            throw new TypeA(z[3]);
        }
        byte[] byArray2 = new byte[n2];
        try {
            System.arraycopy(byArray, n, byArray2, 0, n2);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new TypeA(z[2] + (byArray.length - n) + z[1] + n2 + z[0]);
        }
        return byArray2;
    }
}
