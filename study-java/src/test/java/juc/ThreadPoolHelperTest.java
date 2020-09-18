package juc;

import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ThreadPoolHelperTest {

    private static Runnable newTask(int id) {
        return () -> {
            System.out.println("execute task " + id + " current time " + System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        Executor executor = ThreadPoolHelper.newFIFOPool();
//        Executor executor = ThreadPoolHelper.newPool(1);
        executor.execute(newTask(1));
        executor.execute(newTask(2));
    }

    @Test
    public void newFIFOPool() {
//        Executor executor = ThreadPoolHelper.newFIFOPool();
        Executor executor = ThreadPoolHelper.newPool();
        executor.execute(newTask(1));
        executor.execute(newTask(2));
    }
}