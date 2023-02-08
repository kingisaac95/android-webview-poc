package com.example.androidwebviewpoc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView leanWebview;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leanWebview = (WebView) findViewById(R.id.webview);
        leanWebview.setWebViewClient(new WebViewClient());
        leanWebview.loadUrl("https://google.com");

        WebSettings webSettings = leanWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (leanWebview.canGoBack()) {
            leanWebview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}