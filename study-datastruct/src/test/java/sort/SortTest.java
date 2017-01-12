package sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import sort.convert.BubbleSort;
import sort.convert.QuickSort;
import sort.insert.InsertionSort;
import sort.insert.ShellSort;
import sort.other.MergeSort;
import sort.other.RadixSort;
import sort.select.HeapSort;
import sort.select.SelectSort;

public class SortTest {

	private void sort(Sort s) {
		Integer[] arrs = new Integer[] { 64, 5, 7, 89, 6, 24 };
		sort(s, arrs);
	}
	
	private void sort(Sort s , Integer[] arrs) {
		ArrayUtil.print_r(arrs);
		s.sort(arrs);
		ArrayUtil.print_r(arrs);
	}
	
	/************ 插入排序 *************/
	@Test
	public void testInsertionSort() {
		sort(new InsertionSort());
	}
	
	@Test
	public void testShellSort() {
		sort(new ShellSort(new int[] { 6, 3, 1 }));
	}
	
	/************ 插入排序 *************/
	
	
	/************ 选择排序 *************/
	@Test
	public void testSelectSort() {
		sort(new SelectSort());
	}
	
	@Test
	public void testHeapSort() {
		Random random = new Random();
		List<Integer> list = new ArrayList<Integer>();
		for(int i =0 ; i < 20; i ++) {
			list.add(random.nextInt(1000));
		}
		sort(new HeapSort() , list.toArray(new Integer[]{}));
	}
	
	/************ 选择排序 *************/
	
	
	/************ 交换排序 *************/
	@Test
	public void testBubbleSort() {
		sort(new BubbleSort());
	}

	@Test
	public void testQuickSort() {
		sort(new QuickSort());
	}
	/************ 交换排序 *************/
	
	/************ other排序 *************/
	@Test
	public void testMergeSort() {
		sort(new MergeSort());
	}
	
	@Test
	public void testRadixSort() {
		sort(new RadixSort());
	}
	/************ other排序 *************/
	
}
