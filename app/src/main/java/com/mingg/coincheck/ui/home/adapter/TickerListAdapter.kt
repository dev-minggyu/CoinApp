package com.mingg.coincheck.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mingg.coincheck.databinding.ItemTickerBinding
import com.mingg.coincheck.ui.base.BaseListAdapter
import com.mingg.domain.model.ticker.Ticker

class TickerListAdapter(
    private val favoriteClickListener: FavoriteClickListener?,
    private val tickerClickListener: (Ticker) -> Unit
) : BaseListAdapter<Ticker, TickerViewHolder>() {

    private var tickerChangeColor = true

    fun setTickerChangeColor(value: Boolean) {
        tickerChangeColor = value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TickerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTickerBinding.inflate(layoutInflater, parent, false)
        return TickerViewHolder(binding, favoriteClickListener)
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

    interface FavoriteClickListener {
        fun onAddFavorite(symbol: String)
        fun onDeleteFavorite(symbol: String)
    }
}

