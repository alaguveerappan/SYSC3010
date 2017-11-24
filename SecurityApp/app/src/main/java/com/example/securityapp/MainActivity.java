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

    /* Called when the user taps the delte user button */
    public void deleteUser(View view){
        Intent intent3 = new Intent(this, DeleteUserActivity.class);
        startActivity(intent3);
    }

    /* Called when the user taps the Check Sensor button */
    public void checkSensor(View view){
        Intent intent4 = new Intent(this, CheckSensorActivity.class);
        startActivity(intent4);
    }
}
