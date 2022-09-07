package com.example.coinapp.ui.setting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.databinding.ItemCheckSymbolBinding
import com.example.domain.model.setting.FloatingTicker

class CheckSymbolListAdapter : ListAdapter<FloatingTicker, CheckSymbolListAdapter.SymbolViewHolder>(SymbolDiffCallback()) {
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

    class SymbolDiffCallback : DiffUtil.ItemCallback<FloatingTicker>() {
        override fun areItemsTheSame(oldItem: FloatingTicker, newItem: FloatingTicker): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: FloatingTicker, newItem: FloatingTicker): Boolean {
            return oldItem == newItem
        }
    }
}
