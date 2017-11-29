package com.siddhantagarwal.cryptomon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.json.JSONObject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainRecyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)
        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        val listCurrencies = ArrayList<Currency>()
        val adapter = MainAdapter(listCurrencies, this)
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = true
            refreshDataFromServer(listCurrencies, adapter)
            refreshLayout.isRefreshing = false
        }
        mainRecyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)
        mainRecyclerView.adapter = adapter
        refreshDataFromServer(listCurrencies, adapter)
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
                runOnUiThread {
                    adapter.notifyItemRangeChanged(0, listCurrencies.size)
                }
            }
        }
    }
}
