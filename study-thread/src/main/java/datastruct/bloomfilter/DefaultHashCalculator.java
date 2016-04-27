package datastruct.bloomfilter;

import datastruct.bloomfilter.BloomFilter.HashCalculator;

/**
 * 默认的hash值计算
 * @author zouziwen
 *
 * 2016年4月15日 下午3:11:22
 */
class DefaultHashCalculator<T> extends HashCalculator<T>{

	public DefaultHashCalculator(int cap, int seed) {
		super(cap, seed);
	}

	@Override
	public int hash(T obj) {
		if(obj != null) {
			return obj.hashCode() & 0x7FFFFFFF;
		}
		return 0;
	}

}
