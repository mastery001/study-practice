package study.nio.reactor.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.nio.reactor.Filter.WriteRequest;
import study.nio.reactor.util.ExceptionMonitor;
import study.nio.reactor.util.NamePreservingRunnable;
import study.nio.reactor.util.Queue;

class SocketIoProcessor {

	protected static final Logger logger = LoggerFactory.getLogger(SocketIoProcessor.class);
	
	private static final int WRITE_SPIN_COUNT = 256;

	/**
	 * @noinspection FieldAccessedSynchronizedAndUnsynchronized
	 */
	private Selector selector;

	private final Queue newSessions = new Queue();

	private final Queue removingSessions = new Queue();

	private final Queue flushingSessions = new Queue();

	private final Queue trafficControllingSessions = new Queue();

	private final Object lock = new Object();

	private final String threadName;

	private final Executor executor;

	private Worker worker;

	public SocketIoProcessor(String threadName, Executor executor) {
		this.threadName = threadName;
		this.executor = executor;
	}

	public void addNew(SocketSessionImpl session) throws IOException {
		synchronized (newSessions) {
			newSessions.push(session);
		}
		startupWorker();
	}

	private void startupWorker() throws IOException {
		synchronized (lock) {
			if (worker == null) {
				selector = Selector.open();
				worker = new Worker();
				executor.execute(new NamePreservingRunnable(worker, threadName));
			}
			selector.wakeup();
		}
	}

	private Selector getSelector() {
		synchronized (lock) {
			return this.selector;
		}
	}

	void flush(SocketSessionImpl session) {
		if (scheduleFlush(session)) {
			Selector selector = getSelector();
			if (selector != null) {
				selector.wakeup();
			}
		}
	}

	private void doAddNew() {
		if (newSessions.isEmpty())
			return;

		Selector selector = getSelector();
		for (;;) {
			SocketSessionImpl session;

			synchronized (newSessions) {
				session = (SocketSessionImpl) newSessions.pop();
			}

			if (session == null)
				break;

			SocketChannel ch = session.getChannel();
			try {
				ch.configureBlocking(false);
				session.setSelectionKey(ch.register(selector, SelectionKey.OP_READ, session));

				session.getFilterChain().fireSessionCreated(session);
			} catch (IOException e) {

			}
		}
	}

	private void doUpdateTrafficMask() {
		if (trafficControllingSessions.isEmpty())
			return;
		for (;;) {
			SocketSessionImpl session;

			synchronized (trafficControllingSessions) {
				session = (SocketSessionImpl) trafficControllingSessions.pop();
			}

			if (session == null)
				break;

			SelectionKey key = session.getSelectionKey();
			// Retry later if session is not yet fully initialized.
			// (In case that Session.suspend??() or session.resume??() is
			// called before addSession() is processed)
			if (key == null) {
				scheduleTrafficControl(session);
				break;
			}
			// skip if channel is already closed
			if (!key.isValid()) {
				continue;
			}

			// The normal is OP_READ and, if there are write requests in the
			// session's write queue, set OP_WRITE to trigger flushing.
			int ops = SelectionKey.OP_READ;
			Queue writeRequestQueue = session.getWriteRequestQueue();
			synchronized (writeRequestQueue) {
				if (!writeRequestQueue.isEmpty()) {
					ops |= SelectionKey.OP_WRITE;
				}
			}

			// Now mask the preferred ops with the mask of the current session
			int mask = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
			key.interestOps(ops & mask);
		}
	}

	private void scheduleTrafficControl(SocketSessionImpl session) {
		synchronized (trafficControllingSessions) {
			trafficControllingSessions.push(session);
		}
	}

	private void doFlush() {
		if (flushingSessions.size() == 0)
			return;

		for (;;) {
			SocketSessionImpl session;

			synchronized (flushingSessions) {
				session = (SocketSessionImpl) flushingSessions.pop();
			}

			if (session == null)
				break;

			session.setScheduledForFlush(false);

			SelectionKey key = session.getSelectionKey();
			// Retry later if session is not yet fully initialized.
			// (In case that Session.write() is called before addSession() is
			// processed)
			if (key == null) {
				scheduleFlush(session);
				break;
			}

			// Skip if the channel is already closed.
			if (!key.isValid()) {
				continue;
			}

			try {
				boolean flushedAll = doFlush(session);
				if (flushedAll && !session.getWriteRequestQueue().isEmpty()) {
					scheduleFlush(session);
				}
			} catch (IOException e) {
				scheduleRemove(session);
				session.getFilterChain().fireExceptionCaught(session, e);
			}
		}
	}

	private void scheduleRemove(SocketSessionImpl session) {
		synchronized (removingSessions) {
			removingSessions.push(session);
		}
	}

	private void doRemove() {
		if (removingSessions.isEmpty())
			return;

		for (;;) {
			SocketSessionImpl session;

			synchronized (removingSessions) {
				session = (SocketSessionImpl) removingSessions.pop();
			}

			if (session == null)
				break;

			SocketChannel ch = session.getChannel();
			SelectionKey key = session.getSelectionKey();
			// Retry later if session is not yet fully initialized.
			// (In case that Session.close() is called before addSession() is
			// processed)
			if (key == null) {
				scheduleRemove(session);
				break;
			}
			// skip if channel is already closed
			if (!key.isValid()) {
				continue;
			}

			try {
				key.cancel();
				ch.close();
			} catch (IOException e) {
				session.getFilterChain().fireExceptionCaught(session, e);
			} finally {
				releaseWriteBuffers(session);
				session.getFilterChain().fireSessionClosed(session);
			}
		}
	}

	private void releaseWriteBuffers(study.nio.reactor.socket.SocketSessionImpl session) {
		Queue writeRequestQueue = session.getWriteRequestQueue();
		WriteRequest req;

		if ((req = (WriteRequest) writeRequestQueue.pop()) != null) {
			ByteBuffer buf = (ByteBuffer) req.getMessage();
			try {
				buf.clear();
			} catch (IllegalStateException e) {
				session.getFilterChain().fireExceptionCaught(session, e);
			} finally {
				// The first unwritten empty buffer must be
				// forwarded to the filter chain.
				session.getFilterChain().fireMessageSent(session, req);
			}

			// Discard others.
			while ((req = (WriteRequest) writeRequestQueue.pop()) != null) {
				try {
					((ByteBuffer) req.getMessage()).clear();
				} catch (IllegalStateException e) {
					session.getFilterChain().fireExceptionCaught(session, e);
				}
			}
		}
	}

	private boolean doFlush(SocketSessionImpl session) throws IOException {
		SocketChannel ch = session.getChannel();
		if (!ch.isConnected()) {
			scheduleRemove(session);
			return false;
		}

		// Clear OP_WRITE
		SelectionKey key = session.getSelectionKey();
		key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));

		Queue writeRequestQueue = session.getWriteRequestQueue();

		int writtenBytes = 0;
		int maxWrittenBytes = session.getWriteBufferSize() << 1;
		try {
			for (;;) {
				WriteRequest req;

				synchronized (writeRequestQueue) {
					req = (WriteRequest) writeRequestQueue.first();
				}

				if (req == null)
					break;

				ByteBuffer buf = (ByteBuffer) req.getMessage();
				if (buf.remaining() == 0) {
					synchronized (writeRequestQueue) {
						writeRequestQueue.pop();
					}

					buf.reset();

					if (!buf.hasRemaining()) {
						session.increaseWrittenMessages();
					}

					session.getFilterChain().fireMessageSent(session, req);
					continue;
				}

				int localWrittenBytes = 0;
				for (int i = WRITE_SPIN_COUNT; i > 0; i--) {
					localWrittenBytes = ch.write(buf);
					if (localWrittenBytes != 0 || !buf.hasRemaining()) {
						break;
					}
				}

				writtenBytes += localWrittenBytes;

				if (localWrittenBytes == 0 || writtenBytes >= maxWrittenBytes) {
					// Kernel buffer is full or wrote too much.
					key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
					return false;
				}
			}
		} finally {
			session.increaseWrittenBytes(writtenBytes);
		}

		return true;
	}

	private boolean scheduleFlush(SocketSessionImpl session) {
		if (session.setScheduledForFlush(true)) {
			synchronized (flushingSessions) {
				flushingSessions.push(session);
			}

			return true;
		}

		return false;
	}

	private void process(Set<SelectionKey> selectedKeys) {
		Iterator<SelectionKey> it = selectedKeys.iterator();

		while (it.hasNext()) {
			SelectionKey key = (SelectionKey) it.next();
			SocketSessionImpl session = (SocketSessionImpl) key.attachment();

			if (key.isReadable()) {
				read(session);
			}

			if (key.isWritable()) {
				scheduleFlush(session);
			}
		}

		selectedKeys.clear();
	}

	private void read(SocketSessionImpl session) {
		ByteBuffer buf = ByteBuffer.allocate(session.getReadBufferSize());
		SocketChannel ch = session.getChannel();

		try {
			int readBytes = 0;
			int ret;

			try {
				while ((ret = ch.read(buf)) > 0) {
					readBytes += ret;
				}
			} finally {
				buf.flip();
			}

			if (readBytes > 0) {
				session.getFilterChain().fireMessageReceived(session, buf);
				buf = null;
			}
		} catch (Throwable e) {
			session.getFilterChain().fireExceptionCaught(session, e);
		} finally {
			if (buf != null)
				buf.clear();
		}
	}

	private class Worker implements Runnable {

		@Override
		public void run() {
			Selector selector = getSelector();
			for (;;) {
				try {
					int nKeys = selector.select(1000);

					doAddNew();
					doUpdateTrafficMask();

					if (nKeys > 0) {
						process(selector.selectedKeys());
					}

					doFlush();

					doRemove();
				} catch (IOException e) {
					ExceptionMonitor.getInstance().exceptionCaught(e);
				}
			}
		}

	}

}
