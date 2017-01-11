package hash.consistent;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Title: 一致性hash
 * @Description:
 * @Author:zouziwen
 * @Since:2016年11月9日
 * @Version:1.1.0
 */
public class StringConsistentHash {

	private HashFunction hashFunc;

	private final int numberOfReplicas;// 节点的复制因子,实际节点个数 * numberOfReplicas =
										// 虚拟节点个数

	private SortedMap<Long, String> circle = new TreeMap<Long, String>(); // 存储虚拟节点的hash值到真实节点的映射

	public StringConsistentHash(HashFunction hashFunc, int numberOfReplicas) {
		super();
		this.hashFunc = hashFunc;
		this.numberOfReplicas = numberOfReplicas;
	}
	
	public void addNode(String node) {
		for(int i =0 ; i < numberOfReplicas ; i ++ ) {
			circle.put(hashFunc.hash(node + i), node);
		}
	}
	
	public String getNode(String key) {
		if(circle.isEmpty()) {
			return null;
		}
		long hash = hashFunc.hash(key);
		if(!circle.containsKey(hash)) {
			SortedMap<Long, String> tailMap = circle.tailMap(hash);
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		return circle.get(hash);
	}

	public static interface HashFunction {

		long hash(String obj);
	}
}
