/* infoweather.jar — d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import recovered.TransferButtons;

class TypeD
implements ActionListener {
    final TransferButtons a;

    TypeD(TransferButtons transferButtons) {
        this.a = transferButtons;
        this.a.getClass();
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (this.a.a.a.getItemCount() + this.a.a.right.getItemCount() < 5) {
            this.a.a.moveAllLeftToRight();
            return;
        }
        this.a.displayErrorMsg("Too many cities! (maximum = " + 5 + ")");
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
                    n3 = 30;
                    break;
                }
                case 1: {
                    n3 = 26;
                    break;
                }
                case 2: {
                    n3 = 46;
                    break;
                }
                case 3: {
                    n3 = 75;
                    break;
                }
                default: {
                    n3 = 68;
                }
            }
            cArray[n4] = (char)(c ^ n3);
            ++n2;
        }
        return new String(cArray);
    }
}
