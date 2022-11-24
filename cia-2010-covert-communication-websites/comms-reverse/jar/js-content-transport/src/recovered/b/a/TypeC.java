/* aeronet.jar — b/a/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.b.a;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import recovered.b.a.TypeD;

public class TypeC {
    private static String[] z = new String[]{"StreamReader", "Unable to read stream fully within alloted time.", "Slice size out of range."};

    public static long a__J(byte[] byArray) {
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

    public static byte[] a(byte[] byArray, int n, int n2) throws ArrayIndexOutOfBoundsException {
        if (n + n2 > byArray.length || n2 < 0) {
            throw new ArrayIndexOutOfBoundsException(z[2]);
        }
        byte[] byArray2 = new byte[n2];
        System.arraycopy(byArray, n, byArray2, 0, byArray2.length);
        return byArray2;
    }

    public static byte[] a(InputStream inputStream) throws IOException {
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

    public static byte[] a(InputStream inputStream, long l) throws IOException {
        if (l < 0L) {
            return TypeC.a(inputStream);
        }
        InputStream[] inputStreamArray = new InputStream[]{inputStream};
        ByteArrayOutputStream[] byteArrayOutputStreamArray = new ByteArrayOutputStream[]{new ByteArrayOutputStream()};
        boolean[] blArray = new boolean[]{false};
        IOException[] iOExceptionArray = new IOException[]{null};
        TypeD typeD = new TypeD(z[0], inputStreamArray, byteArrayOutputStreamArray, iOExceptionArray, blArray);
        typeD.start();
        long l2 = System.currentTimeMillis() + l;
        while (!blArray[0] && System.currentTimeMillis() <= l2) {
            try {
                Thread.currentThread();
                Thread.sleep(Math.min(5L, l));
            }
            catch (InterruptedException interruptedException) {}
        }
        if (!blArray[0]) {
            typeD.a__V();
            throw new IOException(z[1]);
        }
        if (iOExceptionArray[0] != null) {
            throw iOExceptionArray[0];
        }
        return byteArrayOutputStreamArray[0].toByteArray();
    }
}
