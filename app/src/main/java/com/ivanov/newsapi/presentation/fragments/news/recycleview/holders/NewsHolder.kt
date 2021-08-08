package com.ivanov.newsapi.presentation.fragments.news.recycleview.holders

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ivanov.newsapi.data.room.entity.News
import com.ivanov.newsapi.databinding.NewsItemBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item.view.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class NewsHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding by viewBinding(NewsItemBinding::bind)

    @SuppressLint("SimpleDateFormat")
    fun bind(news: News?) = with(binding){
        if (news?.urlToImg != null && news.urlToImg.isNotBlank()) {
            Picasso.get().load(news.urlToImg).into(itemNewsImage)
        }

        itemNewsTitle.text = news?.title
        itemNewsDescription.text = news?.description

        if (news?.date != null) {
            itemNewsDate.text = SimpleDateFormat("HH:mm dd.MM.yyyy")
                .format(news.date)
        }
    }
}