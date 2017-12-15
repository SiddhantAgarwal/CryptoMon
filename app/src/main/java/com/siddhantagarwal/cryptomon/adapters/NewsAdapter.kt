package com.siddhantagarwal.cryptomon.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.siddhantagarwal.cryptomon.R
import com.siddhantagarwal.cryptomon.models.News
import com.siddhantagarwal.cryptomon.setTextIfNotNull
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_news_recycler_item.view.*

/**
 * Created by siddhant on 15/12/17.
 */
class NewsAdapter(private  val context: Context): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var newsList: ArrayList<News> = ArrayList()
    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bindItems(newsList[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NewsAdapter.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.layout_news_recycler_item,
                parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItems(entry: News, context: Context) {
            with(entry) {
                itemView.news_title_text_view.setTextIfNotNull(title)
                itemView.news_description_text_view.setTextIfNotNull(description)
                itemView.news_source_text_view.setTextIfNotNull(source)
                Picasso.with(context)
                        .load(Uri.parse(imgUrl))
                        .placeholder(R.drawable.news_placeholder)
                        .into(itemView.news_image_view)
                itemView.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    context.startActivity(intent)
                }
            }
        }
    }

    fun replaceItems(list: ArrayList<News>) {
        newsList = list
    }

}