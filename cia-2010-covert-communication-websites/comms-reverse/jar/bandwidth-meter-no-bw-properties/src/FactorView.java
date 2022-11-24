/*
 * Factor Force News, factorspeed.jar, FactorView.class. Method names follow the archive.
 */
import java.awt.Color;
import java.awt.Image;

public interface FactorView {
    public static final int LABEL_MODE = 0;
    public static final int METER_MODE = 1;
    public static final int GAUGE_MODE = 2;
    public static final int VERTICAL = 1;
    public static final int HORIZONTAL = 0;
    public static final int LINEAR = 0;
    public static final int LOGARITHMIC = 1;
    public static final int DISPLAYFOREGROUNDON = 0;
    public static final int DISPLAYFOREGROUNDOFF = 1;

    public void setThickness(int var1);

    public void setRate(double var1);

    public void setScaleMode(int var1);

    public void setDisplayForeGround(int var1);

    public void setScaleMax(double var1);

    public void setDrawColor(Color var1);

    public void setImage(Image var1);

    public void setOrientation(int var1);
}
