package com.example.coinapp.ui.myasset.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.databinding.ItemAssetHeaderBinding
import com.example.coinapp.databinding.ItemAssetTickerBinding
import com.example.coinapp.model.MyAssetItem
import com.example.domain.model.myasset.MyTicker

class MyAssetListAdapter(
    private val assetClickListener: (MyTicker) -> Unit
) : ListAdapter<MyAssetItem, RecyclerView.ViewHolder>(TickerDiffCallback()) {

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

    class TickerDiffCallback : DiffUtil.ItemCallback<MyAssetItem>() {
        override fun areItemsTheSame(oldItem: MyAssetItem, newItem: MyAssetItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyAssetItem, newItem: MyAssetItem): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val HEADER = 0
        private const val TICKER = 1
    }
}
