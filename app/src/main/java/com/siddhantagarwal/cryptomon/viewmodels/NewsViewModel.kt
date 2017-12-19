package com.siddhantagarwal.cryptomon.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.os.AsyncTask
import com.siddhantagarwal.cryptomon.models.News
import java.lang.ref.WeakReference


/**
 * Created by siddhant on 15/12/17.
 */
class NewsViewModel: ViewModel() {
    private var news = MutableLiveData<ArrayList<News>>()

    init {
        this.news.value = ArrayList()
    }
    fun getNews(context: Context, type: String): LiveData<ArrayList<News>> {
        if (this.news.value?.size == 0) {
            refreshNews(context, type)
        }
        return news
    }

    fun refreshNews(context: Context, type: String) {
        val task = NewsFetchTask(news)
        task.execute(context, type)
    }

    private class NewsFetchTask(newsLiveData: MutableLiveData<ArrayList<News>>) : AsyncTask<Any, Void, ArrayList<News>>() {
        var weakReference: WeakReference<MutableLiveData<ArrayList<News>>>? = null
        init {
            weakReference = WeakReference(newsLiveData)
        }
        override fun doInBackground(vararg params: Any): ArrayList<News> {
            return News.fetchNews(params[0] as Context, params[1] as String)
        }
        override fun onPostExecute(result: ArrayList<News>?) {
            weakReference?.get()?.value = result
            super.onPostExecute(result)
        }
    }
}