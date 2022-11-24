/* slides.jar — a/a/l/i.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.l;

import recovered.a.a.b.TypeA;
import recovered.a.a.l.TypeN;

public class TypeI
implements TypeN {
    private String a;
    private String b;
    private int c;
    private static String[] z = new String[]{"; ", "DisplayRequestInformation {lastContentId: ", "renderingID: ", "offset: "};

    public TypeI(String string, String string2, int n) {
        this.a__V(string);
        this.b__V(string2);
        this.a__V(n);
    }

    public void a__V(String string) {
        this.a = string;
    }

    public String a__Ljava_lang_String() {
        return this.a;
    }

    public void b__V(String string) {
        this.b = string;
    }

    public String b__Ljava_lang_String() {
        return this.b;
    }

    public void a__V(int n) {
        this.c = n;
    }

    public int c__I() {
        return this.c;
    }

    public boolean equals(Object object) {
        try {
            TypeI typeI = (TypeI)object;
            return this.a__Ljava_lang_String().equals(typeI.a__Ljava_lang_String()) && this.b__Ljava_lang_String().equals(typeI.b__Ljava_lang_String()) && this.c__I() == typeI.c__I();
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public String toString() {
        return new String(z[1] + this.b__Ljava_lang_String() + z[0] + z[3] + this.c__I() + z[0] + z[2] + this.a__Ljava_lang_String() + "}");
    }

    public int a__I() {
        return 0;
    }

    public int a(byte[] byArray, int n) throws TypeA {
        return 0;
    }

    public int a__I(byte[] byArray) throws TypeA {
        return this.a(byArray, 0);
    }

    public int b(byte[] byArray, int n) throws TypeA {
        return 0;
    }

    public int b__I(byte[] byArray) throws TypeA {
        return this.b(byArray, 0);
    }

    public byte[] a__ArrayB() throws TypeA {
        byte[] byArray = new byte[this.a__I()];
        this.b__I(byArray);
        return byArray;
    }
}
