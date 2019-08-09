package sort.convert;

import org.junit.Test;

import sort.ArrayUtil;
import sort.Sort;

public class BubbleSortTest {

	@Test
	public void testSortStrategy() {
		Sort s = new QuickSort();
		Integer[] arrs = new Integer[] { 64, 5, 7, 89, 6, 24 };
		ArrayUtil.print_r(arrs);
		s.sort(arrs);
		ArrayUtil.print_r(arrs);
	}

}
