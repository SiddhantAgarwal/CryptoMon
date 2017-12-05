package com.siddhantagarwal.cryptomon.models

import com.orm.SugarRecord

/**
 * Created by siddhant on 29/11/17.
 */

/**
 * Didn't use kotlin data class because need a empty constructor
 */

class Currency(): SugarRecord() {
    var code: String? = ""
    var value: Double? = 0.0
    var ltp: Double? = null
    var la: Double? = null
    var hb: Double? = null
    constructor(code: String?, value: Double?, ltp: Double?, la: Double?, hb: Double?) : this() {
        this.code = code
        this.value = value
        this.ltp = ltp
        this.la = la
        this.hb = hb
    }
}