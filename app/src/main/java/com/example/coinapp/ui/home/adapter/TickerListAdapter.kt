package com.example.coinapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.R
import com.example.coinapp.databinding.ItemTickerBinding
import com.example.domain.model.ticker.Ticker

class TickerListAdapter(
    private val favoriteClickListener: FavoriteClickListener?,
    private val tickerClickListener: (Ticker) -> Unit
) : ListAdapter<Ticker, TickerListAdapter.TickerViewHolder>(
    TickerDiffCallback()
) {

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
            holder.bind(holder.itemView, item)
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

        fun bind(item: Ticker) {
            binding.ticker = item
            binding.favoriteClickListener = favoriteClickListener
            binding.executePendingBindings()
        }

        fun bind(itemView: View, item: Ticker) {
            val prevSymbol = binding.tvCurrency.text.toString()
            val currentSymbol = item.symbol + "/" + item.currencyType.name
            if (prevSymbol != currentSymbol) {
                bind(item)
                return
            }
            val prevPrice = binding.tvPrice.tag.toString().toFloat()
            val currentPrice = item.currentPrice.toFloat()
            if (prevPrice != currentPrice) {
                itemView.apply {
                    val startColor =
                        ContextCompat.getColor(context, R.color.color_background_regular2)
                    val endColor = if (prevPrice < currentPrice) {
                        ContextCompat.getColor(context, R.color.color_price_up_transparent)
                    } else {
                        ContextCompat.getColor(context, R.color.color_price_down_transparent)
                    }
                    setBackgroundColor(endColor)
                    postDelayed({
                        setBackgroundColor(startColor)
                    }, 200)
                }
            }
            bind(item)
        }
    }

    class TickerDiffCallback : DiffUtil.ItemCallback<Ticker>() {
        override fun areItemsTheSame(
            oldItem: Ticker,
            newItem: Ticker
        ): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(
            oldItem: Ticker,
            newItem: Ticker
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface FavoriteClickListener {
        fun onAddFavorite(symbol: String)
        fun onDeleteFavorite(symbol: String)
    }
}
