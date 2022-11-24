/* slides.jar — a/a/o/f.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.o;

import recovered.a.a.o.TypeD;

public class ContentEvent
extends TypeD {
    public ContentEvent(Object object, int n, String string) {
        this(object, n, System.currentTimeMillis(), string);
    }

    public ContentEvent(Object object, int n, long l, String string) {
        super(object, n, l, string);
    }
}
