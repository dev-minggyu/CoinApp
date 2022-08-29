package com.example.coinapp.databinding

import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import com.example.coinapp.enums.SortCategory
import com.example.coinapp.enums.SortModel
import com.example.coinapp.enums.SortType
import com.example.coinapp.ui.custom.SortButton
import com.example.coinapp.ui.home.adapter.TickerListAdapter
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
    @BindingAdapter("onSortChangedListener")
    fun bindOnSortChangedListener(
        view: SortButton, function: (SortModel) -> Unit
    ) {
        view.setOnSortChangedListener(object : SortButton.OnSortChangedListener {
            override fun onChanged(sortCategory: SortCategory, sortType: SortType) {
                function(SortModel(sortCategory, sortType))
            }
        })
    }

    @JvmStatic
    @BindingAdapter("sortArrow")
    fun bindSortArrowDrawable(
        view: SortButton, res: Int
    ) {
        view.setArrowDrawable(res)
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