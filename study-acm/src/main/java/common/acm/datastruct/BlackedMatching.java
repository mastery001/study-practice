package common.acm.datastruct;

/**
 * 括号配对问题
 *
 * @author Administrator
 *
 */
public class BlackedMatching {
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
						}else if(i == matchChars.length - 1){
							return true;
						} else {
							return false;
						}
					}
				}else {
					// 如果是左符号则将其放入堆栈
					if(Blacked.isLeftBlacked(matchChars[i])) {
						stack.push(matchChars[i]);
					}else if(Blacked.isRightBlacked(matchChars[i])) {
						return false;
					}
				}
			}
		}
		return false;
	}
}
