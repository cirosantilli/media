/* slides.jar — a/a/e/c/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.e.c;

import java.awt.Cursor;
import recovered.a.a.e.c.TypeB;
import recovered.a.a.e.c.TypeI;
import recovered.a.a.o.ContentEvent;

public class TypeC
extends TypeB
implements TypeI {
    private static final Cursor p = new Cursor(12);
    private ContentEvent q;
    private ContentEvent r;
    private ContentEvent s;

    public TypeC() {
        this(new recovered.a.a.e.b.b.TypeC(), new recovered.a.a.e.b.b.TypeC(), null, p);
    }

    public TypeC(recovered.a.a.e.b.b.TypeC typeC, String string) {
        this(typeC, typeC, string, p);
    }

    public TypeC(recovered.a.a.e.b.b.TypeC typeC, recovered.a.a.e.b.b.TypeC typeC2, String string) {
        this(typeC, typeC2, string, p);
    }

    public TypeC(recovered.a.a.e.b.b.TypeC typeC, recovered.a.a.e.b.b.TypeC typeC2, String string, Cursor cursor) {
        super(typeC, typeC2, true, false);
        this.a__V(string);
        this.b(cursor);
    }

    public void a__V(String string) {
        this.b(this.q);
        this.d(this.r);
        this.f(this.s);
        if (string != null) {
            this.q = new ContentEvent((Object)this, 357, string);
            this.r = new ContentEvent((Object)this, 358, string);
            this.s = new ContentEvent((Object)this, 359, string);
            this.a(this.q);
            this.c(this.r);
            this.e(this.s);
        }
    }

    public void b(Cursor cursor) {
        if (cursor != null) {
            this.a(cursor);
        } else {
            this.a(p);
        }
    }
}
