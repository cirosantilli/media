/* infoweather.jar — a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.Point;
import recovered.DoubleList;
import recovered.MsgBox;
import recovered.TypeB;
import recovered.TypeC;
import recovered.TypeD;
import recovered.TypeE;

class TransferButtons
extends Panel {
    DoubleList a;
    private Button b = new Button(">");
    private Button c = new Button(">>");
    private Button d = new Button("<");
    private Button e = new Button("<<");
    private Font f = new Font("TimesRoman", 1, 10);

    public TransferButtons(DoubleList doubleList) {
        this.a = doubleList;
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        this.setLayout(gridBagLayout);
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.fill = 2;
        gridBagLayout.setConstraints(this.b, gridBagConstraints);
        gridBagLayout.setConstraints(this.c, gridBagConstraints);
        gridBagLayout.setConstraints(this.d, gridBagConstraints);
        gridBagLayout.setConstraints(this.e, gridBagConstraints);
        this.add(this.b);
        this.add(this.c);
        this.add(this.d);
        this.add(this.e);
        this.b.setFont(this.f);
        this.c.setFont(this.f);
        this.d.setFont(this.f);
        this.e.setFont(this.f);
        this.b.addActionListener(new TypeB(this));
        this.d.addActionListener(new TypeC(this));
        this.c.addActionListener(new TypeD(this));
        this.e.addActionListener(new TypeE(this));
    }

    public void displayErrorMsg(String string) {
        Point point = new Point(0, 0);
        try {
            point = this.getLocationOnScreen();
        }
        catch (Exception exception) {}
        MsgBox msgBox = new MsgBox(new Frame(""), string, point);
        this.requestFocus();
        msgBox.dispose();
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
                    n3 = 35;
                    break;
                }
                case 1: {
                    n3 = 10;
                    break;
                }
                case 2: {
                    n3 = 34;
                    break;
                }
                case 3: {
                    n3 = 11;
                    break;
                }
                default: {
                    n3 = 67;
                }
            }
            cArray[n4] = (char)(c ^ n3);
            ++n2;
        }
        return new String(cArray);
    }
}
