package com.mingg.coincheck.ui.myasset.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mingg.coincheck.databinding.ItemAssetHeaderBinding
import com.mingg.coincheck.databinding.ItemAssetTickerBinding
import com.mingg.coincheck.model.myasset.MyAssetItem
import com.mingg.coincheck.ui.base.BaseListAdapter
import com.mingg.domain.model.myasset.MyTicker

class MyAssetListAdapter(
    private val assetClickListener: (MyTicker) -> Unit
) : BaseListAdapter<MyAssetItem, RecyclerView.ViewHolder>() {

    fun submitAssetList(list: MutableList<MyAssetItem>) {
        val items = listOf(MyAssetItem.Header((list[0] as MyAssetItem.Header).header)) +
                list.slice(1 until list.size).map {
                    MyAssetItem.Ticker((it as MyAssetItem.Ticker).ticker)
                }
        submitList(items)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MyAssetItem.Header -> HEADER
            is MyAssetItem.Ticker -> TICKER
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AssetHeaderViewHolder -> {
                val item = getItem(position) as MyAssetItem.Header
                holder.bind(item.header)
            }

            is AssetTickerViewHolder -> {
                val item = getItem(position) as MyAssetItem.Ticker
                holder.itemView.setOnClickListener {
                    assetClickListener.invoke(item.ticker)
                }
                holder.bind(item.ticker)
            }
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

    companion object {
        private const val HEADER = 0
        private const val TICKER = 1
    }
}
