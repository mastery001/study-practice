package demo;

import java.io.IOException;

public interface ZookeeperClient {

	/**
	 * 连接zookeeper
	 * @param hosts
	 * @throws IOException
	 * @throws InterruptedException
	 * 2015年11月17日 上午10:47:47
	 */
	public void connect(String hosts) throws IOException, InterruptedException;
	
	/**
	 * 关闭zookeeper
	 * @throws InterruptedException
	 * 2015年11月17日 上午10:48:04
	 */
	public void close() throws InterruptedException;
}
