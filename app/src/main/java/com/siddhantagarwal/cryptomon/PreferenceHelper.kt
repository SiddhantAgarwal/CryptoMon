package com.siddhantagarwal.cryptomon

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by siddhant on 16/12/17.
 */
object PreferenceHelper {
    private var preferences: SharedPreferences? = null
    fun getInstance(context: Context): SharedPreferences? {
        if (preferences == null) {
            this.preferences = context.getSharedPreferences("MainPreferences",
                    Context.MODE_PRIVATE)
        }
        return this.preferences
    }
    enum class PreferencesNames(val namePref: String) {
        API_KEY("NEWS_API_KEY"),
        FCM_TOKEN("FCM_TOKEN")
    }
}