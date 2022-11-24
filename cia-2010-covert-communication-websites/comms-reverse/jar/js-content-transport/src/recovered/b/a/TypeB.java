/* aeronet.jar — b/a/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.b.a;

import java.io.PrintStream;
import java.io.PrintWriter;

public class TypeB
extends Exception {
    Throwable a;
    private static String z = "Caused by: ";

    public TypeB() {
    }

    public TypeB(String string) {
        super(string);
    }

    public TypeB(Throwable throwable) {
        this();
        this.a = throwable;
    }

    public void printStackTrace() {
        super.printStackTrace(System.err);
        if (this.a != null) {
            System.err.print(z);
            this.a.printStackTrace(System.err);
        }
    }

    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        if (this.a != null) {
            printStream.print(z);
            this.a.printStackTrace(printStream);
        }
    }

    public void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        if (this.a != null) {
            printWriter.print(z);
            this.a.printStackTrace(printWriter);
        }
    }
}
