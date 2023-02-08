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
    public String LEAN_SDK_BASE_URL = "https://lean-loader-test.s3.amazonaws.com/lean-sdk.html";

    String initializationUrl = LEAN_SDK_BASE_URL +
            "?env=ae03" + // Optional [defaults to prod]
            "&method=connect" +
            "&app_token=9fb9e934-9efb-4e7e-a508-de67c0839be0" +
            "&customer_id=03bfa403-33b6-4ec5-96eb-28e822d1cdc1" +
            "&permissions=balance" +
            "&permissions=identity" +
            "&permissions=accounts" +
            "&permissions=payments" +
            "&permissions=transactions" +
            "&sandbox=true";
            // "&callback='response => console.log(\"callback received:\", response)'"
            // @TODO: Implement redirect in place of callbacks

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leanWebview = (WebView) findViewById(R.id.webview);
        leanWebview.setWebViewClient(new WebViewClient());
        leanWebview.loadUrl(initializationUrl);

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
