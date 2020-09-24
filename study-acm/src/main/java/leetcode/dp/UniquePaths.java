package leetcode.dp;

/**
 * 62. Unique Paths
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 *
 * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 *
 * How many possible unique paths are there?
 *
 * <a>https://leetcode.com/problems/unique-paths/</a>
 * @Author zouziwen
 * @CreateTime 2020-09-24 22:11
 */
public class UniquePaths {

    /**
     * 第一步：定义数组元素的含义
     * dp[i][j]:从起点到网格i,j的步数
     *
     * 第二步：找出数组元素间的关系式
     * dp[i][j] = dp[i][j - 1] + dp[i - 1][j]
     *
     * 第三步：找出初始条件
     * 一直往右走：dp[i][0] = 1
     * 一直往下走：dp[0][j] = 1
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for(int i = 0 ; i < m ; i++ ) dp[i][0] = 1;
        for(int i = 0 ; i < n ; i++ ) dp[0][i] = 1;

        for(int i = 1 ; i < m ; i++) {
            for(int j = 1 ; j < n ; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        UniquePaths uniquePaths = new UniquePaths();
        System.out.println(uniquePaths.uniquePaths(3 , 7));
        System.out.println(uniquePaths.uniquePaths(3 , 2));
        System.out.println(uniquePaths.uniquePaths(3 , 3));
    }
}
