package thread.common.lockway;

import java.util.concurrent.TimeUnit;

import thread.lockway.LockWay;
import thread.lockway.PessimisticLockWay;

public class LockWayTest {

	private LockWay lockWay = new PessimisticLockWay();

	public LockWayTest() {
		new Thread(new Increase(), "A").start();
		new Thread(new Increase(), "B").start();
	}

	class Increase implements Runnable {

		@Override
		public void run() {
			while (true) {
				System.out.println(
						Thread.currentThread().getName() + " thread's i value is " + lockWay.incrementAndGet());
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		new LockWayTest();
	}
}
