package leetcode;

public class LongestSubstring {
	
	public int lengthOfLongestSubstring(String s) {
        int maxLen = 0;
        char[] chs = s.toCharArray();
        char[] result = new char[chs.length];
        for(int i = 0 ; i < chs.length ; i++) {
        	int j = i , start = i;
        	int size = 0;
        	while(j < chs.length) {
        		boolean duplicate = false;
        		for(int k = start ; k < start+size; k++) {
        			if(chs[j] == result[k]) {
        				duplicate = true;
//        				for(int in = start+1 ; in < start + size ; in ++) {
//        					result[in] = ' ';
//        				}
        				break;
        			}
        		}
        		if(duplicate){
        			break;
        		}else{
        			result[j] = chs[j];
        			j++;
        			size ++;
        		}
        	}
        	maxLen = maxLen > size ? maxLen : size;
        }
		return maxLen;
    }
	
	public static void main(String[] args) {
		String s = "c";
		System.out.println(new LongestSubstring().lengthOfLongestSubstring(s));
	}
}
