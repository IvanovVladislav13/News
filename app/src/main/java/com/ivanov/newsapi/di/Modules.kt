package com.ivanov.newsapi.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.room.Room
import com.ivanov.newsapi.data.remote.network.NetworkConfig
import com.ivanov.newsapi.data.remote.network.NewsServices
import com.ivanov.newsapi.data.repository.NewsServicesRepositoryImpl
import com.ivanov.newsapi.data.room.AppRoomDatabase
import com.ivanov.newsapi.data.room.DBConfig
import com.ivanov.newsapi.domain.repository.NewsServicesRepository
import com.ivanov.newsapi.presentation.fragments.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
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

    single { NewsServicesRepositoryImpl(get()) }
    single<NewsServicesRepository> { NewsServicesRepositoryImpl(get()) }

    single {
        Room.databaseBuilder(get(), AppRoomDatabase::class.java, DBConfig.DB_NAME).build()
    }

    single { get<AppRoomDatabase>().getNewsDao() }

    single { get<AppRoomDatabase>().getKeysDao() }
}

@ExperimentalPagingApi
val presentationModule = module {
    viewModel { NewsViewModel(get(), get(), get(), get()) }

    single { PagingConfig(
        pageSize = 5,
        prefetchDistance = 5,
        enablePlaceholders = true
    ) }
}