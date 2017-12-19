package com.siddhantagarwal.cryptomon.adapters

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.RecyclerView
import android.view.*
import com.siddhantagarwal.cryptomon.R
import com.siddhantagarwal.cryptomon.models.Currency
import com.siddhantagarwal.cryptomon.models.TransactionCrypto
import com.siddhantagarwal.cryptomon.setTextIfNotNull
import kotlinx.android.synthetic.main.layout_transaction_recycler_item.view.*

/**
 * Created by siddhant on 01/12/17.
 */
class TransactionRecyclerAdapter(private val transactionList: ArrayList<TransactionCrypto>, val context: AppCompatActivity) : RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder>() {
    val TAG = "TransactionAdapter"

    var multiSelect = false
    val selectedItems = ArrayList<Int>()
    val contextAction = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            multiSelect = true
            menu?.let {
                context.menuInflater.inflate(R.menu.transactions_contextual_menu, menu)
            }
            return true
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.delete -> {
                    deleteTransactions()
                    notifyDataSetChanged()
                    mode?.finish()
                }
                R.id.select_all -> {
                    selectedItems.clear()
                    transactionList.mapTo(selectedItems) { transactionList.indexOf(it) }
                    notifyDataSetChanged()
                }
            }
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            multiSelect = false
            selectedItems.clear()
            notifyDataSetChanged()
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(transactionList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.layout_transaction_recycler_item,
                parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(transaction: TransactionCrypto) {
            with(transaction) {
                if (selectedItems.contains(adapterPosition)) {
                    itemView.setBackgroundColor(Color.LTGRAY)
                } else {
                    itemView.setBackgroundColor(Color.WHITE)
                }
                itemView.setOnClickListener {
                    selectItem(adapterPosition)
                }
                itemView.setOnLongClickListener {
                    context.startSupportActionMode(contextAction)
                    selectItem(adapterPosition)
                    true
                }
                itemView.currency_code_text_view.setTextIfNotNull(currencyCode)
                itemView.quantity_text_view.setTextIfNotNull(quantity.toString())
                rate?.let {
                    if (TransactionCrypto.compareWithCurrentPrice(rate!!, currencyCode!!) < 0) {
                        itemView.movement_image_view.setImageResource(R.drawable.ic_caret_down)
                        itemView.movement_image_view.drawable.setTint(Color.RED)
                    } else {
                        itemView.movement_image_view.setImageResource(R.drawable.ic_caret_up)
                        itemView.movement_image_view.drawable.setTint(Color.GREEN)
                    }
                    val current = Currency.getRateForCurrency(currencyCode!!)!! * quantity!!
                    itemView.current_value_text_view.setTextIfNotNull(context.getString(
                            R.string.currency_holding_current_string,
                            current.toString()))
                    itemView.valuation_text_view.setTextIfNotNull(context.getString(
                            R.string.currency_valuation_string,
                            rate.toString()))
                }
                itemView.total_amount_text_view.setTextIfNotNull(context.getString(
                        R.string.currency_holding_invested_string,
                        amount.toString()))
            }
        }

        private fun selectItem(item: Int) {
            if (multiSelect) {
                if (selectedItems.contains(item)) {
                    selectedItems.remove(item)
                    itemView.setBackgroundColor(Color.WHITE)
                } else {
                    selectedItems.add(item)
                    itemView.setBackgroundColor(Color.LTGRAY)
                }
            }
        }
    }

    fun deleteTransactions() {
        for (index in selectedItems) {
            transactionList[index].delete()
            transactionList.removeAt(index)
        }
    }
}
