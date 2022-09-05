package com.example.coinapp.ui.myasset

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.coinapp.R
import com.example.coinapp.base.BaseFragment
import com.example.coinapp.databinding.FragmentMyAssetBinding
import com.example.coinapp.extension.collectWithLifecycle
import com.example.coinapp.model.MyAssetHeader
import com.example.coinapp.model.MyAssetItem
import com.example.coinapp.model.MyTickerInfo
import com.example.coinapp.ui.home.detail.TickerDetailActivity
import com.example.coinapp.ui.myasset.adpater.MyAssetListAdapter
import com.example.domain.model.myasset.MyTicker
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
            _myAssetViewModel.myAssetList.collectWithLifecycle(lifecycle) {
                it?.let {
                    binding.isNoData = it.isEmpty()
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
                pnlPercent = String.format("%.2f", ((totalAsset - totalBuy) / totalBuy) * 100) + "%",
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