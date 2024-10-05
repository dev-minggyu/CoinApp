package com.mingg.coincheck.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.ItemTickerBinding
import com.mingg.coincheck.ui.base.BaseListAdapter
import com.mingg.domain.model.ticker.Ticker

class TickerListAdapter(
    private val favoriteClickListener: FavoriteClickListener?,
    private val tickerClickListener: (Ticker) -> Unit
) : BaseListAdapter<Ticker, TickerListAdapter.TickerViewHolder>() {

    private var tickerChangeColor = true

    fun setTickerChangeColor(value: Boolean) {
        tickerChangeColor = value
    }

    override fun onBindViewHolder(holder: TickerViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            tickerClickListener.invoke(item)
        }
        if (tickerChangeColor) {
            holder.bindWithColorChange(item)
        } else {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TickerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTickerBinding.inflate(layoutInflater, parent, false)
        return TickerViewHolder(binding)
    }

    inner class TickerViewHolder(private val binding: ItemTickerBinding) :
        RecyclerView.ViewHolder(binding.root) {

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

    interface FavoriteClickListener {
        fun onAddFavorite(symbol: String)
        fun onDeleteFavorite(symbol: String)
    }
}