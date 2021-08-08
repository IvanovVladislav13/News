package com.ivanov.newsapi.presentation.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ivanov.newsapi.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private var binding: ActivityWebViewBinding? = null
    private val mBinding get() = binding!!
    private lateinit var mWebView: WebView
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        url = intent.getStringExtra("URL")!!
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(){
        mWebView = mBinding.webView
        mWebView.settings.javaScriptEnabled = true

        mWebView.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }

        mWebView.loadUrl(url)
    }

    override fun onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack()
        }else{
            super.onBackPressed()
        }
    }
}