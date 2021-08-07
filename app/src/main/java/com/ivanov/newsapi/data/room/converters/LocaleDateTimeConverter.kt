package com.ivanov.newsapi.data.room.converters

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocaleDateTimeConverter {
    @TypeConverter
    fun fromLocaleDateTime(date: LocalDateTime): String {
        return date.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocaleDateTime(date: String): LocalDateTime{
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}