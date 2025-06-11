package com.ming.network.mapper.ticker

import com.ming.common.extension.formatWithPlusSignPrefix
import com.ming.domain.model.ticker.Currency
import com.ming.domain.model.ticker.Ticker
import com.ming.network.data.response.TickerResponse
import java.text.NumberFormat
import javax.inject.Inject

class TickerResponseMapperImpl @Inject constructor() : TickerResponseMapper {
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

            var isVolumeDividedByMillion = false

            when (currencyType) {
                Currency.KRW -> {
                    decimalCurrentPrice = priceFormat.format(trade_price ?: 0.0)
                    changePricePrevDay = priceFormat.formatWithPlusSignPrefix(signed_change_price ?: 0.0)

                    val accTradePrice = acc_trade_price_24h ?: 0.0
                    val dividedValue = (accTradePrice / 1_000_000)

                    formattedVolume = if (dividedValue < 1) {
                        volumeFormat.format(accTradePrice)
                    } else {
                        isVolumeDividedByMillion = true
                        volumeFormat.format(dividedValue)
                    }
                }

                Currency.BTC -> {
                    decimalCurrentPrice = priceFormat.format(trade_price ?: 0.0)
                    changePricePrevDay = priceFormat.formatWithPlusSignPrefix(signed_change_price ?: 0.0)
                    formattedVolume = volumeFormat.format(acc_trade_price_24h)
                }

                Currency.USDT -> {
                    decimalCurrentPrice = priceFormat.format(trade_price ?: 0.0)
                    changePricePrevDay = priceFormat.formatWithPlusSignPrefix(signed_change_price ?: 0.0)
                    formattedVolume = volumeFormat.format(acc_trade_price_24h)
                }
            }

            Ticker(
                symbol = splitCode[1],
                currencyType = currencyType,
                currentPrice = (trade_price ?: 0.0).toString(),
                decimalCurrentPrice = decimalCurrentPrice,
                changePricePrevDay = changePricePrevDay,
                rate = String.format("%.2f", (signed_change_rate ?: 0.0) * 100),
                volume = acc_trade_price_24h.toString(),
                formattedVolume = formattedVolume,
                isVolumeDividedByMillion = isVolumeDividedByMillion
            )
        }
    }
}