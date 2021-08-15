package com.ivanov.newsapi.domain.models

data class Keys(
    val keyId: String,
    val nextKey: Int?,
    val prevKey: Int?
)
