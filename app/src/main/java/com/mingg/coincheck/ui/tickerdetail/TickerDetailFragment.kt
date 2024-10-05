package com.mingg.coincheck.ui.tickerdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.FragmentTickerDetailBinding
import com.mingg.coincheck.model.myasset.MyTickerInfo
import com.mingg.coincheck.ui.base.BaseFragment
import com.mingg.coincheck.ui.myasset.dialog.AddMyAssetDialogFragment
import com.mingg.coincheck.utils.AppThemeManager
import com.mingg.coincheck.utils.TradingViewUtil
import com.mingg.domain.model.ticker.Ticker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class TickerDetailFragment : BaseFragment<FragmentTickerDetailBinding>(FragmentTickerDetailBinding::inflate) {

    private val tickerDetailViewModel: TickerDetailViewModel by viewModels()

    private var myTickerInfo: MyTickerInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myTickerInfo = arguments?.getParcelable(KEY_MY_TICKER)

        setupListener()
        setupWebView()
        setupObservers()

        myTickerInfo?.let {
            tickerDetailViewModel.setEvent(TickerDetailIntent.LoadTicker(it.symbol, it.currency))
        }
    }

    private fun setupListener() {
        with(binding) {
            btnBack.setOnClickListener {
                navigationManager.goBack()
            }
            btnAddAsset.setOnClickListener {
                myTickerInfo?.let {
                    AddMyAssetDialogFragment.newInstance(it).show(childFragmentManager, null)
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        myTickerInfo?.let {
            val symbol = it.symbol + it.currency.name
            binding.webviewChart.webViewClient = WebViewClient()
            binding.webviewChart.settings.javaScriptEnabled = true
            binding.webviewChart.loadData(
                TradingViewUtil.getScript(
                    symbol = symbol,
                    isDarkMode = AppThemeManager.isDarkMode(resources)
                ), "text/html", "base64"
            )
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            tickerDetailViewModel.uiState.collect { state ->
                state.ticker?.let { ticker ->
                    binding.tvSymbol.text = getTickerSymbolText(ticker)
                    binding.tvPrice.text = ticker.decimalCurrentPrice
                    binding.tvPrevChange.text = "${ticker.changePricePrevDay} (${ticker.rate}%)"
                    setTickerPriceColor(binding.tvPrice, ticker.rate)
                    setTickerPriceColor(binding.tvPrevChange, ticker.rate)
                }
            }
        }
    }

    private fun getTickerSymbolText(ticker: Ticker): String {
        return if (Locale.getDefault().language == "ko") {
            "${ticker.koreanSymbol} (${ticker.symbol})"
        } else {
            "${ticker.englishSymbol} (${ticker.symbol})"
        }
    }

    private fun setTickerPriceColor(view: TextView, rate: String?) {
        rate?.let {
            val color: Int = when {
                it.toFloat() > 0 -> ContextCompat.getColor(requireContext(), R.color.color_price_up)
                it.toFloat() < 0 -> ContextCompat.getColor(requireContext(), R.color.color_price_down)
                else -> ContextCompat.getColor(requireContext(), R.color.color_price_same)
            }
            view.setTextColor(color)
        }
    }

    companion object {
        const val KEY_MY_TICKER = "KEY_MY_TICKER"
    }
}