package com.example.androidwebviewpoc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Lean {
    private final String baseURL;
    private final String country;
    private final String region;
    private final String appToken;
    private final String version;
    private final String language;
    private final Boolean isSandbox;
    private final Boolean showLogs;

    private Activity activity = null;

    private Lean(String appToken, Boolean isSandbox, String version, String country, String region, String language, Boolean showLogs) {
        this.region = region;
        this.country = country;
        this.version = version;
        this.appToken = appToken;
        this.language = language;
        this.showLogs = showLogs;
        this.isSandbox = isSandbox;
        this.baseURL = "https://lean-loader-test.s3.amazonaws.com/lean-sdk.html";
    }

    public void link() {

    }

//    public void connect(String customerId, String bankIdentifier, String paymentDestinationId, String failRedirectUrl, String successRedirectUrl, ArrayList<UserPermissions> permissions
//                        // customization: Customization
//    ) {
//        String permissionsParams = this.convertPermissionsToURLString(permissions);
//        StringBuilder initializationURL = new StringBuilder(this.getBaseURLWithCommonQueryParams()).append("&method=connect").append("&sandbox=").append(this.isSandbox).append("&app_token=").append(this.appToken) // 9fb9e934-9efb-4e7e-a508-de67c0839be0
//                .append("&customer_id=").append(customerId) // 03bfa403-33b6-4ec5-96eb-28e822d1cdc1
//                .append("&bank_identifier=").append(bankIdentifier).append("&payment_destination_id=").append(paymentDestinationId).append("&fail_redirect_url=").append(failRedirectUrl).append("&success_redirect_url=").append(successRedirectUrl).append(permissionsParams);
//
//    }

    public void connect(
            String customerId, String bankIdentifier, String paymentDestinationId, String failRedirectUrl, String successRedirectUrl, ArrayList<UserPermissions> permissions
    ) {
        String permissionsParams = this.convertPermissionsToURLString(permissions);
        StringBuilder initializationURL = new StringBuilder(this.getBaseURLWithCommonQueryParams())
                .append("&method=connect")
                .append("&sandbox=")
                .append(isSandbox)
                .append("&app_token=")
                .append(appToken) // 9fb9e934-9efb-4e7e-a508-de67c0839be0
                .append("&customer_id=")
                .append(customerId) // 03bfa403-33b6-4ec5-96eb-28e822d1cdc1
                .append("&bank_identifier=")
                .append(bankIdentifier)
                .append("&payment_destination_id=")
                .append(paymentDestinationId)
                .append("&fail_redirect_url=")
                .append(failRedirectUrl)
                .append("&success_redirect_url=")
                .append(successRedirectUrl)
                .append(permissionsParams);

        startFlow(initializationURL.toString());
    }

    private void startFlow(String url) {
        Intent intent = new Intent(activity, LeanActivityy.class);

        intent.putExtra("url", url);
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    public void reconnect() {

    }

    public void createPaymentSource() {

    }

    public void createBeneficiary() {

    }

    public void pay() {

    }

    public void authorize() {

    }

    enum UserPermissions {
        identity, balance, accounts, transactions, payment,
        // @TODO: add KSA permissions
    }

    // @TODO: add support for customization

    private String convertPermissionsToURLString(ArrayList<UserPermissions> permissions) {
        StringBuilder permissionsString = new StringBuilder("");
        for (UserPermissions permission : permissions) {
            permissionsString.append("&permissions=").append(permission);
        }

        return permissionsString.toString();
    }

    private String getBaseURLWithCommonQueryParams() {
        return this.baseURL + "?implementation=webview-hosted-html" + "&region=" + this.region + "&sandbox=" + this.isSandbox + "&app_token=" + this.appToken + // 9fb9e934-9efb-4e7e-a508-de67c0839be0
                "&version" + this.version + "&country" + this.country + "&language" + this.language;
    }

    public static class Builder {
        private String appToken;
        private String country = "ArabEmirates"; // SaudiArabia
        private String region = "ae"; // sa
        private String version = "latest";
        private String language = "en"; // ar
        private Boolean isSandbox = false;
        private Boolean showLogs = false;

        public Builder setAppToken(String appToken) {
            this.appToken = appToken;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setSandboxMode(Boolean isSandbox) {
            this.isSandbox = isSandbox;
            return this;
        }

        public Builder setShowLogs(Boolean showLogs) {
            this.showLogs = showLogs;
            return this;
        }

        public Lean build() {
            return new Lean(this.appToken, this.isSandbox, this.version, this.country, this.region, this.language, this.showLogs);
        }
    }

    public class LeanActivityy extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_webview);

            WebView leanWebView = findViewById(R.id.webview);
            leanWebView.setWebViewClient(new LeanWebViewClientt());

            leanWebView = new WebView(this);
            WebSettings webSettings = leanWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            String url = getIntent().getStringExtra("url");
            leanWebView.loadUrl(url);
        }
    }

    static class LeanWebViewClientt extends WebViewClient {
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
