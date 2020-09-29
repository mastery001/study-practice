package leetcode.dp;

/**
 * 85. Maximal Rectangle
 *
 * Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.
 *
 * <a>https://leetcode.com/problems/maximal-rectangle/</a>
 * @Author zouziwen
 * @CreateTime 2020-09-29 15:51
 */
public class MaximalRectangle {

    /*
    错误的思路
    第一步：定义数组元素的含义
    dp[i][j]:网格i到j的组成的区域的矩形面积

    第二步：找出数组元素间的关系式
    如果arr[i][j] = 0 , dp[i][j] = 0
    如果arr[i][j] = 1 , dp[i][j - 1] > 0 && dp[i - 1][j] > 0 ，则dp[i][j] = dp[j - i][j] * dp[i][j - i]

    第三步：找出初始条件
    当为第一行或者第一列时，求得就是连续的1构成的矩形面积，当碰到arr[i][j]=0时，即不构成矩形，面积为0
     */

    /**
     * TODO
     *
     * @param matrix
     * @return
     */
    public int maximalRectangle(char[][] matrix) {
        if(matrix == null || matrix.length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];

        dp[0][0] = matrix[0][0] == '0' ? 0 : 1;
        int max = dp[0][0];
        // 初始化
//        int i = 0 , j = 0;
//        // 计算每一行的面积
//        while(i < m ) {
//            while(j < n) {
//                dp[j][0] = matrix[j][0] == '0' ? 0 : dp[j - 1][0] + 1;
//                max = Math.max(max , dp[j][0]);
//                j++;
//            }
//            i++;
//        }
//        // 计算每一列的面积
//        while(j < n ) {
//            while(i < m) {
//                dp[0][j] = matrix[0][j] == '0' ? 0 : dp[j - 1][0] + 1;
//                max = Math.max(max , dp[j][0]);
//                j++;
//            }
//            i++;
//        }
        int i = 1;
        while(i < m) {
            dp[i][0] = matrix[i][0] == '0' ? 0 : dp[i - 1][0] + 1;
            max = Math.max(max , dp[i][0]);
            i++;
        }
        i = 1;
        while(i < n) {
            dp[0][i] = matrix[0][i] == '0' ? 0 : dp[0][i - 1] + 1;
            max = Math.max(max , dp[0][i]);
            i++;
        }
        i = 0;
        for(; i < m ; i++) {
            for(int j = i ; j < n ; j++) {
                /**
                 * 需要满足以下条件才能计算面积：
                 * 1. 当前的值=1
                 * 2. 往左一个格子和往上一个格子的值都是1
                 */
                if(matrix[i][j] == '1' && (j == 0 || matrix[i][j - 1] == '1') && (i == 0 || matrix[i - 1][j] == '1')) {
                    int len = Math.abs(j - i);
                    dp[i][j] = dp[len][j] * dp[i][len];
//                    dp[i][j] = dp[i][j - 1] + j - i + 1;
//                    dp[i][j] = dp[i][j - 1] + dp[i][0];
                }else {
                    dp[i][j] = 0;
                }
                max = Math.max(max , dp[i][j]);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        MaximalRectangle maximalRectangle = new MaximalRectangle();
//        System.out.println(maximalRectangle.maximalRectangle(new char[][]{{'0'}}));
//        System.out.println(maximalRectangle.maximalRectangle(new char[][]{{'1'}}));
        System.out.println(maximalRectangle.maximalRectangle(new char[][]{
                {'1' , '0' , '1' , '0' ,'0'},
                {'1' , '0' , '1' , '1' ,'1'},
                {'1' , '1' , '1' , '1' ,'1'},
                {'1' , '0' , '0' , '1' ,'0'},
        }));
        System.out.println(maximalRectangle.maximalRectangle(new char[][]{
                {'1' , '1' , '0' },
                {'1' , '1' , '1' },
        }));
    }
}
