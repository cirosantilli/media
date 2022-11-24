/* slides.jar — a/a/r/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.r;

import recovered.a.a.e.c.TypeH;
import recovered.a.a.i.b.TypeB;

class TypeD {
    private TypeH b;
    private TypeB c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private long l;
    private int m;
    private boolean n;
    private boolean o;

    public TypeD(TypeB typeB, TypeH typeH, int n, int n2, int n3, int n4, int n5, int n6) {
        this.c = typeB;
        this.b = typeH;
        this.n = false;
        this.o = true;
        this.m = 1;
        this.a(n, n2, n3, n4, n5, n6, 0L);
    }

    public TypeB a__La_a_i_b_b() {
        return this.c;
    }

    public TypeH b__La_a_e_c_h() {
        return this.b;
    }

    private void a(int n, int n2, int n3, int n4, int n5, int n6, long l) {
        this.d = n;
        this.e = n2;
        this.f = n3;
        this.g = n4;
        this.h = n3;
        this.i = n4;
        this.j = n5;
        this.k = n6;
        this.l = l;
        if (this.b != null) {
            this.b.setLocation(this.f, this.g);
        }
    }

    public void a__V(long l) {
        this.l = l;
        this.o = false;
    }

    public void b(long l) {
        if (this.l > 0L && !this.o) {
            boolean bl = false;
            boolean bl2 = false;
            if (this.m != 1) {
                double d = this.d * this.m;
                double d2 = this.e * this.m;
            } else {
                double d = 1.0;
                double d3 = 1.0;
            }
            int n = (int)((l - this.l) * (long)this.d / 1600L);
            int n2 = (int)((l - this.l) * (long)this.e / 1600L);
            if (this.f != this.h + n) {
                this.f = this.h + n;
                this.b.setLocation(this.f, this.b.getY());
                bl = true;
                if (Math.abs(this.f - this.h) >= this.j) {
                    this.o = true;
                }
            }
            if (this.g != this.i + n2) {
                this.g = this.i + n2;
                this.b.setLocation(this.b.getX(), this.g);
                bl2 = true;
                if (Math.abs(this.g - this.i) >= this.k) {
                    this.o = true;
                }
            }
            this.n = bl || bl2;
        }
    }

    public boolean c__Z() {
        return this.n;
    }

    public boolean d__Z() {
        return this.o;
    }

    public int g__I() {
        return this.f;
    }

    public int h__I() {
        return this.g;
    }
}
