package leetcode.dp;

import java.util.Arrays;

/**
 * 91. Decode Ways
 * A message containing letters from A-Z is being encoded to numbers using the following mapping:
 *
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * Given a non-empty string containing only digits, determine the total number of ways to decode it.
 *
 * The answer is guaranteed to fit in a 32-bit integer.
 *
 * <a>https://leetcode.com/problems/decode-ways/</a>
 * @Author zouziwen
 * @CreateTime 2020-09-29 17:35
 */
public class DecodeWays {

    /*
    第一步：定义数组元素的含义
    dp[i]:长度为i的字符串s的解码种类

    第二步：找出数组元素间的关系式
    如果s[i] = 0,则s[i]不能单独解码，反之dp[i] += dp[i - 1]
    如果前后两个字符都能解码，即 10 <= s[i:j] <= 26 ，则dp[i] += dp[i - 2]

    第三步：找出初始条件
    长度为1的字符串只有一种解码种类
     */

    /**
     *
     * @param s
     * @return
     */
    public int numDecodings(String s) {
        int n = s.length();
        if(n == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        // 0开头不能解码
        if(chars[0] == '0') {
            return 0;
        }
        int[] dp = new int[n];
        dp[0] = 1;
        for(int i = 1 ; i < n ; i++) {
            if(chars[i] != '0') {
                dp[i] = dp[i - 1];
            }
            int num = (chars[i - 1] - '0') * 10 + chars[i] - '0';
            if(num >= 10 && num <= 26 ) {
                // 如果是前两位数字且可以直接被解析，则还可以增加一种两个数字的解码方式
                if(i == 1) {
                    dp[i] ++;
                }else {
                    dp[i] += dp[i - 2];
                }
            }
        }
        return dp[n - 1];
    }


    public static void main(String[] args) {
        DecodeWays decodeWays = new DecodeWays();
        System.out.println(decodeWays.numDecodings("12"));
//        System.out.println("12".compareTo("26"));
    }
}
