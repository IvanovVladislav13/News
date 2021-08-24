package com.ivanov.newsapi.presentation.fragments.news_overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ivanov.newsapi.R
import com.ivanov.newsapi.databinding.FragmentNewsOverviewBinding

class NewsOverviewFragment : Fragment(R.layout.fragment_news_overview) {

    private val binding by viewBinding(FragmentNewsOverviewBinding::bind)
    private lateinit var webView: WebView
    private lateinit var url: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url = arguments?.getString("URL") ?: " "
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView = binding.webView
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBarWeb.visibility = View.INVISIBLE
            }
        }

        if (url.isNotBlank()) {
            webView.loadUrl(url)
            binding.progressBarWeb.visibility = View.VISIBLE
        }
    }
}