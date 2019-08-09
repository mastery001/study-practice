package sort.other;

import org.junit.Test;

import sort.ArrayUtil;
import sort.Sort;

public class MergeSortTest {

	@Test
	public void testSort() {
		Sort s = new MergeSort();
		Integer[] arrs = new Integer[] { 64, 5, 7, 89, 6, 24 };
		ArrayUtil.print_r(arrs);
		s.sort(arrs);
		ArrayUtil.print_r(arrs);
	}

}
