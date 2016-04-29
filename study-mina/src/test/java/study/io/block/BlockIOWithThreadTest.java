package study.io.block;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import org.junit.Test;

public class BlockIOWithThreadTest {

	@SuppressWarnings("resource")
	@Test
	public void testMultiThreadJIOSocket() throws Exception {
		ServerSocket server = new ServerSocket(10002);
		Thread thread = new Thread(new Accptor(server));
		thread.start();
		Scanner scanner = new Scanner(System.in);
		scanner.next();
	}

	private class Accptor implements Runnable {

		ServerSocket server;

		Accptor(ServerSocket server) {
			this.server = server;
		}

		@Override
		public void run() {

			while (true) {
				Socket socket = null;
				try {
					socket = server.accept();
					if (socket != null) {
						System.out.println("收到了socket：" + socket.getRemoteSocketAddress().toString());
						new Thread(new Processor(socket)).start();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class Processor implements Runnable {

		Socket socket;

		Processor(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String readLine;
				while(true) {
					readLine = reader.readLine();
					System.out.println(socket.getRemoteSocketAddress().toString() + "发送消息：" + readLine);
                    if("end".equals(readLine))
                    {
                        break;
                    }
                    //客户端断开连接
                    socket.sendUrgentData(0xFF);
                   // Thread.sleep(5000);
				}
			}catch(SocketException e) {
				System.out.println("客户端关闭了连接");
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				if(socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}
