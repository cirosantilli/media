/* aeronet.jar — a/a/b/a/f.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.b.a;

import java.io.IOException;
import recovered.a.a.b.a.ContentTransport;
import recovered.a.a.b.a.TypeG;
import recovered.c.a.a.TypeA;
import recovered.c.a.a.TypeB;

class TypeF
extends Thread {
    private final ContentTransport a;

    public TypeF(ContentTransport contentTransport) {
        this.a = contentTransport;
        this.setDaemon(true);
    }

    public void run() {
        while (ContentTransport.a(this.a).size() > 0) {
            TypeG typeG = (TypeG)ContentTransport.a(this.a).elementAt(0);
            ContentTransport.a(this.a).removeElementAt(0);
            String string = typeG.a__Ljava_lang_String();
            TypeB typeB = new TypeB();
            try {
                String string2 = ContentTransport.a(this.a, string, typeB);
                try {
                    ContentTransport.a(this.a, typeG, string2, typeB);
                }
                catch (IOException iOException) {
                }
            }
            catch (TypeA typeA) {}
        }
    }
}
