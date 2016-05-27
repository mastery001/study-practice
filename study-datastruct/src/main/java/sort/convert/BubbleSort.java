package sort.convert;

import sort.AbstractSort;
/**
 * 冒泡排序
 * @author mastery
 *
 */
public class BubbleSort extends AbstractSort{

	@Override
	protected <T> T[] sortStrategy(T[] elements)
			throws IllegalArgumentException {
		for(int i = 0 ; i < elements.length - 1 ; i++) {
			for(int j = 0 ; j < elements.length - i - 1; j ++) {
				if(compare(elements[j], elements[j+1]) > 0) {
					T temp = elements[j];
					elements[j] = elements[j+1];
					elements[j+1] = temp;
				}
			}
		}
		return elements;
	}
}
