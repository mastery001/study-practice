package sort.insert;

import org.junit.Test;

import sort.ArrayUtil;
import sort.Sort;

public class InsertionSortTest {

	@Test
	public void test() {
		Sort s = new InsertionSort();
		//Integer[] arrs = new Integer[] { 64, 5, 7, 89, 6, 24 };
		String[] arrs = new String[] { "sdf", "sre","za","aq","hrte"};
		ArrayUtil.print_r(arrs);
		s.sort(arrs);
		ArrayUtil.print_r(arrs);
		
	}
}
