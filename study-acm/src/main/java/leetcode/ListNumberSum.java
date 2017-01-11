package leetcode;

/**
 * @Title:
 * @Description: 链表加法运算
 * @Author:zouziwen
 * @Since:2016年9月17日
 * @Version:1.1.0
 */
public class ListNumberSum {

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		if(l1 == null && l2 == null) {
			return null;
		}
		ListNode result = new ListNode(0);
		ListNode p = result;
		// 进位
		int carry = 0;
		int sum = 0;
		while(l1 != null && l2 != null) {
			sum = l1.val + l2.val + carry;
			p.next = new ListNode(sum % 10);
			carry = sum / 10;
			l1 = l1.next;
			l2 = l2.next;
			p = p.next;
		}
		while(l1 != null) {
			sum = l1.val + carry;
			p.next = new ListNode(sum % 10);
			carry = sum / 10;
			l1 = l1.next;
			p = p.next;
		}
		while(l2 != null) {
			sum = l2.val + carry;
			p.next = new ListNode(sum % 10);
			carry = sum / 10;
			l2 = l2.next;
			p = p.next;
		}
		return result.next;
	}
	
	public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
		if(l1 == null && l2 == null) {
			return null;
		}
		ListNode result = new ListNode(0);
		ListNode p = result;
		// 进位
		int carry = 0;
		int sum = 0;
		while(l1 != null || l2 != null || carry != 0) {
			int l1Val = l1 != null ? l1.val : 0;
			int l2Val = l2 != null ? l2.val : 0;
			sum = l1Val + l2Val + carry;
			p.next = new ListNode(sum % 10);
			carry = sum / 10;
			l1 = l1 == null ? l1 : l1.next;
			l2 = l2 == null ? l2 : l2.next;
			p = p.next;
		}
		return result.next;
	}
	
	public static void main(String[] args) {
		ListNode l1 = new ListNode(2);
		l1.next = new ListNode(4);
		l1.next.next = new ListNode(3);
		ListNode l2 = new ListNode(5);
		l2.next = new ListNode(6);
		l2.next.next = new ListNode(4);
		ListNode result = new ListNumberSum().addTwoNumbers1(l1, l2);
		System.out.println(result);
	}
}

class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		ListNode p = this;
		do {
			sb.append(p.val).append("->");
			p = p.next;
		}while(p != null);
		return sb.substring(0 , sb.length() - 2);
	}
}