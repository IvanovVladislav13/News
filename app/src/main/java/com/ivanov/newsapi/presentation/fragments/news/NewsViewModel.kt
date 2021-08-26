package com.ivanov.newsapi.presentation.fragments.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.ivanov.newsapi.data.room.AppRoomDatabase
import com.ivanov.newsapi.data.room.entity.News
import com.ivanov.newsapi.domain.repository.NewsServicesRepository
import com.ivanov.newsapi.presentation.fragments.news.paging.NewsMediator
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class NewsViewModel(
    savedStateHandle: SavedStateHandle,
    application: Application,
    private val newsServices: NewsServicesRepository,
    private val database: AppRoomDatabase
) : AndroidViewModel(application) {

    val newsState = savedStateHandle.get<Flow<PagingData<News>>>("news") ?: fetchNews()
    var newsPosition = savedStateHandle.get<Int>("newsPosition") ?: 0


    private fun fetchNews(): Flow<PagingData<News>> {
        val pagingSourceFactory = { database.getNewsDao().getAllNews() }

        return Pager(
            PagingConfig(
                pageSize = 5,
                prefetchDistance = 5,
                enablePlaceholders = true
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = NewsMediator(
                newsServices = newsServices,
                database = database
            )
        ).flow
            .cachedIn(viewModelScope)
    }
}
