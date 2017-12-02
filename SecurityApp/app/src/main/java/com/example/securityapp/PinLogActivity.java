package com.example.securityapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;

import org.bson.Document;

//Array of options --> ArrayAdapter --> ListView

//List View: {views: da_logs.xml}

public class PinLogActivity extends AppCompatActivity {

    @Override
    //main
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_log);
        Log.d("test1", "oncreate" );
        populateListView();

    }

    private void populateListView() {
        // Creating a Mongo client
        MongoClient mongoClient  = new MongoClient( "192.168.43.200" , 27017 );
        //Create Database
        MongoDatabase db = mongoClient.getDatabase("security");
        //create collection
        MongoCollection<Document> historyCollect = db.getCollection("history");

        //create an iterator to iterate over the documents in the historyCollect collection
        FindIterable<Document> historyJson = historyCollect.find();

        //to fix andoird.os.NetWorkOnMainThreadExcetion
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //iterate through the collection once to determine the size of the collection
        int count = 0;
        for(Document historyDoc : historyJson) {
            count = count + 1;
        }

        //create an empty string array of size of the collection
        //string array will be used to output to the ListView
        String[] myItems = new String[count];

        //iterate through the collection the second time to fill the string array
        int count2 = 0;
        for(Document historyDoc : historyJson) {
            //searches through the collection for documents with the key specified and
            //fills the string array to be outputted to the ListView
            if (historyDoc.get("dateTime") != null) {
                myItems[count2] = historyDoc.getString("dateTime");
            }
            if (historyDoc.get("entry") != null) {
                myItems[count2] = myItems[count2] +" / " + historyDoc.getString("entry");
            }
            if (historyDoc.get("name") != null){
                myItems[count2] = myItems[count2] + " / " + historyDoc.getString("name");
            }
            count2 = count2 +1;
        }

        //build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.da_logs, myItems);

        //configure the list view and displays it on the screen
        ListView list = (ListView) findViewById(R.id.listViewLogs);
        list.setAdapter(adapter);

        //closes the mongo client
        mongoClient.close();

    }
}
