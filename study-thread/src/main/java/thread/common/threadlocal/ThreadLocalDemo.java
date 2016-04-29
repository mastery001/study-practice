package thread.common.threadlocal;

import java.util.Random;

public class ThreadLocalDemo {
	
	private static ThreadLocal<Object> objectThreadLocal = new ThreadLocal<Object>();
	
	public static Object getObject() {
		if(objectThreadLocal.get() == null) {
			Object obj = getObject0();
			objectThreadLocal.set(obj);
			return obj;
		}else {
			return objectThreadLocal.get();
		}
	}
	
	private static Object getObject0() {
		return String.valueOf(new Random().nextInt(1000));
	}
	
	public static void main(String[] args) {
		System.out.println(ThreadLocalDemo.getObject());
	}
}
