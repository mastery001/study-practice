package sort.insert;

import sort.AbstractSort;

/**
 * 直接插入排序
 * 		基本思想：顺序地将待排序的数据元素按其关键字值的大小插入到已排序数据元素子
 * 				集合的适当位置。子集合的数据元素个数从只有一个数据元素开始逐次增大
 *              ，当子集合大小最终与集合大小相同时排序完毕。
 *      时间复杂度分析：
 *      	(1)最好的情况是原始数据集合已全部排好序。这时算法的内层while循环的循环次数
 *      	   均为0。这样，外层for循环中每次数据元素的比较次数均为1,数据元素的赋值语句
 *      	   执行次数均为2.因此，整个排序过程中的比较次数为n-1,赋值语句执行次数为2(n-1)
 *      	   所以直接插入排序算法最好情况下的时间复杂度为O(n).
 *          (2)最坏情况是原始数据元素集合为反序排列。此时算法中内层while循环的循环次数每次
 *             均为i。这样，整个外层for循环中的比较次数和赋值语句执行次数(即移动次数)计算公式
 *             如下：
 *             		比较次数=(n-1)(n+2)/2
 *             		移动次数=(n-1)(n+4)/2
 *             因此，直接插入排序算法最坏情况下的时间复杂度为O(n2).
 *          (3)如果原始数据集合中元素大小的排列是随机的，则数据元素的期望比较次数和期望移动次数
 *             约为n2/4。因此，直接插入排序算法的期望时间复杂度为O(n2).
 * @author mastery
 *
 */
public class InsertionSort extends AbstractSort{

	@Override
	protected <T> T[] sortStrategy(T[] elements) throws IllegalArgumentException  {
		int j;
		for(int i = 0 ; i < elements.length -1 ; i ++) {
			T temp = elements[i+1];
			j = i ; 
			while(j > -1 && compare(elements[j], temp) > 0) {
				elements[j+1] = elements[j];
				j--;
			}
			elements[j+1] = temp;
		}
		return elements;
	}

}
