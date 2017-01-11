package algorithm;

import java.util.Arrays;

/**
 * @Title:
 * @Description: 一个无序数组里有若干个正整数，范围从1到100，其中98个整数都出现了偶数次，只有两个整数出现了奇数次
 *               （比如1,1,2,2,3,4,5,5），如何找到这个出现奇数次的整数？
 * @Author:zouziwen
 * @Since:2016年10月18日
 * @Version:1.1.0
 */
public class NumberChecker {

	/**
	 * @return 异或操作获得从末位出现1的位数
	 * @Description:
	 */
	public int xorAfter(int arrs[]) {
		int h = arrs[0];
		// 异或出相同的数后得到两个整数的异或，至少有一位为1
		for (int i = 1; i < arrs.length; i++) {
			h ^= arrs[i];
		}
		// 得到1所在的位数
		int k = 1;
		while ((h & k) != k) {
			k <<= 1;
		}
		return k;
	}

	public int[] devide(int arrs[], int k) {
		int first = -1, second = -1;
		// 根据1所在的位数进行异或
		for (int i = 0; i < arrs.length; i++) {
			if ((arrs[i] & k) == k) {
				first = xor(first, arrs[i]);
			} else {
				second = xor(second, arrs[i]);
			}
		}
		return new int[] { first, second };
	}

	private int xor(int first, int h) {
		if (first == -1) {
			first = h;
		} else {
			first ^= h;
		}
		return first;
	}

	public int[] numberGet(int arrs[]) {
		int k = xorAfter(arrs);
		return devide(arrs, k);
	}

	public static void main(String[] args) {
		NumberChecker number = new NumberChecker();
		int arrs[] = new int[] { 1, 1, 2, 2, 3, 3, 4, 5, 5, 7 };
		System.out.println(Arrays.toString(number.numberGet(arrs)));
	}
}
