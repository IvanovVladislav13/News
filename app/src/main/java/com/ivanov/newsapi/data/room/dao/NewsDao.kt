package com.ivanov.newsapi.data.room.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.ivanov.newsapi.data.room.entity.News

@Dao
interface NewsDao {
    @Query("SELECT * from news order by date desc")
    fun getAllNews(): PagingSource<Int, News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<News>)

    @Query("DELETE from news")
    suspend fun deleteAll()
}