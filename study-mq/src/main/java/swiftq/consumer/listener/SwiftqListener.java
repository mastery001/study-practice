package swiftq.consumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import swiftq.consumer.handler.MessageHandler;
import swiftq.message.IMessage;

public class SwiftqListener {
	
	private final Logger logger = LoggerFactory.getLogger(SwiftqListener.class);
	
	private MessageHandler concreteHandler;
	
	public SwiftqListener(MessageHandler concreteHandler) {
		this.concreteHandler = concreteHandler;
	}

	public void handleMessage(IMessage message) {
		if(concreteHandler != null && concreteHandler.support(message)) {
			concreteHandler.handle(message);
		}
		logger.info("no handler to receive the message {}" , message );
	}
}
