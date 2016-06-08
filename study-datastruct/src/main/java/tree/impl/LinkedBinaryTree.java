package tree.impl;

import tree.AbstractBinaryTree;
import tree.TreeNode;

/**
 * 链式实现二叉树
 * 
 * @Description
 * @author mastery
 * @Date 2015年7月16日下午8:54:27
 */
public class LinkedBinaryTree<T> extends AbstractBinaryTree<T>{

	/**
	 * 初始化根结点
	 * 
	 * @param root
	 */
	public LinkedBinaryTree(TreeNode<T> root) {
		this.root = root;
	}

	/**
	 * 添加指定结点的左结点
	 * 
	 * @param node
	 *            指定的结点
	 * @param data
	 *            需要添加的数据
	 * @return
	 */
	public TreeNode<T> insertLeftNode(TreeNode<T> curr, T data) {
		if (curr == null)
			throw new NullPointerException("curr is null");
		TreeNode<T> left = curr.getLeft();
		TreeNode<T> newNode = new TreeNode<T>(data);
		if (left == null) {
			curr.setLeft(newNode);
		} else {
			newNode.setLeft(left.getLeft());
			newNode.setRight(left.getRight());
			curr.setLeft(newNode);
		}
		size ++;
		return newNode;
	}

	/**
	 * 添加指定结点的右结点
	 * 
	 * @param node
	 *            指定的结点
	 * @param data
	 *            需要添加的数据
	 * @return
	 */
	public TreeNode<T> insertRightNode(TreeNode<T> curr, T data) {
		if (curr == null)
			throw new NullPointerException("curr is null");
		TreeNode<T> right = curr.getRight();
		TreeNode<T> newNode = new TreeNode<T>(data);
		if (right == null) {
			curr.setRight(newNode);
		} else {
			newNode.setLeft(right.getLeft());
			newNode.setRight(right.getRight());
			curr.setRight(newNode);
		}
		size ++;
		return newNode;
	}

	/**
	 * <h1>删除结点的思想</h1>
	 * 
	 * <pre>
	 * 总体思想：分多种情况讨论 
	 * 	1.被删除节点没有子树的情况，直接删除，并修改对应父节点的指针为空。
	 * 
	 * 	2.对于只有一个子树的情况，考虑将其子树作为其父节点的子树，关于是左还是右，
	 * 	根据被删除的节点确定。
	 * 
	 * 	3.最复杂的是有两个子数的情况，可以考虑两种方法.都是同样的思想：
	 * 	用被删除节点A的左子树的最右节点或者A的右子树的最左节点作为替代A的节点，
	 * 	并修改相应的最左或最右节点的父节点的指针修改方法类似2 ，不做细致讨论
	 * </pre>
	 */

	/**
	 * 删除指定结点的左结点
	 * 
	 * @param node
	 *            指定的结点
	 * @return
	 */
	public TreeNode<T> deleteLeftNode(TreeNode<T> curr) {
		if (curr == null) {
			throw new NullPointerException("curr is" + curr);
		}
		TreeNode<T> deleteNode = curr.getLeft();
		deleteNode(deleteNode);
		return deleteNode;
	}

	/**
	 * 删除指定结点的右结点
	 * 
	 * @param node
	 *            指定的结点
	 * @return
	 */
	public TreeNode<T> deleteRightNode(TreeNode<T> curr) {
		if (curr == null) {
			throw new NullPointerException("curr is" + curr);
		}
		TreeNode<T> deleteNode = curr.getRight();
		deleteNode(deleteNode);
		return deleteNode;
	}

}
