import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/** Restore archived JVM names, including overloads that Java source cannot express. */
@SuppressWarnings("deprecation")
class RestoreNames extends Remapper {
    private final Map<String, String> classes = new HashMap<>();
    private final Map<String, String> methods = new HashMap<>();
    private final Map<String, String> fields = new HashMap<>();

    RestoreNames(Path table) throws IOException {
        if (!Files.exists(table)) return;
        var rows = Files.readAllLines(table).stream().map(line -> line.split("\t")).toList();
        Map<String, String> sourceNames = new HashMap<>();
        for (String[] row : rows) {
            if (row[0].equals("C")) {
                classes.put(row[2], row[1]);
                sourceNames.put(row[1], row[2]);
            }
        }
        Remapper forward = new Remapper() {
            public String map(String name) { return sourceNames.getOrDefault(name, name); }
        };
        for (String[] row : rows) {
            if (row[0].equals("M")) methods.put(row[3] + forward.mapMethodDesc(row[2]), row[1]);
            if (row[0].equals("F")) fields.put(row[3] + forward.mapDesc(row[2]), row[1]);
        }
    }

    public String map(String name) { return classes.getOrDefault(name, name); }
    public String mapMethodName(String owner, String name, String desc) {
        return classes.containsKey(owner) ? methods.getOrDefault(name + desc, name) : name;
    }
    public String mapFieldName(String owner, String name, String desc) {
        return classes.containsKey(owner) ? fields.getOrDefault(name + desc, name) : name;
    }

    public static void main(String[] args) throws IOException {
        RestoreNames names = new RestoreNames(Path.of(args[0]));
        Path compiled = Path.of(args[1]), output = Path.of(args[3]);
        try (JarFile jar = new JarFile(args[2]); var paths = Files.walk(compiled)) {
            for (Path file : paths.filter(p -> p.toString().endsWith(".class")).toList()) {
                ClassNode rebuilt = new ClassNode();
                new ClassReader(Files.readAllBytes(file)).accept(new ClassRemapper(rebuilt, names), 0);
                var entry = jar.getJarEntry(rebuilt.name + ".class");
                if (entry == null) throw new IOException("Unexpected rebuilt class " + rebuilt.name);
                ClassNode original = new ClassNode();
                // The original supplies declaration flags and compiler metadata only.
                new ClassReader(jar.getInputStream(entry)).accept(original, ClassReader.SKIP_CODE);
                // Early JVMs accepted interfaces without ACC_ABSTRACT. Java 8 requires it.
                rebuilt.access = original.access;
                if ((original.access & Opcodes.ACC_INTERFACE) != 0) rebuilt.access |= Opcodes.ACC_ABSTRACT;
                Map<String, MethodNode> signatures = new HashMap<>();
                for (MethodNode m : original.methods) signatures.put(m.name + m.desc, m);
                // javac supplies a constructor even for classes stripped of every constructor.
                rebuilt.methods.removeIf(m -> m.name.equals("<init>") && !signatures.containsKey(m.name + m.desc));
                for (MethodNode m : rebuilt.methods) {
                    MethodNode declaration = signatures.remove(m.name + m.desc);
                    if (declaration != null) m.access = declaration.access;
                }
                if (!signatures.isEmpty()) throw new IOException(rebuilt.name + " missing " + signatures.keySet());
                for (var field : rebuilt.fields) {
                    for (var archived : original.fields) {
                        if (field.name.equals(archived.name) && field.desc.equals(archived.desc)) {
                            field.access = archived.access;
                            break;
                        }
                    }
                }
                ClassWriter writer = new ClassWriter(0);
                rebuilt.accept(writer);
                Path target = output.resolve(rebuilt.name + ".class");
                Files.createDirectories(target.getParent());
                Files.write(target, writer.toByteArray());
            }
        }
    }
}
