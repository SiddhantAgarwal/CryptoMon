package com.siddhantagarwal.cryptomon

import android.content.Context
import android.widget.Toast

/**
 * Created by siddhant on 04/12/17.
 */
fun Context.showDebugToast(text: String) {
    if (Config.debugMode) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
