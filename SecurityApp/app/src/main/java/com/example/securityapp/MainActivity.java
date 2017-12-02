package com.example.securityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /* Called when the use taps pin log button */
    public void viewLogs(View view){
        Intent intent = new Intent(this, PinLogActivity.class);
        startActivity(intent);
    }

    /* Called  when the user taps the add user button */
    public void addUser(View view){
        Intent intent2 = new Intent(this, AddUserActivity.class);
        startActivity(intent2);
    }



}
