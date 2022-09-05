package com.example.coinapp.provider

import com.example.coinapp.App
import com.example.coinapp.R
import com.example.coinapp.extension.formatWithPlusSignPrefix
import com.example.data.mapper.ticker.TickerMapperProvider
import com.example.data.model.ticker.TickerResponse
import com.example.domain.model.ticker.Currency
import com.example.domain.model.ticker.Ticker
import java.text.NumberFormat
import javax.inject.Inject

class TickerMapperProviderImpl @Inject constructor() : TickerMapperProvider {
    override fun mapperToTicker(tickerResponse: TickerResponse): Ticker {
        return tickerResponse.run {
            val splitCode = code.split("-")
            val currencyType = Currency.valueOf(splitCode[0])

            var decimalCurrentPrice = ""
            var changePricePrevDay = ""
            var formattedVolume = ""

            val priceFormat = NumberFormat.getInstance().apply {
                maximumFractionDigits = currencyType.priceDecimalDigits
            }
            val volumeFormat = NumberFormat.getInstance().apply {
                maximumFractionDigits = currencyType.volumeDecimalDigits
            }

            when (currencyType) {
                Currency.KRW -> {
                    decimalCurrentPrice = priceFormat.format(trade_price)
                    changePricePrevDay = priceFormat.formatWithPlusSignPrefix(signed_change_price)
                    val dividedValue = (acc_trade_price_24h / 1_000_000).toInt()
                    formattedVolume = volumeFormat.format(dividedValue)
                    formattedVolume += if (dividedValue < 1) {
                        App.getString(R.string.unit_won)
                    } else {
                        App.getString(R.string.unit_million)
                    }
                }
                Currency.BTC -> {
                    decimalCurrentPrice = priceFormat.format(trade_price)
                    changePricePrevDay = priceFormat.format(signed_change_price)
                    formattedVolume = volumeFormat.format(acc_trade_price_24h)
                }
                Currency.USDT -> {
                    decimalCurrentPrice = priceFormat.format(trade_price)
                    changePricePrevDay = priceFormat.formatWithPlusSignPrefix(signed_change_price)
                    formattedVolume = volumeFormat.format(acc_trade_price_24h)
                }
            }

            Ticker(
                symbol = splitCode[1],
                currencyType = currencyType,
                currentPrice = trade_price.toString(),
                decimalCurrentPrice = decimalCurrentPrice,
                changePricePrevDay = changePricePrevDay,
                rate = String.format("%.2f", signed_change_rate * 100),
                volume = acc_trade_price_24h.toString(),
                formattedVolume = formattedVolume
            )
        }
    }
}