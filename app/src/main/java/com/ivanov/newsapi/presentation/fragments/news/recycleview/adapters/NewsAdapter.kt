package com.ivanov.newsapi.presentation.fragments.news.recycleview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ivanov.newsapi.R
import com.ivanov.newsapi.data.room.entity.News
import com.ivanov.newsapi.presentation.fragments.news.recycleview.holders.NewsHolder

class NewsAdapter(private val context: Context?): PagingDataAdapter<News, NewsHolder>(diffCallback = DifferCallback) {

    private lateinit var mItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val item = getItem(position = position)

        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                if (item != null) {
                    mItemClickListener.onItemClick(item, position)
                }
            }
        }
        holder.bind(item)
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener){
        mItemClickListener = itemClickListener
    }
}

interface OnItemClickListener{
    fun onItemClick(news: News, position: Int)
}

private object DifferCallback : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}
