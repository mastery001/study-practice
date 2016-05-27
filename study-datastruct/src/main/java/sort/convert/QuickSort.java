package sort.convert;

import sort.AbstractSort;

/**
 * 快速排序
 * @author mastery
 *
 */
public class QuickSort extends AbstractSort {

	@Override
	protected <T> T[] sortStrategy(T[] elements)
			throws IllegalArgumentException {
		quickSort(elements, 0, elements.length - 1);
		return elements;
	}

	<T> void quickSort(T[] elements, int low, int high) {
		if (low < high) {
			// 获取快速排序的基准
			int middle = partition(elements, low, high);
			// 重新对比基准小的左半部进行快速排序
			quickSort(elements, low, middle - 1);
			// 对比基准大的右半部排序
			quickSort(elements, middle + 1, high);
		}
	}

	private <T> int partition(T[] elements, int low, int high) {
		int i = low, j = high + 1;
		T x = elements[low];
		while (true) {
			// 从左往右取得第一个比基准小的数
			while (compare(elements[++i], x) < 0);
			// 从右往左取得第一个比基准大的数
			while (compare(elements[--j], x) > 0);
			// 若比基准小的数的位置>比基准大的数的位置则中断循环
			if (i >= j)
				break;
			T temp = elements[i];
			elements[i] = elements[j];
			elements[j] = temp;
		}
		elements[low] = elements[j];
		elements[j] = x;
		return j;
	}

	public <T> int partition1(T[] elements, int low, int high) {
		// 数组的第一个作为中轴
		T x = elements[low];
		while (low < high) {
			while (low < high && compare(elements[high], x) >= 0) {
				high--;
			}
			elements[low] = elements[high]; // 比中轴小的记录移到低端
			while (low < high && compare(elements[low], x) <= 0) {
				low++;
			}
			elements[high] = elements[low]; // 比中轴大的记录移到高端
		}
		elements[low] = x; // 中轴记录到尾
		return low;
	}

}
