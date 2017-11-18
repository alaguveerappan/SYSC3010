import com.mongodb.MongoClient;
import com.mongodb.client.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.Document;

public class Database {
   
	public static void main( String args[] ) { 
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("security");
		MongoCollection<Document> historyCollect = db.getCollection("history");
		
		Document doc = new Document("date", "Monday")
				.append("name", "kien");
		historyCollect.insertOne(doc);
		
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		Document entryDoc = new Document("date", ft.format(date))
				.append("entry", false);
		historyCollect.insertOne(entryDoc);
		
		FindIterable<Document> historyJson = historyCollect.find();
		for (Document historyDoc : historyJson) {
			if (historyDoc.get("date") != null) {
				System.out.println(historyDoc.get("date"));
			}
			if (historyDoc.get("entry") != null) {
				System.out.println("Entry permitted: " + historyDoc.get("entry"));
			}
			if (historyDoc.get("name") != null) {
				System.out.println(historyDoc.get("name"));
			}
		}
		
		MongoCollection<Document> pinCollect = db.getCollection("pin");
		Document validPin = new Document("pin", 1234);
		pinCollect.insertOne(validPin);
		
		FindIterable<Document> doc5 = pinCollect.find();
		for (Document doc4 : doc5) {
			if (doc4.get("pin") != null) {
				System.out.println(doc4.get("pin"));
			}
		}
		
		db.drop();
		mongoClient.close();
	}
}
