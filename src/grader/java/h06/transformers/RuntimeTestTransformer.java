package h06.transformers;

import org.objectweb.asm.*;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

import java.util.List;
import java.util.function.BiPredicate;

public class RuntimeTestTransformer implements ClassTransformer {

    public static boolean RANDOM_NUMBER_FUNCTION_CALLED_PLAIN = false;
    public static boolean RANDOM_NUMBER_FUNCTION_CALLED_MOD = false;
    public static boolean MOD_FUNCTION_USED = false;

    private static final List<String> METHOD_DESCRIPTORS_PLAIN = List.of(
        "doubles(DD)Ljava/util/stream/DoubleStream;",
        "doubles(JDD)Ljava/util/stream/DoubleStream;",
        "ints(II)Ljava/util/stream/IntStream;",
        "ints(JII)Ljava/util/stream/IntStream;",
        "longs(JJ)Ljava/util/stream/LongStream;",
        "longs(JJJ)Ljava/util/stream/LongStream;",
        "nextDouble(D)D",
        "nextDouble(DD)D",
        "nextFloat(F)F",
        "nextFloat(FF)F",
        "nextInt(I)I",
        "nextInt(II)I",
        "nextLong(J)J",
        "nextLong(JJ)J"
    );
    private static final List<String> METHOD_DESCRIPTORS_MOD = List.of(
        "doubles()Ljava/util/stream/DoubleStream;",
        "doubles(J)Ljava/util/stream/DoubleStream;",
        "ints()Ljava/util/stream/IntStream;",
        "ints(J)Ljava/util/stream/IntStream;",
        "longs()Ljava/util/stream/LongStream;",
        "longs(J)Ljava/util/stream/LongStream;",
        "nextDouble()D",
        "nextFloat()F",
        "nextInt()I",
        "nextLong()J"
    );
    private static final BiPredicate<Integer, String> MODULO_OPERATION = (opcode, signature) -> {
        if (opcode == Opcodes.IREM || opcode == Opcodes.LREM) {
            return true;
        } else if (opcode == Opcodes.INVOKEVIRTUAL) {
            return signature.matches("floorMod\\((II|JI|JJ)\\)[IJ]");
        } else {
            return false;
        }
    };

    @Override
    public String getName() {
        return "RuntimeTestTransformer";
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        if (reader.getClassName().equals("h06/RuntimeTest")) {
            reader.accept(new ClassTransformer(writer), ClassReader.SKIP_DEBUG);
        } else {
            reader.accept(writer, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }
    }

    @Override
    public int getWriterFlags() {
        return ClassWriter.COMPUTE_MAXS;
    }

    private static class ClassTransformer extends ClassVisitor {

        private ClassTransformer(ClassWriter classWriter) {
            super(Opcodes.ASM9, classWriter);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (name.equals("generateTestdata") && descriptor.equals("()[[Lh06/MyDate;")) {
                return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        if (opcode == Opcodes.IREM || opcode == Opcodes.LREM || opcode == Opcodes.INVOKESTATIC
                            && owner.equals("java/lang/Math")
                            && name.equals("floorMod")
                            && descriptor.matches("\\((II|JI|JJ)\\)[IJ]")) {
                            super.visitInsn(Opcodes.ICONST_1);
                            super.visitFieldInsn(Opcodes.PUTSTATIC,
                                "h06/transformers/RuntimeTestTransformer",
                                "MOD_FUNCTION_USED",
                                "Z");
                        }
                        if (owner.equals("java/util/Random") || owner.equals("java/util/concurrent/ThreadLocalRandom")) {
                            if (METHOD_DESCRIPTORS_PLAIN.contains(name + descriptor)) {
                                super.visitInsn(Opcodes.ICONST_1);
                                super.visitFieldInsn(Opcodes.PUTSTATIC,
                                    "h06/transformers/RuntimeTestTransformer",
                                    "RANDOM_NUMBER_FUNCTION_CALLED_PLAIN",
                                    "Z");
                            } else if (METHOD_DESCRIPTORS_MOD.contains(name + descriptor)) {
                                super.visitInsn(Opcodes.ICONST_1);
                                super.visitFieldInsn(Opcodes.PUTSTATIC,
                                    "h06/transformers/RuntimeTestTransformer",
                                    "RANDOM_NUMBER_FUNCTION_CALLED_MOD",
                                    "Z");
                            }
                        }
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }
                };
            } else {
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }
    }
}
