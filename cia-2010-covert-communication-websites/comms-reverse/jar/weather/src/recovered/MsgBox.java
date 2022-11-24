/* infoweather.jar — MsgBox.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import recovered.CloseWindow;

public class MsgBox
extends Dialog
implements ActionListener {
    Button a;

    MsgBox(Frame frame, String string, Point point) {
        super(frame, "Message", true);
        this.addWindowListener(new CloseWindow());
        this.setLayout(new BorderLayout());
        this.add((Component)new Label(string), "Center");
        Panel panel = new Panel();
        panel.setLayout(new FlowLayout());
        this.a = new Button("OK");
        panel.add(this.a);
        this.a.addActionListener(this);
        this.add((Component)panel, "South");
        ((Component)this).setLocation(point);
        this.pack();
        ((Component)this).setVisible(true);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.a) {
            ((Component)this).setVisible(false);
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
                    n3 = 50;
                    break;
                }
                case 1: {
                    n3 = 111;
                    break;
                }
                case 2: {
                    n3 = 97;
                    break;
                }
                case 3: {
                    n3 = 52;
                    break;
                }
                default: {
                    n3 = 79;
                }
            }
            cArray[n4] = (char)(c ^ n3);
            ++n2;
        }
        return new String(cArray);
    }
}
