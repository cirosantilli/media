/* slides.jar — a/a/r/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.r;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Vector;
import recovered.a.a.d.a.TypeA;
import recovered.a.a.e.c.TypeD;
import recovered.a.a.e.c.TypeH;
import recovered.a.a.r.TypeC;
import recovered.a.a.r.TypeE;
import recovered.org.b.g.TypeG;

public class TypeB
extends Container
implements TypeC {
    private recovered.org.b.b.TypeG a = recovered.a.a.k.TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
    private static final Component b;
    private boolean c = false;
    private TypeA d;
    private Vector e = new Vector();
    private Image g;
    private String h;
    private Component i;
    private boolean j;
    private static String[] z;

    public TypeB(String string, TypeA typeA) {
        this(string, typeA, null);
    }

    public TypeB(String string, TypeA typeA, Image image) {
        this.a(image);
        this.a__V(string);
        this.a(typeA);
        this.a(b);
        this.j = false;
    }

    public void a(Component component) {
        this.i = component != null ? component : b;
    }

    public void a__V(String string) {
        this.h = string;
    }

    public void a(Image image) {
        this.g = image;
    }

    public void a(TypeA typeA) {
        this.d = typeA != null ? typeA : new TypeA(this.h);
    }

    public void a__V(recovered.a.a.d.a.TypeD typeD) {
        this.d.a__V((Object)typeD);
    }

    public int getX() {
        return this.getBounds().x;
    }

    public int getY() {
        return this.getBounds().y;
    }

    public int getHeight() {
        return this.getBounds().height;
    }

    public int getWidth() {
        return this.getBounds().width;
    }

    private TypeE a__La_a_r_e(recovered.a.a.d.a.TypeD typeD) {
        if (typeD != null) {
            TypeH typeH = typeD.a__La_a_e_c_h();
            recovered.a.a.i.b.TypeC typeC = null;
            try {
                typeC = (recovered.a.a.i.b.TypeC)typeD.b__La_a_i_b_a();
            }
            catch (ClassCastException classCastException) {
                this.a.d(z[0], classCastException);
            }
            if (typeH != null && typeC != null) {
                TypeE typeE = new TypeE(typeC, typeH, typeC.a__J());
                int n = 0;
                switch (typeH.b__I()) {
                    case 1: {
                        n = 0;
                        break;
                    }
                    case 2: {
                        n = this.getHeight() - typeH.getHeight();
                        break;
                    }
                    default: {
                        n = (this.getHeight() - typeH.getHeight()) / 2;
                    }
                }
                typeE.a(new Point((this.getWidth() - typeH.getWidth()) / 2, n));
                return typeE;
            }
            return null;
        }
        return null;
    }

    public boolean c__Z() {
        boolean bl = false;
        if (this.j) {
            this.d__V();
        } else if (!this.c) {
            int n = 0;
            while (n < this.e.size()) {
                TypeE typeE = (TypeE)this.e.elementAt(n);
                typeE.b(System.currentTimeMillis());
                if (typeE.c__Z()) {
                    this.remove((Component)((Object)typeE.b__La_a_e_c_h()));
                    this.e.removeElementAt(n);
                    bl = true;
                    continue;
                }
                ++n;
            }
            if (this.e.size() == 0) {
                if (this.d.a__I() > 0) {
                    this.a__V();
                    bl = true;
                } else {
                    this.d__V();
                    bl = true;
                }
            }
        }
        if (bl) {
            this.getParent().repaint(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
        return bl;
    }

    public void paint(Graphics graphics) {
        if (this.g != null) {
            graphics.drawImage(this.g, 0, 0, this.getWidth(), this.getHeight(), null);
        }
        super.paint(graphics);
    }

    public void update(Graphics graphics) {
        this.paint(graphics);
    }

    public void d__V() {
        if (this.d.a__I() > 0) {
            if (this.j && this.i != null) {
                this.remove(this.i);
                this.getParent().repaint(this.getX(), this.getY(), this.getWidth(), this.getHeight());
                this.j = false;
            }
        } else if (!this.j && this.i != null) {
            this.i.setBounds((this.getWidth() - this.i.getBounds().width) / 2, (this.getHeight() - this.i.getBounds().height) / 2, this.i.getBounds().width, this.i.getBounds().height);
            this.add(this.i);
            this.getParent().repaint(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            this.j = true;
        }
    }

    private synchronized void a__V() {
        TypeE typeE = null;
        boolean bl = true;
        while (bl) {
            try {
                recovered.a.a.d.a.TypeD typeD = (recovered.a.a.d.a.TypeD)this.d.b__Ljava_lang_Object();
                typeE = this.a__La_a_r_e(typeD);
            }
            catch (TypeG typeG) {
            }
            bl = typeE == null && this.d.a__I() > 0;
        }
        if (typeE != null) {
            this.add((Component)((Object)typeE.b__La_a_e_c_h()));
            typeE.a__V(System.currentTimeMillis());
            this.e.addElement(typeE);
        }
    }

    static {
        z = new String[]{"Cannot use the SlideShowRenderer to render a non-SlideShow type of content.", "Loading, please wait"};
        b = new TypeD(z[1]);
    }
}
