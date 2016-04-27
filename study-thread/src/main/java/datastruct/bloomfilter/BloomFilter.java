package datastruct.bloomfilter;

public interface BloomFilter<T> {
	
	
	void add(T value);
	
	boolean get(T value);
	
	
	/**
	 * hash计算
	 * @author zouziwen
	 *
	 * 2016年4月15日 下午3:05:35
	 */
	public abstract class HashCalculator<T> {
		
		/* 
         * cap为DEFAULT_SIZE，即用于结果的最大字符串的值 
         * seed为计算hash值的一个key值，
         */  
        protected final int cap;  
        protected final int seed;  
        
		public HashCalculator(int cap, int seed) {
			super();
			this.cap = cap;
			this.seed = seed;
		}

		/**
		 * 获取对象的hash值
		 * @param obj
		 * @return
		 * 2016年4月15日 下午3:06:51
		 */
		abstract int hash(T obj);
	}
}
