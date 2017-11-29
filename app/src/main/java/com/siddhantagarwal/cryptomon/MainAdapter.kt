package com.siddhantagarwal.cryptomon

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by siddhant on 29/11/17.
 */

class MainAdapter(private val dataList: ArrayList<Currency>, val context: Context): RecyclerView.Adapter<MainAdapter.ViewHolder>() {
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(currency: Currency) {
            val currencyNameTextView = itemView.findViewById<TextView>(R.id.currency_name_textview)
            val currencyPriceTextView = itemView.findViewById<TextView>(R.id.currency_value_textview)
            val currencySymbolImageView = itemView.findViewById<ImageView>(R.id.currency_symbol_imageview)
            //            val expandButton = itemView.findViewById<ImageView>(R.id.more_button)
            with(currency) {
                currencyNameTextView.text = code
                val currValue = context.getString(R.string.currency_value_string, value.toString())
                currencyPriceTextView.text = currValue
                val resourceId = context.resources.getIdentifier(code.toLowerCase(), "drawable", context.packageName)
                currencySymbolImageView.setImageDrawable(context.getDrawable(resourceId))
            }
        }
    }
}