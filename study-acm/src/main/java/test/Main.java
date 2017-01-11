package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

	private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 20 + 1);
	
	public static void execute() {
		for(int i = 0 ; i < 10000 ; i ++) {
			if(i % 100 == 0) {
				executor.execute(new Runnable() {
					
					@Override
					public void run() {
						ThreadNew.get();
					}
				});
			}
		}
	}
	
	public static void main(String[] args) {
		//execute();
		System.out.println(boolean.class);
		System.out.println(Boolean.class);
		NotifyRunnable nr = new NotifyRunnable();
		new Thread(nr).start();
		synchronized (nr) {
			nr.notify();
			System.out.println(12);
		}
		
	}
	
	private static class NotifyRunnable implements Runnable {
		
		
		@Override
		public void run() {
			System.out.println(1111);
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block  
				e.printStackTrace();
			}
		}
		
	}
}
