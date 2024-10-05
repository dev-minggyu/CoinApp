package com.mingg.coincheck.ui.myasset.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.FragmentDialogAddMyAssetBinding
import com.mingg.coincheck.extension.addNumberFormatter
import com.mingg.coincheck.extension.collectWithLifecycle
import com.mingg.coincheck.extension.getParcelableCompat
import com.mingg.coincheck.extension.getTextWithoutComma
import com.mingg.coincheck.model.MyTickerInfo
import com.mingg.coincheck.ui.base.BaseBottomSheetDialogFragment
import com.mingg.domain.model.myasset.MyTicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddMyAssetDialogFragment :
    BaseBottomSheetDialogFragment<FragmentDialogAddMyAssetBinding>(FragmentDialogAddMyAssetBinding::inflate) {

    private val addMyAssetDialogViewModel: AddMyAssetDialogViewModel by viewModels()
    private var myTickerInfo: MyTickerInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myTickerInfo = arguments?.getParcelableCompat(KEY_MY_TICKER)

        setupObserver()
        setupListener()
        checkMyAssetTicker()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            addMyAssetDialogViewModel.uiState.collectWithLifecycle(lifecycle) { state ->
                state.myAssetTicker?.let { ticker ->
                    if (ticker.averagePrice.isNotEmpty() && ticker.amount.isNotEmpty()) {
                        setExistAsset(ticker)
                    }
                }
            }
        }
    }

    private fun setupListener() {
        binding.apply {
            etAmount.addNumberFormatter()
            etAveragePrice.addNumberFormatter()
            btnAddAsset.setOnClickListener { handleAddAsset() }
            btnDeleteAsset.setOnClickListener { handleDeleteAsset() }
        }
    }

    private fun checkMyAssetTicker() {
        myTickerInfo?.let {
            addMyAssetDialogViewModel.setEvent(AddMyAssetDialogIntent.CheckMyAssetTicker(it.symbol, it.currency.name))
        }
    }

    private fun setExistAsset(ticker: MyTicker) {
        binding.apply {
            btnAddAsset.setText(R.string.ticker_detail_modify_my_asset)
            btnDeleteAsset.visibility = View.VISIBLE
            etAmount.setText(ticker.amount)
            etAveragePrice.setText(ticker.averagePrice)
        }
    }

    private fun handleAddAsset() {
        myTickerInfo?.let {
            val amount = binding.etAmount.getTextWithoutComma()
            val price = binding.etAveragePrice.getTextWithoutComma()
            if (amount.isEmpty() || price.isEmpty()) return

            val myTicker = MyTicker(
                symbol = it.symbol,
                koreanSymbol = it.koreanSymbol,
                englishSymbol = it.englishSymbol,
                currencyType = it.currency,
                amount = amount,
                averagePrice = price
            )
            addMyAssetDialogViewModel.setEvent(AddMyAssetDialogIntent.AddAsset(myTicker))
            dismiss()
        }
    }

    private fun handleDeleteAsset() {
        myTickerInfo?.let {
            addMyAssetDialogViewModel.setEvent(AddMyAssetDialogIntent.DeleteAsset(it.symbol, it.currency.name))
            dismiss()
        }
    }

    companion object {
        fun newInstance(ticker: MyTickerInfo): AddMyAssetDialogFragment {
            val bundle = Bundle().apply {
                putParcelable(KEY_MY_TICKER, ticker)
            }
            return AddMyAssetDialogFragment().apply {
                arguments = bundle
            }
        }

        private const val KEY_MY_TICKER = "KEY_MY_TICKER"
    }
}