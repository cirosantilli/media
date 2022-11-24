/* aeronet.jar — c/a/a/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.c.a.a;

import java.util.Vector;
import recovered.c.a.a.TypeA;
import recovered.c.a.a.TypeC;
import recovered.c.a.a.TypeG;

public class TypeB {
    private Vector a = new Vector();
    private static String[] z = new String[]{"] not found.", "JSONArray[", "] is not a JSONObject.", "A JSONArray text must start with '['", "Expected a ',' or ']'"};

    public TypeB() {
    }

    public TypeB(TypeG typeG) throws TypeA {
        this();
        if (typeG.d__C() != '[') {
            throw typeG.a__Lc_a_a_a(z[3]);
        }
        if (typeG.d__C() == ']') {
            return;
        }
        typeG.a__V();
        block4: while (true) {
            if (typeG.d__C() == ',') {
                typeG.a__V();
                this.a.addElement(null);
            } else {
                typeG.a__V();
                this.a.addElement(typeG.e__Ljava_lang_Object());
            }
            switch (typeG.d__C()) {
                case ',': 
                case ';': {
                    if (typeG.d__C() == ']') {
                        return;
                    }
                    typeG.a__V();
                    continue block4;
                }
                case ']': {
                    return;
                }
            }
            break;
        }
        throw typeG.a__Lc_a_a_a(z[4]);
    }

    public TypeB(String string) throws TypeA {
        this(new TypeG(string));
    }

    public Object a__Ljava_lang_Object(int n) throws TypeA {
        Object object = this.c(n);
        if (object == null) {
            throw new TypeA(z[1] + n + z[0]);
        }
        return object;
    }

    public TypeC b__Lc_a_a_c(int n) throws TypeA {
        Object object = this.a__Ljava_lang_Object(n);
        if (object instanceof TypeC) {
            return (TypeC)object;
        }
        throw new TypeA(z[1] + n + z[2]);
    }

    public String a__Ljava_lang_String(String string) throws TypeA {
        int n = this.a__I();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                stringBuffer.append(string);
            }
            stringBuffer.append(TypeC.c(this.a.elementAt(i)));
        }
        return stringBuffer.toString();
    }

    public int a__I() {
        return this.a.size();
    }

    public Object c(int n) {
        return n < 0 || n >= this.a__I() ? null : this.a.elementAt(n);
    }

    public TypeB a__Lc_a_a_b(Object object) {
        this.a.addElement(object);
        return this;
    }

    public String toString() {
        try {
            return '[' + this.a__Ljava_lang_String(",") + ']';
        }
        catch (Exception exception) {
            return null;
        }
    }
}
