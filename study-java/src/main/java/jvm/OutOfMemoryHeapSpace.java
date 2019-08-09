package jvm;

/**
 * jvm参数为-Xms256m -Xmx512m
 * Created by zouziwen on 2017/4/14.
 */
public class OutOfMemoryHeapSpace {

    public static void main(String[] args) throws InterruptedException {

        //TimeUnit.MINUTES.sleep(2);

        // 默认新生代和老年代的比例为1:2，计算出老年代最大为340-341之间
        // 可通过XX:NewRatio参数进行调整，若NewRatio=2则2表示新生代占年老代的1/2，占整个堆内存的1/3。
        // 当分配为341M产生java.lang.OutOfMemoryError: Java heap space
        byte[] hugeMemory = new byte[341 * 1024 * 1024];

        System.out.println(hugeMemory.length);
    }
}
