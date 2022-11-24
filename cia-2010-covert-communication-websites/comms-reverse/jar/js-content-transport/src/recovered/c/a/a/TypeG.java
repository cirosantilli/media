/* aeronet.jar — c/a/a/g.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.c.a.a;

import recovered.c.a.a.TypeA;
import recovered.c.a.a.TypeB;
import recovered.c.a.a.TypeC;

public class TypeG {
    private int a = 0;
    private String b;
    private static String[] z = new String[]{"true", "null", "false", "Missing value.", ",:]}/\\\"[{;=#", "Substring bounds error", "Unterminated string", "Unclosed comment.", " at character ", " of "};

    public TypeG(String string) {
        this.b = string;
    }

    public void a__V() {
        if (this.a > 0) {
            --this.a;
        }
    }

    public boolean b__Z() {
        return this.a < this.b.length();
    }

    public char c__C() {
        if (this.b__Z()) {
            char c = this.b.charAt(this.a);
            ++this.a;
            return c;
        }
        return '\u0000';
    }

    public String a__Ljava_lang_String(int n) throws TypeA {
        int n2 = this.a;
        int n3 = n2 + n;
        if (n3 >= this.b.length()) {
            throw this.a__Lc_a_a_a(z[5]);
        }
        this.a += n;
        return this.b.substring(n2, n3);
    }

    public char d__C() throws TypeA {
        char c;
        block4: while (true) {
            if ((c = this.c__C()) == '/') {
                switch (this.c__C()) {
                    case '/': {
                        while ((c = this.c__C()) != '\n' && c != '\r' && c != '\u0000') {
                        }
                        continue block4;
                    }
                    case '*': {
                        while (true) {
                            if ((c = this.c__C()) == '\u0000') {
                                throw this.a__Lc_a_a_a(z[7]);
                            }
                            if (c != '*') continue;
                            if (this.c__C() == '/') continue block4;
                            this.a__V();
                        }
                    }
                    default: {
                        this.a__V();
                        return '/';
                    }
                }
            }
            if (c == '#') {
                while ((c = this.c__C()) != '\n' && c != '\r' && c != '\u0000') {
                }
                continue;
            }
            if (c == '\u0000' || c > ' ') break;
        }
        return c;
    }

    public String a(char c) throws TypeA {
        StringBuffer stringBuffer = new StringBuffer();
        block13: while (true) {
            char c2 = this.c__C();
            switch (c2) {
                case '\u0000': 
                case '\n': 
                case '\r': {
                    throw this.a__Lc_a_a_a(z[6]);
                }
                case '\\': {
                    c2 = this.c__C();
                    switch (c2) {
                        case 'b': {
                            stringBuffer.append('\b');
                            continue block13;
                        }
                        case 't': {
                            stringBuffer.append('\t');
                            continue block13;
                        }
                        case 'n': {
                            stringBuffer.append('\n');
                            continue block13;
                        }
                        case 'f': {
                            stringBuffer.append('\f');
                            continue block13;
                        }
                        case 'r': {
                            stringBuffer.append('\r');
                            continue block13;
                        }
                        case 'u': {
                            stringBuffer.append((char)Integer.parseInt(this.a__Ljava_lang_String(4), 16));
                            continue block13;
                        }
                        case 'x': {
                            stringBuffer.append((char)Integer.parseInt(this.a__Ljava_lang_String(2), 16));
                            continue block13;
                        }
                    }
                    stringBuffer.append(c2);
                    continue block13;
                }
            }
            if (c2 == c) {
                return stringBuffer.toString();
            }
            stringBuffer.append(c2);
        }
    }

    public Object e__Ljava_lang_Object() throws TypeA {
        char c = this.d__C();
        switch (c) {
            case '\"': 
            case '\'': {
                return this.a(c);
            }
            case '{': {
                this.a__V();
                return new TypeC(this);
            }
            case '[': {
                this.a__V();
                return new TypeB(this);
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        char c2 = c;
        while (c >= ' ' && z[4].indexOf(c) < 0) {
            stringBuffer.append(c);
            c = this.c__C();
        }
        this.a__V();
        String string = stringBuffer.toString().trim();
        if (string.equals("")) {
            throw this.a__Lc_a_a_a(z[3]);
        }
        if (string.equalsIgnoreCase(z[0])) {
            return Boolean.TRUE;
        }
        if (string.equalsIgnoreCase(z[2])) {
            return Boolean.FALSE;
        }
        if (string.equalsIgnoreCase(z[1])) {
            return TypeC.b;
        }
        if (c2 >= '0' && c2 <= '9' || c2 == '.' || c2 == '-' || c2 == '+') {
            if (c2 == '0') {
                if (string.length() > 2 && (string.charAt(1) == 'x' || string.charAt(1) == 'X')) {
                    try {
                        return new Integer(Integer.parseInt(string.substring(2), 16));
                    }
                    catch (Exception exception) {
                    }
                } else {
                    try {
                        return new Integer(Integer.parseInt(string, 8));
                    }
                    catch (Exception exception) {
                            }
                }
            }
            try {
                return Integer.valueOf(string);
            }
            catch (Exception exception) {
                try {
                    return new Long(Long.parseLong(string));
                }
                catch (Exception exception2) {
                    try {
                        return Double.valueOf(string);
                    }
                    catch (Exception exception3) {
                        return string;
                    }
                }
            }
        }
        return string;
    }

    public TypeA a__Lc_a_a_a(String string) {
        return new TypeA(string + this.toString());
    }

    public String toString() {
        return z[8] + this.a + z[9] + this.b;
    }
}
