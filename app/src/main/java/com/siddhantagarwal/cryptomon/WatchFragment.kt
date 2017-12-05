package com.siddhantagarwal.cryptomon

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.layout_fragment_watch.*
import org.json.JSONObject
import kotlin.concurrent.thread

/**
 * Created by siddhant on 30/11/17.
 */
class WatchFragment: Fragment() {
    private lateinit var listCurrencies: ArrayList<HashMap<String, Any>>
    private lateinit var adapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.layout_fragment_watch, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listCurrencies = ArrayList()
        adapter = MainAdapter(listCurrencies, activity)
        watch_recycler_view?.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL,
                false)
        swipe_refresh_layout?.setOnRefreshListener {
            refreshRecycler()
        }
        watch_recycler_view?.adapter = adapter
        refreshRecycler()
    }

    private fun refreshRecycler() {
        swipe_refresh_layout.isRefreshing = true
        refreshDataFromServer(listCurrencies, adapter)
        swipe_refresh_layout.isRefreshing = false
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
                SugarRecord.deleteAll(Currency::class.java)
                for (entry in map) {
                    Currency(entry.key,
                            entry.value["value"]?.toDouble(),
                            entry.value["ltp"]?.toDouble(),
                            entry.value["la"]?.toDouble(),
                            entry.value["hb"]?.toDouble()
                    ).save()
                }
                SugarRecord.findAll(Currency::class.java).forEach {
                    val mapOb = HashMap<String, Any>()
                    mapOb["expanded"] = false
                    mapOb["currency"] = it
                    listCurrencies.add(mapOb)
                }
                activity?.runOnUiThread {
                    adapter.notifyItemRangeChanged(0, listCurrencies.size)
                }
            }
        }
    }
}