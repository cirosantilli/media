/* slides.jar — org/b/d/d/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.d.d;

import java.util.Random;
import recovered.org.a.a.TypeD;
import recovered.org.a.a.TypeF;
import recovered.org.b.d.TypeG;
import recovered.org.b.d.TypeH;
import recovered.org.b.g.a.TypeB;

public class TypeA
extends TypeH {
    private byte[] c;
    private byte[] d;
    private static String z = "Unable to encod data due to: ";

    public TypeA() {
        this(null, null);
    }

    public TypeA(byte[] byArray, byte[] byArray2) {
        this.a__V(byArray);
        this.b__V(byArray2);
    }

    public void a__V(byte[] byArray) {
        this.c = byArray == null ? TypeA.c__ArrayB() : byArray;
    }

    public void b__V(byte[] byArray) {
        if (byArray == null) {
            this.d = TypeA.d__ArrayB();
        } else if (byArray.length == 8) {
            this.d = byArray;
        } else if (byArray.length > 8) {
            this.d = new byte[8];
            System.arraycopy(byArray, 0, this.d, 0, 8);
        } else {
            byte[] byArray2 = TypeA.d__ArrayB();
            System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
        }
    }

    public byte[] a__ArrayB(byte[] byArray) throws TypeG {
        try {
            int n;
            recovered.org.a.a.b.TypeA typeA = new recovered.org.a.a.b.TypeA();
            recovered.org.a.a.a.TypeA typeA2 = new recovered.org.a.a.a.TypeA(this.c);
            typeA.a(true, typeA2);
            int n2 = typeA.b__I();
            int n3 = (byArray.length / n2 + 1) * n2;
            int n4 = n3 - byArray.length;
            byte[] byArray2 = new byte[n3];
            System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
            for (n = byArray.length; n < n3; ++n) {
                byArray2[n] = 0;
            }
            byArray2[byArray2.length - 1] = (byte)n4;
            n = 0;
            int n5 = 0;
            while (n5 < n3 / n2) {
                int n6;
                if (n5 == 0) {
                    for (n6 = 0; n6 < n2; ++n6) {
                        int n7 = n6;
                        byArray2[n7] = (byte)(byArray2[n7] ^ this.d[n6]);
                    }
                } else {
                    for (n6 = 0; n6 < n2; ++n6) {
                        int n8 = n5 * n2 + n6;
                        byArray2[n8] = (byte)(byArray2[n8] ^ byArray2[(n5 - 1) * n2 + n6]);
                    }
                }
                TypeA.a__V(byArray2, n5 * n2, n2);
                typeA.a(byArray2, n, byArray2, n);
                TypeA.a__V(byArray2, n5 * n2, n2);
                ++n5;
                n += n2;
            }
            return byArray2;
        }
        catch (Exception exception) {
            throw new TypeG(z + exception.toString(), exception);
        }
    }

    public byte[] b__ArrayB(byte[] byArray) throws TypeG {
        try {
            int n = 0;
            int n2 = 0;
            byte[] byArray2 = null;
            recovered.org.a.a.b.TypeA typeA = new recovered.org.a.a.b.TypeA();
            recovered.org.a.a.a.TypeA typeA2 = new recovered.org.a.a.a.TypeA(this.c);
            typeA.a(false, typeA2);
            int n3 = byArray.length;
            int n4 = typeA.b__I();
            int n5 = 0;
            byte[] byArray3 = new byte[n3];
            System.arraycopy(byArray, 0, byArray3, 0, byArray.length);
            n = 0;
            while (n < n3 / n4) {
                TypeA.a__V(byArray3, n * n4, n4);
                typeA.a(byArray3, n5, byArray3, n5);
                TypeA.a__V(byArray3, n * n4, n4);
                if (n == 0) {
                    for (n2 = 0; n2 < n4; ++n2) {
                        int n6 = n2;
                        byArray3[n6] = (byte)(byArray3[n6] ^ this.d[n2]);
                    }
                } else {
                    for (n2 = 0; n2 < n4; ++n2) {
                        int n7 = n * n4 + n2;
                        byArray3[n7] = (byte)(byArray3[n7] ^ byArray[(n - 1) * n4 + n2]);
                    }
                }
                ++n;
                n5 += n4;
            }
            int n8 = byArray3[byArray3.length - 1] & 0xFF;
            byArray2 = new byte[n3 - n8];
            System.arraycopy(byArray3, 0, byArray2, 0, byArray2.length);
            return byArray2;
        }
        catch (Exception exception) {
            throw new TypeG(z + exception.toString(), exception);
        }
    }

    public static byte[] a(byte[] byArray, byte[] byArray2, byte[] byArray3) throws TypeG {
        TypeA typeA = new TypeA(byArray2, byArray3);
        return typeA.a__ArrayB(byArray);
    }

    public static byte[] b(byte[] byArray, byte[] byArray2, byte[] byArray3) throws TypeG {
        TypeA typeA = new TypeA(byArray2, byArray3);
        return typeA.b__ArrayB(byArray);
    }

    private static void a__V(byte[] byArray, int n, int n2) {
        if (byArray.length % 2 == 0 && n2 % 2 == 0) {
            for (int i = n; i < n + n2; i += 2) {
                byte by = byArray[i + 0];
                byArray[i + 0] = byArray[i + 1];
                byArray[i + 1] = by;
            }
        }
    }

    public static byte[] c__ArrayB() {
        TypeD typeD = new TypeD();
        typeD.a(new TypeF(TypeB.a__Lorg_b_g_a_a().d__Ljava_util_Random(), 128));
        return typeD.a__ArrayB();
    }

    public static byte[] d__ArrayB() {
        Random random = TypeB.a__Lorg_b_g_a_a().d__Ljava_util_Random();
        byte[] byArray = new byte[8];
        random.nextBytes(byArray);
        return byArray;
    }
}
