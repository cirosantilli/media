/* slides.jar — a/a/r/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.r;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;
import recovered.a.a.e.c.TypeH;
import recovered.a.a.i.b.TypeB;
import recovered.a.a.r.TypeC;
import recovered.a.a.r.TypeD;
import recovered.org.b.g.TypeG;

public class TypeA
extends Container
implements TypeC {
    private recovered.org.b.b.TypeG a = recovered.a.a.k.TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
    private static final Component b;
    private int d = 1;
    private boolean e = false;
    private recovered.a.a.d.a.TypeA f;
    private Vector g = new Vector();
    private int h;
    private Image j;
    private String k;
    private Component l;
    private boolean m;
    private static String[] z;

    public TypeA(String string, recovered.a.a.d.a.TypeA typeA) {
        this(string, typeA, null);
    }

    public TypeA(String string, recovered.a.a.d.a.TypeA typeA, Image image) {
        this.a(image);
        this.a__V(string);
        this.a(typeA);
        this.a(b);
        this.a__V(50);
        this.m = false;
    }

    public void a(Component component) {
        this.l = component != null ? component : b;
    }

    public void a__V(String string) {
        this.k = string;
    }

    public void a(recovered.a.a.d.a.TypeA typeA) {
        this.f = typeA != null ? typeA : new recovered.a.a.d.a.TypeA(this.k);
    }

    public void a__V(recovered.a.a.d.a.TypeD typeD) {
        this.f.a__V((Object)typeD);
    }

    public void a(Image image) {
        this.j = image;
    }

    public void a__V(int n) {
        this.h = n < 0 ? 0 : n;
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

    private TypeD a__La_a_r_d(recovered.a.a.d.a.TypeD typeD) {
        if (typeD != null) {
            TypeH typeH = typeD.a__La_a_e_c_h();
            TypeB typeB = null;
            try {
                typeB = (TypeB)typeD.b__La_a_i_b_a();
            }
            catch (ClassCastException classCastException) {
                this.a.d(z[0], classCastException);
            }
            if (typeH != null && typeB != null) {
                TypeD typeD2 = null;
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
                switch (typeB.b__I()) {
                    case 0: {
                        typeD2 = new TypeD(typeB, typeH, -typeB.a__I(), 0, this.getWidth(), n, this.getWidth() + typeH.getWidth(), 0);
                        break;
                    }
                    case 1: {
                        typeD2 = new TypeD(typeB, typeH, typeB.a__I(), 0, -typeH.getWidth(), n, this.getWidth() + typeH.getWidth(), 0);
                        break;
                    }
                    case 2: {
                        typeD2 = new TypeD(typeB, typeH, 0, -typeB.a__I(), (this.getWidth() - typeH.getWidth()) / 2, this.getHeight(), 0, this.getHeight() + typeH.getHeight());
                        break;
                    }
                    case 3: {
                        typeD2 = new TypeD(typeB, typeH, 0, typeB.a__I(), (this.getWidth() - typeH.getWidth()) / 2, -typeH.getHeight(), 0, this.getHeight() + typeH.getHeight());
                    }
                }
                return typeD2;
            }
            return null;
        }
        return null;
    }

    public boolean c__Z() {
        boolean bl = false;
        if (this.m) {
            this.d__V();
        } else if (!this.e) {
            TypeD typeD;
            int n = 0;
            while (n < this.g.size()) {
                typeD = (TypeD)this.g.elementAt(n);
                typeD.b(System.currentTimeMillis());
                if (typeD.c__Z()) {
                    bl = true;
                }
                if (typeD.d__Z()) {
                    this.g.removeElementAt(n);
                    this.remove((Component)((Object)typeD.b__La_a_e_c_h()));
                    bl = true;
                    continue;
                }
                ++n;
            }
            if (this.g.size() > 0) {
                typeD = (TypeD)this.g.lastElement();
                if (this.f.a__I() > 0 && this.a(typeD, this.h)) {
                    bl = true;
                    this.b__V();
                }
            } else if (this.f.a__I() > 0) {
                bl = true;
                this.b__V();
            } else {
                this.d__V();
            }
        }
        return bl;
    }

    public void paint(Graphics graphics) {
        if (this.j != null) {
            graphics.drawImage(this.j, 0, 0, this.getWidth(), this.getHeight(), null);
        }
        super.paint(graphics);
    }

    public void update(Graphics graphics) {
        this.paint(graphics);
    }

    public void d__V() {
        if (this.f.a__I() > 0) {
            if (this.m && this.l != null) {
                this.remove(this.l);
                this.getParent().repaint(this.getX(), this.getY(), this.getWidth(), this.getHeight());
                this.m = false;
            }
        } else if (!this.m && this.l != null) {
            this.l.setBounds((this.getWidth() - this.l.getBounds().width) / 2, (this.getHeight() - this.l.getBounds().height) / 2, this.l.getBounds().width, this.l.getBounds().height);
            this.add(this.l);
            this.getParent().repaint(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            this.m = true;
        }
    }

    private synchronized void b__V() {
        TypeD typeD = null;
        boolean bl = true;
        while (bl) {
            try {
                recovered.a.a.d.a.TypeD typeD2 = (recovered.a.a.d.a.TypeD)this.f.b__Ljava_lang_Object();
                typeD = this.a__La_a_r_d(typeD2);
            }
            catch (TypeG typeG) {
            }
            bl = typeD == null && this.f.a__I() > 0;
        }
        if (typeD != null) {
            this.add((Component)((Object)typeD.b__La_a_e_c_h()));
            typeD.a__V(System.currentTimeMillis());
            this.g.addElement(typeD);
        }
    }

    private boolean a(TypeD typeD, int n) {
        if (typeD != null && typeD.a__La_a_i_b_b() != null) {
            TypeB typeB = typeD.a__La_a_i_b_b();
            boolean bl = false;
            switch (typeB.b__I()) {
                case 0: {
                    int n2 = this.getWidth() - n;
                    bl = typeD.g__I() + typeD.b__La_a_e_c_h().getWidth() <= n2;
                    break;
                }
                case 1: {
                    int n3 = n;
                    bl = typeD.g__I() >= n3;
                    break;
                }
                case 2: {
                    int n4 = this.getHeight() - n;
                    bl = typeD.h__I() + typeD.b__La_a_e_c_h().getHeight() <= n4;
                    break;
                }
                case 3: {
                    int n5 = n;
                    bl = typeD.h__I() >= n5;
                }
            }
            return bl;
        }
        return false;
    }

    static {
        z = new String[]{"Cannot use the ScrollRenderer to render a non-Scroll type of content.", "Loading, please wait"};
        b = new recovered.a.a.e.c.TypeD(z[1]);
    }
}
