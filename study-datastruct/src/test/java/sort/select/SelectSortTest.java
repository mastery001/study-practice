package sort.select;

import org.junit.Test;

import sort.ArrayUtil;
import sort.Sort;

public class SelectSortTest {

	@Test
	public void testSort() {
		Sort s = new SelectSort();
		Integer[] arrs = new Integer[] { 65, 34, 25, 87, 12, 38, 56, 46, 14,
				77, 92, 23 };
		s.sort(arrs);
		ArrayUtil.print_r(arrs);
	}

}
