package jz.fiber.introduction;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;

public class FiberHelloWorld {
	
	private final AtomicInteger sum = new AtomicInteger();
	
	public static void main(String[] args) {
		FiberHelloWorld fiber = new FiberHelloWorld();
		fiber.newFiber(100);
		//fiber.newThread(100);
		System.out.println(fiber.sum);
	}
	
	public void newFiber(int count) {
		for(int i = 0 ; i < count ; i ++) {
			new Fiber<Long>(new SuspendableRunnable(){

				/**  
				 *   
				 */
				private static final long serialVersionUID = -3849946576564480724L;

				@Override
				public void run() throws SuspendExecution, InterruptedException {
					
					count();
					
					int i =0 ;
					
					for(i = 0 ; i < 10000 ; i++) {
						sum.incrementAndGet();
					}
					
					System.out.println(Thread.currentThread()+ "结束i=" + i);
					//TimeUnit.MICROSECONDS.sleep(10);
				}
				
			}).start();
		}
	}
	
	private void count() {
		for(int i = 0 ; i < 10000 ; i++) {
			sum.incrementAndGet();
		}
	}
	
	public void newThread(int count) {
		for(int i = 0 ; i < count ; i ++) {
			new Thread(new Runnable(){

				@Override
				public void run() {
					count();
				}
				
			}).start();
		}
	}
}
