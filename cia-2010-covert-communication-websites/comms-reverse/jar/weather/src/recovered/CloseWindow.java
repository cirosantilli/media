/* infoweather.jar — CloseWindow.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CloseWindow
extends WindowAdapter {
    public void windowClosing(WindowEvent windowEvent) {
        ((Component)windowEvent.getWindow()).setVisible(false);
    }
}
