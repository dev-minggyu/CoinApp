package com.example.coinapp.ui.floating.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.databinding.ItemFloatingTickerBinding
import com.example.domain.model.ticker.Ticker

class FloatingListAdapter :
    ListAdapter<Ticker, FloatingListAdapter.FloatingViewHolder>(
        TickerDiffCallback()
    ) {
    override fun onBindViewHolder(holder: FloatingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloatingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFloatingTickerBinding.inflate(layoutInflater, parent, false)
        return FloatingViewHolder(binding)
    }

    inner class FloatingViewHolder(private val binding: ItemFloatingTickerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Ticker) {
            binding.ticker = item
            binding.executePendingBindings()
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
}
