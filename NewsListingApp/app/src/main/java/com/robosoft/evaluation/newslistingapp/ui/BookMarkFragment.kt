package com.robosoft.evaluation.newslistingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.robosoft.evaluation.newslistingapp.R
import com.robosoft.evaluation.newslistingapp.adapter.NewsBookMarkAdapter
import com.robosoft.evaluation.newslistingapp.db.NewsItemDatabase
import com.robosoft.evaluation.newslistingapp.db.entities.NewsItem
import com.robosoft.evaluation.newslistingapp.repository.NewsItemDbRepository
import com.robosoft.evaluation.newslistingapp.viewmodel.NewsDbViewModel
import com.robosoft.evaluation.newslistingapp.viewmodel.NewsViewModelFactory
import kotlinx.android.synthetic.main.bookmark_frag.*


class BookMarkFragment : Fragment() {
    private lateinit var adapter: NewsBookMarkAdapter
    private lateinit var newsDbViewModel: NewsDbViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bookmark_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        toolBar.findViewById<ImageView>(R.id.logoImg).visibility = GONE
        toolBar.findViewById<TextView>(R.id.appNameTv).visibility = GONE
        toolBar.findViewById<TextView>(R.id.headerTv).visibility = VISIBLE

        init()

    }

    private fun init() {
        val database = context?.let { NewsItemDatabase(it) }
        val repository = database?.let { NewsItemDbRepository(it) }
        val factory = repository?.let { NewsViewModelFactory(it) }
        var items: List<NewsItem>
        newsDbViewModel = ViewModelProviders.of(this, factory).get(NewsDbViewModel::class.java)

        newsDbViewModel.getAllNewsItems().observe(this, Observer {
            items = it
            adapter = context?.let { it1 -> NewsBookMarkAdapter(items, newsDbViewModel, it1) }!!
            bookmarkItems.adapter = adapter
        })
    }

    companion object {
        fun newInstance(): BookMarkFragment {
            return BookMarkFragment()
        }
    }
}