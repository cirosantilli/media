/* slides.jar — world/finance/markets/americas/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.americas;

import recovered.a.a.b.TypeA;
import recovered.a.a.e.TypeD;
import recovered.a.a.e.a.TypeI;
import recovered.a.a.e.b.a.TypeE;
import recovered.a.a.e.c.TypeH;
import recovered.world.finance.markets.americas.TypeB;

public class TypeC
extends TypeB
implements TypeI {
    private TypeE d = null;
    private boolean e = false;
    private boolean f = false;
    private int g = 0;
    private byte[] h = new byte[0];
    private static String[] z = new String[]{"The given array is not large enough to fill this object: ", "The given array is not large enough to hold the contents of this object due to: ", "Super class ConfigurableImageContent.getData().translate() should be returning ImageData objects, but is not.", "Unable to correctly translate the ImageData."};

    public TypeC() {
        this(null, null);
    }

    public TypeC(TypeE typeE, TypeE typeE2) {
        this(typeE, typeE2, false, false);
    }

    public TypeC(TypeE typeE, TypeE typeE2, boolean bl, boolean bl2) {
        super(typeE);
        this.b(typeE2);
        this.a(bl);
        this.b(bl2);
    }

    public void b(TypeE typeE) {
        this.d = typeE;
    }

    public TypeE b__La_a_e_b_a_e() {
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

    public TypeH a__La_a_e_c_h() throws TypeD {
        try {
            recovered.a.a.e.b.b.TypeC typeC;
            recovered.a.a.e.b.b.TypeC typeC2 = typeC = (recovered.a.a.e.b.b.TypeC)super.a__La_a_e_b_a_e().a__La_a_e_b_b_d();
            if (this.b__La_a_e_b_a_e() != null) {
                typeC2 = (recovered.a.a.e.b.b.TypeC)this.b__La_a_e_b_a_e().a__La_a_e_b_b_d();
            }
            return new recovered.a.a.e.c.TypeB(typeC, typeC2, this.a__Z(), this.b__Z());
        }
        catch (recovered.a.a.e.b.TypeC typeC) {
            throw new TypeD(z[3], typeC);
        }
        catch (ClassCastException classCastException) {
            throw new TypeD(z[2]);
        }
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            TypeC typeC = (TypeC)object;
            return this.a(this.a__La_a_e_b_a_e(), typeC.a__La_a_e_b_a_e()) && this.a(this.b__La_a_e_b_a_e(), typeC.b__La_a_e_b_a_e());
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    private boolean a(TypeE typeE, TypeE typeE2) {
        if (typeE == null || typeE2 == null) {
            return typeE == typeE2;
        }
        return typeE.equals(typeE2);
    }

    public void a__V(int n) {
        this.g = n;
    }

    public int a__I() {
        int n = super.a__I() + this.g;
        if (this.b__La_a_e_b_a_e() != null) {
            n += this.b__La_a_e_b_a_e().a__I();
        }
        return n;
    }

    public int a(byte[] byArray, int n) throws TypeA {
        int n2 = super.a(byArray, n);
        if (this.b__La_a_e_b_a_e() != null) {
            n2 += this.b__La_a_e_b_a_e().a(byArray, n + n2);
        }
        try {
            this.h = recovered.a.a.q.TypeB.a__ArrayB(byArray, n + n2, this.g);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new TypeA(z[0] + typeA.toString());
        }
        return n2 += this.g;
    }

    public int b(byte[] byArray, int n) throws TypeA {
        int n2 = super.b(byArray, n);
        if (this.b__La_a_e_b_a_e() != null) {
            n2 += this.b__La_a_e_b_a_e().b(byArray, n + n2);
        }
        try {
            System.arraycopy(this.h, 0, byArray, n + n2, this.h.length);
        }
        catch (Exception exception) {
            throw new TypeA(z[1] + exception.toString());
        }
        return n2 += this.h.length;
    }

    public Object clone() {
        TypeC typeC = (TypeC)super.clone();
        if (this.b__La_a_e_b_a_e() != null) {
            typeC.b((TypeE)this.b__La_a_e_b_a_e().clone());
        }
        return typeC;
    }
}
