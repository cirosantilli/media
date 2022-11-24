/* slides.jar — a/a/p/f.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.p;

import java.util.Vector;
import recovered.a.a.e.c.TypeH;
import recovered.a.a.k.TypeB;
import recovered.a.a.l.TypeN;
import recovered.a.a.o.TypeA;
import recovered.a.a.o.TypeC;
import recovered.a.a.o.TypeE;
import recovered.a.a.p.TypeD;
import recovered.a.a.p.TypeP;
import recovered.org.b.g.TypeG;
import recovered.org.b.g.TypeI;

public abstract class TypeF
implements Runnable,
TypeA {
    private static recovered.org.b.b.TypeG a;
    private Vector b;
    private TypeI c;
    private TypeP d;
    private boolean e = false;
    private static String[] z;

    public TypeF() {
        ThreadGroup threadGroup = TypeB.a__La_a_k_b().c__La_a_p_o().a__Ljava_lang_ThreadGroup();
        if (a == null) {
            a = TypeB.a__La_a_k_b().d__La_a_h_f().a__Lorg_b_b_g(this.getClass().getName());
        }
        this.b = new Vector();
        this.c = new TypeI();
        this.d = threadGroup != null ? new TypeP(threadGroup, this) : new TypeP(this);
        this.d.start();
    }

    public int a__I() {
        return 256;
    }

    public long b__J() {
        return 500L;
    }

    public recovered.a.a.d.a.TypeD[] a__ArrayLa_a_d_a_d(TypeN[] typeNArray) throws TypeD {
        if (typeNArray == null) {
            return null;
        }
        recovered.a.a.d.a.TypeD[] typeDArray = this.b__ArrayLa_a_d_a_d(typeNArray);
        if (typeDArray == null) {
            a.c(z[2]);
            return null;
        }
        if (typeDArray.length != typeNArray.length) {
            a.c(z[3] + typeNArray.length + z[5] + typeDArray.length);
        }
        for (int i = 0; i < typeDArray.length; ++i) {
            if (typeDArray[i] != null) {
                this.a__V(typeDArray[i]);
                continue;
            }
            a.c(z[4] + i);
        }
        return typeDArray;
    }

    protected abstract recovered.a.a.d.a.TypeD[] b__ArrayLa_a_d_a_d(TypeN[] var1) throws TypeD;

    public void a(TypeC typeC) {
        if (typeC != null) {
            this.b.addElement(typeC);
        }
    }

    protected void a__V(recovered.a.a.d.a.TypeD typeD) {
        TypeH typeH;
        if (typeD != null && (typeH = typeD.a__La_a_e_c_h()) != null) {
            for (int i = 0; i < this.b.size(); ++i) {
                TypeC typeC = (TypeC)this.b.elementAt(i);
                typeH.a(typeC);
            }
        }
    }

    public void a(recovered.a.a.o.TypeD typeD) {
        if (typeD != null) {
            try {
                this.c.a__V((TypeE)typeD);
            }
            catch (ClassCastException classCastException) {
            }
        }
    }

    protected void c__V() {
        int n;
        int n2;
        TypeN[] typeNArray;
        recovered.a.a.d.a.TypeC[] typeCArray;
        int n3;
        int n4 = this.c.a__I();
        if (n4 <= 0) {
            return;
        }
        recovered.a.a.d.a.TypeC[] typeCArray2 = new recovered.a.a.d.a.TypeC[n4];
        TypeN[] typeNArray2 = new TypeN[n4];
        int n5 = 0;
        int n6 = 0;
        for (n3 = 0; n3 < n4; ++n3) {
            TypeE event = null;
            try {
                event = (TypeE)this.c.b__Ljava_lang_Object();
            }
            catch (TypeG typeG) {
            }
            catch (ClassCastException classCastException) {
            }
            n5 = n3 - n6;
            if (event != null) {
                typeCArray2[n5] = (recovered.a.a.d.a.TypeC)event.getSource();
                typeNArray2[n5] = event.a__La_a_l_n();
            }
            if (typeCArray2[n5] != null && typeNArray2[n5] != null) continue;
            if (typeCArray2[n5] != null) {
                typeCArray2[n5].a__V(null);
            }
            ++n6;
            a.c(z[1]);
        }
        if (n6 != 0) {
            n3 = n4 - n6;
            if (n3 > 0) {
                typeCArray = new recovered.a.a.d.a.TypeC[n3];
                typeNArray = new TypeN[n3];
                for (n2 = 0; n2 < n3; ++n2) {
                    typeCArray[n2] = typeCArray2[n2];
                    typeNArray[n2] = typeNArray2[n2];
                }
                typeCArray2 = typeCArray;
                typeNArray2 = typeNArray;
            }
            n4 = n3;
        }
        for (n3 = 0; n3 < n4; n3 += n) {
            n = Math.min(this.a__I(), n4 - n3);
            typeNArray = new TypeN[n];
            for (n2 = 0; n2 < n; ++n2) {
                typeNArray[n2] = typeNArray2[n2 + n3];
            }
            recovered.a.a.d.a.TypeC[] typeCArray3 = new recovered.a.a.d.a.TypeC[n];
            for (int i = 0; i < n; ++i) {
                typeCArray3[i] = typeCArray2[i + n3];
            }
            this.a(typeNArray, typeCArray3);
        }
    }

    protected void a(TypeN[] typeNArray, recovered.a.a.d.a.TypeC[] typeCArray) {
        recovered.a.a.d.a.TypeD[] typeDArray = null;
        try {
            typeDArray = this.a__ArrayLa_a_d_a_d(typeNArray);
        }
        catch (TypeD typeD) {
            a.c(z[0], typeD);
        }
        if (typeDArray != null) {
            for (int i = 0; i < typeCArray.length; ++i) {
                if (i < typeDArray.length) {
                    typeCArray[i].a__V(typeDArray[i]);
                    continue;
                }
                typeCArray[i].a__V(null);
            }
        } else {
            for (int i = 0; i < typeCArray.length; ++i) {
                typeCArray[i].a__V(null);
            }
        }
    }

    public void run() {
        Thread thread = Thread.currentThread();
        if (this.e || !(thread instanceof TypeP)) {
            return;
        }
        this.e = true;
        TypeP typeP = (TypeP)thread;
        while (!typeP.c__Z()) {
            this.c__V();
            if (typeP.c__Z()) continue;
            try {
                Thread.sleep(this.b__J());
            }
            catch (InterruptedException interruptedException) {}
        }
        this.e = false;
    }

    static {
        z = new String[]{"Unable to get a DisplaySet due to error:", "Skipping queued DisplaySet request due null source or null paramters.", "Created a null DisplaySet array.", "Coordinator did not receive the expected number of DisplaySets.  Expected: ", "Created a null DisplaySet at index: ", " got: "};
    }
}
