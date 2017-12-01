package com.siddhantagarwal.cryptomon

import android.app.AlertDialog
import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils

/**
 * Created by siddhant on 30/11/17.
 */

class PersonalFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.layout_fragment_personal, container, false)
        val addTransactionButton = view.findViewById<FloatingActionButton>(R.id.add_transaction_button)
        addTransactionButton.animation = AnimationUtils.loadAnimation(activity, R.anim.fab_grow)
        addTransactionButton.animation.start()
        addTransactionButton.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            val popupView: View = inflater.inflate(R.layout.layout_add_transaction_popup, null)
            builder.setView(popupView)
            val transactionPopup: AlertDialog = builder.create()
            transactionPopup.show()
        }
        return view
    }
}