package speciallinear;

import java.util.Arrays;

public class Vector<T> {

	protected Object[] elements;

	protected int size;

	public Vector() {
		// 默认容器的容量为10
		this(10);
	}

	public Vector(int capacity) {
		elements = new Object[capacity];
		size = 0;
	}

	/**
	 * 添加元素至容器
	 * 
	 * @param t
	 */
	public void addElement(T t) {
		if (size == elements.length) {
			ensureCapacity(size + 1);
		}
		elements[size++] = t;
	}

	/**
	 * 删除指定位置的元素
	 * 
	 * @param index
	 */
	public void removeElementAt(int index) {
		if (index >= size) {
			throw new ArrayIndexOutOfBoundsException(index + " >= " + size);
		} else if (index < 0) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		int j = size - index - 1;
		if (j > 0) {
			System.arraycopy(elements, index + 1, elements, index, j);
		}
		size--;
		elements[size] = null; /* to let gc do its work */
	}

	@SuppressWarnings("unchecked")
	public T elementAt(int index) {
		if (index >= size) {
			throw new ArrayIndexOutOfBoundsException(index + " >= " + size);
		} else if (index < 0) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		return (T) elements[index];
	}
	
	/**
	 * 返回当前元素个数
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * 判断当前是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 扩大容器的容量
	 * 
	 * @param minCapacity
	 */
	private void ensureCapacityHelper(int minCapacity) {
		if (minCapacity - elements.length > 0)
			grow(minCapacity);
	}

	protected void ensureCapacity(int minCapacity) {
		if (minCapacity > 0) {
			ensureCapacityHelper(minCapacity);
		}
	}

	private void grow(int minCapacity) {
		int newCapacity = elements.length * 2 + 1;
		elements = Arrays.copyOf(elements, newCapacity);
	}
}
