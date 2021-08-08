package com.ivanov.newsapi.presentation.fragments.news.recycleview.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.ivanov.newsapi.R
import com.ivanov.newsapi.presentation.fragments.news.recycleview.holders.LoaderStateHolder

class LoaderStateAdapter(private val context: Context?,
                         private val retry: () -> Unit
): LoadStateAdapter<LoaderStateHolder>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: LoaderStateHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderStateHolder {
        return LoaderStateHolder(LayoutInflater.from(context).inflate(R.layout.item_news_loader, parent, false), retry)
    }
}