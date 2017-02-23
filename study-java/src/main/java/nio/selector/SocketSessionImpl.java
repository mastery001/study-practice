package nio.selector;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SocketSessionImpl implements SocketSession{

	private final SocketChannel channel;
	
	private final IHandler handler;
	
	private final SocketIoProcessor ioProcessor;
	
	private final SocketAddress remoteAddress;

	private final SocketAddress localAddress;

	private final SocketAddress serviceAddress;
	
	private SelectionKey key;
	
	private final Map<String , Object > attributes = new HashMap<String , Object>();
	
	private final LinkedList<ByteBuffer> writeRequestQueue = new LinkedList<ByteBuffer>();
	
	public SocketSessionImpl(SocketChannel channel, IHandler handler , SocketIoProcessor ioProcessor , SocketAddress serviceAddress) {
		super();
		this.channel = channel;
		this.handler = handler;
		this.localAddress = channel.socket().getLocalSocketAddress();
		this.remoteAddress = channel.socket().getRemoteSocketAddress();
		this.serviceAddress = serviceAddress;
		this.ioProcessor = ioProcessor;
	}

	public SocketChannel getChannel() {
		return channel;
	}

	@Override
	public IHandler getHandler() {
		return handler;
	}
	
	public LinkedList<ByteBuffer> getWriteRequestQueue() {
		return writeRequestQueue;
	}
	
	public void setSelectionKey(SelectionKey key) {
		this.key = key;
	}
	
	public SelectionKey getSelectionKey() {
		return key;
	}

	@Override
	public void write(Object message) throws IOException {
		if(message == null) {
			return ;
		}
		
		doWrite(message);
		
	}
	
	private void doWrite(Object message) {
 		String writeMessage = message.toString();
		ByteBuffer buffer = ByteBuffer.allocate(writeMessage.length() * 2 + 1);
		buffer.put(writeMessage.getBytes());
		buffer.flip();
		
		buffer.mark();
		
		synchronized (writeRequestQueue) {
			writeRequestQueue.push(buffer);
		}
		
		this.ioProcessor.onWrite(this);
	}

	@Override
	public SocketSession setAttribute(String key, Object value) {
		synchronized (attributes) {
			attributes.put(key, value);
		}
		return this;
	}

	@Override
	public Object getAttribute(String key) {
		synchronized (attributes) {
			return attributes.get(key);
		}
	}

	@Override
	public SocketAddress getLocalAddress() {
		return localAddress;
	}

	@Override
	public SocketAddress getServiceAddress() {
		return serviceAddress;
	}

	@Override
	public SocketAddress getRemoteAddress() {
		return remoteAddress;
	}
}
