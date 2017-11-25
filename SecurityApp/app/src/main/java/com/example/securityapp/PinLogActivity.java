package com.example.securityapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

//Array of options --> ArrayAdapter --> ListView

//List View: {views: da_logs.xml}

public class PinLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_log);

        populateListView();

    }

    private void populateListView() {
        // Creating a Mongo client
        MongoClient mongo = new MongoClient( "172.17.133.195" , 27017 );
        //Create Database
        MongoDatabase db = mongo.getDatabase("myDb");
        //create collection
        MongoCollection<Document> col = db.getCollection("users");

        MongoCollection<Document> historyCollect = db.getCollection("history");
        FindIterable<Document> historyJson = historyCollect.find();

        //to fix andoird.os.NetWorkOnMainThreadExcetion
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String strDate= "";
        String strEntry= "";
        int count = 0;
        for(Document historyDoc : historyJson) {
            count = count +1;
        }

        String[] myItems = new String[count];

        int count2 = 0;
        for(Document historyDoc : historyJson) {
            if (historyDoc.get("date") != null) {
                myItems[count2] = historyDoc.getString("date");
            }
            if (historyDoc.get("entry") != null) {
                myItems[count2] = myItems[count2] +" / " + historyDoc.getBoolean("entry").toString();
            }
            if (historyDoc.get("name") != null){
                myItems[count2] = myItems[count2] + " / " + historyDoc.getString("name");
            }
            count2 = count2 +1;
        }




        //build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.da_logs, myItems);

        //configure the list view
        ListView list = (ListView) findViewById(R.id.listViewLogs);
        list.setAdapter(adapter);

        mongo.close();

    }
}
