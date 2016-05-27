package thread.lockway;

/**
 * java针对悲观锁和乐观锁的实现形式
 * @author mastery
 * @time 2016年1月17日下午4:27:41
 */
public abstract class LockWay {
	
	protected volatile int i;
	
	public abstract int incrementAndGet();
	
	protected int get() {
		return i;
	}
}
