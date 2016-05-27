package stack;

import static org.junit.Assert.fail;

import org.junit.Test;

import exception.StructureException;

public class StackTest {

	@Test
	public void testPush() throws StructureException {
		Stack<String> s = new Stack<String>();
		s.push("ssds");
		s.push("dfg");
		System.out.println(s.pop());
		System.out.println(s.pop());
	}
	
	@Test
	public void testPop() {
		fail("Not yet implemented");
	}

	@Test
	public void testPeek() {
		fail("Not yet implemented");
	}

}
