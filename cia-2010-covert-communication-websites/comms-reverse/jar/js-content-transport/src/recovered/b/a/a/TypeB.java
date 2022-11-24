/* aeronet.jar — b/a/a/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.b.a.a;

public class TypeB
extends Thread {
    private boolean[] a = new boolean[1];

    public TypeB() {
        this.a[0] = false;
    }

    public TypeB(String string) {
        super(string);
        this.a[0] = false;
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
