/* infoweather.jar — Cities.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.awt.List;
import java.util.Vector;

public class Cities {
    private Vector a = new Vector(20, 5);
    private String b;

    private String a__Ljava_lang_String(int n) {
        return this.a.elementAt(n).toString();
    }

    private void a(int n, List list) {
        this.a.removeAllElements();
        int n2 = 0;
        while (n2 < n) {
            String string = list.getItem(n2).toString();
            this.a.insertElementAt(string, n2);
            ++n2;
        }
    }

    public synchronized String accessCities(boolean bl, int n, int n2, List list) {
        if (bl) {
            this.b = this.a__Ljava_lang_String(n);
        } else {
            this.b = null;
            this.a(n2, list);
        }
        this.notifyAll();
        return this.b;
    }
}
