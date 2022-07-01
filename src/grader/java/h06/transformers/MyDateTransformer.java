package h06.transformers;

import org.objectweb.asm.*;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class MyDateTransformer implements ClassTransformer {

    public static boolean CONSTRUCTOR_SURROGATE_ACTIVE = false;
    public static boolean HASH_CODE_SURROGATE_ACTIVE = true;

    @Override
    public String getName() {
        return "MyDateTransformer";
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        if (reader.getClassName().equals("h06/MyDate")) {
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
            if (name.equals("<init>") && descriptor.equals("(Ljava/util/Calendar;Z)V")) {
                return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    final Label label = new Label();

                    @Override
                    public void visitCode() {
                        super.visitFieldInsn(Opcodes.GETSTATIC,
                            "h06/transformers/MyDateTransformer",
                            "CONSTRUCTOR_SURROGATE_ACTIVE",
                            "Z");
                        super.visitJumpInsn(Opcodes.IFNE, label);
                        super.visitCode();
                    }

                    @Override
                    public void visitEnd() {
                        super.visitLabel(label);
                        super.visitFrame(Opcodes.F_FULL,
                            3, new Object[] {Opcodes.UNINITIALIZED_THIS, "java/util/Calendar", Opcodes.INTEGER},
                            0, new Object[0]);
                        injectConstructorCode(this);
                        super.visitInsn(Opcodes.RETURN);
                        super.visitEnd();
                    }
                };
            } else if (name.equals("hashCode") && descriptor.equals("()I")) {
                return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    final Label label = new Label();

                    @Override
                    public void visitCode() {
                        super.visitFieldInsn(Opcodes.GETSTATIC,
                            "h06/transformers/MyDateTransformer",
                            "HASH_CODE_SURROGATE_ACTIVE",
                            "Z");
                        super.visitJumpInsn(Opcodes.IFNE, label);
                        super.visitCode();
                    }

                    @Override
                    public void visitEnd() {
                        super.visitLabel(label);
                        super.visitFrame(Opcodes.F_FULL, 1, new Object[] {"h06/MyDate"}, 0,
                            new Object[0]);
                        super.visitVarInsn(Opcodes.ALOAD, 0);
                        super.visitFieldInsn(Opcodes.GETFIELD,
                            "h06/MyDate",
                            "hashCodeReturnValue",
                            "I");
                        super.visitInsn(Opcodes.IRETURN);
                        super.visitEnd();
                    }
                };
            } else if (name.equals("<init>") && descriptor.equals("(Ljava/util/Calendar;ZI)V")) {
                return null;
            } else {
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }

        @Override
        public void visitEnd() {
            super.visitField(Opcodes.ACC_PUBLIC,
                "hashCodeReturnValue",
                "I",
                null,
                null);

            MethodVisitor mv = super.visitMethod(Opcodes.ACC_PUBLIC,
                "<init>",
                "(Ljava/util/Calendar;ZI)V",
                null,
                null);
            injectConstructorCode(mv);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ILOAD, 3);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "hashCodeReturnValue",
                "I");
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(3, 4);

            super.visitEnd();
        }

        private static void injectConstructorCode(MethodVisitor mv) {
            // super()
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL,
                "java/lang/Object",
                "<init>",
                "()V",
                false);

            // assign coefficientYear
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitLdcInsn(4563766470487201L);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "coefficientYear",
                "J");

            // assign coefficientMonth
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitLdcInsn(73232L);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "coefficientMonth",
                "J");

            // assign coefficientDay
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitLdcInsn(4L);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "coefficientDay",
                "J");

            // assign coefficientHour
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitLdcInsn(1234L);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "coefficientHour",
                "J");

            // assign coefficientMinute
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitLdcInsn(99998L);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "coefficientMinute",
                "J");

            // assign coefficientSum
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitLdcInsn(98924L);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "coefficientSum",
                "J");

            // assign year
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitFieldInsn(Opcodes.GETSTATIC,
                "java/util/Calendar",
                "YEAR",
                "I");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/util/Calendar",
                "get",
                "(I)I",
                false);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "year",
                "I");

            // assign month
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitFieldInsn(Opcodes.GETSTATIC,
                "java/util/Calendar",
                "MONTH",
                "I");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/util/Calendar",
                "get",
                "(I)I",
                false);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "month",
                "I");

            // assign day
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitFieldInsn(Opcodes.GETSTATIC,
                "java/util/Calendar",
                "DAY_OF_MONTH",
                "I");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/util/Calendar",
                "get",
                "(I)I",
                false);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "day",
                "I");

            // assign hour
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitFieldInsn(Opcodes.GETSTATIC,
                "java/util/Calendar",
                "HOUR_OF_DAY",
                "I");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/util/Calendar",
                "get",
                "(I)I",
                false);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "hour",
                "I");

            // assign minute
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitFieldInsn(Opcodes.GETSTATIC,
                "java/util/Calendar",
                "MINUTE",
                "I");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/util/Calendar",
                "get",
                "(I)I",
                false);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "minute",
                "I");

            // assign randomBoolean
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ILOAD, 2);
            mv.visitFieldInsn(Opcodes.PUTFIELD,
                "h06/MyDate",
                "randomBoolean",
                "Z");
        }
    }
}
