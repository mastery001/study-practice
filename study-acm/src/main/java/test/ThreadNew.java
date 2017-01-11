package test;

import java.util.concurrent.TimeUnit;

public class ThreadNew {
	
	private static ThreadNew instance = new ThreadNew();
	
	public static ThreadNew get() {
		return instance;
	}
	
	private ThreadNew() {
		System.out.println("create");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
	}
}
