package thread.flow;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import thread.LockTemplate;
import thread.LockTemplate.EmptyLockExecutor;

/**
 * 通过ReentrantLock类模拟CountDownLatch功能
 * 
 * @author mastery
 * @time 2016年1月17日下午10:52:44
 */
public class UseLockAsCountDownLatch {
	private final Lock lock = new ReentrantLock();

	private Condition awaitComplete = lock.newCondition();

	private LockTemplate lockTemplate = new LockTemplate(lock);

	private int sum;

	public void calculate() throws InterruptedException {
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					sum += i;
				}
				System.out.println("开始执行线程,会执行完线程才会计算结果----");
				try {
					lockTemplate.execute(new EmptyLockExecutor() {

						@Override
						public void execute() throws InterruptedException {
							awaitComplete.signalAll();
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}).start();
		lockTemplate.execute(new EmptyLockExecutor() {

			@Override
			public void execute() throws InterruptedException {
				awaitComplete.await();
			}
		});
	}

	public int get() {
		return sum;
	}

	public static void main(String[] args) throws InterruptedException {
		UseLockAsCountDownLatch useCountDown = new UseLockAsCountDownLatch();
		useCountDown.calculate();
		System.out.println("线程执行完，才会计算出结果，sum=" + useCountDown.get());
	}
}
