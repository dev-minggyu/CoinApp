package com.mingg.coincheck.ui.setting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mingg.coincheck.databinding.ItemCheckSymbolBinding
import com.mingg.coincheck.ui.base.BaseListAdapter
import com.mingg.domain.model.setting.FloatingTicker

class CheckSymbolListAdapter : BaseListAdapter<FloatingTicker, CheckSymbolListAdapter.SymbolViewHolder>() {

    override fun onBindViewHolder(holder: SymbolViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymbolViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCheckSymbolBinding.inflate(layoutInflater, parent, false)
        return SymbolViewHolder(binding)
    }

    inner class SymbolViewHolder(private val binding: ItemCheckSymbolBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FloatingTicker) {
            binding.symbol = item
            binding.executePendingBindings()
        }
    }
}
