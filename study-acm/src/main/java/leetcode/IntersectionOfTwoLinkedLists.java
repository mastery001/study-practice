package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title:
 * @Description: 两个单链表的交集
 * @Author:zouziwen
 * @Since:2016年11月27日
 * @Version:1.1.0
 */
public class IntersectionOfTwoLinkedLists {

	public static class ListNode {
		int val;
		ListNode next;

		public ListNode(int x) {
			val = x;
			next = null;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			ListNode node = this;
			while(node != null ){
				sb.append(node.val).append("->");
				node = node.next;
			}
			return sb.toString();
		}
		
		
	}

	public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		if(headA == null || headB == null) {
			return null;
		}
		ListNode node = headA;
		while(node.next != null) node = node.next;
		// 找到A的最后一个节点并让其=B，使两个链表相连
		node.next = headB;
		ListNode result = find(headA);
		node.next = null;
		return result;
	}
		
	private static ListNode find(ListNode head) {
		ListNode slow = head , fast = head.next;
		//判断链表是否成环
		while(slow != fast) {
			if(fast == null || fast.next == null ) {
				return null;
			}
			slow = slow.next;
			fast = fast.next.next;
		}
		// 若成环后则根据上面成环的相交点和起始节点循环找到相交点 
		slow = head;
        fast = fast.next;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;
	}
	
	public static void main(String[] args) {
		int[] arrs = new int[]{1,2 ,3,5,7,9};
		for(int val : arrs) {
			cache.put(val, new ListNode(val));
		}
		int[] arrsA = new int[]{1,3,5,7,9};
		Builder builderA = new Builder();
		for(int val : arrsA) {
			builderA.add(val);
		}
		ListNode headA = builderA.build();
		//System.out.println(headA);
		int[] arrsB = new int[]{2,5,7,9};
		Builder builderB = new Builder();
		for(int val : arrsB) {
			builderB.add(val);
		}
		ListNode headB = builderB.build();
		//System.out.println(headB);
		System.out.println(getIntersectionNode(headA, headB));
	}
	
	private static class Builder {
		private ListNode root;
		
		private ListNode curr;
		
		public Builder add(int val) {
			if(root == null) {
				root = cache.get(val);
				curr = root;
			}else {
				curr.next = cache.get(val);
				curr = curr.next;
			}
			return this;
		}
		
		public ListNode build() {
			return root;
		}
	}
	
	private static final Map<Integer , ListNode> cache = new HashMap<Integer , ListNode>();
	
	static void print(ListNode head) {
		StringBuilder sb = new StringBuilder();
		ListNode node = head;
		while(node != null ){
			sb.append(node.val).append("->");
			node = node.next;
		}
		System.out.println(sb);
	}
}
