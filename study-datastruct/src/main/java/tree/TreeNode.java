package tree;

/**
 * @Description 二叉树的结点
 * @author mastery
 * @Date 2015年7月16日下午8:56:45
 */
public class TreeNode<T> {

	private T value;
	
	private TreeNode<T> left;
	
	private TreeNode<T> right;
	
	private TreeNode<T> parent;
	
	public TreeNode(T data) {
		this(data , null);
	}

	public TreeNode(T data, TreeNode<T> left) {
		this(data , left , null);
	}

	public TreeNode(T data, TreeNode<T> left, TreeNode<T> right) {
		this.value = data;
		setLeft(left);
		setRight(right);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public TreeNode<T> getLeft() {
		return left;
	}

	public void setLeft(TreeNode<T> left) {
		this.left = left;
		if(left != null) {
			left.parent = this;
		}
	}

	public TreeNode<T> getRight() {
		return right;
	}

	public void setRight(TreeNode<T> right) {
		this.right = right;
		if(right != null) {
			right.parent = this;
		}
	}
	
	public TreeNode<T> parent() {
		return parent;
	}
	
	public boolean equalsValue(T value) {
		if(this.value != null) {
			return this.value.equals(value);
		}
		return false;
	}

	@Override
	public String toString() {
		return "value=" + value;
	}

}
