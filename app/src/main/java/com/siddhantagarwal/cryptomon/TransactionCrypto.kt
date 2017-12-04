package com.siddhantagarwal.cryptomon

import com.orm.SugarRecord

/**
 * Created by siddhant on 01/12/17.
 */
data class Transaction(val currencyCode: String,
                       val amount: Double,
                       val quantity: Double,
                       val rate: Double): SugarRecord<Transaction>()