package threadsync.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import thread.LockTemplate;
import thread.LockTemplate.EmptyLockExecutor;
import thread.LockTemplate.LockExecutor;

/**
 * 使用ReentrantLock的Condition来实现阻塞队列
 * 
 * @author Administrator
 *
 * @param <E>
 */
public class BlockQueueByLockCondition<E> implements BlockingQueue<E> {

	private final Lock lock = new ReentrantLock();

	private final Condition notEmpty = lock.newCondition();

	private final Condition notFull = lock.newCondition();

	private LockTemplate template = new LockTemplate(lock);

	private Queue<E> list = new LinkedList<E>();

	private int maxLength = 10;

	@Override
	public void put(final E e) throws InterruptedException {
		template.execute(new EmptyLockExecutor() {

			@Override
			public void execute() throws InterruptedException {
				if(isFull()) {
					notFull.await();
				}
				if(isEmpty()) {
					notEmpty.signalAll();
				}
				list.add(e);
			}

		});
	}

	@Override
	public E take() throws InterruptedException {
		return template.execute(new LockExecutor<E>() {

			@Override
			public E execute() throws InterruptedException {
				if (isEmpty()) {
					notEmpty.await(3 , TimeUnit.SECONDS);
				}
				if(isFull()) {
					notFull.signalAll();
				}
				return list.poll();
			}

		});
	}

	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	private boolean isFull() {
		return list.size() == maxLength;
	}
	
	@Override
	public E remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean add(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offer(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int remainingCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		// TODO Auto-generated method stub
		return 0;
	}

}
