/* world.jar — b/a/a/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.b.a.a;

import java.util.Random;

public class Stopwatch {
    private long a;
    private long b;
    private boolean c;
    public static int d;

    public Stopwatch() {
        this.start();
    }

    public synchronized void start() {
        this.c = false;
        this.a = System.currentTimeMillis();
    }

    public double a__D() {
        this.b__V();
        double d = (double)(this.b - this.a) / 1000.0;
        if (d == 0.0) {
            Random random = new Random();
            int n = Math.abs(random.nextInt() % 200) + 700;
            d = (double)n / 100000.0;
        }
        return d;
    }

    private synchronized void b__V() {
        if (this.c) {
            return;
        }
        this.b = System.currentTimeMillis();
    }
}
