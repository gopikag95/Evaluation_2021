package com.robosoft.evaluation.newslistingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.evaluation.newslistingapp.R
import com.robosoft.evaluation.newslistingapp.db.entities.NewsItem
import com.robosoft.evaluation.newslistingapp.viewmodel.NewsDbViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item_lyt.view.*

class NewsBookMarkAdapter(
    var items: List<NewsItem>,
    val newsDbViewModel: NewsDbViewModel,
    val context: Context
) : RecyclerView.Adapter<NewsBookMarkAdapter.NewsDbViewHolder>() {

    inner class NewsDbViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val title = v.tvTitle
        val description = v.tvDescription
        val source = v.tvSource
        val bookMarkImg = v.bookmark_icon
        val newsImg = v.ivNewsImage
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsBookMarkAdapter.NewsDbViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_item_lyt, parent, false)
        return NewsDbViewHolder(view)
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: NewsBookMarkAdapter.NewsDbViewHolder, position: Int) {
        val newsItem = items[position]
        holder.title.text = newsItem.headline
        holder.description.text = newsItem.description
        holder.source.text = newsItem.source
        Picasso.with(context).load(newsItem.imageUrl).into(holder.newsImg)
        holder.bookMarkImg.setOnClickListener {
            newsDbViewModel.delete(newsItem)
            Toast.makeText(context,"Article removed from bookmark section", Toast.LENGTH_SHORT).show()
            notifyDataSetChanged()
        }

    }
}