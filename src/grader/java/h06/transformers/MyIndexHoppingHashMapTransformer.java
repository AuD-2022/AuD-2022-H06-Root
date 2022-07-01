package h06.transformers;

import org.objectweb.asm.*;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

import static h06.Config.ASSIGNMENT_ID;

public class MyIndexHoppingHashMapTransformer implements ClassTransformer {

    @Override
    public String getName() {
        return "MyIndexHoppingHashMapTransformer";
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        if (reader.getClassName().equals(ASSIGNMENT_ID + "/MyIndexHoppingHashMap")) {
            reader.accept(new ClassTransformer(writer), ClassReader.SKIP_DEBUG);
        } else {
            reader.accept(writer, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }
    }

    @Override
    public int getWriterFlags() {
        return ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES;
    }

    private static class ClassTransformer extends ClassVisitor {

        public ClassTransformer(ClassWriter classWriter) {
            super(Opcodes.ASM9, classWriter);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (name.equals("put") && descriptor.equals("(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;")) {
                return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        if (owner.equals(ASSIGNMENT_ID + "/MyIndexHoppingHashMap")
                            && name.equals("rehash")
                            && descriptor.equals("()V")) {
                            super.visitInsn(Opcodes.ICONST_1);
                            super.visitFieldInsn(Opcodes.PUTSTATIC,
                                ASSIGNMENT_ID + "/h3/MyIndexHoppingHashMapTests",
                                "REHASH_CALLED",
                                "Z");
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
