package leetcode;

import java.util.Arrays;

  
/** 
 * <pre>
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 You may assume that each input would have exactly one solution.
 Example:
 
   Given nums = [2, 7, 11, 15], target = 9,
   Because nums[0] + nums[1] = 2 + 7 = 9,
   return [0, 1].
 * </pre> 
 *@Title:  
 *@Description:  
 *@Author:zouziwen
 *@Since:2016年9月11日  
 *@Version:1.1.0  
 */
public class TwoSum {

	public int[] twoSum(int[] nums, int target) {
		int j = 0;
		for (int i = 0; i < nums.length; i++) {
			if ((j = find(nums, i, target - nums[i])) != -1) {
				return new int[] { i, j };
			}
		}
		return null;
	}

	public int find(int[] nums, int i, int target) {
		for (int j = 0; j < i; j++) {
			if (target == nums[j]) {
				return j;
			}
		}
		if(i != nums.length - 1) {
			for (int j = i + 1; j < nums.length; j++) {
				if (target == nums[j]) {
					return j;
				}
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		int nums[] = new int[]{-3 , 3 , 0};
		System.out.println(Arrays.toString(new TwoSum().twoSum(nums, 0)));
	}
}
