package com.ivanov.newsapi.presentation.fragments.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ivanov.newsapi.R
import com.ivanov.newsapi.databinding.FragmentWebViewBinding
import com.ivanov.newsapi.presentation.activities.MainActivity

class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private val mBinding by viewBinding(FragmentWebViewBinding::bind)
    private lateinit var mWebView: WebView
    private lateinit var url: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url = arguments?.getString("URL")!!
        initFloatButton()
        initWebView()
    }

    private fun initFloatButton() {
        mBinding.backButton.setOnClickListener{
            (activity as MainActivity).navController.navigate(R.id.action_webViewFragment_to_newsFragment)
        }
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
}