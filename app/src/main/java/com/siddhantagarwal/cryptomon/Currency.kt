package com.siddhantagarwal.cryptomon

/**
 * Created by siddhant on 29/11/17.
 */

data class Currency(val code: String,
                    val value: Double?,
                    val ltp: Double? = 0.0,
                    val la: Double? = 0.0,
                    val hb: Double? = 0.0)