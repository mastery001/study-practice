package study.nio.reactor;

/**
 * 负责非堵塞行为,即read和write操作
 * 
 * @author zouziwen
 *
 *         2016年1月5日 下午2:46:22
 */
public interface Handler {

	void sessionCreated(Session session) throws Exception;

	void sessionOpened(Session session) throws Exception;

	void sessionClosed(Session session) throws Exception;

	void messageReceived(Session session, Object message) throws Exception;

	void messageSent(Session session, Object message) throws Exception;

	void exceptionCaught(Session session, Throwable cause);
}
