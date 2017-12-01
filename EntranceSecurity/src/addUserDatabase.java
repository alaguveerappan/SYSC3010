import com.mongodb.MongoClient;
import com.mongodb.client.*;

import org.bson.Document;

public class addUserDatabase {

    public static void main( String args[] ) {
        String NAME = args[0];
        String PIN = args[1];

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("security");
        MongoCollection<Document> historyCollect = db.getCollection("pin");

        MongoCollection<Document> pinCollect = db.getCollection("pin");
		Document validPin = new Document(NAME, PIN);
		pinCollect.insertOne(validPin);

		FindIterable<Document> pinIterator = pinCollect.find();
		for (Document pinDocument : pinIterator) {
			if (pinDocument.get(NAME) != null) {
				System.out.println(pinDocument.get(NAME));
			}
		}

		mongoClient.close();

    }
}
