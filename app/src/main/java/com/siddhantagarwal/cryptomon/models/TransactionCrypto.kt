package com.siddhantagarwal.cryptomon.models

import com.orm.SugarRecord

/**
 * Created by siddhant on 01/12/17.
 */
class TransactionCrypto(): SugarRecord() {
    var currencyCode: String? = null
    var amount: Double? = null
    var quantity: Double? = null
    var rate: Double? = null
    constructor(currencyCode: String, amount: Double, quantity: Double, rate: Double) : this() {
        this.currencyCode = currencyCode
        this.amount = amount
        this.quantity = quantity
        this.rate = rate
    }

    companion object {
        fun compareWithCurrentPrice(rate: Double, currencyCode: String): Int {
            val find = SugarRecord.find(Currency::class.java,
                    "CODE = '$currencyCode'")
            find[0]?.let {
                return when {
                    it.value!! > rate -> 1
                    it.value!! == rate -> 0
                    it.value!! < rate -> -1
                    else -> -999
                }
            }
            return -999
        }
    }
}