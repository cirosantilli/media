/* infoweather.jar — h.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import recovered.InfoWeatherApplet;

class TypeH
implements ActionListener {
    final InfoWeatherApplet a;

    TypeH(InfoWeatherApplet infoWeatherApplet) {
        this.a = infoWeatherApplet;
        this.a.getClass();
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (!this.a.n.isVisible()) {
            ((Component)this.a.n).setVisible(true);
        }
    }
}
