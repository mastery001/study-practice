package tree.search;

import tree.AbstractBinaryTree;
import tree.TreeNode;

public class BinarySearchTree<T extends Comparable<T>> extends AbstractBinaryTree<T> {

	/**
	 * 二叉查找树的结点
	 * @author zouziwen
	 *
	 * @param <T>
	 * 2016年5月27日 下午2:04:47
	 */
	protected static class SearchNode<T> extends TreeNode<T> {

		int freq;	// 频率
		
		public SearchNode(T data) {
			super(data);
			this.freq = 1;
		}
		
	}
	
	public BinarySearchTree() {
	}

	public BinarySearchTree(T[] initArray) {
		if (initArray != null) {
			for (T value : initArray) {
				insert(value);
			}
		}
	}

	@Override
	protected TreeNode<T> createNode(T value) {
		return new SearchNode<T>(value);
	}

	@Override
	protected boolean insert(TreeNode<T> curr, TreeNode<T> insertNode) {
		return insert((SearchNode<T>)curr, (SearchNode<T>)insertNode, null, false);
	}

	private boolean insert(SearchNode<T> curr, SearchNode<T> insertNode, SearchNode<T> parent, boolean currIsLeft) {
		if (curr == null) {
			curr = insertNode;
			if (currIsLeft) {
				parent.setLeft(curr);
			} else {
				parent.setRight(curr);
			}
		} else {
			int result = curr.getValue().compareTo(insertNode.getValue());
			// 如果当前值大于插入的值
			if (result > 0) {
				return insert((SearchNode<T>)curr.getLeft(), insertNode, curr, true);
			} else if (result < 0) {
				return insert((SearchNode<T>)curr.getRight(), insertNode, curr, false);
			}else {
				curr.freq++;
			}
		}
		return true;
	}

	@Override
	public SearchNode<T> root() {
		return (SearchNode<T>) super.root();
	}

	@Override
	public SearchNode<T> find(T value) {
		return (SearchNode<T>) super.find(value);
	}

	@Override
	protected TreeNode<T> find0(TreeNode<T> node, T value) {
		if (node == null) {
			return null;
		}
		int result = node.getValue().compareTo(value);
		if (result > 0) {
			return find0(node.getLeft(), value);
		} else if (result < 0) {
			return find0(node.getRight(), value);
		}
		return node;
	}

	@Override
	protected void deleteNode(TreeNode<T> deleteNodeParent, TreeNode<T> deleteNode) {
		if (deleteNodeParent == null) {
			// 左右子树都为空
			if (deleteNode.getLeft() == null && deleteNode.getRight() == null) {
				root = null;
			} else if (deleteNode.getLeft() == null || deleteNode.getRight() == null) {
				// 存在左子树或右子树
				if (deleteNode.getLeft() != null) {
					root = deleteNode.getLeft();
				} else {
					root = deleteNode.getRight();
				}
			} else {
				// 左右子树都不为空
				TreeNode<T> temp = deleteNode;
				TreeNode<T> rightLeft = deleteNode.getRight();
				while (rightLeft.getLeft() != null) {
					temp = rightLeft;
					rightLeft = rightLeft.getLeft();
				}
				if(temp == deleteNode) {
					deleteNode.setRight(rightLeft.getRight());
				}else {
					temp.setLeft(rightLeft.getRight());
				}
				deleteNode.setValue(rightLeft.getValue());	
			}
		} else {
			// 左右子树都为空
			if (deleteNode.getLeft() == null && deleteNode.getRight() == null) {
				// 根结点
				if (deleteNodeParent.getLeft() == deleteNode) {
					deleteNodeParent.setLeft(null);
				} else {
					deleteNodeParent.setRight(null);
				}
			} else if (deleteNode.getLeft() == null || deleteNode.getRight() == null) {
				// 存在左子树或右子树
				if (deleteNode.getLeft() != null) {
					if (deleteNodeParent.getLeft() == deleteNode) {
						// 如果待删除结点是左结点，且其存在左结点
						deleteNodeParent.setLeft(deleteNode.getLeft());
					} else {
						// 如果待删除结点是左结点，且其存在右结点
						deleteNodeParent.setRight(deleteNode.getLeft());
					}
				} else {
					if (deleteNodeParent.getRight() == deleteNode) {
						deleteNodeParent.setRight(deleteNode.getRight());
					} else {
						deleteNodeParent.setLeft(deleteNode.getRight());
					}
				}
			} else {
				// 左右子树都不为空
				TreeNode<T> temp = deleteNode;
				TreeNode<T> rightLeft = deleteNode.getRight();
				while (rightLeft.getLeft() != null) {
					temp = rightLeft;
					rightLeft = rightLeft.getLeft();
				}
				if(temp == deleteNode) {
					deleteNode.setRight(rightLeft.getRight());
				}else {
					temp.setLeft(rightLeft.getRight());
				}
				deleteNode.setValue(rightLeft.getValue());	
			}
		}

	}

}
