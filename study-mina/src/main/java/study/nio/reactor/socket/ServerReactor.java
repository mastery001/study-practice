package study.nio.reactor.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.nio.reactor.Handler;
import study.nio.reactor.support.BaseReactor;
import study.nio.reactor.util.ExceptionMonitor;
import study.nio.reactor.util.NamePreservingRunnable;
import study.nio.reactor.util.Queue;

/**
 * Server端的Reactor，负责维持三个Handler，分别是acceptor、Read、Writ
 * 
 * @author zouziwen
 *
 *         2016年1月4日 下午4:32:18
 */
public class ServerReactor extends BaseReactor {

	private static final int DEFAULT_BACKLOG = 1024;

	static Logger logger = LoggerFactory.getLogger(ServerReactor.class);

	/**
	 * @noinspection StaticNonFinalField
	 */
	private static volatile int nextId = 0;

	private final Executor executor;

	private final Object lock = new Object();

	private final int id = nextId++;

	private final String threadName = "SocketAcceptor-" + id;

	private final int processorCount;

	private final Queue registerQueue = new Queue();

	/**
	 * @noinspection FieldAccessedSynchronizedAndUnsynchronized
	 */
	private Selector selector;

	private Worker worker;

	private SocketIoProcessor[] ioProcessors;

	private int processorDistributor = 0;

	public ServerReactor() {
		this(1, Executors.newCachedThreadPool());
	}

	public ServerReactor(int processorCount, Executor executor) {
		if (processorCount < 1) {
			throw new IllegalArgumentException("Must have at least one processor");
		}
		this.executor = executor;
		this.processorCount = processorCount;
		ioProcessors = new SocketIoProcessor[this.processorCount];

		for (int i = 0; i < processorCount; i++) {
			ioProcessors[i] = new SocketIoProcessor("SocketAcceptorIoProcessor-" + id + "." + i, executor);
		}

	}

	@Override
	public void bind(SocketAddress address, Handler handler) throws IOException {
		if (handler == null) {
			throw new NullPointerException("handler");
		}

		if (address != null && !(address instanceof InetSocketAddress)) {
			throw new IllegalArgumentException("Unexpected address type: " + address.getClass());
		}

		RegistrationRequest request = new RegistrationRequest(address, handler);

		synchronized (lock) {
			startupWorker();

			synchronized (registerQueue) {
				registerQueue.push(request);
			}

			selector.wakeup();
		}

		synchronized (request) {
			while (!request.done) {
				try {
					request.wait();
				} catch (InterruptedException e) {
					ExceptionMonitor.getInstance().exceptionCaught(e);
				}
			}
		}

		if (request.exception != null) {
			throw request.exception;
		}
	}

	private void startupWorker() throws IOException {
		synchronized (lock) {
			if (worker == null) {
				selector = Selector.open();
				worker = new Worker();

				executor.execute(new NamePreservingRunnable(worker, threadName));
			}
		}
	}

	private Selector getSelector() {
		synchronized (lock) {
			return this.selector;
		}
	}

	private static class RegistrationRequest {
		private InetSocketAddress address;

		private final Handler handler;

		private IOException exception;

		private boolean done;

		private RegistrationRequest(SocketAddress address, Handler handler) {
			this.address = (InetSocketAddress) address;
			this.handler = handler;
		}
	}

	private class Worker implements Runnable {

		@Override
		public void run() {
			Selector selector = getSelector();
			while (true) {
				try {
					int nKeys = selector.select();

					registerNew();

					if (nKeys > 0) {
						processSessions(selector.selectedKeys());
					}
				} catch (IOException e) {
					ExceptionMonitor.getInstance().exceptionCaught(e);
				}
			}
		}

		private void processSessions(Set<SelectionKey> selectedKeys) throws IOException {
			Iterator<SelectionKey> it = selectedKeys.iterator();
			while (it.hasNext()) {
				SelectionKey key = (SelectionKey) it.next();

				it.remove();

				if (!key.isAcceptable()) {
					continue;
				}

				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();

				SocketChannel ch = ssc.accept();

				if (ch == null) {
					continue;
				}

				boolean success = false;
				try {
					RegistrationRequest req = (RegistrationRequest) key.attachment();
					SocketSessionImpl session = new SocketSessionImpl(nextProcessor(), ch, req.handler, req.address);

					getFilterChainBuilder().buildFilterChain(session.getFilterChain());

					session.getIoProcessor().addNew(session);
					success = true;
				} catch (Throwable t) {
					ExceptionMonitor.getInstance().exceptionCaught(t);
				} finally {
					if (!success) {
						ch.close();
					}
				}
			}
		}

	}

	private SocketIoProcessor nextProcessor() {
		if (this.processorDistributor == Integer.MAX_VALUE) {
			this.processorDistributor = Integer.MAX_VALUE % this.processorCount;
		}

		return ioProcessors[processorDistributor++ % processorCount];
	}

	private void registerNew() {
		Selector selector = getSelector();

		while (true) {
			RegistrationRequest req;

			synchronized (registerQueue) {
				req = (RegistrationRequest) registerQueue.pop();
			}

			if (req == null) {
				break;
			}

			ServerSocketChannel ssc = null;

			try {
				ssc = ServerSocketChannel.open();
				ssc.configureBlocking(false);
				
				ssc.socket().setReuseAddress(true);
				if (req.address == null || req.address.getPort() == 0) {
					req.address = (InetSocketAddress) ssc.socket().getLocalSocketAddress();
				}
				ssc.socket().bind(req.address, DEFAULT_BACKLOG);

				ssc.register(selector, SelectionKey.OP_ACCEPT, req);

			} catch (IOException e) {
				req.exception = e;
			} finally {
				if (ssc != null && req.exception != null) {
					try {
						ssc.close();
					} catch (IOException e) {
						ExceptionMonitor.getInstance().exceptionCaught(e);
					}
				}
			}
		}
	}

}
