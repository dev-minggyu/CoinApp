package com.mingg.coincheck.ui.myasset.adpater

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.ItemAssetHeaderBinding
import com.mingg.coincheck.ui.myasset.model.MyAssetHeader

class AssetHeaderViewHolder(private val binding: ItemAssetHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val colorPriceUp = ContextCompat.getColor(itemView.context, R.color.color_price_up)
    private val colorPriceDown = ContextCompat.getColor(itemView.context, R.color.color_price_down)
    private val colorPriceSame = ContextCompat.getColor(itemView.context, R.color.color_price_same)
    private val textColor = ContextCompat.getColor(itemView.context, R.color.color_text)

    @SuppressLint("SetTextI18n")
    fun bind(item: MyAssetHeader) {
        binding.apply {
            setupChart()
            val pnlColor = getPnlColor(item.totalAsset, item.totalBuy)
            tvPnl.setTextColor(pnlColor)
            tvPnlPercent.setTextColor(pnlColor)
            tvTotalAsset.text = item.decimalTotalAsset
            tvTotalBuy.text = item.decimalTotalBuy
            tvPnl.text = item.pnl
            tvPnlPercent.text = item.pnlPercent
            chartAsset.data = PieData(
                item.chartData.apply {
                    valueFormatter = PercentFormatter(chartAsset)
                    valueTextSize = 12f
                    valueTextColor = Color.WHITE
                    colors = ColorTemplate.COLORFUL_COLORS.toList()
                }
            )
            chartAsset.invalidate()
        }
    }

    private fun ItemAssetHeaderBinding.setupChart() {
        chartAsset.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            centerText = context.getString(R.string.my_asset_chart_center_text)
            setCenterTextColor(textColor)
            isRotationEnabled = false
            setEntryLabelTextSize(12f)
            setEntryLabelColor(Color.WHITE)
            isHighlightPerTapEnabled = false
            setHoleColor(Color.TRANSPARENT)
            legend.apply {
                verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
                textColor = textColor
                xOffset = 15f
            }
        }
    }

    private fun getPnlColor(totalAsset: Double, totalBuy: Double): Int {
        return when {
            totalAsset - totalBuy > 0 -> colorPriceUp
            totalAsset - totalBuy < 0 -> colorPriceDown
            else -> colorPriceSame
        }
    }
}