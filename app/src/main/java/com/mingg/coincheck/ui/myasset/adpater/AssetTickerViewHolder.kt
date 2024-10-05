package com.mingg.coincheck.ui.myasset.adpater

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.ItemAssetTickerBinding
import com.mingg.domain.model.myasset.MyTicker
import java.text.NumberFormat

class AssetTickerViewHolder(private val binding: ItemAssetTickerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val colorPriceUp = ContextCompat.getColor(itemView.context, R.color.color_price_up)
    private val colorPriceDown = ContextCompat.getColor(itemView.context, R.color.color_price_down)
    private val colorPriceSame = ContextCompat.getColor(itemView.context, R.color.color_price_same)

    @SuppressLint("SetTextI18n")
    fun bind(item: MyTicker) {
        val priceFormat = NumberFormat.getInstance()
        val amount = item.amount.toDouble()
        val currentPrice = item.currentPrice.toDouble()
        val averagePrice = item.averagePrice.toDouble()

        val currentValue = amount * currentPrice
        val buyValue = amount * averagePrice
        val pnl = currentValue - buyValue
        val pnlPercent = if (buyValue != 0.0) (pnl / buyValue) * 100 else 0.0

        binding.apply {
            ticker = item
            val pnlColor = getPnlColor(pnl)
            tvPnl.setTextColor(pnlColor)
            tvPnlPercent.setTextColor(pnlColor)
            tvPnl.text = priceFormat.format(pnl)
            tvPnlPercent.text = String.format("%.2f", pnlPercent) + "%"
            tvAmount.text = priceFormat.format(amount)
            tvAveragePrice.text = priceFormat.format(averagePrice)
            tvPriceValue.text = priceFormat.format(currentValue)
            tvBuyPrice.text = priceFormat.format(buyValue)

            executePendingBindings()
        }
    }

    private fun getPnlColor(pnl: Double): Int {
        return when {
            pnl > 0 -> colorPriceUp
            pnl < 0 -> colorPriceDown
            else -> colorPriceSame
        }
    }
}