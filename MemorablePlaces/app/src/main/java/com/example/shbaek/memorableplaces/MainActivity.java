package com.example.shbaek.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> myPlacesName;
    static ArrayList<LatLng> myPlacesLocation;
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myListView = findViewById(R.id.myListView);

        myPlacesName = new ArrayList<String>();
        myPlacesLocation = new ArrayList<LatLng>();

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.shbaek.memorableplaces", Context.MODE_PRIVATE);

        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();

        myPlacesLocation.clear();
        myPlacesName.clear();
        latitudes.clear();
        longitudes.clear();

        try {

            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats", ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lons", ObjectSerializer.serialize(new ArrayList<String>())));
            myPlacesName = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("myPlacesName", ObjectSerializer.serialize(new ArrayList<String>())));


        } catch(Exception e){
            e.printStackTrace();
        }

        if(myPlacesName.size() > 0 && latitudes.size() > 0 && longitudes.size() > 0) {
            if(myPlacesName.size() == latitudes.size() && myPlacesName.size() == longitudes.size()){
                for(int i=0; i< myPlacesName.size(); i++) {
                    myPlacesLocation.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));
                }
            }
        } else {
            myPlacesName.add("add New Memorable Places");
            myPlacesLocation.add(new LatLng(0,0));
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myPlacesName);

        myListView.setAdapter(arrayAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber", position);

                startActivity(intent);
            }
        });
        


    }
}
