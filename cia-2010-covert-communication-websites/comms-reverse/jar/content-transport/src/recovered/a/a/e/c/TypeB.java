/* slides.jar — a/a/e/c/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.e.c;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import recovered.a.a.e.b.b.TypeC;
import recovered.a.a.e.c.TypeA;
import recovered.a.a.e.c.TypeJ;

public class TypeB
extends TypeA
implements TypeJ {
    private TypeC k;
    private TypeC l;
    private boolean m;
    private boolean n;
    private boolean o;

    public TypeB(TypeC typeC, TypeC typeC2, boolean bl, boolean bl2) {
        super(typeC);
        this.l = typeC;
        this.k = typeC2;
        this.m = bl;
        this.n = bl2;
        this.o = false;
        this.d__V();
    }

    public void a__V() {
        if (this.o) {
            this.c__V();
        } else {
            this.b__V();
        }
    }

    public void b__V() {
        if (!this.o) {
            this.a(this.k, false);
            this.o = true;
            this.repaint();
        }
    }

    public void c__V() {
        if (this.o) {
            this.a(this.l, false);
            this.o = false;
            this.repaint();
        }
    }

    protected void d__V() {
        Dimension dimension = this.b(this.k);
        Dimension dimension2 = this.b(this.l);
        int n = Math.max(dimension.width, dimension2.width);
        int n2 = Math.max(dimension.height, dimension2.height);
        this.setSize(n, n2);
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        if (this.m) {
            this.a__V();
        }
        super.mouseEntered(mouseEvent);
    }

    public void mouseExited(MouseEvent mouseEvent) {
        if (this.m) {
            this.a__V();
        }
        super.mouseExited(mouseEvent);
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        if (this.n) {
            this.a__V();
        }
        super.mouseReleased(mouseEvent);
    }
}
