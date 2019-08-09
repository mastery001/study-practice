package service.mongo;

import swiftq.message.IMessage;

public interface MongoMessage extends IMessage{
	
	enum Operation {
		ADD , UPDATE , DELETE
	}
	
	Operation operation();
	
	Object target();
}
