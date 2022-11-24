/* aeronet.jar — a/a/b/b/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.b.b;

import java.io.IOException;
import recovered.b.a.TypeC;
import recovered.b.a.a.TypeD;

public class StartupWorker
extends TypeD {
    public StartupWorker(String string) {
        super(string);
    }

    public byte[] a__ArrayB(int n) throws IOException {
        byte[] byArray = new byte[n];
        int n2 = this.read(byArray);
        if (n2 != n) {
            // empty if block
        }
        byte[] byArray2 = TypeC.a(byArray, 0, n2);
        return byArray2;
    }

    public int a__I(byte[] byArray) throws IOException {
        this.write(byArray);
        return byArray.length;
    }

    public void run() {
        while (!this.b__Z()) {
            try {
                if (this.a__Z()) {
                    // empty if block
                }
                try {
                    Thread.sleep(500L);
                }
                catch (Exception exception) {
                }
            }
            catch (Exception exception) {}
        }
        try {
            this.close();
        }
        catch (IOException iOException) {
        }
    }
}
