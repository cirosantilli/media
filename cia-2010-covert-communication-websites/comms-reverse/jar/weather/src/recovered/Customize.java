/* infoweather.jar — Customize.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import recovered.CloseWindow;
import recovered.ConstantsLoc;
import recovered.DoubleList;
import recovered.InfoWeatherApplet;
import recovered.TypeF;

public class Customize
extends Frame
implements ItemListener {
    private List a = new List(8, false);
    private List b = new List();
    private List c = new List();
    private CheckboxGroup d;
    private Checkbox e;
    private Checkbox f;
    Button g;
    Button h;
    Button i;
    public DoubleList dblList;
    private int j;
    private ConstantsLoc k = new ConstantsLoc();
    InfoWeatherApplet l;

    public Customize(String string) {
        super(string);
        ((Component)this).setSize(550, 350);
        this.addWindowListener(new CloseWindow());
        this.setLayout(new BorderLayout(10, 10));
        this.d = new CheckboxGroup();
        this.e = new Checkbox("Select US", this.d, true);
        this.e.addItemListener(this);
        this.f = new Checkbox("Select International", this.d, false);
        this.f.addItemListener(this);
        Panel panel = new Panel();
        panel.setLayout(new BorderLayout());
        panel.add((Component)this.e, "West");
        panel.add((Component)this.f, "Center");
        panel.add((Component)this.a, "South");
        this.a.addItemListener(this);
        this.add((Component)panel, "North");
        this.add((Component)new Label("Cities"), "West");
        this.add((Component)new Label("Selected Cities"), "East");
        this.dblList = new DoubleList();
        this.add((Component)this.dblList, "Center");
        Panel panel2 = new Panel();
        this.g = new Button("  OK  ");
        this.h = new Button("Cancel");
        this.i = new Button("Remove All Selected Cities");
        panel2.add(this.g);
        panel2.add(this.h);
        panel2.add(this.i);
        this.add((Component)panel2, "South");
        this.h.addActionListener(new TypeF(this));
        this.g.addActionListener(new TypeF(this));
        this.i.addActionListener(new TypeF(this));
        this.addItems(this.a, ConstantsLoc.a);
    }

    public void itemStateChanged(ItemEvent itemEvent) {
        boolean bl = InfoWeatherApplet.N;
        if (itemEvent.getSource() == this.e) {
            this.dblList.emptyLeftList();
            this.a.removeAll();
            this.addItems(this.a, ConstantsLoc.a);
            return;
        }
        if (itemEvent.getSource() == this.f) {
            this.dblList.emptyLeftList();
            this.a.removeAll();
            this.addItems(this.a, ConstantsLoc.b);
            return;
        }
        if (itemEvent.getSource() == this.a) {
            int n = this.a.getSelectedIndex();
            String string = this.a.getSelectedItem();
            if (this.e.getState()) {
                this.dblList.fillDoubleList(ConstantsLoc.c[n], string);
                return;
            }
            if (this.f.getState()) {
                this.dblList.fillDoubleList(ConstantsLoc.d[n], string);
            }
        }
    }

    public void setParent(InfoWeatherApplet infoWeatherApplet) {
        this.l = infoWeatherApplet;
    }

    public void AddCity(String string, String string2, String string3) {
        this.dblList.rightCode.add(string);
        this.dblList.right.add(string2);
        this.dblList.rightCountry.add(string3);
    }

    public void addItems(List list, String[][] stringArray) {
        int n = 0;
        while (n < stringArray.length) {
            list.add(stringArray[n][0]);
            ++n;
        }
    }

    private static String a__Ljava_lang_String(String string) {
        char[] cArray = string.toCharArray();
        int n = cArray.length;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int n4 = n2;
            char c = cArray[n4];
            switch (n2 % 5) {
                case 0: {
                    n3 = 75;
                    break;
                }
                case 1: {
                    n3 = 30;
                    break;
                }
                case 2: {
                    n3 = 98;
                    break;
                }
                case 3: {
                    n3 = 27;
                    break;
                }
                default: {
                    n3 = 66;
                }
            }
            cArray[n4] = (char)(c ^ n3);
            ++n2;
        }
        return new String(cArray);
    }
}
