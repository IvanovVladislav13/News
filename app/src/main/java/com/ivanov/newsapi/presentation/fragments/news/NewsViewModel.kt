package com.ivanov.newsapi.presentation.fragments.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.ivanov.newsapi.presentation.fragments.news.paging.NewsMediator
import com.ivanov.newsapi.data.room.AppRoomDatabase
import com.ivanov.newsapi.data.room.entity.News
import com.ivanov.newsapi.domain.repository.NewsServicesRepository
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class NewsViewModel(application: Application,
                    private val pagingConfig: PagingConfig,
                    private val newsServices: NewsServicesRepository,
                    private val database: AppRoomDatabase
) : AndroidViewModel(application) {

    fun fetchNews(): Flow<PagingData<News>>{
        val pagingSourceFactory = { database.getNewsDao().getAllNews() }

        return Pager(
            pagingConfig,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = NewsMediator(
                newsServices = newsServices,
                database = database
            )
        ).flow
            .cachedIn(viewModelScope)
    }
}
