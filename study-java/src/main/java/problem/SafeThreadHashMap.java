package problem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 线程安全的HashMap
 * 
 * @author zouziwen
 *
 *         2016年4月22日 下午3:49:56
 */
public class SafeThreadHashMap<K, V> extends HashMap<K, V> implements Map<K, V> {

	/**
	 * 2016年4月22日 下午3:52:04
	 */
	private static final long serialVersionUID = 4530811996637218105L;

	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();

	public SafeThreadHashMap() {
		super();
	}

	public SafeThreadHashMap(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public V get(Object key) {
		readLock.lock();
		try {
			return super.get(key);
		} finally {
			readLock.unlock();
		}
		
	}

	@Override
	public V put(K key, V value) {
		writeLock.lock();
		try {
			return super.put(key, value);
		} finally {
			writeLock.unlock();
		}
		
	}

}
