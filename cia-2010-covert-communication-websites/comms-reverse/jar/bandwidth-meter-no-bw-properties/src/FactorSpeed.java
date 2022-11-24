/*
 * Factor Force News, factorspeed.jar, FactorSpeed.class. Method names follow the archive.
 */
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

public class FactorSpeed
extends Applet
implements Runnable {
    private Object m_display = null;
    private FactorData m_traffic = null;
    private boolean m_done = false;
    private Thread m_thread = null;
    private int m_repaintDelay = 1000;
    private int m_displayMode = 0;
    private String m_UrlParam = "http://www.factorforcenews.com/cgi-bin/factorspeed.cgi";
    private int m_SendSizeParam = 1344;
    private int m_ReadSizeParam = 1344;
    private int m_RepetitionParam = 1;
    private int m_CycleParam = 1000;
    private int m_DelayParam = 500;
    private String m_PatternParam = "UD";
    private Color m_backColorParam = new Color(47, 109, 1);
    private Color m_foreColorParam = Color.white;
    private Color m_drawColorParam = Color.white;
    private String m_fontParam = "Monospaced";
    private int m_displayForeColorParam = 1;
    private int m_thicknessParam = 6;
    private double m_scaleMaxParam = 9000.0;
    private int m_scaleModeParam = 1;
    private int m_orientationParam = 1;
    private String m_backImageParam = null;
    private final String UrlParam = "URL";
    private final String sendSizeParam = "SendSize";
    private final String readSizeParam = "ReadSize";
    private final String cycleParam = "Cycle";
    private final String patternParam = "Pattern";
    private final String repetitionParam = "Repetition";
    private final String delayParam = "Delay";
    private final String displayModeParam = "DisplayMode";
    private final String backColorParam = "BackColor";
    private final String foreColorParam = "ForeColor";
    private final String drawColorParam = "DrawColor";
    private final String backImageParam = "BackImage";
    private final String fontParam = "Font";
    private final String displayForeColorParam = "DisplayForeColor";
    private final String orientationParam = "Orientation";
    private final String scaleMaxParam = "ScaleMax";
    private final String scaleModeParam = "ScaleMode";
    private final String thicknessParam = "Thickness";

    public void stop() {
        this.m_done = true;
        while (this.m_thread.isAlive()) {
        }
        this.m_traffic.stopDataPump();
        this.m_traffic = null;
    }

    public void repaint() {
        switch (this.m_displayMode) {
            case 0: {
                ((DisplayLabel)this.m_display).setRate(this.m_traffic.getDataRate());
                break;
            }
            case 1: {
                ((DisplayMeter)this.m_display).setRate(this.m_traffic.getDataRate());
                break;
            }
            case 2: {
                ((DisplayGauge)this.m_display).setRate(this.m_traffic.getDataRate());
            }
        }
        super.repaint();
        ((Canvas)this.m_display).repaint();
    }

    public String[][] getParameterInfo() {
        String[][] stringArray = new String[][]{{"URL", "String", "URL, like \"http://www.xyz.com/Script.pl\""}, {"SendSize", "Int", "Outbound buffer size in bytes, like \"4096\""}, {"ReadSize", "Int", "Inbound buffer size in bytes, like \"4096\""}, {"Cycle", "Int", "Cycle time, in milliseconds, like \"2000\""}, {"Pattern", "String", "Pattern for Upload & Download, like \"UDUD...\""}, {"Repetition", "Int", "Repetition of pattern, like \"1\""}, {"Delay", "Int", "Delay, in milliseconds, between URL operations, like \"500\""}, {"DisplayMode", "Int", "Display Mode, \"0\"=label, \"1\"=meter, \"2\"=gauge"}, {"BackColor", "Int", "Background color in hex digits, format \"rrggbb\""}, {"ForeColor", "Int", "Foreground color in hex digits, format \"rrggbb\""}, {"DrawColor", "Int", "Drawing color in hex digits, format \"rrggbb\""}, {"BackImage", "String", "Background image, format \"<path>/<gif file>\""}, {"Font", "String", "Font name"}, {"DisplayForeColor", "Int", "Display foreground color, \"0\"=display, \"1\"=nodisplay"}, {"Orientation", "Int", "Orientation, \"0\"=horizontal, \"1\"=vertical"}, {"ScaleMax", "Double", "Maximum throughput in Kbps, like \"56.0\""}, {"ScaleMode", "String", "Scaling factor, \"1\"=Logarithmic\" or \"0\"=Linear"}, {"Thickness", "String", "Drawing thickness in pixels"}};
        return stringArray;
    }

    private double paramToDouble(String string, double d) {
        String string2 = this.getParameter(string);
        if (string2 != null && string2.length() > 0) {
            d = Double.valueOf(string2);
        }
        return d;
    }

    private void initForm() {
        this.m_displayMode = this.paramToInt("DisplayMode", this.m_displayMode);
        switch (this.m_displayMode) {
            case 0: {
                this.initLabel();
                break;
            }
            case 1: {
                this.initMeter();
                break;
            }
            case 2: {
                this.initGauge();
                break;
            }
            default: {
                throw new RuntimeException("U/I Mode not set");
            }
        }
        this.setLayout(new BorderLayout());
        this.add((Component)((Canvas)this.m_display), "Center");
    }

    private Image paramToImage(String string, Image image) {
        String string2 = this.getParameter(string);
        if (string2 != null && string2.length() > 0) {
            image = this.getImage(this.getCodeBase(), string2);
        } else if (this.m_backImageParam != null && this.m_backImageParam.length() > 0) {
            image = this.getImage(this.getCodeBase(), this.m_backImageParam);
        }
        return image;
    }

    private void initGauge() {
        this.m_display = new DisplayGauge();
        DisplayGauge displayGauge = (DisplayGauge)this.m_display;
        displayGauge.setBackground(this.paramToColor("BackColor", this.m_backColorParam));
        displayGauge.setForeground(this.paramToColor("ForeColor", this.m_foreColorParam));
        displayGauge.setDrawColor(this.paramToColor("DrawColor", this.m_drawColorParam));
        displayGauge.setDisplayForeGround(this.paramToInt("DisplayForeColor", this.m_displayForeColorParam));
        displayGauge.setThickness(this.paramToInt("Thickness", this.m_thicknessParam));
        displayGauge.setScaleMax(this.paramToDouble("ScaleMax", this.m_scaleMaxParam));
        displayGauge.setScaleMode(this.paramToInt("ScaleMode", this.m_scaleModeParam));
        Image image = null;
        image = this.paramToImage("BackImage", image);
        if (image != null) {
            displayGauge.setImage(image);
        }
    }

    private Color paramToColor(String string, Color color) {
        String string2 = this.getParameter(string);
        if (string2 != null && string2.length() > 0) {
            int n = Integer.decode("0x" + string2.substring(0, 2));
            int n2 = Integer.decode("0x" + string2.substring(2, 4));
            int n3 = Integer.decode("0x" + string2.substring(4, 6));
            color = new Color(n, n2, n3);
        }
        return color;
    }

    public void start() {
        this.m_done = false;
        this.m_thread = new Thread(this);
        this.m_thread.start();
        this.m_traffic.start();
    }

    public String getAppletInfo() {
        return "Name: Traffic (network bandwidth meter)\r\nCreated with Microsoft Visual J++ Version 1.1";
    }

    private void initPump() {
        this.m_traffic = new FactorData(this.paramToString("URL", this.m_UrlParam));
        this.m_traffic.setSize(this.paramToInt("ReadSize", this.m_ReadSizeParam), this.m_traffic.DOWN);
        this.m_traffic.setSize(this.paramToInt("SendSize", this.m_SendSizeParam), this.m_traffic.UP);
        this.m_traffic.setPattern(this.paramToString("Pattern", this.m_PatternParam));
        this.m_traffic.setRepetition(this.paramToInt("Repetition", this.m_RepetitionParam));
        this.m_traffic.setCycle(this.paramToInt("Cycle", this.m_CycleParam));
        this.m_traffic.setDelay(this.paramToInt("Delay", this.m_DelayParam));
    }

    private void initMeter() {
        this.m_display = new DisplayMeter();
        DisplayMeter displayMeter = (DisplayMeter)this.m_display;
        displayMeter.setBackground(this.paramToColor("BackColor", this.m_backColorParam));
        displayMeter.setForeground(this.paramToColor("ForeColor", this.m_foreColorParam));
        displayMeter.setDrawColor(this.paramToColor("DrawColor", this.m_drawColorParam));
        displayMeter.setDisplayForeGround(this.paramToInt("DisplayForeColor", this.m_displayForeColorParam));
        displayMeter.setThickness(this.paramToInt("Thickness", this.m_thicknessParam));
        displayMeter.setScaleMax(this.paramToDouble("ScaleMax", this.m_scaleMaxParam));
        displayMeter.setScaleMode(this.paramToInt("ScaleMode", this.m_scaleModeParam));
        displayMeter.setOrientation(this.paramToInt("Orientation", this.m_orientationParam));
        Image image = null;
        image = this.paramToImage("BackImage", image);
        if (image != null) {
            displayMeter.setImage(image);
        }
    }

    public void run() {
        try {
            while (!this.m_done) {
                Thread.sleep(1000L);
                this.repaint();
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public void init() {
        this.initForm();
        this.initPump();
    }

    private void initLabel() {
        this.m_display = new DisplayLabel();
        DisplayLabel displayLabel = (DisplayLabel)this.m_display;
        displayLabel.setBackground(this.paramToColor("BackColor", this.m_backColorParam));
        displayLabel.setForeground(this.paramToColor("ForeColor", this.m_foreColorParam));
        displayLabel.setDrawColor(this.paramToColor("DrawColor", this.m_drawColorParam));
        String string = this.paramToString("Font", this.m_fontParam);
        if (string != null) {
            displayLabel.setFont(new Font(string, 0, 12));
        }
        displayLabel.setScaleMax(this.paramToDouble("ScaleMax", this.m_scaleMaxParam));
    }

    private int paramToInt(String string, int n) {
        String string2 = this.getParameter(string);
        if (string2 != null && string2.length() > 0) {
            n = Integer.parseInt(string2);
        }
        return n;
    }

    private String paramToString(String string, String string2) {
        String string3 = this.getParameter(string);
        if (string3 != null && string3.length() > 0) {
            string2 = new String(string3);
        }
        return string2;
    }
}
