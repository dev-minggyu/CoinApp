package com.example.coinapp.model.myasset

import com.example.domain.model.myasset.MyTicker

sealed class MyAssetItem {
    abstract val id: String

    data class Ticker(val ticker: MyTicker) : MyAssetItem() {
        override val id: String = ticker.symbol
    }

    data class Header(val header: MyAssetHeader) : MyAssetItem() {
        override val id: String = "HEADER"
    }
}
