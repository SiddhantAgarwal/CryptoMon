package com.siddhantagarwal.cryptomon

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by siddhant on 30/11/17.
 */

class PersonalFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.layout_fragment_personal, container, false)
        return view
    }
}