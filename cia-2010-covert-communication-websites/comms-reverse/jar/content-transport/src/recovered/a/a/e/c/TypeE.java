/* slides.jar — a/a/e/c/e.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.e.c;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import recovered.a.a.e.c.TypeD;
import recovered.a.a.e.c.TypeJ;

public class TypeE
extends TypeD
implements TypeJ {
    private recovered.a.a.e.b.b.TypeE n = null;
    private recovered.a.a.e.b.b.TypeE o = null;
    private boolean p = false;
    private boolean q = false;
    private boolean r = true;

    public TypeE() {
    }

    public TypeE(recovered.a.a.e.b.b.TypeE typeE, recovered.a.a.e.b.b.TypeE typeE2, boolean bl, boolean bl2) {
        super(typeE);
        this.o = typeE;
        this.n = typeE2;
        this.p = bl;
        this.q = bl2;
        this.r = false;
        this.e__V();
    }

    public void a__V() {
        if (this.r) {
            this.c__V();
        } else {
            this.b__V();
        }
    }

    public void b__V() {
        if (!this.r) {
            this.a(this.n, false);
            this.r = true;
            this.repaint();
        }
    }

    public void c__V() {
        if (this.r) {
            this.a(this.o, false);
            this.r = false;
            this.repaint();
        }
    }

    protected void e__V() {
        Dimension dimension = this.b(this.n);
        Dimension dimension2 = this.b(this.o);
        int n = Math.max(dimension.width, dimension2.width);
        int n2 = Math.max(dimension.height, dimension2.height);
        this.setSize(n, n2);
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        if (this.p) {
            this.a__V();
        }
        super.mouseEntered(mouseEvent);
    }

    public void mouseExited(MouseEvent mouseEvent) {
        if (this.p) {
            this.a__V();
        }
        super.mouseExited(mouseEvent);
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        if (this.q) {
            this.a__V();
        }
        super.mouseReleased(mouseEvent);
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            TypeE typeE = (TypeE)object;
            return this.b(this.n, typeE.n) && this.b(this.o, typeE.o);
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    private boolean b(Object object, Object object2) {
        if (object == null || object2 == null) {
            return object == object2;
        }
        return object.equals(object2);
    }
}
