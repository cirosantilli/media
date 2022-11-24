/* world.jar — c/a/a/c.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.c.a.a;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import recovered.c.a.a.TypeA;
import recovered.c.a.a.TypeB;

public abstract class TypeC
extends TypeB
implements TypeA,
ObjectInput,
ObjectOutput {
    public static boolean c;
    private static String z;

    public TypeC() {
    }

    public TypeC(ThreadGroup threadGroup, String string) {
        super(threadGroup, string);
    }

    public abstract void close() throws IOException;

    public abstract int available() throws IOException;

    public abstract int read(byte[] var1) throws IOException;

    public abstract void write(byte[] var1) throws IOException;

    public abstract boolean readBoolean();

    public abstract short readShort();

    public abstract void writeObject(Object var1) throws IOException;

    public void flush() throws IOException {
        throw new IOException(z);
    }

    public int read() throws IOException {
        throw new IOException(z);
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        throw new IOException(z);
    }

    public byte readByte() throws IOException {
        throw new IOException(z);
    }

    public char readChar() throws IOException {
        throw new IOException(z);
    }

    public double readDouble() throws IOException {
        throw new IOException(z);
    }

    public float readFloat() throws IOException {
        throw new IOException(z);
    }

    public void readFully(byte[] byArray) throws IOException {
        throw new IOException(z);
    }

    public void readFully(byte[] byArray, int n, int n2) throws IOException {
        throw new IOException(z);
    }

    public int readInt() throws IOException {
        throw new IOException(z);
    }

    public String readLine() throws IOException {
        throw new IOException(z);
    }

    public long readLong() throws IOException {
        throw new IOException(z);
    }

    public Object readObject() throws ClassNotFoundException, IOException {
        throw new IOException(z);
    }

    public String readUTF() throws IOException {
        throw new IOException(z);
    }

    public int readUnsignedByte() throws IOException {
        throw new IOException(z);
    }

    public int readUnsignedShort() throws IOException {
        throw new IOException(z);
    }

    public long skip(long l) throws IOException {
        throw new IOException(z);
    }

    public int skipBytes(int n) throws IOException {
        throw new IOException(z);
    }

    public void write(int n) throws IOException {
        throw new IOException(z);
    }

    public void write(byte[] byArray, int n, int n2) throws IOException {
        throw new IOException(z);
    }

    public void writeBoolean(boolean bl) throws IOException {
        throw new IOException(z);
    }

    public void writeByte(int n) throws IOException {
        throw new IOException(z);
    }

    public void writeBytes(String string) throws IOException {
        throw new IOException(z);
    }

    public void writeChar(int n) throws IOException {
        throw new IOException(z);
    }

    public void writeChars(String string) throws IOException {
        throw new IOException(z);
    }

    public void writeDouble(double d) throws IOException {
        throw new IOException(z);
    }

    public void writeFloat(float f) throws IOException {
        throw new IOException(z);
    }

    public void writeInt(int n) throws IOException {
        throw new IOException(z);
    }

    public void writeLong(long l) throws IOException {
        throw new IOException(z);
    }

    public void writeShort(int n) throws IOException {
        throw new IOException(z);
    }

    public void writeUTF(String string) throws IOException {
        throw new IOException(z);
    }

    static {
        z = "This method is not supported.";
    }
}
