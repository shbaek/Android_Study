package com.example.shbaek.sharedprefrences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.shbaek.sharedprefrences", Context.MODE_PRIVATE);

        ArrayList<String> myTeam = new ArrayList<>();

        myTeam.add("step1");
        myTeam.add("step2");
        myTeam.add("step3");

        try {
            sharedPreferences.edit().putString("myTeam", ObjectSerializer.serialize(myTeam)).apply();
            Log.i("This is the myTeam : ", ObjectSerializer.serialize(myTeam));
        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<String> newTeam = new ArrayList<>();

        try{
            newTeam = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("myTeam", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch(Exception e){
            e.printStackTrace();
        }

        Log.i("This is the newTeam : ", newTeam.toString());

//        sharedPreferences.edit().putString("username", "shbaek").apply();

//        String username = sharedPreferences.getString("username","");

//        Log.i("This is the username : ", username);
    }
}
