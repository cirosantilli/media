/* slides.jar — a/a/e/b/b/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.e.b.b;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import recovered.a.a.e.b.b.TypeD;
import recovered.org.b.g.TypeH;

public class TypeC
implements TypeD {
    private MediaTracker a = new MediaTracker(new Panel());
    private int b = 0;
    private Image c;
    private Toolkit d = Toolkit.getDefaultToolkit();
    private int e;

    public TypeC() {
        this((Image)null);
    }

    public TypeC(Image image) {
        this.a(image);
    }

    public TypeC(byte[] byArray) {
        this.a__V(byArray);
    }

    public TypeC(String string) {
        this.a__V(string);
    }

    public Image a__Ljava_awt_Image() {
        return this.c;
    }

    public void a(Image image) {
        this.c = image;
        this.b = this.b(this.c);
    }

    public void a__V(byte[] byArray) {
        if (byArray != null) {
            this.c = this.d.createImage(byArray);
            this.b = this.b(this.c);
        } else {
            this.c = null;
        }
    }

    public void a__V(URL uRL) {
        this.a(uRL, true);
    }

    public void a(URL uRL, boolean bl) {
        if (!bl) {
            try {
                InputStream inputStream = uRL.openStream();
                byte[] byArray = TypeH.a__ArrayB(inputStream);
                this.a__V(byArray);
            }
            catch (IOException iOException) {
                this.c = null;
            }
        } else {
            this.c = this.d.getImage(uRL);
            this.b = this.b(this.c);
        }
    }

    public void a__V(String string) {
        URL uRL = this.getClass().getResource(string);
        this.a__V(uRL);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int b(Image image) {
        if (image == null) {
            return -1;
        }
        int n = -1;
        MediaTracker mediaTracker = this.a;
        synchronized (mediaTracker) {
            n = this.b++;
        }
        this.a.addImage(image, n);
        this.a.checkID(n, true);
        return n;
    }

    public boolean b__Z() {
        if (this.e == -1 || this.c == null) {
            return false;
        }
        return this.a.checkID(this.e);
    }

    public boolean c__Z() {
        if (this.e == -1 || this.c == null) {
            return false;
        }
        return this.a.isErrorID(this.e);
    }
}
