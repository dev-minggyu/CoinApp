package com.example.coinapp.databinding

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.coinapp.R
import com.example.domain.model.myasset.MyTicker
import com.example.domain.model.ticker.Ticker
import java.util.*

object AssetItemBindingAdapter {
    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("asset:detailTickerSymbol")
    fun bindTextDetailTickerSymbol(
        view: TextView,
        ticker: MyTicker?
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