import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;
import com.mongodb.BasicDBObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Database { 
	public static String outputFilename;
	static List<String> list = new ArrayList<String>();
   
   public static void main( String args[] ) { 
	   BufferedWriter writer;
      
      // Creating a Mongo client 
      MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
      
      // Accessing the database 
      MongoDatabase historyDatabase = mongo.getDatabase("history");
      MongoCollection<Document> collect = historyDatabase.getCollection("Date");
      
      Document doc = new Document("title", "hello")
      .append("id", 1);
      Document doc2 = new Document("hello", "world")
    		  .append("age", 25);
      System.out.println(doc);
      collect.insertOne(doc);
      collect.insertOne(doc2);
      FindIterable<Document> iterDoc = collect.find();
      Iterator it = iterDoc.iterator();
      //JSONObject jobject = new JSONObject();
      //jobject = (JSONObject) it;
      while (it.hasNext()) {
    	  System.out.println(it.next());
      }
      //System.out.println(jobject);
      // json("hello")
      
      MongoIterable<String> mg = historyDatabase.listCollectionNames();
      MongoCursor<String> it1 = mg.iterator();
      
      //https://stackoverflow.com/questions/39923406/bson-document-to-json-in-java
      while (it1.hasNext()) {
    	  MongoCollection<Document> table = historyDatabase.getCollection(it1.next());
    	  
    	  for (Document doc3 : table.find()) {
    		  System.out.println(doc3.toJson());
    		  String js = doc3.toJson();
    		  //System.out.println(js);
    		  JsonParser parser = new JsonParser();
    		  keyValue("title", parser.parse(js));
    		  System.out.println(list.get(0));
    		  
    	  }
      }
      
      historyDatabase.drop();
            
      mongo.close();
      System.out.println("Closed MongoDB");
   } 
   
   private static void keyValue(String key, JsonElement jsonElement) {

       if (jsonElement.isJsonArray()) {
           for (JsonElement element : jsonElement.getAsJsonArray()) {
               keyValue(key, element);
           }
       } else {
           if (jsonElement.isJsonObject()) {
               Set<Map.Entry<String, JsonElement>> entrySet = jsonElement
                       .getAsJsonObject().entrySet();
               for (Map.Entry<String, JsonElement> entry : entrySet) {
                   String tempKey = entry.getKey();
                   if (tempKey.equals(key)) {
                       list.add(entry.getValue().toString());
                   }
                   keyValue(key, entry.getValue());
               }
           } else {
               if (jsonElement.toString().equals(key)) {
                   list.add(jsonElement.toString());
               }
           }
       }
   }
}
