package study.nio.reactor.socket;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.nio.reactor.Filter.WriteRequest;
import study.nio.reactor.Session;
import study.nio.reactor.support.AbstractFilterChain;
import study.nio.reactor.util.Queue;

class SocketFilterChain extends AbstractFilterChain {

	private final Logger logger = LoggerFactory.getLogger(SocketFilterChain.class);
	
	public SocketFilterChain(Session session) {
		super(session);
	}

	protected void doWrite(Session session, WriteRequest writeRequest) {
		SocketSessionImpl s = (SocketSessionImpl) session;
		Queue writeRequestQueue = s.getWriteRequestQueue();

		// SocketIoProcessor.doFlush() will reset it after write is finished
		// because the buffer will be passed with messageSent event.
		ByteBuffer buffer = (ByteBuffer) writeRequest.getMessage();
		buffer.mark();

		int remaining = buffer.remaining();
		logger.info("remaining : {}" , remaining);
		if (remaining == 0) {
			s.increaseScheduledWriteRequests();
		} else {
			s.increaseScheduledWriteBytes(buffer.remaining());
		}

		synchronized (writeRequestQueue) {
			writeRequestQueue.push(writeRequest);
		}

		s.getIoProcessor().flush(s);
	}

	protected void doClose(Session session) throws IOException {
		// SocketSessionImpl s = (SocketSessionImpl) session;
		// s.getIoProcessor().remove(s);
	}

}
