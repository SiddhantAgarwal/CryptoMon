package com.siddhantagarwal.cryptomon

import android.app.Fragment
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.json.JSONObject
import kotlin.concurrent.thread

/**
 * Created by siddhant on 30/11/17.
 */
class WatchFragment: Fragment() {
    lateinit var listCurrencies: ArrayList<Currency>
    lateinit var watchRecylerView: RecyclerView
    lateinit var adapter: MainAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.layout_fragment_watch, container, false)
        watchRecylerView = view.findViewById(R.id.watch_recycler_view)
        listCurrencies = ArrayList()
        adapter = MainAdapter(listCurrencies, activity)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        watchRecylerView.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL,
                false)
        swipeRefreshLayout.setOnRefreshListener {
            refreshRecycler()
        }
        watchRecylerView.adapter = adapter
        refreshRecycler()
        return view
    }

    private fun refreshRecycler() {
        swipeRefreshLayout.isRefreshing = true
        refreshDataFromServer(listCurrencies, adapter)
        swipeRefreshLayout.isRefreshing = false
    }

    private fun refreshDataFromServer(listCurrencies: ArrayList<Currency>, adapter: MainAdapter) {
        thread {
            val response = Utility.fetchDataFromURL(getString(R.string.url_for_data))
            response?.let {
                listCurrencies.clear()
                val currencyList = JSONObject(it).getJSONObject("prices")
                for (key in currencyList.keys()) {
                    listCurrencies.add(Currency(key, currencyList[key].toString().toDouble()))
                }
                activity.runOnUiThread {
                    adapter.notifyItemRangeChanged(0, listCurrencies.size)
                }
            }
        }
    }
}