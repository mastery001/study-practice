package tree.impl;

import tree.AbstractBinaryTree;

public class ArrayBinaryTree<T> extends AbstractBinaryTree<T> {

	/**
	 * 使用数组构建二叉树
	 */
	public ArrayBinaryTree(T[] arrs) {
		for (T i : arrs) {
			insert(i);
		}
	}
}
