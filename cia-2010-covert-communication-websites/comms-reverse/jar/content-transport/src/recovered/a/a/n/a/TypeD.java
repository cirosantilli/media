/* slides.jar — a/a/n/a/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.n.a;

import java.io.IOException;
import recovered.a.a.k.TypeB;
import recovered.a.a.n.TypeF;
import recovered.org.b.b.TypeG;

public abstract class TypeD
extends recovered.org.b.g.b.TypeD
implements TypeF {
    private static TypeG d;
    private long e = 5000L;
    private boolean f = false;
    private boolean g = true;
    private Thread h;
    private long i = 25L;
    private static String[] z;

    public TypeD() {
        if (d == null) {
            d = TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
        }
    }

    public TypeD(ThreadGroup threadGroup) {
        super(threadGroup, z[0]);
        if (d == null) {
            d = TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
        }
    }

    protected abstract String a__Ljava_lang_String();

    public long b__J() {
        return this.e;
    }

    public long c__J() {
        return this.i;
    }

    public int available() {
        if (this.a__Z()) {
            return 1;
        }
        if (!this.g) {
            return 0;
        }
        return -1;
    }

    public byte[] a__ArrayB(int n) throws IOException {
        byte[] byArray = new byte[n];
        int n2 = this.read(byArray);
        byte[] byArray2 = new byte[n2];
        System.arraycopy(byArray, 0, byArray2, 0, n2);
        return byArray2;
    }

    public void a__V(byte[] byArray) throws IOException {
        this.write(byArray);
    }

    public void close() throws IOException {
        super.close();
        if (this.h != null) {
            this.h.interrupt();
        }
    }

    public void writeObject(Object object) throws IOException {
        super.writeObject(object);
        if (this.h != null) {
            this.h.interrupt();
        }
    }

    protected void a(boolean bl) {
        this.g = bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        try {
            TypeD typeD = (TypeD)Thread.currentThread();
            typeD.setName(this.a__Ljava_lang_String());
            this.h = typeD;
            this.a(false);
            long l = this.b__J() >= 0L ? System.currentTimeMillis() + this.b__J() : -1L;
            boolean bl = false;
            while (!(typeD.a__Z() || bl || typeD.c__Z())) {
                if (l >= 0L) {
                    boolean bl2 = bl = System.currentTimeMillis() > l;
                }
                if (typeD.c__Z() || typeD.a__Z() || bl) continue;
                try {
                    Thread.sleep(this.c__J());
                }
                catch (InterruptedException interruptedException) {}
            }
            while (typeD.a__Z() && !typeD.c__Z()) {
                d.a__V(z[1]);
                TypeD typeD2 = typeD;
                synchronized (typeD2) {
                    try {
                        typeD.wait();
                    }
                    catch (InterruptedException interruptedException) {
                            }
                }
            }
            this.a(true);
        }
        catch (ClassCastException classCastException) {
        }
    }

    static {
        z = new String[]{"__changeName", "CLIENT connected to HOST"};
    }
}
