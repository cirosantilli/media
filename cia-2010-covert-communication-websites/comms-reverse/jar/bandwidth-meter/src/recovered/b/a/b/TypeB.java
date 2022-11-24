/* world.jar — b/a/b/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.b.a.b;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import recovered.b.a.b.TypeD;

public class TypeB
extends Canvas {
    private Font a = null;
    private double b = 0.0;
    private double c = 1540.0;
    private Color d = new Color(0, 0, 0);
    private DecimalFormat e = new DecimalFormat();
    private static final Color f;
    private static final Color g;
    private static final Color h;
    private static final Color i;
    private static String[] z;

    public TypeB() {
        this.a__V();
    }

    public void a(double d) {
        if (d > 0.0) {
            this.c = d;
        }
        this.a__V();
    }

    public void a(Color color) {
        this.d = color;
    }

    public void b(double d) {
        block3: {
            block4: {
                boolean bl;
                block2: {
                    bl = TypeD.m;
                    if (!(d < 0.0)) break block2;
                    this.b = -1.0;
                    if (!bl) break block3;
                }
                if (!(d > this.c)) break block4;
                this.b = this.c;
                if (!bl) break block3;
            }
            this.b = d;
        }
    }

    public void paint(Graphics graphics) {
        double d;
        FontMetrics fontMetrics;
        boolean bl;
        block5: {
            block4: {
                bl = TypeD.m;
                this.a(graphics);
                graphics.setFont(this.a);
                fontMetrics = graphics.getFontMetrics();
                if (!(this.c < 1000.0)) break block4;
                d = this.b;
                if (!bl) break block5;
            }
            d = this.b / 1000.0;
        }
        String string = this.e.format(d);
        super.paint(graphics);
        graphics.setColor(h);
        graphics.drawRect(0, 0, this.getSize().width - 1, this.getSize().height - 1);
        graphics.setColor(g);
        graphics.drawRect(1, 1, this.getSize().width - 3, this.getSize().height - 3);
        graphics.setColor(f);
        graphics.drawRect(2, 2, this.getSize().width - 5, this.getSize().height - 5);
        graphics.setColor(this.d);
        graphics.drawString(string, (this.getSize().width - fontMetrics.stringWidth(string)) / 2, fontMetrics.getMaxAscent());
        if (recovered.c.a.a.TypeB.b != 0) {
            TypeD.m = !bl;
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(640, 480);
    }

    private void a(Graphics graphics) {
        if (this.a != null) {
            return;
        }
        int n = 0;
        int[] nArray = new int[]{72, 70, 68, 66, 64, 62, 60, 58, 56, 54, 52, 50, 48, 46, 44, 42, 40, 38, 36, 34, 32, 30, 28, 26, 24, 22, 20, 18, 16, 15, 14, 13, 12, 11, 10, 9, 8, 6, 4, 0};
        Rectangle rectangle = new Rectangle(this.getSize());
        rectangle.grow(-5, -1);
        boolean bl = false;
        do {
            this.a = new Font(graphics.getFont().getName(), graphics.getFont().getStyle(), nArray[n]);
            FontMetrics fontMetrics = graphics.getFontMetrics(this.a);
            if (fontMetrics.getHeight() <= rectangle.height && fontMetrics.stringWidth(z[2]) <= rectangle.width) {
                bl = true;
                if (!TypeD.m) continue;
            }
            if (nArray[++n] != 0) continue;
            bl = true;
        } while (!bl);
    }

    private void a__V() {
        block3: {
            block2: {
                this.a = null;
                if (!(this.c < 1000.0)) break block2;
                this.e.setDecimalSeparatorAlwaysShown(false);
                this.e.setMinimumIntegerDigits(1);
                this.e.setMaximumIntegerDigits(3);
                this.e.setMaximumFractionDigits(1);
                this.e.setMinimumFractionDigits(1);
                this.e.setPositiveSuffix(z[0]);
                this.e.setNegativeSuffix(z[0]);
                if (!TypeD.m) break block3;
            }
            this.e.setDecimalSeparatorAlwaysShown(false);
            this.e.setMinimumIntegerDigits(1);
            this.e.setMaximumIntegerDigits(2);
            this.e.setMaximumFractionDigits(3);
            this.e.setMinimumFractionDigits(3);
            this.e.setPositiveSuffix(z[1]);
            this.e.setNegativeSuffix(z[1]);
        }
    }

    static {
        z = new String[]{" Kbps", " Mbps", "1.000 Mbps"};
        f = new Color(236, 233, 216);
        g = new Color(212, 208, 200);
        h = new Color(172, 168, 153);
        i = new Color(113, 111, 100);
    }
}
