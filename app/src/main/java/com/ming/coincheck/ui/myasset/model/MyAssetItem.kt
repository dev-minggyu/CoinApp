package com.ming.coincheck.ui.myasset.model

import com.ming.domain.model.Diffable
import com.ming.domain.model.myasset.MyTicker

sealed class MyAssetItem : Diffable<MyAssetItem> {
    abstract val id: String

    data class Header(val header: MyAssetHeader) : MyAssetItem() {
        override val id: String = "HEADER"

        override fun areItemsTheSame(other: MyAssetItem): Boolean {
            return this.id == other.id
        }

        override fun areContentsTheSame(other: MyAssetItem): Boolean {
            return this == other
        }
    }

    data class Ticker(val ticker: MyTicker) : MyAssetItem() {
        override val id: String = ticker.symbol

        override fun areItemsTheSame(other: MyAssetItem): Boolean {
            return this.id == other.id
        }

        override fun areContentsTheSame(other: MyAssetItem): Boolean {
            return this == other
        }
    }
}