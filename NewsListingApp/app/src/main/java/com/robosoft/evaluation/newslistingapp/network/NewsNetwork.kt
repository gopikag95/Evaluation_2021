package com.robosoft.evaluation.newslistingapp.network

import com.robosoft.evaluation.newslistingapp.model.NewsDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NewsNetwork {



    @GET()
    fun newsData(@Url url : String) : Call<NewsDataModel?>?

    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
        const val AOI_KEY = "96056e4a8429402b813eaa0957031702"
        const val TOP_NEWS_URL = BASE_URL+"top-headlines?country=in"
    }
}