// Compile-time declarations referenced by org.b.b.d and org.b.b.e.
package org.apache.log4j;

public class Category {
    public native boolean isEnabledFor(Priority priority);
    public native void setLevel(Level level);
    public native void removeAllAppenders();
    public native void debug(Object message);
    public native void debug(Object message, Throwable error);
    public native void info(Object message);
    public native void info(Object message, Throwable error);
    public native void warn(Object message);
    public native void warn(Object message, Throwable error);
    public native void error(Object message);
    public native void error(Object message, Throwable error);
    public native void fatal(Object message);
    public native void fatal(Object message, Throwable error);
}
