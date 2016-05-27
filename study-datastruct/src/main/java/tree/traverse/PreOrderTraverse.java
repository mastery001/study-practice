package tree.traverse;

import tree.TreeNode;

/**
 * 先序遍历
 * @author mastery
 *
 */
public class PreOrderTraverse extends AbstractTraverseStrategy{

	@Override
	protected <T> void traverse(TreeNode<T> root, StringBuilder sb) {
		if(root == null) {
			return ;
		}else {
			visit(root , sb);
			traverse(root.getLeft() , sb);
			traverse(root.getRight(), sb);
		}
	}

}
