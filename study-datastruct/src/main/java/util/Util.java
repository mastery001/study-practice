package util;

import tree.BinaryTree;
import tree.traverse.TreeTraverser;

public class Util {
	
	public static <T> void print_r(T[] arrs) {
		StringBuilder sb = new StringBuilder("[");
		for(T arr : arrs) {
			sb.append(arr + ",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("]");
		System.out.println(sb);
	}
	
	public static <T> void printTree(BinaryTree<T> tree) {
		Util.print_r(TreeTraverser.newInstance().preOrder(tree));
		Util.print_r(TreeTraverser.newInstance().inOrder(tree));
		Util.print_r(TreeTraverser.newInstance().postOrder(tree));
	}
}
