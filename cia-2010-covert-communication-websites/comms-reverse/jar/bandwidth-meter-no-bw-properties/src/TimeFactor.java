/*
 * Factor Force News, factorspeed.jar, TimeFactor.class. Method names follow the archive.
 */
import java.util.Random;

public class TimeFactor {
    private long m_begin;
    private long m_end;
    private boolean m_stopped;
    private long[] m_buffer;
    private long m_hiresFrequency;

    public synchronized void stop() {
        this.readTime();
        this.m_stopped = true;
    }

    public TimeFactor() {
        this.start();
    }

    public double getElapsedTime() {
        this.readTime();
        double d = (double)(this.m_end - this.m_begin) / 1000.0;
        if (d == 0.0) {
            Random random = new Random();
            int n = Math.abs(random.nextInt() % 200) + 700;
            d = (double)n / 100000.0;
        }
        return d;
    }

    public synchronized void start() {
        this.m_stopped = false;
        this.m_begin = System.currentTimeMillis();
    }

    public boolean isHighResolution() {
        return false;
    }

    private synchronized void readTime() {
        if (this.m_stopped) {
            return;
        }
        this.m_end = System.currentTimeMillis();
    }
}
