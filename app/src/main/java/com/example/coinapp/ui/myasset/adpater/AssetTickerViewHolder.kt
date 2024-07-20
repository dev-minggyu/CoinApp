package com.example.coinapp.ui.myasset.adpater

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.R
import com.example.coinapp.databinding.ItemAssetTickerBinding
import com.example.domain.model.myasset.MyTicker
import java.text.NumberFormat

class AssetTickerViewHolder(private val binding: ItemAssetTickerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: MyTicker) {
        val priceFormat = NumberFormat.getInstance()
        val currentValue = item.amount.toDouble() * item.currentPrice.toDouble()
        val buyValue = item.amount.toDouble() * item.averagePrice.toDouble()

        binding.apply {
            ticker = item
            tvPnl.setTextColor(
                when {
                    currentValue - buyValue > 0 -> ContextCompat.getColor(
                        itemView.context,
                        R.color.color_price_up
                    )

                    currentValue - buyValue < 0 -> ContextCompat.getColor(
                        itemView.context,
                        R.color.color_price_down
                    )

                    else -> ContextCompat.getColor(itemView.context, R.color.color_price_same)
                }
            )
            tvPnlPercent.setTextColor(
                when {
                    currentValue - buyValue > 0 -> ContextCompat.getColor(
                        itemView.context,
                        R.color.color_price_up
                    )

                    currentValue - buyValue < 0 -> ContextCompat.getColor(
                        itemView.context,
                        R.color.color_price_down
                    )

                    else -> ContextCompat.getColor(itemView.context, R.color.color_price_same)
                }
            )
            tvPnl.text = priceFormat.format(currentValue - buyValue)
            tvPnlPercent.text =
                String.format("%.2f", ((currentValue - buyValue) / buyValue) * 100) + "%"
            tvAmount.text = priceFormat.format(item.amount.toDouble())
            tvAveragePrice.text = priceFormat.format(item.averagePrice.toFloat())
            tvPriceValue.text = priceFormat.format(currentValue)
            tvBuyPrice.text = priceFormat.format(buyValue)

            executePendingBindings()
        }
    }
}