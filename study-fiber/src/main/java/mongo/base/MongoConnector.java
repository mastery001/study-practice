package mongo.base;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConnector {
	
	private static MongoClient client;
	
	static {
		client = new MongoClient();
	}
	
	public static MongoDatabase getDatabase(String databaseName) {
		return client.getDatabase(databaseName);
	}
}
