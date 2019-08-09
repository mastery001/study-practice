package record;

import java.nio.channels.Selector;
import java.util.Objects;

/**
 * nio四种操作的维持者
 * 
 * @author zouziwen
 *
 *         2016年1月4日 下午8:32:30
 */
public class SelectorHolder {

	private static SelectorHolder INSTANCE;
	
	
	private static final Byte lock = 0;
	
	private final Selector[] selectors = new Selector[4];

	/**
	 * 单例生成SelectorHolder对象
	 * @param selectors
	 * @return
	 * 2016年1月4日 下午8:57:29
	 */
	public static SelectorHolder getInstance(Selector[] selectors) {
		if(INSTANCE == null) {
			synchronized (lock) {
				if(INSTANCE == null) {
					INSTANCE = new SelectorHolder(selectors);
				}
			}
		}
		return INSTANCE;
	}
	
	private SelectorHolder(Selector[] selectors) {
		Objects.requireNonNull(selectors);
		if (selectors.length == 1) {
			for (int i = 0; i < this.selectors.length; i++) {
				this.selectors[i] = selectors[0];
			}
		} else if (selectors.length == 2) {
			for (int i = 1; i <= this.selectors.length; i++) {
				if (i % 2 == 0) {
					this.selectors[i - 1] = selectors[i - 1];
				}
			}
		} else if (selectors.length == 3) {
			for (int i = 0; i < this.selectors.length - 1; i++) {
				this.selectors[i] = selectors[i];
			}
			this.selectors[this.selectors.length - 1] = selectors[0];
		} else if (selectors.length >= 4) {
			for (int i = 0; i < this.selectors.length; i++) {
				this.selectors[i] = selectors[i];
			}
		}
	}

	/**
	 * 获得SelectionKey.OP_ACCEPT的Selector
	 * 
	 * @return 2016年1月4日 下午8:33:01
	 */
	public Selector selectorForAcceptor() {
		return selectors[0];
	}

	/**
	 * 获得SelectionKey.OP_READ的Selector
	 * 
	 * @return 2016年1月4日 下午8:33:39
	 */
	public Selector selectorForRead() {
		return selectors[1];
	}

	/**
	 * 获得SelectionKey.OP_WRITE的Selector
	 * 
	 * @return 2016年1月4日 下午8:33:41
	 */
	public Selector selectorForWrite() {
		return selectors[2];
	}

	/**
	 * 获得SelectionKey.OP_CONNECT的Selector
	 * 
	 * @return 2016年1月4日 下午8:33:42
	 */
	public Selector selectorForConnect() {
		return selectors[3];
	}
}
