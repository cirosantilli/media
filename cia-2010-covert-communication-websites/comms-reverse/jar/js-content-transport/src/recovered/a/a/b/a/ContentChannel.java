/* aeronet.jar — a/a/b/a/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.b.a;

import java.util.Vector;
import recovered.a.a.b.a.TypeC;
import recovered.a.a.b.a.TypeG;
import recovered.a.a.b.a.a.TypeA;

public class ContentChannel {
    private static int a;
    private String b;
    private Vector c = new Vector();
    private Vector d = new Vector();

    public ContentChannel(String string) {
        this.b = string;
    }

    public static void a__V(int n) {
        a = n;
    }

    public boolean a__Z() {
        return this.c.size() < a || this.c.size() == 0;
    }

    public void a__V(TypeA typeA) {
        this.c.addElement(typeA);
    }

    public TypeA b__La_a_b_a_a_a() {
        TypeA typeA = null;
        if (this.c.size() > 0 && (typeA = (TypeA)this.c.elementAt(0)) != null) {
            this.c.removeElementAt(0);
        }
        if (this.a__Z()) {
            this.e__V();
        }
        return typeA;
    }

    public TypeA c__La_a_b_a_a_a() {
        TypeA typeA = null;
        if (this.c.size() > 0) {
            typeA = (TypeA)this.c.elementAt(0);
        }
        return typeA;
    }

    public void d__V() {
        for (int i = 0; i < a; ++i) {
            this.e__V();
        }
    }

    public void e__V() {
        for (int i = 0; i < this.d.size(); ++i) {
            ((TypeC)this.d.elementAt(i)).a(new TypeG(this, this.b));
        }
    }

    public void a(TypeC typeC) {
        this.d.addElement(typeC);
    }
}
