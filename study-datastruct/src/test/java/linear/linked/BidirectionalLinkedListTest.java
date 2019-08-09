package linear.linked;

import static org.junit.Assert.fail;

import org.junit.Test;

import exception.StructureException;
import linear.List;

public class BidirectionalLinkedListTest {

	@Test
	public void testBidirectionalLinkedList() throws StructureException {
		List<Integer> list = new BidirectionalLinkedList<Integer>();
		for(int i = 0 ; i < 10 ; i ++) {
			list.insert(i, i+1);
		}
		list.delete(4);
		for(int i = 0 ; i < list.size() ; i++) {
			System.out.print(list.get(i) + "   ");
		}
	}

	@Test
	public void testIndexNodeToCurrent() {
		fail("尚未实现");
	}

	@Test
	public void testInsert() {
		fail("尚未实现");
	}

	@Test
	public void testDelete() {
		fail("尚未实现");
	}

	@Test
	public void testGet() {
		fail("尚未实现");
	}

}
