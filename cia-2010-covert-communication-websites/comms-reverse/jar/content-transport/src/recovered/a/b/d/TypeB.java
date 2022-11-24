/* slides.jar — a/b/d/b.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.b.d;

import recovered.a.a.l.TypeA;
import recovered.a.a.l.TypeF;
import recovered.a.a.l.TypeI;
import recovered.a.a.l.TypeM;
import recovered.a.a.l.TypeN;
import recovered.a.a.n.TypeH;

public class TypeB
extends TypeM {
    private TypeF a = null;
    private int b;
    private static String[] z = new String[]{"No DataStuffer could be found with which to fill the request parameters.", "Location value (", ") of the MinutemanRequestInformation cannot be null; no request can be generated.", "No support for null request data.", ") and last ID (", "The RequestParameters object passed to the RequestGenerator was not an instance of either a DisplayRequestInformation or a ConfigurableRequestParameters, but should have been.", "Cannot generate a request from a null parameters input."};

    public TypeB(TypeH typeH, int n) {
        this.a(typeH);
        this.a__V(n);
    }

    public void a(TypeH typeH) {
        this.a = new TypeF(typeH);
    }

    public void a__V(int n) {
        this.b = n;
    }

    public byte[] a__ArrayB(TypeN[] typeNArray) throws TypeA {
        if (typeNArray == null) {
            throw new TypeA(z[3]);
        }
        TypeN[] typeNArray2 = new recovered.world.finance.markets.middleeast.TypeA[typeNArray.length];
        recovered.world.finance.markets.middleeast.TypeA typeA = null;
        try {
            for (int i = 0; i < typeNArray.length; ++i) {
                try {
                    TypeI typeI = (TypeI)typeNArray[i];
                    typeA = new recovered.world.finance.markets.middleeast.TypeA(typeI.a__Ljava_lang_String(), typeI.b__Ljava_lang_String(), typeI.c__I());
                    typeA.b__V(this.b);
                }
                catch (ClassCastException classCastException) {
                    typeA = (recovered.world.finance.markets.middleeast.TypeA)typeNArray[i];
                }
                if (typeA == null) {
                    throw new TypeA(z[6]);
                }
                if (this.a == null) {
                    throw new TypeA(z[0]);
                }
                this.a.a(typeA);
                typeNArray2[i] = typeA;
            }
            return this.b__ArrayB(typeNArray2);
        }
        catch (ClassCastException classCastException) {
            throw new TypeA(z[5]);
        }
        catch (NullPointerException nullPointerException) {
            throw new TypeA(z[1] + typeA.a__Ljava_lang_String() + z[4] + typeA.b__Ljava_lang_String() + z[2], nullPointerException);
        }
    }
}
