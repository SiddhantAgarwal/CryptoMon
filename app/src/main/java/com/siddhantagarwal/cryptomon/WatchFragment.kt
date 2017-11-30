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
    lateinit var listCurrencies: ArrayList<HashMap<String, Any>>
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

    private fun refreshDataFromServer(listCurrencies: ArrayList<HashMap<String, Any>>, adapter: MainAdapter) {
        thread {
            val response = Utility.fetchDataFromURL(getString(R.string.url_for_data))
            response?.let {
                listCurrencies.clear()
                val currencyList = JSONObject(it).getJSONObject("prices")
                val map = HashMap<String, HashMap<String, String>>()
                for (key in currencyList.keys()) {
                    map[key] = HashMap()
                    map[key]?.set("value", currencyList[key].toString())
                }
                val detailList = JSONObject(it).getJSONObject("stats")
                for (key in detailList.keys()) {
                    val subDetailList = JSONObject(detailList[key].toString())
                    map[key]?.set("ltp", subDetailList["last_traded_price"].toString())
                    map[key]?.set("la", subDetailList["lowest_ask"].toString())
                    map[key]?.set("hb", subDetailList["highest_bid"].toString())
                }
                for (entry in map) {
                    val tempMap = HashMap<String, Any>()
                    tempMap["currency"] = Currency(entry.key,
                            entry.value["value"]?.toDouble(),
                            entry.value["ltp"]?.toDouble(),
                            entry.value["la"]?.toDouble(),
                            entry.value["hb"]?.toDouble()
                    )
                    tempMap["expanded"] = false
                    listCurrencies.add(tempMap)
                }
                activity.runOnUiThread {
                    adapter.notifyItemRangeChanged(0, listCurrencies.size)
                }
            }
        }
    }
}