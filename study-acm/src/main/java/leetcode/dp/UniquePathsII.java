package leetcode.dp;

/**
 * 63. Unique Paths II
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 *
 * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 *
 * Now consider if some obstacles are added to the grids. How many unique paths would there be?
 *
 * <a>https://leetcode.com/problems/unique-paths-ii/</a>
 *
 * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
 * @Author zouziwen
 * @CreateTime 2020-09-26 12:02
 */
public class UniquePathsII {

    /**
     * 第一步：定义数组元素的含义
     * dp[i][j]:从起点到网格i,j的可行路径
     *
     * 第二步：找出数组元素间的关系式
     * 如果arr[i][j]是障碍物，则dp[i][j] = 0
     * 否则dp[i][j] = dp[i][j - 1] + dp[i - 1][j]
     *
     * 第三步：找出初始条件
     * 一直往右走：dp[i][0] = 1,如果碰到障碍物，代表后续的网格都不能走了，则后续的都为0
     * 一直往下走：dp[0][j] = 1,如果碰到障碍物，代表后续的网格都不能走了，则后续的都为0
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        // 初始化
        int currentValue = 1;
        for(int i = 0 ; i < m ; i++ ) {
            if(obstacleGrid[i][0] == 1) {
                currentValue = 0;
            }
            dp[i][0] = currentValue;
        }
        currentValue = 1;
        for(int i = 0 ; i < n ; i++ ) {
            if(obstacleGrid[0][i] == 1) {
                currentValue = 0;
            }
            dp[0][i] = currentValue;
        }

        for(int i = 1 ; i < m ; i++) {
            for(int j = 1 ; j < n ; j++) {
                if(obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        UniquePathsII uniquePathsII = new UniquePathsII();
        int[][] obstacleGrid = new int[][]{
                {0 , 1 , 0 },
                {0 , 1 , 0 },
                {0 , 1 , 0 },
        };
        // expect 0
        System.out.println(uniquePathsII.uniquePathsWithObstacles(obstacleGrid));
        int[][] obstacleGrid1 = new int[][]{
                {0 , 0 , 0 },
                {0 , 1 , 0 },
                {0 , 1 , 0 },
        };
        // expect 1
        System.out.println(uniquePathsII.uniquePathsWithObstacles(obstacleGrid1));

        int[][] obstacleGrid2 = new int[][]{
                {0 , 0 , 0 },
                {0 , 1 , 0 },
                {0 , 0 , 0 },
        };
        // expect 2
        System.out.println(uniquePathsII.uniquePathsWithObstacles(obstacleGrid2));

        int[][] obstacleGrid3 = new int[][]{
                {0 , 0 , 0 },
                {0 , 0 , 0 },
                {0 , 0 , 0 },
        };
        // expect 6
        System.out.println(uniquePathsII.uniquePathsWithObstacles(obstacleGrid3));
    }
}
