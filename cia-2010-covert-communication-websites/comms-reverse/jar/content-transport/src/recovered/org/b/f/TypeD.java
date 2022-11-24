/* slides.jar — org/b/f/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.f;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import recovered.org.b.f.TypeC;

public class TypeD {
    private Hashtable a = new Hashtable();

    public void a(TypeC typeC) {
        String string = typeC.e__Ljava_lang_String();
        String string2 = typeC.d__Ljava_lang_String();
        String string3 = typeC.a__Ljava_lang_String();
        Hashtable<String, Hashtable<String, TypeC>> hashtable = (Hashtable<String, Hashtable<String, TypeC>>)this.a.get(string);
        if (hashtable == null) {
            Hashtable<String, TypeC> hashtable2 = new Hashtable<String, TypeC>();
            hashtable2.put(string3, typeC);
            hashtable = new Hashtable<String, Hashtable<String, TypeC>>();
            hashtable.put(string2, hashtable2);
            this.a.put(string, hashtable);
        } else {
            Hashtable<String, TypeC> hashtable3 = (Hashtable<String, TypeC>)hashtable.get(string2);
            if (hashtable3 == null) {
                hashtable3 = new Hashtable<String, TypeC>();
                hashtable3.put(string3, typeC);
                hashtable.put(string2, hashtable3);
            } else {
                hashtable3.put(string3, typeC);
            }
        }
    }

    public Vector a__Ljava_util_Vector(String string, String string2) {
        Vector<TypeC> vector = new Vector<TypeC>();
        Hashtable hashtable = (Hashtable)this.a.get(string);
        if (hashtable != null) {
            Enumeration enumeration = hashtable.keys();
            while (enumeration.hasMoreElements()) {
                String string3 = (String)enumeration.nextElement();
                if (string2.length() < string3.length() || !string3.equalsIgnoreCase(string2.substring(0, string3.length()))) continue;
                Hashtable hashtable2 = (Hashtable)hashtable.get(string3);
                if (hashtable == null) continue;
                Enumeration enumeration2 = hashtable2.elements();
                while (enumeration2.hasMoreElements()) {
                    vector.addElement((TypeC)enumeration2.nextElement());
                }
            }
        }
        return vector;
    }
}
