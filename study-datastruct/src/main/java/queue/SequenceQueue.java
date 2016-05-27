package queue;

import exception.StructureException;
import speciallinear.Vector;

public class SequenceQueue<T> extends Vector<T> implements Queue<T> {

	@Override
	public void add(T t) {
		addElement(t);
	}

	@Override
	public T remove() throws StructureException {
		T t = peek();
		removeElementAt(0);
		return t;
	}

	@Override
	public T peek() throws StructureException {
		if (size == 0) {
			throw new StructureException("队列中无元素！");
		}
		return elementAt(0);
	}

}
