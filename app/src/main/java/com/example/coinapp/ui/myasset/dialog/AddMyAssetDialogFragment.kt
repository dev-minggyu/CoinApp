package com.example.coinapp.ui.myasset.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.coinapp.R
import com.example.coinapp.base.BaseBottomSheetDialogFragment
import com.example.coinapp.databinding.FragmentDialogAddMyAssetBinding
import com.example.coinapp.extension.collectWithLifecycle
import com.example.coinapp.model.MyTickerInfo
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

        binding.btnAddAsset.setOnClickListener {
            _myTickerInfo?.let {
                it.amount = binding.etAmount.text.toString()
                it.averagePrice = binding.etAveragePrice.text.toString()
                _addMyAssetDialogViewModel.addAsset(it)
                dismiss()
            }
        }
    }

    private fun checkMyAssetTicker() {
        _myTickerInfo?.let {
            _addMyAssetDialogViewModel.checkMyAssetTicker(it)
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _addMyAssetDialogViewModel.myAssetTicker.collectWithLifecycle(lifecycle) {
                it?.let {
                    binding.btnAddAsset.setText(R.string.modify_my_asset)
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