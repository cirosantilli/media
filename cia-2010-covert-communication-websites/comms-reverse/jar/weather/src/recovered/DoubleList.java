/* infoweather.jar — DoubleList.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.List;
import java.awt.Panel;
import recovered.InfoWeatherApplet;
import recovered.TransferButtons;

public class DoubleList
extends Panel {
    public List a = new List();
    private List b = new List();
    private List c = new List();
    public List right = new List();
    public List rightCode = new List();
    public List rightCountry = new List();
    private Panel d = new TransferButtons(this);
    private int e;
    public static int f;

    public DoubleList() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        this.setLayout(gridBagLayout);
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagLayout.setConstraints(this.a, gridBagConstraints);
        gridBagConstraints.fill = 3;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 1.0;
        gridBagLayout.setConstraints(this.d, gridBagConstraints);
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagLayout.setConstraints(this.right, gridBagConstraints);
        this.add(this.a);
        this.add(this.d);
        this.add(this.right);
    }

    public void moveAllLeftToRight() {
        boolean bl = InfoWeatherApplet.N;
        int n = this.a.getItemCount();
        int n2 = 0;
        while (n2 < n) {
            this.right.add(this.a.getItem(n2), n2);
            this.rightCode.add(this.b.getItem(n2), n2);
            this.rightCountry.add(this.c.getItem(n2), n2);
            ++n2;
        }
        this.a.removeAll();
        this.b.removeAll();
        this.c.removeAll();
    }

    public void moveAllRightToLeft() {
        boolean bl = InfoWeatherApplet.N;
        int n = this.right.getItemCount();
        int n2 = 0;
        while (n2 < n) {
            this.a.add(this.right.getItem(n2), n2);
            this.b.add(this.rightCode.getItem(n2), n2);
            this.c.add(this.rightCountry.getItem(n2), n2);
            ++n2;
        }
        this.right.removeAll();
        this.rightCode.removeAll();
        this.rightCountry.removeAll();
    }

    public void moveLeftToRight() {
        int n = this.a.getSelectedIndex();
        if (n >= 0) {
            String string = this.a.getSelectedItem();
            String string2 = this.b.getItem(n);
            String string3 = this.c.getItem(n);
            this.a.remove(n);
            this.b.remove(n);
            this.c.remove(n);
            this.right.add(string, 0);
            this.rightCode.add(string2, 0);
            this.rightCountry.add(string3, 0);
            this.right.select(0);
        }
    }

    public void fillDoubleList(String[] stringArray, String string) {
        this.a.removeAll();
        this.b.removeAll();
        this.c.removeAll();
        int n = 1;
        while (n < stringArray.length) {
            this.e = stringArray[n].indexOf("|");
            this.a.add(stringArray[n].substring(this.e + 1));
            this.b.add(stringArray[n].substring(0, this.e));
            this.c.add(string);
            ++n;
        }
    }

    public void emptyLeftList() {
        this.a.removeAll();
        this.b.removeAll();
        this.c.removeAll();
    }

    public void emptyRightList() {
        this.right.removeAll();
        this.rightCode.removeAll();
        this.rightCountry.removeAll();
    }

    public void moveRighttoLeft() {
        int n = this.right.getSelectedIndex();
        if (n >= 0) {
            String string = this.right.getSelectedItem();
            String string2 = this.rightCode.getItem(n);
            String string3 = this.rightCountry.getItem(n);
            this.right.remove(n);
            this.rightCode.remove(n);
            this.rightCountry.remove(n);
            this.a.add(string, 0);
            this.b.add(string2, 0);
            this.c.add(string3, 0);
            this.a.select(0);
        }
    }
}
