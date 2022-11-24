/* slides.jar — org/b/g/h.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.g;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import recovered.org.b.g.TypeG;

public class TypeH {
    public static int a__I(byte[] byArray) {
        int n = 0;
        int n2 = 255;
        int n3 = 4;
        if (byArray == null) {
            byArray = new byte[]{};
        }
        if (byArray.length < n3) {
            n3 = byArray.length;
        }
        for (int i = 0; i < n3; ++i) {
            int n4 = (byArray[i] & n2) << i * 8;
            n += n4;
        }
        return n;
    }

    public static byte[] a__ArrayB(long l) {
        long l2 = 255L;
        byte[] byArray = new byte[8];
        for (int i = 0; i < 8; ++i) {
            byArray[i] = (byte)(l >> i * 8 & l2);
        }
        return byArray;
    }

    public static long b__J(byte[] byArray) {
        long l = 0L;
        long l2 = 255L;
        int n = 8;
        if (byArray == null) {
            byArray = new byte[]{};
        }
        if (byArray.length < n) {
            n = byArray.length;
        }
        for (int i = 0; i < n; ++i) {
            long l3 = ((long)byArray[i] & l2) << i * 8;
            l += l3;
        }
        return l;
    }

    public static boolean a__Z(byte[] byArray, byte[] byArray2) {
        if (byArray == null && byArray2 == null) {
            return true;
        }
        if (byArray == null || byArray2 == null) {
            return false;
        }
        if (byArray.length != byArray2.length) {
            return false;
        }
        boolean bl = true;
        for (int i = 0; i < byArray.length && bl; ++i) {
            if (byArray[i] == byArray2[i]) continue;
            bl = false;
        }
        return bl;
    }

    public static byte[] b(byte[] byArray, byte[] byArray2) {
        if (byArray == null && byArray2 == null) {
            return null;
        }
        if (byArray == null || byArray.length == 0) {
            return byArray2;
        }
        if (byArray2 == null || byArray2.length == 0) {
            return byArray;
        }
        byte[] byArray3 = new byte[byArray.length + byArray2.length];
        System.arraycopy(byArray, 0, byArray3, 0, byArray.length);
        System.arraycopy(byArray2, 0, byArray3, byArray.length, byArray2.length);
        return byArray3;
    }

    public static byte[] a__ArrayB(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n = 128;
        byte[] byArray = new byte[n];
        int n2 = 0;
        while (inputStream.available() >= 0 && n2 >= 0) {
            n2 = inputStream.read(byArray, 0, byArray.length);
            if (n2 < 1) continue;
            byteArrayOutputStream.write(byArray, 0, n2);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] d(byte[] byArray) throws TypeG {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(byArray, 0, byArray.length);
            gZIPOutputStream.finish();
            gZIPOutputStream.close();
            ((OutputStream)byteArrayOutputStream).close();
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException iOException) {
            throw new TypeG(iOException);
        }
    }

    public static byte[] e(byte[] byArray) throws TypeG {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(gZIPInputStream);
            byte[] byArray2 = TypeH.a__ArrayB(bufferedInputStream);
            gZIPInputStream.close();
            ((InputStream)byteArrayInputStream).close();
            return byArray2;
        }
        catch (IOException iOException) {
            throw new TypeG(iOException);
        }
    }
}
