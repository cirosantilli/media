/* slides.jar — org/b/e/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.e;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import recovered.org.b.d.TypeG;
import recovered.org.b.e.TypeA;
import recovered.org.b.e.TypeB;
import recovered.org.b.g.TypeH;

public class TypeC {
    private recovered.org.b.d.TypeH c;
    private static String[] z = new String[]{"Unable to parse resource name size.", "Unable to decode resource: ", "Resource name size out of bounds: ", "Unable to parse resource size.", "Unable to parse resource.", "Resource size out of bounds: ", "Unable to parse resource name."};

    public TypeC() {
        this((recovered.org.b.d.TypeH)null);
    }

    public TypeC(recovered.org.b.d.TypeH typeH) {
        this.a(typeH);
    }

    public TypeC(byte[] byArray) {
        this.a(new TypeA(byArray));
    }

    protected void a(recovered.org.b.d.TypeH typeH) {
        this.c = typeH != null ? typeH : new TypeA(null);
    }

    protected recovered.org.b.d.TypeH a__Lorg_b_d_h() {
        return this.c;
    }

    public TypeB a__Lorg_b_e_b(InputStream inputStream) throws IOException {
        TypeB typeB = new TypeB();
        this.a(typeB, inputStream);
        return typeB;
    }

    public void a(TypeB typeB, InputStream inputStream) throws IOException {
        byte[] byArray = TypeH.a__ArrayB(inputStream);
        this.a(typeB, byArray);
    }

    public void a(TypeB typeB, byte[] byArray) throws IOException {
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byte[] byArray2 = this.a__Lorg_b_d_h().b__ArrayB(byArray);
            byteArrayInputStream = new ByteArrayInputStream(byArray2);
        }
        catch (TypeG typeG) {
            throw new IOException(z[1] + typeG.toString());
        }
        while (byteArrayInputStream.available() > 0) {
            int n = 4;
            byte[] byArray3 = null;
            if (byteArrayInputStream.available() < n) {
                throw new IOException(z[0]);
            }
            byArray3 = new byte[n];
            byteArrayInputStream.read(byArray3, 0, n);
            n = TypeH.a__I(byArray3);
            if (n < 0 || n > 1000) {
                throw new IOException(z[2] + n);
            }
            if (byteArrayInputStream.available() < n) {
                throw new IOException(z[6]);
            }
            byArray3 = new byte[n];
            byteArrayInputStream.read(byArray3, 0, n);
            String string = new String(byArray3);
            n = 4;
            if (byteArrayInputStream.available() < n) {
                throw new IOException(z[3]);
            }
            byArray3 = new byte[n];
            byteArrayInputStream.read(byArray3, 0, n);
            n = TypeH.a__I(byArray3);
            if (n < 0 || n > 500000) {
                throw new IOException(z[5] + n);
            }
            if (byteArrayInputStream.available() < n) {
                throw new IOException(z[4]);
            }
            byArray3 = new byte[n];
            byteArrayInputStream.read(byArray3, 0, n);
            byte[] byArray4 = byArray3;
            typeB.a__V(string, byArray4);
        }
    }
}
