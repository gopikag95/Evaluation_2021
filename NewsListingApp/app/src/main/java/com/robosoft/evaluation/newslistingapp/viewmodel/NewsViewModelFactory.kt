package com.robosoft.evaluation.newslistingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robosoft.evaluation.newslistingapp.repository.NewsItemDbRepository

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory (
    private var repository: NewsItemDbRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsDbViewModel(repository) as T
    }
}