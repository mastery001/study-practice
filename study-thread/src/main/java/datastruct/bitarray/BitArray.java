package datastruct.bitarray;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * 模拟字节数组
 * @author zouziwen
 *
 * 2016年4月15日 上午11:03:12
 */
public class BitArray {
	
	public static final int LENGTH = 10000;
	
	private static final Random random = new Random();
	
	/**
	 * 生成range大小range范围的Set(防止重复)集合
	 * @param range
	 * @return
	 * 2016年4月15日 上午11:01:42
	 */
	public Set<Integer> generateNumber(int range) {
		Set<Integer> set = new HashSet<Integer>(range);
		for(int i = 0 ; i < range ; i ++) {
			set.add(random.nextInt(range));
		}
		return set;
	}
	
	public byte[] fillBitArray(Set<Integer> set) {
		byte[] arrays = new byte[LENGTH];
		for(Iterator<Integer> it = set.iterator() ; it.hasNext();) {
			arrays[it.next()] = 1;
		}
		return arrays;
	}
	
	public int max(byte[] arrays ) {
		for(int i = arrays.length -1 ; i > 0 ; i--) {
			if(arrays[i] == 1) {
				return i;
			}
		}
		throw new NullPointerException("arrays not have element");
	}
	
	public static void main(String[] args) {
		BitArray bitArray = new BitArray();
		int max = bitArray.max(bitArray.fillBitArray(bitArray.generateNumber(LENGTH)));
		System.out.println(max);
	}
}
