package study.nio.reactor.support;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import study.nio.reactor.Filter.WriteRequest;
import study.nio.reactor.Session;

public abstract class BaseSession implements Session {

	private final Map<String, Object> attributes = new HashMap<String, Object>(8);

	private final AtomicBoolean scheduledForFlush = new AtomicBoolean();

	private final AtomicInteger scheduledWriteBytes = new AtomicInteger();

	private final AtomicInteger scheduledWriteRequests = new AtomicInteger();

	private long writtenMessages;

	private long writtenBytes;

	public long getWrittenWriteRequests() {
		return writtenMessages;
	}

	public long getWrittenBytes() {
		return writtenBytes;
	}

	@Override
	public Object getAttribute(String key) {
		synchronized (attributes) {
			return attributes.get(key);
		}
	}

	@Override
	public Object setAttribute(String key, Object value) {
		synchronized (attributes) {
			return attributes.put(key, value);
		}
	}

	public boolean setScheduledForFlush(boolean flag) {
		if (flag) {
			return scheduledForFlush.compareAndSet(false, true);
		} else {
			scheduledForFlush.set(false);
			return true;
		}
	}

	public void increaseWrittenMessages() {
		writtenMessages++;
		scheduledWriteRequests.decrementAndGet();
	}

	public void increaseWrittenBytes(int increment) {
		if (increment > 0) {
			writtenBytes += increment;

			scheduledWriteBytes.addAndGet(-increment);
		}
	}

	public void increaseScheduledWriteBytes(int increment) {
        scheduledWriteBytes.addAndGet(increment);
    }
	
	public void increaseScheduledWriteRequests() {
		scheduledWriteRequests.incrementAndGet();
	}

	@Override
	public void write(Object message) {
		write(message, null);
	}

	public void write(Object message, SocketAddress remoteAddress) {
		write0(new WriteRequest(message, remoteAddress));
	}

	protected void write0(WriteRequest writeRequest) {
	}

}
