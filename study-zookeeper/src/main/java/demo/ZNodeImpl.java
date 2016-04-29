package demo;

import java.util.List;
import java.util.Objects;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zookeeper的一些基本操作
 * 
 * @author zouziwen
 *
 *         2015年11月17日 上午10:49:19
 */
public class ZNodeImpl extends AbstractZNode {

	private static final Logger logger = LoggerFactory.getLogger(ZNodeImpl.class);

	@Override
	public void create(String path, byte[] data) throws KeeperException, InterruptedException {
		Objects.requireNonNull(path);
		String[] dirs = null;
		path = path.replace("\\", "/");
		if (path.contains("/")) {
			dirs = path.split("/");
		}
		if (dirs == null) {
			Stat stat = this.zookeeper.exists(path, false);
			if (stat == null) {
				String info = this.zookeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				logger.info("创建成功，返回信息为：" + info);
			}
		} else {
			String currentDir = "";
			for (int i = 0; i < dirs.length; i++) {
				String dir = dirs[i];
				if (!dir.equals("")) {
					currentDir += "/" + dir;
					Stat stat = this.zookeeper.exists(currentDir, false);
					if (stat == null) {
						String info = null;
						if (i == dirs.length - 1) {
							info = this.zookeeper.create(currentDir, data, Ids.OPEN_ACL_UNSAFE,
									CreateMode.PERSISTENT);
						} else {
							info = this.zookeeper.create(currentDir, null, Ids.OPEN_ACL_UNSAFE,
									CreateMode.PERSISTENT);
						}
						logger.info("创建成功，返回信息为：" + info);

					}
				}

			}
		}

	}

	@Override
	public void getChild(String path) throws KeeperException, InterruptedException {
		List<String> list = this.zookeeper.getChildren(path, false);
		if (list.isEmpty()) {
			logger.info(path + "中没有节点");
		} else {
			logger.info(path + "中存在节点");
			for (String child : list) {
				logger.info("节点为" + child);
			}
		}
	}

	@Override
	public byte[] getData(String path) throws KeeperException, InterruptedException {
		Stat stat = this.zookeeper.exists(path, false);
		if (stat != null) {
			return this.zookeeper.getData(path, false, null);
		}
		return null;
	}

	@Override
	public void update(String path, byte[] data) throws KeeperException, InterruptedException {
		Stat stat = this.zookeeper.exists(path, false);
		if (stat != null) {
			this.zookeeper.setData(path, data, stat.getVersion());
		}

	}

	@Override
	public void delete(String path) throws KeeperException, InterruptedException {
		Stat stat = this.zookeeper.exists(path, false);
		if (stat != null) {
			this.zookeeper.delete(path, stat.getVersion());
		}
	}

}
