package com.siddhantagarwal.cryptomon.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.siddhantagarwal.cryptomon.models.News


/**
 * Created by siddhant on 15/12/17.
 */
class NewsViewModel: ViewModel() {
    private lateinit var news: MutableLiveData<ArrayList<News>>
    fun getNews(): LiveData<ArrayList<News>> {
        loadNews()
        return news
    }

    private fun loadNews() {
        news = MutableLiveData()
        news.value = News.fetchNews()
    }
}