package sort;

public class ArrayUtil {

	public static <T> void print_r(T[] arrs) {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < arrs.length; i++) {
			sb.append(arrs[i] + " , ");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("]");
		System.out.println(sb);
	}
}
