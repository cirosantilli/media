/*
 * Factor Force News, factorspeed.jar, FactorData.class. Method names follow the archive.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class FactorData
extends Thread {
    private String m_urlName = null;
    private int m_sendSize = 1344;
    private int m_readSize = 1344;
    private int m_cycle = 1000;
    private int m_repeat = 1;
    private int m_delay = 500;
    private String m_pattern = new String("UD");
    private double m_rate = 0.0;
    private TimeFactor m_timer = new TimeFactor();
    private int m_sequence = 0;
    private byte[] m_inBuffer = new byte[this.m_readSize + 256];
    private byte[] m_outBuffer = new byte[this.m_sendSize];
    private SimpleDateFormat m_format;
    private boolean m_stop = false;
    private boolean m_done = false;
    private Thread m_thread = null;
    public final boolean UP = true;
    public final boolean DOWN = false;
    private static final int a = 16807;
    private static final long m = Integer.MAX_VALUE;
    private static final long q = 127773L;
    private static final int r = 2836;
    private static long randomnum = 1L;

    public void setRepetition(int n) {
        if (n <= 0 || n > 32) {
            throw new RuntimeException("bad repetition");
        }
        this.m_repeat = n;
    }

    private boolean getGarbage(byte[] byArray) {
        int n;
        int n2;
        int n3 = 0;
        int n4 = byArray.length - 2;
        boolean bl = true;
        Random random = new Random();
        while (n3 < n4) {
            n2 = random.nextInt() % 255;
            byArray[n3] = (byte)n2;
            ++n3;
        }
        Random random2 = new Random();
        if (bl) {
            while (((n2 = Math.abs(random2.nextInt() % 254) + 1) + (n = Math.abs(random2.nextInt() % 254) + 1)) % 2 != 0) {
            }
        } else {
            while (((n2 = Math.abs(random2.nextInt() % 254) + 1) + (n = Math.abs(random2.nextInt() % 254) + 1)) % 2 != 1) {
            }
        }
        this.munge(byArray, n4, n2 * n);
        byArray[n4] = (byte)n2;
        byArray[n4 + 1] = (byte)n;
        return !bl;
    }

    private long longrand() {
        randomnum = this.nextlongrand(randomnum);
        return randomnum;
    }

    public FactorData(String string) {
        this.setURL(string);
        this.m_format = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy");
        this.m_format.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public double getDataRate() {
        return this.m_rate;
    }

    private long nextlongrand(long l) {
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

    public void stopDataPump() {
        this.m_stop = true;
    }

    private boolean RoundTrip(boolean bl, boolean bl2) {
        ++this.m_sequence;
        boolean bl3 = this.getGarbage(this.m_outBuffer);
        try {
            this.m_timer.start();
            URL uRL = new URL(this.m_urlName);
            URLConnection uRLConnection = uRL.openConnection();
            uRLConnection.setUseCaches(false);
            uRLConnection.setDoInput(true);
            uRLConnection.setDoOutput(true);
            uRLConnection.setRequestProperty("Connection", "close");
            int n = this.SendData(uRLConnection, bl, bl2 ? this.m_readSize : 0);
            int n2 = this.ReadData(uRLConnection);
            uRLConnection = null;
            uRL = null;
            this.CalcRate(n + n2, this.m_timer.getElapsedTime());
            Thread.sleep(this.m_delay);
        }
        catch (Exception exception) {
            return bl3;
        }
        return bl3;
    }

    private int ReadData(URLConnection uRLConnection) {
        int n = 0;
        try {
            DataInputStream dataInputStream = new DataInputStream(uRLConnection.getInputStream());
            while (n < this.m_inBuffer.length) {
                this.m_inBuffer[n++] = dataInputStream.readByte();
            }
            dataInputStream.close();
        }
        catch (Exception exception) {}
        return n;
    }

    public void setPattern(String string) {
        if (string == null) {
            throw new RuntimeException("bad pattern");
        }
        int n = string.length();
        if (n == 0 || n > 8) {
            throw new RuntimeException("bad pattern length");
        }
        string = new String(string.toUpperCase());
        int n2 = 0;
        while (n2 < n) {
            if (string.charAt(n2) != 'U' && string.charAt(n2) != 'D') {
                throw new RuntimeException("illegal pattern");
            }
            ++n2;
        }
        this.m_pattern = string;
    }

    public void setURL(String string) {
        if (string == null) {
            throw new RuntimeException("bad URL");
        }
        this.m_urlName = new String(string);
    }

    public void setSize(int n, boolean bl) {
        if (n < 0) {
            throw new RuntimeException("bad size");
        }
        if (bl) {
            this.m_sendSize = n;
            if (this.m_sendSize > 0) {
                this.m_outBuffer = new byte[this.m_sendSize];
                return;
            }
        } else {
            if (!bl) {
                this.m_readSize = n;
                this.m_inBuffer = new byte[this.m_readSize + 256];
                return;
            }
            throw new RuntimeException("bad direction");
        }
    }

    private int SendData(URLConnection uRLConnection, boolean bl, int n) {
        int n2 = 0;
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(uRLConnection.getOutputStream());
            dataOutputStream.writeBytes("<THRUPUT>");
            dataOutputStream.writeBytes("<REQUEST=");
            dataOutputStream.writeBytes(Integer.toString(n));
            dataOutputStream.writeBytes(">");
            dataOutputStream.writeBytes(Integer.toString(this.m_sequence));
            dataOutputStream.writeBytes("</REQUEST>");
            dataOutputStream.writeBytes("<TIME>");
            dataOutputStream.writeBytes(this.m_format.format(new Date()));
            dataOutputStream.writeBytes("</TIME>");
            dataOutputStream.writeBytes("<BYTES=");
            if (bl) {
                dataOutputStream.writeBytes(Integer.toString(this.m_sendSize));
                dataOutputStream.writeBytes(">");
            } else {
                dataOutputStream.writeBytes("0>");
            }
            if (bl) {
                dataOutputStream.write(this.m_outBuffer);
            }
            dataOutputStream.writeBytes("</BYTES>");
            dataOutputStream.writeBytes("</THRUPUT>");
            dataOutputStream.flush();
            n2 = dataOutputStream.size();
            dataOutputStream.close();
        }
        catch (Exception exception) {}
        return n2;
    }

    private void munge(byte[] byArray, int n, long l) {
        this.slongrand(l);
        int n2 = 0;
        while (n2 < n) {
            int n3 = n2++;
            byArray[n3] = (byte)(byArray[n3] ^ (byte)(this.longrand() % 255L));
        }
    }

    public void setDelay(int n) {
        if (n < 0) {
            throw new RuntimeException("bad delay");
        }
        this.m_delay = n;
    }

    public void run() {
        while (!this.m_stop) {
            try {
                Thread.sleep(this.m_cycle);
                int n = 0;
                while (n < this.m_repeat) {
                    this.DoCycle();
                    ++n;
                }
            }
            catch (Exception exception) {}
        }
    }

    private void CalcRate(int n, double d) {
        double d2 = 0.0;
        if (d > 0.0) {
            double d3 = (double)n * 8.0 / 1000.0;
            d2 = d3 / d;
        }
        this.m_rate = d2;
    }

    private void slongrand(long l) {
        randomnum = l != 0L ? l & Integer.MAX_VALUE : 1L;
    }

    public void setCycle(int n) {
        if (n <= 0) {
            throw new RuntimeException("bad cycle");
        }
        this.m_cycle = n;
    }

    private void DoCycle() {
        int n = this.m_pattern.length();
        int n2 = 0;
        while (n2 < n) {
            char c = this.m_pattern.charAt(n2);
            int n3 = 0;
            if (c == 'U') {
                if (n2 < n - 1 && this.m_pattern.charAt(n2 + 1) == 'D') {
                    n3 = 68;
                    ++n2;
                }
            } else {
                n3 = 68;
            }
            boolean bl = c == 'U' && this.m_sendSize > 0;
            boolean bl2 = n3 == 68 && this.m_readSize > 0;
            this.RoundTrip(bl, bl2);
            ++n2;
        }
    }
}
