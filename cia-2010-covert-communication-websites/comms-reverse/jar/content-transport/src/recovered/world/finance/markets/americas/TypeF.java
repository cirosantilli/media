/* slides.jar — world/finance/markets/americas/f.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.americas;

import recovered.a.a.b.TypeA;
import recovered.a.a.e.TypeD;
import recovered.a.a.e.a.TypeJ;
import recovered.a.a.e.b.TypeC;
import recovered.a.a.e.b.a.TypeG;
import recovered.a.a.e.c.TypeH;
import recovered.a.a.q.TypeB;
import recovered.world.finance.markets.americas.TypeE;

public class TypeF
extends TypeE
implements TypeJ {
    private TypeG d = null;
    private boolean e = false;
    private boolean f = false;
    private int g = 0;
    private byte[] h = new byte[0];
    private static String[] z = new String[]{"The given array is not large enough to hold the contents of this object due to: ", "Unable to correctly translate the TextData.", "Super class ConfigurableTextContent.getData().translate() should be returning TextData objects, but is not.", "The given array is not large enough to fill this object: "};

    public TypeF() {
        this(null, null);
    }

    public TypeF(TypeG typeG, TypeG typeG2) {
        this(typeG, typeG2, false, false);
    }

    public TypeF(TypeG typeG, TypeG typeG2, boolean bl, boolean bl2) {
        super(typeG);
        this.b(typeG2);
        this.a(bl);
        this.b(bl2);
    }

    public void b(TypeG typeG) {
        this.d = typeG;
    }

    public TypeG b__La_a_e_b_a_g() {
        return this.d;
    }

    public void a(boolean bl) {
        this.e = bl;
    }

    public boolean a__Z() {
        return this.e;
    }

    public void b(boolean bl) {
        this.f = bl;
    }

    public boolean b__Z() {
        return this.f;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            TypeF typeF = (TypeF)object;
            return super.a__La_a_e_b_a_g().equals(typeF.a__La_a_e_b_a_g()) && (this.b__La_a_e_b_a_g() == null && typeF.b__La_a_e_b_a_g() == null || this.b__La_a_e_b_a_g().equals(typeF.b__La_a_e_b_a_g()));
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public TypeH a__La_a_e_c_h() throws TypeD {
        try {
            recovered.a.a.e.b.b.TypeE typeE;
            recovered.a.a.e.b.b.TypeE typeE2 = typeE = (recovered.a.a.e.b.b.TypeE)super.a__La_a_e_b_a_g().a__La_a_e_b_b_d();
            if (this.b__La_a_e_b_a_g() != null) {
                typeE2 = (recovered.a.a.e.b.b.TypeE)this.b__La_a_e_b_a_g().a__La_a_e_b_b_d();
            }
            return new recovered.a.a.e.c.TypeE(typeE, typeE2, this.a__Z(), this.b__Z());
        }
        catch (TypeC typeC) {
            throw new TypeD(z[1], typeC);
        }
        catch (ClassCastException classCastException) {
            throw new TypeD(z[2]);
        }
    }

    public void a__V(int n) {
        this.g = n;
    }

    public int a__I() {
        int n = super.a__I() + this.g;
        if (this.b__La_a_e_b_a_g() != null) {
            n += this.b__La_a_e_b_a_g().a__I();
        }
        return n;
    }

    public int a(byte[] byArray, int n) throws TypeA {
        int n2 = super.a(byArray, n);
        if (this.b__La_a_e_b_a_g() != null) {
            n2 += this.b__La_a_e_b_a_g().a(byArray, n + n2);
        }
        try {
            this.h = TypeB.a__ArrayB(byArray, n + n2, this.g);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new TypeA(z[3] + typeA.toString());
        }
        return n2 += this.g;
    }

    public int b(byte[] byArray, int n) throws TypeA {
        int n2 = super.b(byArray, n);
        if (this.b__La_a_e_b_a_g() != null) {
            n2 += this.b__La_a_e_b_a_g().b(byArray, n + n2);
        }
        try {
            System.arraycopy(this.h, 0, byArray, n + n2, this.h.length);
        }
        catch (Exception exception) {
            throw new TypeA(z[0] + exception.toString());
        }
        return n2 += this.h.length;
    }

    public Object clone() {
        TypeF typeF = (TypeF)super.clone();
        if (this.b__La_a_e_b_a_g() != null) {
            typeF.b((TypeG)this.b__La_a_e_b_a_g().clone());
        }
        return typeF;
    }
}
