/* aeronet.jar — a/a/b/e.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.b;

import recovered.a.a.b.ContentApplet;
import recovered.a.a.b.a.ContentChannel;
import recovered.a.a.b.a.a.TypeA;
import recovered.a.a.b.a.a.TypeB;
import recovered.a.a.b.a.a.TypeC;

class ContentTimer
extends Thread {
    private long a;
    private String b;
    private final ContentApplet c;
    private static String[] z = new String[]{"<a href='javascript:void(0)' onclick='window.open(\"", "</a>", "<img src='", "' alt='", "\",\"w\")'>", "' border='0'/>", "Thread-"};

    public ContentTimer(ContentApplet contentApplet, long l, String string) {
        super(z[6] + string);
        this.c = contentApplet;
        this.setDaemon(true);
        this.a = l;
        this.b = string;
    }

    public void run() {
        while (this.isAlive()) {
            try {
                TypeA typeA = ((ContentChannel)ContentApplet.d__Ljava_util_Hashtable().get(this.b)).b__La_a_b_a_a_a();
                if (typeA != null) {
                    this.c.a(this.b, this.a__Ljava_lang_String(typeA));
                    TypeA typeA2 = ((ContentChannel)ContentApplet.d__Ljava_util_Hashtable().get(this.b)).c__La_a_b_a_a_a();
                    if (typeA2 instanceof TypeB) {
                        this.c.a__V(((TypeB)typeA2).d__Ljava_lang_String());
                    }
                    Thread.sleep(this.a);
                    continue;
                }
                Thread.sleep(500L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    private String a__Ljava_lang_String(TypeA typeA) {
        String string = "";
        String string2 = "";
        if (typeA instanceof TypeB) {
            TypeB typeB = (TypeB)typeA;
            string2 = z[2] + typeB.d__Ljava_lang_String() + z[3] + typeB.b__Ljava_lang_String() + z[5];
        } else if (typeA instanceof TypeC) {
            TypeC typeC = (TypeC)typeA;
            string2 = typeC.d__Ljava_lang_String();
        }
        if (typeA.c__Ljava_lang_String() != null && typeA.c__Ljava_lang_String().length() > 0) {
            string = z[0] + typeA.c__Ljava_lang_String() + z[4] + string2 + z[1];
            if (typeA.b__Ljava_lang_String() != null && typeA.b__Ljava_lang_String().length() > 0) {
                // empty if block
            }
        } else {
            string = string2;
        }
        return string;
    }
}
