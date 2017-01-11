package algorithm;

/**
 * 在并行计算和图形图像等处理中，经常会遇到一类叫做"下个2的幂"的问题，简单说来就是给定一个数，需要找到满足如下条件的一个数： 1. 最靠近这个数 2.
 * 大于或等于这个数 3. 是2的N次方 简单函数描述就是 ` int nextPowerOfTwo(int num);` 首先想到的一般算法可能是： ```
 * int nextPowerOfTwo(int num)
 * 
 * @Description:
 * @Author:zouziwen
 * @Since:2017年1月3日
 * @Version:1.1.0
 */
public class TwoPower {

	public static void main(String[] args) {
		System.out.println(Integer.toBinaryString(2));
		System.out.println(Integer.toBinaryString((2 >> 1)));
		System.out.println(new Wonderful().nextPowerOfTwo(65537));
	}
	
	static class Base {

		int nextPowerOfTwo(int num) {
			int p = 1;
			while (p < num) {
				p <<= 1;
			}
			return p;
		}
	}

	static class Wonderful extends Base {

		@Override
		int nextPowerOfTwo(int num) {
			if (0 == num--) {
				return 1;
			}
			num = (num >> 1) | num;
			num = (num >> 2) | num;
			num = (num >> 4) | num;
			num = (num >> 8) | num;
			num = (num >> 16) | num;
			// num = (num >> 32) | num;//如果是64位机器则需要增加一次计算

			return ++num;
		}

	}

}
