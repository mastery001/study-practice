package sort.select;

import sort.AbstractSort;

  
/**  
 * 堆排序
 *@Description:  
 *@Author:zouziwen
 *@Since:2017年1月11日  
 *@Version:1.1.0  
 */
public class HeapSort extends AbstractSort {

	@Override
	protected <T> T[] sortStrategy(T[] elements) throws IllegalArgumentException {
		int length = elements.length;
		
		buildMaxHeap(elements , length);
		
		// 将堆中的末端节点逐个进行最大堆调整，使得子节点永远小于父节点
		for(int i = length - 1; i > 0  ; i--) {
			swap(elements , 0 , i);
			maxHeapify(elements , 0 , i);
		}
		return elements;
	}

	
	/**  
	 * 最大堆调整
	 * @param elements
	 * @param head
	 * @param size  
	 * @Description:  
	 */
	private <T> void maxHeapify(T[] elements, int index , int heapSize) {
		int max = index;
		int left = leftOf(index) , right = rightOf(index);
		// 分别对左右子节点进行比较
		if(left < heapSize && compare(elements[left],elements[max]) > 0) {
			max = left;
		}
		if(right < heapSize && compare(elements[right],elements[max]) > 0) {
			max = right;
		}
		if(max != index) {
			swap(elements, max, index);
			// 像此次的max继续向下递归调整成最大堆（检查max和max的左右节点是否能构成最大堆）
			maxHeapify(elements, max, heapSize);
		}
	}


	/**  
	 * 创建最大堆
	 * @param elements  
	 * @Description:  
	 */
	private <T> void buildMaxHeap(T[] elements , int length) {
		// 从中间节点逆向至根节点，使其成为最大堆（根节点最大）
		int midParent = parentOf(length);
		
		for(int i = midParent ; i >= 0 ; i--) {
			maxHeapify(elements, i, length);
		}
	}

	private <T> void swap(T[] elements , int i , int j) {
		T tmp = elements[i];
		elements[i] = elements[j];
		elements[j] = tmp;
	}
	

	private int parentOf(int index) {
		return (index - 1 ) / 2;
	}
	
	private int leftOf(int index) {
		return 2 * index + 1;
	}
	
	private int rightOf(int index) {
		return 2 * (index + 1);
	}
}
