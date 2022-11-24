/* slides.jar — world/finance/markets/europe/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.europe;

import recovered.a.a.i.a.TypeD;
import recovered.world.finance.markets.europe.TypeB;
import recovered.world.finance.markets.europe.TypeC;

public class TypeA
implements recovered.a.a.i.a.TypeA {
    private TypeC a;
    private TypeB b;
    private static TypeA c = new TypeA();

    public static TypeA a__Lworld_finance_markets_europe_a() {
        return c;
    }

    public void a(TypeB typeB) {
        this.b = typeB;
    }

    public recovered.a.a.i.a.TypeB a__La_a_i_a_b() {
        if (this.b != null) {
            return (TypeB)this.b.clone();
        }
        return new TypeB();
    }

    public void a(TypeC typeC) {
        this.a = typeC;
    }

    public TypeD b__La_a_i_a_d() {
        if (this.a != null) {
            return (TypeC)this.a.clone();
        }
        return new TypeC();
    }
}
