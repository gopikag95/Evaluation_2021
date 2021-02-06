package com.robosoft.evaluation.newslistingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.robosoft.evaluation.newslistingapp.model.NewsArticle
import com.robosoft.evaluation.newslistingapp.repository.NewsListingRepository

class NewsListingViewModel (application: Application) : AndroidViewModel(application) {

    private val repository = NewsListingRepository(application)
    val showProgess : LiveData<Boolean>
    val newsList : LiveData<List<NewsArticle>>


    init {
        this.showProgess = repository.showProgess
        this.newsList = repository.newsList
    }

    fun getNewsData(s: String) {
        repository.getNewsList(s)

    }
}