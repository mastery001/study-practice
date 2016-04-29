package study.io.block;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import study.nio.reactor.Handler;
import study.nio.reactor.Reactor;
import study.nio.reactor.Session;
import study.nio.reactor.filter.MessageCodecFilter;
import study.nio.reactor.socket.ServerReactor;

public class ServerReactorTest {

	@Test
	public void testBind() throws IOException {
		Reactor server = new ServerReactor();
		server.getFilterChain().addLast("codec", new MessageCodecFilter(Charset.forName("utf-8")));
		server.bind(new InetSocketAddress(10002), new Handler() {

			@Override
			public void sessionCreated(Session session) throws Exception {
				System.out.println("session was create;" + session.getRemoteAddress().toString());
			}

			@Override
			public void sessionOpened(Session session) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void sessionClosed(Session session) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void messageReceived(Session session, Object message) throws Exception {
				String str = message.toString();
				if (str.trim().equalsIgnoreCase("quit")) {
					return;
				}
				System.out
						.println("服务器端接受客户端" + session.getRemoteAddress().toString().substring(1) + "的数据--:" + message);
				session.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date()));
				System.out.println("Message written...");
			}

			@Override
			public void messageSent(Session session, Object message) throws Exception {
				System.out.println("message send :" + message);
			}

			@Override
			public void exceptionCaught(Session session, Throwable cause) {
				cause.printStackTrace();
			}

		});
	}
}
