package com.siddhantagarwal.cryptomon.ui.fragments

import android.app.AlertDialog
import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.orm.SugarRecord
import com.siddhantagarwal.cryptomon.R
import com.siddhantagarwal.cryptomon.Utility
import com.siddhantagarwal.cryptomon.adapters.TransactionRecyclerAdapter
import com.siddhantagarwal.cryptomon.models.TransactionCrypto
import kotlinx.android.synthetic.main.layout_add_transaction_popup.view.*
import kotlinx.android.synthetic.main.layout_fragment_personal.*
import kotlin.concurrent.thread

/**
 * Created by siddhant on 30/11/17.
 */

class PersonalFragment : Fragment() {
    private lateinit var transactionArrayList: ArrayList<TransactionCrypto>

    // codebeat:disable[TOO_MANY_IVARS,ARITY]
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.layout_fragment_personal, container, false)
    }
    // codebeat:disable[TOO_MANY_IVARS,ARITY]

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_transaction_button.animation = AnimationUtils.loadAnimation(activity, R.anim.fab_grow)
        add_transaction_button.animation.start()
        add_transaction_button.setOnClickListener {
            val addTransactionDialog = createAddTransactionDialog()
            addTransactionDialog?.show()
        }
        transactionArrayList = ArrayList()
        val adapter = TransactionRecyclerAdapter(transactionArrayList, activity as AppCompatActivity)
        transaction_recycler_view.layoutManager = LinearLayoutManager(activity,
                RecyclerView.VERTICAL, false)
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
            val findAll = SugarRecord.findAll(TransactionCrypto::class.java)
            transactionArrayList.clear()
            findAll.forEach {
                transactionArrayList.add(it)
            }
            transaction_recycler_view.adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Utility.logError("Error", e.message)
        }
    }

    private fun createAddTransactionDialog(): AlertDialog? {
        activity?.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            val view = LayoutInflater.from(it).inflate(R.layout.layout_add_transaction_popup,
                    null)
            builder.setView(view)
            val dialog = builder.create()
            val handler = Handler()
            view.add_button.setOnClickListener {
                val transaction = TransactionCrypto(
                        view.currency_code_edit_text.text.toString().toUpperCase(),
                        view.amount_edit_text.text.toString().toDouble(),
                        view.quantity_edit_text.text.toString().toDouble(),
                        view.rate_edit_text.text.toString().toDouble())
                thread {
                    transaction.save()
                    handler.post {
                        dialog.dismiss()
                        handler.postDelayed({
                            refreshTransactionRecycler()
                        }, 500)
                    }
                }
            }
            return dialog
        }
        return null
    }
}