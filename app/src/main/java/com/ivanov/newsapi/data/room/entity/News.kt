package com.ivanov.newsapi.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ivanov.newsapi.data.room.converters.DateConverter
import java.util.Date

@Entity(tableName = "news")
@TypeConverters(DateConverter::class)
data class News(
    @PrimaryKey val url: String,
    val title: String?,
    val description: String?,
    val urlToImg: String?,
    val date: Date
)