/* slides.jar — world/finance/markets/americas/north/e.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.americas.north;

import java.awt.Color;
import java.awt.Font;
import java.io.UnsupportedEncodingException;
import recovered.a.a.b.TypeA;
import recovered.a.a.k.TypeC;
import recovered.org.b.b.TypeG;
import recovered.world.finance.markets.americas.north.TypeB;
import recovered.world.finance.markets.americas.north.TypeD;

public class TypeE
implements recovered.a.a.e.b.a.TypeG {
    public static final String a;
    public static final int b = 1;
    public static final int c = 16;
    public static final String d;
    private byte[] e = new byte[0];
    private recovered.a.a.e.b.a.TypeB f = null;
    private recovered.a.a.e.b.a.TypeD g = null;
    private boolean h = false;
    private int i = 0;
    private byte[] j = new byte[0];
    private static String[] z;

    public TypeE() {
        this(null);
    }

    public TypeE(String string) {
        this(string, (recovered.a.a.e.b.a.TypeD)null, (recovered.a.a.e.b.a.TypeB)null);
    }

    public TypeE(String string, Font font, Color color) {
        this.a__V(string);
        this.a(font);
        this.a(color);
    }

    public TypeE(String string, recovered.a.a.e.b.a.TypeD typeD, recovered.a.a.e.b.a.TypeB typeB) {
        this.a__V(string);
        this.a(typeD);
        this.a(typeB);
    }

    public Object clone() {
        try {
            TypeE typeE = (TypeE)super.clone();
            if (this.a__Ljava_lang_String() != null) {
                typeE.a__V(new String(this.a__Ljava_lang_String()));
            }
            if (this.c__La_a_e_b_a_b() != null) {
                typeE.a((recovered.a.a.e.b.a.TypeB)this.c__La_a_e_b_a_b().clone());
            }
            if (this.e__La_a_e_b_a_d() != null) {
                typeE.a((recovered.a.a.e.b.a.TypeD)this.e__La_a_e_b_a_d().clone());
            }
            return typeE;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            TypeG typeG = recovered.a.a.k.TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
            typeG.e(z[6]);
            return null;
        }
    }

    public recovered.a.a.e.b.a.TypeB c__La_a_e_b_a_b() {
        return this.f;
    }

    public Color b__Ljava_awt_Color() {
        try {
            return ((recovered.a.a.e.b.b.TypeA)this.f.a__La_a_e_b_b_d()).a__Ljava_awt_Color();
        }
        catch (recovered.a.a.e.b.TypeC typeC) {
            return null;
        }
        catch (ClassCastException classCastException) {
            return null;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
    }

    public Font d__Ljava_awt_Font() {
        try {
            return this.g.a__Ljava_awt_Font();
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
    }

    public recovered.a.a.e.b.a.TypeD e__La_a_e_b_a_d() {
        return this.g;
    }

    public String a__Ljava_lang_String() {
        try {
            return new String(this.e, z[4]);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return new String(this.e);
        }
    }

    public void a(Color color) {
        recovered.a.a.e.b.a.TypeB typeB = null;
        try {
            typeB = TypeC.a__La_a_k_c().b__La_a_e_b_a_c().b__La_a_e_b_a_b();
        }
        catch (recovered.a.a.k.TypeA typeA) {
            typeB = new TypeB();
        }
        if (color == null) {
            typeB.b__V(d);
        } else {
            typeB.a(color);
        }
        this.f = typeB;
    }

    public void a(recovered.a.a.e.b.a.TypeB typeB) {
        this.f = typeB;
    }

    public void a(Font font) {
        if (font == null) {
            this.a(a, 1, 16);
        } else {
            this.a(font.getName(), font.getStyle(), font.getSize());
        }
    }

    public void a(String string, int n, int n2) {
        recovered.a.a.e.b.a.TypeD typeD = null;
        try {
            typeD = TypeC.a__La_a_k_c().b__La_a_e_b_a_c().c__La_a_e_b_a_d();
        }
        catch (recovered.a.a.k.TypeA typeA) {
            typeD = new TypeD();
        }
        typeD.a__V(string);
        typeD.a__V(n);
        typeD.b__V(n2);
        this.a(typeD);
    }

    public void a(recovered.a.a.e.b.a.TypeD typeD) {
        this.g = typeD;
    }

    public void a__V(String string) {
        if (string == null) {
            this.e = new byte[0];
            return;
        }
        try {
            this.e = string.getBytes(z[4]);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            this.e = string.getBytes();
        }
    }

    public void a(boolean bl) {
        this.h = bl;
    }

    public boolean f__Z() {
        return this.h;
    }

    public boolean equals(Object object) {
        try {
            TypeE typeE = (TypeE)object;
            return this.a__Ljava_lang_String().equals(typeE.a__Ljava_lang_String()) && this.a(this.b__Ljava_awt_Color(), typeE.b__Ljava_awt_Color()) && this.a(this.d__Ljava_awt_Font(), typeE.d__Ljava_awt_Font());
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    private boolean a(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    public recovered.a.a.e.b.b.TypeD a__La_a_e_b_b_d() throws recovered.a.a.e.b.TypeC {
        recovered.a.a.e.b.b.TypeE typeE = new recovered.a.a.e.b.b.TypeE(this.a__Ljava_lang_String(), this.d__Ljava_awt_Font(), this.b__Ljava_awt_Color());
        typeE.a(this.h);
        return typeE;
    }

    public void a__V(int n) {
        this.i = n;
    }

    public int a__I() {
        int n = this.i;
        if (this.c__La_a_e_b_a_b() != null) {
            n += this.c__La_a_e_b_a_b().a__I();
        }
        if (this.e__La_a_e_b_a_d() != null) {
            n += this.e__La_a_e_b_a_d().a__I();
        }
        return n;
    }

    public int a(byte[] byArray, int n) throws TypeA {
        if (byArray == null) {
            throw new TypeA(z[1]);
        }
        if (n >= byArray.length || n < 0) {
            throw new TypeA(z[0] + n + z[3] + byArray.length);
        }
        int n2 = 0;
        if (this.e__La_a_e_b_a_d() != null) {
            n2 += this.e__La_a_e_b_a_d().a(byArray, n + n2);
        }
        if (this.c__La_a_e_b_a_b() != null) {
            n2 += this.c__La_a_e_b_a_b().a(byArray, n + n2);
        }
        try {
            this.j = recovered.a.a.q.TypeB.a__ArrayB(byArray, n + n2, this.i);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new TypeA(z[2] + typeA.toString());
        }
        return n2 += this.i;
    }

    public int a__I(byte[] byArray) throws TypeA {
        return this.a(byArray, 0);
    }

    public int b(byte[] byArray, int n) throws TypeA {
        if (byArray == null) {
            throw new TypeA(z[1]);
        }
        if (n >= byArray.length || n < 0) {
            throw new TypeA(z[0] + n + z[3] + byArray.length);
        }
        int n2 = 0;
        if (this.e__La_a_e_b_a_d() != null) {
            n2 += this.e__La_a_e_b_a_d().b(byArray, n + n2);
        }
        if (this.c__La_a_e_b_a_b() != null) {
            n2 += this.c__La_a_e_b_a_b().b(byArray, n + n2);
        }
        try {
            System.arraycopy(this.j, 0, byArray, n + n2, this.j.length);
        }
        catch (Exception exception) {
            throw new TypeA(z[5] + exception.toString());
        }
        return n2 += this.j.length;
    }

    public int b__I(byte[] byArray) throws TypeA {
        return this.b(byArray, 0);
    }

    public byte[] a__ArrayB() throws TypeA {
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

    static {
        String[] stringArray = new String[7];
        d = "0x000000";
        a = "Serif";
        stringArray[0] = "Array index out of bounds: ";
        stringArray[1] = "Null data array.";
        stringArray[2] = "The given array is not large enough to fill this object: ";
        stringArray[3] = " is not in the range 0 - ";
        stringArray[4] = "UTF8";
        stringArray[5] = "The given array is not large enough to hold the contents of this object due to: ";
        stringArray[6] = "ConfigurableTextData is not cloneable.";
        z = stringArray;
    }
}
