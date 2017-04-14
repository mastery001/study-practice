package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * jvm参数为-XX:PermSize=10m -XX:MaxPermSize=10m
 * 限制了持久代大小，使得字符串常量进入到持久代，当常量大小超出持久代时，产生OOM
 * Created by zouziwen on 2017/4/14.
 */
public class OutOfMemoryPermSpace {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();

        int i = 0;

        while(true) {
            //当调用 intern 方法时，如果池已经包含一个等于此 String 对象的字符串（该对象由 equals(Object) 方法确定），
            // 则返回池中的字符串。否则，将此 String 对象添加到池中，并且返回此 String 对象的引用。
            list.add(String.valueOf(i++).intern());
        }
    }
}
