import com.mongodb.MongoClient;
import com.mongodb.client.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.Document;

public class Database {
   
	public static void main( String args[] ) { 
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("security");
		MongoCollection<Document> collect = db.getCollection("history");
		MongoIterable<String> strings = mongoClient.listDatabaseNames();
		MongoCursor<String> iterator = strings.iterator();
		Document doc = new Document("Date", "Monday")
				.append("name", "kien");
		collect.insertOne(doc);
		
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		//System.out.println(ft.format(date));
		Document doc2 = new Document("Date", ft.format(date));
		collect.insertOne(doc2);
		
		FindIterable<Document> doc3 = collect.find();
		for (Document doc4 : doc3) {
			if (doc4.get("Date") != null) {
				System.out.println(doc4.get("Date"));
			}
		}
		db.drop();
		mongoClient.close();
	}
}
