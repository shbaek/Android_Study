package com.example.shbaek.convertcurrency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void convertCurrency(View view){

        EditText editText = (EditText)findViewById(R.id.editText);

        Double  result = new Double(0);



        try {
            result = Double.parseDouble(editText.getText().toString()) * 1280.81;
        } catch (Exception e) {
            e.printStackTrace();
        }

        String ret = String.format("%.2f", result);
        Toast.makeText(this, "Convert Euro to WON : " + ret + " WON", Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
