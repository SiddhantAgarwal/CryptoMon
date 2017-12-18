package com.siddhantagarwal.cryptomon.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.siddhantagarwal.cryptomon.R
import com.siddhantagarwal.cryptomon.Utility
import com.siddhantagarwal.cryptomon.adapters.MainPagerAdapter
import com.siddhantagarwal.cryptomon.ui.fragments.NewsFragment
import com.siddhantagarwal.cryptomon.ui.fragments.PersonalFragment
import com.siddhantagarwal.cryptomon.ui.fragments.WatchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var watchFragment: WatchFragment
    private lateinit var personalFragment: PersonalFragment
    private lateinit var newsFragment: NewsFragment
    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPagerAdapter()
        root_layout.offscreenPageLimit = 3
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.watch_tab -> root_layout.currentItem = 0
                R.id.personal_tab -> root_layout.currentItem = 1
                R.id.news_tab -> root_layout.currentItem = 2
                else -> Utility.logDebug(TAG, "I am also looking for satoshi nakamoto")
            }
            true
        }
    }

    private fun setupPagerAdapter() {
        val adapter = MainPagerAdapter(supportFragmentManager, 3)
        this.watchFragment = WatchFragment()
        this.personalFragment = PersonalFragment()
        this.newsFragment = NewsFragment()
        adapter.addFragment(watchFragment)
        adapter.addFragment(personalFragment)
        adapter.addFragment(newsFragment)
        root_layout.adapter = adapter
        root_layout.currentItem = 0
    }

}
