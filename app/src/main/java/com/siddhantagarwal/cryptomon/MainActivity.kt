package com.siddhantagarwal.cryptomon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainRecyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)
        mainRecyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)
        val listCurrencies = ArrayList<Currency>()
        val adapter = MainAdapter(listCurrencies)
        mainRecyclerView.adapter = adapter
        thread {
            val response = fetchDataFromURL(getString(R.string.url_for_data))
            runOnUiThread {
                val currencyList = JSONObject(response).getJSONObject("prices")
                for (key in currencyList.keys()) {
                    listCurrencies.add(Currency(key,  currencyList[key].toString().toDouble()))
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun fetchDataFromURL(url: String): String {
        val urlObject = URL(url)
        with(urlObject.openConnection() as HttpURLConnection) {
            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()
                var inputLine = it.readLine()
                response.append(inputLine)
                while (inputLine != null) {
                    inputLine = it.readLine()
                    response.append(inputLine)
                }
                inputStream.close()
                return response.toString()
            }
        }
    }
}
