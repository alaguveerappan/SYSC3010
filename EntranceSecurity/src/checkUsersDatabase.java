import com.mongodb.MongoClient;
import com.mongodb.client.*;

import org.bson.Document;

/*
 * Check what users are registered in the Database.
 */

public class checkUsersDatabase {

    public static void main( String args[] ) {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("security");

        MongoCollection<Document> pinCollect = db.getCollection("pin");

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
