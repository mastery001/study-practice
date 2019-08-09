package thread.sync;

import java.util.concurrent.TimeUnit;

/** 
* @ClassName: SynchronizedThreadByObejctLock 
* @Description: 通过对象锁来实现同步线程
* @author mastery
* @date 2016年1月2日 下午4:47:03 
*/ 

public class SynchronizedThreadByObejctLock {
	
	private int i;
	
	private final Object lock = new Object();

	public SynchronizedThreadByObejctLock() {
		new Increase("A").start();
		new Increase("B").start();
	}

	public static void main(String[] args) {
		new SynchronizedThreadByObejctLock();
	}

	class Increase extends Thread {

		public Increase(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (true) {
				synchronized(lock) {
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

}
