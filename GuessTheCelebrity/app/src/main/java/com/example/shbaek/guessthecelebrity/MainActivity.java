package com.example.shbaek.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button btn1, btn2, btn3, btn4;
    ArrayList<String>  celebrity_image = new ArrayList<String>();
    ArrayList<String>  celebrity_name  = new ArrayList<String>();
    ArrayList<String> answers = new ArrayList<String>();
    int makeWrongAnswer, makeCorrectAnswer;
    int locationOfCorrectAnswer;

    public void checkedAnswer(View view){

        Log.i("CorrectAnswer : ", Integer.toString(locationOfCorrectAnswer));
        Log.i("Correct Tag #", view.getTag().toString());

        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            Toast.makeText(this, "정답!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "오답! 정답은 " + celebrity_name.get(makeCorrectAnswer) + "입니다.", Toast.LENGTH_SHORT).show();
        }

        makeProblems();
    }

    public void makeProblems() {

        Random rand = new Random();

        locationOfCorrectAnswer = rand.nextInt(4);
        makeCorrectAnswer = rand.nextInt(celebrity_name.size());
        answers.clear();

        for(int i=0; i<4; i++){
            if(i==locationOfCorrectAnswer){
                answers.add(celebrity_name.get(makeCorrectAnswer));
            } else {
                makeWrongAnswer = rand.nextInt(celebrity_name.size());

                if(makeWrongAnswer == locationOfCorrectAnswer){
                    makeWrongAnswer = rand.nextInt(celebrity_name.size());
                }
                answers.add(celebrity_name.get(makeWrongAnswer));
            }
        }

        btn1.setText(answers.get(0));
        btn2.setText(answers.get(1));
        btn3.setText(answers.get(2));
        btn4.setText(answers.get(3));


        // Print Image View Random Image
        ImageDownloader imageDownloader = new ImageDownloader();
        Bitmap randomCelebrityBitmapImg = null;

        try {
            randomCelebrityBitmapImg = imageDownloader.execute(celebrity_image.get(makeCorrectAnswer)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        imageView.setImageBitmap(randomCelebrityBitmapImg);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);

        DownloadHtml downloadHtml = new DownloadHtml();
        String html = "";

        // downloading HTML page
        try {
            html = downloadHtml.execute("http://www.posh24.se/kandisar").get();

            // Split HTML

            String[] splitHtml = html.split("<div class=\"listedArticles\">");

            // Parsing
            Pattern image = Pattern.compile("<img src=\"(.*?)\"");
            Matcher image_matcher = image.matcher(splitHtml[0]);


            while(image_matcher.find()){
                celebrity_image.add(image_matcher.group(1));
            }

            Pattern name = Pattern.compile("alt=\"(.*?)\"");
            Matcher name_matcher = name.matcher(splitHtml[0]);

            while(name_matcher.find()){
                 celebrity_name.add(name_matcher.group(1));
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        makeProblems();

    }

    public class DownloadHtml extends  AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            try {

                String result = "";
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1){
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return "Download HTML Faild!! ";
            }

        }
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream in = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);

                return bitmap;


            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
