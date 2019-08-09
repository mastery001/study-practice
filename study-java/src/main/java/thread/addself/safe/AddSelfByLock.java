package thread.addself.safe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import thread.LockTemplate;
import thread.LockTemplate.EmptyLockExecutor;

/**
 * 通过ReentrantLock锁来实现线程安全的自加操作
 * @author mastery
 * @time 2016年1月16日下午10:34:08
 */
public class AddSelfByLock {

	private int i;

	private LockTemplate lockTemplate = new LockTemplate(new ReentrantLock());

	// private final Object obj = new Object();

	public AddSelfByLock() {
		new Increase("A").start();
		new Increase("B").start();
	}

	public static void main(String[] args) {
		new AddSelfByLock();
	}

	class Increase extends Thread {

		public Increase(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (true) {
				try {
					lockTemplate.execute(new EmptyLockExecutor() {

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
