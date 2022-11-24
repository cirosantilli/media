/* slides.jar — org/b/b/e.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.b;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

class TypeE {
    private Logger a;

    public TypeE() {
        this.a(Logger.getRootLogger());
    }

    public TypeE(String string) {
        this.a(Logger.getLogger((String)string));
    }

    public void a(Logger logger) {
        this.a = logger;
    }

    public void a__V(Object object) {
        if (this.a != null && this.a.isEnabledFor((Priority)Level.DEBUG)) {
            this.a.debug(object);
        }
    }

    public void a(Object object, Throwable throwable) {
        if (this.a != null && this.a.isEnabledFor((Priority)Level.DEBUG)) {
            this.a.debug(object, throwable);
        }
    }

    public void b(Object object) {
        if (this.a != null && this.a.isEnabledFor((Priority)Level.INFO)) {
            this.a.info(object);
        }
    }

    public void b(Object object, Throwable throwable) {
        if (this.a != null && this.a.isEnabledFor((Priority)Level.INFO)) {
            this.a.info(object, throwable);
        }
    }

    public void c(Object object) {
        if (this.a != null && this.a.isEnabledFor((Priority)Level.WARN)) {
            this.a.warn(object);
        }
    }

    public void c(Object object, Throwable throwable) {
        if (this.a != null && this.a.isEnabledFor((Priority)Level.WARN)) {
            this.a.warn(object, throwable);
        }
    }

    public void d(Object object) {
        if (this.a != null && this.a.isEnabledFor((Priority)Level.ERROR)) {
            this.a.error(object);
        }
    }

    public void d(Object object, Throwable throwable) {
        if (this.a != null && this.a.isEnabledFor((Priority)Level.ERROR)) {
            this.a.error(object, throwable);
        }
    }

    public void e(Object object) {
        if (this.a != null && this.a.isEnabledFor((Priority)Level.FATAL)) {
            this.a.fatal(object);
        }
    }

    public void e(Object object, Throwable throwable) {
        if (this.a != null && this.a.isEnabledFor((Priority)Level.FATAL)) {
            this.a.fatal(object, throwable);
        }
    }

    public int b__I() {
        if (this.a != null) {
            if (this.a.isEnabledFor((Priority)Level.ALL)) {
                return Integer.MIN_VALUE;
            }
            if (this.a.isEnabledFor((Priority)Level.DEBUG)) {
                return 10000;
            }
            if (this.a.isEnabledFor((Priority)Level.INFO)) {
                return 20000;
            }
            if (this.a.isEnabledFor((Priority)Level.WARN)) {
                return 30000;
            }
            if (this.a.isEnabledFor((Priority)Level.ERROR)) {
                return 40000;
            }
            if (this.a.isEnabledFor((Priority)Level.FATAL)) {
                return 50000;
            }
            return Integer.MAX_VALUE;
        }
        return Integer.MAX_VALUE;
    }
}
