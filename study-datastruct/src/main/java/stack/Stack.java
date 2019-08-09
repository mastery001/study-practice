package stack;

import exception.StructureException;
import speciallinear.Vector;

public class Stack<T> extends Vector<T> {
	
	/**
	 * 入栈操作
	 * @param t
	 */
	public void push(T t){
		addElement(t);
	}
	
	/**
	 * 弹出栈顶元素并且删除
	 * @return
	 * @throws StructureException 
	 */
	public T pop() throws StructureException {
		T t = peek(); 
		removeElementAt(size - 1);
		return t;
	}
	
	/**
	 * 得到栈顶元素但删除
	 * @return
	 * @throws StructureException 
	 */
	public T peek() throws StructureException {
		if(size == 0) {
			throw new StructureException("栈中无元素！");
		}
		return elementAt(size -1);
	}
	
}
