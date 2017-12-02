import com.mongodb.MongoClient;
import com.mongodb.client.*;

/*
 * Drop existing collection(s) in database
 */

public class closeDatabase {

	public static void main( String args[] ) { 
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("security");

		db.drop();
		mongoClient.close();
	}
}
