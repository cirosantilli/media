/* slides.jar — a/a/n/a/a/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.n.a.a;

import java.io.IOException;
import recovered.a.a.k.TypeB;
import recovered.a.a.n.TypeH;
import recovered.a.a.n.TypeI;
import recovered.a.a.n.TypeL;
import recovered.a.a.n.a.TypeD;
import recovered.org.b.b.TypeG;

public abstract class TypeA
extends TypeD
implements TypeI {
    private static TypeG j;
    private static boolean k;
    private TypeH l;
    private int m;
    private static String z;

    protected TypeA() {
    }

    protected TypeA(ThreadGroup threadGroup) {
        super(threadGroup);
    }

    protected void b__V() {
        if (j == null) {
            j = TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
        }
        k = false;
        this.m = 0;
        this.l = new TypeL();
        this.start();
    }

    protected abstract String a__Ljava_lang_String();

    public byte[] a__ArrayB(int n) throws IOException {
        byte[] byArray = new byte[]{};
        byte[] byArray2 = new byte[]{};
        if (this.a__Z()) {
            try {
                byArray = super.a__ArrayB(n);
            }
            catch (IOException iOException) {
                j.c(z);
            }
        }
        if (this.l != null) {
            int n2 = n - byArray.length;
            byArray2 = this.l.a__ArrayB(n2);
        }
        byte[] byArray3 = new byte[byArray.length + byArray2.length];
        System.arraycopy(byArray, 0, byArray3, 0, byArray.length);
        System.arraycopy(byArray2, 0, byArray3, byArray.length, byArray2.length);
        this.m = byArray.length;
        return byArray3;
    }

    public void a__V(byte[] byArray) throws IOException {
        if (this.a__Z()) {
            super.a__V(byArray);
        }
    }

    public int available() {
        int n = -1;
        int n2 = -1;
        if (this.a__Z()) {
            n = super.available();
        }
        if (this.l != null) {
            n2 = this.l.available();
        }
        if (n > 0 || n2 > 0) {
            return 1;
        }
        if (n == 0 || n2 == 0) {
            return 0;
        }
        return -1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        boolean bl;
        TypeA typeA = this;
        synchronized (typeA) {
            boolean bl2 = bl = !k;
            if (bl) {
                k = true;
            }
        }
        if (bl) {
            super.run();
            k = false;
        }
    }

    static {
        z = "Error while reading from HOST, will not return Host data.";
    }
}
