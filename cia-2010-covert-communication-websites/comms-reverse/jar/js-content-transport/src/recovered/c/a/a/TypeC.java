/* aeronet.jar — c/a/a/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.c.a.a;

import java.util.Enumeration;
import java.util.Hashtable;
import recovered.c.a.a.TypeA;
import recovered.c.a.a.TypeB;
import recovered.c.a.a.TypeE;
import recovered.c.a.a.TypeF;
import recovered.c.a.a.TypeG;

public class TypeC {
    private Hashtable a = new Hashtable();
    public static final Object b;
    private static String[] z;

    public TypeC() {
    }

    public TypeC(TypeG typeG) throws TypeA {
        this();
        if (typeG.d__C() != '{') {
            throw typeG.a__Lc_a_a_a(z[17]);
        }
        block8: while (true) {
            char c = typeG.d__C();
            switch (c) {
                case '\u0000': {
                    throw typeG.a__Lc_a_a_a(z[18]);
                }
                case '}': {
                    return;
                }
            }
            typeG.a__V();
            String string = typeG.e__Ljava_lang_Object().toString();
            c = typeG.d__C();
            if (c == '=') {
                if (typeG.c__C() != '>') {
                    typeG.a__V();
                }
            } else if (c != ':') {
                throw typeG.a__Lc_a_a_a(z[20]);
            }
            this.a(string, typeG.e__Ljava_lang_Object());
            switch (typeG.d__C()) {
                case ',': 
                case ';': {
                    if (typeG.d__C() == '}') {
                        return;
                    }
                    typeG.a__V();
                    continue block8;
                }
                case '}': {
                    return;
                }
            }
            break;
        }
        throw typeG.a__Lc_a_a_a(z[19]);
    }

    public Object a__Ljava_lang_Object(String string) throws TypeA {
        Object object = this.g(string);
        if (object == null) {
            throw new TypeA(z[13] + TypeC.h(string) + z[12]);
        }
        return object;
    }

    public TypeB b__Lc_a_a_b(String string) throws TypeA {
        Object object = this.a__Ljava_lang_Object(string);
        if (object instanceof TypeB) {
            return (TypeB)object;
        }
        throw new TypeA(z[13] + TypeC.h(string) + z[15]);
    }

    public String c__Ljava_lang_String(String string) throws TypeA {
        return this.a__Ljava_lang_Object(string).toString();
    }

    public boolean d__Z(String string) {
        return this.a.containsKey(string);
    }

    public boolean e(String string) {
        return b.equals(this.g(string));
    }

    public Enumeration a__Ljava_util_Enumeration() {
        return this.a.keys();
    }

    public static String f(String string) {
        if (string.indexOf(46) > 0 && string.indexOf(101) < 0 && string.indexOf(69) < 0) {
            while (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith(".")) {
                string = string.substring(0, string.length() - 1);
            }
        }
        return string;
    }

    public static String a__Ljava_lang_String(Object object) throws TypeA {
        if (object == null) {
            throw new TypeA(z[14]);
        }
        TypeC.b(object);
        return TypeC.f(object.toString());
    }

    public Object g(String string) {
        return string == null ? null : this.a.get(string);
    }

    public TypeC a(String string, Object object) throws TypeA {
        if (string == null) {
            throw new TypeA(z[16]);
        }
        if (object != null) {
            TypeC.b(object);
            this.a.put(string, object);
        } else {
            this.i(string);
        }
        return this;
    }

    public static String h(String string) {
        if (string == null || string.length() == 0) {
            return z[9];
        }
        char c = '\u0000';
        int n = string.length();
        StringBuffer stringBuffer = new StringBuffer(n + 4);
        stringBuffer.append('\"');
        block9: for (int i = 0; i < n; ++i) {
            char c2 = c;
            c = string.charAt(i);
            switch (c) {
                case '\"': 
                case '\\': {
                    stringBuffer.append('\\');
                    stringBuffer.append(c);
                    continue block9;
                }
                case '/': {
                    if (c2 == '<') {
                        stringBuffer.append('\\');
                    }
                    stringBuffer.append(c);
                    continue block9;
                }
                case '\b': {
                    stringBuffer.append(z[7]);
                    continue block9;
                }
                case '\t': {
                    stringBuffer.append(z[5]);
                    continue block9;
                }
                case '\n': {
                    stringBuffer.append(z[4]);
                    continue block9;
                }
                case '\f': {
                    stringBuffer.append(z[11]);
                    continue block9;
                }
                case '\r': {
                    stringBuffer.append(z[6]);
                    continue block9;
                }
                default: {
                    if (c < ' ') {
                        String string2 = z[8] + Integer.toHexString(c);
                        stringBuffer.append(z[10] + string2.substring(string2.length() - 4));
                        continue block9;
                    }
                    stringBuffer.append(c);
                }
            }
        }
        stringBuffer.append('\"');
        return stringBuffer.toString();
    }

    public Object i(String string) {
        return this.a.remove(string);
    }

    static void b(Object object) throws TypeA {
        if (object != null) {
            if (object instanceof Double) {
                if (((Double)object).isInfinite() || ((Double)object).isNaN()) {
                    throw new TypeA(z[3]);
                }
            } else if (object instanceof Float && (((Float)object).isInfinite() || ((Float)object).isNaN())) {
                throw new TypeA(z[2]);
            }
        }
    }

    public String toString() {
        try {
            Enumeration enumeration = this.a__Ljava_util_Enumeration();
            StringBuffer stringBuffer = new StringBuffer("{");
            while (enumeration.hasMoreElements()) {
                if (stringBuffer.length() > 1) {
                    stringBuffer.append(',');
                }
                Object e = enumeration.nextElement();
                stringBuffer.append(TypeC.h(e.toString()));
                stringBuffer.append(':');
                stringBuffer.append(TypeC.c(this.a.get(e)));
            }
            stringBuffer.append('}');
            return stringBuffer.toString();
        }
        catch (Exception exception) {
            return null;
        }
    }

    static String c(Object object) throws TypeA {
        if (object == null || object.equals(null)) {
            return z[0];
        }
        if (object instanceof TypeF) {
            String string;
            try {
                string = ((TypeF)object).a__Ljava_lang_String();
            }
            catch (Exception exception) {
                throw new TypeA(exception);
            }
            if (string instanceof String) {
                return string;
            }
            throw new TypeA(z[1] + string);
        }
        if (object instanceof Float || object instanceof Double || object instanceof Byte || object instanceof Short || object instanceof Integer || object instanceof Long) {
            return TypeC.a__Ljava_lang_String(object);
        }
        if (object instanceof Boolean || object instanceof TypeC || object instanceof TypeB) {
            return object.toString();
        }
        return TypeC.h(object.toString());
    }

    static {
        z = new String[]{"null", "Bad value from toJSONString: ", "JSON does not allow non-finite numbers.", "JSON does not allow non-finite numbers", "\\n", "\\t", "\\r", "\\b", "000", "\"\"", "\\u", "\\f", "] not found.", "JSONObject[", "Null pointer", "] is not a JSONArray.", "Null key.", "A JSONObject text must begin with '{'", "A JSONObject text must end with '}'", "Expected a ',' or '}'", "Expected a ':' after a key"};
        b = new TypeE(null);
    }
}
