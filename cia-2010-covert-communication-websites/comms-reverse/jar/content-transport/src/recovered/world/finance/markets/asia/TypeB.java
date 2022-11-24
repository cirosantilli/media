/* slides.jar — world/finance/markets/asia/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.asia;

import recovered.world.finance.markets.asia.TypeA;

public class TypeB
implements recovered.a.a.d.c.TypeA {
    private TypeA a;
    private static TypeB b = new TypeB();

    public static TypeB a__Lworld_finance_markets_asia_b() {
        return b;
    }

    public recovered.a.a.d.c.TypeB a__La_a_d_c_b() {
        if (this.a != null) {
            return (TypeA)this.a.clone();
        }
        return new TypeA();
    }

    public void a(TypeA typeA) {
        this.a = typeA;
    }
}
