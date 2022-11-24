/* world.jar — c/a/a/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.c.a.a;

import recovered.c.a.a.TypeC;

public class TypeB
extends Thread {
    private boolean[] a;
    public static int b;

    public TypeB() {
        boolean bl = TypeC.c;
        this.a = new boolean[1];
        this.a[0] = false;
        if (b != 0) {
            TypeC.c = !bl;
        }
    }

    public TypeB(ThreadGroup threadGroup, String string) {
        super(threadGroup, string);
        boolean bl = TypeC.c;
        this.a = new boolean[1];
        this.a[0] = false;
        if (bl) {
            int n = b;
            b = ++n;
        }
    }

    public void finalize() throws Throwable {
        this.a__V();
        super.finalize();
    }

    public void a__V() {
        this.a[0] = true;
        try {
            this.interrupt();
        }
        catch (Exception exception) {
        }
    }

    public boolean b__Z() {
        return this.a[0];
    }
}
