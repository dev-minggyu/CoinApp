package com.mingg.coincheck.ui.setting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mingg.coincheck.databinding.ItemCheckSymbolBinding
import com.mingg.coincheck.ui.base.BaseListAdapter
import com.mingg.domain.model.setting.FloatingTicker

class CheckTickerListAdapter : BaseListAdapter<FloatingTicker, CheckSymbolViewHolder>() {

    override fun onBindViewHolder(holder: CheckSymbolViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckSymbolViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCheckSymbolBinding.inflate(layoutInflater, parent, false)
        return CheckSymbolViewHolder(binding)
    }
}