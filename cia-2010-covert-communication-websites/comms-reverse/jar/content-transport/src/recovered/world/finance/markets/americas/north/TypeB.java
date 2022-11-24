/* slides.jar — world/finance/markets/americas/north/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.americas.north;

import java.awt.Color;
import recovered.a.a.e.b.TypeC;
import recovered.a.a.e.b.b.TypeA;
import recovered.a.a.e.b.b.TypeD;
import recovered.org.b.b.TypeG;

public class TypeB
implements recovered.a.a.e.b.a.TypeB {
    private int a = -1;
    private int b = -1;
    private int c = -1;
    private String d = null;
    private String e = null;
    private int f = 0;
    private byte[] g = new byte[0];
    private static String[] z = new String[]{"Cannot clone ConfigurableColorDefinition: ", " is not in the range 0 - ", "Null data array.", "Array index out of bounds: ", "The given array is not large enough to fill this object: ", "Could not generate an ColorData from this ConfigurableColorDefinition", "yellow", "magenta", "black", "lightgray", "darkgray", "green", "orange", "red", "cyan", "white", "gray", "pink", "blue", "The given array is not large enough to hold the contents of this object due to: "};

    public void a(int n, int n2, int n3) {
        this.a(n, n2, n3, null, null);
    }

    public void a__V(String string) {
        this.a(-1, -1, -1, string, null);
    }

    public void b__V(String string) {
        this.a(-1, -1, -1, null, string);
    }

    public void a(Color color) {
        this.a(color.getRed(), color.getGreen(), color.getBlue(), null, null);
    }

    public int b__I() {
        return this.a;
    }

    public int c__I() {
        return this.b;
    }

    public int d__I() {
        return this.c;
    }

    public String e__Ljava_lang_String() {
        return this.d;
    }

    public String f__Ljava_lang_String() {
        return this.e;
    }

    public Object clone() {
        try {
            TypeB typeB = (TypeB)super.clone();
            if (this.e__Ljava_lang_String() != null) {
                typeB.a__V(new String(this.e__Ljava_lang_String()));
            }
            if (this.f__Ljava_lang_String() != null) {
                typeB.b__V(new String(this.f__Ljava_lang_String()));
            }
            return typeB;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            TypeG typeG = recovered.a.a.k.TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
            typeG.e(z[0], cloneNotSupportedException);
            return null;
        }
    }

    private void a(int n, int n2, int n3, String string, String string2) {
        this.a = n;
        this.b = n2;
        this.c = n3;
        this.d = string;
        this.e = string2;
    }

    public void a__V(int n) {
        this.f = n;
    }

    public int a__I() {
        return this.f;
    }

    public TypeD a__La_a_e_b_b_d() throws TypeC {
        Color color = null;
        if (this.d != null) {
            color = TypeB.a__Ljava_awt_Color(this.d);
        } else if (this.e != null) {
            try {
                color = Color.decode(this.e);
            }
            catch (NumberFormatException numberFormatException) {}
        } else if (TypeB.b__Z(this.a) && TypeB.b__Z(this.b) && TypeB.b__Z(this.c)) {
            color = new Color(this.a, this.b, this.c);
        }
        if (color == null) {
            throw new TypeC(z[5]);
        }
        return new TypeA(color);
    }

    private static boolean b__Z(int n) {
        return n < 256 && n > -1;
    }

    private static Color a__Ljava_awt_Color(String string) {
        Color color = null;
        if (string.equalsIgnoreCase(z[13])) {
            color = Color.red;
        } else if (string.equalsIgnoreCase(z[18])) {
            color = Color.blue;
        } else if (string.equalsIgnoreCase(z[11])) {
            color = Color.green;
        } else if (string.equalsIgnoreCase(z[8])) {
            color = Color.black;
        } else if (string.equalsIgnoreCase(z[15])) {
            color = Color.white;
        } else if (string.equalsIgnoreCase(z[6])) {
            color = Color.yellow;
        } else if (string.equalsIgnoreCase(z[17])) {
            color = Color.pink;
        } else if (string.equalsIgnoreCase(z[12])) {
            color = Color.orange;
        } else if (string.equalsIgnoreCase(z[7])) {
            color = Color.magenta;
        } else if (string.equalsIgnoreCase(z[9])) {
            color = Color.lightGray;
        } else if (string.equalsIgnoreCase(z[16])) {
            color = Color.gray;
        } else if (string.equalsIgnoreCase(z[10])) {
            color = Color.darkGray;
        } else if (string.equalsIgnoreCase(z[14])) {
            color = Color.cyan;
        }
        return color;
    }

    public int a(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        if (byArray == null) {
            throw new recovered.a.a.b.TypeA(z[2]);
        }
        if (n >= byArray.length || n < 0) {
            throw new recovered.a.a.b.TypeA(z[3] + n + z[1] + byArray.length);
        }
        int n2 = 0;
        try {
            this.g = recovered.a.a.q.TypeB.a__ArrayB(byArray, n + n2, this.f);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new recovered.a.a.b.TypeA(z[4] + typeA.toString());
        }
        return n2 += this.f;
    }

    public int a__I(byte[] byArray) throws recovered.a.a.b.TypeA {
        return this.a(byArray, 0);
    }

    public int b(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        if (byArray == null) {
            throw new recovered.a.a.b.TypeA(z[2]);
        }
        if (n >= byArray.length || n < 0) {
            throw new recovered.a.a.b.TypeA(z[3] + n + z[1] + byArray.length);
        }
        int n2 = 0;
        try {
            System.arraycopy(this.g, 0, byArray, n + n2, this.g.length);
        }
        catch (Exception exception) {
            throw new recovered.a.a.b.TypeA(z[19] + exception.toString());
        }
        return n2 += this.g.length;
    }

    public int b__I(byte[] byArray) throws recovered.a.a.b.TypeA {
        return this.b(byArray, 0);
    }

    public byte[] a__ArrayB() throws recovered.a.a.b.TypeA {
        int n = this.a__I();
        if (n == 0) {
            return new byte[0];
        }
        byte[] byArray = new byte[n];
        int n2 = this.b(byArray, 0);
        if (n2 < byArray.length) {
            byte[] byArray2 = new byte[n2];
            System.arraycopy(byArray, 0, byArray2, 0, n2);
            byArray = byArray2;
        }
        return byArray;
    }
}
