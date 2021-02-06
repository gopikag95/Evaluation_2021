package com.robosoft.evaluation.newslistingapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.robosoft.evaluation.newslistingapp.db.entities.NewsItem
import com.robosoft.evaluation.newslistingapp.model.NewsArticle

@Dao
interface NewsItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: NewsItem)

    @Delete
    suspend fun delete(item: NewsItem)

    @Query("SELECT * FROM `NEWS..ITEMS`")
    fun getAllNewsItems() : LiveData<List<NewsItem>>

    @Query("SELECT COUNT() FROM `NEWS..ITEMS` WHERE id = :id")
    fun count(id: Int): Int

}