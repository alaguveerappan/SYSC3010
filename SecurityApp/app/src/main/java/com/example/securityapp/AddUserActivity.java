package com.example.securityapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import org.bson.Document;

public class AddUserActivity extends AppCompatActivity {

    public static final String userName = "com.example.SecurityApp.strUserName";
    public static final String userPIN = "com.example.SecurityApp.strUserPIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
    }

    /*
    Runs when the submit button is activated.
    creates a mongo client and connects to PIN database on the
     */

    public void addUser(View view){
        //creating a mongo client
        MongoClient mongo = new MongoClient( "192.168.43.200" , 27017 );
        //Create Database
        MongoDatabase db = mongo.getDatabase("security");
        //create collection
        MongoCollection<Document> pinCollect = db.getCollection("pin");

        //obtain the user inputs for username and pin
        EditText getUserName = (findViewById(R.id.enter_user));
        EditText getUserPin = (findViewById(R.id.enter_pin));

        //convert the inputs to type string
        String userName = getUserName.getText().toString();
        String userPin = getUserPin.getText().toString();

        //create a new document to place the username and pin inputs
        Document validPin = new Document();
        validPin.put("name", userName);
        validPin.put("pin", userPin);

        //append the new user in the collection that stores the users and their
        //specific pins
        pinCollect.insertOne(validPin);

        mongo.close();

    }

}


