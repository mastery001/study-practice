package sort;

public abstract class AbstractSort implements Sort {

	@Override
	public <T> T[] sort(T[] elements) throws IllegalArgumentException {
		if (elements == null || elements.length == 0) {
			throw new NullPointerException(
					"the parameter is null or the parameter's length = 0");
		}
		// if the array's length is 1 , not sort
		if (elements.length == 1) {
			return elements;
		}
		checkIsIllegal(elements[0]);
		return sortStrategy(elements);
	}

	protected abstract <T> T[] sortStrategy(T[] elements)  throws IllegalArgumentException ;

	/**
	 * 用参数o1和o2进行比较，默认使用compareTo方法
	 * 若o1>o2则返回>=1的数
	 * 若o1=o2返回0
	 * 若o1<o2返回<=-1的数
	 * @param o1
	 * @param o2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> int compare(T o1, T o2) {
		return compareByObject((Comparable<Object>) o1, (Comparable<Object>) o2);
	}

	private int compareByObject(Comparable<Object> o1, Comparable<Object> o2) {
		return o1.compareTo(o2);
	}

	/**
	 * check that the object implements the Comparable interface
	 * @param o1
	 */
	private <T> void checkIsIllegal(T o1) {
		/**
		 * check that the object implements the Comparable interface
		 */
		if (!(o1 instanceof Comparable)) {
			throw new IllegalArgumentException(
					"if the obj\'s type is not a primitive type and it should implements the Comparable interface");
		}
	}
}
