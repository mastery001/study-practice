package thread.common.addself.safe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过Lock-Free算法来实现线程安全的设置最大值的操作
 * @author mastery
 * @time 2016年1月16日下午10:36:43
 */
public class AddSelfByLockFreeAlgorithm {
	
	private AtomicInteger max = new AtomicInteger();
	
	public void set(int value) {
		for(;;) {
			int current = max.get();
			if(value > current) {
				if(max.compareAndSet(current, value)) {
					break;
				}else {
					continue;
				}
			}else {
				break;
			}
		}
	}
	
	public int getMax() {
		return max.get();
	}
}
