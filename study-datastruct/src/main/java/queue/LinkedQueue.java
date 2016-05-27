package queue;

import exception.StructureException;

public class LinkedQueue<T> implements Queue<T> {
	
	class Node<E> {
		E element;
		
		Node<E> next;

		public Node(E element) {
			super();
			this.element = element;
		}
	}
	
	/**
	 * 队头结点,该结点的元素值为空
	 */
	private Node<T> front;
	
	/**
	 * 队尾结点
	 */
	private Node<T> rear;
	
	public LinkedQueue() {
		front = new Node<T>(null);
	}
	
	@Override
	public void add(T t) throws StructureException {
		Node<T> node = new Node<T>(t);
		if(front.next == null) {
			front.next = node;
		}else {
			rear.next = node;
		}
		rear = node;
	}

	@Override
	public T remove() throws StructureException {
		T t = peek();
		front.next = front.next.next;
		return t;
	}

	@Override
	public T peek() throws StructureException {
		if(front.next == null) {
			throw new StructureException("队列为空！");
		}
		return front.next.element;
	}

}
