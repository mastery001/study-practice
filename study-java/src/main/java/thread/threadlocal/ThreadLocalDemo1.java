package thread.threadlocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ThreadLocalDemo1 {
	
	private static ThreadLocal<Object> objectThreadLocal = new ThreadLocal<Object>();
	
	public static Object getObject() {
		Object obj = objectThreadLocal.get();
		if(obj == null) {
			obj = getObject0();
			objectThreadLocal.set(obj);
		}
		try {
			TimeUnit.SECONDS.sleep(new Random().nextInt(10));
		} catch (InterruptedException e) {
		}
		return obj;
		
	}
	
	private static Object getObject0() {
		return String.valueOf(new Random().nextInt(1000));
	}
	
	public static void main(String[] args) {
		for(int i = 0 ; i < 10; i ++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + "----" + ThreadLocalDemo1.getObject());
				}
			} , "thread-" + i).start();;
		}
	}
}
