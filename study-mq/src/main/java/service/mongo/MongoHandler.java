package service.mongo;

import swiftq.consumer.handler.MessageHandler;
import swiftq.message.IMessage;

public class MongoHandler implements MessageHandler{

	@Override
	public boolean support(IMessage message) {
		return message instanceof MongoMessage;
	}

	@Override
	public void handle(IMessage message) {
		
	}

}
