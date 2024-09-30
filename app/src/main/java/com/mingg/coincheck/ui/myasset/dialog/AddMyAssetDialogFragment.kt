package com.mingg.coincheck.ui.myasset.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.FragmentDialogAddMyAssetBinding
import com.mingg.coincheck.extension.addNumberFormatter
import com.mingg.coincheck.extension.collectWithLifecycle
import com.mingg.coincheck.extension.getTextWithoutComma
import com.mingg.coincheck.model.myasset.MyTickerInfo
import com.mingg.coincheck.ui.base.BaseBottomSheetDialogFragment
import com.mingg.domain.model.myasset.MyTicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddMyAssetDialogFragment :
    BaseBottomSheetDialogFragment<FragmentDialogAddMyAssetBinding>(FragmentDialogAddMyAssetBinding::inflate) {
    private val _addMyAssetDialogViewModel: AddMyAssetDialogViewModel by viewModels()

    private var _myTickerInfo: MyTickerInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _myTickerInfo = arguments?.getParcelable(KEY_MY_TICKER)

        setupObserver()
        checkMyAssetTicker()
        setupListener()
    }

    private fun setupListener() {
        binding.apply {
            etAmount.addNumberFormatter()
            etAveragePrice.addNumberFormatter()

            btnAddAsset.setOnClickListener {
                _myTickerInfo?.let {
                    val amount = binding.etAmount.getTextWithoutComma()
                    val price = binding.etAveragePrice.getTextWithoutComma()
                    if (amount.isEmpty() || price.isEmpty()) return@setOnClickListener

                    _addMyAssetDialogViewModel.addAsset(
                        MyTicker(
                            symbol = it.symbol,
                            koreanSymbol = it.koreanSymbol,
                            englishSymbol = it.englishSymbol,
                            currencyType = it.currency,
                            amount = amount,
                            averagePrice = price
                        )
                    )
                    dismiss()
                }
            }

            btnDeleteAsset.setOnClickListener {
                _myTickerInfo?.let {
                    _addMyAssetDialogViewModel.deleteAsset(it.symbol, it.currency.name)
                    dismiss()
                }
            }
        }
    }

    private fun checkMyAssetTicker() {
        _myTickerInfo?.let {
            _addMyAssetDialogViewModel.checkMyAssetTicker(it.symbol, it.currency.name)
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _addMyAssetDialogViewModel.myAssetTicker.collectWithLifecycle(lifecycle) {
                it?.let {
                    if (it.averagePrice.isEmpty() || it.amount.isEmpty()) return@let
                    binding.btnAddAsset.setText(R.string.ticker_detail_modify_my_asset)
                    binding.btnDeleteAsset.visibility = View.VISIBLE
                    binding.etAmount.setText(it.amount)
                    binding.etAveragePrice.setText(it.averagePrice)
                }
            }
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