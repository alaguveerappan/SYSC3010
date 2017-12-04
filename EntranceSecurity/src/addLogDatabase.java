import com.mongodb.MongoClient;
import com.mongodb.client.*;

import org.bson.Document;

/*
 * Add history logs to the database. The log includes the name of the
 * user of entry (if applicable), date and time of attempted entry, and
 * if the user's entry was successful or not.
 */
public class addLogDatabase {

	/*
	 * Main arguments:
	 * 	Name = name of user
	 * 	DateTime = day and time of attempted entry
	 * 	Valid = if entry permitted or not
	 */

	public static void main( String args[] ) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("security");
		MongoCollection<Document> historyCollect = db.getCollection("history");

		String Name = args[0];
		String DateTime = args[1];
		String Valid = args[2];

		/*
		 * Create an entry document of name, date and time, and
		 * entry.
		 */
		Document entryDoc = new Document("name", Name);
            	entryDoc.append("dateTime", DateTime);
		entryDoc.append("entry", Valid);
		historyCollect.insertOne(entryDoc);

		/*
		 * Print out name, dateTime, and entry if they exist in
		 * the database.
		 */
		FindIterable<Document> historyJson = historyCollect.find();
		for (Document historyDoc : historyJson) {
            		if (historyDoc.get("name") != null) {
				System.out.println(historyDoc.get("name"));
			}

			if (historyDoc.get("dateTime") != null) {
               			 System.out.println(historyDoc.get("dateTime"));
			}
			if (historyDoc.get("entry") != null) {
				System.out.println("Entry permitted: " + historyDoc.get("entry"));
			}

		}

		mongoClient.close();
	}
}
