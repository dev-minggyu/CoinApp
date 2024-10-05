package com.mingg.coincheck.ui.floating.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mingg.coincheck.databinding.ItemFloatingTickerBinding
import com.mingg.coincheck.ui.base.BaseListAdapter
import com.mingg.domain.model.ticker.Ticker

class FloatingListAdapter : BaseListAdapter<Ticker, FloatingListAdapter.FloatingViewHolder>() {
    override fun onBindViewHolder(holder: FloatingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloatingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFloatingTickerBinding.inflate(layoutInflater, parent, false)
        return FloatingViewHolder(binding)
    }

    class FloatingViewHolder(private val binding: ItemFloatingTickerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Ticker) {
            binding.ticker = item
            binding.executePendingBindings()
        }
    }
}
