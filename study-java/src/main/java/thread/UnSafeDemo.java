package thread;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnSafeDemo {

	private static final Unsafe unsafe;

	private static final long valueOffset;
	
	private volatile int value;

	static {
		try {
			unsafe = getUnsafeInstance();
			valueOffset = unsafe.objectFieldOffset(UnSafeDemo.class.getDeclaredField("value"));
			System.out.println(valueOffset);
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public boolean compareAndSetValue(int expect, int update) {
		return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
	}

	public static Unsafe getUnsafeInstance() throws Exception {
		// 通过反射获取rt.jar下的Unsafe类
		Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafeInstance.setAccessible(true);
		// return (Unsafe) theUnsafeInstance.get(null);是等价的
		return (Unsafe) theUnsafeInstance.get(Unsafe.class);
	}

	public static void main(String[] args) {
		System.out.println(new UnSafeDemo().compareAndSetValue(0, 1));
	}
}
