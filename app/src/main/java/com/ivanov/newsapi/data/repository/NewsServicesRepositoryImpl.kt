package com.ivanov.newsapi.data.repository

import com.ivanov.newsapi.data.remote.entities.Article
import com.ivanov.newsapi.data.remote.network.NewsServices
import com.ivanov.newsapi.domain.repository.NewsServicesRepository
import retrofit2.awaitResponse

class NewsServicesRepositoryImpl(private val newsServices: NewsServices) : NewsServicesRepository {

    override suspend fun getNewsFromApi(page: Int): List<Article>? {
        return newsServices.getNewsList(page = page)
            .awaitResponse()
            .body()
            ?.articles
    }
}