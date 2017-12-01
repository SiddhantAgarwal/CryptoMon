package com.siddhantagarwal.cryptomon

/**
 * Created by siddhant on 01/12/17.
 */
data class Transaction(val currencyCode: String,
                       val amount: Double,
                       val date: Long,
                       val valueAtInvestment: Double)