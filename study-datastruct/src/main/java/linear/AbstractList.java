package linear;

import exception.StructureException;

public abstract class AbstractList<T> implements List<T>{
	
	/**
	 * 结构的长度
	 */
	protected int size;

	@Override
	public int size() throws StructureException {
		return size;
	}

	@Override
	public boolean isEmpty() throws StructureException {
		return size == 0;
	}
}
