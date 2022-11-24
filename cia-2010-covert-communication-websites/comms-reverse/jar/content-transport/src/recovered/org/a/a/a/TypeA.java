/* slides.jar — org/a/a/a/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.a.a.a;

import recovered.org.a.a.TypeE;

public class TypeA
implements TypeE {
    private byte[] a;

    public TypeA(byte[] byArray) {
        this(byArray, 0, byArray.length);
    }

    public TypeA(byte[] byArray, int n, int n2) {
        this.a = new byte[n2];
        System.arraycopy(byArray, n, this.a, 0, n2);
    }

    public byte[] a__ArrayB() {
        return this.a;
    }
}
