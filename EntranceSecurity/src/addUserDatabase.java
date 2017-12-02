import com.mongodb.MongoClient;
import com.mongodb.client.*;

import org.bson.Document;

/*
 * Add new users to the database. Each user has a NAME and PIN
 * associated with them.
 */

public class addUserDatabase {

    public static void main( String args[] ) {
        
	/*
	 * Argument 1 = User's name
	 * Argument 2 = User's PIN
	 */
	String NAME = args[0];
        String PIN = args[1];

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("security");

        MongoCollection<Document> pinCollect = db.getCollection("pin");

	FindIterable<Document> pinIterator = pinCollect.find();
	for (Document checkDocument : pinIterator) {
		if (checkDocument.get("name").equals(NAME)) {
			System.out.println("User exists, exiting program");
			mongoClient.close();
			System.exit(0);
		}
	}

	/*
	 * Create document with pin and name and add it to the database
	 */
	Document validPin = new Document("pin", PIN).append("name", NAME);
	pinCollect.insertOne(validPin);

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
