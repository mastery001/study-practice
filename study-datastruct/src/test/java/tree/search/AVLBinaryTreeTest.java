package tree.search;

import static org.junit.Assert.fail;

import org.junit.Test;

import tree.BinaryTree;
import util.Util;

public class AVLBinaryTreeTest {

	@Test
	public void testInsertTreeNodeOfTTreeNodeOfT() {
		Integer[] initArray = new Integer[]{6,7,2,1,4,3};
		BinaryTree<Integer> tree = new AVLBinaryTree<Integer>(initArray );
		Util.printTree(tree);
		tree.delete(2);
		Util.printTree(tree);
	}

	@Test
	public void testDeleteNode() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateNodeT() {
		fail("Not yet implemented");
	}

}
