package leetcode;

import java.util.Stack;

public class ValidParentheses {

	public boolean isValid(String s) {
		if(s == null || "".equals(s.trim())) {
			return false;
		}
		Stack<Character> stack = new Stack<Character>();
		char[] chars = s.toCharArray();
		matches(stack, chars);
		return stack.isEmpty();
	}

	private void matches(Stack<Character> stack, char[] chars) {
		stack.push(chars[0]);
		char top = stack.peek();
		for (int i = 1; i < chars.length; i++) {
			char current = chars[i];
			switch (top) {
			case '(':
				current = current == ')' ? stack.pop() : stack.push(current);
				break;
			case '[':
				current = current == ']' ? stack.pop() : stack.push(current);
				break;
			case '{':
				current = current == '}' ? stack.pop() : stack.push(current);
				break;
			default:
				break;
			}
			if(stack.isEmpty()) {
				if(i == chars.length -1) {
					break;
				}
				stack.push(chars[++i]);
			}
			top = stack.peek();
		}
	}
	
	public static void main(String[] args) {
		String s = "()[]{}";
		System.out.println(new ValidParentheses().isValid(s));
	}
}
