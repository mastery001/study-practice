package study.nio.reactor;

import java.net.SocketAddress;

public interface Session {

	Handler getHandler();

	SocketAddress getLocalAddress();

	SocketAddress getRemoteAddress();

	SocketAddress getServiceAddress();

	FilterChain getFilterChain();

	Object getAttribute(String key);

	Object setAttribute(String key, Object value);
	
	void write(Object message);
}
