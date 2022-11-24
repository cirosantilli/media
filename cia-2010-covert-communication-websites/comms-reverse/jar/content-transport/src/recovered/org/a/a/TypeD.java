/* slides.jar — org/a/a/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.a.a;

import java.util.Random;
import recovered.org.a.a.TypeF;

public class TypeD {
    protected Random a;
    protected int b;

    public void a(TypeF typeF) {
        this.a = typeF.a__Ljava_util_Random();
        this.b = (typeF.b__I() + 7) / 8;
    }

    public byte[] a__ArrayB() {
        byte[] byArray = new byte[this.b];
        this.a.nextBytes(byArray);
        return byArray;
    }
}
