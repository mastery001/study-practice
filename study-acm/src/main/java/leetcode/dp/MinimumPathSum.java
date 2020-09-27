package leetcode.dp;

/**
 * 64. Minimum Path Sum
 * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path.
 *
 * Note: You can only move either down or right at any point in time.
 *
 * <a>https://leetcode.com/problems/minimum-path-sum/</a>
 * @Author zouziwen
 * @CreateTime 2020-09-26 12:31
 */
public class MinimumPathSum {

    /**
     * 第一步：定义数组元素的含义
     * dp[i][j]:从起点到网格i,j的最短路径和
     *
     * 第二步：找出数组元素间的关系式
     * 从上一个可能路径挑一个最短的 + 当前网格的路径值 = 当前节点最短路径和
     * dp[i][j] = min(dp[i][j - 1] , dp[i - 1][j]) + arrs[i][j]
     *
     * 第三步：找出初始条件
     * 一直往右走：dp[i][0] = dp[i - 1][0] + arrs[i][0]
     * 一直往下走：dp[0][j] = dp[0][j - 1] + arrs[0][j]
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        // 初始化
        dp[0][0] = grid[0][0];
        for(int i = 1 ; i < m ; i++) dp[i][0] = dp[i - 1][0] + grid[i][0];
        for(int i = 1 ; i < n ; i++) dp[0][i] = dp[0][i - 1] + grid[0][i];

        for(int i = 1 ; i < m ; i++) {
            for(int j = 1 ; j < n ; j++) {
                dp[i][j] = Math.min(dp[i][j - 1] ,  dp[i - 1][j]) + grid[i][j];
            }
        }

        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        MinimumPathSum minimumPathSum = new MinimumPathSum();

        int[][] grid = new int[][]{
                {1 , 3 , 1 },
                {1 , 5 , 1 },
                {4 , 2 , 1 },
        };
        // expect 7
        System.out.println(minimumPathSum.minPathSum(grid));
    }
}
