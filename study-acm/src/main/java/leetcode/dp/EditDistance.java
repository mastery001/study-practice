package leetcode.dp;

/**
 * 72. Edit Distance
 * Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.
 * <p>
 * You have the following 3 operations permitted on a word:
 * <p>
 * 1. Insert a character
 * 2. Delete a character
 * 3. Replace a character
 *
 * <a>https://leetcode.com/problems/edit-distance/</a>
 *
 * @Author zouziwen
 * @CreateTime 2020-09-28 19:15
 */
public class EditDistance {

    /**
     * <pre>
     * 第一步：定义数组元素的含义
     * dp[i][j]:长度为i的word1到长度为j的word2的最少操作次数
     *
     * 第二步：找出数组元素间的关系式
     * 如果word1[i] = word2[j] , 则 dp[i][j] = dp[i-1][j-1]
     * 如果word1[i] != word2[j] ，则有三种操作：
     * 1、如果需要修改下一个字符，则dp[i][j] = dp[i - 1][j - 1] + 1
     * 2、如果需要给word1增加一个字符，则dp[i][j] = dp[i][j - 1] + 1
     * 2、如果需要给word1删除一个字符，则dp[i][j] = dp[i - 1][j] + 1
     *
     * 所以此时最少操作数的关系式为 dp[i][j] = min(dp[i - 1][j - 1] , dp[i][j - 1] , dp[i - 1][j]) + 1
     *
     * 第三步：找出初始条件
     * 当word1长度为0时，则需要添加长度为j的字符，即次数为i
     * 当word2长度为0时，则需要删除长度为i的字符，即次数为j
     *
     * </pre>
     *
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        char[] wordChar1 = word1.toCharArray();
        char[] wordChar2 = word2.toCharArray();
        int[][] dp = new int[m + 1][n + 1];
        dp[0][0] = 0;
        // 初始化
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i <= n; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (wordChar1[i - 1] == wordChar2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j])) + 1;
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        EditDistance editDistance = new EditDistance();
        System.out.println(editDistance.minDistance("horse", "ros"));
        System.out.println(editDistance.minDistance("intention", "execution"));
    }
}
