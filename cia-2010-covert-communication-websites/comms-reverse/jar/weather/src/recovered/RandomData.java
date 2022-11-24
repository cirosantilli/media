/* infoweather.jar — a$5.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered;

import recovered.InfoWeatherApplet;
import recovered.TypeG;

public class RandomData {
    private TypeG a;
    private int[] b;

    public RandomData() {
        boolean bl = InfoWeatherApplet.N;
        this.a = new TypeG();
        int[] nArray = new int[128];
        nArray[32] = 536564574;
        nArray[33] = 536586352;
        nArray[34] = 536586352;
        nArray[35] = 537153748;
        nArray[36] = 537192720;
        nArray[37] = 537274104;
        nArray[38] = 537546912;
        nArray[39] = 537682170;
        nArray[40] = 539766062;
        nArray[41] = 541849954;
        nArray[42] = 541849954;
        nArray[43] = 541908413;
        nArray[44] = 550066314;
        nArray[45] = 552144475;
        nArray[46] = 586130955;
        nArray[47] = 586130955;
        nArray[48] = 590408781;
        nArray[49] = 594574274;
        nArray[50] = 596684530;
        nArray[51] = 598756960;
        nArray[52] = 599793175;
        nArray[53] = 600827097;
        nArray[54] = 601956158;
        nArray[55] = 602980910;
        nArray[56] = 603971274;
        nArray[57] = 605030414;
        nArray[58] = 605030414;
        nArray[59] = 606135404;
        nArray[60] = 606135404;
        nArray[61] = 623034651;
        nArray[62] = 623034651;
        nArray[63] = 623034651;
        nArray[64] = 623104572;
        nArray[65] = 631581132;
        nArray[66] = 633676487;
        nArray[67] = 641979963;
        nArray[68] = 650305217;
        nArray[69] = 658723318;
        nArray[70] = 662942685;
        nArray[71] = 667215926;
        nArray[72] = 668244117;
        nArray[73] = 676722970;
        nArray[74] = 676782575;
        nArray[75] = 677330485;
        nArray[76] = 679375405;
        nArray[77] = 687697221;
        nArray[78] = 691981924;
        nArray[79] = 696280382;
        nArray[80] = 704732871;
        nArray[81] = 705003387;
        nArray[82] = 713421488;
        nArray[83] = 730226742;
        nArray[84] = 746826816;
        nArray[85] = 748902685;
        nArray[86] = 751045036;
        nArray[87] = 755324008;
        nArray[88] = 757403315;
        nArray[89] = 758449846;
        nArray[90] = 758514036;
        nArray[91] = 759044752;
        nArray[92] = 759044752;
        nArray[93] = 759564005;
        nArray[94] = 759570882;
        nArray[95] = 760100452;
        nArray[96] = 760100452;
        nArray[97] = 894533317;
        nArray[98] = 911400469;
        nArray[99] = 945017855;
        nArray[100] = 978519469;
        nArray[101] = 1246452148;
        nArray[102] = 1263359419;
        nArray[103] = 1279977833;
        nArray[104] = 1313560831;
        nArray[105] = 1381446677;
        nArray[106] = 1383528277;
        nArray[107] = 1385590390;
        nArray[108] = 1419481731;
        nArray[109] = 1553722025;
        nArray[110] = 1620217463;
        nArray[111] = 1687206937;
        nArray[112] = 1720757840;
        nArray[113] = 1721782592;
        nArray[114] = 1855108174;
        nArray[115] = 1922411722;
        nArray[116] = 2056798737;
        nArray[117] = 2090040151;
        nArray[118] = 2098469715;
        nArray[119] = 2106794969;
        nArray[120] = 2110992557;
        nArray[121] = 2144770419;
        nArray[122] = 2146940280;
        nArray[123] = 2147214235;
        nArray[124] = 2147214235;
        nArray[125] = 2147469850;
        nArray[126] = 2147483605;
        nArray[127] = Integer.MAX_VALUE;
        this.b = nArray;
    }

    public byte[] getRData(int n) {
        boolean bl = InfoWeatherApplet.N;
        byte[] byArray = new byte[n];
        int n2 = 0;
        while (n2 < n) {
            int n3 = 0;
            n3 = this.a.nextByte() & 0xFF;
            n3 |= this.a.nextByte() << 8 & 0xFF00;
            n3 |= this.a.nextByte() << 16 & 0xFF0000;
            n3 |= this.a.nextByte() << 24 & 0x7F000000;
            int n4 = 31;
            while (n4 < this.b.length) {
                if (n3 > this.b[n4 - 1]) {
                    if (n3 <= this.b[n4]) {
                        byArray[n2] = (byte)n4;
                        break;
                    }
                }
                n4 = (byte)(n4 + 1);
            }
            ++n2;
        }
        try {
            Thread.sleep(50 + (int)(Math.random() * 100.0));
        }
        catch (Exception exception) {}
        return byArray;
    }
}
