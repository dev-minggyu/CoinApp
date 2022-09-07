package com.example.domain.model.setting

data class FloatingTicker(
    val symbol: String,
    val koreanSymbol: String,
    val englishSymbol: String,
    var isChecked: Boolean
)
