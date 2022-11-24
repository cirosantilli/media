/* infoweather.jar — c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import recovered.TransferButtons;

class TypeC
implements ActionListener {
    final TransferButtons a;

    TypeC(TransferButtons transferButtons) {
        this.a = transferButtons;
        this.a.getClass();
    }

    public void actionPerformed(ActionEvent actionEvent) {
        this.a.a.moveRighttoLeft();
    }
}
