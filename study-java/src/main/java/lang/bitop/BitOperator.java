package lang.bitop;

public class BitOperator {
	
	public static void main(String[] args) {
		System.out.println(Integer.toBinaryString(-110));
		System.out.println(Integer.toBinaryString(109 & (1 << 3 -1)));
		System.out.println(Integer.toBinaryString(109 >> (6-1) & 1));
		System.out.println(Integer.toBinaryString(-110 << 3 | 109 >> 32 -3));
		System.out.println(Integer.toBinaryString(-110 << 3));
	}
	
	public static int average(int x, int y)   //返回X、Y的平均值  
	{     
	     return (x & y) + ( (x^y)>>1 );  
	}  
}
