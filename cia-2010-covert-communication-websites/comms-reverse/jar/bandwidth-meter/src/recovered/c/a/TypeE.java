/* world.jar — c/a/e.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.c.a;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import recovered.c.a.a.TypeB;

class TypeE
extends TypeB {
    final InputStream[] c = null;
    final ByteArrayOutputStream[] d = null;
    final IOException[] e = null;
    final boolean[] f = null;
    private static String z = "Error reading stream caused by: ";

    public void run() {
        block4: {
            int n = recovered.c.a.TypeB.b;
            TypeB typeB = (TypeB)Thread.currentThread();
            int n2 = 128;
            byte[] byArray = new byte[n2];
            int n3 = 0;
            try {
                while (!typeB.b__Z() && this.c[0].available() >= 0 && n3 >= 0) {
                    n3 = this.c[0].read(byArray, 0, byArray.length);
                    if (n3 < 1) continue;
                    this.d[0].write(byArray, 0, n3);
                    if (n == 0) continue;
                    int n4 = TypeB.b;
                    TypeB.b = ++n4;
                    break;
                }
            }
            catch (Exception exception) {
                if (exception instanceof IOException) {
                    this.e[0] = (IOException)exception;
                    if (n == 0) break block4;
                }
                this.e[0] = new IOException(z + exception.getMessage());
            }
        }
        this.f[0] = true;
    }
}
