package com.siddhantagarwal.cryptomon.models

import org.json.JSONArray

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
        fun fetchNews(): ArrayList<News> {
            val array = JSONArray("")
            val list = ArrayList<News>()
            for (i in 1 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(News(obj.getString("title"),
                        obj.getString("description"),
                        obj.getString("urlToImage"),
                        obj.getJSONObject("source").getString("name"),
                        obj.getString("url")
                ))
            }
            return list
        }
    }
}