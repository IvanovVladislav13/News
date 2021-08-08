package com.ivanov.newsapi

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.ivanov.newsapi.di.dataModule
import com.ivanov.newsapi.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    @ExperimentalPagingApi
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                dataModule,
                presentationModule
            )
        }
    }
}