import com.mongodb.MongoClient;
import com.mongodb.client.*;

public class closeDatabase {

	// Purpose of this file is to close to database.
	public static void main( String args[] ) { 
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("security");

		db.drop();
		mongoClient.close();
	}
}