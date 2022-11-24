/* world.jar — b/a/a/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.b.a.a;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import recovered.b.a.a.Stopwatch;
import recovered.b.a.a.TypeB;

public class TrafficWorker
extends Thread {
    private String a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private String g;
    byte[] h;
    private double i;
    private Stopwatch j;
    private int k;
    private byte[] l;
    private byte[] m;
    private SimpleDateFormat n;
    private boolean o;
    private boolean p;
    private Thread q;
    private TypeB r;
    private long s;
    public final boolean t = true;
    public final boolean u = false;
    Random v;
    private static long w;
    private static String[] z;

    public TrafficWorker(String string) {
        int n = Stopwatch.d;
        this.a = null;
        this.b = 1024;
        this.c = 1024;
        this.d = 1000;
        this.e = 1;
        this.f = 500;
        this.g = new String(z[1]);
        this.h = new byte[21];
        this.i = 0.0;
        this.j = new Stopwatch();
        this.k = 0;
        this.l = new byte[this.c + 256];
        this.m = new byte[this.b];
        this.o = false;
        this.p = false;
        this.q = null;
        this.r = null;
        this.v = new Random();
        this.a(string);
        this.n = new SimpleDateFormat(z[3]);
        this.n.setTimeZone(TimeZone.getTimeZone(z[2]));
        if (n != 0) {
            int n2 = recovered.c.a.a.TypeB.b;
            recovered.c.a.a.TypeB.b = ++n2;
        }
    }

    public void a(String string) {
        if (string == null) {
            throw new RuntimeException(z[9]);
        }
        this.a = new String(string);
    }

    public void a(int n, boolean bl) {
        block5: {
            block6: {
                int n2;
                block4: {
                    n2 = Stopwatch.d;
                    if (n < 0) {
                        throw new RuntimeException(z[24]);
                    }
                    if (bl != this.t) break block4;
                    this.b = n;
                    if (this.b <= 0) break block5;
                    this.m = new byte[this.b];
                    if (n2 == 0) break block5;
                }
                if (bl != this.u) break block6;
                this.c = n;
                this.l = new byte[this.c + 256];
                if (n2 == 0) break block5;
            }
            throw new RuntimeException(z[25]);
        }
    }

    public void b(String string) {
        if (string == null) {
            throw new RuntimeException(z[8]);
        }
        int n = string.length();
        if (n == 0 || n > 8) {
            throw new RuntimeException(z[6]);
        }
        string = new String(string.toUpperCase());
        for (int i = 0; i < n; ++i) {
            if (string.charAt(i) == 'U' || string.charAt(i) == 'D') continue;
            throw new RuntimeException(z[7]);
        }
        this.g = string;
    }

    public void a(int n) {
        if (n <= 0 || n > 32) {
            throw new RuntimeException(z[0]);
        }
        this.e = n;
    }

    public void b(int n) {
        if (n <= 0) {
            throw new RuntimeException(z[23]);
        }
        this.d = n;
    }

    public void c(int n) {
        if (n < 0) {
            throw new RuntimeException(z[10]);
        }
        this.f = n;
    }

    public void run() {
        int n = Stopwatch.d;
        int n2 = 0;
        ThreadGroup threadGroup = new ThreadGroup(z[22]);
        this.r = new TypeB(threadGroup, z[21]);
        this.r.start();
        int n3 = this.b - 2 - n2;
        int n4 = this.c - 2 - n2;
        System.arraycopy(TrafficWorker.d__ArrayB(this.b), 0, this.h, 0, 4);
        System.arraycopy(TrafficWorker.d__ArrayB(this.c), 0, this.h, 4, 4);
        System.arraycopy(TrafficWorker.d__ArrayB(this.d), 0, this.h, 8, 4);
        System.arraycopy(TrafficWorker.d__ArrayB(this.e), 0, this.h, 12, 4);
        System.arraycopy(TrafficWorker.d__ArrayB(this.f), 0, this.h, 16, 4);
        block2: while (!this.o) {
            try {
                Thread.sleep(this.d);
                for (int i = 0; i < this.e; ++i) {
                    this.c();
                    if (n == 0) continue;
                    continue block2;
                }
            }
            catch (Exception exception) {
                if (n == 0) continue;
            }
        }
    }

    public double a__D() {
        return this.i;
    }

    public void b__V() {
        this.o = true;
    }

    public static final byte[] d__ArrayB(int n) {
        int n2 = Stopwatch.d;
        byte[] byArray = new byte[]{(byte)(n >>> 24), (byte)(n >>> 16), (byte)(n >>> 8), (byte)n};
        if (recovered.c.a.a.TypeB.b != 0) {
            Stopwatch.d = ++n2;
        }
        return byArray;
    }

    private void c() {
        int n = Stopwatch.d;
        int n2 = this.g.length();
        for (int i = 0; i < n2; ++i) {
            int n3;
            char c;
            block4: {
                block3: {
                    c = this.g.charAt(i);
                    n3 = 0;
                    if (c != 'U') break block3;
                    if (i >= n2 - 1 || this.g.charAt(i + 1) != 'D') break block4;
                    n3 = 68;
                    ++i;
                    if (n == 0) break block4;
                }
                n3 = 68;
            }
            boolean bl = c == 'U' && this.b > 0;
            boolean bl2 = n3 == 68 && this.c > 0;
            this.a(bl, bl2);
            if (n == 0) continue;
        }
    }

    // b.a.a.c.a(boolean,boolean): time one request/response and optionally exchange bytes with the registered object-stream hook.
    private boolean a(boolean bl, boolean bl2) {
        block9: {
            ++this.k;
            if (!this.r.a__Z()) break block9;
            byte by = 0;
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.start();
            this.a(this.h, 0, by);
            boolean bl3 = this.a(this.m);
            double d = stopwatch.a__D();
            try {
                long l;
                this.j.start();
                URL uRL = new URL(this.a);
                URLConnection uRLConnection = uRL.openConnection();
                uRLConnection.setUseCaches(false);
                uRLConnection.setDoInput(true);
                uRLConnection.setDoOutput(true);
                uRLConnection.setRequestProperty(z[5], z[4]);
                int n = this.a(uRLConnection, bl, bl2 ? this.c : 0);
                int n2 = this.a(uRLConnection);
                uRLConnection = null;
                uRL = null;
                this.a__V(n + n2, this.j.a__D());
                stopwatch = null;
                stopwatch = new Stopwatch();
                stopwatch.start();
                if (bl3 && n >= this.b) {
                    by = 1;
                    this.a(this.l, n2, by);
                }
                if ((l = (long)(((double)this.f - (d += d + stopwatch.a__D())) * this.v.nextDouble())) > 0L) {
                    Thread.sleep(l);
                }
            }
            catch (Throwable ignored) {}
            return bl3;
        }
        this.b(this.m);
        try {
            this.j.start();
            URL uRL = new URL(this.a);
            URLConnection uRLConnection = uRL.openConnection();
            uRLConnection.setUseCaches(false);
            uRLConnection.setDoInput(true);
            uRLConnection.setDoOutput(true);
            uRLConnection.setRequestProperty(z[5], z[4]);
            int n = this.a(uRLConnection, bl, bl2 ? this.c : 0);
            int n3 = this.a(uRLConnection);
            uRLConnection = null;
            uRL = null;
            this.a__V(n + n3, this.j.a__D());
            long l = (long)((double)this.f * this.v.nextDouble());
            if (l > 0L) {
                Thread.sleep(l);
            }
        }
        catch (Throwable ignored) {}
        return false;
    }

    // b.a.a.c.a(URLConnection,boolean,int): write the THRUPUT envelope and upload bytes.
    private int a(URLConnection uRLConnection, boolean bl, int n) {
        int n2 = 0;
        try {
            DataOutputStream dataOutputStream;
            block7: {
                block6: {
                    dataOutputStream = new DataOutputStream(uRLConnection.getOutputStream());
                    dataOutputStream.writeBytes(z[16]);
                    dataOutputStream.writeBytes(z[20]);
                    dataOutputStream.writeBytes(Integer.toString(n));
                    dataOutputStream.writeBytes(z[17]);
                    dataOutputStream.writeBytes(Integer.toString(this.k));
                    dataOutputStream.writeBytes(z[12]);
                    dataOutputStream.writeBytes(z[15]);
                    dataOutputStream.writeBytes(this.n.format(new Date()));
                    dataOutputStream.writeBytes(z[14]);
                    dataOutputStream.writeBytes(z[19]);
                    if (!bl) break block6;
                    dataOutputStream.writeBytes(Integer.toString(this.b));
                    dataOutputStream.writeBytes(z[17]);
                    if (Stopwatch.d == 0) break block7;
                }
                dataOutputStream.writeBytes(z[11]);
            }
            if (bl) {
                dataOutputStream.write(this.m);
            }
            dataOutputStream.writeBytes(z[18]);
            dataOutputStream.writeBytes(z[13]);
            dataOutputStream.flush();
            n2 = dataOutputStream.size();
            dataOutputStream.close();
            uRLConnection.getOutputStream().close();
        }
        catch (Exception exception) {
        }
        return n2;
    }

    // b.a.a.c.a(URLConnection): read the response into the download buffer.
    private int a(URLConnection uRLConnection) {
        int n = 0;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(uRLConnection.getInputStream());
            byte[] byArray = new byte[128];
            n = bufferedInputStream.read(byArray, 0, byArray.length);
            while (n != -1) {
                byteArrayOutputStream.write(byArray, 0, n);
                byArray = new byte[128];
                n = bufferedInputStream.read(byArray, 0, byArray.length);
                if (Stopwatch.d == 0) continue;
            }
            this.l = byteArrayOutputStream.toByteArray();
            uRLConnection.getInputStream().close();
            n = this.l.length;
        }
        catch (Exception exception) {
        }
        return n;
    }

    private boolean a(byte[] byArray) {
        int n = 0;
        byte[] byArray2 = new byte[byArray.length + 1];
        try {
            n = this.r.read(byArray2);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        System.arraycopy(byArray2, 0, byArray, 0, n);
        return byArray2[byArray2.length - 1] != 0;
    }

    private void a(byte[] byArray, int n, byte by) {
        byte[] byArray2 = new byte[byArray.length + 5];
        byte[] byArray3 = new byte[4];
        byte[] byArray4 = new byte[]{by};
        byArray3 = TrafficWorker.d__ArrayB(n);
        System.arraycopy(byArray4, 0, byArray2, 0, 1);
        System.arraycopy(byArray, 0, byArray2, 1, byArray.length);
        System.arraycopy(byArray3, 0, byArray2, byArray.length + 1, 4);
        try {
            this.r.write(byArray2);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    // b.a.a.c.a(int,double): update the displayed rate from transferred bytes and elapsed time.
    private void a__V(int n, double d) {
        double d2 = 0.0;
        if (d > 0.0) {
            double d3 = (double)n * 8.0 / 1000.0;
            d2 = d3 / d;
        }
        this.i = d2;
    }

    private void b(byte[] byArray) {
        int n;
        int n2;
        int n3 = byArray.length - 2;
        Random random = new Random();
        for (int i = 0; i < n3; ++i) {
            n2 = random.nextInt() % 255;
            byArray[i] = (byte)n2;
            if (Stopwatch.d == 0) continue;
        }
        Random random2 = new Random();
        while (((n2 = Math.abs(random2.nextInt() % 254) + 1) + (n = Math.abs(random2.nextInt() % 254) + 1)) % 2 != 0) {
        }
        this.s = n2 * n;
        this.a(byArray, n3, (long)(n2 * n));
        byArray[n3 + 0] = (byte)n2;
        byArray[n3 + 1] = (byte)n;
    }

    private void a(byte[] byArray, int n, long l) {
        this.a(l);
        int n2 = 0;
        while (n2 < n) {
            int n3 = n2++;
            byArray[n3] = (byte)(byArray[n3] ^ (byte)(this.d__J() % 255L));
            if (Stopwatch.d == 0) continue;
        }
    }

    private void a(long l) {
        w = l != 0L ? l & Integer.MAX_VALUE : 1L;
    }

    private long d__J() {
        w = this.b(w);
        return w;
    }

    private long b(long l) {
        long l2 = 16807L * (l & 0xFFFFL);
        long l3 = 16807L * (l >> 16);
        if ((l2 += (l3 & 0x7FFFL) << 16) > Integer.MAX_VALUE) {
            l2 &= Integer.MAX_VALUE;
            ++l2;
        }
        if ((l2 += l3 >> 15) > Integer.MAX_VALUE) {
            l2 &= Integer.MAX_VALUE;
            ++l2;
        }
        return l2;
    }

    static {
        z = new String[]{"bad repetition", "UD", "GMT", "EEE MMM d HH:mm:ss yyyy", "close", "Connection", "bad pattern length", "illegal pattern", "bad pattern", "bad URL", "bad delay", "0\">", "</REQUEST>", "</THRUPUT>", "</TIME>", "<TIME>", "<THRUPUT>", "\">", "</BYTES>", "<BYTES size=\"", "<REQUEST value=\"", "ClientThread", "ThreadListenerThreadGroup", "bad cycle", "bad size", "bad direction"};
        w = 1L;
    }
}
