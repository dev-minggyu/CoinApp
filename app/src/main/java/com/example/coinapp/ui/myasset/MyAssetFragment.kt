package com.example.coinapp.ui.myasset

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.coinapp.R
import com.example.coinapp.base.BaseFragment
import com.example.coinapp.databinding.FragmentMyAssetBinding
import com.example.coinapp.extension.collectWithLifecycle
import com.example.coinapp.model.MyTickerInfo
import com.example.coinapp.ui.home.detail.TickerDetailActivity
import com.example.coinapp.ui.myasset.adpater.MyAssetListAdapter
import com.example.domain.model.myasset.MyTicker
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint
class MyAssetFragment : BaseFragment<FragmentMyAssetBinding>(R.layout.fragment_my_asset) {
    private val _myAssetViewModel: MyAssetViewModel by viewModels()

    private lateinit var _myAssetListAdapter: MyAssetListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupChart()
        setupObserver()
    }

    private fun setupChart() {
        binding.chartAsset.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            centerText = context.getString(R.string.my_asset_chart_center_text)
            isRotationEnabled = false
            setEntryLabelTextSize(12f)
            setEntryLabelColor(Color.WHITE)
            isHighlightPerTapEnabled = false
            legend.apply {
                verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
                textColor = ContextCompat.getColor(requireContext(), R.color.color_text)
            }
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _myAssetViewModel.myAssetList.collectWithLifecycle(lifecycle) {
                it?.let {
                    bindAssetList(it)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        _myAssetListAdapter = MyAssetListAdapter {
            TickerDetailActivity.startActivity(
                requireContext(), MyTickerInfo(symbol = it.symbol, currency = it.currencyType, it.koreanSymbol, it.englishSymbol)
            )
        }
        binding.rvAsset.apply {
            setHasFixedSize(true)
            itemAnimator = null
            adapter = _myAssetListAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        _myAssetViewModel.refreshAssetList()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            _myAssetViewModel.refreshAssetList()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindAssetList(list: List<MyTicker>) {
        val priceFormat = DecimalFormat("#,###")

        binding.apply {
            // 총 보유자산
            val totalAsset = list.sumOf { it.amount.toDouble() * it.currentPrice.toDouble() }
            tvTotalAsset.text = priceFormat.format(totalAsset)

            // 총 매수
            val totalBuy = list.sumOf { it.amount.toDouble() * it.averagePrice.toDouble() }
            tvTotalBuy.text = priceFormat.format(totalBuy)

            // 평가손익
            tvPnl.setTextColor(
                when {
                    totalAsset - totalBuy > 0 -> ContextCompat.getColor(requireContext(), R.color.color_price_up)
                    totalAsset - totalBuy < 0 -> ContextCompat.getColor(requireContext(), R.color.color_price_down)
                    else -> ContextCompat.getColor(requireContext(), R.color.color_price_same)
                }
            )
            tvPnl.text = priceFormat.format(totalAsset - totalBuy)

            // 수익률
            tvPnlPercent.setTextColor(
                when {
                    totalAsset - totalBuy > 0 -> ContextCompat.getColor(requireContext(), R.color.color_price_up)
                    totalAsset - totalBuy < 0 -> ContextCompat.getColor(requireContext(), R.color.color_price_down)
                    else -> ContextCompat.getColor(requireContext(), R.color.color_price_same)
                }
            )
            tvPnlPercent.text = String.format("%.2f", ((totalAsset - totalBuy) / totalBuy) * 100) + "%"

            // 차트
            chartAsset.data = PieData(
                PieDataSet(
                    list.map {
                        PieEntry(
                            it.amount.toFloat() * it.currentPrice.toFloat(),
                            it.symbol
                        )
                    }, ""
                ).apply {
                    colors = ColorTemplate.COLORFUL_COLORS.toList()
                }
            ).apply {
                setValueFormatter(PercentFormatter(binding.chartAsset))
                setValueTextSize(12f)
                setValueTextColor(Color.WHITE)
            }
            chartAsset.invalidate()

            // 자산 리스트
            _myAssetListAdapter.submitList(list)
        }
    }

    companion object {
        fun newInstance() = MyAssetFragment()
    }
}