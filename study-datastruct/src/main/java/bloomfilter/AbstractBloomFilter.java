package bloomfilter;

import java.util.BitSet;

public abstract class AbstractBloomFilter<T> implements BloomFilter<T> {

	// DEFAULT_SIZE为2的29次方，即此处的左移28位
	static final int DEFAULT_INITIAL_CAPACITY = 2 << 28;

	static final int[] DEFAULT_SEEDS = new int[]{3, 5, 7, 11, 13, 31, 37, 61};
	
	/* 
     * 不同哈希函数的种子，一般取质数 
     * seeds数组共有8个值，则代表采用8种不同的哈希函数 
     */  
    private int[] seeds = DEFAULT_SEEDS;  

	protected BitSet bitSets;

	private HashCalculator<T>[] hashCalculators;

	public AbstractBloomFilter() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * 初始化位集大小
	 * 
	 * @param initialCapacity
	 *            2016年4月15日 下午3:21:56
	 */
	@SuppressWarnings("unchecked")
	public AbstractBloomFilter(int initialCapacity) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
		bitSets = new BitSet(initialCapacity);
		hashCalculators = getHashCalculator(getHashSeeds());
		if(hashCalculators == null || hashCalculators.length != initialCapacity) {
			hashCalculators = new HashCalculator[getHashSeeds().length];
			/** 
	         *  给出所有的hash值，共计seeds.length个hash值。共8位。 
	         *  通过调用SimpleHash.hash(),可以得到根据8种hash函数计算得出hash值。   
	         *  传入DEFAULT_SIZE(最终字符串的长度），seeds[i](一个指定的质数)即可得到需要的那个hash值的位置。         
	         */  
	        for(int i=0; i<seeds.length; i++){  
	        	hashCalculators[i] = new DefaultHashCalculator<T>(DEFAULT_INITIAL_CAPACITY, seeds[i]);  
	        }  
		}
	}


	@Override
	public synchronized void add(T value) {
		for(HashCalculator<T> hashCalculator : hashCalculators) {
			bitSets.set(hashCalculator.hash(value) , true);
		}
	}

	@Override
	public synchronized boolean get(T value) {
		if(value == null) {
			return false;
		}
		for(HashCalculator<T> hashCalculator : hashCalculators) {
			if(!bitSets.get(hashCalculator.hash(value))) {
				//如果判断8个hash函数值中有一个位置不存在即可判断为不存在Bloofilter中  
                return false;  
			}
		}
		return true;
	}

	protected abstract HashCalculator<T>[] getHashCalculator(int[] seeds);

	/**
	 * 不同哈希函数的种子，一般取质数 seeds数组共有8个值，则代表采用8种不同的哈希函数
	 * 
	 * @return 2016年4月15日 下午3:46:43
	 */
	protected int[] getHashSeeds() {
		return seeds;
	}
}
