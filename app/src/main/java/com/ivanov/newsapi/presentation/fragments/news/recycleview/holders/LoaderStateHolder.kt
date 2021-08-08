package com.ivanov.newsapi.presentation.fragments.news.recycleview.holders

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news_loader.view.*

class LoaderStateHolder(view: View, retry: () -> Unit): RecyclerView.ViewHolder(view) {

    private val motionLayout: MotionLayout = view.motion_loader

    init {
        view.btn_retry.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState){

        when(loadState){
            LoadState.Loading -> {
                motionLayout.transitionToEnd()
            }
            else -> {
                motionLayout.transitionToStart()
            }
        }
    }
}