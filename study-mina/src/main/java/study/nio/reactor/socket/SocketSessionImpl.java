package study.nio.reactor.socket;

import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import study.nio.reactor.Filter.WriteRequest;
import study.nio.reactor.FilterChain;
import study.nio.reactor.Handler;
import study.nio.reactor.support.BaseSession;
import study.nio.reactor.util.Queue;

class SocketSessionImpl extends BaseSession {

	private static final int DEFAULT_BUFFER_SIZE = 1024;

	private final Handler handler;

	private final SocketAddress remoteAddress;

	private final SocketAddress localAddress;

	private final SocketAddress serviceAddress;

	private final SocketFilterChain filterChain;

	private final Queue writeRequestQueue;

	private final SocketChannel ch;

	private final SocketIoProcessor ioProcessor;

	private SelectionKey key;

	private int readBufferSize = DEFAULT_BUFFER_SIZE;

	private int writeBufferSize = DEFAULT_BUFFER_SIZE;

	SocketSessionImpl(SocketIoProcessor ioProcessor, SocketChannel ch, Handler defaultHandler,
			SocketAddress serviceAddress) {
		this.ioProcessor = ioProcessor;
		this.ch = ch;
		this.handler = defaultHandler;
		this.writeRequestQueue = new Queue();
		this.filterChain = new SocketFilterChain(this);
		this.localAddress = ch.socket().getLocalSocketAddress();
		this.remoteAddress = ch.socket().getRemoteSocketAddress();
		this.serviceAddress = serviceAddress;
	}

	@Override
	public Handler getHandler() {
		return handler;
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

	public SelectionKey getSelectionKey() {
		return key;
	}

	public FilterChain getFilterChain() {
		return filterChain;
	}

	public Queue getWriteRequestQueue() {
		return writeRequestQueue;
	}

	public void setSelectionKey(SelectionKey key) {
		this.key = key;
	}

	public int getReadBufferSize() {
		return readBufferSize;
	}

	public void setReadBufferSize(int readBufferSize) {
		this.readBufferSize = readBufferSize;
	}

	public int getWriteBufferSize() {
		return writeBufferSize;
	}

	public void setWriteBufferSize(int writeBufferSize) {
		this.writeBufferSize = writeBufferSize;
	}

	public SocketChannel getChannel() {
		return ch;
	}

	public SocketIoProcessor getIoProcessor() {
		return ioProcessor;
	}
	
	protected void write0(WriteRequest writeRequest) {
		filterChain.fireFilterWrite(this, writeRequest);
	}

}
