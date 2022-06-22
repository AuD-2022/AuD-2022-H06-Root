package h06.transformers;

import org.objectweb.asm.*;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class Hash2IndexFctTransformer implements ClassTransformer {

    public static boolean SURROGATE_ACTIVE = true;

    @Override
    public String getName() {
        return "Hash2IndexFctTransformer";
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        if (reader.getClassName().equals("h06/Hash2IndexFct")) {
            reader.accept(new ClassTransformer(writer), ClassReader.SKIP_DEBUG);
        } else {
            reader.accept(writer, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }
    }

    private static class ClassTransformer extends ClassVisitor {

        private ClassTransformer(ClassWriter classWriter) {
            super(Opcodes.ASM9, classWriter);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (name.equals("apply") && descriptor.equals("(Ljava/lang/Object;)I")) {
                return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    final Label label = new Label();

                    @Override
                    public void visitCode() {
                        super.visitFieldInsn(Opcodes.GETSTATIC,
                            "h06/transformers/Hash2IndexFctTransformer",
                            "SURROGATE_ACTIVE",
                            "Z");
                        super.visitJumpInsn(Opcodes.IFNE, label);
                        super.visitCode();
                    }

                    @Override
                    public void visitEnd() {
                        super.visitLabel(label);
                        super.visitFrame(Opcodes.F_FULL, 2, new Object[] {"h06/Hash2IndexFct", "java/lang/Object"}, 0,
                            new Object[0]);
                        super.visitFieldInsn(Opcodes.GETSTATIC,
                            "java/lang/System",
                            "out",
                            "Ljava/io/PrintStream;");
                        super.visitLdcInsn("Hello world!");
                        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/io/PrintStream",
                            "println",
                            "(Ljava/lang/String;)V",
                            false);
                        super.visitInsn(Opcodes.ICONST_0);
                        super.visitInsn(Opcodes.IRETURN);
                        super.visitEnd();
                    }
                };
            } else {
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }
    }
}
