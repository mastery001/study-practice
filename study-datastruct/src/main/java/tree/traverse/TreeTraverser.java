package tree.traverse;

import tree.BinaryTree;

public final class TreeTraverser {

	private static final TreeTraverser instance = new TreeTraverser();
	
	private final TraverseStrategy preOrder = new PreOrderTraverse();
	private final TraverseStrategy postOrder = new PostOrderTraverse();
	private final TraverseStrategy inOrder = new InOrderTraverse();

	private TreeTraverser(){}
	
	public static final TreeTraverser newInstance() {
		return instance;
	}
	
	/**
	 * 先序遍历
	 * @see PreOrderTraverse
	 * @param tree
	 * @return
	 * 2016年5月26日 下午3:58:02
	 */
	public <T> String[] preOrder(BinaryTree<T> tree) {
		return preOrder.traverse(tree);
	}

	/**
	 * 后序遍历
	 * @see PostOrderTraverse
	 * @param tree
	 * @return
	 * 2016年5月26日 下午3:58:26
	 */
	public <T> String[] postOrder(BinaryTree<T> tree) {
		return postOrder.traverse(tree);
	}

	/**
	 * 中序遍历
	 * @see PreOrderTraverse
	 * @param tree
	 * @return
	 * 2016年5月26日 下午3:58:28
	 */
	public <T> String[] inOrder(BinaryTree<T> tree) {
		return inOrder.traverse(tree);
	}
}
