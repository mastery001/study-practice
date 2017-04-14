package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;

import javax.jms.*;

public class ActiveMQDemo {

	Connection getConnection() throws JMSException {
		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");

			Connection connection = factory.createConnection();
			connection.start();
			return connection;
		} catch (JMSException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void testQueue() throws Exception {

		Connection connection = getConnection();

		Queue queue = new ActiveMQQueue("testQueue");

		final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Message message = session.createTextMessage("Hello JMS!");

		MessageProducer producer = session.createProducer(queue);
		producer.send(message);

		System.out.println("Send Message Completed");

		MessageConsumer consumer = session.createConsumer(queue);

		consumerReceiveByListener(consumer, new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					System.out.println(((TextMessage) message).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

	}

	void consumerReceive(MessageConsumer consumer, MessageListener listener) throws JMSException {
		// 消息等待接收，阻塞方式
		Message recvMessage = consumer.receive();
		listener.onMessage(recvMessage);
	}

	void consumerReceiveByListener(MessageConsumer consumer, MessageListener listener) throws JMSException {
		// 消息接收，监听非阻塞
		consumer.setMessageListener(listener);
	}

	/**
	 * 发布订阅模式，使用topic定义该model
	 * 
	 * @throws Exception
	 *             2016年5月4日 下午2:01:59
	 */
	@Test
	public void testTopic() throws Exception {

		Connection connection = getConnection();

		Topic topic = new ActiveMQTopic("testTopic");

		final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// 注册两个消费者，订阅同一个topic
		MessageConsumer consumer1 = session.createConsumer(topic);
		consumerReceiveByListener(consumer1, new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					System.out.println("consumer1" + ((TextMessage) message).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		MessageConsumer consumer2 = session.createConsumer(topic);
		consumerReceiveByListener(consumer2, new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					System.out.println("consumer2" + ((TextMessage) message).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		// 一个生产者负责生产消息
		MessageProducer producer = session.createProducer(topic);
		for (int i = 0; i < 10; i++) {
			producer.send(session.createTextMessage("Message:" + i));
		}
	}
}
