package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**  
 * <pre>
 *Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
Note: The solution set must not contain duplicate triplets.
For example, given array S = [-1, 0, 1, 2, -1, -4],

	A solution set is:
	[
	  [-1, 0, 1],
	  [-1, -1, 2]
	]
 * </pre>
 *@Title:  
 *@Description:  
 *@Author:zouziwen
 *@Since:2016年9月11日  
 *@Version:1.1.0  
 */
public class ThreeSum {

	public List<List<Integer>> threeSum(int[] nums) {
		Arrays.sort(nums);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		for(int i = 0 ; i < nums.length ; i++) {
			if(i > 0 && nums[i] == nums[i-1])
				continue;
			twoSum(list , nums , i);
		}
		return list;
    }
	private void twoSum(List<List<Integer>> list, int[] nums, int curIndex) {
		int i = curIndex + 1 , j = nums.length - 1;
		while(i < j) {
			if(nums[curIndex] + nums[i] + nums[j] < 0) i++;
			else if(nums[curIndex] + nums[i] + nums[j] > 0) j--;
			else {
				List<Integer> result = new ArrayList<Integer>(3);
				result.add(nums[curIndex]);
				result.add(nums[i]);
				result.add(nums[j]);
				list.add(result);
				i++;j--;
				while(i < nums.length && nums[i] == nums[i -1 ]) i++;
				while(j >= 0 && nums[j] == nums[j+1]) j--;
			}
		} 
		
	}
	public static void main(String[] args) {
		System.out.println(new ThreeSum().threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
		
	}
}
