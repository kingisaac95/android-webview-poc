package com.example.androidwebviewpoc

import android.app.Activity
import android.content.Intent

class LeanKT private constructor(
    private val appToken: String?,
    private val isSandbox: Boolean,
    private val version: String,
    private val country: String,
    private val region: String,
    private val language: String,
    private val showLogs: Boolean,
) {
    private var activity: Activity? = null
    private val baseURL =
        "https://lean-loader-test.s3.amazonaws.com/lean-sdk.html"
//        "https://cdn.leantech.me/link/loader/prod/$region/$version/lean-sdk.html"

    fun connect(
        customerId: String,
        bankIdentifier: String?,
        paymentDestinationId: String?,
        failRedirectUrl: String?,
        successRedirectUrl: String?,
        permissions: ArrayList<UserPermissions> // customization: Customization
    ) {
        val permissionsParams = convertPermissionsToURLString(permissions)
        val initializationURL = StringBuilder(baseURLWithCommonQueryParams)
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

    private fun startFlow(url: String) {
        val intent = Intent(activity, LeanActivity::class.java)

        intent.putExtra("url", url)
        activity?.startActivity(intent)
    }

    // @TODO: add support for customization
    private fun convertPermissionsToURLString(permissions: ArrayList<UserPermissions>): String {
        val permissionsString = StringBuilder("")
        for (permission in permissions) {
            permissionsString.append("&permissions=").append(permission)
        }
        return permissionsString.toString()
    }

    private val baseURLWithCommonQueryParams: String
        get() = baseURL +
                "?implementation=webview-hosted-html" +
                "&region=" +
                region +
                "&sandbox=" +
                isSandbox +
                "&app_token=" +
                appToken +
                "&version" +
                version +
                "&country" +
                country +
                "&language" +
                language

    enum class UserPermissions(val permissionName: String) {
        IDENTITY("identity"),
        BALANCE("balance"),
        ACCOUNTS("accounts"),
        TRANSACTIONS("transactions"),
        PAYMENTS("payments")
    }

    enum class Country(val countryCode: String?) {
        SAUDI_ARABIA("sa"),
        UNITED_ARAB_EMIRATES("ae"),
    }

    // @TODO: Implement lean listener

    class Builder {
        private var appToken: String? = null
        private var country = "ArabEmirates"
        private var region = "ae"
        private var version = "latest"
        private var language = "en"
        private var isSandbox = false
        private var showLogs = false

        fun setAppToken(appToken: String?): Builder {
            this.appToken = appToken
            return this
        }

        fun setCountry(country: String): Builder {
            this.country = country
            return this
        }

        fun setRegion(region: String): Builder {
            this.region = region
            return this
        }

        fun setVersion(version: String): Builder {
            this.version = version
            return this
        }

        fun setLanguage(language: String): Builder {
            this.language = language
            return this
        }

        fun setSandboxMode(isSandbox: Boolean): Builder {
            this.isSandbox = isSandbox
            return this
        }

        fun setShowLogs(showLogs: Boolean): Builder {
            this.showLogs = showLogs
            return this
        }

        fun build(): LeanKT {
            if (appToken.isNullOrBlank()) {
                throw IllegalStateException("App ID must be set")
            }

            return LeanKT(
                appToken, isSandbox, version, country, region, language, showLogs
            )
        }
    }
}
