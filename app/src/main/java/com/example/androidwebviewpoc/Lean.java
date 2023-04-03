package com.example.androidwebviewpoc;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Lean {
    private final String baseURL;
    private final String country;
    private final String region;
    private final String appToken;
    private final String version;
    private final String language;
    private final Boolean isSandbox;
    private final Boolean showLogs;

    private Lean(Builder builder) {
        this.region = builder.region;
        this.country = builder.country;
        this.version = builder.version;
        this.appToken = builder.appToken;
        this.language = builder.language;
        this.showLogs = builder.showLogs;
        this.isSandbox = builder.isSandbox;
        this.baseURL = "https://cdn.leantech.me/link/loader/prod/" + builder.region + "/" + builder.version + "/lean-sdk.html";
    }

    public void link() {

    }

    public void connect(
            String customerId,
            String bankIdentifier,
            String paymentDestinationId,
            String failRedirectUrl,
            String successRedirectUrl,
            ArrayList<UserPermissions> permissions
            // customization: Customization
    ) {
        String permissionsParams = this.convertPermissionsToURLString(permissions);
        StringBuilder initializationURL = new StringBuilder(this.getBaseURLWithCommonQueryParams())
                .append("&method=connect")
                .append("&sandbox=")
                .append(this.isSandbox)
                .append("&app_token=")
                .append(this.appToken) // 9fb9e934-9efb-4e7e-a508-de67c0839be0
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
        identity,
        balance,
        accounts,
        transactions,
        payment,
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
        return this.baseURL +
                "&implementation=webview-hosted-html" +
                "?region=" +
                this.region +
                "&sandbox=" +
                this.isSandbox +
                "&app_token=" +
                this.appToken + // 9fb9e934-9efb-4e7e-a508-de67c0839be0
                "&version" +
                this.version +
                "&country" +
                this.country +
                "&language" +
                this.language;
    }

    public static class Builder {
        private String appToken; // SaudiArabia
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

        public Builder setIsSandbox(Boolean isSandbox) {
            this.isSandbox = isSandbox;
            return this;
        }

        public Builder setShowLogs(Boolean showLogs) {
            this.showLogs = showLogs;
            return this;
        }

        public Lean build() {
            return new Lean(this);
        }
    }
}
