/* slides.jar — org/b/d/d/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.d.d;

import recovered.org.b.d.TypeG;
import recovered.org.b.d.TypeH;
import recovered.org.b.d.d.TypeA;

public class TypeB
extends TypeH {
    private byte[] a;
    private static String z = "The given bytes do not contain an iv value";

    public TypeB(byte[] byArray) {
        this.a__V(byArray);
    }

    public void a__V(byte[] byArray) {
        this.a = byArray;
    }

    public byte[] a__ArrayB(byte[] byArray) throws TypeG {
        return TypeB.a__ArrayB(byArray, this.a);
    }

    public byte[] b__ArrayB(byte[] byArray) throws TypeG {
        return TypeB.b(byArray, this.a);
    }

    public static byte[] a__ArrayB(byte[] byArray, byte[] byArray2) throws TypeG {
        byte[] byArray3 = TypeA.d__ArrayB();
        byte[] byArray4 = new byte[byArray3.length + byArray.length];
        System.arraycopy(byArray3, 0, byArray4, 0, byArray3.length);
        System.arraycopy(byArray, 0, byArray4, byArray3.length, byArray.length);
        return TypeA.a(byArray4, byArray2, byArray3);
    }

    public static byte[] b(byte[] byArray, byte[] byArray2) throws TypeG {
        if (byArray.length < 8) {
            throw new TypeG(z);
        }
        byte[] byArray3 = TypeA.d__ArrayB();
        byte[] byArray4 = TypeA.b(byArray, byArray2, byArray3);
        byte[] byArray5 = new byte[byArray4.length - byArray3.length];
        System.arraycopy(byArray4, byArray3.length, byArray5, 0, byArray5.length);
        return byArray5;
    }
}
