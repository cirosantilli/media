/* slides.jar — a/a/d/a/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.d.a;

import java.util.Vector;
import recovered.a.a.d.a.TypeB;
import recovered.a.a.d.a.TypeC;
import recovered.a.a.d.a.TypeD;
import recovered.a.a.d.a.TypeE;
import recovered.org.b.g.TypeG;
import recovered.org.b.g.TypeI;

public class TypeA
extends TypeI
implements Runnable,
TypeC {
    private String f;
    private TypeE g;
    private int h;
    private int i;
    private String j;
    private int k;
    private long l;
    private Vector m;
    private int n;
    private long o;
    private static String z = "The given Object o is not of type DisplaySet.";

    public TypeA(String string) {
        this(string, null, 4, 3);
    }

    public TypeA(String string, ThreadGroup threadGroup, int n, int n2) {
        this(string, threadGroup, n, n2, 500L, 0L);
    }

    public TypeA(String string, ThreadGroup threadGroup, int n, int n2, long l, long l2) {
        this.a__V(string);
        this.m = new Vector();
        this.a__V(n);
        this.b__V(n2);
        this.a__V(l);
        this.b(l2);
        this.n = this.h + this.i;
        this.c__V();
        this.g = threadGroup != null ? new TypeE(threadGroup, this) : new TypeE(this);
        this.g.start();
    }

    public void finalize() throws Throwable {
        super.finalize();
        if (this.g != null) {
            this.g.a__V();
            this.g.finalize();
            this.g = null;
        }
    }

    public void a__V(String string) {
        this.f = string;
    }

    public String b__Ljava_lang_String() {
        return this.f;
    }

    public void a__V(Object object) throws ClassCastException {
        if (!(object instanceof TypeB)) {
            throw new ClassCastException(z);
        }
        this.a((TypeB)object);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(TypeB typeB) {
        TypeA typeA = this;
        synchronized (typeA) {
            if (typeB != null) {
                this.j = typeB.c__Ljava_lang_String();
            }
        }
        if (typeB != null) {
            super.a__V(typeB);
        }
    }

    public synchronized Object b__Ljava_lang_Object() throws TypeG {
        return (TypeB)super.b__Ljava_lang_Object();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void c__V() {
        TypeA typeA = this;
        synchronized (typeA) {
            this.j = "";
            this.k = 0;
        }
    }

    public void a__V(long l) {
        this.l = l >= 0L ? l : 500L;
    }

    public void b(long l) {
        this.o = l >= 0L ? l : 0L;
    }

    public void a__V(int n) {
        this.h = n >= 1 ? n : 1;
    }

    public void b__V(int n) {
        this.i = n >= 1 ? n : 1;
    }

    public void a(recovered.a.a.o.TypeA typeA) {
        if (typeA != null) {
            this.m.addElement(typeA);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        Thread thread = Thread.currentThread();
        if (thread instanceof TypeE) {
            TypeE typeE = (TypeE)thread;
            while (!typeE.c__Z()) {
                if (this.a__I() < this.h && this.m.size() > 0) {
                    int n = 0;
                    String string = null;
                    int n2 = 0;
                    TypeA typeA = this;
                    synchronized (typeA) {
                        if (this.a__I() + this.k < this.h - 1 + this.i && this.k < this.n) {
                            n2 = this.i;
                            n = this.k;
                            this.k += n2;
                            string = new String(this.j);
                        }
                    }
                    if (n2 > 0) {
                        this.a(n2, string, n);
                    }
                }
                if (typeE.c__Z() || this.l <= 0L) continue;
                try {
                    Thread.sleep(this.l);
                }
                catch (InterruptedException interruptedException) {}
            }
        }
    }

    protected recovered.a.a.l.TypeI a(String string, String string2, int n) {
        return new recovered.a.a.l.TypeI(string, string2, n);
    }

    protected void b(String string, String string2, int n) {
        recovered.a.a.l.TypeI typeI = this.a(string, string2, n);
        recovered.a.a.o.TypeE typeE = new recovered.a.a.o.TypeE(this, 124, typeI);
        for (int i = 0; i < this.m.size(); ++i) {
            try {
                recovered.a.a.o.TypeA typeA = (recovered.a.a.o.TypeA)this.m.elementAt(i);
                typeA.a(typeE);
                continue;
            }
            catch (NullPointerException nullPointerException) {
                this.m.removeElementAt(i);
                --i;
            }
        }
    }

    private void a(int n, String string, int n2) {
        for (int i = 0; i < n; ++i) {
            this.b(this.b__Ljava_lang_String(), string, n2 + i);
            if (i >= n || this.o <= 0L) continue;
            try {
                Thread.sleep(this.o);
                continue;
            }
            catch (InterruptedException interruptedException) {
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a__V(TypeD typeD) {
        TypeA typeA = this;
        synchronized (typeA) {
            if (this.k > 0) {
                --this.k;
            }
        }
        if (typeD != null) {
            this.a__V((Object)typeD);
        }
    }
}
