/* world.jar — b/a/a/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.b.a.a;

import java.util.Random;
import recovered.b.a.a.Stopwatch;
import recovered.c.a.a.TypeD;

public class TypeB
extends TypeD
implements Runnable {
    private int f = 0;
    private Random g = new Random();

    public TypeB(ThreadGroup threadGroup, String string) {
        super(threadGroup, string);
    }

    public void run() {
        while (!this.b__Z()) {
            this.yield();
            if (Stopwatch.d == 0) continue;
        }
    }
}
