package com.example.shbaek.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myListView = findViewById(R.id.myListView);

        final ArrayList<String> myTeam = new ArrayList<String>();

        myTeam.add("백승호");
        myTeam.add("김민조");
        myTeam.add("이랑교");
        myTeam.add("이명성");
        myTeam.add("조형우");
        myTeam.add("이정일");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myTeam);

        myListView.setAdapter(arrayAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(this,"안녕하세요~ " , Toast.LENGTH_SHORT).show();
//                Log.i("msg", "안녕하세요! " + myTeam.get(position));
                Toast.makeText(getApplicationContext(), "안녕하세요!  " + myTeam.get(position), Toast.LENGTH_LONG).show();
            }
        });


    }
}