package service.demo;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloServiceServer {

	/**
	 * 启动Thrift服务器
	 * 
	 * @param args
	 *            2015年11月24日 下午5:14:58
	 */
	public static void main(String[] args) {
		try {
			// 设置服务器的端口为7911
			TServerSocket serverTransport = new TServerSocket(7911);

			// 设置协议工厂为TBinaryProtocol.Factory
			Factory proFactory = new TBinaryProtocol.Factory();

			// 关联处理器和Hello服务的实现
			TProcessor processor = new Hello.Processor<Hello.Iface>(new HelloServiceImpl());

			Args arg = new Args(serverTransport);
			arg.processor(processor);
			arg.protocolFactory(proFactory);

			TServer server = new TThreadPoolServer(arg);
			System.out.println("Start server on port 7911...");
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		}

	}
}
