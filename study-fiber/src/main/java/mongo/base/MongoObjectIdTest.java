package mongo.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoObjectIdTest {
	
	private MongoDatabase database = MongoConnector.getDatabase("study");
	
	@Test
	public void testObjectIdDelete() {
		MongoCollection<Document> collection = database.getCollection("book");
		FindIterable<Document> iterable = collection.find();
		List<ObjectId> list = new ArrayList<ObjectId>();
		Iterator<Document> iterator = iterable.iterator();
		while(iterator.hasNext()) {
			Document d = iterator.next();
			list.add(d.getObjectId("_id"));
			System.out.println(d.getObjectId("_id"));
			break;
		}
		Bson doc = Filters.in("_id", list);
		collection.deleteMany(doc);
	}
}
