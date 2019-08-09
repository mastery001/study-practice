package nio.selector;  
  
public interface IHandler {
	
	void sessionCreated(SocketSession session);
	
	void messageReceive(SocketSession session , Object message) throws Exception;
	
	void messageSend(SocketSession session , Object message) throws Exception;
	
	void exceptionCaught(SocketSession session , Throwable cause);
}
