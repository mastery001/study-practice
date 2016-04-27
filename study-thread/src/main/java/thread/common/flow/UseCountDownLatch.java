package thread.common.flow;

import java.util.concurrent.CountDownLatch;

/**
 * 使用CountDownLatch实现并发流程控制
 * 
 * @author mastery
 * @time 2016年1月17日下午10:44:40
 */
public class UseCountDownLatch {

	private int sum;

	public void calculate() throws InterruptedException {
		final CountDownLatch countDown = new CountDownLatch(1);
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					sum += i;
				}
				System.out.println("开始执行线程,会执行完线程才会计算结果----");
				countDown.countDown();
			}

		}).start();
		countDown.await();
	}

	public int get() {
		return sum;
	}

	public static void main(String[] args) throws InterruptedException {
		UseCountDownLatch useCountDown = new UseCountDownLatch();
		useCountDown.calculate();
		System.out.println("线程执行完，才会计算出结果，sum=" + useCountDown.get());
	}
}
