/* slides.jar — a/a/i/b/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.i.b;

import java.io.Serializable;
import recovered.a.a.i.b.TypeA;

public class TypeC
implements TypeA,
Serializable {
    private long b;
    private static String z = "SlideShowEffect {duration: ";

    public TypeC() {
        this(4000L);
    }

    public TypeC(long l) {
        this.a__V(l);
    }

    public long a__J() {
        return this.b;
    }

    public void a__V(long l) {
        this.b = l >= 0L ? l : 4000L;
    }

    public boolean equals(Object object) {
        try {
            if (object == null) {
                return false;
            }
            TypeC typeC = (TypeC)object;
            return this.a__J() == typeC.a__J();
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public String toString() {
        return new String(z + this.a__J() + "}");
    }
}
