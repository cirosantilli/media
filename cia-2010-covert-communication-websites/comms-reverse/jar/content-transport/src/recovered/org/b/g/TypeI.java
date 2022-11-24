/* slides.jar — org/b/g/i.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.g;

import java.util.Vector;
import recovered.org.b.g.TypeG;

public class TypeI {
    protected Vector a = new Vector();
    private static String z = "Empty queue";

    public int a__I() {
        return this.a.size();
    }

    public synchronized void a__V(Object object) {
        this.a.addElement(object);
    }

    public synchronized Object b__Ljava_lang_Object() throws TypeG {
        if (this.a__I() <= 0) {
            throw new TypeG(z);
        }
        Object e = this.a.elementAt(0);
        this.a.removeElementAt(0);
        return e;
    }
}
