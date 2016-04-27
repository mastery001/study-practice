package common.util;

/**
 * 运行时间计算器
 * @author zouziwen
 *
 * 2016年4月15日 下午4:35:22
 */
public class RuntimeCalculator {
	
	private static long begin;
	
	public static void begin() {
		begin = System.currentTimeMillis();
	}
	
	public static long calculate() {
		long end = System.currentTimeMillis();
		return end - begin;
	}
	
	
}
