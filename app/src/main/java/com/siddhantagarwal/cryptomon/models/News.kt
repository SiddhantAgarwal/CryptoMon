package com.siddhantagarwal.cryptomon.models

import android.content.Context
import com.siddhantagarwal.cryptomon.PreferenceHelper
import com.siddhantagarwal.cryptomon.R
import com.siddhantagarwal.cryptomon.Utility
import org.json.JSONObject

/**
 * Created by siddhant on 15/12/17.
 */

class News() {
    var title: String? = null
    var description: String? = null
    var imgUrl: String? = null
    var source: String? = null
    var url: String? = null
    constructor(title: String, description: String, imgUrl: String, source: String, url: String) : this() {
        this.title = title
        this.description = description
        this.imgUrl = imgUrl
        this.source = source
        this.url = url
    }

    companion object {
        fun fetchNews(context: Context, newsType: String): ArrayList<News> {
            val list = ArrayList<News>()
            val key = PreferenceHelper.getInstance(context)?.
                    getString(PreferenceHelper.PreferencesNames.API_KEY.namePref, "")
            if (key != "") {
                val url = context.getString(R.string.url_for_news, newsType, key)
                try {
                    val json = JSONObject(Utility.fetchDataFromURL(url))
                    val array = json.getJSONArray("articles")
                    for (i in 1 until array.length()) {
                        val obj = array.getJSONObject(i)
                        list.add(News(obj.getString("title"),
                                obj.getString("description"),
                                obj.getString("urlToImage"),
                                obj.getJSONObject("source").getString("name"),
                                obj.getString("url")
                        ))
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
            return list
        }
    }
}