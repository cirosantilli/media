/* slides.jar — org/b/b/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.b;

import java.util.Properties;
import recovered.org.b.b.TypeD;
import recovered.org.b.b.TypeE;
import recovered.org.b.b.TypeG;

public class TypeC
implements TypeG {
    private static Boolean h;
    private TypeE i;
    private static String z;

    public static boolean a__Z() {
        if (h != null) {
            return h;
        }
        try {
            Class.forName(z);
            h = new Boolean(true);
            return true;
        }
        catch (Throwable throwable) {
            h = new Boolean(false);
            return false;
        }
    }

    public static boolean a__Z(Properties properties) {
        if (!TypeC.a__Z()) {
            return false;
        }
        TypeD typeD = new TypeD();
        return typeD.a__Z(properties);
    }

    public TypeC() {
        if (TypeC.a__Z()) {
            this.i = new TypeE();
        }
    }

    public TypeC(String string) {
        if (TypeC.a__Z()) {
            this.i = new TypeE(string);
        }
    }

    public void a__V(Object object) {
        if (this.i != null) {
            this.i.a__V(object);
        }
    }

    public void a(Object object, Throwable throwable) {
        if (this.i != null) {
            this.i.a(object, throwable);
        }
    }

    public void b(Object object) {
        if (this.i != null) {
            this.i.b(object);
        }
    }

    public void b(Object object, Throwable throwable) {
        if (this.i != null) {
            this.i.b(object, throwable);
        }
    }

    public void c(Object object) {
        if (this.i != null) {
            this.i.c(object);
        }
    }

    public void c(Object object, Throwable throwable) {
        if (this.i != null) {
            this.i.c(object, throwable);
        }
    }

    public void d(Object object) {
        if (this.i != null) {
            this.i.d(object);
        }
    }

    public void d(Object object, Throwable throwable) {
        if (this.i != null) {
            this.i.d(object, throwable);
        }
    }

    public void e(Object object) {
        if (this.i != null) {
            this.i.e(object);
        }
    }

    public void e(Object object, Throwable throwable) {
        if (this.i != null) {
            this.i.e(object, throwable);
        }
    }

    public int a__I() {
        if (this.i != null) {
            return this.i.b__I();
        }
        return Integer.MAX_VALUE;
    }

    static {
        z = "org.apache.log4j.Logger";
        h = null;
    }
}
