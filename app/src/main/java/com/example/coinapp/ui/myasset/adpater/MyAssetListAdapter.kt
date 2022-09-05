package com.example.coinapp.ui.myasset.adpater

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.R
import com.example.coinapp.databinding.ItemAssetBinding
import com.example.domain.model.myasset.MyTicker
import java.text.DecimalFormat

class MyAssetListAdapter(
    private val assetClickListener: (MyTicker) -> Unit
) : ListAdapter<MyTicker, MyAssetListAdapter.MyAssetViewHolder>(TickerDiffCallback()) {
    override fun onBindViewHolder(holder: MyAssetViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            assetClickListener.invoke(item)
        }
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAssetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAssetBinding.inflate(layoutInflater, parent, false)
        return MyAssetViewHolder(binding)
    }

    inner class MyAssetViewHolder(private val binding: ItemAssetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: MyTicker) {
            val priceFormat = DecimalFormat("#,###")
            val currentValue = item.amount.toDouble() * item.currentPrice.toDouble()
            val buyValue = item.amount.toDouble() * item.averagePrice.toDouble()

            binding.apply {
                ticker = item
                tvPnl.setTextColor(
                    when {
                        currentValue - buyValue > 0 -> ContextCompat.getColor(itemView.context, R.color.color_price_up)
                        currentValue - buyValue < 0 -> ContextCompat.getColor(itemView.context, R.color.color_price_down)
                        else -> ContextCompat.getColor(itemView.context, R.color.color_price_same)
                    }
                )
                tvPnlPercent.setTextColor(
                    when {
                        currentValue - buyValue > 0 -> ContextCompat.getColor(itemView.context, R.color.color_price_up)
                        currentValue - buyValue < 0 -> ContextCompat.getColor(itemView.context, R.color.color_price_down)
                        else -> ContextCompat.getColor(itemView.context, R.color.color_price_same)
                    }
                )
                tvPnl.text = priceFormat.format(currentValue - buyValue)
                tvPnlPercent.text = String.format("%.2f", ((currentValue - buyValue) / buyValue) * 100) + "%"
                tvAmount.text = priceFormat.format(item.amount.toDouble())
                tvAveragePrice.text = priceFormat.format(item.averagePrice.toFloat())
                tvPriceValue.text = priceFormat.format(currentValue)
                tvBuyPrice.text = priceFormat.format(buyValue)

                executePendingBindings()
            }
        }
    }

    class TickerDiffCallback : DiffUtil.ItemCallback<MyTicker>() {
        override fun areItemsTheSame(oldItem: MyTicker, newItem: MyTicker): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: MyTicker, newItem: MyTicker): Boolean {
            return oldItem == newItem
        }
    }
}
