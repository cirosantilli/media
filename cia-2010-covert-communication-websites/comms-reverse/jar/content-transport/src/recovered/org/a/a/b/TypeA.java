/* slides.jar — org/a/a/b/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.a.a.b;

import recovered.org.a.a.TypeC;
import recovered.org.a.a.TypeE;
import recovered.org.a.a.TypeH;

public class TypeA
implements TypeC {
    private int[] b = null;
    private static String[] z = new String[]{"IDEA engine not initialised", "input buffer too short", "output buffer too short", "invalid parameter passed to IDEA init - "};

    public void a(boolean bl, TypeE typeE) {
        if (typeE instanceof recovered.org.a.a.a.TypeA) {
            this.b = this.a(bl, ((recovered.org.a.a.a.TypeA)typeE).a__ArrayB());
            return;
        }
        throw new IllegalArgumentException(z[3] + typeE.getClass().getName());
    }

    public int b__I() {
        return 8;
    }

    public int a(byte[] byArray, int n, byte[] byArray2, int n2) {
        if (this.b == null) {
            throw new IllegalStateException(z[0]);
        }
        if (n + 8 > byArray.length) {
            throw new TypeH(z[1]);
        }
        if (n2 + 8 > byArray2.length) {
            throw new TypeH(z[2]);
        }
        this.a(this.b, byArray, n, byArray2, n2);
        return 8;
    }

    private int a(byte[] byArray, int n) {
        return (byArray[n] << 8 & 0xFF00) + (byArray[n + 1] & 0xFF);
    }

    private void a(int n, byte[] byArray, int n2) {
        byArray[n2] = (byte)(n >>> 8);
        byArray[n2 + 1] = (byte)n;
    }

    // org.a.a.b.a.a(II)I: IDEA multiplication modulo 65537, with zero encoding 65536.
    private int a(int left, int right) {
        if (left == 0) return (65537 - right) & 0xFFFF;
        if (right == 0) return (65537 - left) & 0xFFFF;
        int product = left * right;
        int low = product & 0xFFFF;
        int high = product >>> 16;
        return (low - high + (low < high ? 1 : 0)) & 0xFFFF;
    }

    private void a(int[] nArray, byte[] byArray, int n, byte[] byArray2, int n2) {
        int n3 = 0;
        int n4 = this.a(byArray, n);
        int n5 = this.a(byArray, n + 2);
        int n6 = this.a(byArray, n + 4);
        int n7 = this.a(byArray, n + 6);
        for (int i = 0; i < 8; ++i) {
            n4 = this.a(n4, nArray[n3++]);
            n5 += nArray[n3++];
            n6 += nArray[n3++];
            n7 = this.a(n7, nArray[n3++]);
            int n8 = n5 &= 0xFFFF;
            int n9 = n6 &= 0xFFFF;
            n6 ^= n4;
            n5 ^= n7;
            n6 = this.a(n6, nArray[n3++]);
            n5 += n6;
            n5 &= 0xFFFF;
            n5 = this.a(n5, nArray[n3++]);
            n6 += n5;
            n4 ^= n5;
            n7 ^= (n6 &= 0xFFFF);
            n5 ^= n9;
            n6 ^= n8;
        }
        this.a(this.a(n4, nArray[n3++]), byArray2, n2);
        this.a(n6 + nArray[n3++], byArray2, n2 + 2);
        this.a(n5 + nArray[n3++], byArray2, n2 + 4);
        this.a(this.a(n7, nArray[n3]), byArray2, n2 + 6);
    }

    private int[] a__ArrayI(byte[] byArray) {
        int n;
        int[] nArray = new int[52];
        if (byArray.length < 16) {
            byte[] byArray2 = new byte[16];
            System.arraycopy(byArray, 0, byArray2, byArray2.length - byArray.length, byArray.length);
            byArray = byArray2;
        }
        for (n = 0; n < 8; ++n) {
            nArray[n] = this.a(byArray, n * 2);
        }
        for (n = 8; n < 52; ++n) {
            nArray[n] = (n & 7) < 6 ? ((nArray[n - 7] & 0x7F) << 9 | nArray[n - 6] >> 7) & 0xFFFF : ((n & 7) == 6 ? ((nArray[n - 7] & 0x7F) << 9 | nArray[n - 14] >> 7) & 0xFFFF : ((nArray[n - 15] & 0x7F) << 9 | nArray[n - 14] >> 7) & 0xFFFF);
        }
        return nArray;
    }

    private int a__I(int n) {
        if (n < 2) {
            return n;
        }
        int n2 = 1;
        int n3 = 65537 / n;
        for (int i = 65537 % n; i != 1; i %= n) {
            int n4 = n / i;
            n2 = n2 + n3 * n4 & 0xFFFF;
            if ((n %= i) == 1) {
                return n2;
            }
            n4 = i / n;
            n3 = n3 + n2 * n4 & 0xFFFF;
        }
        return 1 - n3 & 0xFFFF;
    }

    int b__I(int n) {
        return 0 - n & 0xFFFF;
    }

    private int[] a(int[] nArray) {
        int n = 52;
        int[] nArray2 = new int[52];
        int n2 = 0;
        int n3 = this.a__I(nArray[n2++]);
        int n4 = this.b__I(nArray[n2++]);
        int n5 = this.b__I(nArray[n2++]);
        int n6 = this.a__I(nArray[n2++]);
        nArray2[--n] = n6;
        nArray2[--n] = n5;
        nArray2[--n] = n4;
        nArray2[--n] = n3;
        for (int i = 1; i < 8; ++i) {
            n3 = nArray[n2++];
            n4 = nArray[n2++];
            nArray2[--n] = n4;
            nArray2[--n] = n3;
            n3 = this.a__I(nArray[n2++]);
            n4 = this.b__I(nArray[n2++]);
            n5 = this.b__I(nArray[n2++]);
            n6 = this.a__I(nArray[n2++]);
            nArray2[--n] = n6;
            nArray2[--n] = n4;
            nArray2[--n] = n5;
            nArray2[--n] = n3;
        }
        n3 = nArray[n2++];
        n4 = nArray[n2++];
        nArray2[--n] = n4;
        nArray2[--n] = n3;
        n3 = this.a__I(nArray[n2++]);
        n4 = this.b__I(nArray[n2++]);
        n5 = this.b__I(nArray[n2++]);
        n6 = this.a__I(nArray[n2]);
        nArray2[--n] = n6;
        nArray2[--n] = n5;
        nArray2[--n] = n4;
        nArray2[--n] = n3;
        return nArray2;
    }

    private int[] a(boolean bl, byte[] byArray) {
        if (bl) {
            return this.a__ArrayI(byArray);
        }
        return this.a(this.a__ArrayI(byArray));
    }
}
