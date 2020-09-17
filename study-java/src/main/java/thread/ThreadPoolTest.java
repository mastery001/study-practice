package thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/*
 * @Author zouziwen
 * @CreateTime 2020-06-10 22:57
 */
public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {

        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        queue.offer(1);

        System.out.println(queue.take());

//        Executor executor = Executors.newCachedThreadPool();
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                while(true) {
//
//                }
//            }
//        });
    }
}
