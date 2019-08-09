package demo;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public abstract class AbstractZNode implements Watcher , ZNode{

	/**
	 * session失效时间
	 * 2015年11月17日
	 */
	private static final int SESSION_TIME = 2000;
	
	/**
	 * zookeeper对象
	 * 2015年11月17日 上午10:24:33
	 */
	protected ZooKeeper zookeeper;
	
	protected CountDownLatch countDownLatch = new CountDownLatch(1);
	
	/**
	 * 连接zookeeper
	 * @param hosts
	 * @throws IOException
	 * @throws InterruptedException
	 * 2015年11月17日 上午10:47:47
	 */
	public void connect(String hosts) throws IOException, InterruptedException {
		zookeeper = new ZooKeeper(hosts , SESSION_TIME , this);
		countDownLatch.await();
	}
	
	@Override
	public void process(WatchedEvent event) {
		if(event.getState() == KeeperState.SyncConnected) {
			countDownLatch.countDown();
		}
	}
	
	/**
	 * 关闭zookeeper
	 * @throws InterruptedException
	 * 2015年11月17日 上午10:48:04
	 */
	public void close() throws InterruptedException {
		zookeeper.close();
	}
	
}
