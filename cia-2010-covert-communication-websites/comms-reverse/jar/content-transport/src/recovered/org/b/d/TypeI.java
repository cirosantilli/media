/* slides.jar — org/b/d/i.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.d;

import java.util.Vector;
import recovered.org.b.d.TypeG;
import recovered.org.b.d.TypeH;

public class TypeI
extends TypeH {
    private Vector a = new Vector();

    public TypeI() {
    }

    public TypeI(TypeH[] typeHArray) {
        this();
        if (typeHArray != null) {
            for (int i = 0; i < typeHArray.length; ++i) {
                this.a(typeHArray[i]);
            }
        }
    }

    public void a(TypeH typeH) {
        this.a.addElement(typeH);
    }

    public byte[] a__ArrayB(byte[] byArray) throws TypeG {
        byte[] byArray2 = byArray;
        for (int i = 0; i < this.a.size(); ++i) {
            TypeH typeH = (TypeH)this.a.elementAt(i);
            byArray2 = typeH.a__ArrayB(byArray2);
        }
        return byArray2;
    }

    public byte[] b__ArrayB(byte[] byArray) throws TypeG {
        byte[] byArray2 = byArray;
        for (int i = this.a.size() - 1; i >= 0; --i) {
            TypeH typeH = (TypeH)this.a.elementAt(i);
            byArray2 = typeH.b__ArrayB(byArray2);
        }
        return byArray2;
    }
}
