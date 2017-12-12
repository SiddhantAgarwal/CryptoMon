package com.siddhantagarwal.cryptomon.models

import com.orm.SugarRecord
import com.siddhantagarwal.cryptomon.Utility
import org.json.JSONObject

/**
 * Created by siddhant on 29/11/17.
 */

/**
 * Didn't use kotlin data class because need a empty constructor
 */


class Currency(): SugarRecord() {
    // codebeat:enable[TOO_MANY_IVARS,ARITY]
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
    // codebeat:disable[TOO_MANY_IVARS,ARITY]

    companion object {

        fun listAllCurrencies(): ArrayList<Currency>? {
            val listOfCurrencies: ArrayList<Currency> = ArrayList()
            SugarRecord.findAll(Currency::class.java).forEach {
                it?.let {
                    listOfCurrencies.add(it)
                }
            }
            return if (listOfCurrencies.size > 0) {
                listOfCurrencies
            } else {
                null
            }
        }

        fun findCurrencyByCode(code: String): Currency? {
            val find = SugarRecord.find(Currency::class.java, "CODE = '$code'")
            return if (find.size > 0) {
                find[0]
            } else {
                null
            }
        }

        fun getRateForCurrency(code: String): Double? {
            val find = SugarRecord.find(Currency::class.java, "CODE = '$code'")
            return if (find.size > 0) {
                find[0].value
            } else {
                null
            }
        }

        fun syncFromServer() {
            val response = Utility.fetchDataFromURL("https://koinex.in/api/ticker")
            response?.let {
                val currencyList = JSONObject(it).getJSONObject("prices")
                val map = HashMap<String, HashMap<String, String>>()
                for (key in currencyList.keys()) {
                    map[key] = HashMap()
                    map[key]?.set("value", currencyList[key].toString())
                }
                val detailList = JSONObject(it).getJSONObject("stats")
                for (key in detailList.keys()) {
                    val subDetailList = JSONObject(detailList[key].toString())
                    map[key]?.set("ltp", subDetailList["last_traded_price"].toString())
                    map[key]?.set("la", subDetailList["lowest_ask"].toString())
                    map[key]?.set("hb", subDetailList["highest_bid"].toString())
                }
                for (entry in map) {
                    val record = SugarRecord.find(Currency::class.java,
                            "CODE = '${entry.key}'")
                    if (record.size != 0) {
                        record[0].apply {
                            value = entry.value["value"]?.toDouble()
                            ltp = entry.value["ltp"]?.toDouble()
                            la = entry.value["la"]?.toDouble()
                            hb = entry.value["hb"]?.toDouble()
                        }.save()
                    } else {
                        Currency(entry.key,
                                entry.value["value"]?.toDouble(),
                                entry.value["ltp"]?.toDouble(),
                                entry.value["la"]?.toDouble(),
                                entry.value["hb"]?.toDouble()
                        ).save()
                    }
                }
            }
        }
    }
}