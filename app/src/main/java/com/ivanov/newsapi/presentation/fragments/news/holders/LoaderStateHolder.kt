package com.ivanov.newsapi.presentation.fragments.news.holders

import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ivanov.newsapi.databinding.ItemNewsLoaderBinding

class LoaderStateHolder(view: View, retry: () -> Unit) : RecyclerView.ViewHolder(view) {

    private val binding by viewBinding(ItemNewsLoaderBinding::bind)

    init {
        binding.btnRetry.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        when (loadState) {
            LoadState.Loading -> {
                binding.motionLoader.transitionToEnd()
            }
            else -> {
                binding.motionLoader.transitionToStart()
            }
        }
    }
}