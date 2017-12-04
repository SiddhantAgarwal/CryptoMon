package com.siddhantagarwal.cryptomon

import android.app.AlertDialog
import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.layout_add_transaction_popup.*
import kotlinx.android.synthetic.main.layout_fragment_personal.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

/**
 * Created by siddhant on 30/11/17.
 */

class PersonalFragment : Fragment() {
    private lateinit var transactionRecyclerView: RecyclerView
    private lateinit var transactionArrayList: ArrayList<TransactionCrypto>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.layout_fragment_personal, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_transaction_button.animation = AnimationUtils.loadAnimation(activity, R.anim.fab_grow)
        add_transaction_button.animation.start()
        add_transaction_button.setOnClickListener {
            val addTransactionDialog = createAddTransactionDialog()
            addTransactionDialog?.show()
        }
        transactionArrayList = ArrayList()
        val adapter = TransactionRecyclerAdapter(transactionArrayList, activity)
        transaction_recycler_view.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        transaction_recycler_view.adapter = adapter
        swipe_refresh_layout.setOnRefreshListener {
            swipe_refresh_layout.isRefreshing = true
            refreshTransactionRecycler()
            swipe_refresh_layout.isRefreshing = false
        }
        refreshTransactionRecycler()
    }

    private fun refreshTransactionRecycler() {
        try {
            transactionArrayList = ArrayList()
            SugarRecord.findAll(TransactionCrypto::class.java).forEach {
                Utility.logDebug("tr", it.toString())
                transactionArrayList.add(it)
            }
            transactionRecyclerView.adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Utility.logError("Error", e.message)
        }
    }

    private fun createAddTransactionDialog(): AlertDialog? {
        activity?.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            val view = LayoutInflater.from(it).inflate(R.layout.layout_add_transaction_popup, null)
            builder.setView(view)
            add_button.setOnClickListener {
                val transaction = TransactionCrypto(currency_code_edit_text.text.toString(),
                        amount_edit_text.text.toString().toDouble(),
                        quantity_edit_text.text.toString().toDouble(),
                        rate_edit_text.text.toString().toDouble())
                thread {
                    transaction.save()
                }
            }
            return builder.create()
        }
        return null
    }
}