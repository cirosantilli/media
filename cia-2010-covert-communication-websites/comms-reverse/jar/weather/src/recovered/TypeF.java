/* infoweather.jar — f.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import recovered.Customize;
import recovered.InfoWeatherApplet;

class TypeF
implements ActionListener {
    final Customize a;

    TypeF(Customize customize) {
        this.a = customize;
        this.a.getClass();
    }

    public void actionPerformed(ActionEvent actionEvent) {
        boolean bl = InfoWeatherApplet.N;
        if (actionEvent.getSource() == this.a.g) {
            this.a.dispose();
            this.a.l.restart(this.a.dblList.rightCode, this.a.dblList.right, this.a.dblList.rightCountry);
            return;
        }
        if (actionEvent.getSource() == this.a.h) {
            this.a.dispose();
            return;
        }
        if (actionEvent.getSource() == this.a.i) {
            this.a.dblList.emptyRightList();
        }
    }
}
