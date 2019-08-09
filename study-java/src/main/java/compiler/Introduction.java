package compiler;

import javax.tools.*;
import java.io.*;
import java.net.URL;

/**
 * @Author zouziwen
 * @CreateTime 2019-08-02 16:02
 */
public class Introduction {

    public static void main(String[] args) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        compiler.getSourceVersions().forEach(System.out::println);

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager javaFileManager = compiler.getStandardFileManager(diagnostics , null , null);


        Iterable<? extends JavaFileObject> sources = javaFileManager.getJavaFileObjects(new File("Check.java"));

        JavaCompiler.CompilationTask task = compiler.getTask(null , javaFileManager , diagnostics , null , null , sources);

        task.call();

//        DynamicClassLoader dynamicClassLoader = new DynamicClassLoader();
//
//        Class<?> clazz = dynamicClassLoader.loadClass("HmacDemo");
//
//        System.out.println(clazz);
    }

    private static class DynamicClassLoader extends ClassLoader {

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {

            try {
                byte[] buffer = loadClassData(new FileInputStream(name + ".class"));
                return defineClass(name , buffer , 0 , buffer.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.findClass(name);
        }

        private byte[] loadClassData(InputStream is) {
            //read class
            ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
            //write into byte
            int len =0;
            try {
                while((len=is.read())!=-1){
                    byteSt.write(len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //convert into byte array
            return byteSt.toByteArray();
        }
    }
}
