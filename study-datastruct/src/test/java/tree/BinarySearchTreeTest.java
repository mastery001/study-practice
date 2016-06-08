package tree;

import static org.junit.Assert.fail;

import org.junit.Test;

import tree.search.BinarySearchTree;
import util.Util;

public class BinarySearchTreeTest {

	@Test
	public void testInsertTreeNodeOfTTreeNodeOfT() {
		
	}

	@Test
	public void testBinarySearchTree() {
//		Integer[] firstTest = new Integer[]{2,5,1,6,4,3};
		Integer[] secondTest = new Integer[]{6,7,2,1,4,3};
		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>(secondTest);
		System.out.println(tree.deep());
		System.out.println(tree.size());
		Util.printTree(tree);	
		tree.delete(2);
		Util.printTree(tree);	
	}

	@Test
	public void testBinarySearchTreeTArray() {
		fail("Not yet implemented");
	}

}
