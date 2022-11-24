/* aeronet.jar — a/a/b/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.b;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import recovered.a.a.a.TypeA;
import recovered.b.a.TypeC;

public class Configuration {
    private static Configuration a;
    private Properties b;
    private static String[] z;

    private Configuration() {
    }

    public static Configuration a__La_a_b_d() {
        return a;
    }

    public void b__V() throws IOException {
        String string = this.c__Ljava_lang_String();
        Properties properties = new Properties();
        properties.load(new ByteArrayInputStream(string.getBytes()));
        String string2 = properties.getProperty(z[1], z[2]);
        this.b = this.b__Ljava_util_Properties(string2);
    }

    public String a__Ljava_lang_String(String string) {
        String string2 = this.b.getProperty(string);
        return string2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String c__Ljava_lang_String() throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(z[0])));
            StringBuffer stringBuffer = new StringBuffer();
            String string = bufferedReader.readLine();
            while (string != null) {
                stringBuffer.append(string.replace(':', '=') + "\n");
                string = bufferedReader.readLine();
            }
            String string2 = stringBuffer.toString();
            return string2;
        }
        finally {
            bufferedReader.close();
        }
    }

    private Properties b__Ljava_util_Properties(String string) throws IOException {
        byte[] byArray = TypeC.a(this.getClass().getResourceAsStream(string));
        byte[] byArray2 = TypeA.a__ArrayB(byArray);
        Properties properties = new Properties();
        properties.load(new ByteArrayInputStream(byArray2));
        return properties;
    }

    static {
        z = new String[]{"/META-INF/MANIFEST.MF", "Settings", "applet.configs"};
        a = new Configuration();
    }
}
