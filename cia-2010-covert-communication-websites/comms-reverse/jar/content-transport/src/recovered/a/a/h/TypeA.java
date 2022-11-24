/* slides.jar — a/a/h/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.h;

import java.io.IOException;
import java.util.Properties;
import recovered.a.a.h.TypeC;
import recovered.a.a.h.TypeD;
import recovered.a.a.k.TypeB;
import recovered.org.b.b.TypeG;

public abstract class TypeA {
    private static boolean c;
    private static boolean d;
    static /* synthetic */ Class e;
    private static String[] z;

    protected abstract String a__Ljava_lang_String();

    protected abstract Class b__Ljava_lang_Class();

    protected TypeC c__La_a_h_c() {
        return new TypeC();
    }

    public void d__V() {
        if (c) {
            return;
        }
        c = true;
        d = false;
        Properties properties = null;
        TypeC typeC = this.c__La_a_h_c();
        try {
            properties = typeC.a(this.a__Ljava_lang_String(), this.b__Ljava_lang_Class());
        }
        catch (IOException iOException) {
        }
        if (properties != null) {
            d = new Boolean(properties.getProperty(z[4], z[0]));
        }
        if (this.e__Z()) {
            TypeD typeD = new TypeD(properties);
            String string = properties.getProperty(z[1]);
            typeD.b__V(string);
            TypeB.a__La_a_k_b().a(typeD);
            TypeG typeG = typeD.a__Lorg_b_b_g((e == null ? (e = TypeA.a__Ljava_lang_Class(z[3])) : e).getName());
            typeG.a__V(z[2]);
        }
    }

    protected boolean e__Z() {
        return d;
    }

    static /* synthetic */ Class a__Ljava_lang_Class(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    static {
        z = new String[]{"false", "LoggerConfiguration.LogIdPre", "Logging is turned ON.", "a.a.h.a", "LoggerConfiguration.IsLogging"};
        d = false;
    }
}
