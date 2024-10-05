package com.mingg.domain.model.setting

import com.mingg.domain.model.Diffable

data class FloatingTicker(
    val symbol: String,
    val koreanSymbol: String,
    val englishSymbol: String,
    var isChecked: Boolean
) : Diffable<FloatingTicker> {
    override fun areItemsTheSame(other: FloatingTicker): Boolean {
        return this.symbol == other.symbol
    }

    override fun areContentsTheSame(other: FloatingTicker): Boolean {
        return this == other
    }
}