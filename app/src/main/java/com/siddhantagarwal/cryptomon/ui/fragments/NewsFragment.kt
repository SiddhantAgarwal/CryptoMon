package com.siddhantagarwal.cryptomon.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.siddhantagarwal.cryptomon.PreferenceHelper
import com.siddhantagarwal.cryptomon.R
import com.siddhantagarwal.cryptomon.adapters.NewsAdapter
import com.siddhantagarwal.cryptomon.showToast
import com.siddhantagarwal.cryptomon.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.layout_news_fragment.*

/**
 * Created by siddhant on 15/12/17.
 */
class NewsFragment: android.support.v4.app.Fragment() {
    val TAG = "news"

    private lateinit var viewModel: NewsViewModel
    // codebeat:enable[TOO_MANY_IVARS,ARITY]
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.layout_news_fragment, container, false)
    }
    // codebeat:disable[TOO_MANY_IVARS,ARITY]

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        viewModel.getNews(context.applicationContext, "crypto OR bitcoin OR ethereum OR litecoin").observe(this, Observer {
            it?.let {
                with(news_recycler_view.adapter as NewsAdapter) {
                    this.replaceItems(it)
                    this.notifyDataSetChanged()
                    news_refresh_layout.isRefreshing = false
                }
            }
        })
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        checkApiKey(context)
        news_refresh_layout.isRefreshing = true
        news_refresh_layout.setOnRefreshListener {
            news_refresh_layout.isRefreshing = true
            viewModel.refreshNews(context.applicationContext, "bitcoin")
        }
        news_recycler_view.adapter = NewsAdapter(context)
        news_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun checkApiKey(context: Context) {
        val key = PreferenceHelper.getInstance(context)
                ?.getString(PreferenceHelper.PreferencesNames.API_KEY.namePref, "")
        if (key == "") {
            news_api_key_view.visibility = View.VISIBLE
            news_refresh_layout.visibility = View.GONE
        } else {
            news_api_key_view.visibility = View.GONE
            news_refresh_layout.visibility = View.VISIBLE
        }
        setupAPIKeyView()
    }

    private fun setupAPIKeyView() {
        save_api_token_button.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_BUTTON_PRESS) {
                v.setBackgroundColor(Color.GRAY)
            }
            false
        }
        save_api_token_button.setOnClickListener {
            PreferenceHelper.getInstance(context)?.edit()?.
                    putString(PreferenceHelper.PreferencesNames.API_KEY.namePref,
                            news_token_edit_text.text.toString())?.apply()
            context.showToast("API_KEY Saved")
        }
    }
}