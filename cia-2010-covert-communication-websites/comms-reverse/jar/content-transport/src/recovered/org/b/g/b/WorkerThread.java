/* slides.jar — org/b/g/b/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.g.b;

public class WorkerThread
extends Thread {
    private boolean[] a = new boolean[1];

    public WorkerThread() {
        this.a[0] = false;
    }

    public WorkerThread(Runnable runnable) {
        super(runnable);
        this.a[0] = false;
    }

    public WorkerThread(ThreadGroup threadGroup, Runnable runnable) {
        super(threadGroup, runnable);
        this.a[0] = false;
    }

    public WorkerThread(ThreadGroup threadGroup, String string) {
        super(threadGroup, string);
        this.a[0] = false;
    }

    public WorkerThread(ThreadGroup threadGroup, Runnable runnable, String string) {
        super(threadGroup, runnable, string);
        this.a[0] = false;
    }

    public void finalize() throws Throwable {
        super.finalize();
        this.a__V();
    }

    public void a__V() {
        this.a[0] = true;
        try {
            this.interrupt();
        }
        catch (Exception exception) {
        }
    }

    public boolean c__Z() {
        return this.a[0];
    }
}
