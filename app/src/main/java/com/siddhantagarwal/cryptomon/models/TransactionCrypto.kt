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
}