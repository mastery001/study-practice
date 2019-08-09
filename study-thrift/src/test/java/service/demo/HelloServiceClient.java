package service.demo;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class HelloServiceClient {

	/**
	 * HelloService的客户端
	 * 
	 * @param args
	 *            2015年11月24日 下午5:32:33
	 */
	public static void main(String[] args) {
		TTransport transport = new TSocket("localhost", 7911);
		try {
			transport.open();
			// 设置传输协议为 TBinaryProtocol
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			Hello.Client client = new Hello.Client(protocol);
			// 调用client服务的helloVoid方法
			boolean flag = client.helloBoolean(true);
			System.out.println(flag);
			transport.close();
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
}
