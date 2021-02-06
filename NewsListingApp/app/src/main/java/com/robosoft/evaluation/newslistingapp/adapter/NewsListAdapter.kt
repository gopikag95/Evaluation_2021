package com.robosoft.evaluation.newslistingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.evaluation.newslistingapp.R
import com.robosoft.evaluation.newslistingapp.db.entities.NewsItem
import com.robosoft.evaluation.newslistingapp.model.NewsArticle
import com.robosoft.evaluation.newslistingapp.viewmodel.NewsDbViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item_lyt.view.*

class NewsListAdapter(
    private val context: Context,
    private val newsDbViewModel: NewsDbViewModel
) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    private var newsList: ArrayList<NewsArticle> = ArrayList()

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val title = v.tvTitle
        val description = v.tvDescription
        val source = v.tvSource
        val bookMarkImg = v.bookmark_icon
        val newsImg = v.ivNewsImage

    }

    fun setNewsList(list: List<NewsArticle>) {
        this.newsList = list as ArrayList<NewsArticle>
        notifyDataSetChanged()

    }

    fun updateNewsList(list: List<NewsArticle>) {
        this.newsList.addAll(list)
            notifyItemInserted(newsList.size-1)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsListAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_item_lyt, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsListAdapter.ViewHolder, position: Int) {
        holder.title.text = newsList[position].title
        holder.description.text = newsList[position].description
        holder.source.text = newsList[position].source.name
        Picasso.with(context).load(newsList[position].urlToImage).into(holder.newsImg)
        holder.bookMarkImg.setOnClickListener {
            val item = NewsItem(newsList[position].title,
                newsList[position].description,
                newsList[position].urlToImage,
                newsList[position].source.name
            )
            newsDbViewModel.insert(item)
            Toast.makeText(context,"Article added to bookmark section", Toast.LENGTH_SHORT).show()

        }

    }
}