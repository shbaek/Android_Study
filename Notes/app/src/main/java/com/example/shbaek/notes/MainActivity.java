package com.example.shbaek.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    static ArrayList<String> myNotes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.shbaek.memorableplaces", Context.MODE_PRIVATE);
        ListView listView = findViewById(R.id.listView);


        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("myNotes", null);

        if(set == null) {
            myNotes.add("Example Note");
        } else {
            myNotes = new ArrayList<>(set);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myNotes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("noteId", position);
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int itemToDelete = position;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Set Delete Notes
                                myNotes.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();

                                HashSet<String> set = new HashSet<>(MainActivity.myNotes);
                                sharedPreferences.edit().putStringSet("myNotes", set).apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.addnotes) {
            Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
            startActivity(intent);

            return true;
        }

        return false;
    }
}
