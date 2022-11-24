/*
 * Factor Force News, factorspeed.jar, DisplayMeter.class. Method names follow the archive.
 */
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

public class DisplayMeter
extends Canvas
implements FactorView {
    private double m_rate = 0.0;
    private int m_orientation = 0;
    private double m_scaleMax = 1540.0;
    private int m_scaleMode = 1;
    private int m_thickness = 3;
    private Color m_drawColor = new Color(0, 0, 0);
    private Image m_backImage = null;
    private int m_displayfg = 0;
    private Image m_offImage = null;
    private Graphics m_graphics = null;
    private int m_border = 0;

    public void setThickness(int n) {
        if (n > 0) {
            this.m_thickness = n;
        }
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

    public void paint(Graphics graphics) {
        this.update(graphics);
    }

    public void update(Graphics graphics) {
        Dimension dimension = this.getSize();
        if (this.m_graphics == null) {
            this.m_offImage = this.createImage(dimension.width, dimension.height);
            this.m_graphics = this.m_offImage.getGraphics();
            this.checkThickness(dimension);
        }
        this.m_graphics.setColor(this.getBackground());
        this.m_graphics.fillRect(0, 0, dimension.width, dimension.height);
        if (this.m_backImage != null) {
            this.m_graphics.drawImage(this.m_backImage, 0, 0, this);
        }
        if (this.m_displayfg == 0) {
            this.m_graphics.setColor(this.getForeground());
            this.m_graphics.fillRect(this.m_border, this.m_border, dimension.width - this.m_border * 2, dimension.height - this.m_border * 2);
        }
        double d = this.m_rate <= 0.0 ? 0.0 : (this.m_scaleMode == 0 ? this.m_rate / this.m_scaleMax : Math.log(this.m_rate) / Math.log(this.m_scaleMax));
        this.m_graphics.setColor(this.m_drawColor);
        if (this.m_orientation == 1) {
            int n = this.getLength(dimension.height - this.m_border * 2, d);
            this.m_graphics.fillRect(this.m_border, dimension.height - this.m_border - n, this.m_thickness, n);
        } else {
            int n = this.getLength(dimension.width - this.m_border * 2, d);
            this.m_graphics.fillRect(this.m_border, this.m_border, n, this.m_thickness);
        }
        graphics.drawImage(this.m_offImage, 0, 0, this);
    }

    public void setDrawColor(Color color) {
        this.m_drawColor = color;
    }

    private void checkThickness(Dimension dimension) {
        int n = this.m_orientation == 0 ? dimension.height : dimension.width;
        if (this.m_thickness > n) {
            this.m_thickness = n;
            this.m_border = 0;
            return;
        }
        this.m_border = (n - this.m_thickness) / 2;
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

    private int getLength(int n, double d) {
        int n2 = (int)((double)n * d);
        if (n2 < 0) {
            n2 = 0;
        } else if (n2 > n) {
            n2 = n;
        }
        return n2;
    }

    public void setOrientation(int n) {
        if (n == 1) {
            this.m_orientation = 1;
            return;
        }
        this.m_orientation = 0;
    }
}
