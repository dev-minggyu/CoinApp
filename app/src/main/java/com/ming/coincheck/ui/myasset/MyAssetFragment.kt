package com.ming.coincheck.ui.myasset

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.ming.coincheck.databinding.FragmentMyAssetBinding
import com.ming.coincheck.extension.collectWithLifecycle
import com.ming.coincheck.ui.myasset.model.MyAssetHeader
import com.ming.coincheck.ui.myasset.model.MyAssetItem
import com.ming.coincheck.model.MyTickerInfo
import com.ming.coincheck.ui.base.BaseFragment
import com.ming.coincheck.ui.myasset.adpater.MyAssetListAdapter
import com.ming.coincheck.ui.tickerdetail.TickerDetailFragment
import com.ming.domain.model.myasset.MyTicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat

@AndroidEntryPoint
class MyAssetFragment : BaseFragment<FragmentMyAssetBinding>(FragmentMyAssetBinding::inflate) {

    private val myAssetViewModel: MyAssetViewModel by viewModels()

    private var myAssetListAdapter: MyAssetListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObserver()

        myAssetViewModel.setEvent(MyAssetIntent.ObserveTickerList)
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            myAssetViewModel.uiState.collectWithLifecycle(lifecycle) { state ->
                state.myAssetList?.let { list ->
                    val checkedList = list.filter { it.averagePrice.isNotEmpty() && it.amount.isNotEmpty() }
                    updateUI(checkedList)
                    bindAssetList(checkedList)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        myAssetListAdapter = MyAssetListAdapter {
            navigationManager.navigateToTickerDetail(
                bundleOf(
                    TickerDetailFragment.KEY_MY_TICKER to MyTickerInfo(
                        symbol = it.symbol,
                        currency = it.currencyType,
                        it.koreanSymbol,
                        it.englishSymbol
                    )
                )
            )
        }
        binding.rvAsset.apply {
            itemAnimator = null
            adapter = myAssetListAdapter
        }
    }

    private fun updateUI(checkedList: List<MyTicker>) {
        with(binding) {
            rvAsset.isVisible = checkedList.isNotEmpty()
            tvGuideAddAsset.isVisible = checkedList.isEmpty()
        }
    }

    private fun bindAssetList(list: List<MyTicker>) {
        binding.apply {
            val assetList = mutableListOf<MyAssetItem>().apply {
                add(MyAssetItem.Header(createAssetHeader(list)))
                addAll(list.map { MyAssetItem.Ticker(it) })
            }
            myAssetListAdapter?.submitAssetList(assetList)
        }
    }

    private fun createAssetHeader(list: List<MyTicker>): MyAssetHeader {
        val priceFormat = NumberFormat.getInstance()
        val totalAsset = list.sumOf { it.amount.toDouble() * it.currentPrice.toDouble() }
        val totalBuy = list.sumOf { it.amount.toDouble() * it.averagePrice.toDouble() }
        return MyAssetHeader(
            totalAsset = totalAsset,
            decimalTotalAsset = priceFormat.format(totalAsset),
            totalBuy = totalBuy,
            decimalTotalBuy = priceFormat.format(totalBuy),
            pnl = priceFormat.format(totalAsset - totalBuy),
            pnlPercent = String.format("%.2f", ((totalAsset - totalBuy) / totalBuy) * 100) + "%",
            chartData = PieDataSet(
                list.map { PieEntry(it.amount.toFloat() * it.currentPrice.toFloat(), it.symbol) }, ""
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myAssetListAdapter = null
    }
}