package com.example.domain.model.ticker

enum class Currency(val currencyFormat: String?) {
    KRW("#,###.####"),
    BTC(null),
    USDT("#,###.####")
}