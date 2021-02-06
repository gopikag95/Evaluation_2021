package com.robosoft.evaluation.newslistingapp.viewmodel

import androidx.lifecycle.ViewModel
import com.robosoft.evaluation.newslistingapp.db.entities.NewsItem
import com.robosoft.evaluation.newslistingapp.repository.NewsItemDbRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsDbViewModel(
    private val repository: NewsItemDbRepository
) : ViewModel() {
    fun insert(item: NewsItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.insert(item)
    }

    fun delete(item: NewsItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getAllNewsItems() = repository.getAllNewsItems()

    fun count(position: Int) = CoroutineScope(Dispatchers.Main).launch {
        repository.count(position)
    }
}