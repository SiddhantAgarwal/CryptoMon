package com.siddhantagarwal.cryptomon.adapters

import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orm.SugarRecord
import com.siddhantagarwal.cryptomon.R
import com.siddhantagarwal.cryptomon.models.TransactionCrypto
import com.siddhantagarwal.cryptomon.Utility
import kotlinx.android.synthetic.main.layout_add_transaction_popup.view.*
import kotlinx.android.synthetic.main.layout_transaction_recycler_item.view.*
import java.util.*
import kotlin.concurrent.thread

/**
 * Created by siddhant on 01/12/17.
 */
class TransactionRecyclerAdapter(private val transactionList: ArrayList<TransactionCrypto>, val context: Context): RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder>() {
    val TAG = "TransactionAdapter"
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

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItems(transaction: TransactionCrypto) {
            with(transaction) {
                itemView.currency_code_text_view.text = currencyCode
                itemView.quantity_text_view.text = quantity.toString()
                itemView.valuation_text_view.text = context.getString(R.string.currency_valuation_string,
                        rate.toString())
                itemView.total_amount_text_view.text = amount.toString()
                itemView.options_button.setOnClickListener {
                    val popUpMenu = PopupMenu(context, itemView.options_button)
                    popUpMenu.menuInflater.inflate(R.menu.transaction_list_item_menu, popUpMenu.menu)
                    popUpMenu.setOnMenuItemClickListener {
                        val handler = android.os.Handler()
                        when(it.itemId) {
                            R.id.update -> {
                                updateTransaction(transaction, adapterPosition)
                                handler.postDelayed({
                                    if(adapterPosition != RecyclerView.NO_POSITION) {
                                        notifyItemChanged(adapterPosition)
                                    }
                                }, 500)
                            }
                            R.id.delete -> {
                                deleteTransaction(transaction)
                                handler.postDelayed({
                                    if(adapterPosition != RecyclerView.NO_POSITION) {
                                        transactionList.removeAt(adapterPosition)
                                        notifyItemRemoved(adapterPosition)
                                    }
                                }, 500)
                            }
                            else -> Utility.logDebug(TAG, "Nope, not yet")
                        }
                        true
                    }
                    popUpMenu.show()
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
            transaction.currencyCode = view.currency_code_edit_text.text.toString()
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

    fun deleteTransaction(transaction: TransactionCrypto) {
        transaction.delete()
    }
}