/* slides.jar — a/a/l/m.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.a.a.l;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import recovered.a.a.l.TypeA;
import recovered.a.a.l.TypeL;
import recovered.a.a.l.TypeN;

public abstract class TypeM
implements TypeL {
    private static String[] z = new String[]{"Cannot write out the DisplayDataSet to an output object stream: ", "Cannot write out a null request object."};

    protected TypeM() {
    }

    protected byte[] b__ArrayB(TypeN[] typeNArray) throws TypeA {
        try {
            if (typeNArray == null) {
                throw new TypeA(z[1]);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            for (int i = 0; i < typeNArray.length; ++i) {
                objectOutputStream.writeObject(typeNArray[i]);
            }
            objectOutputStream.flush();
            byte[] byArray = byteArrayOutputStream.toByteArray();
            objectOutputStream.close();
            return byArray;
        }
        catch (IOException iOException) {
            throw new TypeA(z[0] + iOException.getMessage(), iOException);
        }
    }

    public abstract /* synthetic */ byte[] a__ArrayB(TypeN[] var1) throws TypeA;
}
