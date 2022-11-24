/* aeronet.jar — b/b/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.b.b;

import java.io.ByteArrayOutputStream;
import recovered.b.b.TypeA;
import recovered.b.b.TypeB;

public class TypeC
extends TypeB {
    private static char[] a;
    private static byte[] b;
    private static String z;

    public static byte[] a__ArrayB(byte[] byArray) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byArray2 = new byte[3];
        byte[] byArray3 = new byte[4];
        int n = 0;
        int n2 = 0;
        while (n2 < byArray.length) {
            int n3;
            n = 0;
            byArray2[0] = byArray[n2];
            if (++n2 < byArray.length) {
                byArray2[1] = byArray[n2];
                if (++n2 < byArray.length) {
                    byArray2[2] = byArray[n2];
                    ++n2;
                } else {
                    n = 1;
                    byArray2[2] = 0;
                    ++n2;
                }
            } else {
                n = 2;
                byArray2[1] = 0;
                byArray2[2] = 0;
                n2 += 2;
            }
            byArray3[0] = (byte)(byArray2[0] >> 2 & 0x3F);
            byArray3[1] = (byte)(byArray2[0] << 4 & 0x30 | byArray2[1] >> 4 & 0xF);
            byArray3[2] = (byte)(byArray2[1] << 2 & 0x3C | byArray2[2] >> 6 & 3);
            byArray3[3] = (byte)(byArray2[2] & 0x3F);
            for (n3 = 0; n3 < 4 - n; ++n3) {
                byteArrayOutputStream.write(a[byArray3[n3]]);
            }
            for (n3 = 0; n3 < n; ++n3) {
                byteArrayOutputStream.write(61);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] b__ArrayB(byte[] byArray) throws TypeA {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] byArray2 = new byte[3];
            byte[] byArray3 = new byte[4];
            int n = 0;
            int n2 = 0;
            while (n2 < byArray.length) {
                n = 0;
                byArray3[0] = b[byArray[n2]];
                byArray3[1] = b[byArray[++n2]];
                byArray3[2] = b[byArray[++n2]];
                byArray3[3] = b[byArray[++n2]];
                ++n2;
                if (byArray3[0] == -1 || byArray3[1] == -1 || byArray3[2] == -1 || byArray3[3] == -1) {
                    throw new TypeA(z);
                }
                if (byArray3[2] == 65) {
                    n = 2;
                } else if (byArray3[3] == 65) {
                    n = 1;
                }
                byArray2[0] = (byte)(byArray3[0] << 2 & 0xFC | byArray3[1] >> 4 & 3);
                byArray2[1] = (byte)(byArray3[1] << 4 & 0xF0 | byArray3[2] >> 2 & 0xF);
                byArray2[2] = (byte)(byArray3[2] << 6 & 0xC0 | byArray3[3] & 0x3F);
                byteArrayOutputStream.write(byArray2[0]);
                if (n < 2) {
                    byteArrayOutputStream.write(byArray2[1]);
                }
                if (n >= 1) continue;
                byteArrayOutputStream.write(byArray2[2]);
            }
            return byteArrayOutputStream.toByteArray();
        }
        catch (Exception exception) {
            throw new TypeA(exception);
        }
    }

    protected static char[] a__ArrayC() {
        char[] cArray = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/', '\u0000', '='};
        return cArray;
    }

    private static void b__V() {
        a = TypeC.a__ArrayC();
    }

    private static void c__V() {
        int n;
        for (n = 0; n < b.length; ++n) {
            TypeC.b[n] = -1;
        }
        for (n = 0; n < a.length; ++n) {
            char c = a[n];
            TypeC.b[c] = (byte)n;
        }
    }

    static {
        z = "The given byte array is not base64 encoded.";
        b = new byte[128];
        TypeC.b__V();
        TypeC.c__V();
    }
}
