package bloomfilter;

/**
 * 针对String的过滤器
 * 
 * @author zouziwen
 *
 *         2016年4月15日 下午3:17:54
 */
public class SimpleStringBloomFilter extends AbstractBloomFilter<String> {
	
	public SimpleStringBloomFilter() {
		super();
	}

	public SimpleStringBloomFilter(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	protected HashCalculator<String>[] getHashCalculator(int[] seeds) {
		StringHashCalculator[] hashCalculators = new StringHashCalculator[seeds.length];
		/** 
         *  给出所有的hash值，共计seeds.length个hash值。共8位。 
         *  通过调用SimpleHash.hash(),可以得到根据8种hash函数计算得出hash值。   
         *  传入DEFAULT_SIZE(最终字符串的长度），seeds[i](一个指定的质数)即可得到需要的那个hash值的位置。         
         */  
        for(int i=0; i<seeds.length; i++){  
        	hashCalculators[i] = new StringHashCalculator(DEFAULT_INITIAL_CAPACITY, seeds[i]);  
        }  
		return hashCalculators;
	}

	class StringHashCalculator extends HashCalculator<String> {

		public StringHashCalculator(int cap, int seed) {
			super(cap, seed);
		}

		@Override
		int hash(String obj) {
			int result = 0;
			int length = obj.length();
			for (int i = 0; i < length; i++) {
				result = seed * result + obj.charAt(i);
			}
			return (cap - 1) & result & 0x7FFFFFFF;
		}

	}
}
