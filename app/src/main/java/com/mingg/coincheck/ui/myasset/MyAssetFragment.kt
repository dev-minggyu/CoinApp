package com.mingg.coincheck.ui.myasset

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.mingg.coincheck.databinding.FragmentMyAssetBinding
import com.mingg.coincheck.extension.collectWithLifecycle
import com.mingg.coincheck.model.myasset.MyAssetHeader
import com.mingg.coincheck.model.myasset.MyAssetItem
import com.mingg.coincheck.model.myasset.MyTickerInfo
import com.mingg.coincheck.navigation.NavigationManager
import com.mingg.coincheck.ui.base.BaseFragment
import com.mingg.coincheck.ui.myasset.adpater.MyAssetListAdapter
import com.mingg.coincheck.ui.tickerdetail.TickerDetailFragment
import com.mingg.domain.model.myasset.MyTicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat

@AndroidEntryPoint
class MyAssetFragment : BaseFragment<FragmentMyAssetBinding>(FragmentMyAssetBinding::inflate) {

    private val myAssetViewModel: MyAssetViewModel by viewModels()

    private lateinit var myAssetListAdapter: MyAssetListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            myAssetViewModel.uiState.collectWithLifecycle(lifecycle) { state ->
                state.myAssetList?.let {
                    val checkedList = it.filter { ticker -> ticker.averagePrice.isNotEmpty() && ticker.amount.isNotEmpty() }
                    with(binding) {
                        rvAsset.isVisible = checkedList.isNotEmpty()
                        tvGuideAddAsset.isVisible = checkedList.isEmpty()
                    }
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

    override fun onResume() {
        super.onResume()
        myAssetViewModel.setEvent(MyAssetIntent.RefreshAssetList)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            myAssetViewModel.setEvent(MyAssetIntent.RefreshAssetList)
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

            myAssetListAdapter.submitAssetList(assetList)
        }
    }

    companion object {
        fun newInstance() = MyAssetFragment()
    }
}