package com.example.domain.model.myasset

import com.example.domain.model.ticker.Currency

data class MyTicker(
    var symbol: String,
    val koreanSymbol: String,
    val englishSymbol: String,
    var currencyType: Currency,
    var amount: String,
    var averagePrice: String
)