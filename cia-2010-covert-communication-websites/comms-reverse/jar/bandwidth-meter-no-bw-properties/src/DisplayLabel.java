/*
 * Factor Force News, factorspeed.jar, DisplayLabel.class. Method names follow the archive.
 */
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DisplayLabel
extends Canvas {
    private Font m_font = null;
    private double m_rate = 0.0;
    private double m_limit = 1540.0;
    private Color m_drawColor = new Color(0, 0, 0);
    private DecimalFormat m_formatter = new DecimalFormat();
    private static final Color BORDER_LIGHT_GRAY = new Color(236, 233, 216);
    private static final Color BORDER_GRAY = new Color(212, 208, 200);
    private static final Color BORDER_DARK_GRAY = new Color(172, 168, 153);
    private static final Color BORDER_DARKER_GRAY = new Color(113, 111, 100);

    public void setThickness(int n) {
    }

    public void setRate(double d) {
        if (d < 0.0) {
            this.m_rate = -1.0;
            return;
        }
        if (d > this.m_limit) {
            this.m_rate = this.m_limit;
            return;
        }
        this.m_rate = d;
    }

    public DisplayLabel() {
        this.calculateFormat();
    }

    public void setScaleMode(int n) {
    }

    public void setDisplayForeGround(int n) {
    }

    public void setScaleMax(double d) {
        if (d > 0.0) {
            this.m_limit = d;
        }
        this.calculateFormat();
    }

    public void paint(Graphics graphics) {
        this.calculateFontSize(graphics);
        graphics.setFont(this.m_font);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        double d = this.m_limit < 1000.0 ? this.m_rate : this.m_rate / 1000.0;
        String string = this.m_formatter.format(d);
        super.paint(graphics);
        graphics.setColor(this.m_drawColor);
        graphics.drawString(string, (this.getSize().width - fontMetrics.stringWidth(string)) / 2, fontMetrics.getMaxAscent());
    }

    private void calculateFormat() {
        this.m_font = null;
        if (this.m_limit < 1000.0) {
            this.m_formatter.setDecimalSeparatorAlwaysShown(false);
            ((NumberFormat)this.m_formatter).setMinimumIntegerDigits(1);
            ((NumberFormat)this.m_formatter).setMaximumIntegerDigits(3);
            ((NumberFormat)this.m_formatter).setMaximumFractionDigits(1);
            ((NumberFormat)this.m_formatter).setMinimumFractionDigits(1);
            this.m_formatter.setPositiveSuffix(" Kbps");
            this.m_formatter.setNegativeSuffix(" Kbps");
            return;
        }
        this.m_formatter.setDecimalSeparatorAlwaysShown(false);
        ((NumberFormat)this.m_formatter).setMinimumIntegerDigits(1);
        ((NumberFormat)this.m_formatter).setMaximumIntegerDigits(2);
        ((NumberFormat)this.m_formatter).setMaximumFractionDigits(3);
        ((NumberFormat)this.m_formatter).setMinimumFractionDigits(3);
        this.m_formatter.setPositiveSuffix(" Mbps");
        this.m_formatter.setNegativeSuffix(" Mbps");
    }

    public void setDrawColor(Color color) {
        this.m_drawColor = color;
    }

    public Dimension getPreferredSize() {
        return new Dimension(640, 480);
    }

    public void setImage() {
    }

    private void calculateFontSize(Graphics graphics) {
        if (this.m_font != null) {
            return;
        }
        int n = 0;
        int[] nArray = new int[40];
        nArray[0] = 72;
        nArray[1] = 70;
        nArray[2] = 68;
        nArray[3] = 66;
        nArray[4] = 64;
        nArray[5] = 62;
        nArray[6] = 60;
        nArray[7] = 58;
        nArray[8] = 56;
        nArray[9] = 54;
        nArray[10] = 52;
        nArray[11] = 50;
        nArray[12] = 48;
        nArray[13] = 46;
        nArray[14] = 44;
        nArray[15] = 42;
        nArray[16] = 40;
        nArray[17] = 38;
        nArray[18] = 36;
        nArray[19] = 34;
        nArray[20] = 32;
        nArray[21] = 30;
        nArray[22] = 28;
        nArray[23] = 26;
        nArray[24] = 24;
        nArray[25] = 22;
        nArray[26] = 20;
        nArray[27] = 18;
        nArray[28] = 16;
        nArray[29] = 15;
        nArray[30] = 14;
        nArray[31] = 13;
        nArray[32] = 12;
        nArray[33] = 11;
        nArray[34] = 10;
        nArray[35] = 9;
        nArray[36] = 8;
        nArray[37] = 6;
        nArray[38] = 4;
        int[] nArray2 = nArray;
        Rectangle rectangle = new Rectangle(this.getSize());
        rectangle.grow(-5, -1);
        boolean bl = false;
        do {
            this.m_font = new Font(graphics.getFont().getName(), graphics.getFont().getStyle(), nArray2[n]);
            FontMetrics fontMetrics = graphics.getFontMetrics(this.m_font);
            if (fontMetrics.getHeight() <= rectangle.height && fontMetrics.stringWidth("1.000 Mbps") <= rectangle.width) {
                bl = true;
                continue;
            }
            if (nArray2[++n] != 0) continue;
            bl = true;
        } while (!bl);
    }

    public void setOrientation(int n) {
    }
}
