package leetcode.dp;

/**
 * 53. Maximum Subarray
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
 *
 * Follow up: If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
 *
 * <a>https://leetcode.com/problems/maximum-subarray/</a>
 * @Author zouziwen
 * @CreateTime 2020-09-23 22:04
 */
public class MaximumSubarray {

    /**
     * 第一步：定义数组元素的含义
     * dp[i]:到下标i时数组和的最大值
     *
     * 第二步：找出数组元素间的关系式
     * dp[i-1] > 0时，dp[i] = dp[i-1] + nums[i]
     * dp[i-1] < 0时，dp[i] = nums[i]
     *
     * 第三步：找出初始条件
     * dp[0] = nums[0]
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        // 题目约束了nums不可能为空，且一定有值，但出于程序安全考虑，还是加了这个判断
        if(nums == null || nums.length == 0) {
            return -1;
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        for(int i = 1; i < nums.length ; i++) {
            if(dp[i-1] > 0) {
                dp[i] = dp[i - 1] + nums[i];
            }else {
                dp[i] = nums[i];
            }
            max = Math.max(max , dp[i]);
        }
        return max;
    }

    public int maxSubArray0(int[] nums) {
        // 题目约束了nums不可能为空，且一定有值，但出于程序安全考虑，还是加了这个判断
        if(nums == null || nums.length == 0) {
            return -1;
        }
        int current = nums[0];
        int max = nums[0];
        for(int i = 1; i < nums.length ; i++) {
            current = Math.max(current + nums[i] , nums[i]);
            max = Math.max(max , current);
        }
        return max;
    }

    public static void main(String[] args) {
        MaximumSubarray maximumSubarray = new MaximumSubarray();
        System.out.println(maximumSubarray.maxSubArray0(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
    }

}
