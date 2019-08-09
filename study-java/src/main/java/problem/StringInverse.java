package problem;

import java.util.Stack;

public class StringInverse {
	
	private static Stack<Character> stack = new Stack<Character>();
	
	public static String inverse(String str) {
		char[] strArray = str.toCharArray();
		int index =0;
		for(char s : strArray) {
			if(s == ',') {
				while(!stack.empty()) {
					strArray[index++] = stack.pop();
				}
				strArray[index++] = s;
			}else {
				stack.push(s);
			}
		}
		while(!stack.empty()) {
			strArray[index++] = stack.pop();
		}
		for(int i = 0 ; i < index ; i ++) {
			stack.push(strArray[i]);
		}
		index = 0;
		while(!stack.empty()) {
			strArray[index++] = stack.pop();
		}
		return new String(strArray);
	}
	
	public static void main(String[] args) {
		String str = inverse("abc,efg,hij");
		System.out.println(str);
	}
}
