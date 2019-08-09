package nio.selector;

import java.io.IOException;
import java.net.SocketAddress;

public interface SocketSession {
	
	IHandler getHandler();
	
	SocketAddress getLocalAddress();

	SocketAddress getRemoteAddress();

	SocketAddress getServiceAddress();
	
	void write(Object message) throws IOException;
	
	SocketSession setAttribute(String key , Object value);
	
	Object getAttribute(String key);
}
