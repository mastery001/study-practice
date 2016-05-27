package linear.sequence;

import static org.junit.Assert.fail;

import org.junit.Test;

import exception.StructureException;
import linear.List;

public class SequenceListTest {

	@Test
	public void testSequenceList() throws StructureException {
		List<String> list = new SequenceList<String>();
		list.insert(0, "asdas");
		list.insert(1, "sdf");
		list.insert(1, "a");
		int index = 0;
		while(index < 10) {
			if(list.get(index) != null) {
				System.out.println(index + "----" + list.get(index));
			}
			index ++;
		}
		System.out.println(list.size());
	}

	@Test
	public void testSequenceListInt() throws StructureException {
		List<Integer> list = new SequenceList<Integer>();
		for(int i = 0 ; i < 10 ; i ++) {
			list.insert(i, i+1);
		}
		list.delete(4);
		for(int i = 0 ; i < list.size() ; i++) {
			System.out.print(list.get(i) + "   ");
		}
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

	@Test
	public void testSize() {
		fail("尚未实现");
	}

	@Test
	public void testIsEmpty() {
		fail("尚未实现");
	}

}
