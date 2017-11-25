package com.example.securityapp;

//import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

//import com.fasterxml.jackson.annotation.JsonAnyGetter;
//import com.fasterxml.jackson.annotation.JsonAnySetter;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import java.util.HashMap;
//import java.util.Map;


public class AddUserActivity extends AppCompatActivity {

    public static final String userName = "com.example.SecurityApp.strUserName";
    public static final String userPIN = "com.example.SecurityApp.strUserPIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

    }

    public void addUser(View view){
        //creating a mongo client
        MongoClient mongo = new MongoClient( "172.17.133.195" , 27017 );
        //Create Database
        MongoDatabase database = mongo.getDatabase("myDb");
        //create collection
        MongoCollection<Document> collection = database.getCollection("users");
        //create document
        Document userPinDoc = new Document();


        EditText getUserName = (findViewById(R.id.enter_user));
        EditText getUserPin = (findViewById(R.id.enter_pin));

        String userName = getUserName.getText().toString();
        String userPin = getUserPin.getText().toString();

        Document newUser = new Document();
        newUser.put("name", userName);
        newUser.put("pin", userPin);

        collection.insertOne(newUser);

        //remove later
        database.drop();
        mongo.close();

        //userPinDoc.put(userName, userPin);

        Log.d("test", userName);
        Log.d("test2", userPin);


    }



}


