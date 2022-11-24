/* world.jar — b/a/b/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.b.a.b;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import recovered.b.a.b.TypeD;
import recovered.b.a.b.TypeE;

public class TypeC
extends Canvas
implements TypeE {
    private double a = 0.0;
    private int b = 0;
    private double c = 1540.0;
    private int d = 1;
    private int e = 3;
    private Color f = new Color(0, 0, 0);
    private Image g = null;
    private int h = 0;
    private Image i = null;
    private Graphics j = null;
    private int k = 0;
    private int l = 0;

    public void a(Image image) {
        this.g = image;
        MediaTracker mediaTracker = new MediaTracker(this);
        try {
            mediaTracker.addImage(this.g, 0);
            mediaTracker.waitForAll();
        }
        catch (Exception exception) {
            this.g = null;
        }
    }

    public void a(int n) {
        block3: {
            block2: {
                if (n != 1) break block2;
                this.b = 1;
                if (!TypeD.m) break block3;
            }
            this.b = 0;
        }
    }

    public void a(double d) {
        if (d > 0.0) {
            this.c = d;
        }
    }

    public void b(int n) {
        block3: {
            block2: {
                if (n != 1) break block2;
                this.d = n;
                if (!TypeD.m) break block3;
            }
            this.d = 0;
        }
    }

    public void a(Color color) {
        this.f = color;
    }

    public void c(int n) {
        block3: {
            block2: {
                if (n != 1) break block2;
                this.h = n;
                if (!TypeD.m) break block3;
            }
            this.h = 0;
        }
    }

    public void d__V(int n) {
        if (n > 0) {
            this.e = n;
        }
    }

    public void b(double d) {
        block3: {
            block4: {
                boolean bl;
                block2: {
                    bl = TypeD.m;
                    if (!(d < 0.0)) break block2;
                    this.a = 0.0;
                    if (!bl) break block3;
                }
                if (!(d > this.c)) break block4;
                this.a = this.c;
                if (!bl) break block3;
            }
            this.a = d;
        }
    }

    public void e(int n) {
        this.k = n;
    }

    public void f(int n) {
        this.l = n;
    }

    public void paint(Graphics graphics) {
        this.update(graphics);
    }

    public void update(Graphics graphics) {
        block13: {
            int n;
            double d;
            Dimension dimension;
            block12: {
                boolean bl;
                block10: {
                    block11: {
                        block9: {
                            bl = TypeD.m;
                            dimension = this.getSize();
                            if (this.j == null) {
                                this.i = this.createImage(dimension.width, dimension.height);
                                this.j = this.i.getGraphics();
                                this.a(dimension);
                            }
                            this.j.setColor(this.getBackground());
                            this.j.fillRect(0, 0, dimension.width, dimension.height);
                            if (this.g != null) {
                                this.j.drawImage(this.g, 0, 0, this);
                            }
                            if (this.h == 0) {
                                this.j.setColor(this.getForeground());
                                this.j.fillRect(this.k, this.l, dimension.width - this.k * 2, dimension.height - this.l * 2);
                            }
                            if (!(this.a <= 0.0)) break block9;
                            d = 0.0;
                            if (!bl) break block10;
                        }
                        if (this.d != 0) break block11;
                        d = this.a / this.c;
                        if (!bl) break block10;
                    }
                    d = Math.log(this.a) / Math.log(this.c);
                }
                this.j.setColor(this.f);
                if (this.b != 1) break block12;
                n = this.a__I(dimension.height - this.l * 2, d);
                this.j.fillRect(this.k, dimension.height - this.l - n, this.e, n);
                if (!bl) break block13;
            }
            n = this.a__I(dimension.width - this.k * 2, d);
            this.j.fillRect(this.k, this.l, n, this.e);
        }
        graphics.drawImage(this.i, 0, 0, this);
    }

    public Dimension getPreferredSize() {
        return new Dimension(640, 480);
    }

    private void a(Dimension dimension) {
        int n;
        block5: {
            block4: {
                if (this.b != 0) break block4;
                n = dimension.height;
                if (!TypeD.m) break block5;
            }
            n = dimension.width;
        }
        if (this.e > n) {
            this.e = n;
            this.k = 0;
            this.l = 0;
        }
    }

    private int a__I(int n, double d) {
        int n2;
        block5: {
            block4: {
                n2 = (int)((double)n * d);
                if (n2 >= 0) break block4;
                n2 = 0;
                if (!TypeD.m) break block5;
            }
            if (n2 > n) {
                n2 = n;
            }
        }
        return n2;
    }
}
