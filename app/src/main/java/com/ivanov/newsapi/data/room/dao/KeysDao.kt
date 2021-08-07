package com.ivanov.newsapi.data.room.dao

import androidx.room.*
import com.ivanov.newsapi.data.room.entity.Keys

@Dao
interface KeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<Keys>)

    @Query("SELECT * FROM keys WHERE keyId = :id")
    suspend fun keysNewsById(id: String): Keys?

    @Query("DELETE FROM keys")
    suspend fun deleteAllKeys()
}