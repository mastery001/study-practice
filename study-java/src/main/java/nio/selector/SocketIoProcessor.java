package nio.selector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class SocketIoProcessor {

	private Selector selector;
	
	private final Object lock = new Object();
	
	private final LinkedList<SocketSessionImpl> newSession = new LinkedList<SocketSessionImpl>();
	
	private final LinkedList<SocketSessionImpl> writeSession = new LinkedList<SocketSessionImpl>();
	
	void addNewSocket(SocketSessionImpl session) throws IOException {
		synchronized (newSession) {
			newSession.push(session);
		}
		if (socketWorker == null) {
			selector = Selector.open();
			socketWorker = new SocketWorker();
			new Thread(socketWorker).start();
		}
		selector.wakeup();
	}
	
	private Selector getSelector() {
		synchronized (lock) {
			return this.selector;
		}
	}
	
	void onWrite(SocketSessionImpl session) {
		if(scheduleWrite(session)) {
			selector.wakeup();
		}
	}

	private boolean scheduleWrite(SocketSessionImpl session) {
		synchronized (writeSession) {
			writeSession.push(session);
		}
		return true;
	}
	

	private void doWrite() {
		if(writeSession.isEmpty()) return;
		
		for(;;) {
			SocketSessionImpl session = null;
			
			synchronized (writeSession) {
				try {
					session = writeSession.pop();
				} catch (Exception e) {
					session = null;
				}
			}
			
			if(session == null) break;
			
			SelectionKey key = session.getSelectionKey();
			
			if(!key.isValid()) continue;
			
			try {
				doWrite(session);
			} catch (Exception e) {
				session.getHandler().exceptionCaught(session, e);
			}
			
		}
	}

	private void doWrite(SocketSessionImpl session) throws Exception {
		SocketChannel channel = session.getChannel();
		
		// 清除写标记
		SelectionKey key = session.getSelectionKey();
		key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
		
		LinkedList<ByteBuffer> writeQueue = session.getWriteRequestQueue();
		
		for(;;) {
			ByteBuffer writeBuffer = null;
			
			synchronized (writeQueue) {
				try {
					writeBuffer = writeQueue.peek();
				} catch (Exception e) {
					writeBuffer = null;
				}
			}
			
			if(writeBuffer == null) break;
			
			if(writeBuffer.remaining() == 0) {
				synchronized (writeQueue) {
					try {
						writeBuffer = writeQueue.pop();
					} catch (Exception e) {
					}
				}
				writeBuffer.reset();
				
				if(writeBuffer.hasRemaining()) {
					session.getHandler().messageSend(session, decode(writeBuffer));
					continue;
				}
			}
			
			channel.write(writeBuffer);
		}
	}

	private String decode(Object message){
		ByteBuffer in = (ByteBuffer) message;
		String receiveText = new String(in.array(), 0, in.capacity(), Charset.defaultCharset());
		int index = -1;
		if ((index = receiveText.lastIndexOf("\r\n")) != -1) {
			receiveText = receiveText.substring(0, index);
		}
		return receiveText;
	}
	

	private void doAddNew() {

		if (newSession.isEmpty())
			return;
		
		Selector selector = getSelector();
		SocketSessionImpl session = null;
		for (;;) {
			synchronized (newSession) {
				try {
					session = newSession.pop();
				} catch (Exception e) {
					session = null;
				}
			}
			
			if(session == null) break;

			try {
				session.getChannel().configureBlocking(false);
				session.setSelectionKey(session.getChannel().register(selector, SelectionKey.OP_READ , session));
				
				session.getHandler().sessionCreated(session);
			} catch (IOException e) {
			}
		}
	}

	
	private SocketWorker socketWorker;

	private class SocketWorker implements Runnable {

		@Override
		public void run() {
			Selector selector = getSelector();
			for (;;) {
				try {
					int keySize = selector.select(1000);
					
					doAddNew();
					
					if (keySize > 0) {
						process(selector.selectedKeys());
					}
					
					doWrite();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		private void process(Set<SelectionKey> selectedKeys) {
			Iterator<SelectionKey> it = selectedKeys.iterator();
			while (it.hasNext()) {
				SelectionKey key = it.next();
				SocketSessionImpl session = (SocketSessionImpl) key.attachment();
				if (key.isReadable()) {
					read(session);
				}
				if (key.isWritable()) {
					System.out.println("write request");
				}
			}
			selectedKeys.clear();
		}

		private void read(SocketSessionImpl session) {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			try {
				while (session.getChannel().read(buffer) > 0) {
				}
				buffer.flip();
				
				String msg = new String(buffer.array(), 0, buffer.capacity(), Charset.defaultCharset());
				
				session.getHandler().messageReceive(session, msg);

			} catch (Exception e) {
				session.getHandler().exceptionCaught(session, e);
			}finally {
				if(buffer != null) {
					buffer.clear();
				}
			}
		}

	}

}
