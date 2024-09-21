package com.mingg.network.mapper.myasset

import com.mingg.domain.model.myasset.MyTicker
import com.mingg.domain.model.ticker.Currency
import com.mingg.database.data.myasset.MyTickerEntity

object MyTickerMapper {
    fun toMyTicker(myTickerEntity: MyTickerEntity): MyTicker =
        MyTicker(
            symbol = myTickerEntity.symbol,
            currencyType = Currency.valueOf(myTickerEntity.currency),
            koreanSymbol = myTickerEntity.koreanSymbol,
            englishSymbol = myTickerEntity.englishSymbol,
            amount = myTickerEntity.amount,
            averagePrice = myTickerEntity.averagePrice
        )

    fun toMyTickerEntity(myTicker: MyTicker): MyTickerEntity =
        MyTickerEntity(
            symbol = myTicker.symbol,
            currency = myTicker.currencyType.name,
            koreanSymbol = myTicker.koreanSymbol,
            englishSymbol = myTicker.englishSymbol,
            amount = myTicker.amount,
            averagePrice = myTicker.averagePrice
        )
}