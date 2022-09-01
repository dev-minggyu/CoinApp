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

            var decimalCurrentPrice = ""
            var changePricePrevDay = ""
            currencyType.currencyFormat?.let { format ->
                val priceFormat = DecimalFormat(format)
                decimalCurrentPrice = priceFormat.format(trade_price)
                changePricePrevDay = priceFormat.format(signed_change_price)
            }

            var dividedVolume = ""
            currencyType.volumeDivider?.let { divider ->
                dividedVolume = DecimalFormat("#,###").format((acc_trade_price_24h / divider).toInt())
            }

            Ticker(
                symbol = splitCode[1],
                currencyType = currencyType,
                prevPrice = trade_price.toString(),
                currentPrice = trade_price.toString(),
                decimalCurrentPrice = decimalCurrentPrice,
                changePricePrevDay = changePricePrevDay,
                decimalPrevPrice = prev_closing_price.toString(),
                rate = String.format("%.2f", signed_change_rate * 100),
                volume = acc_trade_price_24h.toString(),
                dividedVolume = dividedVolume
            )
        }
    }
}