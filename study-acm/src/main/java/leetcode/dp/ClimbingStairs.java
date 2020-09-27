package leetcode.dp;

/**
 * 70. Climbing Stairs
 *
 * You are climbing a stair case. It takes n steps to reach to the top.
 *
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 *
 * <a>https://leetcode.com/problems/climbing-stairs/</a>
 * @Author zouziwen
 * @CreateTime 2020-09-27 20:40
 */
public class ClimbingStairs {

    /**
     * 第一步：定义数组元素的含义
     * dp[i]:走到位置i时的可能
     *
     * 第二步：找出数组元素间的关系式
     * dp[i] = dp[i - 1] + dp[i - 2]
     *
     * 第三步：找出初始条件
     * dp[0] = 0
     * dp[1] = 1 ，走1步
     * dp[2] = 2 , 走两次1步，或1次两步
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if(n == 0) {
            return 0;
        }
        if(n <= 2) {
            return n;
        }
        int len = n + 1;
        int[] dp = new int[len];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        for(int i = 3 ; i < len ; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public static void main(String[] args) {
        ClimbingStairs climbingStairs = new ClimbingStairs();
        System.out.println(climbingStairs.climbStairs(2));
        System.out.println(climbingStairs.climbStairs(3));
        System.out.println(climbingStairs.climbStairs(4));
    }
}
