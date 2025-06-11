package com.ming.domain.model.myasset

import com.ming.domain.model.ticker.Currency

data class MyTicker(
    var symbol: String,
    val koreanSymbol: String,
    val englishSymbol: String,
    var currencyType: Currency,
    var amount: String,
    var averagePrice: String,
    var currentPrice: String = ""
)