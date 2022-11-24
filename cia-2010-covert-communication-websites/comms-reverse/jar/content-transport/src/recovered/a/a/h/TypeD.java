/* slides.jar — a/a/h/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.h;

import java.util.Properties;
import recovered.a.a.h.TypeF;
import recovered.org.b.b.TypeC;
import recovered.org.b.b.TypeG;

public class TypeD
implements TypeF {
    private String a;

    public static boolean a__Z(Properties properties) {
        if (!TypeC.a__Z()) {
            return false;
        }
        return TypeC.a__Z(properties);
    }

    public TypeD() {
    }

    public TypeD(Properties properties) {
        TypeD.a__Z(properties);
    }

    public void b__V(String string) {
        this.a = string;
    }

    public TypeG a__Lorg_b_b_g() {
        if (this.a == null) {
            return new TypeC();
        }
        return new TypeC(this.a);
    }

    public TypeG a__Lorg_b_b_g(Object object) {
        if (object == null) {
            return this.a__Lorg_b_b_g();
        }
        String string = null;
        string = object instanceof Class ? ((Class)object).getName() : object.toString();
        if (this.a == null) {
            return new TypeC(string);
        }
        return new TypeC(new String(this.a + string));
    }
}
