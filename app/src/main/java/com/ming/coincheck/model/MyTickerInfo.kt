package com.ming.coincheck.model

import android.os.Parcelable
import com.ming.domain.model.myasset.MyTicker
import com.ming.domain.model.ticker.Currency
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyTickerInfo(
    var symbol: String,
    var currency: Currency,
    val koreanSymbol: String,
    val englishSymbol: String,
    var amount: String = "",
    var averagePrice: String = ""
) : Parcelable {
    companion object {
        fun toMyTicker(myTickerInfo: MyTickerInfo): MyTicker =
            MyTicker(
                symbol = myTickerInfo.symbol,
                koreanSymbol = myTickerInfo.koreanSymbol,
                englishSymbol = myTickerInfo.englishSymbol,
                currencyType = myTickerInfo.currency,
                "",
                ""
            )
    }
}