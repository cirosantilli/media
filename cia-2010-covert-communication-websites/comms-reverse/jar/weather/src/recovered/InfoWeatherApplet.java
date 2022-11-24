/* infoweather.jar — InfoWeatherApplet.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import recovered.Cities;
import recovered.ConstantsMain;
import recovered.Customize;
import recovered.ImageCanvas;
import recovered.Server;
import recovered.TypeH;

public class InfoWeatherApplet
extends Applet
implements Runnable,
MouseListener {
    Thread a = null;
    String[] b = new String[8];
    public boolean SHOWBGIM = true;
    public boolean CUST = false;
    public int DELAY = 7500;
    public String FONTNAME = "Arial";
    public String FONTSTYLE_STR = "normal";
    public int FONTSIZE = 11;
    public int CITY_LOC_X = 5;
    public int CITY_LOC_Y = 20;
    public int STATE_LOC_X = 5;
    public int STATE_LOC_Y = 40;
    public int UPDTIME_LOC_X = 5;
    public int UPDTIME_LOC_Y = 60;
    public int CONDITION_LOC_X = 10;
    public int CONDITION_LOC_Y = 80;
    public int BUTTON_LOC_X = 180;
    public int BUTTON_LOC_Y = 100;
    public String CITYSTATE_FONT_COLOR_STR = "black";
    public String UPDTIME_FONT_COLOR_STR = "black";
    public String CONDITION_FONT_COLOR_STR = "blue";
    public String BGIMGNAME = "bgimage.gif";
    public String BUTTONIMGNAME = "btn.jpg";
    public String TOPBGCOLOR_STR = "gray";
    public int LAYOUT_HGAP = 60;
    public int LAYOUT_VGAP = 15;
    volatile boolean c = false;
    int d = 0;
    int e = 0;
    String f;
    String g;
    String h;
    String i;
    Font j;
    int k;
    Server l;
    Cities m;
    Customize n;
    ConstantsMain o = new ConstantsMain();
    MediaTracker p;
    int q;
    List r = new List();
    List s = new List();
    List t = new List();
    List u = new List();
    Label v;
    Label w;
    Label x;
    Label y;
    Label z;
    Button B;
    Font C;
    Color D;
    Image E = null;
    ImageCanvas F;
    Image G = null;
    Image H = null;
    Image I = null;
    Color J;
    Color K;
    Color L;
    private static Class M;
    public static boolean N;

    public void stop() {
        this.a.stop();
        this.a = null;
    }

    private void a(String string, String string2, String string3, String string4) {
        String string5;
        String string6;
        boolean bl = N;
        int n = this.g.indexOf(44);
        if (n > 0) {
            string6 = string.substring(0, n);
            string5 = string.substring(n + 2);
        } else {
            string6 = string;
            string5 = " ";
        }
        Graphics graphics = this.G.getGraphics();
        graphics.fillRect(0, 0, this.G.getWidth(null), this.G.getHeight(null));
        if (this.H != null) {
            graphics.drawImage(this.H, 0, 0, this.G.getWidth(null), this.G.getHeight(null), this);
            if (this.I != null) {
                graphics.drawImage(this.I, this.BUTTON_LOC_X, this.BUTTON_LOC_Y, this);
            }
        }
        Image image = this.G;
        synchronized (image) {
            graphics.setFont(this.j);
            graphics.setColor(this.J);
            graphics.drawString(string6, this.CITY_LOC_X, this.CITY_LOC_Y);
            graphics.drawString(string5, this.STATE_LOC_X, this.STATE_LOC_Y);
            graphics.setColor(this.K);
            graphics.drawString(string2, this.UPDTIME_LOC_X, this.UPDTIME_LOC_Y);
            graphics.setColor(this.L);
            graphics.drawString(string3 + " " + string4, this.CONDITION_LOC_X, this.CONDITION_LOC_Y);
            Object var10_11 = null;
            return;
        }
    }

    private void a(ImageCanvas imageCanvas, Image image, String string, String string2) {
        String string3;
        String string4;
        int n = string.indexOf(44);
        if (n > 0) {
            string4 = string.substring(0, n);
            string3 = string.substring(n + 2);
        } else {
            string4 = string;
            string3 = " ";
        }
        this.v.setText(string4);
        this.w.setText(string3);
        this.x.setText(string2);
        this.p = new MediaTracker(this);
        imageCanvas.refresh(image, this.p);
    }

    public void initializeApplet(Applet applet) {
        boolean bl = N;
        Class<?> clazz = applet.getClass();
        Field[] fieldArray = clazz.getFields();
        String string = null;
        int n = 0;
        while (n < fieldArray.length) {
            try {
                string = applet.getParameter(fieldArray[n].getName());
                if (string != null) {
                    if (!Modifier.isFinal(fieldArray[n].getModifiers())) {
                        Class<?> clazz2 = fieldArray[n].getType();
                        if (clazz2.equals(Boolean.TYPE)) {
                            fieldArray[n].setBoolean(applet, Boolean.valueOf(string));
                        } else if (clazz2.equals(Byte.TYPE)) {
                            fieldArray[n].setByte(applet, Byte.valueOf(string));
                        } else if (clazz2.equals(Character.TYPE)) {
                            fieldArray[n].setChar(applet, string.charAt(0));
                        } else if (clazz2.equals(Double.TYPE)) {
                            fieldArray[n].setDouble(applet, Double.valueOf(string));
                        } else if (clazz2.equals(Float.TYPE)) {
                            fieldArray[n].setFloat(applet, Float.valueOf(string).floatValue());
                        } else if (clazz2.equals(Integer.TYPE)) {
                            fieldArray[n].setInt(applet, Integer.valueOf(string));
                        } else if (clazz2.equals(Long.TYPE)) {
                            fieldArray[n].setLong(applet, Long.valueOf(string));
                        } else if (clazz2.equals(Short.TYPE)) {
                            fieldArray[n].setShort(applet, Short.valueOf(string));
                        } else if (clazz2.equals(M != null ? M : InfoWeatherApplet.a__Ljava_lang_Class("java.lang.String"))) {
                            fieldArray[n].set(applet, string);
                        }
                    }
                }
            }
            catch (Exception exception) {
                this.displayErrorMsg("ERROR " + exception + " while initializing " + fieldArray[n]);
            }
            ++n;
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        boolean bl = N;
        if (mouseEvent.getX() >= this.BUTTON_LOC_X) {
            if (mouseEvent.getX() <= this.BUTTON_LOC_X + this.I.getWidth(this)) {
                if (mouseEvent.getY() >= this.BUTTON_LOC_Y) {
                    if (mouseEvent.getY() <= this.BUTTON_LOC_Y + this.I.getHeight(this)) {
                        if (!this.n.isVisible()) {
                            ((Component)this.n).setVisible(true);
                        }
                    }
                }
            }
        }
    }

    public void mousePressed(MouseEvent mouseEvent) {
        boolean bl = N;
        if (mouseEvent.getX() >= this.BUTTON_LOC_X) {
            if (mouseEvent.getX() <= this.BUTTON_LOC_X + this.I.getWidth(this)) {
                if (mouseEvent.getY() >= this.BUTTON_LOC_Y) {
                    if (mouseEvent.getY() <= this.BUTTON_LOC_Y + this.I.getHeight(this)) {
                        if (!this.n.isVisible()) {
                            ((Component)this.n).setVisible(true);
                        }
                    }
                }
            }
        }
    }

    public void paint(Graphics graphics) {
        if (this.G == null) {
            return;
        }
        Image image = this.G;
        synchronized (image) {
            graphics.drawImage(this.G, 0, 0, null);
            Object var3_3 = null;
            return;
        }
    }

    public void mouseReleased(MouseEvent mouseEvent) {
    }

    public Color parseColorName(String string) {
        boolean bl = N;
        if ("red".equals(string)) {
            return Color.red;
        }
        if ("green".equals(string)) {
            return Color.green;
        }
        if ("blue".equals(string)) {
            return Color.blue;
        }
        if ("yellow".equals(string)) {
            return Color.yellow;
        }
        if ("orange".equals(string)) {
            return Color.orange;
        }
        if ("white".equals(string)) {
            return Color.white;
        }
        if ("lightGray".equals(string)) {
            return Color.lightGray;
        }
        if ("gray".equals(string)) {
            return Color.gray;
        }
        if ("darkGray".equals(string)) {
            return Color.darkGray;
        }
        if ("black".equals(string)) {
            return Color.black;
        }
        if ("cyan".equals(string)) {
            return Color.cyan;
        }
        if ("magenta".equals(string)) {
            return Color.magenta;
        }
        if ("pink".equals(string)) {
            return Color.pink;
        }
        return Color.black;
    }

    private void a() {
        boolean bl = N;
        int n = this.s.getItemCount();
        this.showStatus("Loading new cities ......");
        if (this.SHOWBGIM) {
            this.g = "Please Wait";
            this.h = " ";
            this.i = "Loading new cities...  ";
            this.f = " ";
            this.a(this.g, this.h, this.i, this.f);
            this.repaint();
        } else {
            String string = "images/" + "bgimage.gif";
            this.E = this.getImage(this.getDocumentBase(), string);
            this.a(this.F, this.E, "Please Wait ", " ");
            this.v.setBackground(Color.red);
            this.w.setBackground(Color.red);
            this.x.setBackground(Color.red);
            this.z.setText("Loading new cities... ");
            this.y.setText(" ");
        }
        if (n > 5) {
            n = 5;
        }
        String string = this.s.getItem(0).toString();
        String string2 = this.t.getItem(0).toString();
        String string3 = this.u.getItem(0).toString();
        int n2 = 1;
        while (n2 < n) {
            string = string + ":" + this.s.getItem(n2).toString();
            string2 = string2 + ":" + this.t.getItem(n2).toString();
            string3 = string3 + ":" + this.u.getItem(n2).toString();
            ++n2;
        }
        String string4 = this.l.setCities(string, string2, string3);
        if (string4.startsWith("ERROR")) {
            if (this.SHOWBGIM) {
                this.g = "Error .... ";
                this.h = "Weather information unavailable ";
                this.i = " ";
                this.f = "Defaulting to previous cities ";
                this.a(this.g, this.h, this.i, this.f);
                this.repaint();
            } else {
                this.v.setText("Error...");
                this.w.setText("Weather information unavailable ");
                this.x.setText(" ");
                this.z.setText("Defaulting to previous cities ");
                this.y.setText(" ");
            }
            try {
                n2 = this.DELAY + 4000;
                Thread.sleep(n2);
            }
            catch (Exception exception) {
            }
        } else {
            if (this.r.getItemCount() > 0) {
                this.r.removeAll();
            }
            boolean bl2 = false;
            this.q = 0;
            int n3 = 0;
            while (!bl2) {
                String string5;
                int n4 = string4.indexOf("|", n3);
                if (n4 > 0) {
                    string5 = string4.substring(n3, n4);
                    n3 = n4 + 1;
                } else {
                    string5 = string4.substring(n3);
                    bl2 = true;
                }
                this.r.add(string5);
                ++this.q;
            }
            this.m.accessCities(false, 0, this.q, this.r);
            this.d = this.q;
        }
        this.e = 0;
        this.showStatus("Done");
        if (!this.SHOWBGIM) {
            this.v.setBackground(this.D);
            this.w.setBackground(this.D);
            this.x.setBackground(this.D);
            this.v.setText(" ");
            this.w.setText(" ");
            this.x.setText(" ");
            this.z.setText(" ");
            this.y.setText(" ");
        }
        this.c = false;
    }

    public void update(Graphics graphics) {
        this.paint(graphics);
    }

    public void mouseEntered(MouseEvent mouseEvent) {
    }

    public void mouseExited(MouseEvent mouseEvent) {
    }

    public void displayErrorMsg(String string) {
        Point point = new Point(0, 0);
        try {
            point = this.getLocationOnScreen();
        }
        catch (Exception exception) {}
        System.out.println(string);
    }

    public void start() {
        if (this.a == null) {
            this.a = new Thread(this);
        }
        this.a.start();
    }

    public void restart(List list, List list2, List list3) {
        boolean bl = N;
        int n = list.getItemCount();
        if (n > 0) {
            this.s.removeAll();
            this.t.removeAll();
            this.u.removeAll();
            int n2 = 0;
            while (n2 < n) {
                this.s.add(list.getItem(n2), n2);
                this.t.add(list2.getItem(n2), n2);
                this.u.add(list3.getItem(n2), n2);
                ++n2;
            }
            this.c = true;
        }
    }

    private static Class a__Ljava_lang_Class(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    /*
     * Handled impossible loop by duplicating code
     * Handled impossible loop by adding 'first' condition
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void run() {
        boolean bl = N;
        this.c = false;
    }

    public void init() {
        Object object;
        boolean bl = N;
        this.b[0] = "wind";
        this.b[1] = "visibility";
        this.b[2] = "sky";
        this.b[3] = "condition";
        this.b[4] = "temperature";
        this.b[5] = "dewpoint";
        this.b[6] = "humidity";
        this.b[7] = "pressure";
        this.initializeApplet(this);
        this.k = this.FONTSTYLE_STR.equals("bold") ? 1 : (this.FONTSTYLE_STR.equals("italic") ? 2 : (this.FONTSTYLE_STR.equals("bolditalic") ? 3 : (this.FONTSTYLE_STR.equals("normal") ? 0 : 1)));
        this.j = new Font(this.FONTNAME, this.k, this.FONTSIZE);
        if (this.SHOWBGIM) {
            this.setLayout(null);
            this.G = this.createImage(this.getSize().width, this.getSize().height);
            try {
                this.p = new MediaTracker(this);
                object = "images/" + this.BGIMGNAME;
                this.H = this.getImage(this.getDocumentBase(), (String)object);
                this.p.addImage(this.H, 0);
                if (this.CUST) {
                    String string = "images/" + this.BUTTONIMGNAME;
                    this.I = this.getImage(this.getDocumentBase(), string);
                    this.p.addImage(this.I, 1);
                }
                this.p.waitForAll();
            }
            catch (Exception exception) {
                this.displayErrorMsg("ERROR 100: Cannot Load Background or Customize Image");
            }
            this.addMouseListener(this);
            this.J = this.parseColorName(this.CITYSTATE_FONT_COLOR_STR);
            this.K = this.parseColorName(this.UPDTIME_FONT_COLOR_STR);
            this.L = this.parseColorName(this.CONDITION_FONT_COLOR_STR);
        } else {
            this.D = this.parseColorName(this.TOPBGCOLOR_STR);
            this.setLayout(new BorderLayout());
            this.z = new Label("");
            this.y = new Label("");
            this.z.setFont(this.j);
            this.y.setFont(this.j);
            this.F = new ImageCanvas();
            object = new Panel();
            ((Container)object).setLayout(new BorderLayout(60, 0));
            ((Container)object).add((Component)this.F, "West");
            this.add((Component)object, "North");
            Panel panel = new Panel(new GridLayout(3, 1));
            if (this.CUST) {
                this.v = new Label("Please Wait...  Loading cities", 1);
                this.w = new Label(" ", 1);
                this.x = new Label(" ", 1);
                this.v.setBackground(this.D);
                this.w.setBackground(this.D);
                this.x.setBackground(this.D);
                this.v.setFont(this.j);
                this.w.setFont(this.j);
                this.x.setFont(this.j);
                panel.add(this.v);
                panel.add(this.w);
                panel.add(this.x);
                this.C = new Font(this.FONTNAME, this.k, this.FONTSIZE - 2);
                this.B = new Button("Cities");
                this.B.setFont(this.C);
                Panel panel2 = new Panel();
                panel2.add((Component)this.B, "Center");
                this.add((Component)panel2, "South");
                Panel panel3 = new Panel(new GridLayout(2, 1));
                panel3.add(this.y);
                panel3.add(this.z);
                ((Container)object).add((Component)panel3, "Center");
                TypeH typeH = new TypeH(this);
                this.B.addActionListener(typeH);
            } else {
                this.v = new Label("Please Wait...  Loading cities", 1);
                this.w = new Label(" ", 1);
                this.x = new Label(" ", 1);
                this.v.setBackground(this.D);
                this.w.setBackground(this.D);
                this.x.setBackground(this.D);
                this.v.setFont(this.j);
                this.w.setFont(this.j);
                this.x.setFont(this.j);
                panel.add(this.v);
                panel.add(this.w);
                panel.add(this.x);
                Panel panel4 = new Panel(new GridLayout(2, 1));
                panel4.add(this.y);
                panel4.add(this.z);
                ((Container)object).add((Component)panel4, "Center");
            }
            this.add((Component)panel, "Center");
        }
        this.m = new Cities();
        this.n = new Customize("Cities");
        this.n.setParent(this);
        this.l = new Server(this.getCodeBase() + "cgi-bin/infoweather.cgi");
        object = this.l.getCities();
        if (object == null) {
            this.d = this.l.initialList.getItemCount();
            if (this.d > 0) {
                this.m.accessCities(false, 0, this.d, this.l.initialList);
                return;
            }
        } else {
            this.displayErrorMsg((String)object);
        }
    }

    private static String b(String string) {
        char[] cArray = string.toCharArray();
        int n = cArray.length;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int n4 = n2;
            char c = cArray[n4];
            switch (n2 % 5) {
                case 0: {
                    n3 = 68;
                    break;
                }
                case 1: {
                    n3 = 76;
                    break;
                }
                case 2: {
                    n3 = 103;
                    break;
                }
                case 3: {
                    n3 = 54;
                    break;
                }
                default: {
                    n3 = 6;
                }
            }
            cArray[n4] = (char)(c ^ n3);
            ++n2;
        }
        return new String(cArray);
    }
}
