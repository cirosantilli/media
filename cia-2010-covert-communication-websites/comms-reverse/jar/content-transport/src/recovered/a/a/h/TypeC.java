/* slides.jar — a/a/h/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.h;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TypeC {
    private static String z = "Unable to find resource: ";

    public Properties a(String string, Class clazz) throws IOException {
        InputStream inputStream;
        if (clazz == null) {
            clazz = this.getClass();
        }
        if ((inputStream = clazz.getResourceAsStream(string)) == null) {
            throw new IOException(z + string);
        }
        return this.a__Ljava_util_Properties(inputStream);
    }

    public Properties a__Ljava_util_Properties(File file) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        return this.a__Ljava_util_Properties(bufferedInputStream);
    }

    protected Properties a__Ljava_util_Properties(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }
}
