package com.example.androidwebviewpoc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

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

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView leanWebView = (WebView) findViewById(R.id.webview);
        leanWebView.setWebViewClient(new LeanWebViewClient());
        leanWebView.loadUrl(initializationUrl);

        WebSettings webSettings = leanWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    static class LeanWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            final Uri uri = Uri.parse(url);
            return handleOverrideUrlLoading(uri);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            final Uri uri = request.getUrl();
            return handleOverrideUrlLoading(uri);
        }

        // Handle redirections and respond appropriately
        private boolean handleOverrideUrlLoading(final Uri uri) {
            // scheme   - <scheme>://<host> leanlink | https | http
            // host     - <scheme>://<host> success | cancelled | failed (matching callback's response.status)
            // linkData - response data from redirect (matching callback's response object)

            if (Objects.equals(uri.getScheme(), "leanlink")) {
                String action = uri.getHost();
                HashMap<String, String> responseData = parseUriData(uri);

                if (Objects.equals(action, "success")) {
                    // User successfully completed the flow
                    Log.d("User connection status: ", responseData.get("status"));
                    Log.d("User connection message: ", responseData.get("message"));
                    Log.d("User connection exit point: ", responseData.get("exit_point"));
                    Log.d("User connection bank identifier: ", responseData.get("bank_identifier"));
                    Log.d("User connection secondary status: ", responseData.get("secondary_status"));

                    // @TODO: Transition the view at this point by closing the WebView
                } else if (Objects.equals(action, "failed")) {
                    // Error occurred during the flow
                    Log.d("User connection status: ", responseData.get("status"));
                    Log.d("User connection message: ", responseData.get("message"));
                    Log.d("User connection exit point: ", responseData.get("exit_point"));
                    Log.d("User connection bank identifier: ", responseData.get("bank_identifier"));
                    Log.d("User connection secondary status: ", responseData.get("secondary_status"));

                    // @TODO: Transition the view at this point by closing the WebView
                } else {
                    // Cancelled event
                    Log.d("Link action detected: ", action);
                }
                // Override URL loading
                return true;
            } else
                return Objects.equals(uri.getScheme(), "https") || Objects.equals(uri.getScheme(), "http");
        }

        // Parse a redirect URL query string into a HashMap for easy manipulation and access
        private HashMap<String, String> parseUriData(Uri uri) {
            final HashMap<String, String> uriData = new HashMap<>();

            for (final String key : uri.getQueryParameterNames()) {
                uriData.put(key, uri.getQueryParameter(key));
            }

            return uriData;
        }
    }
}
