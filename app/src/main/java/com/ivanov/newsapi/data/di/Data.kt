package com.ivanov.newsapi.data.di

import androidx.room.Room
import com.ivanov.newsapi.data.remote.network.NetworkConfig
import com.ivanov.newsapi.data.remote.network.NewsServices
import com.ivanov.newsapi.data.repository.NewsServicesRepositoryImpl
import com.ivanov.newsapi.data.room.AppRoomDatabase
import com.ivanov.newsapi.data.room.DBConfig
import com.ivanov.newsapi.domain.repository.NewsServicesRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsServices::class.java)
    }

    single<NewsServicesRepository> { NewsServicesRepositoryImpl(get()) }

    single {
        Room.databaseBuilder(get(), AppRoomDatabase::class.java, DBConfig.DB_NAME).build()
    }

    single { get<AppRoomDatabase>().getNewsDao() }

    single { get<AppRoomDatabase>().getKeysDao() }
}