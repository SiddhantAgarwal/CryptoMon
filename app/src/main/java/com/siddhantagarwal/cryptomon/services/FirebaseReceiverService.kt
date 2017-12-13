package com.siddhantagarwal.cryptomon.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.siddhantagarwal.cryptomon.Utility

/**
 * Created by siddhant on 13/12/17.
 */
class FirebaseReceiverService : FirebaseMessagingService() {
    val tag = "FirebaseMessagingService"
    override fun onMessageReceived(p0: RemoteMessage?) {
        p0?.let {
            Utility.logDebug(tag, it.data.toString())
        }
        super.onMessageReceived(p0)
    }
}