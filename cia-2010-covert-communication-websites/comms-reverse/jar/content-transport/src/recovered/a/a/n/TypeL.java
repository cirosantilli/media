/* slides.jar — a/a/n/l.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.n;

import java.util.Random;
import recovered.a.a.n.TypeH;

public class TypeL
implements TypeH {
    private static Random b = new Random();
    private static Random c;
    private int d = 1;

    public TypeL() {
        this(null);
    }

    public TypeL(Random random) {
        this.a(random);
    }

    public int available() {
        return this.d;
    }

    public byte[] a__ArrayB(int n) {
        byte[] byArray = new byte[n];
        c.nextBytes(byArray);
        return byArray;
    }

    public void a(Random random) {
        c = random == null ? new Random() : random;
    }
}
