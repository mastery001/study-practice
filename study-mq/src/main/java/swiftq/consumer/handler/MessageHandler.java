package swiftq.consumer.handler;

import swiftq.message.IMessage;

public interface MessageHandler {

	public boolean support(IMessage message);
	
	public void handle(IMessage message);
}
