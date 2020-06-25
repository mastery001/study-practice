package juc;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author zouziwen
 * @CreateTime 2019-10-23 15:18
 */
public class ConcurrentMapUpdate {

    private static final Map<String , String> map = Maps.newConcurrentMap();

    static final int SIZE = 1000000;

    private static void add() {
        for(int i = 0 ; i < SIZE ; i ++ ) {
            map.put(String.valueOf(i) , String.valueOf(i));
        }
    }

    /**
     * ConcurrentMap同时修改、删除、遍历不会造成ConcurrentModificationException,但遍历时的统计操作会受影响
     * why？
     * - 分段锁的保障
     * - 删除不会真实移除元素
     * @param args
     */
    public static void main(String[] args) {
        add();
        new Thread(new IteratorRunnable()).start();
        new Thread(new RemoveRunnable()).start();
    }

    private static class IteratorRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("iterator start");
            while(true) {
                for(Map.Entry<String , String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    // nothing
                }
                if(map.isEmpty()) {
                    System.exit(0);
                }
            }

        }
    }

    private static class RemoveRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("remove start");
            for(int i = 0 ; i < 1000 ; i ++ ) {
                map.remove(String.valueOf(i));
            }

        }
    }
}
