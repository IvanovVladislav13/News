package com.ivanov.newsapi.data.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ivanov.newsapi.data.room.entity.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY date DESC")
    fun getAllNews(): PagingSource<Int, News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<News>)

    @Query("DELETE FROM news")
    suspend fun deleteAll()
}