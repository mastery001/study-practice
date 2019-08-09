package thread.addself.safe;

import java.util.concurrent.TimeUnit;

/**
 * 通过synchronized关键字来实现线程安全的自减操作
 * @author mastery
 * @time 2016年1月16日下午10:34:42
 */
public class AddSelfBySynchronized {

	public AddSelfBySynchronized() {
		Ticket t = new Ticket();
		for(int i = 1 ; i < 5 ; i ++) {
			new Thread(t , "窗口" + i).start();
		}
	}
	
	public static void main(String[] args) {
		new AddSelfBySynchronized();
	}
	
	class Ticket implements Runnable {
		
		private final Object obj = new Object();
		
		private int ticket = 400;
		
		@Override
		public void run() {
			while(true) {
				/**
				 * synchronized(new Object()) {
				 * 当使用这种方式时并未锁住代码块，出现同时消费一张票的现象
				 */
				//synchronized(new Object()) {
				synchronized(obj) {
					try {
						TimeUnit.MILLISECONDS.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(ticket <= 0) {
						break;
					}
					System.out.println(Thread.currentThread().getName() + "---卖出" + ticket--);
				}
			}
		}
		
	}
	
}
