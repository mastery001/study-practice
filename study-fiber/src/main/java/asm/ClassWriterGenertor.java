package asm;

import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.objectweb.asm.Opcodes.*;


/**
 * Created by zouziwen on 2017/4/10.
 */
public class ClassWriterGenertor {

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = null;
        try {
            ClassWriter cw = new ClassWriter(0);
            cw.visit(V1_5 , ACC_PUBLIC + ACC_FINAL + ACC_STATIC , "asm/ASMClassVister" , null , "java/lang/Object" , new String[]{"org/objectweb/asm/ClassVister"});
            cw.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL , "VERSION" , "Ljava/lang/String;" , null , new String("1.5")).visitEnd();
            byte[] b = cw.toByteArray();
            File file = new File("ASMClassVister.class");
            System.out.println(file.getAbsolutePath());
            if(!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(b);
//
//            ClassReader cr = new ClassReader(b);
//            System.out.println(cr.getClassName());

        } finally {
            if(outputStream != null) {
                outputStream.close();
            }
        }
    }
}
