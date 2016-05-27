package threadsync.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockQueueBySynchronized<E> implements BlockingQueue<E>{

	private Queue<E> list = new LinkedList<E>();
	
	private Object notEmpty = new Object();
	
	private Object notFull = new Object();
	
	private int maxLength = 10;
	
	@Override
	public void put(E e) throws InterruptedException {
		synchronized (notFull) {
			if(isFull()) {
				notFull.wait();
			}
		}
		list.add(e);
		synchronized (notEmpty) {
			notEmpty.notifyAll();
		}
	}

	@Override
	public E take() throws InterruptedException {
		synchronized (notEmpty) {
			if(isEmpty()) {
				//System.out.println("wait");
				//notEmpty.wait();
				// 等待3ms，防止生产者生产完，消费者持续消费并等待
				notEmpty.wait(3000);
			}
		}
		E e = list.poll();
		synchronized (notFull) {
			notFull.notifyAll();
			return e;
		}
	}
	
	private boolean isFull() {
		return list.size() == maxLength;
	}

	@Override
	public boolean isEmpty() {
		return list.size() == 0;
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
