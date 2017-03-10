package nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class SocketChannelAcceptor {

	private Selector selector;
	
	private final Object lock = new Object();

	private SocketIoProcessor processor;

	public SocketChannelAcceptor() {
		processor = new SocketIoProcessor();
	}
	
	public void bind(SocketAddress address , IHandler handler) throws IOException {
		synchronized (this) {
			if (selector == null)
				selector = Selector.open();
		}
		
		RegisterRequest request = new RegisterRequest(handler , address);
		
		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.configureBlocking(false);
		channel.bind(address);
		channel.register(selector, SelectionKey.OP_ACCEPT , request);

		System.out.println("服务启动，监听端口：" + address.toString());

		new Thread(new AcceptWorker()).start();
		selector.wakeup();
	}
	
	private Selector getSelector() {
		synchronized (lock) {
			return this.selector;
		}
	}

	private class AcceptWorker implements Runnable {

		@Override
		public void run() {
			Selector selector = getSelector();
			for (;;) {
				try {
					int keySize = selector.select();

					if (keySize > 0) {
						process(selector.selectedKeys());
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private void process(Set<SelectionKey> set) throws IOException {
			Iterator<SelectionKey> it = set.iterator();
			while (it.hasNext()) {
				SelectionKey key = it.next();

				if (!key.isAcceptable())
					continue;

				ServerSocketChannel channel = (ServerSocketChannel) key.channel();
				
				SocketChannel socket = channel.accept();

				if (socket == null) {
					continue;
				}
				
				RegisterRequest request = (RegisterRequest) key.attachment();
				
				processor.addNewSocket(new SocketSessionImpl(socket, request.handler , processor , request.address));
			}
		}

	}

	private class RegisterRequest {
		
		IHandler handler;
		
		SocketAddress address;

		public RegisterRequest(IHandler handler, SocketAddress address) {
			super();
			this.handler = handler;
			this.address = address;
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		new SocketChannelAcceptor().bind(new InetSocketAddress(10002) , new IHandler() {
			
			@Override
			public void sessionCreated(SocketSession session) {
				System.out.println("session was create;" + session.getRemoteAddress().toString());
			}
			
			@Override
			public void messageSend(SocketSession session, Object message) throws Exception {
				System.out.println("message send :" + message);
			}
			
			@Override
			public void messageReceive(SocketSession session, Object message) throws Exception {
				System.out.println(message);
				String writeMessage = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ":" + message;
				session.write(writeMessage);
			}
			
			@Override
			public void exceptionCaught(SocketSession session, Throwable cause) {
				cause.printStackTrace();
			}
		});
	}
}
