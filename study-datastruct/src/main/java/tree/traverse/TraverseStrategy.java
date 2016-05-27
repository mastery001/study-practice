package tree.traverse;

import tree.BinaryTree;

/**
 * 按某种次序访问当前二叉树的每个结点，且每个结点只访问一次，
 * 访问结点时要实现的具体操作 由TraverseStrategy接口的实现类完成。
 * 二叉树的遍历次序主要有先序遍历次序、中序遍历次序，后序遍历次序和层序遍历次序四种。
 * 
 * @author mastery
 *
 */
public interface TraverseStrategy {

	/**
	 * 按某种次序访问当前二叉树的每个结点，且每个结点只访问一次，
	 * @param tree
	 * @return
	 */
	<T> String[] traverse(BinaryTree<T> tree);

}
