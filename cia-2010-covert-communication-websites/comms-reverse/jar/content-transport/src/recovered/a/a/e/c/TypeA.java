/* slides.jar — a/a/e/c/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.e.c;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.util.Vector;
import recovered.a.a.e.b.b.TypeC;
import recovered.a.a.e.c.TypeH;
import recovered.a.a.o.TypeD;

public class TypeA
extends Component
implements TypeH,
ImageObserver,
MouseListener {
    private TypeC d;
    private Cursor e;
    private Vector f = new Vector();
    private Vector g = new Vector();
    private Vector h = new Vector();
    private Vector i = new Vector();
    private int j;

    public TypeA() {
        this(new TypeC());
    }

    public TypeA(TypeC typeC) {
        this.a(typeC);
        this.a((Cursor)null);
        this.a__V(0);
        this.addMouseListener(this);
    }

    public void a(TypeC typeC) {
        this.a(typeC, true);
    }

    protected void a(TypeC typeC, boolean bl) {
        this.d = typeC != null ? typeC : new TypeC();
        if (bl) {
            this.e__V();
        }
    }

    public void a(TypeD typeD) {
        if (typeD != null) {
            this.f.addElement(typeD);
        }
    }

    public void b(TypeD typeD) {
        if (typeD != null) {
            this.f.removeElement(typeD);
        }
    }

    public void c(TypeD typeD) {
        if (typeD != null) {
            this.g.addElement(typeD);
        }
    }

    public void d(TypeD typeD) {
        if (typeD != null) {
            this.g.removeElement(typeD);
        }
    }

    public void e(TypeD typeD) {
        if (typeD != null) {
            this.h.addElement(typeD);
        }
    }

    public void f(TypeD typeD) {
        if (typeD != null) {
            this.h.removeElement(typeD);
        }
    }

    public void a(recovered.a.a.o.TypeC typeC) {
        this.i.addElement(typeC);
    }

    public void a__V(int n) {
        this.j = n;
    }

    public int b__I() {
        return this.j;
    }

    public int getX() {
        return this.getBounds().x;
    }

    public int getY() {
        return this.getBounds().y;
    }

    public int getWidth() {
        return this.getSize().width;
    }

    public int getHeight() {
        return this.getSize().height;
    }

    public void paint(Graphics graphics) {
        Image image;
        if (graphics != null && this.d != null && (image = this.d.a__Ljava_awt_Image()) != null) {
            graphics.drawImage(image, 0, 0, null);
        }
    }

    public void a(Cursor cursor) {
        this.e = cursor;
    }

    protected void d__V() {
        this.setSize(this.b(this.d));
    }

    protected Dimension b(TypeC typeC) {
        if (typeC == null) {
            return new Dimension(0, 0);
        }
        Image image = typeC.a__Ljava_awt_Image();
        if (image != null) {
            return new Dimension(image.getWidth(null), image.getHeight(null));
        }
        return new Dimension(0, 0);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        if (this.e != null) {
            super.setCursor(this.e);
        }
        for (int i = 0; i < this.i.size(); ++i) {
            recovered.a.a.o.TypeC typeC = (recovered.a.a.o.TypeC)this.i.elementAt(i);
            for (int j = 0; j < this.g.size(); ++j) {
                try {
                    TypeD typeD = (TypeD)this.g.elementAt(j);
                    typeC.a(typeD);
                    continue;
                }
                catch (NullPointerException nullPointerException) {
                    this.i.removeElementAt(i);
                    --i;
                }
            }
        }
    }

    public void mouseExited(MouseEvent mouseEvent) {
        if (this.e != null) {
            super.setCursor(new Cursor(0));
        }
        for (int i = 0; i < this.i.size(); ++i) {
            recovered.a.a.o.TypeC typeC = (recovered.a.a.o.TypeC)this.i.elementAt(i);
            for (int j = 0; j < this.h.size(); ++j) {
                try {
                    TypeD typeD = (TypeD)this.h.elementAt(j);
                    typeC.a(typeD);
                    continue;
                }
                catch (NullPointerException nullPointerException) {
                    this.i.removeElementAt(i);
                    --i;
                }
            }
        }
    }

    public void mousePressed(MouseEvent mouseEvent) {
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        for (int i = 0; i < this.i.size(); ++i) {
            recovered.a.a.o.TypeC typeC = (recovered.a.a.o.TypeC)this.i.elementAt(i);
            for (int j = 0; j < this.f.size(); ++j) {
                try {
                    TypeD typeD = (TypeD)this.f.elementAt(j);
                    typeC.a(typeD);
                    continue;
                }
                catch (NullPointerException nullPointerException) {
                    this.i.removeElementAt(i);
                    --i;
                }
            }
        }
    }

    private void e__V() {
        if (this.d != null && this.d.a__Ljava_awt_Image() != null) {
            this.prepareImage(this.d.a__Ljava_awt_Image(), -1, -1, this);
            this.d__V();
        }
    }

    public boolean imageUpdate(Image image, int n, int n2, int n3, int n4, int n5) {
        if ((n & 0x80) != 0) {
            return false;
        }
        if ((n & 0x20) != 0) {
            this.d__V();
            return false;
        }
        return true;
    }
}
