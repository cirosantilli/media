/* slides.jar — a/a/e/c/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.e.c;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.Vector;
import recovered.a.a.e.b.b.TypeE;
import recovered.a.a.e.c.TypeH;
import recovered.a.a.o.TypeC;

public class TypeD
extends Component
implements TypeH,
MouseListener,
Serializable {
    private TypeE e;
    private int f;
    private Image g;
    private Cursor h;
    private Vector i = new Vector();
    private Vector j = new Vector();
    private Vector k = new Vector();
    private Vector l = new Vector();
    private int m;
    private static String z = "TextContent {text: ";

    public TypeD() {
        this(null, null, null);
    }

    public TypeD(String string) {
        this(string, null, null);
    }

    public TypeD(String string, Font font, Color color) {
        this(new TypeE(string, font, color));
    }

    public TypeD(TypeE typeE) {
        this.a(typeE);
        this.a((Cursor)null);
        this.a__V(0);
        this.addMouseListener(this);
    }

    public void a(TypeE typeE) {
        this.a(typeE, true);
    }

    protected void a(TypeE typeE, boolean bl) {
        if (typeE != null) {
            this.e = typeE;
            if (this.e.b__Ljava_awt_Font() != null) {
                super.setFont(this.e.b__Ljava_awt_Font());
            }
            if (this.e.c__Ljava_awt_Color() != null) {
                super.setForeground(this.e.c__Ljava_awt_Color());
            }
        } else {
            this.e = new TypeE();
        }
        if (bl) {
            this.e__V();
        }
        this.g = null;
    }

    public void a(recovered.a.a.o.TypeD typeD) {
        if (typeD != null) {
            this.i.addElement(typeD);
        }
    }

    public void b(recovered.a.a.o.TypeD typeD) {
        if (typeD != null) {
            this.i.removeElement(typeD);
        }
    }

    public void c(recovered.a.a.o.TypeD typeD) {
        if (typeD != null) {
            this.j.addElement(typeD);
        }
    }

    public void d(recovered.a.a.o.TypeD typeD) {
        if (typeD != null) {
            this.j.removeElement(typeD);
        }
    }

    public void e(recovered.a.a.o.TypeD typeD) {
        if (typeD != null) {
            this.k.addElement(typeD);
        }
    }

    public void f(recovered.a.a.o.TypeD typeD) {
        if (typeD != null) {
            this.k.removeElement(typeD);
        }
    }

    public void a(TypeC typeC) {
        if (typeC != null) {
            this.l.addElement(typeC);
        }
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

    public void paint(Graphics graphics) {
        if (graphics != null) {
            if (this.g != null) {
                graphics.drawImage(this.g, 0, 0, null);
            } else {
                this.a(graphics);
            }
        }
    }

    public String b__Ljava_lang_String() {
        String[] stringArray = this.e.a__ArrayLjava_lang_String();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < stringArray.length; ++i) {
            stringBuffer.append(stringArray[i]);
        }
        return stringBuffer.toString();
    }

    public Font getFont() {
        return this.e.b__Ljava_awt_Font();
    }

    public void setFont(Font font) {
        this.e.a(font);
        super.setFont(this.e.b__Ljava_awt_Font());
        this.e__V();
        this.g = null;
    }

    public void b__V(int n) {
        this.e.a__V(n);
    }

    public void a__V(int n) {
        this.m = n;
    }

    public int b__I() {
        return this.m;
    }

    public Color c__Ljava_awt_Color() {
        return this.e.c__Ljava_awt_Color();
    }

    public void a(Cursor cursor) {
        this.h = cursor;
    }

    private void a(Graphics graphics) {
        if (graphics == null) {
            return;
        }
        graphics.setFont(this.e.b__Ljava_awt_Font());
        graphics.setColor(this.e.c__Ljava_awt_Color());
        FontMetrics fontMetrics = this.getFontMetrics(this.e.b__Ljava_awt_Font());
        this.f = fontMetrics.getLeading() + fontMetrics.getMaxAscent();
        Dimension[] dimensionArray = this.e.a(fontMetrics);
        String[] stringArray = this.e.a__ArrayLjava_lang_String();
        int n = 0;
        int n2 = this.f;
        for (int i = 0; i < stringArray.length; ++i) {
            switch (this.e.d__I()) {
                case 0: {
                    n = 0;
                    break;
                }
                case 1: {
                    n = this.getWidth() - dimensionArray[i].width;
                    break;
                }
                case 2: {
                    n = (this.getWidth() - dimensionArray[i].width) / 2;
                    break;
                }
                default: {
                    n = 0;
                }
            }
            graphics.drawString(stringArray[i], n, n2);
            if (this.e.e__Z()) {
                graphics.drawLine(n, n2 + 1, n + dimensionArray[i].width, n2 + 1);
            }
            n2 += dimensionArray[i].height;
        }
    }

    protected void e__V() {
        this.setSize(this.b(this.e));
    }

    protected Dimension b(TypeE typeE) {
        int n = 0;
        int n2 = 0;
        if (typeE != null && typeE.b__Ljava_awt_Font() != null) {
            Dimension[] dimensionArray = typeE.b(this.getFontMetrics(typeE.b__Ljava_awt_Font()));
            for (int i = 0; i < dimensionArray.length; ++i) {
                n += dimensionArray[i].height;
                n2 = Math.max(n2, dimensionArray[i].width);
            }
        }
        return new Dimension(n2, n);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        if (this.h != null) {
            super.setCursor(this.h);
        }
        for (int i = 0; i < this.l.size(); ++i) {
            TypeC typeC = (TypeC)this.l.elementAt(i);
            for (int j = 0; j < this.j.size(); ++j) {
                try {
                    recovered.a.a.o.TypeD typeD = (recovered.a.a.o.TypeD)this.j.elementAt(j);
                    typeC.a(typeD);
                    continue;
                }
                catch (NullPointerException nullPointerException) {
                    this.l.removeElementAt(i);
                    --i;
                }
            }
        }
    }

    public void mouseExited(MouseEvent mouseEvent) {
        if (this.h != null) {
            super.setCursor(new Cursor(0));
        }
        for (int i = 0; i < this.l.size(); ++i) {
            TypeC typeC = (TypeC)this.l.elementAt(i);
            for (int j = 0; j < this.k.size(); ++j) {
                try {
                    recovered.a.a.o.TypeD typeD = (recovered.a.a.o.TypeD)this.k.elementAt(j);
                    typeC.a(typeD);
                    continue;
                }
                catch (NullPointerException nullPointerException) {
                    this.l.removeElementAt(i);
                    --i;
                }
            }
        }
    }

    public void mousePressed(MouseEvent mouseEvent) {
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        for (int i = 0; i < this.l.size(); ++i) {
            TypeC typeC = (TypeC)this.l.elementAt(i);
            for (int j = 0; j < this.i.size(); ++j) {
                try {
                    recovered.a.a.o.TypeD typeD = (recovered.a.a.o.TypeD)this.i.elementAt(j);
                    typeC.a(typeD);
                    continue;
                }
                catch (NullPointerException nullPointerException) {
                    this.l.removeElementAt(i);
                    --i;
                }
            }
        }
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            TypeD typeD = (TypeD)object;
            return this.a(this.b__Ljava_lang_String(), typeD.b__Ljava_lang_String()) && this.a(this.getFont(), typeD.getFont()) && this.a(this.c__Ljava_awt_Color(), typeD.c__Ljava_awt_Color());
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    private boolean a(Object object, Object object2) {
        if (object == null || object2 == null) {
            return object == object2;
        }
        return object.equals(object2);
    }

    public String toString() {
        return new String(z + this.b__Ljava_lang_String() + "}");
    }
}
