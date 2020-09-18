package juc;

import java.util.concurrent.*;

/**
 * @Author zouziwen
 * @CreateTime 2020-08-31 16:38
 */
public class ThreadPoolHelper {

    public static Executor newFIFOPool() {
        return newFIFOPool(60L, TimeUnit.SECONDS);
    }

    public static Executor newFIFOPool(long keepAliveTime, TimeUnit unit) {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                keepAliveTime, unit,
                new SynchronousQueue<Runnable>());
    }

    public static Executor newPool() {
        return newPool(Runtime.getRuntime().availableProcessors());
    }

    public static Executor newPool(int coreSize) {
        return new ThreadPoolExecutor(coreSize , coreSize , 0 , TimeUnit.SECONDS , new LinkedBlockingQueue<>());
    }
}
