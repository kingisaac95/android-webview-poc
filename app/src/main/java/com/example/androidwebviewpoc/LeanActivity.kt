package com.example.androidwebviewpoc

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class LeanActivity : AppCompatActivity() {
    private lateinit var leanWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        leanWebView = findViewById(R.id.webview)
        leanWebView.webViewClient = LeanWebViewClient()

        leanWebView = WebView(this).apply {
            settings.javaScriptEnabled = true
        }

        val url = intent.getStringExtra("url")
        leanWebView.loadUrl(url!!)
    }

    override fun onPause() {
        super.onPause()
        leanWebView.onPause()
        leanWebView.pauseTimers()
    }

    override fun onResume() {
        super.onResume()
        leanWebView.resumeTimers()
        leanWebView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        leanWebView.destroy()
    }

    override fun onBackPressed() {
        // @TODO: close WebView with cancelled status
        /**
        if (leanWebView.canGoBack()) {
        leanWebView.goBack()
        } else {
        super.onBackPressed()
        }
         */
    }
}