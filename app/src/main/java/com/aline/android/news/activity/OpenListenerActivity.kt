package com.aline.android.news.activity

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.aline.android.news.R
import kotlinx.android.synthetic.main.activity_open_listener.*

class OpenListenerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_listener)

        val bundle = intent.extras
        if (bundle != null) {
            val urlNewsClicked = bundle.getString("urlWebView")
            openWebView(urlNewsClicked)
        }
    }

    private fun openWebView(urlNewsClicked: String?) {
        val webView = findViewById<WebView>(R.id.web_view)
        setWebViewClient(webView)
        webView?.loadUrl(urlNewsClicked)
        webView?.settings?.javaScriptEnabled =true
    }

    private fun setWebViewClient(webView: WebView?) {
         webView?.webViewClient = object : WebViewClient(){
             override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                 super.onPageStarted(view, url, favicon)
                 progress_web_view.visibility = View.VISIBLE
             }

             override fun onPageFinished(view: WebView?, url: String?) {
                 super.onPageFinished(view, url)
                 progress_web_view.visibility = View.GONE
             }
         }

    }
}
