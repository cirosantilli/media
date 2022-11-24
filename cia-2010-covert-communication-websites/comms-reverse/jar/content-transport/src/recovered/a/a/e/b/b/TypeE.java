/* slides.jar — a/a/e/b/b/e.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.e.b.b;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Vector;
import recovered.a.a.e.b.b.TypeD;

public class TypeE
implements TypeD {
    private static final Font e;
    private static final Color f;
    private String[] g = new String[]{" "};
    private Dimension[] h;
    private Font i = e;
    private Color j = f;
    private int k = 0;
    private boolean l = false;
    private static String[] z;

    public TypeE() {
        this((String[])null, null, null, 0);
    }

    public TypeE(String string, Font font, Color color) {
        this(string, font, color, 0);
    }

    public TypeE(String string, Font font, Color color, int n) {
        this.a__V(string);
        this.a(font);
        this.a(color);
        this.a__V(n);
    }

    public TypeE(String[] stringArray, Font font, Color color) {
        this(stringArray, font, color, 0);
    }

    public TypeE(String[] stringArray, Font font, Color color, int n) {
        this.a__V(stringArray);
        this.a(font);
        this.a(color);
        this.a__V(n);
    }

    public String[] a__ArrayLjava_lang_String() {
        return this.g;
    }

    public void a__V(String string) {
        this.g = string != null ? this.b__ArrayLjava_lang_String(string) : this.b__ArrayLjava_lang_String(" ");
    }

    public void a__V(String[] stringArray) {
        this.g = stringArray != null ? stringArray : new String[]{" "};
    }

    public Font b__Ljava_awt_Font() {
        return this.i;
    }

    public void a(Font font) {
        this.i = font != null ? font : e;
    }

    public Color c__Ljava_awt_Color() {
        return this.j;
    }

    public void a(Color color) {
        this.j = color != null ? color : f;
    }

    public int d__I() {
        return this.k;
    }

    public void a__V(int n) {
        this.k = n;
    }

    public void a(boolean bl) {
        this.l = bl;
    }

    public boolean e__Z() {
        return this.l;
    }

    private String[] b__ArrayLjava_lang_String(String string) {
        Vector<String> vector = new Vector<String>();
        boolean bl = false;
        int n = 0;
        while (!bl) {
            String string2;
            int n2 = string.indexOf(z[0], n);
            if (n2 >= 0) {
                string2 = string.substring(n, n2);
                vector.addElement(string2);
                n = n2 + z[0].length();
                continue;
            }
            string2 = string.substring(n, string.length());
            vector.addElement(string2);
            bl = true;
        }
        String[] stringArray = new String[vector.size()];
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = (String)vector.elementAt(i);
        }
        return stringArray;
    }

    public Dimension[] a(FontMetrics fontMetrics) {
        if (this.h == null) {
            return this.b(fontMetrics);
        }
        return this.h;
    }

    public Dimension[] b(FontMetrics fontMetrics) {
        int n;
        Vector<Dimension> vector = new Vector<Dimension>();
        int n2 = 0;
        if (this.i != null) {
            n2 = fontMetrics.getLeading() + fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent();
        }
        for (int i = 0; i < this.g.length; ++i) {
            n = 0;
            n = this.g[i] != null && this.i != null ? fontMetrics.stringWidth(this.g[i]) : 0;
            vector.addElement(new Dimension(n, n2));
        }
        Dimension[] dimensionArray = new Dimension[vector.size()];
        for (n = 0; n < dimensionArray.length; ++n) {
            dimensionArray[n] = (Dimension)vector.elementAt(n);
        }
        this.h = dimensionArray;
        return this.h;
    }

    static {
        z = new String[]{"\\n", "Serif"};
        e = new Font(z[1], 1, 16);
        f = new Color(0, 0, 0);
    }
}
