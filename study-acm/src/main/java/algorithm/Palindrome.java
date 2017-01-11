package algorithm;

public class Palindrome {

	public static <T> boolean isPalindrome(T[] values) {
		int length = values.length;
		int left = 0;
		int right = length - 1;
		while (left < right) {
			if (values[left].equals(values[right])) {
				left++;
				right--;
			} else {
				return false;
			}
		}
		return true;
	}

	public static <T> boolean isPalindrome1(T[] values) {
		int length = values.length;
		int left = 0;
		int right = length - 1;
		while (left < right && values[left].equals(values[right])) {
			left++;
			right--;
		}
		System.out.println(left + "--" + right);
		int diff = left - right;
		return diff == 0 || diff == 1;
	}

	public static void main(String[] args) {
		// System.out.println(isPalindrome1(new String[] { "a", "b" , "c", "a"
		// }));
		Record r = getPalindrome1(new String[] { "c", "a", "b", "d", "c" , "b" });
		System.out.println("result :" + r);
	}

	public static <T> Record getPalindrome1(T[] values) {
		int length = values.length;
		Record r = null;
		for (int i = 0; i < length - 1; i++) {
			int left = i;
			int right = length - 1;
			int begin = left, end = right;
			boolean flag = false;
			while (left < right) {
				if (values[left].equals(values[right])) {
					left++;
					right--;
					flag = true;
				} else {
					// 从右往左搜寻 
					int currentRight = right; 
					while(left < currentRight) {
						if(values[left].equals(values[--currentRight])) {
							break;
						}
					}
					if (left < currentRight) {
						right = currentRight;
						flag = true;
					}else {
						right --;
						left = i;
						flag = false;
					}
					begin = left;
					end = currentRight;
				}
			}
			if (flag) {
				r = max(r, begin, end);
			}
		}
		return r;
	}
	
	/**
	 * 求最长回文子序列
	 * 
	 * @param values
	 * @return
	 * @Description:
	 */
	public static <T> Record getPalindrome(T[] values) {
		int length = values.length;
		Record r = null;
		r = findLeft(values, length, r);
		r = findRight(values, length, r);
		return r;
	}

	private static <T> Record findLeft(T[] values, int length, Record r) {
		for (int i = 0; i < length - 1; i++) {
			int left = i;
			int right = length - 1;
			int begin = left, end = right;
			boolean flag = false;
			while (left < right) {
				if (values[left].equals(values[right])) {
					left++;
					right--;
					flag = true;
				} else {
					int currentLeft = left;
					// 找到从左往右开始与最后一点节点值相等的下标
					while (currentLeft < right) {
						if (values[++currentLeft].equals(values[right])) {
							break;
						}
					}
					// 如果存在则将此left作为begin , 否则将left重置
					if (currentLeft < right) {
						left = currentLeft + 1;
					} else {
						left = i;
					}
					right--;
					begin = left;
					end = right;
					flag = false;
				}
			}
			if (flag) {
				r = max(r, begin, end);
			}
		}
		return r;
	}
	
	private static <T> Record findRight(T[] values, int length, Record r) {
		for (int i = length - 1; i > 0; i--) {
			int left = 0;
			int right = i;
			int begin = left, end = right;
			boolean flag = false;
			while (left < right) {
				if (values[left].equals(values[right])) {
					left++;
					right--;
					flag = true;
				} else {
					int currentRight = right; 
					while(left < currentRight) {
						if(values[left].equals(values[--currentRight])) {
							break;
						}
					}
					// 如果存在则将此left作为begin , 否则将left重置
					if (left < currentRight) {
						right = currentRight - 1;
						left ++;
					} else {
						right = i;
					}
					left++;
					begin = left;
					end = right;
					flag = false;
				}
			}
			if (flag) {
				r = max(r, begin, end);
			}
		}
		return r;
	}

	private static Record max(Record r, int begin, int end) {
		if (r == null) {
			r = new Record();
			r.begin = begin;
			r.end = end;
			r.size = end - begin + 1;
		} else {
			int size = end - begin + 1;
			if (r.size < size) {
				r.begin = begin;
				r.end = end;
				r.size = size;
			}
		}
		return r;
	}

	private static class Record {
		private int size;

		private int begin;

		private int end;

		@Override
		public String toString() {
			return "Record [size=" + size + ", begin=" + begin + ", end=" + end + "]";
		}

	}
}
