package linear.sequence;

import exception.StructureException;
import linear.AbstractList;

public class SequenceList<T> extends AbstractList<T>{

	/**
	 * 该顺序表的默认容量为10
	 */
	private final static int defaultCapacity = 10;
	/**
	 * 实现顺序表的数组
	 */
	private Object[] arrs;

	/**
	 * 实例化顺序表，使用默认的容量大小，为10
	 */
	public SequenceList() {
		this(defaultCapacity);
	}
	
	/**
	 * 实例化顺序表， 指定顺序表的容量
	 * @param capacity
	 */
	public SequenceList(int capacity) {
		arrs = new Object[capacity];
		size = 0;
	}
	
	@Override
	public void insert(int index, T t) throws StructureException {
		if(arrs.length <= size) {
			throw new StructureException("顺序表的容量已满");
		}
		if(index < 0 || index > size) {
			throw new StructureException("参数异常！不能小于0或者大于当前长度");
		}
		// 插入前先后移之后的元素
		for(int i = size ; i > index ; i--) {
			arrs[i] = arrs[i-1];
		}
		arrs[index] = t;
		size ++;
	}

	@Override
	public void delete(int index)  throws StructureException{
		if(isEmpty()) {
			throw new StructureException("该顺序表为空！不存在任何元素");
		}
		if(index < 0 || index >size - 1) {
			throw new StructureException("参数异常！不能小于0或者大于顺序表的容量");
		}
		for(int i = index+1 ; i < size ; i++ ) {
			arrs[i-1] = arrs[i];
		}
		arrs[size -1] = null;
		size--;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int index) throws StructureException {
		if(isEmpty()) {
			throw new StructureException("该顺序表为空！不存在任何元素");
		}
		if(index < 0 || index >arrs.length) {
			throw new StructureException("参数异常！不能小于0或者大于顺序表的容量");
		}
		return (T) arrs[index];
	}
	
}
