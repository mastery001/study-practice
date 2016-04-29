package study.io.block;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.junit.Test;

public class NIONonBlockSelectorTest {

	private Selector selector;

	private ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);

	@Test
	public void testNioNonBlockSelector() throws Exception {
		selector = Selector.open();
		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.bind(new InetSocketAddress(10002));
		channel.configureBlocking(false);
		channel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			selector.select();
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				handleKey(key);
			}
		}
	}

	private void handleKey(SelectionKey key) throws IOException {
		ServerSocketChannel server = null;
		SocketChannel client = null;

		if (key.isAcceptable()) {
			server = (ServerSocketChannel) key.channel();
			client = server.accept();
			System.out.println("客户端： " + client.socket().getRemoteSocketAddress().toString().substring(1));
			client.configureBlocking(false);
			client.register(selector, SelectionKey.OP_READ );
		}
		if(key.isReadable()) {
			client = (SocketChannel)key.channel();
			receiveBuffer.clear();
			int count = client.read(receiveBuffer);
			if(count > 0) {
				String receiveText = new String(receiveBuffer.array() , 0 , count);
				receiveText = receiveText.substring(0,receiveText.indexOf("\r\n"));
				System.out.println("服务器端接受客户端"+client.socket().getRemoteSocketAddress().toString().substring(1)+"的数据--:" + receiveText);  
                client.register(selector, SelectionKey.OP_READ);  
			}
		}
	}
}
