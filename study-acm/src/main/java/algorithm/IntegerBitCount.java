package algorithm;  
  
public class IntegerBitCount {
	
	public static void main(String[] args) {
		int i = -3;
		System.out.println(bitCount(i));
		System.out.println(Integer.bitCount(i));
	}
	
	public static int bitCount(int a) {
		if(a == 0) {
			return 0;
		}
		int count = 1;
		
		// 2的乘方 a & a-1 = 0
		while((a &= (a-1)) != 0) {
			count++;
		}
		return count;
	}
}
