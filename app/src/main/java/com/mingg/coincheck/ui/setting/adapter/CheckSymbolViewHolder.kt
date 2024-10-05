package com.mingg.coincheck.ui.setting.adapter

import androidx.recyclerview.widget.RecyclerView
import com.mingg.coincheck.databinding.ItemCheckSymbolBinding
import com.mingg.domain.model.setting.FloatingTicker

class CheckSymbolViewHolder(private val binding: ItemCheckSymbolBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: FloatingTicker) {
        binding.symbol = item
        binding.executePendingBindings()
    }
}