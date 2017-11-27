import com.mongodb.MongoClient;
import com.mongodb.client.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.Document;

public class addLogDatabase {

	public static void main( String args[] ) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("security");
		MongoCollection<Document> historyCollect = db.getCollection("history");

		String Name = args[0];
		String DateTime = args[1];
		String Valid = args[2];

		Document entryDoc = new Document("name", Name)
            .append("dateTime", DateTime)
			.append("entry", Valid);
		historyCollect.insertOne(entryDoc);

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