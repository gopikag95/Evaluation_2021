package com.robosoft.evaluation.newslistingapp.repository

import com.robosoft.evaluation.newslistingapp.db.NewsItemDatabase
import com.robosoft.evaluation.newslistingapp.db.entities.NewsItem

class NewsItemDbRepository(
    private val db: NewsItemDatabase
) {
    suspend fun insert(item: NewsItem) = db.getNewsItemDao().upsert(item)

    suspend fun delete(item: NewsItem) = db.getNewsItemDao().delete(item)

    suspend fun count(position: Int) = db.getNewsItemDao().count(position)

    fun getAllNewsItems() = db.getNewsItemDao().getAllNewsItems()
}