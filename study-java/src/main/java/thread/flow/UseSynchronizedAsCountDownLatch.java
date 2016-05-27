package thread.flow;

/**
 * 通过synchronized关键字模拟CountDownLatch功能
 * 
 * @author mastery
 * @time 2016年1月17日下午10:52:44
 */
public class UseSynchronizedAsCountDownLatch {
	private final byte[] lock = new byte[0];
	private int sum;

	public void calculate() throws InterruptedException {
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					sum += i;
				}
				System.out.println("开始执行线程,会执行完线程才会计算结果----");
				synchronized (lock) {
					lock.notifyAll();
				}
			}

		}).start();
		synchronized (lock) {
			lock.wait();
		}
	}

	public int get() {
		return sum;
	}

	public static void main(String[] args) throws InterruptedException {
		UseSynchronizedAsCountDownLatch useCountDown = new UseSynchronizedAsCountDownLatch();
		useCountDown.calculate();
		System.out.println("线程执行完，才会计算出结果，sum=" + useCountDown.get());
	}
}
