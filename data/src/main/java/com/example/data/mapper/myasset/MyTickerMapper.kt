package com.example.data.mapper.myasset

import com.example.data.model.myasset.MyTickerEntity
import com.example.domain.model.myasset.MyTicker
import com.example.domain.model.ticker.Currency

object MyTickerMapper {
    fun toMyTicker(myTickerEntity: MyTickerEntity): MyTicker {
        val splitSymbol = myTickerEntity.symbol.split("-")
        return MyTicker(
            splitSymbol[1],
            currencyType = Currency.valueOf(splitSymbol[0]),
            amount = myTickerEntity.amount,
            averagePrice = myTickerEntity.averagePrice
        )
    }

    fun toMyTickerEntity(myTicker: MyTicker): MyTickerEntity =
        MyTickerEntity(
            symbol = myTicker.currencyType.name + "-" + myTicker.symbol,
            amount = myTicker.amount,
            averagePrice = myTicker.averagePrice
        )
}