package com.siddhantagarwal.cryptomon

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.util.Log


class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var watchFragment: WatchFragment
    lateinit var personalFragment: PersonalFragment
    var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
           when(it.itemId) {
               R.id.watch_tab -> pushFragment(watchFragment)
               R.id.personal_tab -> pushFragment(personalFragment)
               else -> Log.d(TAG, "I am also looking for satoshi nakamoto")
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
