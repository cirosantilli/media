/* world.jar — b/a/b/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.b.a.b;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Polygon;
import recovered.b.a.b.TypeD;
import recovered.b.a.b.TypeE;
import recovered.c.a.a.TypeB;

public class TypeA
extends Canvas
implements TypeE {
    private double a = 0.0;
    private double b = 1540.0;
    private int c = 1;
    private int d = 3;
    private Color e = new Color(0, 0, 0);
    private Image f = null;
    private int g = 0;
    private int h;
    private int i;
    private Image j = null;
    private Graphics k = null;

    public void a(Image image) {
        this.f = image;
        MediaTracker mediaTracker = new MediaTracker(this);
        try {
            mediaTracker.addImage(this.f, 0);
            mediaTracker.waitForAll();
        }
        catch (Exception exception) {
            this.f = null;
        }
    }

    public void a(double d) {
        if (d > 0.0) {
            this.b = d;
        }
    }

    public void a(int n) {
        block3: {
            block2: {
                if (n != 1) break block2;
                this.c = n;
                if (!TypeD.m) break block3;
            }
            this.c = 0;
        }
    }

    public void a(Color color) {
        this.e = color;
    }

    public void b(int n) {
        block3: {
            block2: {
                if (n != 1) break block2;
                this.g = n;
                if (!TypeD.m) break block3;
            }
            this.g = 0;
        }
    }

    public void c(int n) {
        if (n > 0) {
            this.d = n;
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
                if (!(d > this.b)) break block4;
                this.a = this.b;
                if (!bl) break block3;
            }
            this.a = d;
        }
    }

    public void update(Graphics graphics) {
        double d;
        Point point;
        block7: {
            block8: {
                boolean bl;
                block6: {
                    bl = TypeD.m;
                    Dimension dimension = this.getSize();
                    if (this.k == null) {
                        this.j = this.createImage(dimension.width, dimension.height);
                        this.k = this.j.getGraphics();
                        this.a(dimension);
                    }
                    this.k.setColor(this.getBackground());
                    this.k.fillRect(0, 0, dimension.width, dimension.height);
                    if (this.f != null) {
                        this.k.drawImage(this.f, 0, 0, this);
                    }
                    point = new Point(dimension.width / 2, dimension.height - this.h);
                    if (this.g == 0) {
                        this.k.setColor(this.getForeground());
                        this.k.fillArc(point.x - this.i, point.y - this.i, this.i * 2, this.i * 2, 0, 180);
                    }
                    if (!(this.a <= 0.0)) break block6;
                    d = 0.0;
                    if (!bl) break block7;
                    int n = TypeB.b;
                    TypeB.b = ++n;
                }
                if (this.c != 0) break block8;
                d = this.a / this.b * 180.0;
                if (!bl) break block7;
            }
            d = Math.log(this.a) / Math.log(this.b) * 180.0;
        }
        Point point2 = new Point(point);
        double d2 = (double)this.i * 0.97;
        double d3 = d * Math.PI / 180.0;
        point2.x -= (int)(d2 * Math.cos(d3));
        point2.y -= (int)(d2 * Math.sin(d3));
        Point point3 = new Point(point);
        d2 = this.d;
        d3 = (d - 90.0) * (Math.PI / 180);
        point3.x -= (int)(d2 * Math.cos(d3));
        point3.y -= (int)(d2 * Math.sin(d3));
        Point point4 = new Point(point);
        d2 = this.d;
        d3 = (d + 90.0) * (Math.PI / 180);
        point4.x -= (int)(d2 * Math.cos(d3));
        point4.y -= (int)(d2 * Math.sin(d3));
        Polygon polygon = new Polygon();
        polygon.addPoint(point4.x, point4.y);
        polygon.addPoint(point3.x, point3.y);
        polygon.addPoint(point2.x, point2.y);
        this.k.setColor(this.e);
        this.k.drawLine(point.x, point.y, point2.x, point2.y);
        this.k.fillPolygon(polygon);
        graphics.drawImage(this.j, 0, 0, this);
    }

    public Dimension getPreferredSize() {
        return new Dimension(640, 480);
    }

    private void a(Dimension dimension) {
        block3: {
            block2: {
                int n = dimension.width / 2;
                n -= (int)((double)n * 0.05);
                int n2 = dimension.height;
                if (n >= (n2 -= 2 * (int)((double)n2 * 0.05))) break block2;
                this.i = dimension.width / 2;
                if (!TypeD.m) break block3;
            }
            this.i = dimension.height;
        }
        this.h = (int)(0.05 * (double)this.i);
        this.i -= 2 * this.h;
    }
}
