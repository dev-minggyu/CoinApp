package com.example.domain.model.ticker

enum class Currency(val currencyFormat: String?, val volumeDivider: Int?) {
    KRW("#,###.####", 1_000_000),
    BTC(null, null),
    USDT("#,###.####", null)
}