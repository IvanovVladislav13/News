package com.ivanov.newsapi.domain.repository

import com.ivanov.newsapi.data.remote.entities.Article

interface NewsServicesRepository {
    suspend fun getNewsFromApi(page: Int): List<Article>?
}