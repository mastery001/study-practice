package sort;

/**
 * 排序总共分为五类：
 * 1.插入排序：直接插入排序，希尔排序
 * 2.选择排序：直接选择排序，堆排序
 * 3.交换排序：冒泡排序，快速排序
 * 4.归并排序
 * 5.基数排序
 * @author mastery
 *
 */
public interface Sort {
	
	/**
	 * 排序，若是基本类型则直接使用'>'操作符比较
	 * 若是对象比较则使用compareTo方法比较,所以比较的对象需要实现Comparable接口
	 * 若是比较的对象未实现Comparable接口则抛出IllegalArgumentException
	 * @param <T>
	 * @param elements
	 */
	<T> T[] sort(T[] elements) throws IllegalArgumentException;
	
}
