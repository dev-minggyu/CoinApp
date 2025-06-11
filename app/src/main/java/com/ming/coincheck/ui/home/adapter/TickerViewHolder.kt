package com.ming.coincheck.ui.home.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ming.coincheck.R
import com.ming.coincheck.databinding.ItemTickerBinding
import com.ming.domain.model.ticker.Ticker

class TickerViewHolder(
    private val binding: ItemTickerBinding,
    private val favoriteClickListener: TickerListAdapter.FavoriteClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    private val startColor = ContextCompat.getColor(itemView.context, R.color.color_background_regular2)
    private val priceUpColor = ContextCompat.getColor(itemView.context, R.color.color_price_up_transparent)
    private val priceDownColor = ContextCompat.getColor(itemView.context, R.color.color_price_down_transparent)

    fun bind(item: Ticker) {
        binding.ticker = item
        binding.favoriteClickListener = favoriteClickListener
        binding.executePendingBindings()
    }

    fun bindWithColorChange(item: Ticker) {
        val prevSymbol = binding.tvCurrency.text.toString()
        val currentSymbol = item.symbol + "/" + item.currencyType.name
        if (prevSymbol != currentSymbol) {
            bind(item)
            return
        }
        val prevPrice = binding.tvPrice.tag.toString().toFloat()
        val currentPrice = item.currentPrice.toFloat()
        if (prevPrice != currentPrice) {
            val endColor = if (prevPrice < currentPrice) priceUpColor else priceDownColor
            itemView.setBackgroundColor(endColor)
            itemView.postDelayed({
                itemView.setBackgroundColor(startColor)
            }, 200)
        }
        bind(item)
    }
}