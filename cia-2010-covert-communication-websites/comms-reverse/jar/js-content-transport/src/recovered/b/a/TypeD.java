/* aeronet.jar — b/a/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.b.a;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import recovered.b.a.a.TypeB;

class TypeD
extends TypeB {
    private final InputStream[] b;
    private final ByteArrayOutputStream[] c;
    private final IOException[] d;
    private final boolean[] e;
    private static String z = "Error reading stream caused by: ";

    TypeD(String string, InputStream[] inputStreamArray, ByteArrayOutputStream[] byteArrayOutputStreamArray, IOException[] iOExceptionArray, boolean[] blArray) {
        super(string);
        this.b = inputStreamArray;
        this.c = byteArrayOutputStreamArray;
        this.d = iOExceptionArray;
        this.e = blArray;
    }

    public void run() {
        TypeB typeB = (TypeB)Thread.currentThread();
        int n = 128;
        byte[] byArray = new byte[n];
        int n2 = 0;
        try {
            while (!typeB.b__Z() && this.b[0].available() >= 0 && n2 >= 0) {
                n2 = this.b[0].read(byArray, 0, byArray.length);
                if (n2 < 1) continue;
                this.c[0].write(byArray, 0, n2);
            }
        }
        catch (Exception exception) {
            this.d[0] = exception instanceof IOException ? (IOException)exception : new IOException(z + exception.getMessage());
        }
        this.e[0] = true;
    }
}
