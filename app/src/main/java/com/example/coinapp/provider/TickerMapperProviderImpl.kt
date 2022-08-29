package com.example.coinapp.provider

import com.example.data.model.ticker.TickerResponse
import com.example.data.provider.TickerMapperProvider
import com.example.domain.model.ticker.Currency
import com.example.domain.model.ticker.Ticker
import java.text.DecimalFormat
import javax.inject.Inject

class TickerMapperProviderImpl @Inject constructor() : TickerMapperProvider {
    override fun mapperToTicker(tickerResponse: TickerResponse): Ticker {
        return tickerResponse.run {
            val splitCode = code.split("-")
            val currencyType = Currency.valueOf(splitCode[0])

            var currentPrice = ""
            var prevPrice = ""
            currencyType.currencyFormat?.let { format ->
                val priceFormat = DecimalFormat(format)
                currentPrice = priceFormat.format(trade_price)
                prevPrice = priceFormat.format(prev_closing_price)
            }

            var volume = ""
            currencyType.volumeDivider?.let { divider ->
                volume = DecimalFormat("#,###").format((acc_trade_price_24h / divider).toInt())
            }

            Ticker(
                symbol = splitCode[1],
                currencyType = currencyType,
                currentPrice = currentPrice,
                prevPrice = prevPrice,
                rate = String.format("%.2f", ((trade_price - prev_closing_price) / prev_closing_price) * 100) + "%",
                volume = volume
            )
        }
    }
}