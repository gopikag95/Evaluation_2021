package com.robosoft.evaluation.newslistingapp.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.robosoft.evaluation.newslistingapp.model.NewsArticle
import com.robosoft.evaluation.newslistingapp.model.NewsDataModel
import com.robosoft.evaluation.newslistingapp.network.NewsNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsListingRepository (val application: Application) {

    val showProgess = MutableLiveData<Boolean>()
    val newsList = MutableLiveData<List<NewsArticle>>()
    val newsData = MutableLiveData<NewsDataModel>()

    val retrofit = Retrofit.Builder()
        .baseUrl(NewsNetwork.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(NewsNetwork::class.java)

    fun getNewsList(s: String) {
        service.newsData(s)?.enqueue(object : Callback<NewsDataModel?> {
            override fun onFailure(call: Call<NewsDataModel?>, t: Throwable) {
                showProgess.value = false
                Toast.makeText(application,"Error", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<NewsDataModel?>,
                response: Response<NewsDataModel?>
            ) {
                Log.d("NewsListingRepository", "Response is : ${Gson().toJson(response.body())}")
                showProgess.value = false
                if(response.body() != null){
                    newsData.value = response.body()
                    if(newsData.value != null && !newsData.value?.articles.isNullOrEmpty())
                        newsList.value = newsData.value?.articles
                }

            }

        })
    }


}