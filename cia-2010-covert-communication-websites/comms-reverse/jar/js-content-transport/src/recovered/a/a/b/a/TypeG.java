/* aeronet.jar — a/a/b/a/g.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.b.a;

import java.util.EventObject;

public class TypeG
extends EventObject {
    private String a;

    public TypeG(Object object) {
        super(object);
    }

    public TypeG(Object object, String string) {
        this(object);
        this.a__V(string);
    }

    public void a__V(String string) {
        this.a = string;
    }

    public String a__Ljava_lang_String() {
        return this.a;
    }
}
