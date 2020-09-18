package string;

import java.util.Arrays;

/**
 * @Author zouziwen
 * @CreateTime 2020-09-11 13:39
 */
public class KMP {

    /**
     * 构建前缀表
     * 定义：以待查找字符串构建，从左往右依次计算最长相同前后缀的元素长度
     * （即假设匹配串为abcdabcr,则当寻找到第7个字符串时，最长相同前后缀为abc，在寻找时，如果下一个字符不一致，则直接跳转到下标为2的位置）
     * @param substr
     * @return
     */
    private static int[] toNext(String substr) {
        int len = substr.length();
        // 定义前缀数组
        int[] next = new int[len];
        for(int i = 0 ; i < len ; i++) {
            next[i] = -1;
        }
        char[] arrs = substr.toCharArray();
        int i = 0 , j = -1;
        while(i < len - 1) {
            if(j == -1 || arrs[i] == arrs[j]) {
                i ++;
                j ++;
                next[i] = j;
            }else {
                j = next[j];
            }
        }
        return next;
    }

    public static boolean contains(String str , String substr) {
        // 定义前缀数组
        int[] next = toNext(substr);

        char[] substrArray = substr.toCharArray();
        char[] strArray = str.toCharArray();
        int n = strArray.length , m = substrArray.length;
        int i = 0 , j = 0;
        while(i < n && j < m) {
            if(j == -1 || strArray[i] == substrArray[j]) {
                i ++;
                j ++;
            }else {
                j = next[j];
            }
        }
        return j == m;
    }

    public static void main(String[] args) {
        String str = "aabaabaadcaabaafaafc";
//        String substr = "aabaaf";
        String substr = "aacdaacf";     // [-1, 0, 1, 0, 0, 1, 2, 3]
//        String substr = "abcd";
        System.out.println(Arrays.toString(toNext(substr)));
        System.out.println(contains(str , substr));

    }
}
