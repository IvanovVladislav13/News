package com.ivanov.newsapi.data.remote.entities

import android.os.Build
import androidx.annotation.RequiresApi
import com.ivanov.newsapi.data.room.entity.News
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(

    val source: Source,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String
)

data class Source(

    val id: String?,
    val name: String?
)