/* slides.jar — org/b/d/e/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.d.e;

import java.util.Hashtable;
import recovered.org.b.d.TypeG;
import recovered.org.b.d.TypeH;

// The archived concrete class inherits two abstract methods. Restore its class flags after javac.
public abstract class TypeA
extends TypeH {
    private static String[] a;
    private static Hashtable b;
    private static String z;

    public static byte[] c(byte[] byArray) {
        StringBuffer stringBuffer = new StringBuffer(byArray.length * 2);
        for (int i = 0; i < byArray.length; ++i) {
            int n = byArray[i];
            if (n < 0) {
                n += 256;
            }
            stringBuffer.append(a[n]);
        }
        return stringBuffer.toString().getBytes();
    }

    public static byte[] d(byte[] byArray) throws TypeG {
        byArray = TypeA.e(byArray);
        byte[] byArray2 = new byte[byArray.length / 2];
        int n = 0;
        for (int i = 0; i < byArray.length; i += 2) {
            StringBuffer stringBuffer = new StringBuffer(2);
            stringBuffer.append((char)byArray[i]);
            stringBuffer.append((char)byArray[i + 1]);
            Byte by = (Byte)b.get(stringBuffer.toString());
            if (by == null) {
                throw new TypeG(z + stringBuffer.toString() + "'");
            }
            byArray2[n] = by;
            ++n;
        }
        return byArray2;
    }

    protected static byte[] e(byte[] byArray) {
        byte[] byArray2 = null;
        if (byArray.length % 2 != 0) {
            byArray2 = recovered.org.b.g.TypeH.b("0".getBytes(), byArray);
        } else {
            byArray2 = new byte[byArray.length];
            System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
        }
        block8: for (int i = 0; i < byArray2.length; ++i) {
            switch ((char)byArray2[i]) {
                case 'a': {
                    byArray2[i] = 65;
                    continue block8;
                }
                case 'b': {
                    byArray2[i] = 66;
                    continue block8;
                }
                case 'c': {
                    byArray2[i] = 67;
                    continue block8;
                }
                case 'd': {
                    byArray2[i] = 68;
                    continue block8;
                }
                case 'e': {
                    byArray2[i] = 69;
                    continue block8;
                }
                case 'f': {
                    byArray2[i] = 70;
                }
            }
        }
        return byArray2;
    }

    protected static char[] a__ArrayC() {
        char[] cArray = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        return cArray;
    }

    private static void b__V() {
        char[] cArray = TypeA.a__ArrayC();
        int n = 0;
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                StringBuffer stringBuffer = new StringBuffer(2);
                stringBuffer.append(cArray[i]);
                stringBuffer.append(cArray[j]);
                TypeA.a[n] = stringBuffer.toString();
                ++n;
            }
        }
    }

    private static void c__V() {
        for (int i = 0; i < a.length; ++i) {
            byte by = 0;
            by = i > 127 ? (byte)(i - 256) : (byte)i;
            b.put(a[i], new Byte(by));
        }
    }

    static {
        z = "The given byte array value is not hex encoded: '";
        a = new String[256];
        b = new Hashtable();
        TypeA.b__V();
        TypeA.c__V();
    }
}
