package com.siddhantagarwal.cryptomon.services

import android.content.Context
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.siddhantagarwal.cryptomon.Utility

/**
 * Created by siddhant on 13/12/17.
 */
class FirebaseInstanceHandlerService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val sharedPreferences = applicationContext.getSharedPreferences("MainPreferences", Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        FirebaseInstanceId.getInstance().token?.let {
            Utility.logDebug("FCM_ID", it)
            edit?.putString("FCM_ID", it)
        }
        edit.apply()
    }
}