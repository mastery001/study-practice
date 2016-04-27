package thread.common.demo.sync;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: UnSynchronizedThread
 * @Description: 通过volatile关键字实现同步线程
 * @author mastery
 * @date 2016年1月2日 下午4:45:15
 */

public class SynchronizedThreadByVolatile {

	private volatile int i;

	public SynchronizedThreadByVolatile() {
		new Increase("A").start();
		new Increase("B").start();
	}

	public static void main(String[] args) {
		new SynchronizedThreadByVolatile();
	}

	class Increase extends Thread {

		public Increase(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (true) {
				i++;
				System.out.println(Thread.currentThread().getName() + " thread's i value is " + i);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
				}
			}

		}
	}

}
