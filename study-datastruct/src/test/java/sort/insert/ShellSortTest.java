package sort.insert;

import org.junit.Test;

import sort.ArrayUtil;
import sort.Sort;

public class ShellSortTest {

	@Test
	public void testShellSort() {
		int[] increments = new int[] { 6, 3, 1 };
		Sort s = new ShellSort(increments);
		Integer[] arrs = new Integer[] { 65, 34, 25, 87, 12, 38, 56, 46, 14,
				77, 92, 23 };
		s.sort(arrs);
		ArrayUtil.print_r(arrs);
	}

}
