package com.example.coinapp.databinding

import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import com.example.coinapp.ui.custom.SortButton
import com.example.coinapp.ui.home.adapter.TickerListAdapter
import com.example.domain.model.ticker.SortModel
import com.google.android.material.bottomnavigation.BottomNavigationView

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("onItemSelectedListener")
    fun bindOnItemSelectedListener(
        view: BottomNavigationView, function: (Int) -> Unit
    ) {
        view.setOnItemSelectedListener { item ->
            function(item.itemId)
            true
        }
    }

    @JvmStatic
    @BindingAdapter("onSortChanged")
    fun bindOnSortChanged(
        view: SortButton, function: (SortModel) -> Unit
    ) {
        view.setOnSortChangedListener(object : SortButton.OnSortChangedListener {
            override fun onChanged(sortModel: SortModel) {
                function(sortModel)
            }
        })
    }

    @JvmStatic
    @BindingAdapter("sortState")
    fun bindSortState(
        view: SortButton, sortModel: SortModel?
    ) {
        sortModel?.let {
            view.setSortState(it)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["tickerSymbol", "favoriteClickListener"], requireAll = true)
    fun bindOnFavoriteClickListener(
        view: ToggleButton,
        symbol: String,
        favoriteClickListener: TickerListAdapter.FavoriteClickListener
    ) {
        view.setOnClickListener {
            if (view.isChecked) {
                favoriteClickListener.onAddFavorite(symbol)
            } else {
                favoriteClickListener.onDeleteFavorite(symbol)
            }
        }
    }
}