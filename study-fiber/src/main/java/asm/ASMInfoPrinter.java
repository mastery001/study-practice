package asm;

import org.objectweb.asm.*;

import java.io.IOException;

/**
 * Created by zouziwen on 2017/4/10.
 */
public class ASMInfoPrinter implements ClassVisitor {


    public static void main(String[] args) throws IOException {
        ASMInfoPrinter asmInfoPrinter = new ASMInfoPrinter();
        ClassReader cr = new ClassReader("java.lang.Runnable");
        cr.accept(asmInfoPrinter , 0);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println(name + " extends " + superName + " {");

    }

    @Override
    public void visitSource(String source, String debug) {

    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {

    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    @Override
    public void visitAttribute(Attribute attr) {

    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {

    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println("    " + desc + " " + name);
        return null;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println(" " + name + desc);
        return null;
    }

    @Override
    public void visitEnd() {

    }
}
