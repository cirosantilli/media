/*
 * Factor Force News, factorspeed.jar, DisplayGauge.class. Method names follow the archive.
 */
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Polygon;

public class DisplayGauge
extends Canvas
implements FactorView {
    private double m_rate = 0.0;
    private double m_scaleMax = 1540.0;
    private int m_scaleMode = 1;
    private int m_thickness = 3;
    private Color m_drawColor = new Color(0, 0, 0);
    private Image m_backImage = null;
    private int m_displayfg = 0;
    private int m_border;
    private int m_radius;
    private Image m_offImage = null;
    private Graphics m_graphics = null;
    private static final double BORDER_PERCENT = 0.05;
    private static final double RADIUS_PERCENT = 0.97;

    public void setThickness(int n) {
        if (n > 0) {
            this.m_thickness = n;
        }
    }

    private void checkRadius(Dimension dimension) {
        int n = dimension.width / 2;
        n -= (int)((double)n * 0.05);
        int n2 = dimension.height;
        this.m_radius = n < (n2 -= 2 * (int)((double)n2 * 0.05)) ? dimension.width / 2 : dimension.height;
        this.m_border = (int)(0.05 * (double)this.m_radius);
        this.m_radius -= 2 * this.m_border;
    }

    public void setRate(double d) {
        if (d < 0.0) {
            this.m_rate = 0.0;
            return;
        }
        if (d > this.m_scaleMax) {
            this.m_rate = this.m_scaleMax;
            return;
        }
        this.m_rate = d;
    }

    public void setScaleMode(int n) {
        if (n == 1) {
            this.m_scaleMode = n;
            return;
        }
        this.m_scaleMode = 0;
    }

    public void setDisplayForeGround(int n) {
        if (n == 1) {
            this.m_displayfg = n;
            return;
        }
        this.m_displayfg = 0;
    }

    public void setScaleMax(double d) {
        if (d > 0.0) {
            this.m_scaleMax = d;
        }
    }

    public void update(Graphics graphics) {
        Dimension dimension = this.getSize();
        if (this.m_graphics == null) {
            this.m_offImage = this.createImage(dimension.width, dimension.height);
            this.m_graphics = this.m_offImage.getGraphics();
            this.checkRadius(dimension);
        }
        this.m_graphics.setColor(this.getBackground());
        this.m_graphics.fillRect(0, 0, dimension.width, dimension.height);
        if (this.m_backImage != null) {
            this.m_graphics.drawImage(this.m_backImage, 0, 0, this);
        }
        Point point = new Point(dimension.width / 2, dimension.height - this.m_border);
        if (this.m_displayfg == 0) {
            this.m_graphics.setColor(this.getForeground());
            this.m_graphics.fillArc(point.x - this.m_radius, point.y - this.m_radius, this.m_radius * 2, this.m_radius * 2, 0, 180);
        }
        double d = this.m_rate <= 0.0 ? 0.0 : (this.m_scaleMode == 0 ? this.m_rate / this.m_scaleMax * 180.0 : Math.log(this.m_rate) / Math.log(this.m_scaleMax) * 180.0);
        Point point2 = new Point(point);
        double d2 = (double)this.m_radius * 0.97;
        double d3 = d * Math.PI / 180.0;
        point2.x -= (int)(d2 * Math.cos(d3));
        point2.y -= (int)(d2 * Math.sin(d3));
        Point point3 = new Point(point);
        d2 = this.m_thickness;
        d3 = (d - 90.0) * (Math.PI / 180);
        point3.x -= (int)(d2 * Math.cos(d3));
        point3.y -= (int)(d2 * Math.sin(d3));
        Point point4 = new Point(point);
        d2 = this.m_thickness;
        d3 = (d + 90.0) * (Math.PI / 180);
        point4.x -= (int)(d2 * Math.cos(d3));
        point4.y -= (int)(d2 * Math.sin(d3));
        Polygon polygon = new Polygon();
        polygon.addPoint(point4.x, point4.y);
        polygon.addPoint(point3.x, point3.y);
        polygon.addPoint(point2.x, point2.y);
        this.m_graphics.setColor(this.m_drawColor);
        this.m_graphics.drawLine(point.x, point.y, point2.x, point2.y);
        this.m_graphics.fillPolygon(polygon);
        graphics.drawImage(this.m_offImage, 0, 0, this);
    }

    public void setDrawColor(Color color) {
        this.m_drawColor = color;
    }

    public Dimension getPreferredSize() {
        return new Dimension(640, 480);
    }

    public void setImage(Image image) {
        this.m_backImage = image;
        MediaTracker mediaTracker = new MediaTracker(this);
        try {
            mediaTracker.addImage(this.m_backImage, 0);
            mediaTracker.waitForAll();
            return;
        }
        catch (Exception exception) {
            this.m_backImage = null;
            return;
        }
    }

    public void setOrientation(int n) {
    }
}
