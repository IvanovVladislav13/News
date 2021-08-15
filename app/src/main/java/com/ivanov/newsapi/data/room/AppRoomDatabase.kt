package com.ivanov.newsapi.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ivanov.newsapi.data.room.dao.KeysDao
import com.ivanov.newsapi.data.room.dao.NewsDao
import com.ivanov.newsapi.data.room.entity.Keys
import com.ivanov.newsapi.data.room.entity.News

@Database(entities = [News::class, Keys::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
    abstract fun getKeysDao(): KeysDao
}