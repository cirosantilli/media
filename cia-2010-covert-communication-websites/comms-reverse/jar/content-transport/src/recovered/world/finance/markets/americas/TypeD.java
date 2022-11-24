/* slides.jar — world/finance/markets/americas/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.americas;

import recovered.a.a.b.TypeA;
import recovered.a.a.e.b.a.TypeE;
import recovered.a.a.e.c.TypeH;
import recovered.a.a.q.TypeB;
import recovered.world.finance.markets.americas.TypeC;

public class TypeD
extends TypeC
implements recovered.a.a.e.a.TypeC {
    private String i = null;
    private boolean j = false;
    private int k = 0;
    private byte[] l = new byte[0];
    private static String[] z = new String[]{"Super class ConfigurableToggleImage.getData().translate() should be returning ImageData objects, but is not.", "Unable to correctly translate the ImageData.", "The given array is not large enough to hold the contents of this object due to: ", "The given array is not large enough to fill this object: "};

    public TypeD() {
        this((TypeE)null, (String)null);
    }

    public TypeD(TypeE typeE, String string) {
        this(typeE, null, string);
    }

    public TypeD(TypeE typeE, TypeE typeE2, String string) {
        super(typeE, typeE2, true, false);
        this.a__V(string);
    }

    public String a__Ljava_lang_String() {
        return this.i;
    }

    public void a__V(String string) {
        this.i = string;
    }

    public TypeH a__La_a_e_c_h() throws recovered.a.a.e.TypeD {
        recovered.a.a.e.b.b.TypeC typeC = null;
        recovered.a.a.e.b.b.TypeC typeC2 = null;
        try {
            if (super.a__La_a_e_b_a_e() != null) {
                typeC = (recovered.a.a.e.b.b.TypeC)super.a__La_a_e_b_a_e().a__La_a_e_b_b_d();
            }
            if (super.b__La_a_e_b_a_e() != null) {
                typeC2 = (recovered.a.a.e.b.b.TypeC)super.b__La_a_e_b_a_e().a__La_a_e_b_b_d();
            }
        }
        catch (recovered.a.a.e.b.TypeC typeC3) {
            throw new recovered.a.a.e.TypeD(z[1], typeC3);
        }
        catch (ClassCastException classCastException) {
            throw new recovered.a.a.e.TypeD(z[0]);
        }
        recovered.a.a.e.c.TypeC typeC4 = null;
        if (typeC2 != null) {
            typeC4 = new recovered.a.a.e.c.TypeC(typeC, typeC2, this.a__Ljava_lang_String());
        } else if (typeC != null) {
            typeC4 = new recovered.a.a.e.c.TypeC(typeC, this.a__Ljava_lang_String());
        } else {
            typeC4 = new recovered.a.a.e.c.TypeC();
            typeC4.a__V(this.a__Ljava_lang_String());
        }
        return typeC4;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            TypeD typeD = (TypeD)object;
            return super.equals(typeD) && (this.a__Ljava_lang_String() == null && typeD.a__Ljava_lang_String() == null || this.a__Ljava_lang_String().equals(typeD.a__Ljava_lang_String()));
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public void a__V(int n) {
        this.k = n;
    }

    public int a__I() {
        int n = super.a__I() + this.k;
        return n;
    }

    public int a(byte[] byArray, int n) throws TypeA {
        int n2 = super.a(byArray, n);
        try {
            this.l = TypeB.a__ArrayB(byArray, n + n2, this.k);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new TypeA(z[3] + typeA.toString());
        }
        return n2 += this.k;
    }

    public int b(byte[] byArray, int n) throws TypeA {
        int n2 = super.b(byArray, n);
        try {
            System.arraycopy(this.l, 0, byArray, n + n2, this.l.length);
        }
        catch (Exception exception) {
            throw new TypeA(z[2] + exception.toString());
        }
        return n2 += this.l.length;
    }

    public Object clone() {
        TypeD typeD = (TypeD)super.clone();
        if (this.a__Ljava_lang_String() != null) {
            typeD.a__V(new String(this.a__Ljava_lang_String()));
        }
        return typeD;
    }
}
