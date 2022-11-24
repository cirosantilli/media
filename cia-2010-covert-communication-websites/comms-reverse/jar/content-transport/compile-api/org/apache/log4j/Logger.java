// Compile-time declarations referenced by org.b.b.d and org.b.b.e.
package org.apache.log4j;

public class Logger extends Category {
    public static native Logger getRootLogger();
    public static native Logger getLogger(String name);
}
