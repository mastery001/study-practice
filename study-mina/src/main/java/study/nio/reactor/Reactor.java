package study.nio.reactor;

import java.io.IOException;
import java.net.SocketAddress;

import study.nio.reactor.support.DefaultFilterChainBuilder;

/**
 * Reactor模式
 * 负责响应IO事件，一旦发生，广播发送给相应的Handler去处理。
 * @author zouziwen
 *
 * 2016年1月4日 下午4:25:51
 */
public interface Reactor {
	
	/**
	 * 监听
	 * 2016年1月4日 下午4:26:44
	 * @throws IOException 
	 */
	public void bind(SocketAddress address,Handler handler) throws IOException;
	
	
	FilterChainBuilder getFilterChainBuilder();
	
	DefaultFilterChainBuilder getFilterChain();
}
