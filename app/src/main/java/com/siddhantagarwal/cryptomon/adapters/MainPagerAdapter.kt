package com.siddhantagarwal.cryptomon.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter


/**
 * Created by siddhant on 16/12/17.
 */
class MainPagerAdapter(fm: FragmentManager, var numTabs: Int): FragmentStatePagerAdapter(fm) {

    private val fragmentList : ArrayList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return this.numTabs
    }

    fun addFragment(fragment: Fragment) {
        this.fragmentList.add(fragment)
    }

}