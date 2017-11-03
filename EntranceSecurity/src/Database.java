import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase; 
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;

import org.json.JSONException;
import org.json.JSONObject;

public class Database { 
   
   public static void main( String args[] ) {  
      
      // Creating a Mongo client 
      MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
   
      // Creating Credentials 
      MongoCredential credential; 
      credential = MongoCredential.createCredential("sampleUser", "myDb", 
         "password".toCharArray()); 
      System.out.println("Connected to the database successfully");  
      
      // Accessing the database 
      MongoDatabase database = mongo.getDatabase("myDb"); 
      System.out.println("Credentials ::"+ credential); 
      
      //Create collection
      database.createCollection("sampleCollection");
      database.getCollection("sampleCollection");
      database.createCollection("sampleCollection2");
      database.getCollection("sampleCollection2");
      System.out.println("Collection created successfully");
      
      for (String name: database.listCollectionNames()) {
    	  System.out.println("Collection: " + name);
      }
      
      JSONObject json = new JSONObject();
      try {
		json.put("password", 1234);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      try {
		String message = json.get("password").toString();
		System.out.println(message);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      database.drop();
            
      mongo.close();
      System.out.println("Closed MongoDB");
   } 
}
