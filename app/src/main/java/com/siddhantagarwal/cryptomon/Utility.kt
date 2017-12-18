package com.siddhantagarwal.cryptomon

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by siddhant on 29/11/17.
 */
class Utility {
    companion object {
        fun fetchDataFromURL(url: String): String? {
            val urlObject = URL(url)
            with(urlObject.openConnection() as HttpURLConnection) {
                try {
                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()
                        var inputLine = it.readLine()
                        response.append(inputLine)
                        while (inputLine != null) {
                            inputLine = it.readLine()
                            response.append(inputLine)
                        }
                        inputStream.close()
                        return response.toString()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                } finally {
                    this.disconnect()
                }
            }
        }

        fun logDebug(tag: String, message: String) {
            if(Config.debugMode) {
                Log.d(tag, message)
            }
        }

        fun logError(tag: String, message: String?) {
            if(Config.debugMode) {
                Log.e(tag, message)
            }
        }
    }
}