package com.example.androidwebviewpoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //    public String LEAN_SDK_BASE_URL = "https://lean-loader-test.s3.amazonaws.com/lean-sdk.html";
//
//    String initializationUrl = LEAN_SDK_BASE_URL +
//            "?env=ae03" + // Optional [defaults to prod]
//            "&method=connect" +
//            "&app_token=9fb9e934-9efb-4e7e-a508-de67c0839be0" +
//            "&customer_id=03bfa403-33b6-4ec5-96eb-28e822d1cdc1" +
//            "&permissions=balance" +
//            "&permissions=identity" +
//            "&permissions=accounts" +
//            "&permissions=payments" +
//            "&permissions=transactions" +
//            "&sandbox=true";
//
//    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Lean.UserPermissions> permissions = new ArrayList<>();
        permissions.add(Lean.UserPermissions.identity);
        permissions.add(Lean.UserPermissions.accounts);
        permissions.add(Lean.UserPermissions.transactions);
        permissions.add(Lean.UserPermissions.balance);
        permissions.add(Lean.UserPermissions.payment);

        final Lean lean = new Lean.Builder()
                .setAppToken("9fb9e934-9efb-4e7e-a508-de67c0839be0")
                .setVersion("latest")
                .setSandboxMode(true)
                .setShowLogs(true)
                .setLanguage("en")
//                .setCountry(Lean.Country.UNITED_ARAB_EMIRATES.getCountryCode())
                .setRegion("ae")
                .build();

        lean.connect("03bfa403-33b6-4ec5-96eb-28e822d1cdc1", "", "", "", "", permissions);
    }
}
