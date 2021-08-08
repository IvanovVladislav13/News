package com.ivanov.newsapi.presentation.fragments.news.recycleview.holders

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
    private val title = view.item_title
    private val description = view.item_news_description
    private val date = view.item_news_date

    fun bind(news: News?){
        if (news?.urlToImg != null && news.urlToImg.isNotBlank()) {
            Picasso.get().load(news.urlToImg).into(image)
        }

        title.text = news?.title
        description.text = news?.description

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date.text = news?.date?.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"))

        }

        // TODO
        val dateT: Date = Calendar.getInstance().time

        date.text = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())
            .format(dateT)

    }
}