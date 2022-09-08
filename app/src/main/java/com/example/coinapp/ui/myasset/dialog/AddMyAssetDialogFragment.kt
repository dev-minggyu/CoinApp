package com.example.coinapp.ui.myasset.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.coinapp.R
import com.example.coinapp.ui.base.BaseBottomSheetDialogFragment
import com.example.coinapp.databinding.FragmentDialogAddMyAssetBinding
import com.example.coinapp.extension.addNumberFormatter
import com.example.coinapp.extension.collectWithLifecycle
import com.example.coinapp.extension.getTextWithoutComma
import com.example.coinapp.model.myasset.MyTickerInfo
import com.example.domain.model.myasset.MyTicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddMyAssetDialogFragment : BaseBottomSheetDialogFragment<FragmentDialogAddMyAssetBinding>(R.layout.fragment_dialog_add_my_asset) {
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
                    _addMyAssetDialogViewModel.addAsset(
                        MyTicker(
                            symbol = it.symbol,
                            koreanSymbol = it.koreanSymbol,
                            englishSymbol = it.englishSymbol,
                            currencyType = it.currency,
                            binding.etAmount.getTextWithoutComma(),
                            averagePrice = binding.etAveragePrice.getTextWithoutComma()
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