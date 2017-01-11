package service.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 *
 * 使用Spring的JmsTemplate方法发送和接受消息
 * 
 * @author Zhaolj
 * @version 1.0
 */
@Service
public class MsgTransportService {
	@Resource(name = "producerJmsTemplate")
	private JmsTemplate producerJmsTemplate;

	@Resource(name = "consumerJmsTemplate")
	private JmsTemplate consumerJmsTemplate;

	private String msg = "";
	private static final Logger _log = LoggerFactory.getLogger(MsgTransportService.class);

	public void producerAndConsumer() {
		sendMsg();
		receiveMsg();
	}

	/**
	 * 发送一条消息
	 */
	public void sendMsg() {

		try {
			producerJmsTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					MapMessage message = createMsg(session);
					return message;
				}
			});

		} catch (Exception e) {
			_log.error(this.getClass().getName() + "message send failure." + e.getMessage());
		}

	}

	/**
	 * 接受一条消息
	 */
	public void receiveMsg() {
		try {
			MapMessage message = (MapMessage) consumerJmsTemplate.receive();
			System.out.println(message.getObject("colours"));
		} catch (Exception e) {
			_log.error(this.getClass().getName() + "message receive failure." + e.getMessage());
		}
	}

	/*
	 * 构造发送的测试消息内容
	 */
	private MapMessage createMsg(Session session) throws JMSException {
		// 只发送字符串
		// TextMessage tmsg = session.createTextMessage(new String(msg));
		MapMessage msg = session.createMapMessage();
		msg.setIntProperty("id", 987654321);
		msg.setStringProperty("name", "demo");
		msg.setDoubleProperty("price", 0.6);

		List<String> colors = new ArrayList<String>();
		colors.add("red");
		colors.add("green");
		colors.add("white");
		msg.setObject("colours", colors);

		Map<String, Double> dimensions = new HashMap<String, Double>();
		dimensions.put("length", 3.0);
		dimensions.put("width", 4.0);
		dimensions.put("depth", 5.0);
		msg.setObject("dimensions", dimensions);

		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}
}
