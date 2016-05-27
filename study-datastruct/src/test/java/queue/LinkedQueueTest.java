package queue;

import static org.junit.Assert.fail;

import org.junit.Test;

import exception.StructureException;

public class LinkedQueueTest {

	@Test
	public void testLinkedQueue() throws StructureException {
		Queue<Integer> queue = new LinkedQueue<Integer>();
		queue.add(1);
		queue.add(2);
		System.out.println(queue.remove());
		System.out.println(queue.remove());
	}

	@Test
	public void testAdd() {
		fail("尚未实现");
	}

	@Test
	public void testRemove() {
		fail("尚未实现");
	}

	@Test
	public void testPeek() {
		fail("尚未实现");
	}

}
