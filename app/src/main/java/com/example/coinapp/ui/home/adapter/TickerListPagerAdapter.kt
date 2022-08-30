package com.example.coinapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.App
import com.example.coinapp.R
import com.example.coinapp.databinding.ItemTickerListBinding

class TickerListPagerAdapter(
    private val mainAdapter: TickerListAdapter,
    private val favoriteAdapter: TickerListAdapter
) : RecyclerView.Adapter<TickerListPagerAdapter.TickerListsViewHolder>() {

    override fun getItemCount(): Int {
        return VIEW_LIST_COUNT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TickerListsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTickerListBinding.inflate(layoutInflater, parent, false)

        binding.tickerList.apply {
            itemAnimator = null
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }

        return TickerListsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TickerListsViewHolder, position: Int) {
        when (position) {
            VIEW_LIST_ALL -> holder.setListAdapter(mainAdapter)
            VIEW_LIST_FAVORITE -> holder.setListAdapter(favoriteAdapter)
        }
    }

    fun getListTitle(position: Int): String {
        return when (position) {
            VIEW_LIST_ALL -> App.getString(R.string.tab_ticker_list_all)
            VIEW_LIST_FAVORITE -> App.getString(R.string.tab_ticker_list_favorite)
            else -> ""
        }
    }

    class TickerListsViewHolder(private val binding: ItemTickerListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setListAdapter(adapter: TickerListAdapter) {
            binding.tickerList.adapter = adapter
        }
    }

    companion object {
        const val VIEW_LIST_ALL = 0
        const val VIEW_LIST_FAVORITE = 1
        const val VIEW_LIST_COUNT = 2
    }
}