/* infoweather.jar — a$4.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import recovered.InfoWeatherApplet;

public class PayloadCodec {
    private int a;
    private int b;
    private String c;
    private static final char d = '=';
    private static final short e = 64;
    private static final short f = 50;
    private static final short g = 255;

    private char a__C(int n) {
        boolean bl = InfoWeatherApplet.N;
        if (n >= 0) {
            if (n < 64) {
                return this.c.charAt(n);
            }
        }
        return '?';
    }

    public String Encodea$4(byte[] byArray) {
        boolean bl = InfoWeatherApplet.N;
        StringBuffer stringBuffer = new StringBuffer();
        int n = 0;
        while (n < byArray.length) {
            stringBuffer.append(this.a__ArrayC(byArray, n));
            n += 3;
        }
        return stringBuffer.toString();
    }

    private int a(char c) {
        int n = this.c.indexOf(c);
        if (n == -1) {
            n = 0;
        }
        return n;
    }

    public PayloadCodec() {
        this.a();
    }

    public PayloadCodec(int n) {
        this.a = n;
        this.a();
        this.c();
    }

    public String GetAlphabet() {
        return this.c;
    }

    public String toHexString(byte[] byArray) {
        boolean bl = InfoWeatherApplet.N;
        StringBuffer stringBuffer = new StringBuffer(byArray.length * 3 + 1);
        int n = 0;
        while (n < byArray.length) {
            int n2 = byArray[n] & 0xFF;
            String string = Integer.toHexString(n2);
            if (string.length() < 2) {
                stringBuffer.append('0');
            }
            stringBuffer.append(string);
            stringBuffer.append(' ');
            ++n;
        }
        return new String(stringBuffer);
    }

    private void a() {
        this.c = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
    }

    private short b() {
        this.b = ((this.b >> 24 & 0xFF) + 117) * ((this.b >> 16 & 0xFF) + 17) * ((this.b >> 8 & 0xFF) + 13) * (this.b + 3 & 0xFF);
        return (short)(this.b >> 11 & Short.MAX_VALUE);
    }

    public byte[] Decodea$4(String input) {
        if (input.indexOf('?') >= 0) return null;
        int padding = 0;
        while (input.charAt(input.length() - 1 - padding) == '=') ++padding;
        byte[] out = new byte[input.length() * 6 / 8 - padding];
        int offset = 0;
        for (int i = 0; i < input.length(); i += 4) {
            int bits = (a(input.charAt(i)) << 18) + (a(input.charAt(i+1)) << 12)
                     + (a(input.charAt(i+2)) << 6) + a(input.charAt(i+3));
            for (int j = 0; j < 3 && offset + j < out.length; j++)
                out[offset+j] = (byte)(bits >> (8 * (2-j)));
            offset += 3;
        }
        return out;
    }

    private char[] a__ArrayC(byte[] byArray, int n) {
        int n2;
        int n3;
        boolean bl = InfoWeatherApplet.N;
        int n4 = 0;
        int n5 = byArray.length - n - 1;
        int n6 = n5 >= 2 ? 2 : n5;
        int n7 = 0;
        while (n7 <= n6) {
            n3 = byArray[n + n7];
            n2 = n3 < 0 ? n3 + 256 : n3;
            n4 += n2 << 8 * (2 - n7);
            ++n7;
        }
        char[] cArray = new char[4];
        n3 = 0;
        do {
            n2 = n4 >>> 6 * (3 - n3) & 0x3F;
            cArray[n3] = this.a__C(n2);
        } while (++n3 < 4);
        if (n5 < 1) {
            cArray[2] = 61;
        }
        if (n5 < 2) {
            cArray[3] = 61;
        }
        return cArray;
    }

    private void c() {
        boolean bl = InfoWeatherApplet.N;
        this.b = this.a & 0xFFFFFFFF;
        char[] cArray = this.c.toCharArray();
        int n = 0;
        while (n < 50) {
            short s = (short)(this.b() % 64 & 0xFF);
            short s2 = (short)(this.b() % 64 & 0xFF);
            char c = cArray[s];
            cArray[s] = cArray[s2];
            cArray[s2] = c;
            n = (short)(n + 1);
        }
        this.c = new String(cArray);
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
                    n3 = 25;
                    break;
                }
                case 1: {
                    n3 = 98;
                    break;
                }
                case 2: {
                    n3 = 41;
                    break;
                }
                case 3: {
                    n3 = 55;
                    break;
                }
                default: {
                    n3 = 63;
                }
            }
            cArray[n4] = (char)(c ^ n3);
            ++n2;
        }
        return new String(cArray);
    }
}
