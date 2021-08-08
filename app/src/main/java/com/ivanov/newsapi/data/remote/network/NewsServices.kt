package com.ivanov.newsapi.data.remote.network

import com.ivanov.newsapi.data.remote.entities.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {
    @GET("everything")
    fun getNewsList(@Query("q") searchName: String = "ios",
                    @Query("from") from: String = "2019-04-00",
                    @Query("sortBy") sortBy: String = "publishedAt",
                    @Query("apiKey") apiKey: String = "26eddb253e7840f988aec61f2ece2907",
                    @Query("page") page: Int
    ): Call<NewsResponse>
}