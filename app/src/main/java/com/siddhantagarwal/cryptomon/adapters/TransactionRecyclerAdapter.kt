package com.siddhantagarwal.cryptomon.adapters

import android.app.AlertDialog
import android.graphics.Color
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.RecyclerView
import android.view.*
import com.siddhantagarwal.cryptomon.R
import com.siddhantagarwal.cryptomon.models.TransactionCrypto
import com.siddhantagarwal.cryptomon.models.Currency
import kotlinx.android.synthetic.main.layout_add_transaction_popup.view.*
import kotlinx.android.synthetic.main.layout_transaction_recycler_item.view.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

/**
 * Created by siddhant on 01/12/17.
 */
class TransactionRecyclerAdapter(private val transactionList: ArrayList<TransactionCrypto>, val context: AppCompatActivity) : RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder>() {
    val TAG = "TransactionAdapter"

    var multiSelect = false
    val selectedItems = ArrayList<Int>()

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
                    context.startSupportActionMode(object : ActionMode.Callback {
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
                    })
                    selectItem(adapterPosition)
                    true
                }
                var currency : Currency? = null
                currencyCode?.let {
                    itemView.currency_code_text_view.text = it
                    currency = Currency.findCurrencyByCode(currencyCode!!)
                }

                quantity?.let {
                    itemView.quantity_text_view.text = quantity.toString()
                }
                rate?.let {
                    itemView.valuation_text_view.text = context.getString(
                            R.string.currency_valuation_string,
                            rate.toString())
                }
                amount?.let {
                    itemView.total_amount_text_view.text = context.getString(
                            R.string.currency_holding_invested_string,
                            amount.toString())
                }

                var current: Double? = null
                currency?.let {
                    if (it.value!! < rate!!.toDouble()) {
                        itemView.movement_image_view.setImageResource(R.drawable.ic_caret_down)
                        itemView.movement_image_view.drawable.setTint(Color.RED)
                    } else {
                        itemView.movement_image_view.setImageResource(R.drawable.ic_caret_up)
                        itemView.movement_image_view.drawable.setTint(Color.GREEN)
                    }
                    current = it.value!! * quantity!!
                }
                current?.let {
                    itemView.current_value_text_view.text = context.getString(
                            R.string.currency_holding_current_string, "%.2f".format(it))
                }

                itemView.edit_button.setOnClickListener {
                    updateTransaction(transactionList[adapterPosition], adapterPosition)
                }
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

    fun updateTransaction(transaction: TransactionCrypto, position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.layout_add_transaction_popup, null)
        builder.setView(view)
        val handler = Handler()
        val dialog = builder.create()
        view.add_button.setOnClickListener {
            transaction.currencyCode = view.currency_code_edit_text.text.toString().toUpperCase()
            transaction.amount = view.amount_edit_text.text.toString().toDouble()
            transaction.quantity = view.quantity_edit_text.text.toString().toDouble()
            transaction.rate = view.rate_edit_text.text.toString().toDouble()
            thread {
                transaction.save()
                handler.post {
                    dialog.dismiss()
                    handler.postDelayed({
                        notifyItemChanged(position)
                    }, 500)
                }
            }
        }
        dialog.show()
    }

    fun deleteTransactions() {
        for (index in selectedItems) {
            transactionList[index].delete()
            transactionList.removeAt(index)
        }
    }
}
