package com.siddhantagarwal.cryptomon

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by siddhant on 29/11/17.
 */

class MainAdapter(val dataList: ArrayList<Currency>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
       return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.layout_currency_recycler_item,
                parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
       holder.bindItems(dataList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(currency: Currency) {
            val currencyNameTextView = itemView.findViewById<TextView>(R.id.currency_name_textview)
            val currencyPriceTextView = itemView.findViewById<TextView>(R.id.currency_value_textview)
//            val currencySymbolImageView = itemView.findViewById<ImageView>(R.id.currency_symbol_imageview)
//            val expandButton = itemView.findViewById<ImageView>(R.id.more_button)
            currencyNameTextView.text = currency.code
            currencyPriceTextView.text = currency.value.toString()
        }
    }
}