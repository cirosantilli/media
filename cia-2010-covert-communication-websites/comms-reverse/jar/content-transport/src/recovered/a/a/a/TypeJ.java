/* slides.jar — a/a/a/j.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.a;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;
import recovered.a.a.a.TypeA;
import recovered.a.a.a.TypeI;
import recovered.a.a.d.a.TypeD;
import recovered.a.a.d.c.TypeB;

public abstract class TypeJ
implements TypeI {
    private static String[] z = new String[]{"Cannot find definition for serialized class: ", "The response object is not a DisplayDataSet: ", "IO Problem when unserializing response object: ", "Cannot parse a null response."};

    protected TypeJ() {
    }

    protected TypeB[] a__ArrayLa_a_d_c_b(byte[] byArray) throws TypeA {
        try {
            if (byArray == null) {
                throw new TypeA(z[3]);
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Vector<Object> vector = new Vector<Object>();
            boolean bl = false;
            while (!bl) {
                try {
                    vector.addElement(objectInputStream.readObject());
                }
                catch (EOFException eOFException) {
                    bl = true;
                }
            }
            TypeB[] typeBArray = new TypeB[vector.size()];
            for (int i = 0; i < vector.size(); ++i) {
                typeBArray[i] = (TypeB)vector.elementAt(i);
            }
            return typeBArray;
        }
        catch (ClassCastException classCastException) {
            throw new TypeA(z[1] + classCastException.getMessage(), classCastException);
        }
        catch (IOException iOException) {
            throw new TypeA(z[2] + iOException.getMessage(), iOException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new TypeA(z[0] + classNotFoundException.getMessage(), classNotFoundException);
        }
    }

    public abstract /* synthetic */ TypeD[] a__ArrayLa_a_d_a_d(byte[] var1) throws TypeA;
}
