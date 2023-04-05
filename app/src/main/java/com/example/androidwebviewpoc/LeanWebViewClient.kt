package com.example.androidwebviewpoc

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class LeanWebViewClient : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        val uri = Uri.parse(url)
        return handleOverrideUrlLoading(uri)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
        val uri = request.url
        return handleOverrideUrlLoading(uri)
    }

    // Handle redirections and respond appropriately
    private fun handleOverrideUrlLoading(uri: Uri): Boolean {
        // scheme   - <scheme>://<host> leanlink | https | http
        // host     - <scheme>://<host> success | cancelled | failed (matching callback's response.status)
        // linkData - response data from redirect (matching callback's response object)
        return if (uri.scheme == "leanlink") {
            val action = uri.host
            val responseData = parseUriData(uri)

            when (action) {
                "success" -> {
                    // User successfully completed the flow
                    Log.d("User connection status: ", responseData["status"]!!)
                    Log.d("User connection message: ", responseData["message"]!!)
                    Log.d("User connection exit point: ", responseData["exit_point"]!!)
                    Log.d(
                        "User connection bank identifier: ",
                        responseData["bank_identifier"]!!
                    )
                    Log.d(
                        "User connection secondary status: ",
                        responseData["secondary_status"]!!
                    )

                    // @TODO: Transition the view at this point by closing the WebView
                }
                "failed" -> {
                    // Error occurred during the flow
                    Log.d("User connection status: ", responseData["status"]!!)
                    Log.d("User connection message: ", responseData["message"]!!)
                    Log.d("User connection exit point: ", responseData["exit_point"]!!)
                    Log.d(
                        "User connection bank identifier: ",
                        responseData["bank_identifier"]!!
                    )
                    Log.d(
                        "User connection secondary status: ",
                        responseData["secondary_status"]!!
                    )

                    // @TODO: Transition the view at this point by closing the WebView
                }
                else -> {
                    // Cancelled event
                    Log.d("Link action detected: ", action!!)
                }
            }
            // Override URL loading
            true
        } else uri.scheme === "https" || uri.scheme === "http"
    }

    // @TODO: handle onBackPressed
    // Parse a redirect URL query string into a HashMap for easy manipulation and access
    private fun parseUriData(uri: Uri): HashMap<String, String?> {
        val uriData = HashMap<String, String?>()
        for (key in uri.queryParameterNames) {
            uriData[key] = uri.getQueryParameter(key)
        }
        return uriData
    }
}
