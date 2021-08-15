package com.ivanov.newsapi.presentation.fragments.news.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.ivanov.newsapi.R
import com.ivanov.newsapi.presentation.fragments.news.holders.LoaderStateHolder

class LoaderStateAdapter(
    private val context: Context?,
    private val retry: () -> Unit
) : LoadStateAdapter<LoaderStateHolder>() {

    override fun onBindViewHolder(holder: LoaderStateHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderStateHolder {
        return LoaderStateHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_news_loader, parent, false),
            retry
        )
    }
}