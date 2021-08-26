package com.ivanov.newsapi.presentation.di

import androidx.lifecycle.SavedStateHandle
import androidx.paging.ExperimentalPagingApi
import com.ivanov.newsapi.presentation.fragments.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalPagingApi
val presentationModule = module {
    viewModel { NewsViewModel(SavedStateHandle(), get(), get(), get()) }
}