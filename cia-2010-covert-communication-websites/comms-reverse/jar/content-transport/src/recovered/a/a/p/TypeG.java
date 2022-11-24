/* slides.jar — a/a/p/g.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.p;

import recovered.a.a.a.TypeI;
import recovered.a.a.l.TypeA;
import recovered.a.a.l.TypeK;
import recovered.a.a.l.TypeL;
import recovered.a.a.l.TypeN;
import recovered.a.a.p.TypeD;
import recovered.a.a.p.TypeE;
import recovered.a.a.p.TypeF;
import recovered.a.a.p.TypeH;
import recovered.org.b.f.TypeB;
import recovered.org.b.f.TypeC;

public class TypeG
extends TypeF {
    private static recovered.org.b.b.TypeG f;
    private TypeL g;
    private TypeI h;
    private String i;
    private recovered.org.b.f.TypeE j;
    private int k;
    private static String[] z;

    public TypeG() {
        if (f == null) {
            f = recovered.a.a.k.TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
        }
    }

    public void a(TypeH typeH) throws TypeD {
        try {
            TypeK typeK = typeH.a__La_a_l_k();
            if (!(typeK instanceof TypeL)) {
                f.d(z[10]);
                throw new TypeD(z[11]);
            }
            this.a((TypeL)typeK);
            this.a(typeH.b__La_a_a_i());
            this.k = typeH.f__I();
            this.j = this.b(typeH);
            this.i = typeH.b__Ljava_lang_String();
            TypeC[] typeCArray = typeH.g__ArrayLorg_b_f_c();
            for (int i = 0; typeCArray != null && i < typeCArray.length; ++i) {
                this.j.a(typeCArray[i]);
            }
            this.j.b__V(typeH.d__I());
            this.j.a__V(typeH.e__I());
        }
        catch (TypeE typeE) {
            f.d(z[9] + typeE);
            throw new TypeD(z[12], typeE);
        }
    }

    protected TypeL a__La_a_l_l() {
        return this.g;
    }

    protected void a(TypeL typeL) throws TypeD {
        this.g = typeL;
    }

    protected TypeI b__La_a_a_i() {
        return this.h;
    }

    protected void a(TypeI typeI) throws TypeD {
        this.h = typeI;
    }

    protected String c__Ljava_lang_String() {
        return z[13];
    }

    protected int d__I() {
        return 3;
    }

    protected long e__J() {
        return 2000L;
    }

    public int a__I() {
        return this.k;
    }

    protected recovered.org.b.f.TypeE b(TypeH typeH) throws TypeD {
        try {
            ThreadGroup threadGroup = recovered.a.a.k.TypeB.a__La_a_k_b().c__La_a_p_o().a__Ljava_lang_ThreadGroup();
            return new recovered.org.b.f.a.TypeA(typeH.a__Ljava_lang_String(), typeH.f__Ljava_lang_String(), typeH.c__I(), this.c__Ljava_lang_String(), threadGroup);
        }
        catch (TypeE typeE) {
            f.c(z[0] + typeE.toString());
            throw new TypeD(z[0] + typeE.toString(), typeE);
        }
    }

    protected recovered.a.a.d.a.TypeD[] b__ArrayLa_a_d_a_d(TypeN[] typeNArray) throws TypeD {
        try {
            byte[] byArray = this.a__La_a_l_l().a__ArrayB(typeNArray);
            if (byArray == null) {
                throw new TypeD(z[8]);
            }
            int n = this.d__I();
            int n2 = 0;
            boolean bl = false;
            TypeB[] typeBArray = new TypeB[n];
            byte[] byArray2 = null;
            while (!bl && n2 < n) {
                try {
                    byArray2 = this.j.a__ArrayB(this.i, byArray);
                    bl = true;
                }
                catch (TypeB typeB) {
                    typeBArray[n2] = typeB;
                    f.d(z[5] + ++n2 + z[2], typeB);
                    if (n2 > n) continue;
                    try {
                        Thread.currentThread();
                        Thread.sleep(this.e__J() + (long)(n2 * 100));
                    }
                    catch (InterruptedException interruptedException) {}
                }
            }
            if (byArray2 == null) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < n2; ++i) {
                    stringBuffer.append(z[7]);
                    stringBuffer.append(i + 1);
                    stringBuffer.append(z[4]);
                    stringBuffer.append(typeBArray[i]);
                }
                f.d(z[6]);
                throw new TypeD(z[3] + stringBuffer.toString());
            }
            recovered.a.a.d.a.TypeD[] typeDArray = this.b__La_a_a_i().a__ArrayLa_a_d_a_d(byArray2);
            return typeDArray;
        }
        catch (NullPointerException nullPointerException) {
            f.d(z[1], nullPointerException);
            throw new TypeD(nullPointerException);
        }
        catch (ClassCastException classCastException) {
            f.d(z[1], classCastException);
            throw new TypeD(classCastException);
        }
        catch (TypeA typeA) {
            f.d(z[1], typeA);
            throw new TypeD(typeA);
        }
        catch (recovered.a.a.a.TypeA typeA) {
            f.d(z[1], typeA);
            throw new TypeD(typeA);
        }
    }

    static {
        z = new String[]{"Unable to create a NetRequest object due to error: ", "Error creating DataSet:", " for DataSet failed.", "Unable to generate DisplaySet due to NetExceptions:", ") ", "Request attempt ", "Unable to generate DisplaySet due to above NetExceptions.", "  (", "Unable to generate request data bytes", "Unable to configure with the given parameters: ", "In the given parameters, the RequestGenerator was of the incorrect type.", "The given RequestGenerator type is not supported.", "Unable to configure with the given parameters.", "POST"};
    }
}
