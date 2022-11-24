/* aeronet.jar — a/a/a/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.a;

import java.util.Random;
import recovered.b.a.TypeC;

public class TypeA {
    public static byte[] a__ArrayB(byte[] byArray) {
        long l = TypeA.b__J(byArray);
        byte[] byArray2 = TypeA.a(l, byArray);
        return byArray2;
    }

    private static byte[] a(long l, byte[] byArray) {
        byte[] byArray2 = TypeC.a(byArray, 8, byArray.length - 8);
        Random random = new Random(l);
        byte[] byArray3 = new byte[byArray2.length];
        random.nextBytes(byArray3);
        byte[] byArray4 = new byte[byArray2.length];
        for (int i = 0; i < byArray2.length; ++i) {
            byArray4[i] = (byte)(byArray3[i] ^ byArray2[i]);
        }
        return byArray4;
    }

    private static long b__J(byte[] byArray) {
        byte[] byArray2 = TypeC.a(byArray, 0, 8);
        long l = TypeC.a__J(byArray2);
        return l;
    }
}
