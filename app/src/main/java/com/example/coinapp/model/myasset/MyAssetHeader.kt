package com.example.coinapp.model.myasset

import com.github.mikephil.charting.data.PieDataSet

data class MyAssetHeader(
    val totalAsset: Double,
    val decimalTotalAsset: String,
    val totalBuy: Double,
    val decimalTotalBuy: String,
    val pnl: String,
    val pnlPercent: String,
    val chartData: PieDataSet
)
