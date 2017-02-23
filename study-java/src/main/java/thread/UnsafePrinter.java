package thread;

import sun.misc.Unsafe;

public class UnsafePrinter {
	
	private static final Unsafe unsafe;
	
	static {
		unsafe = UnsafeUtils.get();
	}
	
	public static void main(String[] args) {
		int a = 10;
		int b = 20;
		method(a , b);
		System.out.println("a=" + a);
		System.out.println("b=" + b);
	}

	private static void method(int a, int b) {
		try {
			long valueOffset = unsafe.objectFieldOffset(Integer.class.getDeclaredField("value"));
			unsafe.putOrderedInt(a, valueOffset, 100);
			System.out.println(unsafe.getInt(a, valueOffset));
			unsafe.putOrderedInt(b, valueOffset, 200);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
