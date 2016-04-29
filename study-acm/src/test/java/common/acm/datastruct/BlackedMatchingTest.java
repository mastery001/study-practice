package common.acm.datastruct;

import org.junit.Test;

import common.acm.datastruct.merge.BlackedMatchingProblem;

public class BlackedMatchingTest {

	@Test
	public void testBlackedMatchString() {
		BlackedMatchingProblem bm = new BlackedMatchingProblem();
		boolean flag = bm.blackedMatch("()[{gfdfgfd}](");
		
		if(flag) {
			System.out.println("YES");
		}else {
			System.out.println("NO");
		}
	}

}
