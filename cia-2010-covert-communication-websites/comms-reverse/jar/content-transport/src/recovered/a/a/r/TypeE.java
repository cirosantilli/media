/* slides.jar — a/a/r/e.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.r;

import java.awt.Point;
import recovered.a.a.e.c.TypeH;
import recovered.a.a.i.b.TypeC;

class TypeE {
    private TypeH a;
    private TypeC b;
    private int c;
    private int d;
    private long e;
    private long f;
    private boolean h;

    public TypeE(TypeC typeC, TypeH typeH, long l) {
        this.b = typeC;
        this.a = typeH;
        this.f = l <= 0L && this.b != null ? this.b.a__J() : l;
        this.e = -1L;
        this.h = true;
    }

    public TypeH b__La_a_e_c_h() {
        return this.a;
    }

    public void a__V(long l) {
        this.e = l;
        this.h = false;
    }

    public void b(long l) {
        if (this.e > 0L && !this.h) {
            this.h = l - this.e >= this.f;
        }
    }

    public boolean c__Z() {
        return this.h;
    }

    public void a(Point point) {
        this.c = point.x;
        this.d = point.y;
        if (this.a != null) {
            this.a.setLocation(this.c, this.d);
        }
    }
}
