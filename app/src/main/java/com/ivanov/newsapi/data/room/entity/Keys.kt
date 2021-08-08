package com.ivanov.newsapi.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys")
data class Keys(@PrimaryKey val keyId: String, val nextKey: Int?, val prevKey: Int?)
