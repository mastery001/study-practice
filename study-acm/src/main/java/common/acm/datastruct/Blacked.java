package common.acm.datastruct;

public class Blacked {

	/**
	 * 括号
	 */
	public static final Character[] blackeds = new Character[] { '(', ')', '[', ']', '{', '}' };
	
	public static final Character[] rightBlackeds = new Character[] { ')', ']', '}' };
	
	public static final Character[] leftBlackeds = new Character[] { '(', '[', '{' };

	/**
	 * 判断当前字符是否为括号
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isBlacked(Character c) {
		return isBlack(c, blackeds);
	}
	

	/**
	 * 判断是否为右括号
	 * 
	 * @param c
	 * @return 2015年11月23日 上午9:58:37
	 */
	public static boolean isRightBlacked(Character c) {
		return isBlack(c, rightBlackeds);
	}
	
	/**
	 * 判断是否为右括号
	 * 
	 * @param c
	 * @return 2015年11月23日 上午9:58:37
	 */
	public static boolean isLeftBlacked(Character c) {
		return isBlack(c, leftBlackeds);
	}
	
	/**
	 * 判断是否为括号
	 * 
	 * @param c
	 * @return 2015年11月23日 上午9:58:37
	 */
	public static boolean isBlack(Character c , Character[] blackeds) {
		for (Character rightBlacked : blackeds) {
			if (c.equals(rightBlacked)) {
				return true;
			}
		}
		return false;
	}
}
