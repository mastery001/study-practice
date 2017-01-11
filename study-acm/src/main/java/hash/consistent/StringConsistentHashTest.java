package hash.consistent;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import hash.consistent.StringConsistentHash.HashFunction;

public class StringConsistentHashTest {
	
	static class BKDRHash implements HashFunction {

		@Override
		public long hash(String str) {
			/* BKDRHash */
			long seed = 131;// 31131131313131131313etc..
			long hash = 0;
			for (int i = 0; i < str.length(); i++)
				hash = (hash * seed) + str.charAt(i);
			return hash;
		}
	}
	
	public static void main(String[] args) {
		int collectionSize = 8 + 1000;
		StringConsistentHash sch = new StringConsistentHash(new BKDRHash(), collectionSize);
		for(int i =0 ; i < collectionSize; i ++) {
			sch.addNode("connects_" + i);
		}
		System.out.println(sch.getNode("94e9787e09a6a84efa51abee02af9f49"));
	}
	
	   /** 
     *  MurMurHash算法，是非加密HASH算法，性能很高， 
     *  比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免） 
     *  等HASH算法要快很多，而且据说这个算法的碰撞率很低. 
     *  http://murmurhash.googlepages.com/ 
     */  
    Long hash(String key) {  
          
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());  
        int seed = 0x1234ABCD;  
          
        ByteOrder byteOrder = buf.order();  
        buf.order(ByteOrder.LITTLE_ENDIAN);  
  
        long m = 0xc6a4a7935bd1e995L;  
        int r = 47;  
  
        long h = seed ^ (buf.remaining() * m);  
  
        long k;  
        while (buf.remaining() >= 8) {  
            k = buf.getLong();  
  
            k *= m;  
            k ^= k >>> r;  
            k *= m;  
  
            h ^= k;  
            h *= m;  
        }  
  
        if (buf.remaining() > 0) {  
            ByteBuffer finish = ByteBuffer.allocate(8).order(  
                    ByteOrder.LITTLE_ENDIAN);  
            // for big-endian version, do this first:   
            // finish.position(8-buf.remaining());   
            finish.put(buf).rewind();  
            h ^= finish.getLong();  
            h *= m;  
        }  
  
        h ^= h >>> r;  
        h *= m;  
        h ^= h >>> r;  
  
        buf.order(byteOrder);  
        return h;  
    }  
	
}
