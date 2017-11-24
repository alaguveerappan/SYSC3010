package com.example.securityapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

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
        //create the list of items
        String[] myItems = {"blue", "green", "purple", "red"};

        //build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.da_logs, myItems);

        //configure the list view
        ListView list = (ListView) findViewById(R.id.listViewLogs);
        list.setAdapter(adapter);

    }
}
