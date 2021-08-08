package com.ivanov.newsapi.data.common.mappers

import android.annotation.SuppressLint
import com.ivanov.newsapi.data.remote.entities.Article
import com.ivanov.newsapi.data.room.entity.News
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Article.toEntity() = News(
    url = url,
    title = title,
    description = description,
    urlToImg = urlToImage,
    date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(publishedAt)!!
)