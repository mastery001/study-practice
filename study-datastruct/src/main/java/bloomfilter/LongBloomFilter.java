package bloomfilter;

import java.util.Random;

public class LongBloomFilter extends AbstractBloomFilter<Long>{
	
	// 希望的误差率
	public static final double PERCENTAGE = 0.001;
	
	// hash函数的数量
	private int hashNum;
	
	// 位向量的長度
	private int size;
	
	private int initialCapacity;
	
	
	public LongBloomFilter() {
	}
	
	public LongBloomFilter(int initialCapacity) {
		super(initialCapacity);
		this.initialCapacity = initialCapacity;
		size = (int) Math.abs(initialCapacity * Math.log(PERCENTAGE) / (Math.log(2) * Math.log(2))) + 1;
		hashNum = (int) (Math.log(2) * ((double) size / initialCapacity));
	}
	
	@Override
	protected HashCalculator<Long>[] getHashCalculator(int[] seeds) {
		LongHashCalculator[] hashCalculators = new LongHashCalculator[seeds.length];
        for(int i=0; i<seeds.length; i++){  
        	hashCalculators[i] = new LongHashCalculator(initialCapacity, seeds[i]);  
        }  
		return hashCalculators;
	}
	
	@Override
	protected int[] getHashSeeds() {
		int[] seeds = new int[hashNum];
		for (int i = 0; i < hashNum; i++) {
			seeds[i] = i;
		}
		return seeds;
	}



	class LongHashCalculator extends HashCalculator<Long>  {

		
		public LongHashCalculator(int cap, int seed) {
			super(cap, seed);
		}

		@Override
		int hash(Long obj) {
			Random random = new Random(obj);
			return random.nextInt(size);
		}
		
	}

}
