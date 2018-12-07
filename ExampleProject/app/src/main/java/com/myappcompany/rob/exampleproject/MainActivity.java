package com.myappcompany.rob.exampleproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    public void clickFunction(View view){
        EditText nameEditText;
        EditText passwordEditText;

        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Log.i("info", "#############################");
        Log.i("username", nameEditText.getText().toString());
        Log.i("password", passwordEditText.getText().toString());
        Log.i("info", "#############################");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
