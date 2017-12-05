package com.siddhantagarwal.cryptomon.ui.activities

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.siddhantagarwal.cryptomon.ui.fragments.PersonalFragment
import com.siddhantagarwal.cryptomon.R
import com.siddhantagarwal.cryptomon.Utility
import com.siddhantagarwal.cryptomon.ui.fragments.WatchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var watchFragment: WatchFragment
    private lateinit var personalFragment: PersonalFragment
    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.watch_tab -> pushFragment(watchFragment)
                R.id.personal_tab -> pushFragment(personalFragment)
                else -> Utility.logDebug(TAG, "I am also looking for satoshi nakamoto")
            }
            true
        }
        watchFragment = WatchFragment()
        personalFragment = PersonalFragment()
        pushFragment(watchFragment)
    }

    private fun pushFragment(fragment: Fragment) {
        fragment.let {
            fragmentManager?.let {
                it.beginTransaction().let {
                    it.let {
                        it.replace(R.id.root_layout, fragment)
                        it.commit()
                    }
                }
            }
        }
    }
}
