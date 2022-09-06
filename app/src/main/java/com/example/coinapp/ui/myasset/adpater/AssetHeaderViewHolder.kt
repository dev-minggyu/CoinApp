package com.example.coinapp.ui.myasset.adpater

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.R
import com.example.coinapp.databinding.ItemAssetHeaderBinding
import com.example.coinapp.model.myasset.MyAssetHeader
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class AssetHeaderViewHolder(private val binding: ItemAssetHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: MyAssetHeader) {
        binding.apply {
            setupChart()
            setupTextViewColor(item)
            tvTotalAsset.text = item.decimalTotalAsset
            tvTotalBuy.text = item.decimalTotalBuy
            tvPnl.text = item.pnl
            tvPnlPercent.text = item.pnlPercent
            chartAsset.data = PieData(
                item.chartData.apply {
                    valueFormatter = PercentFormatter(chartAsset)
                    valueTextSize = 12f
                    valueTextColor = Color.WHITE
                }.apply {
                    colors = ColorTemplate.COLORFUL_COLORS.toList()
                }
            )
            chartAsset.invalidate()
        }
    }

    private fun ItemAssetHeaderBinding.setupTextViewColor(item: MyAssetHeader) {
        tvPnl.setTextColor(
            when {
                item.totalAsset - item.totalBuy > 0 -> ContextCompat.getColor(itemView.context, R.color.color_price_up)
                item.totalAsset - item.totalBuy < 0 -> ContextCompat.getColor(itemView.context, R.color.color_price_down)
                else -> ContextCompat.getColor(itemView.context, R.color.color_price_same)
            }
        )
        tvPnlPercent.setTextColor(
            when {
                item.totalAsset - item.totalBuy > 0 -> ContextCompat.getColor(itemView.context, R.color.color_price_up)
                item.totalAsset - item.totalBuy < 0 -> ContextCompat.getColor(itemView.context, R.color.color_price_down)
                else -> ContextCompat.getColor(itemView.context, R.color.color_price_same)
            }
        )
    }

    private fun ItemAssetHeaderBinding.setupChart() {
        chartAsset.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            centerText = context.getString(R.string.my_asset_chart_center_text)
            setCenterTextColor(ContextCompat.getColor(itemView.context, R.color.color_text))
            isRotationEnabled = false
            setEntryLabelTextSize(12f)
            setEntryLabelColor(Color.WHITE)
            isHighlightPerTapEnabled = false
            setHoleColor(Color.TRANSPARENT)
            legend.apply {
                verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
                textColor = ContextCompat.getColor(itemView.context, R.color.color_text)
                xOffset = 15f
            }
        }
    }
}