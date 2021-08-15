package com.ivanov.newsapi.data.remote.entities

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)