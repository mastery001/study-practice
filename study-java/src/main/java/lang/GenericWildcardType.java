package lang;

import java.util.ArrayList;
import java.util.List;

public class GenericWildcardType {
	
	public static void main(String[] args) {
		// 不允许的，<? extends B>意味着最大父类为B
		//List<? extends B> list = new ArrayList<A>();
		List<? super B> list1 = new ArrayList<A>();
	}
	
	private static interface A {}
	
	private static class B implements A{}
}
