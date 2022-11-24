package replay.display;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

// Some obfuscated types share a name with a package. Others overload methods
// by return type alone, as in c.b.o.a.d.b(). Resolve against JVM signatures.
final class ArchivedApi {
    static Object create(String name, Object... args) throws Exception {
        Class<?> type = Class.forName(name);
        for (Constructor<?> constructor : type.getConstructors()) {
            if (score(constructor.getParameterTypes(), args) >= 0) {
                return constructor.newInstance(args);
            }
        }
        throw new NoSuchMethodException(name + " constructor");
    }

    static Object invoke(Object target, String name, Class<?> result, Object... args) throws Exception {
        Class<?> type = target instanceof Class ? (Class<?>) target : target.getClass();
        Method best = null;
        int bestScore = Integer.MAX_VALUE;
        for (Method method : type.getMethods()) {
            if (!name.equals(method.getName()) || result != method.getReturnType()) continue;
            int score = score(method.getParameterTypes(), args);
            if (score >= 0 && score < bestScore) {
                best = method;
                bestScore = score;
            }
        }
        if (best == null) throw new NoSuchMethodException(type.getName() + "." + name);
        try {
            return best.invoke(target instanceof Class ? null : target, args);
        } catch (InvocationTargetException error) {
            throw new IllegalStateException(type.getName() + "." + name, error.getCause());
        }
    }

    private static int score(Class<?>[] parameters, Object[] args) {
        if (parameters.length != args.length) return -1;
        int score = 0;
        for (int i = 0; i < args.length; ++i) {
            if (args[i] == null || !boxed(parameters[i]).isInstance(args[i])) return -1;
            if (args[i].getClass() != boxed(parameters[i])) score += parameters[i] == Object.class ? 2 : 1;
        }
        return score;
    }

    private static Class<?> boxed(Class<?> type) {
        if (type == int.class) return Integer.class;
        if (type == long.class) return Long.class;
        if (type == char.class) return Character.class;
        return type;
    }

    static void set(Object target, String name, Object... args) throws Exception {
        invoke(target, name, void.class, args);
    }

    static byte[] bytes(Object target, String name, Object... args) throws Exception {
        return (byte[]) invoke(target, name, byte[].class, args);
    }

    static Object listener(String name, InvocationHandler callback) throws Exception {
        return Proxy.newProxyInstance(ArchivedApi.class.getClassLoader(),
                                      new Class<?>[]{Class.forName(name)}, callback);
    }

    private ArchivedApi() {}
}
