package linear.linked;

import exception.StructureException;
import linear.AbstractList;

/**
 * @Description 单链表
 * @author mastery
 * @Date 2015年6月30日下午6:11:17
 * @param <T>
 */
public class SingleLinkedList<T> extends AbstractList<T> {

	class Node<T1> {

		T1 element;
		
		Node<T1> next;
		
		public Node(Node<T1> next) {
			super();
			this.next = next;
		}

		public Node(T1 element, Node<T1> next) {
			super();
			this.element = element;
			this.next = next;
		}

		@Override
		public String toString() {
			return element.toString();
		}
	}
	
	
	/**
	 * 头结点,该结点没有参数值，只是指向第一个元素的结点
	 */
	private Node<T> head;
	
	/**
	 * 当前的结点
	 */
	private Node<T> currentNode;
	
	public SingleLinkedList() {
		head = currentNode = new Node<T>(null);
		size = 0;
	}

	/**
	 * 得到当前下标对应的结点
	 * @param index
	 * @throws StructureException
	 */
	public void indexNodeToCurrent(int index) throws StructureException {
		currentNode = head;
		if(index < -1 || index > size -1) {
			throw new StructureException("index参数异常！");
		}
		if(index == -1) {
			return ;
		}
		currentNode = head.next;
		int j = 0 ; 
		while(currentNode != null && j < index) {
			currentNode = currentNode.next;
			j++;
		}
	}
	
	@Override
	public void insert(int index, T t) throws StructureException {
		if(index < 0 || index > size) {
			throw new StructureException("index参数异常！");
		}
		// 得到当前下标的上一个结点
		indexNodeToCurrent(index-1);
		// 将新元素生成结点插入到当前结点下
		currentNode.next = new Node<T>(t , currentNode.next);
		size++;
	}

	@Override
	public void delete(int index) throws StructureException {
		if(isEmpty()) {
			throw new StructureException("链表为空");
		}
		if(index < 0 || index > size) {
			throw new StructureException("index参数异常");
		}
		indexNodeToCurrent(index-1);
		Node<T> twoNextNode = currentNode.next.next;
		currentNode.next = twoNextNode;
		size--;
	}

	@Override
	public T get(int index) throws StructureException {
		if(isEmpty()) {
			throw new StructureException("链表为空");
		}
		if(index < 0 || index > size) {
			throw new StructureException("index参数异常！");
		}
		indexNodeToCurrent(index);
		return currentNode.element;
	}

	public T findKNode(int target) {
		if(target < 1) {
			return null;
		}
		Node<T> p = head;
		Node<T> q = p;
		int i = 0 , j = 0;
		while(p != null && q != null && q.next != null) {
			p = p.next;
			q = q.next.next;
			i ++;
			if(i == target) {
				return p.element;
			}
			j += 2;
		}
		// 如果是奇数，则需要长度减1
		if(q == null) {
			j --;
		}
		if(j < target) {
			return null;
		}
		while(i < target && p != null) {
			p = p.next;
			i++;
		}
		return p.element;
	}

	public static void main(String[] args) throws StructureException {
		SingleLinkedList<Integer> linkedList = new SingleLinkedList<Integer>();
		linkedList.insert(0 , 1);
		linkedList.insert(1 , 7);
		linkedList.insert(2 , 5);
		linkedList.insert(3 , 2);
		linkedList.insert(4 , 9);
		linkedList.insert(5 , 4);

		System.out.println(linkedList.findKNode(0));
//		System.out.println(linkedList.findKNode(1));
//		System.out.println(linkedList.findKNode(3));
//		System.out.println(linkedList.findKNode(4));
//		System.out.println(linkedList.findKNode(6));

	}

}
