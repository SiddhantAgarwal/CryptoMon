package com.siddhantagarwal.cryptomon

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * Created by siddhant on 29/11/17.
 */

class MainAdapter(private val dataList: ArrayList<HashMap<String, Any>>, val context: Context) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    lateinit var recyclerView: RecyclerView

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.layout_currency_recycler_item,
                parent, false)
        recyclerView = parent as RecyclerView
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.bindItems(dataList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(entry: HashMap<String, Any>) {
            val currencyNameTextView = itemView.findViewById<TextView>(R.id.currency_name_textview)
            val currencyPriceTextView = itemView.findViewById<TextView>(R.id.currency_value_textview)
            val currencySymbolImageView = itemView.findViewById<ImageView>(R.id.currency_symbol_imageview)
            val lastTradedPriceTextView = itemView.findViewById<TextView>(R.id.last_traded_price_textView)
            val lowestAskTextView = itemView.findViewById<TextView>(R.id.lowest_ask_textView)
            val highestBidTextView = itemView.findViewById<TextView>(R.id.highest_bid_textView)
            val expandButton = itemView.findViewById<ImageView>(R.id.more_button)
            with(entry["currency"] as Currency) {
                currencyNameTextView.text = code

                currencyPriceTextView.text = ""
                value?.let {
                    val currValue = context.getString(R.string.currency_value_string, it.toString())
                    currencyPriceTextView.text = currValue
                }

                lastTradedPriceTextView.text = ""
                ltp?.let {
                    val ltpValue = context.getString(R.string.currency_value_string, it.toString())
                    lastTradedPriceTextView.text = ltpValue
                }

                highestBidTextView.text = ""
                hb?.let {
                    val hbValue = context.getString(R.string.currency_value_string, it.toString())
                    highestBidTextView.text = hbValue
                }

                lowestAskTextView.text = ""
                la?.let {
                    val laValue = context.getString(R.string.currency_value_string, it.toString())
                    lowestAskTextView.text = laValue
                }
                val resourceId = context.resources.getIdentifier(code.toLowerCase(), "drawable", context.packageName)
                currencySymbolImageView.setImageDrawable(context.getDrawable(resourceId))
            }

            expandButton.setOnClickListener {
                val detailsView = itemView.findViewById<LinearLayout>(R.id.details_layout)
                if (entry["expanded"] as Boolean) {
                    detailsView.visibility = View.GONE
                    entry["expanded"] = false
                    expandButton.setImageResource(R.drawable.ic_expand)
                } else {
                    detailsView.visibility = View.VISIBLE
                    entry["expanded"] = true
                    expandButton.setImageResource(R.drawable.ic_expand_less_black_24dp)
                }
            }
        }
    }
}