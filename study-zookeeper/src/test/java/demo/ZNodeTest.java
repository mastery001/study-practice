package demo;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZNodeTest {

	private static final Logger logger = LoggerFactory.getLogger(ZNodeTest.class);
	
	@Test
	public void testCreate() throws IOException, InterruptedException, KeeperException {
		ZNode node = new ZNodeImpl();
		node.connect("10.58.100.166:2181,10.58.100.166:2182,10.58.100.166:2183");
		byte[] data = new byte[]{'a' , 'b' , 'c' , 'd'};
		//String zkTest = "Zookeeper的Java API测试";
		node.create("/root/child2", data);
		logger.info("获取设置的信息："+new String(node.getData("/root/child2")));
		logger.info("节点孩子信息为：");
		node.getChild("/root");
		node.close();
	}

	@Test
	public void testGetChild() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetData() {
		fail("Not yet implemented");
	}

}
