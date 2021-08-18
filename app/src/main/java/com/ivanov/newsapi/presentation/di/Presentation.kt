package com.ivanov.newsapi.presentation.di

import androidx.paging.ExperimentalPagingApi
import com.ivanov.newsapi.presentation.fragments.map.MapViewModel
import com.ivanov.newsapi.presentation.fragments.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalPagingApi
val presentationModule = module {
    viewModel { NewsViewModel(get(), get(), get()) }

    viewModel {MapViewModel(get())}
}