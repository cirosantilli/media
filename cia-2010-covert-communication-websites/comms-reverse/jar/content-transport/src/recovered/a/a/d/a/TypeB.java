/* slides.jar — a/a/d/a/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.d.a;

import recovered.a.a.d.a.TypeD;
import recovered.a.a.e.c.TypeH;
import recovered.a.a.i.b.TypeA;

public class TypeB
implements TypeD {
    private TypeH a;
    private TypeA b;
    private String c;
    private static String[] z = new String[]{"; effect: ", "; content: ", "DisplaySet {ID: "};

    public TypeB() {
        this(null, null, null);
    }

    public TypeB(TypeH typeH, TypeA typeA, String string) {
        this.a(typeH);
        this.a(typeA);
        this.a__V(string);
    }

    public TypeH a__La_a_e_c_h() {
        return this.a;
    }

    public void a(TypeH typeH) {
        this.a = typeH;
    }

    public TypeA b__La_a_i_b_a() {
        return this.b;
    }

    public void a(TypeA typeA) {
        this.b = typeA;
    }

    public String c__Ljava_lang_String() {
        return this.c;
    }

    public void a__V(String string) {
        this.c = string;
    }

    public String toString() {
        return new String(z[2] + this.c__Ljava_lang_String() + z[1] + this.a__La_a_e_c_h().toString() + z[0] + this.b__La_a_i_b_a().toString() + "}");
    }
}
