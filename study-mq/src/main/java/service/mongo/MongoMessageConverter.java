package service.mongo;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MongoMessageConverter implements MessageConverter{

	private static final String key = "mongoMessage";
	
	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		JSONObject data = new JSONObject();
		data.put(key, object);
		return session.createTextMessage(data.toJSONString());
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		if(message instanceof TextMessage) {
			return convertJsonToObject((TextMessage)message);
		}
		return message;
	}

	private Object convertJsonToObject(TextMessage message) throws JMSException {
		String jsonStr = message.getText();
		JSONObject json = JSON.parseObject(jsonStr);
		jsonStr = json.getString(key);
		return JSON.parseObject(jsonStr , MongoMessage.class);
	}

}
