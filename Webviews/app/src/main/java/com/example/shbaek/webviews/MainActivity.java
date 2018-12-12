package com.example.shbaek.webviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("http://www.naver.com");

        webView.loadData("<html><body><h1>Hello world!!</h1><p>This is my cool WebSite</p></body></html>", "text/html", "UTF-8");
    }
}
