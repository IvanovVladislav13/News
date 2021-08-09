package com.ivanov.newsapi.presentation.fragments.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
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
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        mWebView = mBinding.webView
        mWebView.settings.javaScriptEnabled = true

//        requireActivity().onBackPressedDispatcher.addCallback(
//            this
//        ){
//            (activity as MainActivity).navController.navigateUp()
//        }

        mWebView.webViewClient = object : WebViewClient() {
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