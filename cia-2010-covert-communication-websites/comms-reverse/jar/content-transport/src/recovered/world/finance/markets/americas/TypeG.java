/* slides.jar — world/finance/markets/americas/g.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.americas;

import recovered.a.a.b.TypeA;
import recovered.a.a.e.TypeD;
import recovered.a.a.e.b.TypeC;
import recovered.a.a.e.b.b.TypeE;
import recovered.a.a.e.c.TypeH;
import recovered.a.a.k.TypeB;
import recovered.world.finance.markets.americas.TypeF;

public class TypeG
extends TypeF
implements recovered.a.a.e.a.TypeD {
    private String i = null;
    public static final String j;
    private int k = 0;
    private byte[] l = new byte[0];
    private static String[] z;

    public TypeG() {
        this((recovered.a.a.e.b.a.TypeG)null, (String)null);
    }

    public TypeG(recovered.a.a.e.b.a.TypeG typeG, String string) {
        super.a(typeG);
        if (typeG != null) {
            try {
                recovered.a.a.e.b.a.TypeG typeG2 = TypeB.a__La_a_k_b().b__La_a_e_b_a_c().a__La_a_e_b_a_g(typeG.a__Ljava_lang_String());
                recovered.a.a.e.b.a.TypeB typeB = TypeB.a__La_a_k_b().b__La_a_e_b_a_c().b__La_a_e_b_a_b();
                typeB.b__V(j);
                typeG2.a(typeB);
                super.b(typeG2);
            }
            catch (recovered.a.a.k.TypeA typeA) {
                TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName()).d(z[4], typeA);
            }
        }
        super.a(true);
        super.b(false);
        this.a__V(string);
    }

    public TypeG(recovered.a.a.e.b.a.TypeG typeG, recovered.a.a.e.b.a.TypeG typeG2, String string) {
        super(typeG, typeG2, true, false);
        this.a__V(string);
    }

    public void a__V(String string) {
        this.i = string;
    }

    public String a__Ljava_lang_String() {
        return this.i;
    }

    public TypeH a__La_a_e_c_h() throws TypeD {
        try {
            if (super.b__La_a_e_b_a_g() == null) {
                return new recovered.a.a.e.c.TypeF((TypeE)super.a__La_a_e_b_a_g().a__La_a_e_b_b_d(), this.a__Ljava_lang_String());
            }
            return new recovered.a.a.e.c.TypeF((TypeE)super.a__La_a_e_b_a_g().a__La_a_e_b_b_d(), (TypeE)super.b__La_a_e_b_a_g().a__La_a_e_b_b_d(), this.a__Ljava_lang_String());
        }
        catch (TypeC typeC) {
            throw new TypeD(z[0], typeC);
        }
        catch (ClassCastException classCastException) {
            throw new TypeD(z[1]);
        }
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            TypeG typeG = (TypeG)object;
            return super.equals(typeG) && (this.a__Ljava_lang_String() == null && typeG.a__Ljava_lang_String() == null || this.a__Ljava_lang_String().equals(typeG.a__Ljava_lang_String()));
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
            this.l = recovered.a.a.q.TypeB.a__ArrayB(byArray, n + n2, this.k);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new TypeA(z[2] + typeA.toString());
        }
        return n2 += this.k;
    }

    public int b(byte[] byArray, int n) throws TypeA {
        int n2 = super.b(byArray, n);
        try {
            System.arraycopy(this.l, 0, byArray, n + n2, this.l.length);
        }
        catch (Exception exception) {
            throw new TypeA(z[3] + exception.toString());
        }
        return n2 += this.l.length;
    }

    public Object clone() {
        TypeG typeG = (TypeG)super.clone();
        if (this.a__Ljava_lang_String() != null) {
            typeG.a__V(new String(this.a__Ljava_lang_String()));
        }
        return typeG;
    }

    static {
        String[] stringArray = new String[5];
        j = "0xDC0A14";
        stringArray[0] = "Unable to correctly translate the ImageData.";
        stringArray[1] = "Super class ConfigurableLinkableText.getData().translate() should be returning TextData objects, but is not.";
        stringArray[2] = "The given array is not large enough to fill this object: ";
        stringArray[3] = "The given array is not large enough to hold the contents of this object due to: ";
        stringArray[4] = "MasterFactory is not configured correctly.";
        z = stringArray;
    }
}
