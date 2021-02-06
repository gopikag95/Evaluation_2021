package com.robosoft.evaluation.newslistingapp.model

class NewsDataModel(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticle>
)

class NewsArticle(
    val source: NewsSource,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)

class NewsSource(
    val id: String,
    val name: String
)

class NewsItem(
    var headline: String,
    var description: String,
    var imageUrl: String,
    var source: String
)


