package com.robosoft.evaluation.newslistingapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news..items")
data class NewsItem(
    @ColumnInfo(name = "news_headline")
    var headline: String,
    @ColumnInfo(name = "news_desciption")
    var description: String,
    @ColumnInfo(name = "news_image")
    var imageUrl: String,
    @ColumnInfo(name = "news_source")
    var source: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}