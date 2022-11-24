/* slides.jar — org/b/e/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.e;

import recovered.org.b.d.TypeG;
import recovered.org.b.d.TypeH;
import recovered.org.b.d.TypeI;
import recovered.org.b.d.d.TypeB;

public class TypeA
extends TypeH {
    private TypeH a;

    public TypeA(byte[] byArray) {
        if (byArray == null || byArray.length == 0) {
            this.a = new recovered.org.b.d.c.TypeA();
        } else {
            TypeB typeB = new TypeB(byArray);
            recovered.org.b.d.a.TypeA typeA = new recovered.org.b.d.a.TypeA();
            this.a = new TypeI(new TypeH[]{typeA, typeB});
        }
    }

    public byte[] a__ArrayB(byte[] byArray) throws TypeG {
        return this.a.a__ArrayB(byArray);
    }

    public byte[] b__ArrayB(byte[] byArray) throws TypeG {
        return this.a.b__ArrayB(byArray);
    }
}
