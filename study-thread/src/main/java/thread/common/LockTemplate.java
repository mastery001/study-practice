package thread.common;

import java.util.concurrent.locks.Lock;

/**
 * @ClassName: LockTemplate
 * @Description: Lock的模板
 * @author mastery
 * @date 2016年1月2日 下午4:39:46
 */
public class LockTemplate {

	private final Lock lock;

	public LockTemplate(Lock lock) {
		this.lock = lock;
	}

	/**
	 * 有返回值的执行器
	 * 
	 * @author Administrator
	 *
	 * @param <E>
	 */
	public interface LockExecutor<E> {

		public E execute() throws InterruptedException;

	}

	/**
	 * 没有返回值的执行器
	 * 
	 * @author mastery
	 *
	 */
	public interface EmptyLockExecutor {

		public void execute() throws InterruptedException;
	}

	public <E> E execute(LockExecutor<E> executor) throws InterruptedException {
		lock.lock();
		try {
			return executor.execute();
		} finally {
			lock.unlock();
		}
	}

	public void execute(EmptyLockExecutor executor) throws InterruptedException {
		lock.lock();
		try {
			executor.execute();
		} finally {
			lock.unlock();
		}
	}

}
