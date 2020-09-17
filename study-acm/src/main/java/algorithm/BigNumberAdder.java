package algorithm;

import java.util.Objects;

/*
 * @Author zouziwen
 * @CreateTime 2020-06-11 20:40
 */
public class BigNumberAdder {

    public static String sum(String num1 , String num2) {
        if(num1 == null || num1.length() == 0) {
            return num2;
        }
        if(num2 == null || num2.length() == 0) {
            return num1;
        }
        int l1 = num1.length() , l2 = num2.length();
        int maxL = l1 > l2 ? l1 : l2;
        int n = maxL + 1;
        int[] results = new int[n];
        // 初始化
        for(int i = 0 ; i < n ; i++) {
            results[i] = 0;
        }
        int index = n - 1;
        for(int i = l1 - 1 ; i >= 0 ; i --) {
            results[index--] += num1.charAt(i) - '0';
        }
        index = n - 1;
        for(int j = l2 - 1; j >= 0 ; j--) {
            results[index--] += num2.charAt(j) - '0';
        }
        // 处理进位
        for(int i = n - 1 ; i >= 0 ; i--) {
            if(results[i] > 10) {
                results[i] -= 10;
                results[i - 1] += 1;
            }
        }
        String sum = "";
        if(results[0] != 0) {
            sum += results[0];
        }
        for(int i = 1; i < n ; i++) {
            sum += results[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(sum("234567543234" , "87654321234565"));
    }
}
