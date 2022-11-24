/* infoweather.jar — a$6.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import java.util.Random;
import recovered.InfoWeatherApplet;
import recovered.PayloadCodec;
import recovered.RandomData;

public class WeatherCodec {
    private Random a = new Random();
    private final double b = 2.0;
    private static final int c = 0;
    private static final int d = 1;
    private static final int e = 2;
    private static final int f = 3;

    private byte[] a__ArrayB(int n) {
        boolean bl = InfoWeatherApplet.N;
        byte[] byArray = new byte[n];
        int n2 = 0;
        while (n2 < byArray.length) {
            byArray[n2] = (byte)(Math.random() * 1000.0 % 255.0);
            ++n2;
        }
        return byArray;
    }

    public String formatOutput(int n, byte[] byArray, boolean bl) {
        String string = null;
        if (n == 0) {
            return string;
        }
        byArray = this.a__ArrayB(byArray, n);
        string = this.a__Ljava_lang_String(byArray);
        return string;
    }

    private String a(boolean bl) {
        int n;
        boolean bl2 = InfoWeatherApplet.N;
        int n2 = 48 + Math.abs(this.a.nextInt() % 10);
        if (bl) {
            while ((n2 + (n = 48 + Math.abs(this.a.nextInt() % 10))) % 2 == 0) {
            }
        } else {
            while ((n2 + (n = 48 + Math.abs(this.a.nextInt() % 10))) % 2 == 1) {
            }
        }
        String string = "";
        string = string + (char)(48 + Math.abs(this.a.nextInt() % 10));
        string = string + (char)n2;
        string = string + (char)n;
        string = string + (char)(48 + Math.abs(this.a.nextInt() % 10));
        return string;
    }

    public String formatQuery(int n, int n2, String[] stringArray, boolean[] blArray) {
        Object object;
        String string;
        String string2;
        String string3;
        boolean bl = InfoWeatherApplet.N;
        byte[] byArray = null;
        blArray[0] = false;
        String string4 = this.a(false);
        if (n2 == 1) {
            string3 = stringArray[0].toString();
            string3 = string3.trim();
            string2 = stringArray[1].toString();
            string2 = string2.trim();
            string = "gw," + string3 + "," + string2 + ",na";
        } else if (n2 == 2) {
            string = "gc,na,na,na";
        } else {
            string3 = stringArray[0].toString();
            string2 = stringArray[1].toString();
            object = stringArray[2].toString();
            string3 = string3.trim();
            string2 = string2.trim();
            object = ((String)object).trim();
            string = "sc," + string3 + "," + string2 + "," + (String)object;
        }
        if (n > 0) {
            int n3 = 0;
            int n4 = 0;
            while (n4 < n) {
                n3 += 22 + Math.abs(this.a.nextInt() % 23);
                ++n4;
            }
            n4 = (int)((double)n3 * 2.0);
            object = new RandomData();
            byArray = ((RandomData)object).getRData(n4);
        }
        String string5 = "<?xml version=\"1.0\" ?>\n";
        string5 = string5 + "<Cities n='" + n + "' " + "id='" + string4 + "'>\n";
        string5 = string5 + "<SQL q=\"" + string + "\">\n";
        if (n > 0) {
            int n5 = 0;
            int[] sizes = this.a(byArray.length, n, 10);
            int n6 = 0;
            while (n6 < sizes.length) {
                byte[] byArray2 = new byte[sizes[n6]];
                System.arraycopy(byArray, n5, byArray2, 0, byArray2.length);
                n5 += sizes[n6];
                PayloadCodec payloadCodec = new PayloadCodec();
                string5 = string5 + payloadCodec.Encodea$4(byArray2);
                string5 = string5 + "; \n";
                byArray2 = null;
                ++n6;
            }
        }
        string5 = string5 + "</SQL>\n</Cities>\n";
        return string5;
    }

    private byte[] a__ArrayB(byte[] byArray, int n) {
        boolean bl = InfoWeatherApplet.N;
        byte[] byArray2 = new byte[n];
        int n2 = 0;
        while (n2 < byArray2.length) {
            byArray2[n2] = byArray[n2];
            ++n2;
        }
        return byArray2;
    }

    int[] a(int n, int n2, int n3) {
        boolean bl = InfoWeatherApplet.N;
        int[] nArray = new int[n2];
        int n4 = n / n2;
        do {
            int n5 = 0;
            int n6 = 0;
            while (n6 < n2) {
                nArray[n6] = n6 == n2 - 1 ? n - n5 : n4 + this.a.nextInt() % n3;
                n5 += nArray[n6];
                ++n6;
            }
        } while (Math.abs(nArray[n2 - 1] - n4) > n3);
        return nArray;
    }

    private String a__Ljava_lang_String(byte[] byArray) {
        boolean bl = InfoWeatherApplet.N;
        String string = new String(byArray);
        if (string.length() == 0) {
            return null;
        }
        if (string.indexOf("</Cities>") == -1) {
            return null;
        }
        String string2 = this.a("Reply;", string);
        if (string2.length() > 0) {
            PayloadCodec payloadCodec = new PayloadCodec();
            byte[] byArray2 = payloadCodec.Decodea$4(string2);
            string2 = new String(byArray2);
        }
        return string2;
    }

    // a$6.a(String, String) reads the first quoted value of each selected tag.
    private String a(String tags, String xml) {
        StringBuilder result = new StringBuilder();
        StringBuilder tag = new StringBuilder();
        StringBuilder value = new StringBuilder();
        int state = 0;
        for (int i = 0; i < xml.length(); i++) {
            char ch = xml.charAt(i);
            if (ch == '<' || ch == '>') {
                tag.setLength(0);
                value.setLength(0);
                state = ch == '<' ? 1 : 0;
            } else if (ch == '"') {
                if (state == 2) {
                    value.setLength(0);
                    state = 3;
                } else if (state == 3) {
                    result.append(value);
                    value.setLength(0);
                    state = 0;
                }
            } else if (state == 1) {
                if (ch == ' ') state = tag.length() > 0 && tags.contains(tag + ";") ? 2 : 0;
                else tag.append(ch);
            } else if (state == 3 && ch != ';') {
                value.append(ch);
            }
        }
        return result.toString();
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
                    n3 = 75;
                    break;
                }
                case 1: {
                    n3 = 96;
                    break;
                }
                case 2: {
                    n3 = 61;
                    break;
                }
                case 3: {
                    n3 = 58;
                    break;
                }
                default: {
                    n3 = 69;
                }
            }
            cArray[n4] = (char)(c ^ n3);
            ++n2;
        }
        return new String(cArray);
    }
}
