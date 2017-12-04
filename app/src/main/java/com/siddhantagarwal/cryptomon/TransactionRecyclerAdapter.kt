package com.siddhantagarwal.cryptomon

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_transaction_recycler_item.view.*

/**
 * Created by siddhant on 01/12/17.
 */
class TransactionRecyclerAdapter(private val transactionList: ArrayList<TransactionCrypto>, val context: Context): RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: TransactionRecyclerAdapter.ViewHolder, position: Int) {
        holder.bindItems(transactionList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TransactionRecyclerAdapter.ViewHolder {
        return LayoutInflater.from(parent?.context).inflate(R.layout.layout_transaction_recycler_item,
                parent, false) as ViewHolder
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItems(transaction: TransactionCrypto) {
            with(transaction) {
                itemView.currency_code_text_view.text = currencyCode
                itemView.quantity_text_view.text = quantity.toString()
                itemView.valuation_text_view.text = rate.toString()
                itemView.total_amount_text_view.text = amount.toString()
            }
        }
    }
}