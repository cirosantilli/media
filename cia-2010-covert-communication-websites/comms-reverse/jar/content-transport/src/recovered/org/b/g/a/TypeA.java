/* slides.jar — org/b/g/a/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.g.a;

import java.util.Random;
import java.util.Vector;

public class TypeA {
    private static int a = 3;
    private Class b;
    private Class c;
    private Class d;

    protected TypeA(Class clazz, Class clazz2, Class clazz3) {
        this.b = clazz;
        this.c = clazz2;
        this.d = clazz3;
    }

    public Class a__Ljava_lang_Class() {
        return this.b;
    }

    public static TypeA a__Lorg_b_g_a_a(String[] stringArray) {
        Vector vector = new Vector();
        for (int i = 0; i < stringArray.length; ++i) {
            try {
                vector.addElement(Class.forName(stringArray[i]));
                continue;
            }
            catch (Exception exception) {
            }
        }
        Class[] classArray = new Class[vector.size()];
        for (int i = 0; i < vector.size(); ++i) {
            classArray[i] = (Class)vector.elementAt(i);
        }
        return TypeA.a(classArray);
    }

    public static TypeA a(Class[] classArray) {
        Class clazz = null;
        Class clazz2 = null;
        Class clazz3 = null;
        if (classArray != null && classArray.length > 0) {
            Random random = new Random();
            long l = random.nextLong();
            for (int i = 0; i < classArray.length && (clazz2 == null || clazz3 == null); ++i) {
                try {
                    int n;
                    byte[] byArray;
                    Class clazz4 = classArray[i];
                    if (clazz4 == null) continue;
                    boolean bl = true;
                    boolean bl2 = true;
                    Random random2 = (Random)clazz4.newInstance();
                    random2.setSeed(random.nextLong());
                    byte[] byArray2 = new byte[4];
                    random2.nextBytes(byArray2);
                    if (clazz == null) {
                        clazz = clazz4;
                    }
                    Random random3 = (Random)clazz4.newInstance();
                    Random random4 = (Random)clazz4.newInstance();
                    random2.setSeed(l);
                    random3.setSeed(l);
                    random4.setSeed(l);
                    if (a < 1) {
                        a = 1;
                    }
                    byte[] byArray3 = new byte[a * 8];
                    random4.nextBytes(byArray3);
                    if (clazz3 == null) {
                        byArray = new byte[a];
                        random2.nextBytes(byArray);
                        for (n = 0; n < a && bl2; ++n) {
                            if (byArray[n] == byArray3[n]) continue;
                            bl2 = false;
                        }
                        if (bl2) {
                            clazz3 = clazz4;
                        }
                    }
                    if (clazz2 != null) continue;
                    byArray = new byte[a];
                    random3.nextBytes(byArray);
                    for (n = 0; n < a && bl; ++n) {
                        if (byArray[n] == byArray3[n]) continue;
                        bl = false;
                    }
                    if (!bl) continue;
                    clazz2 = clazz4;
                    continue;
                }
                catch (Exception exception) {
                    }
            }
        }
        return new TypeA(clazz, clazz2, clazz3);
    }

    public Random d__Ljava_util_Random() {
        try {
            return (Random)this.a__Ljava_lang_Class().newInstance();
        }
        catch (Exception exception) {
            return new Random();
        }
    }
}
