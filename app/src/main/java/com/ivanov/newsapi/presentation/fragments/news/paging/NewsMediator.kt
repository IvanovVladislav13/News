package com.ivanov.newsapi.presentation.fragments.news.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ivanov.newsapi.data.common.mappers.toEntity
import com.ivanov.newsapi.data.room.AppRoomDatabase
import com.ivanov.newsapi.data.room.entity.Keys
import com.ivanov.newsapi.data.room.entity.News
import com.ivanov.newsapi.domain.repository.NewsServicesRepository

@ExperimentalPagingApi
class NewsMediator(
    private val newsServices: NewsServicesRepository,
    private val database: AppRoomDatabase
) : RemoteMediator<Int, News>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, News>): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val key = getClosestKey(state)
                key?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.APPEND -> {
                val key = getLastKey(state)
                key?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = false)
            }
        }

        if (page > 5) {
            return MediatorResult.Success(endOfPaginationReached = true)
        }

        try {
            val articles = newsServices.getNewsFromApi(page)
            val response = articles?.map { it.toEntity() }
            val endOfPagination = response?.size!! < state.config.pageSize

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.getNewsDao().deleteAll()
                    database.getKeysDao().deleteAllKeys()
                }

                val prevKey = if (page == 1) null else page.minus(1)
                val nextKey = if (endOfPagination) null else page.plus(1)

                val keys = response.map {
                    Keys(
                        keyId = it.url,
                        nextKey = nextKey,
                        prevKey = prevKey
                    )
                }

                database.getKeysDao().insertAll(keys = keys)


                database.getNewsDao().insertNews(news = response)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getLastKey(state: PagingState<Int, News>): Keys? {
        return state.lastItemOrNull()?.let { news ->
            database.getKeysDao().keysNewsById(news.url)
        }
    }

    private suspend fun getClosestKey(state: PagingState<Int, News>): Keys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { id ->
                database.getKeysDao().keysNewsById(id)
            }
        }
    }
}