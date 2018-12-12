package com.example.shbaek.newsreader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    String html;
    SQLiteDatabase sqLiteDatabase;
    String result = "";

    static ArrayList<String> titles = new ArrayList<>();
    static ArrayList<String> urls = new ArrayList<>();

    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteDatabase = this.openOrCreateDatabase("Articles", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS articles (id INTEGER PRIMARY KEY, articleid INT(10), title VARCHAR, url VARCHAR)");

        DownloadTask task = new DownloadTask();
        html = "";

        // downloading HTML page
        try {
//            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ListView listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("articleId", position);
                startActivity(intent);
            }
        });

        updateListView();
    }

    public void updateListView(){
        try{

            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM articles",null);

            int idIndex = c.getColumnIndex("id");
            int titleIndex = c.getColumnIndex("title");
            int urlIndex = c.getColumnIndex("url");

            if(c.moveToFirst()){
                titles.clear();
                urls.clear();

                do{
                    Log.i("News -id ", c.getString(idIndex));
                    Log.i("News -title ", c.getString(titleIndex));
                    Log.i("News -url ", c.getString(urlIndex));

                    titles.add(c.getString(titleIndex));
                    urls.add(c.getString(urlIndex));

                }while(c.moveToNext());

                arrayAdapter.notifyDataSetChanged();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            String articleInfo = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1){
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }

                JSONArray jsonArray = new JSONArray(result);

                int numberOfItems = 20;

                if(jsonArray.length() < 20){
                    numberOfItems = jsonArray.length();
                }

                sqLiteDatabase.execSQL("DELETE FROM articles");

                for(int i=0; i<numberOfItems; i++){
                    String articleId = jsonArray.getString(i);
                    url = new URL("https://hacker-news.firebaseio.com/v0/item/"+ articleId +".json?print=pretty");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    in = connection.getInputStream();
                    reader = new InputStreamReader(in);
                    data = reader.read();

                    articleInfo = "";

                    while(data != -1){
                        char current = (char)data;
                        articleInfo += current;
                        data = reader.read();
                    }

                    Log.i("ArticleInfo : ", articleInfo);

                    JSONObject jsonObject = new JSONObject(articleInfo);

                    if(!jsonObject.isNull("title") && !jsonObject.isNull("url")) {

                        String id = jsonObject.getString("id");
                        String titleInfo = jsonObject.getString("title");
                        String urlInfo = jsonObject.getString("url");

//                        Log.i("idInfo", idInfo);
                        Log.i("titleInfo", titleInfo);
                        Log.i("urlInfo", urlInfo);

                        String sql="INSERT OR REPLACE INTO articles (articleid, title, url) VALUES(?, ?, ?)";
                        sqLiteDatabase.execSQL(sql, new String[]{id, titleInfo,urlInfo});
                    }

                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            updateListView();
        }
    }
}
