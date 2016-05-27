package tree.traverse;

import tree.BinaryTree;
import tree.TreeNode;

public abstract class AbstractTraverseStrategy implements TraverseStrategy {

	protected <T> void visit(TreeNode<T> node , StringBuilder sb) {
		if (node != null) {
			sb.append(node.getValue() + ",");
		}
	}
	
	@Override
	public <T> String[] traverse(BinaryTree<T> tree) {
		TreeNode<T> root = tree.root();
		StringBuilder sb = new StringBuilder("");
		if(root == null) {
			return sb.toString().split(",");
		}
		traverse(root , sb);
		sb.deleteCharAt(sb.lastIndexOf(","));
		return sb.toString().split(",");
	}
	
	protected abstract <T> void traverse(TreeNode<T> root , StringBuilder sb);
}
