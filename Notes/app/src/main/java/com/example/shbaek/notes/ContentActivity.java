package com.example.shbaek.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;

public class ContentActivity extends AppCompatActivity {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        EditText editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-1);

        if(noteId != -1) {
            editText.setText(MainActivity.myNotes.get(noteId));
        } else {
            MainActivity.myNotes.add("");
            noteId = MainActivity.myNotes.size() - 1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.myNotes.set(noteId, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.shbaek.memorableplaces", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.myNotes);
                sharedPreferences.edit().putStringSet("myNotes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
