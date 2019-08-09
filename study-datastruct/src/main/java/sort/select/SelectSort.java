package sort.select;

import sort.AbstractSort;

/**
 * 直接选择排序
 * @author mastery
 *
 */
public class SelectSort extends AbstractSort{

	@Override
	protected <T> T[] sortStrategy(T[] elements)
			throws IllegalArgumentException {
		int minIndex = 0;
		for(int i = 0 ; i < elements.length - 1 ; i++) {
			minIndex = i;
			for(int j = i+1 ; j < elements.length ; j++) {
				if(compare(elements[minIndex], elements[j]) > 0) {
					minIndex = j;
				}
			}
			if(minIndex != i) {
				T temp = elements[i];
				elements[i] = elements[minIndex];	
				elements[minIndex] = temp;	
			}
		}
		return elements;
	}

}
