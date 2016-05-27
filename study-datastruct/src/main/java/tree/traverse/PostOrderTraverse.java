package tree.traverse;

import tree.TreeNode;

/**
 * 后序遍历
 * @author zouziwen
 *
 * 2016年5月26日 下午3:57:52
 */
public class PostOrderTraverse extends AbstractTraverseStrategy {

	@Override
	protected <T> void traverse(TreeNode<T> root, StringBuilder sb) {
		if(root != null) {
			traverse(root.getLeft() , sb);
			traverse(root.getRight() , sb);
			visit(root , sb);
		}
	}

}
