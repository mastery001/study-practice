package thread.lockway;

/**
 * 悲观锁实现
 * 	在操作初期就加上排他锁
 * @author mastery
 * @time 2016年1月17日下午4:30:35
 */
public class PessimisticLockWay extends LockWay{

	private final byte[] lock = new byte[0];
	
	@Override
	public int incrementAndGet() {
		synchronized(lock) {
			return ++i;
		}
	}
	
}
