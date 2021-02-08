package com.robosoft.evaluation.newslistingapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.evaluation.newslistingapp.R
import com.robosoft.evaluation.newslistingapp.adapter.NewsListAdapter
import com.robosoft.evaluation.newslistingapp.db.entities.NewsItem
import com.robosoft.evaluation.newslistingapp.network.NewsNetwork
import com.robosoft.evaluation.newslistingapp.viewmodel.NewsDbViewModel
import com.robosoft.evaluation.newslistingapp.viewmodel.NewsListingViewModel
import com.robosoft.evaluation.newslistingapp.viewmodel.NewsViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_listing_screen.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class NewsListingActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: NewsViewModelFactory by instance()
    private var isLoading = false
    private var canLoadMore = true
    private var pageValue: Int = 0
    private var pageSize: Int = 10


    private lateinit var viewModel: NewsListingViewModel
    private lateinit var newsDbViewModel: NewsDbViewModel
    private lateinit var adapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_NewsListingApp)
        setContentView(R.layout.news_listing_screen)
    }


    override fun onResume() {
        super.onResume()

        initElements()
        initUI()
    }

    private fun initElements() {
        scrollView.visibility = VISIBLE
        container.visibility = GONE
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        ).get(NewsListingViewModel::class.java)

        newsDbViewModel = ViewModelProviders.of(this, factory).get(NewsDbViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {

        fetchNewsData()
        viewModel.showProgess.observe(this, Observer {
            if (it)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE

        })
        rvNewsItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!rvNewsItems.canScrollVertically(1)) {
                    if (canLoadMore && !isLoading) {
                        pageValue++
                        loading.visibility = VISIBLE
                        progress.visibility = VISIBLE
                        fetchNewsData()
                    }
                }
            }
        })

        adapter = NewsListAdapter(applicationContext, newsDbViewModel)
        rvNewsItems.adapter = adapter

        viewModel.newsList.observe(this, Observer {
            loading.visibility = GONE
            progress.visibility = GONE
            isLoading = false
            if (pageValue == 0) {
                headlineTv.visibility = VISIBLE
                headlineTv.text = it.get(0).title
                sourceTv.visibility = VISIBLE
                sourceTv.text = it.get(0).source.name
                newsImg.visibility = VISIBLE
                descriptionTv.visibility = VISIBLE
                descriptionTv.text = it.get(0).description
                adapter.setNewsList(it)
                bookmarkImg.visibility = VISIBLE
              //  headerTv.visibility = VISIBLE
                headerTv.text = getString(R.string.popilar_news)

                val item = NewsItem(
                    it.get(0).title,
                    it.get(0).description,
                    it.get(0).urlToImage,
                    it.get(0).source.name
                )

                Picasso.with(applicationContext)
                    .load(it.get(0).urlToImage)
                    .into(newsImg)
                bookmarkImg.setOnClickListener {
                    newsDbViewModel.insert(item)
                    Toast.makeText(this,"Article added to bookmark section", Toast.LENGTH_SHORT).show()
                }

            } else {
                adapter.updateNewsList(it)
            }

            toolbar_lyt.findViewById<ImageView>(R.id.toolBarBookmarkImg).setOnClickListener {
                scrollView.visibility = GONE
                val mainFragment: BookMarkFragment = BookMarkFragment()
                container.visibility = VISIBLE
                supportFragmentManager.beginTransaction()
                    .addToBackStack("BookMarkFragment")
                    .replace(R.id.container, mainFragment)
                    .commit()
            }
        })
    }

    private fun fetchNewsData() {
        isLoading = true
        val url =
            NewsNetwork.TOP_NEWS_URL + "&pageSize=$pageSize&page=$pageValue&apiKey=" + NewsNetwork.AOI_KEY
        viewModel.getNewsData(url)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
    }

}