package common;

import java.util.Arrays;

public class FunctionTest {
	
	public static void main(String[] args) {
		char[] a = "aabbcc".toCharArray();
		System.arraycopy(a, 2, a, 0, 4);
		System.out.println(Arrays.toString(a));
	}
}
