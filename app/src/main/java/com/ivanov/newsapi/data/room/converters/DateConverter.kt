package com.ivanov.newsapi.data.room.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromDate(date: Date): String {
        return date.toString()
    }

    @TypeConverter
    fun toDate(date: String): Date{
        return SimpleDateFormat("yyyy.MM.dd'T'HH:mm:ss", Locale.getDefault()).parse(date)!!
    }
}