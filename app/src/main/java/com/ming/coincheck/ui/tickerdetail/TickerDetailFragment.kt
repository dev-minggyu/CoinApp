package com.ming.coincheck.ui.tickerdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ming.coincheck.R
import com.ming.coincheck.databinding.FragmentTickerDetailBinding
import com.ming.coincheck.extension.collectWithLifecycle
import com.ming.coincheck.extension.getParcelableCompat
import com.ming.coincheck.model.MyTickerInfo
import com.ming.coincheck.ui.base.BaseFragment
import com.ming.coincheck.ui.myasset.dialog.AddMyAssetDialogFragment
import com.ming.coincheck.utils.AppThemeManager
import com.ming.coincheck.utils.TradingViewUtil
import com.ming.domain.model.ticker.Ticker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class TickerDetailFragment : BaseFragment<FragmentTickerDetailBinding>(FragmentTickerDetailBinding::inflate) {

    private val tickerDetailViewModel: TickerDetailViewModel by viewModels()

    private var myTickerInfo: MyTickerInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myTickerInfo = arguments?.getParcelableCompat(KEY_MY_TICKER)

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
            binding.webviewChart.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadData(
                    TradingViewUtil.getScript(
                        symbol = symbol,
                        isDarkMode = AppThemeManager.isDarkMode(resources)
                    ), "text/html", "base64"
                )
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            tickerDetailViewModel.uiState.collectWithLifecycle(lifecycle) { state ->
                state.ticker?.let { ticker ->
                    updateUI(ticker)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(ticker: Ticker) {
        binding.apply {
            tvSymbol.text = getTickerSymbolText(ticker)
            tvPrice.text = ticker.decimalCurrentPrice
            tvPrevChange.text = "${ticker.changePricePrevDay} (${ticker.rate}%)"
            tvPrice.setTickerPriceColor(ticker.rate)
            tvPrevChange.setTickerPriceColor(ticker.rate)
        }
    }

    private fun getTickerSymbolText(ticker: Ticker): String {
        return if (Locale.getDefault().language == "ko") {
            "${ticker.koreanSymbol} (${ticker.symbol})"
        } else {
            "${ticker.englishSymbol} (${ticker.symbol})"
        }
    }

    private fun TextView.setTickerPriceColor(rate: String?) {
        rate ?: return
        val color: Int = when {
            rate.toFloat() > 0 -> ContextCompat.getColor(requireContext(), R.color.color_price_up)
            rate.toFloat() < 0 -> ContextCompat.getColor(requireContext(), R.color.color_price_down)
            else -> ContextCompat.getColor(requireContext(), R.color.color_price_same)
        }
        setTextColor(color)
    }

    companion object {
        const val KEY_MY_TICKER = "KEY_MY_TICKER"
    }
}