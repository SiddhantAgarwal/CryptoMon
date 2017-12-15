package com.siddhantagarwal.cryptomon.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.siddhantagarwal.cryptomon.R
import com.siddhantagarwal.cryptomon.adapters.NewsAdapter
import com.siddhantagarwal.cryptomon.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.layout_news_fragment.*

/**
 * Created by siddhant on 15/12/17.
 */
class NewsFragment: android.support.v4.app.Fragment() {

    lateinit var viewModel: NewsViewModel
    // codebeat:enable[TOO_MANY_IVARS,ARITY]
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.layout_news_fragment, container, false)
    }
    // codebeat:disable[TOO_MANY_IVARS,ARITY]

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        viewModel.getNews().observe(this, Observer {
            it?.let {
                with(news_recycler_view.adapter as NewsAdapter) {
                    this.replaceItems(it)
                    this.notifyDataSetChanged()
                }
            }
        })
        news_recycler_view.adapter = NewsAdapter(activity)
        news_recycler_view.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        super.onViewCreated(view, savedInstanceState)
    }
}