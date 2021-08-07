package com.ivanov.newsapi.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ivanov.newsapi.data.room.converters.LocaleDateTimeConverter
import java.time.LocalDateTime

@Entity(tableName = "news")
@TypeConverters(LocaleDateTimeConverter::class)
data class News(@PrimaryKey val url: String,
                val title: String?,
                val description: String?,
                val urlToImg: String?,
                val date: LocalDateTime)