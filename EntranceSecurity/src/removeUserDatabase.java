import com.mongodb.MongoClient;
import com.mongodb.client.*;

import org.bson.Document;

public class removeUserDatabase {

    public static void main( String args[] ) {
        String NAME = args[0];
        String PIN = args[1];

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("security");

        MongoCollection<Document> pinCollect = db.getCollection("pin");
		Document validPin = new Document("pin", PIN).append("name", NAME);
		pinCollect.deleteOne(validPin);

		FindIterable<Document> pinIterator = pinCollect.find();
		for (Document pinDocument : pinIterator) {
			if (pinDocument.get("pin") != null) {
				System.out.println(pinDocument.get("pin"));
			}
            if (pinDocument.get("name") != null) {
				System.out.println(pinDocument.get("name"));
			}
		}

		mongoClient.close();

    }
}
