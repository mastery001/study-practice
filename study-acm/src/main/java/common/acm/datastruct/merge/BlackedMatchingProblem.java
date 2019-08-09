package common.acm.datastruct.merge;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 括号配对问题
 *
 * @author mastery
 *
 */
public class BlackedMatchingProblem {
	/**
	*
	*/
	private final BlackedStack<Character> stack = new BlackedStack<Character>();

	public String[] blackedMatch(String[] matchStrs) {
		return null;
	}

	public boolean blackedMatch(String matchStrs) {
		if (matchStrs == null) {
			return false;
		}
		if (matchStrs.trim().length() < 1) {
			return false;
		}
		char[] matchChars = matchStrs.toCharArray();
		int index = 0;
		while (index < matchChars.length) {
			if (Blacked.isBlacked(matchChars[index])) {
				// 判断第一个字符是不是右括号，如果是则返回false结束
				if (Blacked.isRightBlacked(matchChars[0])) {
					return false;
				}
				break;
			}
			index++;
		}
		return matches(matchChars, index);
	}

	private boolean matches(char[] matchChars, int index) {
		// 如果最后一个数才是左括号，则返回false，
		if (index != matchChars.length - 1) {
			stack.push(matchChars[index]);

			for (int i = index + 1; i < matchChars.length; i++) {
				if (stack.getCurrentLeftMatchBlacked().equals(matchChars[i])) {
					// 将其弹出
					stack.pop();
					if (stack.empty()) {
						if (i < matchChars.length - 2) {
							index = i + 1;
							while (index < matchChars.length) {
								if (Blacked.isBlacked(matchChars[index])) {
									// 判断第一个字符是不是右括号，如果是则返回false结束
									if (Blacked.isRightBlacked(matchChars[0])) {
										return false;
									}
									break;
								}
								index++;
							}
							matches(matchChars, index);
						} else if (i == matchChars.length - 1) {
							return true;
						} else {
							return false;
						}
					}
				} else {
					// 如果是左符号则将其放入堆栈
					if (Blacked.isLeftBlacked(matchChars[i])) {
						stack.push(matchChars[i]);
					} else if (Blacked.isRightBlacked(matchChars[i])) {
						return false;
					}
				}
			}
		}
		return false;
	}
}

class Blacked {

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
	public static boolean isBlack(Character c, Character[] blackeds) {
		for (Character rightBlacked : blackeds) {
			if (c.equals(rightBlacked)) {
				return true;
			}
		}
		return false;
	}
}

class BlackedStack<E> extends Stack<Character> {

//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * 存储了括号对应的关系
	 */
	private static final Map<Character, Character> BLACKED_RELATION_MAP = new HashMap<Character, Character>() {
		/**
		*
		*/
		private static final long serialVersionUID = 1L;

		{
			put('(', ')');
			put('[', ']');
			put('{', '}');
		}
	};

	private Character currentitem;

	@Override
	public Character push(Character item) {
		currentitem = item;
		//logger.info("current push item is " + item + " , and the matcher is " + getCurrentLeftMatchBlacked());
		return super.push(currentitem);
	}

	/**
	 * 获得当前左侧括号的匹配值
	 * 
	 * @return
	 */
	public Character getCurrentLeftMatchBlacked() {
		return BLACKED_RELATION_MAP.get(currentitem);
	}

	@Override
	public synchronized Character pop() {
		Character c = super.pop();
		//StringBuffer loggerBuffer = new StringBuffer("current pop item is " + c);
		if (!empty()) {
			currentitem = super.peek();
			//loggerBuffer.append(", and the last item is not null , value is " + currentitem);
		}
		//logger.info(loggerBuffer.toString());
		return c;
	}

}
