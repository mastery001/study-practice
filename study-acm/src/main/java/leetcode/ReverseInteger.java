package leetcode;  
  
  
/**  
 *@Title:  
 *@Description:<pre>
Reverse digits of an integer.

Example1: x = 123, return 321
Example2: x = -123, return -321
</pre>
 *@Author:zouziwen
 *@Since:2016年9月18日  
 *@Version:1.1.0  
 */
public class ReverseInteger {

	public int reverse(int x) {
       long reverse = 0;
       long digit = 1;
       boolean negative = false;
       if(x < 0) {
    	   x = -x;
    	   negative = true;
       }
       int cur = x;
       while(cur >= 10) { 
    	   cur = cur / 10;
    	   digit *= 10;
       }
       while(x > 0) {
    	   long result = x % 10 * digit;
    	   if(result > Integer.MAX_VALUE) {
    		   reverse = 0;
    		   break;
    	   }
    	   reverse += result;
    	   digit /= 10;
    	   x /= 10;
       }
       if(reverse > Integer.MAX_VALUE) {
		   reverse = 0;
	   }
       return (int) (negative ? -reverse : reverse);
    }
	
	public int reverse1(int x ) {
		int reserve = 0;
		while(x != 0) {
			int tmp = reserve * 10 + x % 10;
			x = x / 10;
			if(tmp / 10 != reserve) {
				reserve = 0;
				break;
			}
			reserve = tmp;
		}
		return reserve;
	}
	
	public static void main(String[] args) {
		System.out.println(new ReverseInteger().reverse1(100));
	}
}
