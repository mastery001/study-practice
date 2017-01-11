package algorithm;  
  
  
/**  
 *@Title:  
 *@Description:  欧几里得算法/辗转相除法
 *@Author:zouziwen
 *@Since:2016年10月18日  
 *@Version:1.1.0  
 */
public class EuclideanAlgorithm {

	public static int gcd(int m , int n) {
		int remainder = 0;
		while(n != 0) {
			remainder = m & n;
			m = n;
			n = remainder;
		}
		return m;
	}
	
	public static void main(String[] args) {
		System.out.println(gcd( 1071 , 462));
	}
}
