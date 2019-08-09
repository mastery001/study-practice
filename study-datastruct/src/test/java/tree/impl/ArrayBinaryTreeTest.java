package tree.impl;

import static org.junit.Assert.fail;

import org.junit.Test;

import tree.BinaryTree;
import util.Util;

public class ArrayBinaryTreeTest {

	@Test
	public void testArrayBinaryTree() {
		Integer[] arrs = new Integer[] { 2, 5, 3, 1, 4 , 6};
		BinaryTree<Integer> tree = new ArrayBinaryTree<Integer>(arrs);
		Util.printTree(tree);	
	}

	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisplay() {
		fail("Not yet implemented");
	}

}
