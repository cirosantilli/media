/* slides.jar — world/finance/markets/americas/north/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.americas.north;

import recovered.a.a.e.b.a.TypeG;
import recovered.world.finance.markets.americas.north.TypeA;
import recovered.world.finance.markets.americas.north.TypeB;
import recovered.world.finance.markets.americas.north.TypeD;
import recovered.world.finance.markets.americas.north.TypeE;
import recovered.world.finance.markets.americas.north.TypeF;

public class TypeC
implements recovered.a.a.e.b.a.TypeC {
    private TypeE a;
    private TypeB b;
    private TypeD c;
    private TypeA d;
    private TypeF e;
    private static TypeC f = new TypeC();

    public TypeG a__La_a_e_b_a_g() {
        if (this.a != null) {
            return (TypeE)this.a.clone();
        }
        return new TypeE();
    }

    public void a(TypeE typeE) {
        this.a = typeE;
    }

    public TypeG a__La_a_e_b_a_g(String string) {
        if (this.a != null) {
            this.a.a__V(string);
            return (TypeE)this.a.clone();
        }
        return new TypeE(string);
    }

    public void a(TypeA typeA) {
        this.d = typeA;
    }

    public recovered.a.a.e.b.a.TypeE a__La_a_e_b_a_e(byte[] byArray) {
        if (this.d != null) {
            this.d.a__V(byArray);
            return (TypeA)this.d.clone();
        }
        return new TypeA(byArray);
    }

    public void a(TypeF typeF) {
        this.e = typeF;
    }

    public recovered.a.a.e.b.a.TypeE b__La_a_e_b_a_e(String string) {
        if (this.e != null) {
            this.e.a__V(string);
            return (TypeF)this.e.clone();
        }
        return new TypeF(string);
    }

    public void a(TypeB typeB) {
        this.b = typeB;
    }

    public recovered.a.a.e.b.a.TypeB b__La_a_e_b_a_b() {
        if (this.b != null) {
            return (TypeB)this.b.clone();
        }
        return new TypeB();
    }

    public void a(TypeD typeD) {
        this.c = typeD;
    }

    public recovered.a.a.e.b.a.TypeD c__La_a_e_b_a_d() {
        if (this.c != null) {
            return (TypeD)this.c.clone();
        }
        return new TypeD();
    }

    public static TypeC a__Lworld_finance_markets_americas_north_c() {
        return f;
    }
}
