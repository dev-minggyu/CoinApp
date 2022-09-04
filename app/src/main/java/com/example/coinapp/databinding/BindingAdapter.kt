package com.example.coinapp.databinding

import android.annotation.SuppressLint
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.coinapp.R
import com.example.coinapp.ui.custom.SortButton
import com.example.coinapp.ui.home.adapter.TickerListAdapter
import com.example.domain.model.ticker.SortModel
import com.example.domain.model.ticker.Ticker
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("onNavItemSelected")
    fun bindOnNavItemSelected(
        view: BottomNavigationView, function: (Int) -> Unit
    ) {
        view.setOnItemSelectedListener { item ->
            function(item.itemId)
            true
        }
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
    @InverseBindingAdapter(attribute = "sortState", event = "sortStateAttrChanged")
    fun inverseBindSortState(view: SortButton): SortModel = view.getSortState()

    @JvmStatic
    @BindingAdapter("sortStateAttrChanged")
    fun bindOnSortChanged(
        view: SortButton, listener: InverseBindingListener?
    ) {
        view.setOnSortChangedListener(object : SortButton.OnSortChangedListener {
            override fun onChanged() {
                listener?.onChange()
            }
        })
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

    @JvmStatic
    @BindingAdapter("changeRatePrevDay")
    fun bindTickerPriceColor(
        view: TextView,
        changeRatePrevDay: String
    ) {
        val color: Int = when {
            changeRatePrevDay.toFloat() > 0 -> {
                ContextCompat.getColor(view.context, R.color.color_price_up)
            }
            changeRatePrevDay.toFloat() < 0 -> {
                ContextCompat.getColor(view.context, R.color.color_price_down)
            }
            else -> ContextCompat.getColor(view.context, R.color.color_price_same)
        }
        view.setTextColor(color)
    }

    @JvmStatic
    @BindingAdapter("textTickerSymbol")
    fun bindTextTickerSymbol(
        view: TextView,
        ticker: Ticker?
    ) {
        ticker?.let {
            if (Locale.getDefault().language == "ko") {
                view.text = ticker.koreanSymbol
            } else {
                view.text = ticker.englishSymbol
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("textDetailTickerSymbol")
    fun bindTextDetailTickerSymbol(
        view: TextView,
        ticker: Ticker?
    ) {
        ticker?.let {
            if (Locale.getDefault().language == "ko") {
                view.text = "${ticker.koreanSymbol} (${ticker.symbol})"
            } else {
                view.text = "${ticker.englishSymbol} (${ticker.symbol})"
            }
        }
    }
}