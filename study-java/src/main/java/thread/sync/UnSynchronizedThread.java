package thread.sync;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: UnSynchronizedThread
 * @Description: 非同步线程
 * @author mastery
 * @date 2016年1月2日 下午4:45:15
 */

public class UnSynchronizedThread {

	private int i;

	public UnSynchronizedThread() {
		new Increase("A").start();
		new Increase("B").start();
	}

	public static void main(String[] args) {
		new UnSynchronizedThread();
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
