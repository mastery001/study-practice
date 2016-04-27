package thread.common.lockway;

/**
 * 乐观锁的模拟实现方式
 * 	不加锁的形式，使用for循环和CAS算法来实现
 * @author mastery
 * @time 2016年1月16日下午11:18:02
 */
public class OptimisicLockWay extends LockWay {

	//private AtomicInteger i = new AtomicInteger();

	/* 采用乐观锁的不加锁机制，运用unsafe类的cpu级操作来实现线程安全的自加操作
	 * (non-Javadoc)
	 * @see thread.common.lockway.LockWay#increment()
	 */
	public int incrementAndGet() {
//		for(;;) {
//			int current = i.get();
//			int next = current + 1;
//			if(i.compareAndSet(current, next)) {
//				return next;
//			}
//		}
		for(;;) {
			int current = get();
			int next = current + 1;
			if(compareAndSet(current , next)) {
				return next;
			}
		}
	}

	private boolean compareAndSet(int current, int next) {
		if(current == i) {
			i = next;
			return true;
		}
		return false;
	}
	
}
