package sort.other;

import sort.AbstractSort;

  
/**  
 * 归并排序
 *@Description:  
 *@Author:zouziwen
 *@Since:2017年1月11日  
 *@Version:1.1.0  
 */
public class MergeSort extends AbstractSort{

	@Override
	protected <T> T[] sortStrategy(T[] elements)
			throws IllegalArgumentException {
		sort(elements , 0 , elements.length - 1);
		return elements;
	}

	private <T> void sort(T[] elements , int left , int right ) {
		if(left >= right) return;
		
		int mid = (right + left) / 2;
		
		// 对数组左半部排序
		sort(elements , left , mid);
		// 对数组右半部排序
		sort(elements , mid + 1 , right);
		// 将排序后的两个数组归并
		merge(elements , left , mid , right);
	}

	
	@SuppressWarnings("unchecked")
	private <T> void merge(T[] elements , int left , int mid , int right) {
		Object[] tmp = new Object[elements.length];
		// 右半部指针下标
		int r = mid + 1;
		// 左半部指针下标 
		int l = left;
		int tmpIndex = left;
		// 逐个进行归并操作
		while(l <= mid && r <= right) {
			if(compare(elements[l], elements[r]) <= 0) {
				tmp[tmpIndex++] = elements[l++];
			}else {
				tmp[tmpIndex++] = elements[r++];
			}
		}
		// 左半部未归并的部分归并
		while(l <= mid) {
			tmp[tmpIndex++] = elements[l++];
		}
		// 右半部未归并的部分归并
		while(r <= right) {
			tmp[tmpIndex++] = elements[r++];
		}
		// 将tmp数组复制至排序前的位置
		while(left <= right) {
			elements[left] = (T) tmp[left];
			left ++;
		}
	}
}
