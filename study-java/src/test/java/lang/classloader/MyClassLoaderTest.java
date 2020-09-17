package lang.classloader;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

import static org.junit.Assert.*;

public class MyClassLoaderTest {

    private String className;

    private Class<?> clazz;

    private void listenerAndLoad(Path path , ClassLoader classLoader) throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService , StandardWatchEventKinds.ENTRY_MODIFY);
        while(true) {
            WatchKey key = watchService.take();
            for(WatchEvent<?> watchEvent : key.pollEvents()) {
                if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
//                    System.out.println(classLoader);
                    clazz = classLoader.loadClass(className);
                    invoke();
                }
            }
            // reset the keyf
            boolean valid = key.reset();
            // exit loop if the key is not valid (if the directory was
            // deleted,for
            if (!valid) {
                break;
            }
        }
    }

    public void invoke() throws Exception {
        clazz.getDeclaredMethod("print").invoke(null);
    }

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new MyClassLoader(null);
        MyClassLoaderTest myClassLoaderTest = new MyClassLoaderTest();
        myClassLoaderTest.className = "Check";
        myClassLoaderTest.clazz = classLoader.loadClass(myClassLoaderTest.className);

        // Check的print方法输出为：a
        myClassLoaderTest.invoke();

        /* 将Check的print的代码替换为输出b，并且替换文件后依然输出为a
        * 原因：同一个类加载时，会优先从缓存获取
        */
//        myClassLoaderTest.listenerAndLoad(new File("").toPath() , classLoader);

        /**
         * 换新的classloader则会重新加载，说明每一个classloader都会有一个独立的缓存
         */
        myClassLoaderTest.listenerAndLoad(new File("study-java").toPath() , new MyClassLoader(null));
    }

//    @Test
//    public void testDuplicateLoadSameClass() throws ClassNotFoundException {
////        String rootDir = new File("").getAbsolutePath();
////        rootDir = rootDir.substring(0 , rootDir.lastIndexOf("/"));
//////        System.out.println(rootDir);
////        MyClassLoader myClassLoader = new MyClassLoader(rootDir);
////        myClassLoader.loadClass("HmacDemo");
////        myClassLoader.loadClass("HmacDemo");
//
//        MyClassLoader myClassLoader = new MyClassLoader(null);
//        myClassLoader.loadClass("Check");
//    }
}