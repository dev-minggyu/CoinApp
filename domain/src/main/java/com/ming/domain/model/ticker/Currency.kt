package com.ming.domain.model.ticker

enum class Currency(val priceDecimalDigits: Int, val volumeDecimalDigits: Int) {
    KRW(4, 0),
    BTC(8, 3),
    USDT(3, 0)
}