package queue;

import exception.StructureException;

public interface Queue<T> {

	/**
	 * 入队
	 * 
	 * @param t
	 */
	void add(T t) throws StructureException;

	/**
	 * 出队
	 * 
	 * @return
	 */
	T remove() throws StructureException;

	/**
	 * 得到队头元素
	 * 
	 * @return
	 */
	T peek() throws StructureException;
}
