package com.mingg.coincheck.ui.myasset.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mingg.coincheck.databinding.ItemAssetHeaderBinding
import com.mingg.coincheck.databinding.ItemAssetTickerBinding
import com.mingg.coincheck.ui.myasset.model.MyAssetItem
import com.mingg.coincheck.ui.base.BaseListAdapter
import com.mingg.domain.model.myasset.MyTicker

class MyAssetListAdapter(
    private val assetClickListener: (MyTicker) -> Unit
) : BaseListAdapter<MyAssetItem, RecyclerView.ViewHolder>() {

    fun submitAssetList(list: List<MyAssetItem>) {
        val items = list.map { item ->
            when (item) {
                is MyAssetItem.Header -> MyAssetItem.Header(item.header)
                is MyAssetItem.Ticker -> MyAssetItem.Ticker(item.ticker)
            }
        }
        submitList(items)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MyAssetItem.Header -> HEADER
            is MyAssetItem.Ticker -> TICKER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER -> AssetHeaderViewHolder(
                ItemAssetHeaderBinding.inflate(layoutInflater, parent, false)
            )

            TICKER -> AssetTickerViewHolder(
                ItemAssetTickerBinding.inflate(layoutInflater, parent, false)
            )

            else -> throw IllegalArgumentException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is MyAssetItem.Header -> {
                (holder as AssetHeaderViewHolder).bind(item.header)
            }

            is MyAssetItem.Ticker -> {
                (holder as AssetTickerViewHolder).apply {
                    itemView.setOnClickListener { assetClickListener.invoke(item.ticker) }
                    bind(item.ticker)
                }
            }
        }
    }

    private companion object {
        private const val HEADER = 0
        private const val TICKER = 1
    }
}