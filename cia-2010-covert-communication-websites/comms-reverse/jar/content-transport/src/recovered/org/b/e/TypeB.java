/* slides.jar — org/b/e/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.e;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import recovered.org.b.g.TypeH;

public class TypeB {
    private Hashtable a = new Hashtable();
    private static String z = "UTF8";

    public void a__V(String string, byte[] byArray) {
        this.a.put(string, byArray);
    }

    public Enumeration a__Ljava_util_Enumeration() {
        return this.a.keys();
    }

    public byte[] b__ArrayB(String string) {
        return (byte[])this.a.get(string);
    }

    public int c__I() {
        return this.a.size();
    }

    public void a(Hashtable hashtable) {
        Enumeration enumeration = hashtable.keys();
        while (enumeration.hasMoreElements()) {
            try {
                String string = (String)enumeration.nextElement();
                byte[] byArray = (byte[])hashtable.get(string);
                if (byArray == null) continue;
                this.a__V(string, byArray);
            }
            catch (ClassCastException classCastException) {}
        }
    }

    public Properties d__Ljava_util_Properties() {
        Properties properties = new Properties();
        Enumeration enumeration = this.a__Ljava_util_Enumeration();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            byte[] byArray = this.b__ArrayB(string);
            try {
                properties.put(string, new String(byArray, z));
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                properties.put(string, new String(byArray));
            }
        }
        return properties;
    }

    public String toString() {
        return this.d__Ljava_util_Properties().toString();
    }

    protected Object clone() throws CloneNotSupportedException {
        TypeB typeB = (TypeB)super.clone();
        typeB.a(this.a);
        return typeB;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        TypeB typeB = null;
        try {
            typeB = (TypeB)object;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
        if (typeB.c__I() != this.c__I()) {
            return false;
        }
        Enumeration enumeration = this.a__Ljava_util_Enumeration();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            byte[] byArray = this.b__ArrayB(string);
            if (byArray == null) {
                return false;
            }
            byte[] byArray2 = this.b__ArrayB(string);
            if (TypeH.a__Z(byArray2, byArray)) continue;
            return false;
        }
        return true;
    }
}
