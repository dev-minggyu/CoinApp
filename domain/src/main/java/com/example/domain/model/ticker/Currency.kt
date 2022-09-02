package com.example.domain.model.ticker

enum class Currency(val currencyFormat: String, val volumeFormat: String) {
    KRW("#,###.####", "#,###"),
    BTC("%.8f", "%.3f"),
    USDT("#,###.###", "#,###")
}