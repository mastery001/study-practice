package study.io.block;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.junit.Test;

public class BlockIOTest {

	@Test
	public void testJIoSocket() throws Exception {
		ServerSocket serverSocket = new ServerSocket(10002);
		Socket socket = null;
		try {
			while (true) {
				socket = serverSocket.accept();
				System.out.println("socket连接：" + socket.getRemoteSocketAddress().toString());
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while (true) {
					String readLine = in.readLine();
					System.out.println("收到消息" + readLine);
					if ("end".equals(readLine)) {
						break;
					}
					// 客户端断开连接
					socket.sendUrgentData(0xFF);
				}
			}
		} catch (SocketException se) {
			System.out.println("客户端断开连接");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("socket关闭：" + socket.getRemoteSocketAddress().toString());
			socket.close();
		}
	}
}
