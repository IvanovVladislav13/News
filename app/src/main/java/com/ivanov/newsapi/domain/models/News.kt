package com.ivanov.newsapi.domain.models

import java.util.Date

data class News(
    val url: String,
    val title: String?,
    val description: String?,
    val urlToImg: String?,
    val date: Date
)