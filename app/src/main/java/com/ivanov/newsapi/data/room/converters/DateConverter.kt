package com.ivanov.newsapi.data.room.converters

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromDate(date: Date): String {
        return date.toString()
    }

    @SuppressLint("SimpleDateFormat")
    @TypeConverter
    fun toDate(date: String): Date{
        return SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ROOT).parse(date)!!
    }
}