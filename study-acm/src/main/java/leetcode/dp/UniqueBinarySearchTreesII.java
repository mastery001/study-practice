package leetcode.dp;

import java.util.LinkedList;
import java.util.List;

/**
 * 95. Unique Binary Search Trees II
 * <p>
 * Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.
 *
 * <a>https://leetcode.com/problems/unique-binary-search-trees-ii/</a>
 *
 * @Author zouziwen
 * @CreateTime 2020-10-20 19:00
 */
public class UniqueBinarySearchTreesII {

    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 动态规划的解法待定！！！ TODO
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }
        return generateTrees(1 , n);
    }

    public List<TreeNode> generateTrees(int i , int j) {
        List<TreeNode> list = new LinkedList<TreeNode>();
        if(i > j ) {
            list.add(null);
            return list;
        }
        for(int k = i ; k <= j ; k++){
            // 构建左子树
            List<TreeNode> leftTrees = generateTrees(i , k - 1);
            // 构建右子树
            List<TreeNode> rightTrees = generateTrees(k + 1, j);
            // 构建完整的树
            for(TreeNode left : leftTrees) {
                for(TreeNode right : rightTrees) {
                    TreeNode root = new TreeNode(k);
                    root.left = left;
                    root.right = right;
                    list.add(root);
                }
            }
        }

        return list;
    }


    public static void main(String[] args) {
        UniqueBinarySearchTreesII uniqueBinarySearchTreesII = new UniqueBinarySearchTreesII();
        List<TreeNode> list = uniqueBinarySearchTreesII.generateTrees(3);
        System.out.println(list);
    }
}
