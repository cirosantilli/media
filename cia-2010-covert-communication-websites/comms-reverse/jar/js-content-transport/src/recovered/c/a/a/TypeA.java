/* aeronet.jar — c/a/a/a.class. CFR 0.152, with archived identifiers in names.tsv. */
package recovered.c.a.a;

public class TypeA
extends Exception {
    private Throwable a;

    public TypeA(String string) {
        super(string);
    }

    public TypeA(Throwable throwable) {
        super(throwable.getMessage());
        this.a = throwable;
    }
}
