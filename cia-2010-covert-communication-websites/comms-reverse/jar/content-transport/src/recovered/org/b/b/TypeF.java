/* slides.jar — org/b/b/f.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.b;

import java.io.PrintStream;
import recovered.org.b.b.TypeG;

public class TypeF
implements TypeG {
    private PrintStream h;
    private int i;

    public TypeF() {
        this(Integer.MAX_VALUE, null);
    }

    public TypeF(int n, PrintStream printStream) {
        this.a(printStream);
        this.a__V(n);
    }

    public void a__V(Object object) {
        if (this.i <= 10000) {
            this.b__V(object.toString());
        }
    }

    public void a(Object object, Throwable throwable) {
        if (this.i <= 10000) {
            this.b__V(object.toString());
            this.a(throwable);
        }
    }

    public void b(Object object) {
        if (this.i <= 20000) {
            this.b__V(object.toString());
        }
    }

    public void b(Object object, Throwable throwable) {
        if (this.i <= 20000) {
            this.b__V(object.toString());
            this.a(throwable);
        }
    }

    public void c(Object object) {
        if (this.i <= 30000) {
            this.b__V(object.toString());
        }
    }

    public void c(Object object, Throwable throwable) {
        if (this.i <= 30000) {
            this.b__V(object.toString());
            this.a(throwable);
        }
    }

    public void d(Object object) {
        if (this.i <= 40000) {
            this.b__V(object.toString());
        }
    }

    public void d(Object object, Throwable throwable) {
        if (this.i <= 40000) {
            this.b__V(object.toString());
            this.a(throwable);
        }
    }

    public void e(Object object) {
        if (this.i <= 50000) {
            this.b__V(object.toString());
        }
    }

    public void e(Object object, Throwable throwable) {
        if (this.i <= 50000) {
            this.b__V(object.toString());
            this.a(throwable);
        }
    }

    public int a__I() {
        return this.i;
    }

    public void a__V(int n) {
        this.i = n;
    }

    protected void b__V(String string) {
        if (this.h != null) {
            this.h.println(string);
        }
    }

    protected void a(Throwable throwable) {
        if (this.h != null) {
            throwable.printStackTrace(this.h);
        }
    }

    public void b__V() {
        if (this.h != null) {
            this.h.flush();
        }
    }

    public void finalize() throws Throwable {
        this.b__V();
        super.finalize();
    }

    public void a(PrintStream printStream) {
        this.h = printStream;
    }
}
