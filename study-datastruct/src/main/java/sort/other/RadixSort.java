package sort.other;

import sort.AbstractSort;

  
/**  
 *@Description:  基数排序
 *@Author:zouziwen
 *@Since:2017年1月11日  
 *@Version:1.1.0  
 */
public class RadixSort extends AbstractSort{

	@Override
	protected <T> T[] sortStrategy(T[] elements) throws IllegalArgumentException {
		Integer[] arrs = (Integer[]) elements;
		// 10^k次幂
		int k = maxDigit(arrs);
		sort(arrs , (int) Math.pow(10, k));
		return elements;
	}

	private void sort(Integer[] arrs, int k) {
		int arrayLength = arrs.length;
		int bucketLength = 10;
		// 基数排序数组
		int[][] radixArrs = new int[bucketLength][arrayLength];
		// 用于保存当前桶的下标
		int bucket[] = new int[bucketLength];
		// 数位从个位开始
		int d = 1;
		int index = 0;
		while(d < k) {
			for(int arr : arrs) {
				// 求当前数位的值
				index = (arr / d) % 10;
				radixArrs[index][bucket[index]] = arr;
				bucket[index]++;
			}
			index = 0;
			// 从0-9中的桶中依次取出值放入数组arrs中，并进行下一位数排序
			for(int i = 0 ; i < bucketLength ; i ++) {
				// 此中有数据
				if(bucket[i] != 0) {
					for(int j = 0 ; j < bucket[i] ; j ++) {
						arrs[index] = radixArrs[i][j];
						index++;
					}
				}
				bucket[i] = 0;
			}
			d *= 10;
			// 重置index
			index = 0;
		}
	}

	/**  
	 * 获取最大数位
	 * @param arrs  
	 * @Description:  
	 */
	private int maxDigit(Integer[] arrs) {
		int k = 1;
		for(Integer arr : arrs) {
			int m = 1;
			while((arr / ((int)Math.pow(10 , m))) != 0) {
				m++;
			}
			if(m > k ) {
				k = m;
			}
		}
		return k;
	}

}
