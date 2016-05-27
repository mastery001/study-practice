package tree.impl;

import static org.junit.Assert.fail;

import org.junit.Test;

import tree.TreeNode;
import util.Util;

public class LinkedBinaryTreeTest {

	@Test
	public void testDisplay() { 	
	}

	@Test
	public void testLinkedBinaryTree() {
		TreeNode<Integer> root = new TreeNode<Integer>(2);
		LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<Integer>(root);
		TreeNode<Integer> leftNode = tree.insertLeftNode(root , 5);
		tree.insertRightNode(root , 3);
		tree.insertLeftNode(leftNode, 1);
		tree.insertRightNode(leftNode, 4);
		System.out.println("root node is : " + tree.root().getValue());
		Util.printTree(tree);	
		System.out.println(tree.find(4));
		System.out.println("删除后");
		tree.deleteLeftNode(root);
		Util.printTree(tree);	
	}

	@Test
	public void testInsertLeftNodeT() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertRightNodeT() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertLeftNodeTreeNodeOfTT() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertRightNodeTreeNodeOfTT() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteLeftNode() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteRightNode() {
		fail("Not yet implemented");
	}

}
