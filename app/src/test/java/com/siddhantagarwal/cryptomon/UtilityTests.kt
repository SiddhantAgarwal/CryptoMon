package com.siddhantagarwal.cryptomon

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UtilityTests {
    @Test
    fun fetchUrlFunctionTest() {
        val resp = Utility.fetchDataFromURL("https://www.google.com")
        assertTrue(resp is String && resp != "")
    }
}
