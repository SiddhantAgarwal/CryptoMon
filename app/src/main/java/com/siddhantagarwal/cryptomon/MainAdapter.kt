package com.siddhantagarwal.cryptomon

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_currency_recycler_item.view.*

/**
 * Created by siddhant on 29/11/17.
 */

class MainAdapter(private val dataList: ArrayList<HashMap<String, Any>>, val context: Context) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

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
        fun bindItems(entry: HashMap<String, Any>) {
            with(entry["currency"] as Currency) {
                itemView.currency_name_textview.text = code

                itemView.currency_value_textview.text = ""
                value?.let {
                    val currValue = context.getString(R.string.currency_value_string, it.toString())
                    itemView.currency_value_textview.text = currValue
                }

                itemView.last_traded_price_textView.text = ""
                ltp?.let {
                    val ltpValue = context.getString(R.string.currency_value_string, it.toString())
                    itemView.last_traded_price_textView.text = ltpValue
                }

                itemView.highest_bid_textView.text = ""
                hb?.let {
                    val hbValue = context.getString(R.string.currency_value_string, it.toString())
                    itemView.highest_bid_textView.text = hbValue
                }

                itemView.lowest_ask_textView.text = ""
                la?.let {
                    val laValue = context.getString(R.string.currency_value_string, it.toString())
                    itemView.lowest_ask_textView.text = laValue
                }
                val resourceId = context.resources.getIdentifier(code.toLowerCase(), "drawable", context.packageName)
                itemView.currency_symbol_imageview.setImageDrawable(context.getDrawable(resourceId))
            }

            itemView.more_button.setOnClickListener {
                val detailsView = itemView.findViewById<LinearLayout>(R.id.details_layout)
                if (entry["expanded"] as Boolean) {
                    detailsView.visibility = View.GONE
                    entry["expanded"] = false
                    itemView.more_button.setImageResource(R.drawable.ic_expand)
                } else {
                    detailsView.visibility = View.VISIBLE
                    entry["expanded"] = true
                    itemView.more_button.setImageResource(R.drawable.ic_expand_less_black_24dp)
                }
            }
        }
    }
}