package thread.deadlock;

public class DeadLock implements Runnable {

	class Task implements Runnable {

		private byte[] lock1 = new byte[0];
		private byte[] lock2 = new byte[0];
		private boolean flag = true;

		@Override
		public void run() {
			if (flag) {
				flag = false;
				synchronized (lock1) {
					System.out.println(Thread.currentThread().getName() + "lock1");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (lock2) {
						System.out.println(Thread.currentThread().getName() + "lock2");
					}
				}
			} else {
				flag = true;
				synchronized (lock2) {
					System.out.println(Thread.currentThread().getName() + "lock2");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (lock1) {
						System.out.println(Thread.currentThread().getName() + "lock1");
					}
				}
			}
		}

	}

	@Override
	public void run() {
		Task task = new Task();
		new Thread(task, "task1").start();
		new Thread(task, "task2").start();
	}
	
	public static void main(String[] args) {
		new Thread(new DeadLock()).start();;
	}
}
