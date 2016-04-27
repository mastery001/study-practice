package thread.common.addself.safe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过{@link java.util.concurrent.atomic} 包下的AtomicInteger类来实现线程安全的自加操作
 * @author mastery
 * @time 2016年1月16日下午10:36:43
 */
public class AddSelfByAtomicInteger {
	
	private AtomicInteger count = new AtomicInteger();
	
	public void increment() {
		count.incrementAndGet();
	}
	
	public int getCount() {
		return count.get();
	}
}
