package com.ivanov.newsapi.presentation.fragments.news.recycleview.holders

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ivanov.newsapi.data.room.entity.News
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item.view.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class NewsHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val image = view.item_news_image
    private val title = view.item_news_title
    private val description = view.item_news_description
    private val date = view.item_news_date

    @SuppressLint("SimpleDateFormat")
    fun bind(news: News?) {
        if (news?.urlToImg != null && news.urlToImg.isNotBlank()) {
            Picasso.get().load(news.urlToImg).into(image)
        }

        title.text = news?.title
        description.text = news?.description

        if (news?.date != null) {
            date.text = SimpleDateFormat("HH:mm dd.MM.yyyy")
                .format(news.date)
        }
    }
}