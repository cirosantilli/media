/* slides.jar — world/finance/markets/americas/north/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.world.finance.markets.americas.north;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import recovered.a.a.e.b.b.TypeC;
import recovered.a.a.e.b.b.TypeD;
import recovered.a.a.k.TypeB;
import recovered.org.b.b.TypeG;
import recovered.org.b.g.TypeH;

public class TypeA
implements recovered.a.a.e.b.a.TypeA {
    private byte[] a = null;
    private int b = 0;
    private byte[] c = new byte[0];
    private static String[] z = new String[]{"Null data array.", "The given array is not large enough to hold the contents of this object due to: ", " is not in the range 0 - ", "Array index out of bounds: ", "Input image data cannot be found; no bytes returned.", "The given array is not large enough to fill this object: ", "Input image file (", ") cannot be found; no bytes returned.", "ConfigurableBytesImageData is not cloneable."};

    public TypeA() {
        this(null);
    }

    public TypeA(byte[] byArray) {
        this.a__V(byArray);
    }

    public byte[] b__ArrayB() {
        return this.a;
    }

    public void a__V(byte[] byArray) {
        this.a = byArray;
    }

    public TypeD a__La_a_e_b_b_d() throws recovered.a.a.e.b.TypeC {
        return new TypeC(this.b__ArrayB());
    }

    public void a__V(int n) {
        this.b = n;
    }

    public int a__I() {
        return this.b;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            TypeA typeA = (TypeA)object;
            byte[] byArray = typeA.b__ArrayB();
            byte[] byArray2 = this.b__ArrayB();
            return TypeH.a__Z(byArray2, byArray);
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public static byte[] a__ArrayB(String string) throws IOException {
        InputStream inputStream = new TypeA().getClass().getResourceAsStream(string);
        if (inputStream == null) {
            throw new IOException(z[6] + string + z[7]);
        }
        return TypeA.a__ArrayB(inputStream);
    }

    public static byte[] a__ArrayB(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        if (fileInputStream == null) {
            throw new IOException(z[6] + file.getName() + z[7]);
        }
        return TypeA.a__ArrayB(fileInputStream);
    }

    public static byte[] a__ArrayB(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IOException(z[4]);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byArray = new byte[1024];
        int n = inputStream.read(byArray);
        while (n > -1) {
            byteArrayOutputStream.write(byArray, 0, n);
            n = inputStream.read(byArray);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public int a(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        if (byArray == null) {
            throw new recovered.a.a.b.TypeA(z[0]);
        }
        if (n >= byArray.length || n < 0) {
            throw new recovered.a.a.b.TypeA(z[3] + n + z[2] + byArray.length);
        }
        int n2 = 0;
        try {
            this.c = recovered.a.a.q.TypeB.a__ArrayB(byArray, n + n2, this.b);
        }
        catch (recovered.a.a.q.TypeA typeA) {
            throw new recovered.a.a.b.TypeA(z[5] + typeA.toString());
        }
        return n2 += this.b;
    }

    public int a__I(byte[] byArray) throws recovered.a.a.b.TypeA {
        return this.a(byArray, 0);
    }

    public int b(byte[] byArray, int n) throws recovered.a.a.b.TypeA {
        if (byArray == null) {
            throw new recovered.a.a.b.TypeA(z[0]);
        }
        if (n >= byArray.length || n < 0) {
            throw new recovered.a.a.b.TypeA(z[3] + n + z[2] + byArray.length);
        }
        int n2 = 0;
        try {
            System.arraycopy(this.c, 0, byArray, n + n2, this.c.length);
        }
        catch (Exception exception) {
            throw new recovered.a.a.b.TypeA(z[1] + exception.toString());
        }
        return n2 += this.c.length;
    }

    public int b__I(byte[] byArray) throws recovered.a.a.b.TypeA {
        return this.b(byArray, 0);
    }

    public byte[] a__ArrayB() throws recovered.a.a.b.TypeA {
        int n = this.a__I();
        if (n == 0) {
            return new byte[0];
        }
        byte[] byArray = new byte[n];
        int n2 = this.b(byArray, 0);
        if (n2 < byArray.length) {
            byte[] byArray2 = new byte[n2];
            System.arraycopy(byArray, 0, byArray2, 0, n2);
            byArray = byArray2;
        }
        return byArray;
    }

    public Object clone() {
        try {
            TypeA typeA = (TypeA)super.clone();
            byte[] byArray = this.b__ArrayB();
            if (byArray != null) {
                byte[] byArray2 = new byte[byArray.length];
                System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
                typeA.a__V(byArray2);
            }
            return typeA;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            TypeG typeG = TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
            typeG.e(z[8]);
            return null;
        }
    }
}
