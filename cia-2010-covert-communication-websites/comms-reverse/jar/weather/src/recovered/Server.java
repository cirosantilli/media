/* infoweather.jar — Server.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.awt.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLConnection;
import recovered.InfoWeatherApplet;
import recovered.WeatherCodec;

public class Server {
    public List initialList = new List();
    private String a;
    private String[] b = new String[5];
    private WeatherCodec c = new WeatherCodec();
    public static int GET_WEATHER_FUNCTION = 1;
    public static int GET_CITIES_FUNCTION = 2;
    public static int SET_CITIES_FUNCTION = 3;

    public Server(String string) {
        this.a = string;
    }

    public String getCities() {
        b[0] = null;
        boolean[] mode = new boolean[1];
        String request = c.formatQuery(0, GET_CITIES_FUNCTION, b, mode);
        String reply = doPOST("getCities", request, mode[0]);
        if (reply.startsWith("ERROR")) return reply;
        int start = 0, end;
        while ((end = reply.indexOf("|", start)) > 0) {
            initialList.add(reply.substring(start, end));
            start = end + 1;
        }
        initialList.add(reply.substring(start));
        return null;
    }

    public String setCities(String string, String string2, String string3) {
        String string4 = null;
        String string5 = "setCities";
        this.b[0] = string;
        this.b[1] = string2;
        this.b[2] = string3;
        boolean[] blArray = new boolean[1];
        String string6 = this.c.formatQuery(0, SET_CITIES_FUNCTION, this.b, blArray);
        string4 = this.doPOST(string5, string6, blArray[0]);
        return string4;
    }

    public String doPOST(String string, String string2, boolean bl) {
        boolean bl2 = InfoWeatherApplet.N;
        String string3 = null;
        int n = 2000;
        try {
            URL uRL = new URL(this.a);
            URLConnection uRLConnection = uRL.openConnection();
            uRLConnection.setDoInput(true);
            uRLConnection.setDoOutput(true);
            uRLConnection.setUseCaches(false);
            uRLConnection.setRequestProperty("Connection", "close");
            DataOutputStream dataOutputStream = new DataOutputStream(uRLConnection.getOutputStream());
            dataOutputStream.writeBytes(string2);
            dataOutputStream.flush();
            dataOutputStream.close();
            DataInputStream dataInputStream = new DataInputStream(uRLConnection.getInputStream());
            byte[] byArray = new byte[n];
            int n2 = 0;
            int n3 = 0;
            do {
                try {
                    n3 = dataInputStream.read(byArray, n2, byArray.length - n2);
                }
                catch (Exception exception) {
                    System.out.println("Exception reading from host: " + exception.toString());
                    n3 = -1;
                }
                if (n3 > 0) {
                    n2 += n3;
                }
                if (byArray.length - n2 > 0) continue;
                byArray = this.a__ArrayB(byArray);
            } while (n3 > 0);
            dataInputStream.close();
            string3 = this.c.formatOutput(n2, byArray, bl);
            if (string3 == null) {
                string3 = "ERROR: response from server is null";
            }
        }
        catch (Exception exception) {
            string3 = "ERROR : Error in " + string + "(" + exception.toString() + " -  " + exception.getMessage() + ")";
        }
        return string3;
    }

    public String getWeather(String string, String string2, int n) {
        String string3 = null;
        String string4 = "getWeather";
        this.b[0] = string;
        this.b[1] = string2;
        boolean[] blArray = new boolean[1];
        String string5 = this.c.formatQuery(n, GET_WEATHER_FUNCTION, this.b, blArray);
        string3 = this.doPOST(string4, string5, blArray[0]);
        return string3;
    }

    private byte[] a__ArrayB(byte[] byArray) {
        boolean bl = InfoWeatherApplet.N;
        byte[] byArray2 = new byte[2 * byArray.length];
        int n = 0;
        while (n < byArray.length) {
            byArray2[n] = byArray[n];
            ++n;
        }
        return byArray2;
    }

    private static String a__Ljava_lang_String(String string) {
        char[] cArray = string.toCharArray();
        int n = cArray.length;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int n4 = n2;
            char c = cArray[n4];
            switch (n2 % 5) {
                case 0: {
                    n3 = 44;
                    break;
                }
                case 1: {
                    n3 = 24;
                    break;
                }
                case 2: {
                    n3 = 118;
                    break;
                }
                case 3: {
                    n3 = 4;
                    break;
                }
                default: {
                    n3 = 98;
                }
            }
            cArray[n4] = (char)(c ^ n3);
            ++n2;
        }
        return new String(cArray);
    }
}
