package com.example.shbaek.showhide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//    Button showButton = findViewById(R.id.showButton);
//    Button hideButton = findViewById(R.id.hideButton);

    TextView textView;


    public void clickedShow(View view){
        textView.setVisibility(View.VISIBLE);
    }

    public void clickedHide(View view){
        textView.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
    }
}
