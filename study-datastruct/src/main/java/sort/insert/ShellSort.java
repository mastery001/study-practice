package sort.insert;

import sort.AbstractSort;

/**
 * 希尔排序
 * 		基本思想：把待排序的数据元素分成若干个小组，对同一小组内的数据元素用直接插入法
 * 				排序；小组的个数逐次缩小，当完成了所有数据元素都在一个组内的排序后排序
 * 				过程结束。希尔排序又称作为缩小增量排序。
 * 		时间复杂度分析：
 * 				比较希尔排序算法和直接插入排序算法，直接插入排序算法是两重循环，希尔排序
 * 				算法是四重循环，但分析希尔排序算法中四重循环的循环次数可以发现，四重循环
 * 				每重的循环次数都很小，并且当增量递减，小组变大时，小组内的数据元素数值已
 * 				基本有序，由前面讨论知道，越接近有序的直接插入排序算法的时间效率越高。
 * 				因此，希尔排序算法的时间复杂度较直接插入排序算法的时间复杂度改善很多。
 * 				希尔排序算法的时间复杂度分析比较复杂，实际所需的时间取决于各次排序时
 * 				增量的个数和增量的取值。研究证明，若增量的取值比较合理，希尔排序算法的
 * 				时间复杂度约为O(n(log2n)2);
 * 				希尔排序算法的空间复杂度为O(1).由于希尔排序算法是按增量分组进行的排序，
 * 				所以希尔排序算法是哟中不稳定的排序算法。
 * @author mastery
 *
 */
public class ShellSort extends AbstractSort {

	private int increments[];
	
	/**
	 * set the shellsort's increment
	 * @param increments
	 */
	public ShellSort(int increments[]) {
		this.increments = increments;
	}
	
	@Override
	protected <T> T[] sortStrategy(T[] elements) throws IllegalArgumentException  {
		int j;
		for(int increment : increments) {
			for(int k = 0 ; k < increment ; k++) {
				for(int i = k ; i < elements.length-increment ; i += increment) {
					T temp = elements[i+increment];
					j = i;
					while(j > -1 && compare(elements[j], temp) > 0) {
						elements[j+increment] = elements[j];
						j -= increment;
					}
					elements[j+increment] = temp;
				}
			}
		}
		return elements;
	}

}
