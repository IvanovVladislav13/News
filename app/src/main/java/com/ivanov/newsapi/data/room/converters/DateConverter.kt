package com.ivanov.newsapi.data.room.converters

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @SuppressLint("SimpleDateFormat")
    @TypeConverter
    fun toDate(date: Long): Date{
        return Date(date)
    }
}