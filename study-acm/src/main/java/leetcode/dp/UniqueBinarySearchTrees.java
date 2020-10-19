package leetcode.dp;

/**
 * 96. Unique Binary Search Trees
 *
 * Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
 *
 * <a>https://leetcode.com/problems/unique-binary-search-trees/</a>
 * @Author zouziwen
 * @CreateTime 2020-10-19 21:43
 */
public class UniqueBinarySearchTrees {

    /*
        第一步：定义数组元素的含义
        dp[i]:i个节点存在的BST个数

        第二步：找出数组元素间的关系式
        假定f(i)为当i为根节点时的BST个数，则dp[i]为：
        dp[i] = f(1) + f(2) + ... + f(i)

        而f(i)又等于其左子树的BST个数 * 右子树的BST个数，
        1. 左子树的BST个数 = dp[i - 1]，因为数字是单调递增的，所以左子树只能有i-1个节点
        2. 右子树的BST个数 = dp[n - 1]

        公式为：f(i) = dp[i - 1] * dp[n - i]

        最终得出关系式为：

        dp[i] = dp[0] * dp[i - 1] + dp[1] * dp[i - 2] + ... + dp[i - 1] * dp[0]

        第三步：找出初始条件
        dp[0] = 1，没有数字，空树--特殊的BST
        dp[1] = 1
     */

    /**
     * BST-二叉搜索树的性质为：左子树小于根，右子树大于根
     * @param n
     * @return
     */
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i = 2 ; i <= n ; i++) {
            for(int j = 1 ; j <= i ; j ++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        UniqueBinarySearchTrees uniqueBinarySearchTrees = new UniqueBinarySearchTrees();
        System.out.println(uniqueBinarySearchTrees.numTrees(3));
    }
}
