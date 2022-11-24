/* slides.jar — org/b/g/b/d.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.org.b.g.b;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import recovered.org.b.g.b.TypeC;

public class TypeD
extends TypeC {
    private Object b;
    private int c = 0;
    private static String[] z = new String[]{"No HostHook defined to read from.", "No HostHook defined to write to.", "The given object is not assignable to a java.io.ObjectInput class"};

    public TypeD() {
    }

    public TypeD(ThreadGroup threadGroup, String string) {
        super(threadGroup, string);
    }

    public void a__V() {
        try {
            this.close();
        }
        catch (IOException iOException) {
        }
        super.a__V();
    }

    public boolean a__Z() {
        return this.readBoolean();
    }

    public void close() throws IOException {
        try {
            ObjectInput objectInput = (ObjectInput)this.b;
            this.b = null;
            if (objectInput.readBoolean()) {
                objectInput.close();
            }
        }
        catch (Exception exception) {
            this.b = null;
        }
    }

    public int available() throws IOException {
        if (!this.readBoolean()) {
            return 0;
        }
        return 1;
    }

    public int read(byte[] byArray) throws IOException {
        if (this.b == null) {
            throw new IOException(z[0]);
        }
        return ((ObjectInput)this.b).read(byArray);
    }

    public void write(byte[] byArray) throws IOException {
        if (this.b == null) {
            throw new IOException(z[1]);
        }
        ((ObjectOutput)this.b).write(byArray);
    }

    public final boolean readBoolean() {
        return this.b != null;
    }

    public short readShort() {
        try {
            ObjectInput objectInput = (ObjectInput)this.b;
            if (objectInput.readBoolean()) {
                return 1;
            }
            return 0;
        }
        catch (Throwable throwable) {
            return 0;
        }
    }

    public void writeObject(Object object) throws IOException {
        if (!(object instanceof ObjectInput)) {
            throw new ClassCastException(z[2]);
        }
        if (!(object instanceof ObjectOutput)) {
            throw new ClassCastException(z[2]);
        }
        this.b = object;
        try {
            ObjectOutput objectOutput = (ObjectOutput)this.b;
            ObjectInput objectInput = (ObjectInput)this.b;
            if (!objectInput.readBoolean()) {
                objectOutput.writeObject(this);
            }
        }
        catch (Exception exception) {
            this.b = null;
        }
    }
}
