package thread.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import thread.LockTemplate;
import thread.LockTemplate.EmptyLockExecutor;

/**
 * @ClassName: SynchronizedThreadByObejctLock
 * @Description: 通过Lock对象ss来实现同步线程
 * @author mastery
 * @date 2016年1月2日 下午4:47:03
 */

public class SynchronizedThreadByLock {

	private int i;

	private LockTemplate lock = new LockTemplate(new ReentrantLock());

	public SynchronizedThreadByLock() {
		new Increase("A").start();
		new Increase("B").start();
	}

	public static void main(String[] args) {
		new SynchronizedThreadByLock();
	}

	class Increase extends Thread {

		public Increase(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (true) {
				try {
					lock.execute(new EmptyLockExecutor() {

						@Override
						public void execute() {
							i++;
							System.out.println(Thread.currentThread().getName() + " thread's i value is " + i);
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e) {
							}

						}

					});
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
