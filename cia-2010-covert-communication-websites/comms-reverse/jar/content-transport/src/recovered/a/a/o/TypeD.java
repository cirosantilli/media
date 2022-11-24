/* slides.jar — a/a/o/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.o;

import java.util.EventObject;

public class TypeD
extends EventObject {
    private Object a;
    private int b;
    private long c;
    private Object d;

    public TypeD(Object object, int n, Object object2) {
        this(object, n, System.currentTimeMillis(), object2);
    }

    public TypeD(Object object, int n, long l, Object object2) {
        super(object);
        this.a = object;
        this.b = n;
        this.c = l;
        this.d = object2;
    }

    public Object getSource() {
        return this.a;
    }

    public int a__I() {
        return this.b;
    }

    public Object c__Ljava_lang_Object() {
        return this.d;
    }
}
