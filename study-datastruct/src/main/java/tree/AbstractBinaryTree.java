package tree;

import java.util.Objects;

import tree.traverse.PreOrderTraverse;
import util.Util;

public abstract class AbstractBinaryTree<T> implements BinaryTree<T> {

	protected int size;

	protected TreeNode<T> root;

	@Override
	public int deep() {
		return caclDeep(root);
	}

	protected int caclDeep(TreeNode<T> root) {
		if (root == null) {
			return 0;
		}
		int leftDeep = caclDeep(root.getLeft());
		int rightDeep = caclDeep(root.getRight());
		return leftDeep > rightDeep ? leftDeep + 1 : rightDeep + 1;
	}

	@Override
	public boolean isNullTree() {
		return deep() == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public TreeNode<T> root() {
		return root;
	}

	/**
	 * 默认实现方法，从root处递归插入
	 * 
	 * @param value
	 */
	public BinaryTree<T> insert(T value) {
		insert(root, value);
		return this;
	}

	@Override
	public BinaryTree<T> insert(TreeNode<T> curr, T value) {
		if (root == null) {
			root = createNode(value);
		}else {
			Objects.requireNonNull(curr, "curr is null");
			insert(curr, createNode(value));
		}
		size++;
		return this;
	}

	/**
	 * 创建一个新的节点
	 * 
	 * @param value
	 * @return 2016年5月27日 下午2:01:09
	 */
	protected TreeNode<T> createNode(T value) {
		return new TreeNode<T>(value);
	}

	/**
	 * 在指定结点出插入值，若其左结点不存在，则插入在左结点， 若右结点不存在，则插入在右结点，否则递归至其各个子结点
	 * 
	 * @param node
	 * @param value
	 * @return
	 */
	protected boolean insert(TreeNode<T> curr, TreeNode<T> insertNode) {
		// 左结点为空，设置左结点
		if (curr.getLeft() == null) {
			curr.setLeft(insertNode);
		} else if (curr.getRight() == null) {
			// 右结点为空，设置右结点
			curr.setRight(insertNode);
		} else {
			// 左右结点都不为空 , 左右结点都无法插入
			if (!insert(curr.getLeft(), insertNode) && !insert(curr.getRight(), insertNode)) {
				return false;
			}
		}
		return true;
	}

	// protected TreeNode<T> insert(TreeNode<T> node , TreeNode<T> insertNode) {
	// if(node == null) {
	// size ++;
	// node = insertNode;
	// }else {
	// if(node.getLeft() == null) {
	// node.setLeft(insert(node.getLeft() , insertNode));
	// }else if(node.getRight() == null){
	// node.setRight(insert(node.getRight() , insertNode));
	// }else {
	// insert(node.getLeft() , insertNode);
	// }
	// }
	// return node;
	// }

	@Override
	public TreeNode<T> find(T value) {
		if (value == null) {
			return null;
		}
		if (root == null) {
			return null;
		}
		return find0(root, value);
	}

	protected TreeNode<T> find0(TreeNode<T> node, T value) {
		if (node == null)
			return null;
		if (node.equalsValue(value))
			return node;
		if (node.getLeft() != null) {
			TreeNode<T> temp = find0(node.getLeft(), value);
			if (temp != null)
				return temp;
		}
		if (node.getRight() != null) {
			TreeNode<T> temp = find0(node.getRight(), value);
			if (temp != null)
				return temp;
		}
		return null;
	}

	@Override
	public BinaryTree<T> delete(T value) {
		if (value != null) {
			TreeNode<T> node = find(value);
			if (node != null) {
				deleteNode(node.parent(), node);
			}
		}
		return this;
	}

	@Override
	public BinaryTree<T> delete(TreeNode<T> node) {
		return delete(node.getValue());
	}

	/**
	 * 删除指定父结点的子结点
	 * 
	 * @param deleteNodeParent
	 * @param deleteNode
	 */
	protected void deleteNode(TreeNode<T> deleteNodeParent, TreeNode<T> deleteNode) {
		if (deleteNode == null) {
			return;
		}
		// 说明此时是删除根节点
		if (deleteNodeParent == null) {
			if (deleteNode.getLeft() == null && deleteNode.getRight() == null) {
				root = null;
			} else if (deleteNode.getLeft() == null && deleteNode.getRight() != null) {
				// 如果左子树为空，则直接连接其右子树
				root = deleteNode.getRight();
			} else if (deleteNode.getLeft() != null && deleteNode.getRight() == null) {
				root = deleteNode.getLeft();
			} else {
				// 遍历寻找待删除的结点的左结点的最右结点
				TreeNode<T> left = deleteNode.getLeft();
				TreeNode<T> temp = left;
				while (left.getRight() != null) {
					left = left.getRight();
				}
				left.setRight(deleteNode.getRight());
				root = temp;
			}
		} else {
			// 如果右子树为空，则直接连接其左子树
			if (deleteNode.getRight() == null) {
				deleteNodeParent.setLeft(deleteNode.getLeft());
			} else {
				// 如果左子树为空，则直接连接其右子树
				if (deleteNode.getLeft() == null) {
					deleteNodeParent.setLeft(deleteNode.getRight());
				} else {
					// 遍历寻找待删除的结点的左结点的最右结点
					TreeNode<T> left = deleteNode.getLeft();
					while (left.getRight() != null) {
						left = left.getRight();
					}
					left.setRight(deleteNode.getRight());
					deleteNodeParent.setLeft(left);
				}
			}
		}
		size--;
	}

	@Override
	public void display() {
		Util.print_r(new PreOrderTraverse().traverse(this));
	}

}
