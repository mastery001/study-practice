package thread;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeUtils {
	
	private static Unsafe unsafe; 
	
	static {
		try {
			unsafe = getUnsafeInstance();
		} catch (Exception e) {
		}
	}
	
	private static Unsafe getUnsafeInstance() throws Exception {
		// 通过反射获取rt.jar下的Unsafe类
		Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafeInstance.setAccessible(true);
		// return (Unsafe) theUnsafeInstance.get(null);是等价的
		return (Unsafe) theUnsafeInstance.get(Unsafe.class);
	}
	
	public static Unsafe get() {
		return unsafe;
	}
}
