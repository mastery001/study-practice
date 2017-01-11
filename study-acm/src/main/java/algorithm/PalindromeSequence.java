package algorithm;

/**
 * @Description: 例如 1 1 1 3 ，使用一种移位策略（取得其中两个数字相加后回归原位，多次操作后使之成为回文） first : 2 1 3
 *               ; second: 3 3 （回文）
 * @Author:zouziwen
 * @Since:2016年12月16日
 * @Version:1.1.0
 */
public class PalindromeSequence {

	public static void main(String[] args) {
		int[] values = new int[] {2  ,3 , 1 , 1 , 2  , 1};
		System.out.println(new PalindromeSequence().caclCount(values));
	}
	
	public int caclCount(int[] values) {
		int length = values.length;
		int left = 0;
		int right = length -1;
		int moveCount = 0;
		while(left < right) {
			if(values[left] < values[right] ) {
				//System.out.print("left " + left);
				values[left + 1] += values[left];
				moveCount ++;
				left ++;
			}else if(values[left] > values[right] ) {
				//System.out.print("right " + right);
				values[right - 1] += values[right];
				moveCount ++;
				right --;
			}else {
				left ++;
				right --;
			}
			//System.out.println(Arrays.toString(values));
		}
		return moveCount;
	}
	
}
