package com.ming.coincheck.databinding

import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.ming.coincheck.R
import com.ming.coincheck.ui.home.adapter.TickerListAdapter
import com.ming.domain.model.ticker.Ticker
import java.util.Locale

object BindingAdapter {
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
    @BindingAdapter("changeColorBySign")
    fun bindTickerPriceColor(
        view: TextView,
        value: String?
    ) {
        value?.let {
            val color: Int = when {
                it.toFloat() > 0 -> {
                    ContextCompat.getColor(view.context, R.color.color_price_up)
                }

                it.toFloat() < 0 -> {
                    ContextCompat.getColor(view.context, R.color.color_price_down)
                }

                else -> ContextCompat.getColor(view.context, R.color.color_price_same)
            }
            view.setTextColor(color)
        }
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
}