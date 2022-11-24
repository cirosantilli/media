/* slides.jar — a/a/e/c/f.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.e.c;

import java.awt.Color;
import java.awt.Cursor;
import recovered.a.a.e.c.TypeE;
import recovered.a.a.e.c.TypeI;
import recovered.a.a.o.ContentEvent;

public class TypeF
extends TypeE
implements TypeI {
    private static final Color s = new Color(220, 10, 20);
    private static final Cursor t = new Cursor(12);
    private ContentEvent u;
    private ContentEvent v;
    private ContentEvent w;

    public TypeF() {
        this(new recovered.a.a.e.b.b.TypeE(), new recovered.a.a.e.b.b.TypeE(), null, t);
    }

    public TypeF(recovered.a.a.e.b.b.TypeE typeE, String string) {
        this(typeE, new recovered.a.a.e.b.b.TypeE(typeE.a__ArrayLjava_lang_String(), typeE.b__Ljava_awt_Font(), s), string, t);
    }

    public TypeF(recovered.a.a.e.b.b.TypeE typeE, recovered.a.a.e.b.b.TypeE typeE2, String string) {
        this(typeE, typeE2, string, t);
    }

    public TypeF(recovered.a.a.e.b.b.TypeE typeE, recovered.a.a.e.b.b.TypeE typeE2, String string, Cursor cursor) {
        super(typeE, typeE2, true, false);
        this.a__V(string);
        this.b(cursor);
    }

    public void a__V(String string) {
        if (this.u != null) {
            this.b(this.u);
        }
        if (this.v != null) {
            this.d(this.v);
        }
        if (this.w != null) {
            this.f(this.w);
        }
        if (string != null) {
            this.u = new ContentEvent((Object)this, 357, string);
            this.v = new ContentEvent((Object)this, 358, string);
            this.w = new ContentEvent((Object)this, 359, string);
            this.a(this.u);
            this.c(this.v);
            this.e(this.w);
        }
    }

    public void b(Cursor cursor) {
        if (cursor != null) {
            this.a(cursor);
        } else {
            this.a(t);
        }
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            TypeF typeF = (TypeF)object;
            return super.equals(typeF);
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }
}
