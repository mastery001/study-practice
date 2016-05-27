package tree.traverse;

import tree.TreeNode;

/**
 * 中序遍历
 * @author zouziwen
 *
 * 2016年5月26日 下午3:57:32
 */
public class InOrderTraverse extends AbstractTraverseStrategy{

	@Override
	protected <T> void traverse(TreeNode<T> root, StringBuilder sb) {
		if(root != null) {
			traverse(root.getLeft() , sb);
			visit(root , sb);
			traverse(root.getRight() , sb);
		}
	}

}
