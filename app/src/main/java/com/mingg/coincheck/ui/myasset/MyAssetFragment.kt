package com.mingg.coincheck.ui.myasset

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.FragmentMyAssetBinding
import com.mingg.coincheck.extension.collectWithLifecycle
import com.mingg.coincheck.model.myasset.MyAssetHeader
import com.mingg.coincheck.model.myasset.MyAssetItem
import com.mingg.coincheck.model.myasset.MyTickerInfo
import com.mingg.coincheck.ui.base.BaseFragment
import com.mingg.coincheck.ui.tickerdetail.TickerDetailActivity
import com.mingg.coincheck.ui.myasset.adpater.MyAssetListAdapter
import com.mingg.domain.model.myasset.MyTicker
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat

@AndroidEntryPoint
class MyAssetFragment : BaseFragment<FragmentMyAssetBinding>(R.layout.fragment_my_asset) {
    private val _myAssetViewModel: MyAssetViewModel by viewModels()

    private lateinit var _myAssetListAdapter: MyAssetListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _myAssetViewModel.myAssetList.collectWithLifecycle(lifecycle) { assetList ->
                assetList?.let {
                    val checkedList = it.filter { ticker -> ticker.averagePrice.isNotEmpty() && ticker.amount.isNotEmpty() }
                    binding.isNoData = checkedList.isEmpty()
                    bindAssetList(checkedList)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        _myAssetListAdapter = MyAssetListAdapter {
            TickerDetailActivity.startActivity(
                requireContext(),
                MyTickerInfo(
                    symbol = it.symbol,
                    currency = it.currencyType,
                    it.koreanSymbol,
                    it.englishSymbol
                )
            )
        }
        binding.rvAsset.apply {
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
        binding.apply {
            val priceFormat = NumberFormat.getInstance()
            val totalAsset = list.sumOf { it.amount.toDouble() * it.currentPrice.toDouble() }
            val totalBuy = list.sumOf { it.amount.toDouble() * it.averagePrice.toDouble() }

            val header = MyAssetHeader(
                totalAsset = totalAsset,
                decimalTotalAsset = priceFormat.format(totalAsset),
                totalBuy = totalBuy,
                decimalTotalBuy = priceFormat.format(totalBuy),
                pnl = priceFormat.format(totalAsset - totalBuy),
                pnlPercent = String.format(
                    "%.2f",
                    ((totalAsset - totalBuy) / totalBuy) * 100
                ) + "%",
                chartData = PieDataSet(
                    list.map {
                        PieEntry(
                            it.amount.toFloat() * it.currentPrice.toFloat(),
                            it.symbol
                        )
                    }, ""
                )
            )

            val assetList = mutableListOf<MyAssetItem>()
            assetList.add(MyAssetItem.Header(header))
            assetList.addAll(list.map {
                MyAssetItem.Ticker(it)
            })

            _myAssetListAdapter.submitAssetList(assetList)
        }
    }

    companion object {
        fun newInstance() = MyAssetFragment()
    }
}