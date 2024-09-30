package com.mingg.coincheck.ui.tickerdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.ActivityTickerDetailBinding
import com.mingg.coincheck.model.myasset.MyTickerInfo
import com.mingg.coincheck.ui.base.BaseActivity
import com.mingg.coincheck.ui.myasset.dialog.AddMyAssetDialogFragment
import com.mingg.coincheck.utils.AppThemeManager
import com.mingg.coincheck.utils.TradingViewUtil
import com.mingg.domain.model.ticker.Ticker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class TickerDetailActivity :
    BaseActivity<ActivityTickerDetailBinding>(ActivityTickerDetailBinding::inflate) {

    private val tickerDetailViewModel: TickerDetailViewModel by viewModels()

    private var myTickerInfo: MyTickerInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myTickerInfo = intent.getParcelableExtra(KEY_MY_TICKER)

        setupActionBar()
        setupWebView()
        setupObservers()

        myTickerInfo?.let {
            tickerDetailViewModel.setEvent(TickerDetailIntent.LoadTicker(it.symbol, it.currency))
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

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
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
                it.toFloat() > 0 -> ContextCompat.getColor(this, R.color.color_price_up)
                it.toFloat() < 0 -> ContextCompat.getColor(this, R.color.color_price_down)
                else -> ContextCompat.getColor(this, R.color.color_price_same)
            }
            view.setTextColor(color)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.ticker_detail_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.menu_add_my_asset -> {
                myTickerInfo?.let {
                    AddMyAssetDialogFragment.newInstance(it).show(supportFragmentManager, null)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun startActivity(context: Context, ticker: MyTickerInfo) {
            val intent = Intent(context, TickerDetailActivity::class.java)
            intent.putExtra(KEY_MY_TICKER, ticker)
            context.startActivity(intent)
        }

        private const val KEY_MY_TICKER = "KEY_MY_TICKER"
    }
}