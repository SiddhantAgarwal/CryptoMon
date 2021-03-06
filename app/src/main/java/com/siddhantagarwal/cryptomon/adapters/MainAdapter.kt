package com.siddhantagarwal.cryptomon.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.siddhantagarwal.cryptomon.R
import com.siddhantagarwal.cryptomon.models.Currency
import com.siddhantagarwal.cryptomon.setTextIfNotNull
import kotlinx.android.synthetic.main.layout_currency_recycler_item.view.*

/**
 * Created by siddhant on 29/11/17.
 */

class MainAdapter(private val dataList: ArrayList<HashMap<String, Any>>, val context: Context) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.layout_currency_recycler_item,
                parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(dataList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(entry: HashMap<String, Any>) {
            with(entry["currency"] as Currency) {
                itemView.currency_name_textview.setTextIfNotNull(code)
                itemView.currency_value_textview.setTextIfNotNull(context.getString(
                        R.string.currency_value_string, value.toString()))
                itemView.last_traded_price_textView.setTextIfNotNull(context.getString(
                        R.string.currency_value_string, ltp.toString()))
                itemView.highest_bid_textView.setTextIfNotNull(context.getString(
                        R.string.currency_value_string, hb.toString()))
                itemView.lowest_ask_textView.setTextIfNotNull(context.getString(
                        R.string.currency_value_string, la.toString()))
                val resourceId = context.resources.getIdentifier(code?.toLowerCase(),
                        "drawable", context.packageName)
                itemView.currency_symbol_imageview.setImageDrawable(context.getDrawable(resourceId))
            }
            itemView.setOnClickListener {
                if (entry["expanded"] as Boolean) {
                    itemView.details_layout.visibility = View.GONE
                    entry["expanded"] = false
                    itemView.more_button.setImageResource(R.drawable.ic_expand)
                } else {
                    itemView.details_layout.visibility = View.VISIBLE
                    entry["expanded"] = true
                    itemView.more_button.setImageResource(R.drawable.ic_expand_less_black_24dp)
                }
            }
        }
    }
}