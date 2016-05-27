package bloomfilter;

import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import bitarray.BitArray;
import common.RuntimeCalculator;

public class SimpleStringBloomFilterTest {

	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testFilter() {
		BitArray bitArray = new BitArray();
		RuntimeCalculator.begin();
		Set<Integer> set = bitArray.generateNumber(10000000);
		System.out.println(RuntimeCalculator.calculate());
		SimpleStringBloomFilter ssbf = new SimpleStringBloomFilter();
		for(Iterator<Integer> it = set.iterator() ; it.hasNext();) {
			ssbf.add(String.valueOf(it.next()));
		}
		System.out.println(ssbf.get("9999"));
	}
	
}
