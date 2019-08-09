package jvm;

public class ClassLoaderTest {
	public static int k = 0;
	public static ClassLoaderTest t1 = new ClassLoaderTest("t1");
	public static ClassLoaderTest t2 = new ClassLoaderTest("t2");
	public static int i = print("i");
	public static int n = 99;
	private int a = 0;
	public int j = print("j");
	{
		print("构造块");
	}
	
	static {
		print("静态块");
	}

	public ClassLoaderTest(String str) {
		System.out.println((++k) + ":" + str + "   i=" + i + "    n=" + n);
		++i;
		++n;
	}

	public static int print(String str) {
		System.out.println((++k) + ":" + str + "   i=" + i + "    n=" + n);
		++n;
		return ++i;
	}

	public static void main(String args[]) {
		ClassLoaderTest t = new ClassLoaderTest("init");
	}
}
