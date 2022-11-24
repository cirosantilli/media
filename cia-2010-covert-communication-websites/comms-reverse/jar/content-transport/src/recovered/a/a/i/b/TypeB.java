/* slides.jar — a/a/i/b/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.i.b;

import java.io.Serializable;
import recovered.a.a.i.b.TypeA;

public class TypeB
implements TypeA,
Serializable {
    private int g;
    private int h;
    private static String[] z = new String[]{"ScrollEffect {direction: ", "; speed: "};

    public TypeB() {
        this(4);
    }

    public TypeB(int n) {
        this(n, 0);
    }

    public TypeB(int n, int n2) {
        this.a__V(n);
        this.b__V(n2);
    }

    public int a__I() {
        return this.g;
    }

    public void a__V(int n) {
        this.g = n;
    }

    public int b__I() {
        return this.h;
    }

    public void b__V(int n) {
        this.h = n;
    }

    public boolean equals(Object object) {
        try {
            if (object == null) {
                return false;
            }
            TypeB typeB = (TypeB)object;
            return this.a__I() == typeB.a__I() && this.b__I() == typeB.b__I();
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public String toString() {
        return new String(z[0] + this.b__I() + z[1] + this.a__I() + "}");
    }
}
